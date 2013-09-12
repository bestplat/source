package com.vssq.crm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;
import com.vssq.core.entity.IdEntity;

/**
 * CRM用户
 * 
 * @author lujijiang
 * 
 */
@Entity
@Table(name = "CRM_ROLE")
@XmlRootElement
public class CrmRole extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2107172535497311213L;

	/**
	 * 角色名称
	 */
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1024)
	@Column(name = "name_")
	private String name;
	/**
	 * 权限列表
	 */
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2048)
	@Column(name = "permissions_")
	private String permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Transient
	public List<String> getPermissionList() {
		return ImmutableList.copyOf(StringUtils.split(permissions, ","));
	}

}
