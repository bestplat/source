package com.bestplat.framework.testcase.persistence.dao;

import java.util.Date;

import org.junit.Test;

import com.bestplat.framework.persistence.dao.QL;

public class QLTest {
	@Test
	public void testWhere() {
		QL ql = QL.froms("PrpDuser user");
		ql.select("count(user.id) ct,user.name");
		ql.leftJoin("PrpDcompany com on com.id=user.company and user.name=?",
				"aaaaaa");
		ql.and("user.id=?", 1);
		ql.and("user.index=32323");
		ql.and("user.birthday>?", new Date());
		ql.order("user.id desc,user.name asc");
		ql.order("user.birthday desc");
		ql.group("user.name");
		ql.having("count(user.id)>?", 2);
		System.out.println(ql.toQueryInfo());
	}

}
