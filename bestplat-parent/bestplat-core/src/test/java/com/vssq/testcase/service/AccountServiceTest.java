package com.vssq.testcase.service;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.vssq.entity.VssqCompany;
import com.vssq.entity.VssqUser;
import com.vssq.service.AccountService;

@ActiveProfiles("development")
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class AccountServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	AccountService accountService;

	@Test
	public void testSaveUser() {
		VssqCompany company = new VssqCompany();
		company.setName("testCompany");
		company.setLegalPerson("lujijiang");
		company.setLinkman("卢吉江");
		company.setLinkmanEmail("lujijiang@gmail.com");
		company.setLinkmanTel("13922437060");

		VssqUser user = new VssqUser();
		user.setCompany(company);
		user.setName(UUID.randomUUID().toString());
		user.setEmail(UUID.randomUUID().toString() + "@gmail.com");
		user.setPlainPassword("123456");
		user.setGender('1');
		accountService.saveUser(user);
	}
}
