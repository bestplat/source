package com.bestplat.framework.persistence.orm.hibernate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;

import com.bestplat.framework.persistence.dao.QL;
import com.bestplat.framework.persistence.dao.QL.QueryInfo;
import com.bestplat.framework.persistence.orm.jpa.JpaDaoExtend;

/**
 * Jpa扩展的Hibernate实现
 * 
 * @author lujijiang
 * 
 */
public class JpaDaoExtendHibernateImpl implements JpaDaoExtend {

	public Long count(EntityManager entityManager, QL ql) {
		Session session = (Session) entityManager.getDelegate();
		QueryInfo queryInfo = ql.toQueryInfo();
		String hql = queryInfo.getQueryString();
		Object[] parameters = queryInfo.getArgs().toArray();
		Map<Integer, Object> hqlParametersMap = new HashMap<Integer, Object>();
		for (int i = 0; i < parameters.length; i++) {
			hqlParametersMap.put(i, parameters[i]);
		}
		Map<Integer, Object> sqlParametersMap = new HashMap<Integer, Object>();
		String sql = hqlToSql(session, hql, hqlParametersMap, sqlParametersMap);
		String countSQL = "select count(1) as row_count_ from (" + sql
				+ ") tmp_result_table_";
		session.flush();// 通过sql查询时需要首先刷新hibernate缓冲
		org.hibernate.Query query = session.createSQLQuery(countSQL);
		query.setCacheable(false);// SQL查询永远不走缓存
		for (Integer i : sqlParametersMap.keySet()) {
			query.setParameter(i, sqlParametersMap.get(i));
		}
		Number count = (Number) query.uniqueResult();
		return count.longValue();
	}

	/**
	 * 将hql翻译为sql语句
	 * 
	 * @param hql
	 * @return
	 */
	public String hqlToSql(Session session, String hql,
			Map<Integer, Object> oldParameters,
			Map<Integer, Object> newParameters) {
		hql = QL.toJpaPositionalParametersStyle(hql, 0);
		final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
		QueryTranslator queryTranslator = translatorFactory
				.createQueryTranslator(hql, hql, Collections.EMPTY_MAP,
						(SessionFactoryImplementor) session.getSessionFactory());
		queryTranslator.compile(Collections.EMPTY_MAP, false);
		String sql = queryTranslator.getSQLString();
		for (Integer oldKey : oldParameters.keySet()) {
			Object value = oldParameters.get(oldKey);
			int[] newKeys = queryTranslator.getParameterTranslations()
					.getNamedParameterSqlLocations(oldKey.toString());
			for (int newKey : newKeys) {
				newParameters.put(newKey, value);
			}
		}
		return sql;
	}

	public void replicate(EntityManager entityManager, Object entity) {
		Session session = (Session) entityManager.getDelegate();
		session.flush();
		session.replicate(entity, ReplicationMode.OVERWRITE);
	}

}
