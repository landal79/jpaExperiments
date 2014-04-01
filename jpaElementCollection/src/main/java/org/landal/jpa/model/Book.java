package org.landal.jpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKS")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String isbn;
	private String title;
	private String description;

	@ElementCollection
	@CollectionTable(name = "AUTHORS", joinColumns = @JoinColumn(name = "BOOK_ID"))
	private List<Author> authors;

	public Book() {
	}

	public Book(Long id, String isbn, String title, String description,
			Author... authors) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.authors = Arrays.asList(authors);
	}

	public void addAuthor(Author author) {
		if (authors == null) {
			authors = new ArrayList<Author>();
		}

		authors.add(author);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Author> getAuthors() {
		return Collections.unmodifiableList(authors);
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}
