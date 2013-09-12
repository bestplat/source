package com.vssq.crm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;
import com.vssq.core.entity.IdEntity;
import com.vssq.core.entity.VssqUser;

/**
 * CRM用户
 * 
 * @author lujijiang
 * 
 */
@Entity
@Table(name = "crm_user")
@XmlRootElement
public class CrmUser extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5971589039101563528L;
	@JoinColumn(name = "user_", referencedColumnName = "id_")
	@OneToOne(optional = false)
	private VssqUser user;
	// 多对多定义
	@ManyToMany
	@JoinTable(name = "crm_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<CrmRole> roleList = Lists.newArrayList(); // 有序的关联对象集合

	public VssqUser getUser() {
		return user;
	}

	public void setUser(VssqUser user) {
		this.user = user;
	}

	public List<CrmRole> getRoleList() {
		return roleList;
	}

}
