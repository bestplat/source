package com.bestplat.framework.testcase.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class JDK7Test {
	@Test
	public void testSwitch() {
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

	// @Test
	public String testIO() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("a"))) {
			return br.readLine();
		}
	}
}
