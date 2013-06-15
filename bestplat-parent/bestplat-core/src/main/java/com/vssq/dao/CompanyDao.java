package com.vssq.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vssq.entity.VssqCompany;

public interface CompanyDao extends
		PagingAndSortingRepository<VssqCompany, String>,
		JpaSpecificationExecutor<VssqCompany> {
}
