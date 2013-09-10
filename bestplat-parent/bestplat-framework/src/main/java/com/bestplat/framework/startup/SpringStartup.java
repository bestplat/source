package com.bestplat.framework.startup;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.bestplat.framework.log.Logs;
import com.bestplat.framework.util.PackageScanner;

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
		Object bean;

		public int compareTo(StartupMeta o) {
			return o.startup.value() - startup.value();
		}
	}

	/**
	 * 需要扫描的包
	 */
	private String scanPackages;

	public void setScanPackages(String scanPackages) {
		this.scanPackages = scanPackages;
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ac = event.getApplicationContext();
		Set<Class<?>> types = PackageScanner.scan(scanPackages);
		TreeSet<StartupMeta> startupMetas = new TreeSet<SpringStartup.StartupMeta>();
		for (Class<?> type : types) {
			if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
				continue;
			}
			scanClass(ac, type, startupMetas);
		}
		executeStartups(startupMetas);
	}

	private void executeStartups(TreeSet<StartupMeta> startupMetas) {
		for (StartupMeta startupMeta : startupMetas) {
			try {
				Logs.info("Begin execute startup(method=%s,beanType=%s)",
						startupMeta.method, startupMeta.bean.getClass()
								.getCanonicalName());
				startupMeta.method.invoke(startupMeta.bean, new Object[] {});
			} catch (Throwable e) {
				if (!startupMeta.startup.ignoreError()) {
					throw new IllegalStateException(String.format(
							"Execute startup(method=%s,beanType=%s) error:",
							startupMeta.method, startupMeta.bean.getClass()
									.getCanonicalName()), e);
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
	private void scanClass(ApplicationContext ac, Class<?> type,
			TreeSet<StartupMeta> startupMetas) {
		Object bean = ac.getBean(type);
		for (Method method : type.getMethods()) {
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			Startup startup = method.getAnnotation(Startup.class);
			if (startup == null) {
				continue;
			}
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			StartupMeta startupMeta = new StartupMeta();
			startupMeta.bean = bean;
			startupMeta.method = method;
			startupMeta.startup = startup;
			startupMetas.add(startupMeta);
		}

	}

}
