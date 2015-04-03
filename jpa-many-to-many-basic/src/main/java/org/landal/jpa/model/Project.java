package org.landal.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.landal.jpa.model.BaseEntity;

@Entity
@Table(name = "PROJECTS")
public class Project extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	public Project() {
	}
	
	public Project(String name) {
		this.name = name;
	}

	// //////////////////////////////


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
