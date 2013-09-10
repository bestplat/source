package com.vssq.testcase.core.service;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.vssq.core.entity.VssqCompany;
import com.vssq.core.entity.VssqUser;
import com.vssq.core.service.AccountService;

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
		user.setName("test");
		user.setEmail("test@gmail.com");
		user.setPlainPassword("123456");
		user.setGender('1');
		accountService.saveUser(user);
		try {
			TimeUnit.HOURS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
