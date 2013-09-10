package com.bestplat.framework.util;

import java.util.Iterator;

public class Strings {
	/**
	 * 分割字符串并返回迭代器，其中分割后空白字符串将被忽略
	 * 
	 * @param str
	 *            字符串
	 * @param delimiters
	 *            分隔字符串
	 * @return
	 */
	public static Iterator<String> splitIgnoreBlank(final String str,
			final String delimiters) {
		if (null == str)
			return null;
		return new Iterator<String>() {

			int fromIndex = 0;

			String subString;

			public boolean hasNext() {
				if (subString == null || subString.length() == 0) {
					if (fromIndex + delimiters.length() > str.length()) {
						return false;
					}
					int index = str.indexOf(delimiters, fromIndex);
					if (index == -1) {
						index = str.length();
					}
					subString = str.substring(fromIndex, index).trim();
					fromIndex = index + delimiters.length();
					return hasNext();
				}
				return true;
			}

			public String next() {
				if (subString == null) {
					throw new IllegalStateException(
							"Should be call hasNext first");
				}
				String s = subString;
				subString = null;
				return s;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
