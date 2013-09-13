package com.bestplat.framework.persistence.dao;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.bestplat.framework.Lang;

/**
 * 查询语言包装器
 * 
 * @author lujijiang
 * 
 */
public class QL {
	/**
	 * 空数组
	 */
	private static final Object[] EMPTY_ARGS = new Object[] {};
	/**
	 * 问号占位符正则表达式
	 */
	public final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("[\\?]");
	/**
	 * 关键字正则表达式
	 */
	private final static Pattern KEYWORDS_PATTERN = Pattern
			.compile(
					"(select)|(where)|(and)|(or)|(group)|(by)|(order)|(having)|(with)|(join)|(on)|(left)|(right)|(full)|(inner)",
					Pattern.CASE_INSENSITIVE);

	enum QLLogicType {
		AND, OR
	}

	class QLMeta {
		/**
		 * 子片段
		 */
		QL subQL;
		/**
		 * 语言片段
		 */
		String ql;
		/**
		 * 参数列表
		 */
		List<Object> args;
		/**
		 * 逻辑类型
		 */
		QLLogicType logicType;
	}

	public class QueryInfo {
		String queryString;
		List<Object> args;

		public String getQueryString() {
			return queryString;
		}

		public void setQueryString(String queryString) {
			this.queryString = queryString;
		}

		public List<Object> getArgs() {
			return args;
		}

		public void setArgs(List<Object> args) {
			this.args = args;
		}

		public String toString() {
			DateFormat dataFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss SSS");
			StringBuffer sb = new StringBuffer();
			Matcher m = PLACEHOLDER_PATTERN.matcher(queryString);
			int i = 0;
			while (m.find()) {
				Object arg = args.get(i);
				String argString = null;
				if (arg == null) {
					argString = "null";
				} else if (arg instanceof Date) {
					argString = "'" + dataFormat.format((Date) arg) + "'";
				} else if (arg instanceof Number) {
					argString = ((Number) arg).toString();
				} else {
					argString = "'" + arg.toString().replace("'", "''") + "'";
				}
				m.appendReplacement(sb, argString);
				i++;
			}
			m.appendTail(sb);
			return "QL:".concat(sb.toString());
		}
	}

	/**
	 * 私有构造器
	 */
	private QL() {
	}

	/**
	 * 创建一个QL对象
	 * 
	 * @return
	 */
	public static QL create() {
		return new QL();
	}

	/**
	 * 创建一个QL对象
	 * 
	 * @return
	 */
	public static QL froms(String... tables) {
		QL ql = create();
		for (String table : tables) {
			ql.from(table);
		}
		return ql;
	}

	/**
	 * select子句的内容
	 */
	private List<QLMeta> selectQLMetas = new ArrayList<QLMeta>();
	/**
	 * from子句的内容
	 */
	private List<QLMeta> fromQLMetas = new ArrayList<QLMeta>();
	/**
	 * join子句的内容
	 */
	private List<QLMeta> joinQLMetas = new ArrayList<QLMeta>();
	/**
	 * left join子句的内容
	 */
	private List<QLMeta> leftJoinQLMetas = new ArrayList<QLMeta>();
	/**
	 * right join子句的内容
	 */
	private List<QLMeta> rightJoinQLMetas = new ArrayList<QLMeta>();
	/**
	 * Where子句的内容
	 */
	private List<QLMeta> whereQLMetas = new ArrayList<QLMeta>();
	/**
	 * order子句的内容
	 */
	private List<QLMeta> orderQLMetas = new ArrayList<QLMeta>();
	/**
	 * group子句的内容
	 */
	private List<QLMeta> groupQLMetas = new ArrayList<QLMeta>();
	/**
	 * having子句的内容
	 */
	private List<QLMeta> havingQLMetas = new ArrayList<QLMeta>();

	/**
	 * 检查查询语句语法以及参数数量是否正确
	 * 
	 * @param ql
	 * @param args
	 */
	private void checkQLAndArgs(String ql, Object[] args) {
		int count = StringUtils.countMatches(ql, "?");
		if (count != args.length) {
			throw new IllegalArgumentException(
					String.format(
							"The QL string{%s} requires %d arguments,but got %d arguments",
							ql, count, args.length));
		}
		if (ql.contains("'")) {
			throw new IllegalArgumentException(
					String.format(
							"The QL string{%s} should not be contains the single quotation marks",
							ql));
		}
	}

	/**
	 * 修复查询语句
	 * 
	 * @param ql
	 * @return
	 */
	private String fixQL(String ql) {
		StringBuffer sb = new StringBuffer();
		Matcher m = KEYWORDS_PATTERN.matcher(ql.trim());
		while (m.find()) {
			if (m.start() == 0) {
				m.appendReplacement(sb, "");
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 修补参数
	 * 
	 * @param ql
	 * @param args
	 * @return
	 */
	private Object[] fixArgs(String ql, Object... args) {
		if (StringUtils.countMatches(ql, "?") == 1 && args.length > 1) {
			Object arg = args[0];
			if (arg != null && arg.getClass().isArray()) {
				return args;
			}
			args = new Object[] { args };
		}
		return args;
	}

	/**
	 * where里面的AND子句
	 * 
	 * @param ql
	 * @param args
	 * @return
	 */
	public QL and(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		qlMeta.logicType = QLLogicType.AND;
		whereQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * where里面的AND子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL and(String ql) {
		return and(ql, EMPTY_ARGS);
	}

	/**
	 * where里面的AND子句
	 * 
	 * @param subQL
	 * @return
	 */
	public QL and(QL subQL) {
		QLMeta qlMeta = new QLMeta();
		qlMeta.subQL = subQL;
		qlMeta.logicType = QLLogicType.AND;
		whereQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * where里面的OR子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL or(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		qlMeta.logicType = QLLogicType.OR;
		whereQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * where里面的OR子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL or(String ql) {
		return or(ql, EMPTY_ARGS);
	}

	/**
	 * where里面的OR子句
	 * 
	 * @param subQL
	 * @return
	 */
	public QL or(QL subQL) {
		QLMeta qlMeta = new QLMeta();
		qlMeta.subQL = subQL;
		qlMeta.logicType = QLLogicType.OR;
		whereQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * 查询子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL select(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		selectQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * 查询子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL select(String ql) {
		return select(ql, EMPTY_ARGS);
	}

	/**
	 * from子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL from(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		fromQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * from子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL from(String ql) {
		return from(ql, EMPTY_ARGS);
	}

	/**
	 * order子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL order(String ql) {
		ql = fixQL(ql);
		checkQLAndArgs(ql, EMPTY_ARGS);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		orderQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * group子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL group(String ql) {
		ql = fixQL(ql);
		checkQLAndArgs(ql, EMPTY_ARGS);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		groupQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * having子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL having(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		havingQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * having子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL having(String ql) {
		return having(ql, EMPTY_ARGS);
	}

	/**
	 * join子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL join(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		joinQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * join子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL join(String ql) {
		return join(ql, EMPTY_ARGS);
	}

	/**
	 * leftJoin子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL leftJoin(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		leftJoinQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * leftJoin子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL leftJoin(String ql) {
		return leftJoin(ql, EMPTY_ARGS);
	}

	/**
	 * rightJoin子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL rightJoin(String ql, Object... args) {
		ql = fixQL(ql);
		args = fixArgs(ql, args);
		checkQLAndArgs(ql, args);
		QLMeta qlMeta = new QLMeta();
		qlMeta.ql = ql;
		qlMeta.args = Arrays.asList(args);
		rightJoinQLMetas.add(qlMeta);
		return this;
	}

	/**
	 * rightJoin子句
	 * 
	 * @param ql
	 * @return
	 */
	public QL rightJoin(String ql) {
		return rightJoin(ql, EMPTY_ARGS);
	}

	/**
	 * 返回总的查询信息
	 * 
	 * @return
	 */
	public QueryInfo toQueryInfo() {
		StringBuilder queryString = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		{
			QueryInfo selectQueryInfo = toSelectQueryInfo();
			if (!Lang.isEmpty(selectQueryInfo.queryString)) {
				queryString.append("select ");
				queryString.append(selectQueryInfo.queryString);
				args.addAll(selectQueryInfo.args);
			}
		}
		{
			QueryInfo fromQueryInfo = toFromQueryInfo();
			if (!Lang.isEmpty(fromQueryInfo.queryString)) {
				queryString.append(" from ");
				queryString.append(fromQueryInfo.queryString);
				args.addAll(fromQueryInfo.args);
			}
		}
		{
			QueryInfo joinQueryInfo = toJoinQueryInfo();
			if (!Lang.isEmpty(joinQueryInfo.queryString)) {
				queryString.append(" join ");
				queryString.append(joinQueryInfo.queryString);
				args.addAll(joinQueryInfo.args);
			}
		}
		{
			QueryInfo leftJoinQueryInfo = toLeftJoinQueryInfo();
			if (!Lang.isEmpty(leftJoinQueryInfo.queryString)) {
				queryString.append(" left join ");
				queryString.append(leftJoinQueryInfo.queryString);
				args.addAll(leftJoinQueryInfo.args);
			}
		}
		{
			QueryInfo rightJoinQueryInfo = toRightJoinQueryInfo();
			if (!Lang.isEmpty(rightJoinQueryInfo.queryString)) {
				queryString.append(" right join ");
				queryString.append(rightJoinQueryInfo.queryString);
				args.addAll(rightJoinQueryInfo.args);
			}
		}
		{
			QueryInfo whereQueryInfo = toWhereQueryInfo();
			if (!Lang.isEmpty(whereQueryInfo.queryString)) {
				queryString.append(" where ");
				queryString.append(whereQueryInfo.queryString);
				args.addAll(whereQueryInfo.args);
			}
		}
		{
			QueryInfo orderQueryInfo = toOrderQueryInfo();
			if (!Lang.isEmpty(orderQueryInfo.queryString)) {
				queryString.append(" order by ");
				queryString.append(orderQueryInfo.queryString);
				args.addAll(orderQueryInfo.args);
			}
		}
		{
			QueryInfo groupQueryInfo = toGroupQueryInfo();
			if (!Lang.isEmpty(groupQueryInfo.queryString)) {
				queryString.append(" group by ");
				queryString.append(groupQueryInfo.queryString);
				args.addAll(groupQueryInfo.args);
			}
		}
		{
			QueryInfo havingQueryInfo = toHavingQueryInfo();
			if (!Lang.isEmpty(havingQueryInfo.queryString)) {
				queryString.append(" having ");
				queryString.append(havingQueryInfo.queryString);
				args.addAll(havingQueryInfo.args);
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	@SuppressWarnings("unchecked")
	private void writeQLMeta(List<Object> args, StringBuilder queryString,
			QLMeta qlMeta) {
		String ql = qlMeta.ql;
		List<Object> newArgs = new ArrayList<Object>();
		StringBuffer newQL = new StringBuffer();
		Matcher m = PLACEHOLDER_PATTERN.matcher(ql);
		int i = 0;
		while (m.find()) {
			Object arg = qlMeta.args.get(i);
			if (arg == null) {
				throw new IllegalArgumentException(
						String.format(
								"The arg should not be null when use for double question mark,the fragment is:%s",
								qlMeta));
			} else if (arg instanceof Collection) {
				Collection<Object> c = (Collection<Object>) arg;
				StringBuilder subQL = new StringBuilder();
				int j = 0;
				for (Object o : c) {
					if (j != 0) {
						subQL.append(',');
					}
					subQL.append('?');
					newArgs.add(o);
					j++;
				}
				m.appendReplacement(newQL, subQL.toString());
			} else if (arg.getClass().isArray()) {
				StringBuilder subQL = new StringBuilder();
				for (int j = 0; j < Array.getLength(arg); j++) {
					Object o = Array.get(arg, j);
					if (j != 0) {
						subQL.append(',');
					}
					subQL.append('?');
					newArgs.add(o);
				}
				m.appendReplacement(newQL, subQL.toString());
			} else if (arg instanceof QL) {
				QL subQL = (QL) arg;
				QueryInfo subSQLQueryInfo = subQL.toQueryInfo();
				m.appendReplacement(newQL, subSQLQueryInfo.queryString);
				newArgs.addAll(subSQLQueryInfo.args);
			} else {
				m.appendReplacement(newQL, "?");
				newArgs.add(arg);
			}
			i++;
		}
		m.appendTail(newQL);
		queryString.append(newQL.toString());
		args.addAll(newArgs);
	}

	private QueryInfo toSelectQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!selectQLMetas.isEmpty()) {
			for (QLMeta qlMeta : selectQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toFromQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!fromQLMetas.isEmpty()) {
			for (QLMeta qlMeta : fromQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toJoinQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!joinQLMetas.isEmpty()) {
			for (QLMeta qlMeta : joinQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toLeftJoinQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!leftJoinQLMetas.isEmpty()) {
			for (QLMeta qlMeta : leftJoinQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toRightJoinQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!rightJoinQLMetas.isEmpty()) {
			for (QLMeta qlMeta : rightJoinQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toWhereQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!whereQLMetas.isEmpty()) {
			for (QLMeta qlMeta : whereQLMetas) {
				switch (qlMeta.logicType) {
				case AND: {
					if (qlMeta.ql != null) {
						if (queryString.length() != 0) {
							queryString.append(" and ");
						}
						writeQLMeta(args, queryString, qlMeta);
					} else if (qlMeta.subQL != null) {
						if (queryString.length() != 0) {
							queryString.append(" and ( ");
						}
						QueryInfo queryInfo = qlMeta.subQL.toWhereQueryInfo();
						queryString.append(queryInfo.queryString);
						queryString.append(")");
						args.addAll(queryInfo.args);
					}
					break;
				}
				case OR: {
					if (qlMeta.ql != null) {
						if (queryString.length() != 0) {
							queryString.append(" or ");
						}
						writeQLMeta(args, queryString, qlMeta);
					} else if (qlMeta.subQL != null) {
						if (queryString.length() != 0) {
							queryString.append(" or ( ");
						}
						QueryInfo queryInfo = qlMeta.subQL.toWhereQueryInfo();
						queryString.append(queryInfo.queryString);
						queryString.append(")");
						args.addAll(queryInfo.args);
					}
					break;
				}
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toOrderQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		if (!orderQLMetas.isEmpty()) {
			for (QLMeta qlMeta : orderQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	private QueryInfo toGroupQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		QueryInfo queryInfo = new QueryInfo();
		if (!groupQLMetas.isEmpty()) {
			for (QLMeta qlMeta : groupQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

	/**
	 * @return
	 */
	private QueryInfo toHavingQueryInfo() {
		List<Object> args = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		QueryInfo queryInfo = new QueryInfo();
		if (!havingQLMetas.isEmpty()) {
			for (QLMeta qlMeta : havingQLMetas) {
				if (qlMeta.ql != null) {
					if (queryString.length() != 0) {
						queryString.append(",");
					}
					writeQLMeta(args, queryString, qlMeta);
				}
			}
		}
		queryInfo.queryString = queryString.toString();
		queryInfo.args = Collections.unmodifiableList(args);
		return queryInfo;
	}

}
