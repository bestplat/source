package com.bestplat.framework.persistence.orm;

import java.util.List;

import javassist.ClassPool;
import javassist.CtMethod;

import com.bestplat.framework.Lang;
import com.bestplat.framework.persistence.Dao;

/**
 * OrmDao实现
 * 
 * @author lujijiang
 * 
 */
public class ORMDao extends Dao {

	public <T> List<T> query(Class<? extends T> type, Where<T> where) {
		try {
			ClassPool pool = ClassPool.getDefault();
			CtMethod m = pool.getMethod(where.getClass().getName(), "match");
			System.err.println(m.getMethodInfo().getCodeAttribute());
			return null;
		} catch (Exception e) {
			throw Lang.wrapCause(e);
		}
	}

	public static void main(String[] args) {

		class TestPojo {
			long id;
			String name;

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}
		Dao dao = new ORMDao();
		List<TestPojo> testPojos = dao.query(TestPojo.class,
				new Dao.Where<TestPojo>() {
					public boolean match(TestPojo target) {
						return target.getName().equals("test");
					}
				});
	}
}