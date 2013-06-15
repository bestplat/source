package com.vssq.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vssq.entity.VssqUser;

public interface UserDao extends PagingAndSortingRepository<VssqUser, String>,
		JpaSpecificationExecutor<VssqUser> {
	VssqUser findByEmail(String email);

	VssqUser findByName(String name);
}
