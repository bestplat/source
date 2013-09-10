package com.vssq.core.consts;

/**
 * 数据状态枚举
 * 
 * @author lujijiang
 * 
 */
public enum ValidStatus {
	/**
	 * 有效状态
	 */
	VALID('1'),
	/**
	 * 无效状态
	 */
	INVALID('0');
	private char value;

	private ValidStatus(char value) {
		this.value = value;
	}

	public char value() {
		return value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
