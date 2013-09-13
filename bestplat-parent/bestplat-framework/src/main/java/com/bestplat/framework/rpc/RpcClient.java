package com.bestplat.framework.rpc;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bestplat.framework.Lang;
import com.bestplat.framework.log.Logs;
import com.bestplat.framework.util.Packages;
import com.bestplat.framework.util.Proxys;
import com.bestplat.framework.util.Watch;

/**
 * RPC客户端
 * 
 * @author lujijiang
 * 
 */
public class RpcClient implements BeanFactoryPostProcessor {

	private static Logger log = Logs.getLogger();

	/**
	 * 要扫描的包
	 */
	private String scanPackages;
	/**
	 * 服务路径
	 */
	private String url;

	/**
	 * 连接超时时间
	 */
	private int connectionTimeout = 60000;
	/**
	 * 读取超时时间
	 */
	private int readTimeout = 60000;

	public void setScanPackages(String scanPackages) {
		this.scanPackages = scanPackages;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Set<Class<?>> rpcClassSet = Packages.scanClassesByAnnotation(
				scanPackages, Rpc.class);
		for (final Class<?> rpcClass : rpcClassSet) {
			if (rpcClass.isInterface()
					|| Modifier.isAbstract(rpcClass.getModifiers())) {
				final Map<Method, Integer> methodKeyMap = new HashMap<Method, Integer>();
				for (Method method : rpcClass.getMethods()) {
					String[] parameterTypeNames = new String[method
							.getParameterTypes().length];
					for (int i = 0; i < parameterTypeNames.length; i++) {
						parameterTypeNames[i] = method.getParameterTypes()[i]
								.getCanonicalName();
					}
					int key = Lang.hashCode(method.getName(),
							parameterTypeNames);
					methodKeyMap.put(method, key);
				}
				final Rpc rpc = (Rpc) rpcClass.getAnnotation(Rpc.class);
				Object bean = Proxys.proxy(new InvocationHandler() {
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						Watch.start();
						RpcRequest rpcRequest = new RpcRequest();
						rpcRequest.setArgs(args);
						rpcRequest.setMethod(method.getName());
						int key = methodKeyMap.get(method);
						rpcRequest.setKey(key);
						rpcRequest.setName(rpc.value());
						String requestJSON = JSON.toJSONString(rpcRequest,
								SerializerFeature.WriteClassName,
								SerializerFeature.BrowserCompatible);
						URL url = new URL(RpcClient.this.url);
						if (url.getProtocol().equalsIgnoreCase("http")
								|| url.getProtocol().equalsIgnoreCase("https")) {
							HttpURLConnection hc = (HttpURLConnection) url
									.openConnection();
							hc.setConnectTimeout(connectionTimeout);
							hc.setReadTimeout(readTimeout);
							hc.setRequestMethod("POST");
							hc.setDoOutput(true);
							hc.setDoInput(true);
							hc.setUseCaches(false);
							hc.setRequestProperty("Content-Type",
									"application/x-www-form-urlencoded");
							try {
								OutputStream os = hc.getOutputStream();
								IOUtils.write(
										"$="
												+ URLEncoder.encode(
														requestJSON, "UTF-8"),
										os);
								os.flush();
								os.close();
								InputStream is = hc.getInputStream();
								byte[] responseBytes = IOUtils.toByteArray(is);
								is.close();
								if (log.isDebugEnabled()) {
									log.debug(String.format(
											"The response data is:\r\n%s",
											new String(responseBytes, "UTF-8")));
								}
								RpcResponse rpcResponse;
								try {
									rpcResponse = (RpcResponse) JSON
											.parse(responseBytes);
								} catch (Exception ex) {
									throw new RuntimeException(
											String.format(
													"The response data for RPC(%s) is not a valid json data,the data is:\r\n%s\r\n",
													rpc.value(), new String(
															responseBytes,
															"UTF-8")), ex);
								}
								if (rpcResponse.error != null) {
									throw new RuntimeException(
											rpcResponse.error);
								}
								return rpcResponse.result;
							} finally {
								hc.disconnect();
								long costTime = Watch.count();
								if (log.isDebugEnabled()) {
									log.debug(String
											.format("The RPC(%s)'s method(%s) execute cost time is %dms",
													rpc.value(),
													method.getName(), costTime));
								}
							}
						} else {
							throw new IllegalStateException(
									String.format(
											"Url protocol(%s) is not supported,url is: %s",
											url.getProtocol(), url));
						}
					}
				}, rpcClass);
				String beanName = rpc.value();
				beanFactory.registerSingleton(beanName, bean);
			}
		}
	}
}
