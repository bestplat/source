package com.bestplat.framework.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * 通用简单日志器，依赖于slf4j的API，支持log4j和logback实现，最大限度优化性能
 * 
 * @author lujijiang
 * 
 */
public class Logs {

	private static final Object[] EMPTY_ARRAY = new Object[] {};
	private static final String FQCN = Logs.class.getName();

	/**
	 * 获取适配日志器，供内部调用
	 * 
	 * @return
	 */
	@SuppressWarnings("restriction")
	private static LocationAwareLogger getLocationAwareLogger(final int depth) {
		String className = sun.reflect.Reflection.getCallerClass(depth)
				.getName();
		return (LocationAwareLogger) LoggerFactory.getLogger(className);
	}

	/**
	 * 静态的获取日志器
	 * 
	 * @return
	 */
	public static Logger getLogger() {
		return getLocationAwareLogger(3);
	}

	/**
	 * 获取日志器名称
	 * 
	 * @return
	 */
	public static String getName() {
		return getLocationAwareLogger(3).getName();
	}

	/**
	 * 判断日志器实现是否支持trace级别
	 * 
	 * @return
	 */
	public static boolean isTraceEnabled() {
		return getLocationAwareLogger(3).isTraceEnabled();
	}

	/**
	 * 输出trace级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 */
	public static void trace(Object msg) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.TRACE_INT, format, EMPTY_ARRAY, null);
	}

	/**
	 * 输出trace级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void trace(Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.TRACE_INT, String.format(format, args),
				EMPTY_ARRAY, null);
	}

	/**
	 * 输出trace级别信息
	 * 
	 * @param t
	 *            异常信息
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void trace(Throwable t, Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.TRACE_INT, String.format(format, args),
				EMPTY_ARRAY, t);
	}

	/**
	 * 输出debug级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 */
	public static void debug(Object msg) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.DEBUG_INT, format, EMPTY_ARRAY, null);
	}

	/**
	 * 输出debug级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void debug(Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.DEBUG_INT, String.format(format, args),
				EMPTY_ARRAY, null);
	}

	/**
	 * 输出debug级别信息
	 * 
	 * @param t
	 *            异常信息
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void debug(Throwable t, Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.DEBUG_INT, String.format(format, args),
				EMPTY_ARRAY, t);
	}

	/**
	 * 输出info级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 */
	public static void info(Object msg) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN, LocationAwareLogger.INFO_INT,
				format, EMPTY_ARRAY, null);
	}

	/**
	 * 输出info级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void info(Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN, LocationAwareLogger.INFO_INT,
				String.format(format, args), EMPTY_ARRAY, null);
	}

	/**
	 * 输出info级别信息
	 * 
	 * @param t
	 *            异常信息
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void info(Throwable t, Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN, LocationAwareLogger.INFO_INT,
				String.format(format, args), EMPTY_ARRAY, t);
	}

	/**
	 * 输出warn级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 */
	public static void warn(Object msg) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN, LocationAwareLogger.WARN_INT,
				format, EMPTY_ARRAY, null);
	}

	/**
	 * 输出warn级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void warn(Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN, LocationAwareLogger.WARN_INT,
				String.format(format, args), EMPTY_ARRAY, null);
	}

	/**
	 * 输出warn级别信息
	 * 
	 * @param t
	 *            异常信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void warn(Throwable t, Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN, LocationAwareLogger.WARN_INT,
				String.format(format, args), EMPTY_ARRAY, t);
	}

	/**
	 * 输出error级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 */
	public static void error(Object msg) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.ERROR_INT, format, EMPTY_ARRAY, null);
	}

	/**
	 * 输出error级别信息
	 * 
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void error(Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.ERROR_INT, String.format(format, args),
				EMPTY_ARRAY, null);
	}

	/**
	 * 输出error级别信息
	 * 
	 * @param t
	 *            异常信息
	 * @param msg
	 *            消息本体，可以是任意对象
	 * @param args
	 *            格式化参数，使用String.format来进行格式化
	 */
	public static void error(Throwable t, Object msg, Object... args) {
		String format = msg == null ? "null" : msg.toString();
		getLocationAwareLogger(3).log(null, FQCN,
				LocationAwareLogger.ERROR_INT, String.format(format, args),
				EMPTY_ARRAY, t);
	}
}
