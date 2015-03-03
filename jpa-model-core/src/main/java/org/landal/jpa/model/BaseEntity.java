package org.landal.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity implements Identifiable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	/**
	 * Optimistic-locking
	 */
	@Version
	@Column(name = "VERSION")
	protected Integer version;

	// JPA default constructor
	protected BaseEntity() {
	}

	public BaseEntity(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getClass().getSimpleName()).append("@").append(hashCode()).append("[id = ")
				.append(getId()).append("]").toString();
	}

	// //////////////////////////// Getters/Setters

	@Override
	public Long getId() {
		return id;
	}

}
