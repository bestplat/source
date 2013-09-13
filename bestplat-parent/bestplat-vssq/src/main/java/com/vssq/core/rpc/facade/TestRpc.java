package com.vssq.core.rpc.facade;

import com.bestplat.framework.rpc.Rpc;

@Rpc("testRpc")
public interface TestRpc {
	public Object test(Object arg1, Object arg2);
}
