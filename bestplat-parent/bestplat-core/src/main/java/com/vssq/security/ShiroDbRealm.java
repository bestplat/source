package com.vssq.security;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.bestplat.framework.util.Encodes;
import com.google.common.base.Objects;
import com.vssq.consts.Status;
import com.vssq.entity.VssqUser;
import com.vssq.service.AccountService;

public class ShiroDbRealm extends AuthorizingRealm {

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String email;
		public String name;

		public ShiroUser(String email, String name) {
			this.email = email;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		public String toString() {
			return email;
		}

		/**
		 * 重载hashCode,只计算email;
		 */
		public int hashCode() {
			return Objects.hashCode(email);
		}

		/**
		 * 重载equals,只计算email;
		 */

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShiroUser other = (ShiroUser) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			return true;
		}
	}

	AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		VssqUser user = accountService.findVssqUserByEmail(shiroUser.email);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// TODO add rules
		return info;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String email = upToken.getUsername();
		VssqUser user = accountService.findVssqUserByEmail(email);
		if (user != null) {
			if (Status.VALID.value() != user.getValidStatus()) {
				throw new DisabledAccountException();
			}
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getEmail(),
					user.getName()), user.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		}
		return null;
	}
}