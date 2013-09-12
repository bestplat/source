/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vssq.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Index;

/**
 * 
 * @author lujijiang
 */
@Entity
@Table(name = "vssq_user")
@XmlRootElement
public class VssqUser extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5863082554572650643L;
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "电子邮件无效")
	// if the field contains email address consider using
	// this annotation to enforce field validation
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 256)
	@Column(name = "email_")
	@Index(name = "vssq_user_email_idx")
	private String email;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 256)
	@Column(name = "password_")
	private String password;
	/**
	 * 原始密码，不保存
	 */
	@Transient
	private String plainPassword;
	@Basic(optional = false)
	@Size(min = 1, max = 64)
	@Column(name = "salt_")
	private String salt;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1024)
	@Column(name = "name_")
	private String name;
	@Size(max = 1024)
	@Column(name = "other_name_")
	private String otherName;
	@Basic(optional = false)
	@NotNull
	@Column(name = "gender_")
	private char gender;
	@Size(max = 16)
	@Column(name = "tel_")
	private String tel;
	@Size(max = 16)
	@Column(name = "im_")
	private String im;
	@Size(max = 1024)
	@Column(name = "address_")
	private String address;
	@Column(name = "birthday_")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Lob
	@Column(name = "remark_")
	private String remark;
	@JoinColumn(name = "company_", referencedColumnName = "id_")
	@ManyToOne(optional = false)
	private VssqCompany company;

	public VssqUser() {
	}

	public VssqUser(String id) {
		this.id = id;
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

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
