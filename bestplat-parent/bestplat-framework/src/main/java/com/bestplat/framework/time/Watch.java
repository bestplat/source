package com.bestplat.framework.time;

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
	private final static ThreadLocal<Long> millisTime = new ThreadLocal<Long>();

	/**
	 * 开始计时
	 */
	public static void start() {
		millisTime.set(System.currentTimeMillis());
	}

	/**
	 * 统计计时
	 * 
	 * @return
	 */
	public static long count() {
		Long t = millisTime.get();
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
		Long t = millisTime.get();
		long d = -1;
		if (t != null) {
			d = System.currentTimeMillis() - t;
		}
		millisTime.set(System.currentTimeMillis());
		return d;
	}
}
