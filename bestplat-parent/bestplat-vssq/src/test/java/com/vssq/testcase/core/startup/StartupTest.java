package com.vssq.testcase.core.startup;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class StartupTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void test() {
		System.out.println("Test..............");
	}
}
