package com.bestplat.framework.startup;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.bestplat.framework.Lang;
import com.bestplat.framework.log.Logs;
import com.bestplat.framework.util.Packages;

/**
 * Spring启动器，在Spring上下文初始化之后调用启动器
 * 
 * @author lujijiang
 * 
 */
public class SpringStartup implements
		ApplicationListener<ContextRefreshedEvent> {

	/**
	 * 启动元数据
	 * 
	 * @author lujijiang
	 * 
	 */
	class StartupMeta implements Comparable<StartupMeta> {
		Startup startup;
		Method method;
		Class<?> type;

		public int compareTo(StartupMeta o) {
			return o.startup.value() - startup.value();
		}
	}

	/**
	 * 需要扫描的包
	 */
	private String scanPackages;
	/**
	 * 是否已执行，避免重复执行
	 */
	private boolean isExecute;

	public void setScanPackages(String scanPackages) {
		this.scanPackages = scanPackages;
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!isExecute) {
			isExecute = true;
			ApplicationContext ac = event.getApplicationContext();
			if (!Lang.isEmpty(scanPackages)) {
				Set<Class<?>> types = Packages.scanClasses(scanPackages);
				TreeSet<StartupMeta> startupMetas = new TreeSet<SpringStartup.StartupMeta>();
				for (Class<?> type : types) {
					if (type.isInterface()
							|| Modifier.isAbstract(type.getModifiers())) {
						continue;
					}
					scanClass(type, startupMetas);
				}
				executeStartups(ac, startupMetas);
			}
		}
	}

	private void executeStartups(ApplicationContext ac,
			TreeSet<StartupMeta> startupMetas) {
		for (StartupMeta startupMeta : startupMetas) {
			try {
				Logs.info("Begin execute startup(method=%s,beanType=%s)",
						startupMeta.method, startupMeta.type.getCanonicalName());
				Object bean = ac.getBean(startupMeta.type);
				if (startupMeta.method.getParameterTypes().length == 1) {
					startupMeta.method.invoke(bean, new Object[] { ac });
				} else {
					startupMeta.method.invoke(bean, new Object[] {});
				}
			} catch (Throwable e) {
				if (!startupMeta.startup.ignoreError()) {
					throw new IllegalStateException(String.format(
							"Execute startup(method=%s,beanType=%s) error:",
							startupMeta.method,
							startupMeta.type.getCanonicalName()), e);
				}
			}
		}
	}

	/**
	 * 处理每一个类型
	 * 
	 * @param ac
	 * @param cls
	 */
	private void scanClass(Class<?> type, TreeSet<StartupMeta> startupMetas) {
		for (Method method : type.getMethods()) {
			if (method.getParameterTypes().length > 1) {
				continue;
			}
			if (method.getParameterTypes().length == 1) {
				if (!method.getParameterTypes()[0]
						.isAssignableFrom(ApplicationContext.class)) {
					continue;
				}
			}
			Startup startup = method.getAnnotation(Startup.class);
			if (startup == null) {
				continue;
			}
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			StartupMeta startupMeta = new StartupMeta();
			startupMeta.type = type;
			startupMeta.method = method;
			startupMeta.startup = startup;
			startupMetas.add(startupMeta);
		}

	}

}
