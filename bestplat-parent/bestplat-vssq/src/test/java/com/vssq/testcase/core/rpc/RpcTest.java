package com.vssq.testcase.core.rpc;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.vssq.core.rpc.facade.TestRpc;

@ContextConfiguration(locations = { "classpath*:test_spring/rpc.xml" })
public class RpcTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	TestRpc testRpc;

	@Test
	public void test() {
		System.out.println(testRpc.test(new Date(), "bbbbbb2".getClass()));
	}
}
