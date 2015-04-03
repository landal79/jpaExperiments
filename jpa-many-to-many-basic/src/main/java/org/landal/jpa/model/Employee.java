package org.landal.jpa.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.landal.jpa.model.BaseEntity;

@Entity
@Table(name = "EMPLOYEES")
public class Employee extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	public static Employee newInstance(String firstName, String lastName){
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		return employee;
	}

	private String firstName;
	private String lastName;

	@ManyToMany
	@JoinTable(name = "EMP_PROJ", joinColumns = { @JoinColumn(name = "EMP_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "PROJ_ID", referencedColumnName = "ID") })
	private List<Project> projects;

	public Employee() {
	}
	
	public void addProject(Project project){
		if(project == null){
			throw new NullPointerException();
		}
		
		if(this.projects == null){
			this.projects = new LinkedList<Project>();
		}
		
		this.projects.add(project);
	}

	// ///////////////////////////

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Project> getProjects() {
		return Collections.unmodifiableList(this.projects);
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}	
	
}
