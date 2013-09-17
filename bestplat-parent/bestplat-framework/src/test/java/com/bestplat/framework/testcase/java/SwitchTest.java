package com.bestplat.framework.testcase.java;

import org.junit.Test;

public class SwitchTest {
	@Test
	public void switchString() {
		String key = "2";
		switch (key) {
		case "1":
			System.out.println("switch" + key);
			break;
		case "2":
			System.out.println("switch" + key);
			break;
		default:
			break;
		}
	}
}
