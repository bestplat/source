package com.vssq.crm.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vssq.crm.entity.CrmUser;

public interface CrmUserDao extends
		PagingAndSortingRepository<CrmUser, String>,
		JpaSpecificationExecutor<CrmUser> {
}
