package com.bestplat.framework;

public class Lang {
	/**
	 * 包裹异常
	 * 
	 * @param cause
	 * @return
	 */
	public static RuntimeException wrapCause(Throwable e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e);
	}

	/**
	 * 包裹异常并带着自己的信息
	 * 
	 * @param cause
	 * @param format
	 * @param args
	 * @return
	 */
	public static RuntimeException wrapCause(Throwable e, String format,
			Object... args) {
		return new RuntimeException(String.format(format, args), e);
	}

	/**
	 * 生成新运行时异常
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static RuntimeException newCause(String format, Object... args) {
		return new RuntimeException(String.format(format, args));
	}

	/**
	 * 生成指定异常类型的运行时异常
	 * 
	 * @param causeType
	 *            必须是异常类型
	 * @param format
	 * @param args
	 * @return
	 */
	public static RuntimeException newCause(
			Class<? extends Throwable> causeType, String format, Object... args) {
		Throwable throwable;
		try {
			throwable = causeType.getConstructor(String.class).newInstance(
					String.format(format, args));
			return wrapCause(throwable);
		} catch (Exception e) {
			throw wrapCause(e);
		}
	}
}
