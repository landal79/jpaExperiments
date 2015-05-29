package org.landal.jpa.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTS")
//@DiscriminatorValue("proj")
public class Project extends BaseEntity<ProjectStatus> {
	


}
