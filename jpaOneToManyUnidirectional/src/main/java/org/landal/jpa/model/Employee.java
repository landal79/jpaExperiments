package org.landal.jpa.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Employee extends BaseEntity{

	@OneToMany//(mappedBy = "employee")
	@JoinTable(name="employee_tag", joinColumns={
	    @JoinColumn(name="ID_employee", referencedColumnName="ID")
	}, inverseJoinColumns={
	    @JoinColumn(name="ID_tag", referencedColumnName="ID")
	})
	private Collection<Tag> tags;
	
	public Employee() {
	
	}
	
	public void addTag(Tag t){
		if(tags == null){
			setTags(new ArrayList<Tag>());
		}
		
		getTags().add(t);
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}
	
	
	
}
