package org.landal.jpa.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class BaseBusinessEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 10)
	@Basic(optional = false)
	@Column(name = "CODE", length = 10, nullable = false)
	private String code;

	@Size(min = 1, max = 100)
	@Basic(optional = true)
	@Column(name = "DESCRIPTION", length = 100, nullable = true)
	private String description;

	public BaseBusinessEntity() {

	}

	@Override
	public String toString() {
		return new StringBuilder().append(getClass().getSimpleName()).append("@").append(hashCode()).append("[id = ")
				.append(getId()).append("; code = ").append(getCode()).append("]").toString();
	}

	// /////////////////////////////

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
