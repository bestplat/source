package com.bestplat.framework.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import com.bestplat.framework.Lang;

/**
 * 代理工具，用于生成代理对象
 * 
 * @author lujijiang
 * 
 */
public class Proxys {
	/**
	 * 是否使用cglib库来执行代理调用
	 */
	private final static boolean USE_CGLIB = System
			.getProperty("java.specification.version") != null
			&& System.getProperty("java.specification.version")
					.compareTo("1.6") < 0;

	/**
	 * 生成代理对象。所有的类型中最多只能有一个非接口类型
	 * 
	 * @param invocationHandler
	 *            代理执行器
	 * @param mainType
	 *            主要类型（生成的对象将直接作为此类型返回）
	 * @param otherTypes
	 *            其它类型
	 * @param arguments
	 *            构造参数
	 * @return 代理对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T proxy(final InvocationHandler invocationHandler,
			Class<? extends T> mainType, Class<?>[] otherTypes,
			Object... arguments) {
		int extendTypeCount = 0;
		Class<?> extendType = null;
		List<Class<?>> interfaceTypes = new ArrayList<Class<?>>();
		if (!mainType.isInterface()) {
			if (Modifier.isFinal(mainType.getModifiers())) {
				throw new IllegalArgumentException(String.format(
						"The main type:%s should'nt be final class", mainType));
			}
			extendTypeCount++;
			extendType = mainType;
		} else {
			interfaceTypes.add(mainType);
		}
		if (otherTypes != null) {
			for (Class<?> otherType : otherTypes) {
				if (!otherType.isInterface()) {
					if (Modifier.isFinal(otherType.getModifiers())) {
						throw new IllegalArgumentException(String.format(
								"The other type:%s should'nt be final class",
								otherType));
					}
					if (extendTypeCount > 0) {
						throw new IllegalArgumentException(String.format(
								"Should only one extend type,but got:%s,%s",
								extendType, otherType));
					}
					extendTypeCount++;
					extendType = otherType;
				} else {
					interfaceTypes.add(otherType);
				}
			}
		}
		if (extendType == null && !USE_CGLIB) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader() == null ? Proxys.class
					.getClassLoader() : Thread.currentThread()
					.getContextClassLoader();
			return (T) Proxy.newProxyInstance(loader,
					interfaceTypes.toArray(new Class<?>[0]),
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							method.setAccessible(true);
							return invocationHandler
									.invoke(proxy, method, args);
						}
					});
		}
		Enhancer en = new Enhancer();
		if (extendType != null) {
			en.setSuperclass(extendType);
		}
		if (interfaceTypes.isEmpty()) {
			en.setInterfaces(interfaceTypes.toArray(new Class<?>[0]));
		}
		en.setCallback(new MethodInterceptor() {
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				method.setAccessible(true);
				return invocationHandler.invoke(obj, method, args);
			}
		});
		if (arguments == null || arguments.length == 0) {
			return (T) en.create();
		}
		Class<?>[] argumentTypes = new Class[arguments.length];
		for (int i = 0; i < argumentTypes.length; i++) {
			argumentTypes[i] = arguments[i] == null ? null : arguments[i]
					.getClass();
		}
		try {
			return (T) ConstructorUtils.getMatchingAccessibleConstructor(
					en.createClass(), argumentTypes).newInstance(arguments);
		} catch (Exception e) {
			throw Lang.wrapCause(e);
		}
	}

	/**
	 * 生成代理对象。
	 * 
	 * @param invocationHandler
	 *            代理执行器
	 * @param mainType
	 *            主要类型（生成的对象将直接作为此类型返回）
	 * @param arguments
	 *            构造参数
	 * @return 代理对象
	 */
	public static <T> T proxy(final InvocationHandler invocationHandler,
			Class<? extends T> mainType, Object... arguments) {
		return proxy(invocationHandler, mainType, null, arguments);
	}
}
