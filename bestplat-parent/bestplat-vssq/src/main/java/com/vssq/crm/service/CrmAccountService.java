package com.vssq.crm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.vssq.core.entity.VssqUser;
import com.vssq.crm.dao.CrmUserDao;
import com.vssq.crm.entity.CrmUser;

public class CrmAccountService {
	@Autowired
	CrmUserDao crmUserDao;

	public CrmUser findCrmUserByUser(VssqUser user) {

		return null;
	}
}
