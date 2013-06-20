package com.vssq.testcase.persistence;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import com.bestplat.framework.time.Watch;
import com.vssq.entity.VssqCompany;
import com.vssq.entity.VssqUser;

@ActiveProfiles("development")
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class JpaTest extends AbstractTransactionalJUnit4SpringContextTests {
	@PersistenceContext
	EntityManager em;

	@Test
	@Transactional(readOnly = false)
	@Rollback(false)
	public void testPersist() {
		Watch.start();
		String hql = "delete from VssqUser";
		em.createQuery(hql).executeUpdate();
		hql = "delete from VssqCompany";
		em.createQuery(hql).executeUpdate();
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
		user.setEmail(UUID.randomUUID().toString() + "@gmail.com");
		user.setPassword("123456");
		user.setGender('1');
		em.persist(user);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		user.setAddress("safasfsafddasf");
		em.merge(user);
		long c = Watch.count();
		System.err.println("耗时：" + c / 1000.0 + "秒");
	}

	// @Test
	public void testCount() {
		String hql = "select count(*) from VssqUser";
		System.err.println(em.createQuery(hql).getSingleResult());
	}
}
