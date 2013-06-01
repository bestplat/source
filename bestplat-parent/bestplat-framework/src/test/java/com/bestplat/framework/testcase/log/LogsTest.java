package com.bestplat.framework.testcase.log;

import org.junit.Test;

import com.bestplat.framework.log.Logs;

public class LogsTest {
	@Test
	public void testLog() {
		Logs.debug(new Throwable("测试异常信息"), "测试日志器一般信息:%d", 32323232);
		// Logs.error("测试日志器错误信息");
	}
}
