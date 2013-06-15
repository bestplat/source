package com.vssq.testcase.persistence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bestplat.framework.time.Watch;

public class ConcurrentJpaTest {
	static {
		System.setProperty("spring.profiles.active", "functional");
	}

	static ApplicationContext ac = new ClassPathXmlApplicationContext(
			"classpath*:spring/*.xml");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doConcurrentTest(200);
	}

	private static void doConcurrentTest(int nThreads) {
		ExecutorService es = Executors.newFixedThreadPool(nThreads);
		final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) ac
				.getBean("entityManagerFactory");
		for (int i = 0; i < 1000000; i++) {
			es.execute(new Runnable() {
				public void run() {
					Watch.start();
					EntityManager em = entityManagerFactory
							.createEntityManager();
					EntityTransaction et = em.getTransaction();
					et.begin();
					try {

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
