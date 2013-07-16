package com.bestplat.framework.persistence;

import java.util.List;

public abstract class Dao {
	public interface Where<T> {
		boolean match(T target);
	}

	public abstract <T> List<T> query(Class<? extends T> type, Where<T> where);
}
