package com.bestplat.framework.rpc;

/**
 * Rpc请求对象
 * 
 * @author lujijiang
 * 
 */
public class RpcRequest {
	/**
	 * 调用类型
	 */
	private String name;
	/**
	 * 调用方法名
	 */
	private String method;
	/**
	 * 调用方法key
	 */
	private Integer key;
	/**
	 * 参数列表
	 */
	private Object[] args;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
