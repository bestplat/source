package com.vssq.testcase.persistence;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bestplat.framework.time.Watch;
import com.vssq.entity.VssqCompany;
import com.vssq.entity.VssqUser;

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
		doConcurrentTest(10);
	}

	private static void doConcurrentTest(int nThreads) {
		ExecutorService es = Executors.newFixedThreadPool(nThreads);
		final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) ac
				.getBean("entityManagerFactory");
		for (int i = 0; i < 100; i++) {
			es.execute(new Runnable() {
				public void run() {
					Watch.start();
					EntityManager em = entityManagerFactory
							.createEntityManager();
					EntityTransaction et = em.getTransaction();
					et.begin();
					try {
						VssqCompany company = new VssqCompany();
						company.setName("testCompany");
						company.setLegalPerson("lujijiang");
						company.setLinkman("卢吉江");
						company.setLinkmanEmail("lujijiang@gmail.com");
						company.setLinkmanTel("13922437060");
						em.persist(company);
						VssqUser user = new VssqUser();
						user.setCompany(company);
						user.setName(UUID.randomUUID().toString());
						user.setEmail(UUID.randomUUID().toString()
								+ "@gmail.com");
						user.setPassword("123456");
						user.setGender('1');
						em.persist(user);
						et.commit();
					} catch (Exception e) {
						et.rollback();
						e.printStackTrace();
					} finally {
						em.close();
						System.err.println(Watch.reset());
					}
				}
			});
		}
	}

}
