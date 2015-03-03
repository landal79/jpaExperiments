package org.landal.jpa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@NamedQueries({ @NamedQuery(name = Book.FIND_ALL, query = "select b from Book b order by b.title"),
		@NamedQuery(name = Book.DELETE_ALL, query = "delete from Book") })
@Entity
@Table(name = "BOOKS")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Book.FIND_ALL";
	public static final String DELETE_ALL = "Book.DELETE_ALL";

	public static final Book newInstance(String isbn, String title, String description, Publisher publisher) {
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setDescription(description);
		book.setPublisher(publisher);
		return book;
	}

	public static final Book newInstance(String isbn, String title, String description, Author author) {
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setDescription(description);
		book.setAuthor(author);
		return book;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String isbn;
	private String title;
	private String description;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PUBLISHER_ID")
	private Publisher publisher;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "AUTHOR_ID")
	private Author author;

	public Book() {
	}

	public Book(Long id, String isbn, String title, String description, Publisher publisher) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.publisher = publisher;
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

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
