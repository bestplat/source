package com.vssq.core.startup;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bestplat.framework.startup.Startup;

@Component
public class Startup2 {
	@Startup(2)
	public void test2() {
		System.out.println(this.getClass() + ".test2 executing");
	}

	@Startup(3)
	public void test3(ApplicationContext ac) {
		System.out.println(this.getClass() + ".test3 executing,ac=" + ac);
	}
}
