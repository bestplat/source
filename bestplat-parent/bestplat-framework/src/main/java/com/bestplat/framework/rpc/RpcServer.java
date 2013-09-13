package com.bestplat.framework.rpc;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.HttpRequestHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bestplat.framework.Lang;
import com.bestplat.framework.log.Logs;
import com.bestplat.framework.util.Packages;
import com.bestplat.framework.util.Watch;

/**
 * RPC服务端
 * 
 * @author lujijiang
 * 
 */
public class RpcServer implements ApplicationContextAware, InitializingBean,
		HttpRequestHandler {
	/**
	 * 日志器
	 */
	private static Logger log = Logs.getLogger();
	/**
	 * 上下文对象
	 */
	ApplicationContext applicationContext;

	/**
	 * 方法键缓存
	 */
	private Map<Class<?>, Map<Integer, Method>> methodKeyMapCache = new HashMap<Class<?>, Map<Integer, Method>>();
	/**
	 * bean池
	 */
	private Map<String, Object> beanMap;
	/**
	 * 要扫描的包
	 */
	private String scanPackages;

	public String getScanPackages() {
		return scanPackages;
	}

	public void setScanPackages(String scanPackages) {
		this.scanPackages = scanPackages;
	}

	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestJSON = request.getParameter("$");
		if (log.isDebugEnabled()) {
			log.debug(String.format("The request data is:\r\n%s", requestJSON));
		}
		final RpcRequest apiRequest = (RpcRequest) JSON.parse(requestJSON);
		if (apiRequest == null) {
			throw new IllegalStateException(String.format(
					"The request data is invalid:\r\n%s", requestJSON));
		}
		Object bean = beanMap.get(apiRequest.getName());
		if (bean == null) {
			throw new IllegalStateException(
					String.format(
							"Could not found a bean which match %s,and all bean's keys is:%s",
							apiRequest.getName(), beanMap.keySet()));
		}
		RpcResponse apiResponse = new RpcResponse();
		try {
			Map<Integer, Method> methodMap = methodKeyMapCache.get(bean
					.getClass());
			boolean isInvoked = false;
			if (methodMap != null) {
				Method method = methodMap.get(apiRequest.getKey());
				if (method != null) {
					if (log.isDebugEnabled()) {
						log.debug(String
								.format("Find exactly method %s for api which name is %s",
										method, apiRequest.getName()));
					}
					if (!method.isAccessible()) {
						method.setAccessible(true);
					}
					Watch.start();
					apiResponse.result = method.invoke(bean,
							apiRequest.getArgs());
					long costTime = Watch.count();
					if (log.isDebugEnabled()) {
						log.debug(String
								.format("The API(%s)'s method(%s) execute cost time is %dms",
										apiRequest.getName(), method.getName(),
										costTime));
					}
					isInvoked = true;
				}
			}
			// 找不到精确匹配的方法，尝试按照方法名和参数类型模糊匹配去调用
			if (!isInvoked) {
				Class<?>[] paramTypes = new Class[apiRequest.getArgs().length];
				for (int i = 0; i < paramTypes.length; i++) {
					Object arg = apiRequest.getArgs()[i];
					if (arg != null) {
						paramTypes[i] = arg.getClass();
					}
				}
				Method method = MethodUtils.getMatchingAccessibleMethod(
						bean.getClass(), apiRequest.getMethod(), paramTypes);
				if (method != null) {
					if (log.isWarnEnabled()) {
						log.warn(String
								.format("Can't find exactly method for api which name is %s,API engine will guess a method(%s) for execute",
										apiRequest.getName(), method));
					}
					Watch.start();
					apiResponse.result = method.invoke(bean,
							apiRequest.getArgs());
					long costTime = Watch.count();
					if (log.isDebugEnabled()) {
						log.debug(String
								.format("The API(%s)'s method(%s) execute cost time is %dms",
										apiRequest.getName(), method.getName(),
										costTime));
					}
				} else {
					throw new IllegalStateException(
							String.format(
									"Can not find a suitable method for perform with API name %s",
									apiRequest.getName()));
				}
			}
		} catch (IllegalArgumentException e) {
			apiResponse.error = "Server side error:" + Lang.toString(e);
			log.error(String.format(
					"server side execute api method %s with type %s error",
					apiRequest.getMethod(), bean.getClass()), e);
		} catch (InvocationTargetException e) {
			apiResponse.error = "Server side error:" + Lang.toString(e);
			log.error(String.format(
					"server side execute api method %s with type %s error",
					apiRequest.getMethod(), bean.getClass()), e);
		} catch (IllegalAccessException e) {
			apiResponse.error = "Server side error:" + Lang.toString(e);
			log.error(String.format(
					"server side execute api method %s with type %s error",
					apiRequest.getMethod(), bean.getClass()), e);
		}
		String json = JSON.toJSONString(apiResponse,
				SerializerFeature.WriteClassName,
				SerializerFeature.BrowserCompatible);
		OutputStream os = response.getOutputStream();
		IOUtils.write(json, os);
		os.flush();
		os.close();
	}

	public void afterPropertiesSet() throws Exception {
		beanMap = new HashMap<String, Object>();
		Set<Class<?>> rpcClassSet = Packages.scanClassesByAnnotation(
				scanPackages, Rpc.class);
		for (Class<?> rpcClass : rpcClassSet) {
			if (rpcClass.isInterface()) {
				continue;
			}
			if (Modifier.isAbstract(rpcClass.getModifiers())) {
				continue;
			}
			try {
				Rpc rpc = rpcClass.getAnnotation(Rpc.class);
				String name = rpc.value();
				if (beanMap.containsKey(name)) {
					throw new IllegalStateException(String.format(
							"Duplicate the name(%s) of the Rpc", name));
				}
				Object bean;
				try {
					bean = applicationContext.getBean(rpcClass);
				} catch (Exception ex) {
					throw new IllegalStateException(
							String.format(
									"The Rpc engine need existing bean for type %s,please use @Component or @Service with the @Rpc",
									rpcClass.getCanonicalName()), ex);
				}
				beanMap.put(name, bean);
				if (log.isInfoEnabled()) {
					log.info(String.format(
							"Publishing Rpc Service:%s,implement class is:%s",
							name, rpcClass.getCanonicalName()));
				}
				// 设置方法缓存
				final Map<Integer, Method> methodKeyMap = new HashMap<Integer, Method>();
				methodKeyMapCache.put(rpcClass, methodKeyMap);
				for (Method method : rpcClass.getMethods()) {
					String[] parameterTypeNames = new String[method
							.getParameterTypes().length];
					for (int i = 0; i < parameterTypeNames.length; i++) {
						parameterTypeNames[i] = method.getParameterTypes()[i]
								.getCanonicalName();
					}
					int key = Lang.hashCode(method.getName(),
							parameterTypeNames);
					methodKeyMap.put(key, method);
				}
			} catch (Exception ex) {
				throw new BeanCreationException(String.format(
						"The class %s's spring bean could't be got", rpcClass),
						ex);
			}
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
