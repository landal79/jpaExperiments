package org.landal.jpa.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.landal.jpa.model.BaseEntity;

@Entity
@Table(name = "PROJECTS")
public class Project extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	
	@ManyToMany(mappedBy="projects")
	private List<Employee> employees;

	public Project() {
	}
	
	public Project(String name) {
		this.name = name;
	}
	
	public void addEmployee(Employee employee){
		if(employee == null){
			throw new NullPointerException();
		}
		
		if(this.employees == null){
			this.employees = new LinkedList<Employee>();
		}
		
		this.employees.add(employee);
	}

	// //////////////////////////////


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	

}
