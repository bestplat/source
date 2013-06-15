/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vssq.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author lujijiang
 */
@Entity
@Table(name = "VSSQ_USER")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "VssqUser.findAll", query = "SELECT v FROM VssqUser v"),
		@NamedQuery(name = "VssqUser.findById", query = "SELECT v FROM VssqUser v WHERE v.id = :id"),
		@NamedQuery(name = "VssqUser.findByEmail", query = "SELECT v FROM VssqUser v WHERE v.email = :email"),
		@NamedQuery(name = "VssqUser.findByPassword", query = "SELECT v FROM VssqUser v WHERE v.password = :password"),
		@NamedQuery(name = "VssqUser.findByName", query = "SELECT v FROM VssqUser v WHERE v.name = :name"),
		@NamedQuery(name = "VssqUser.findByOtherName", query = "SELECT v FROM VssqUser v WHERE v.otherName = :otherName"),
		@NamedQuery(name = "VssqUser.findByGender", query = "SELECT v FROM VssqUser v WHERE v.gender = :gender"),
		@NamedQuery(name = "VssqUser.findByTel", query = "SELECT v FROM VssqUser v WHERE v.tel = :tel"),
		@NamedQuery(name = "VssqUser.findByIm", query = "SELECT v FROM VssqUser v WHERE v.im = :im"),
		@NamedQuery(name = "VssqUser.findByAddress", query = "SELECT v FROM VssqUser v WHERE v.address = :address"),
		@NamedQuery(name = "VssqUser.findByBirthday", query = "SELECT v FROM VssqUser v WHERE v.birthday = :birthday"),
		@NamedQuery(name = "VssqUser.findByDataValidStatus", query = "SELECT v FROM VssqUser v WHERE v.dataValidStatus = :dataValidStatus"),
		@NamedQuery(name = "VssqUser.findByLastModifiedTime", query = "SELECT v FROM VssqUser v WHERE v.lastModifiedTime = :lastModifiedTime"),
		@NamedQuery(name = "VssqUser.findByFirstCreatedTime", query = "SELECT v FROM VssqUser v WHERE v.firstCreatedTime = :firstCreatedTime") })
public class VssqUser extends IdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "电子邮件无效")
	// if the field contains email address consider using
	// this annotation to enforce field validation
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 256)
	@Column(name = "EMAIL_")
	private String email;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 256)
	@Column(name = "PASSWORD_")
	private String password;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1024)
	@Column(name = "NAME_")
	private String name;
	@Size(max = 1024)
	@Column(name = "OTHER_NAME_")
	private String otherName;
	@Basic(optional = false)
	@NotNull
	@Column(name = "GENDER_")
	private char gender;
	@Size(max = 16)
	@Column(name = "TEL_")
	private String tel;
	@Size(max = 16)
	@Column(name = "IM_")
	private String im;
	@Size(max = 1024)
	@Column(name = "ADDRESS_")
	private String address;
	@Column(name = "BIRTHDAY_")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Lob
	@Column(name = "REMARK_")
	private String remark;
	@JoinColumn(name = "COMPANY_", referencedColumnName = "ID_")
	@ManyToOne(optional = false)
	private VssqCompany company;

	public VssqUser() {
	}

	public VssqUser(String id) {
		this.id = id;
	}

	public VssqUser(String id, String email, String password, String name,
			char gender, char dataValidStatus, Date lastModifiedTime,
			Date firstCreatedTime) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dataValidStatus = dataValidStatus;
		this.lastModifiedTime = lastModifiedTime;
		this.firstCreatedTime = firstCreatedTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public VssqCompany getCompany() {
		return company;
	}

	public void setCompany(VssqCompany company) {
		this.company = company;
	}

}