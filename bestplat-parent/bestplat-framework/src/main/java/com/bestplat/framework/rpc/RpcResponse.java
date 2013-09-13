package com.bestplat.framework.rpc;

/**
 * Rpc应答对象
 * 
 * @author lujijiang
 * 
 */
public class RpcResponse {
	/**
	 * 结果
	 */
	Object result;
	/**
	 * 错误消息
	 */
	String error;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
