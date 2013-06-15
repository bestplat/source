package com.vssq.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vssq.consts.Status;

@MappedSuperclass
public abstract class IdEntity {
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 32, max = 32)
	@Column(name = "id_")
	protected String id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "DATA_VALID_STATUS_")
	protected char dataValidStatus;
	@Basic(optional = false)
	@NotNull
	@Column(name = "LAST_MODIFIED_TIME_")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedTime;
	@Basic(optional = false)
	@NotNull
	@Column(name = "FIRST_CREATED_TIME_")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date firstCreatedTime;
	@Basic(optional = false)
	@NotNull
	@Column(name = "VERSION_")
	@Version
	protected int version;

	public IdEntity() {
		this.id = UUID.randomUUID().toString().replace("-", "");
		this.dataValidStatus = Status.VALID.value();
	}

	final public String getId() {
		return id;
	}

	final public void setId(String id) {
		this.id = id;
	}

	final public char getDataValidStatus() {
		return dataValidStatus;
	}

	final public void setDataValidStatus(char dataValidStatus) {
		this.dataValidStatus = dataValidStatus;
	}

	final public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	final public Date getFirstCreatedTime() {
		return firstCreatedTime;
	}

	final public int getVersion() {
		return version;
	}

	final public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	final public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(this.getClass().isAssignableFrom(object.getClass()))) {
			return false;
		}
		IdEntity other = (IdEntity) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	final public String toString() {
		return this.getClass().getCanonicalName().concat("[ id=" + id + " ]");
	}

	@PrePersist
	public void prePersist() {
		this.firstCreatedTime = new Date();
		this.lastModifiedTime = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		this.lastModifiedTime = new Date();
	}
}
