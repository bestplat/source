package com.vssq.core.startup;

import org.springframework.stereotype.Component;

import com.bestplat.framework.startup.Startup;

@Component
public class Startup1 {
	@Startup(value = 1, ignoreError = true)
	public void test1() {
		System.out.println(this.getClass() + ".test1 executing");
		throw new RuntimeException("aaaaaaaaa");
	}
}
