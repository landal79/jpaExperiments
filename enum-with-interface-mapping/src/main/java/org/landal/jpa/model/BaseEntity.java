package org.landal.jpa.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "TYPE")
@MappedSuperclass
public abstract class BaseEntity<S extends Status> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Version
	@Column(name = "VERSION")
	protected Integer version;

	@NotNull
	@Size(min = 2, max = 10)
	@Basic(optional = false)
	@Column(name = "CODE", length = 10, nullable = false)
	private String code;
	
	@Size(min = 1, max = 100)
	@Basic(optional = false)
	@Column(name = "DESCRIPTION", length = 100, nullable = false)
	private String description;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private S status;

	public BaseEntity() {

	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("@").append(hashCode())
				.append("[id = ").append(getId()).append("; code = ")
				.append(getCode()).append("]");

		return sb.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public S getStatus() {
		return status;
	}

	public void setStatus(S status) {
		this.status = status;
	}

	
	
}
