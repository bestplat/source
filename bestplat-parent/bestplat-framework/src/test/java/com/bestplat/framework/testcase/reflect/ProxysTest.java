package com.bestplat.framework.testcase.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.bestplat.framework.log.Logs;
import com.bestplat.framework.util.Proxys;
import com.bestplat.framework.util.Watch;

public class ProxysTest {
	@Test
	public void testProxy() {
		Watch.start();
		for (int i = 0; i < 1000000; i++) {
			List list = Proxys.proxy(new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if ("get".equals(method.getName())) {
						Logs.info("The args:" + Arrays.asList(args));
					}
					return null;
				}
			}, List.class);
		}
		Logs.info("time:" + Watch.count() + "ms");
		final List orgList = new ArrayList();
		orgList.add(1111);
		List list = Proxys.proxy(new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if ("get".equals(method.getName())) {
					return orgList.get(0);
				}
				return null;
			}
		}, List.class);
		list.get(887);
		Watch.start();
		for (int i = 0; i < 1000000; i++) {
			list.get(333);
		}
		Logs.info("time:" + Watch.count() + "ms");
	}
}
