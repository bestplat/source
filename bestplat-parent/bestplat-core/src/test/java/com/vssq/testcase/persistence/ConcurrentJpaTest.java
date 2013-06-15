package com.vssq.testcase.persistence;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bestplat.framework.time.Watch;
import com.vssq.entity.VssqCompany;
import com.vssq.entity.VssqUser;
import com.vssq.service.AccountService;

public class ConcurrentJpaTest {
	static {
		System.setProperty("spring.profiles.active", "development");
	}

	static ApplicationContext ac = new ClassPathXmlApplicationContext(
			"classpath*:spring/*.xml");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doConcurrentTest(100);
	}

	private static void doConcurrentTest(int nThreads) {
		ExecutorService es = Executors.newFixedThreadPool(nThreads);
		final AccountService accountService = ac.getBean(AccountService.class);
		final AtomicLong t = new AtomicLong();
		final AtomicInteger k = new AtomicInteger();
		for (int i = 0; i < 10000; i++) {
			es.execute(new Runnable() {
				public void run() {
					Watch.start();
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
					System.err.println(t.addAndGet(Watch.reset())
							/ k.incrementAndGet());
				}
			});
		}
		es.shutdown();
	}

}
