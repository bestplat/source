package com.vssq.core.rpc.impl;

import org.springframework.stereotype.Component;

import com.bestplat.framework.rpc.Rpc;
import com.vssq.core.rpc.facade.TestRpc;

@Rpc("testRpc")
@Component
public class TestRpcImpl implements TestRpc {
	public Object test(Object arg1, Object arg2) {
		System.out.println("RPC server has been invoked.....");
		System.out.println("arg1=" + arg1);
		System.out.println("arg2=" + arg2);
		return "{arg1=" + arg1 + ",arg2=" + arg2 + "}";
	}

}
