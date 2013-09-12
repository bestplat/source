package com.bestplat.framework.testcase.persistence.dao;

import java.util.Date;

import org.junit.Test;

import com.bestplat.framework.persistence.dao.QL;

public class QLTest {
	@Test
	public void testWhere() {
		QL ql = QL.create();
		QL orQL = QL.create();
		orQL.and("where aaaa in (?) ", "dsdsd", 2);
		ql.and("name=?", "aaaaaaa").and("id=?", 3232323)
				.and("date=?", new Date()).or("xxx=aaaaaa").or(orQL)
				.and("dddd=?", 2);
		System.out.println(ql.toQueryInfo());
	}
}
