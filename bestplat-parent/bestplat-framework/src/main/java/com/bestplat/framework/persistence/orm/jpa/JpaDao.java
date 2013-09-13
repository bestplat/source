package com.bestplat.framework.persistence.orm.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bestplat.framework.persistence.dao.Dao;
import com.bestplat.framework.persistence.dao.QL;
import com.bestplat.framework.persistence.dao.QL.QueryInfo;

/**
 * JPA的Dao实现
 * 
 * @author lujijiang
 * 
 */
public class JpaDao extends Dao {
	/**
	 * 用于类型转换
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> castListToType(List<?> list,
			Class<? extends T> type) {
		return (List<T>) list;
	}

	/**
	 * 用于类型转换
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T castObjectToType(Object obj, Class<? extends T> type) {
		return (T) obj;
	}

	/**
	 * 由Spring注入
	 */
	@PersistenceContext
	EntityManager entityManager;

	JpaDaoExtend jpaDaoExtend;

	public JpaDaoExtend getJpaDaoExtend() {
		return jpaDaoExtend;
	}

	public void setJpaDaoExtend(JpaDaoExtend jpaDaoExtend) {
		this.jpaDaoExtend = jpaDaoExtend;
	}

	public Long count(QL ql) {
		if (jpaDaoExtend == null) {
			throw new UnsupportedOperationException(
					"You must set a jpaDaoExtend for count operation");
		}
		return jpaDaoExtend.count(entityManager, ql);
	}

	private Query createQuery(QL ql) {
		QueryInfo queryInfo = ql.toQueryInfo();
		Query query = entityManager.createQuery(queryInfo.getQueryString());
		for (int i = 0; i < queryInfo.getArgs().size(); i++) {
			Object arg = queryInfo.getArgs().get(i);
			query.setParameter(i, arg);
		}
		return query;
	}

	public <T> List<T> find(Class<? extends T> type, QL ql, int start, int size) {
		Query query = createQuery(ql);
		query.setFirstResult(start).setMaxResults(size);
		return castListToType(query.getResultList(), type);
	}

	public List<?> find(QL ql, int start, int size) {
		Query query = createQuery(ql);
		query.setFirstResult(start).setMaxResults(size);
		return query.getResultList();
	}

	public <T> T unique(Class<? extends T> type, QL ql) {
		Query query = createQuery(ql);
		return castObjectToType(query.getSingleResult(), type);
	}

	public Object unique(QL ql) {
		Query query = createQuery(ql);
		return query.getSingleResult();
	}

	public <T> T get(Class<T> entityClass, Serializable primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	public void persist(Object entity) {
		entityManager.persist(entity);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> Class<T> getEntityType(Class<? extends T> type) {
		Class cls = type;
		while (cls != null) {
			try {
				entityManager.getMetamodel().entity(cls);
				return cls;
			} catch (IllegalArgumentException e) {

			}
			cls = cls.getSuperclass();
		}
		throw new IllegalArgumentException(String.format(
				"The type:%s is not a entity type", type));
	}

	public <T> T merge(T entity) {
		return entityManager.merge(entity);
	}

	public void replicate(Object entity) {
		if (jpaDaoExtend == null) {
			throw new UnsupportedOperationException(
					"You must set a jpaDaoExtend for count operation");
		}
		jpaDaoExtend.replicate(entityManager, entity);
	}

	public void remove(Object entity) {
		entityManager.remove(entity);
	}

	public void refresh(Object entity) {
		entityManager.refresh(entity);
	}

	public boolean contains(Object entity) {
		return entityManager.contains(entity);
	}

	public void detach(Object entity) {
		entityManager.detach(entity);
	}

	public Object getIdentifier(Object entity) {
		return entityManager.getEntityManagerFactory().getPersistenceUnitUtil()
				.getIdentifier(entity);
	}

	public void flush() {
		entityManager.flush();
	}

	public void clear() {
		entityManager.clear();
	}

	protected void checkDaoConfig() throws IllegalArgumentException {
		if (entityManager == null) {
			throw new UnsupportedOperationException();
		}
	}

}
