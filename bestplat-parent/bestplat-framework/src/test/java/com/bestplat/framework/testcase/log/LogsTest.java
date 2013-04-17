package com.bestplat.framework.testcase.log;

import org.junit.Test;

import com.bestplat.framework.log.Logs;

public class LogsTest {
	@Test
	public void testLog() {
		Logs.info("测试日志器一般信息");
		Logs.error("测试日志器错误信息");
	}
}
