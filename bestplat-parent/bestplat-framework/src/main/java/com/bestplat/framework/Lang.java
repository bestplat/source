package com.bestplat.framework;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 全局工具类
 * 
 * @author lujijiang
 * 
 */
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
		try {
			Throwable throwable = causeType.getConstructor(String.class)
					.newInstance(String.format(format, args));
			return wrapCause(throwable);
		} catch (Exception e) {
			throw wrapCause(e);
		}
	}

	/**
	 * 判断一个对象是否为空，可判断数组、集合、Map和字符串，null对象也被认为是空
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof Collection) {
			return ((Collection) o).isEmpty();
		}
		if (o instanceof Map) {
			return ((Map) o).isEmpty();
		}
		if (o instanceof CharSequence) {
			return o.toString().trim().length() == 0;
		}
		if (o.getClass().isArray()) {
			return Array.getLength(o) == 0;
		}
		return false;
	}

	/**
	 * 从数组中求取哈希值
	 * 
	 * @param array
	 * @return
	 */
	private static int arrayHashCode(Object array) {
		if (array == null) {
			return 0;
		}
		Class<?> arrayClass = array.getClass();
		if (!arrayClass.isArray()) {
			return array.hashCode();
		}
		Class<?> componentType = arrayClass.getComponentType();
		if (componentType.isPrimitive()) {
			if (componentType.equals(long.class)) {
				return Arrays.hashCode((long[]) array);
			}
			if (componentType.equals(int.class)) {
				return Arrays.hashCode((int[]) array);
			}
			if (componentType.equals(short.class)) {
				return Arrays.hashCode((short[]) array);
			}
			if (componentType.equals(char.class)) {
				return Arrays.hashCode((char[]) array);
			}
			if (componentType.equals(byte.class)) {
				return Arrays.hashCode((byte[]) array);
			}
			if (componentType.equals(boolean.class)) {
				return Arrays.hashCode((boolean[]) array);
			}
			if (componentType.equals(float.class)) {
				return Arrays.hashCode((float[]) array);
			}
			if (componentType.equals(double.class)) {
				return Arrays.hashCode((double[]) array);
			}
		}
		Object[] a = (Object[]) array;

		int result = 1;

		for (int i = 0; i < a.length; i++) {
			Object element = a[i];
			result = 31 * result + arrayHashCode(element);
		}
		return result;
	}

	/**
	 * 计算hash值
	 * 
	 * @param a
	 * @return
	 */
	public static int hashCode(Object... a) {
		return arrayHashCode(a);
	}

	/**
	 * 输出异常
	 * 
	 * @param e
	 * @return
	 */
	public static String toString(Throwable e) {
		OutputStream os = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(os);
		e.printStackTrace(pw);
		pw.close();
		return os.toString();
	}

}
