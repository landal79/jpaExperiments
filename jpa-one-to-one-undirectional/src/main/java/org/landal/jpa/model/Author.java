package org.landal.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@NamedQueries({ @NamedQuery(name = Author.FIND_ALL, query = "select a from Author a"),
		@NamedQuery(name = Author.DELETE_ALL, query = "delete from Author") })
@Entity
@Table(name = "AUTHORS")
public class Author implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Author.FIND_ALL";
	public static final String DELETE_ALL = "Author.DELETE_ALL";

	public static final Author newInstance(String name, String surname) {

		if (name == null || surname == null) {
			throw new IllegalArgumentException();
		}

		Author a = new Author();
		a.setName(name);
		a.setSurname(surname);
		return a;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String surname;

	public Author() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	// /////////////////////////// Getters/Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}
