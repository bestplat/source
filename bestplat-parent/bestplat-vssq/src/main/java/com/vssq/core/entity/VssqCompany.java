/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vssq.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author lujijiang
 */
@Entity
@Table(name = "vssq_company")
@XmlRootElement
public class VssqCompany extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7835244919760124530L;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1024)
	@Column(name = "name_")
	private String name;
	@Size(max = 1024)
	@Column(name = "other_name_")
	private String otherName;
	@Size(max = 1024)
	@Column(name = "registered_address_")
	private String registeredAddress;
	@Column(name = "established_date_")
	@Temporal(TemporalType.DATE)
	private Date establishedDate;
	@Size(max = 256)
	@Column(name = "registered_capital_")
	private String registeredCapital;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1024)
	@Column(name = "legal_person_")
	private String legalPerson;
	@Size(max = 1024)
	@Column(name = "address_")
	private String address;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "linkman_")
	private String linkman;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "linkman_tel_")
	private String linkmanTel;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 256)
	@Column(name = "linkman_email_")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "电子邮件无效")
	private String linkmanEmail;
	@Size(max = 1024)
	@Column(name = "linkman_address_")
	private String linkmanAddress;
	@Lob
	@Column(name = "remark_")
	private String remark;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private List<VssqUser> vssqUserList;

	public VssqCompany() {
	}

	public VssqCompany(String id) {
		this.id = id;
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

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public Date getEstablishedDate() {
		return establishedDate;
	}

	public void setEstablishedDate(Date establishedDate) {
		this.establishedDate = establishedDate;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanTel() {
		return linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public String getLinkmanEmail() {
		return linkmanEmail;
	}

	public void setLinkmanEmail(String linkmanEmail) {
		this.linkmanEmail = linkmanEmail;
	}

	public String getLinkmanAddress() {
		return linkmanAddress;
	}

	public void setLinkmanAddress(String linkmanAddress) {
		this.linkmanAddress = linkmanAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@XmlTransient
	public List<VssqUser> getVssqUserList() {
		return vssqUserList;
	}

	public void setVssqUserList(List<VssqUser> vssqUserList) {
		this.vssqUserList = vssqUserList;
	}

}
