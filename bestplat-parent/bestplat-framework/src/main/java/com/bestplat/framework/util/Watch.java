package com.bestplat.framework.util;

/**
 * 线程计时器
 * 
 * @author lujijiang
 * 
 */
public class Watch {
	/**
	 * 时间计数器
	 */
	private final static ThreadLocal<Long> TIME = new ThreadLocal<Long>();

	/**
	 * 开始计时
	 */
	public static void start() {
		TIME.set(System.currentTimeMillis());
	}

	/**
	 * 统计计时
	 * 
	 * @return
	 */
	public static long count() {
		Long t = TIME.get();
		if (t == null) {
			return -1;
		}
		return System.currentTimeMillis() - t;
	}

	/**
	 * 重置计时
	 * 
	 * @return
	 */
	public static long reset() {
		Long t = TIME.get();
		long d = -1;
		if (t != null) {
			d = System.currentTimeMillis() - t;
		}
		TIME.set(System.currentTimeMillis());
		return d;
	}
}
