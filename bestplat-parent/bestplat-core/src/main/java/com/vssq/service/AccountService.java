package com.vssq.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bestplat.framework.log.Logs;
import com.bestplat.framework.security.Digests;
import com.bestplat.framework.util.Encodes;
import com.vssq.dao.CompanyDao;
import com.vssq.dao.UserDao;
import com.vssq.entity.VssqUser;
import com.vssq.security.ShiroDbRealm.ShiroUser;

/**
 * 用户管理类.
 * 
 * @author lujijiang
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private CompanyDao companyDao;

	public VssqUser findVssqUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(VssqUser user) {
		return false;
	}

	/**
	 * 取出Shiro中的当前用户email
	 */
	private String getCurrentUserEmail() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.email;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(VssqUser user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),
				salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Transactional(readOnly = false)
	public void saveUser(VssqUser user) {

		if (isSupervisor(user)) {
			Logs.warn("操作员%s尝试修改超级管理员用户", getCurrentUserEmail());
			throw new ServiceException("不能修改超级管理员用户");
		}

		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}

		companyDao.save(user.getCompany());
		userDao.save(user);
	}

}
