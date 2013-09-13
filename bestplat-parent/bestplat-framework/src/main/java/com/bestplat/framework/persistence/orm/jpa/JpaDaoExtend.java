package com.bestplat.framework.persistence.orm.jpa;

import javax.persistence.EntityManager;

import com.bestplat.framework.persistence.dao.QL;

public interface JpaDaoExtend {

	Long count(EntityManager entityManager, QL ql);

	void replicate(EntityManager entityManager, Object entity);

}
