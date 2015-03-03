package org.landal.jpa.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.landal.jpa.model.Book;

@RunWith(Arquillian.class)
public class OneToOneBidirectionalWithCascadingTest {

	private static final Logger LOG = Logger.getLogger(OneToOneBidirectionalWithCascadingTest.class.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackage(Book.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml").addAsManifestResource("test-ds.xml");
	}

	@PersistenceContext(unitName = "test")
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Before
	public void insertSampleRecords() throws Exception {
		utx.begin();
		em.joinTransaction();

		printStatus("Clearing the database...");
		em.createNamedQuery(Book.DELETE_ALL).executeUpdate();
		em.createQuery("delete from Author").executeUpdate();

		printStatus("Inserting records...");

		em.persist(Book.newInstance("000000000000000", "title", "description", Author.newInstance("Steven", "Surname")));

		utx.commit();
	}

	@Test
	public void test_one_to_one_unidirectional() throws Exception {

		utx.begin();
		em.joinTransaction();

		printStatus("Selecting (using JPQL)...");
		List<Book> books = em.createNamedQuery(Book.FIND_ALL, Book.class).getResultList();
		assertThat(books, notNullValue());
		assertThat(books.size(), equalTo(1));
		Book book = books.get(0);
		assertThat(book.getAuthor().getName(), equalTo("Steven"));

		utx.commit();

	}

	@Test
	public void test_one_to_one_unidirectional_delete() throws Exception {

		utx.begin();
		em.joinTransaction();

		int executeUpdate = em.createNamedQuery(Book.DELETE_ALL).executeUpdate();
		assertThat(executeUpdate, equalTo(1));
		utx.commit();
		utx.begin();
		em.joinTransaction();

		List<Book> books = em.createNamedQuery(Book.FIND_ALL, Book.class).getResultList();
		assertThat(books, notNullValue());
		assertThat(books.size(), equalTo(0));

		List<Author> authors = em.createNamedQuery(Author.FIND_ALL, Author.class).getResultList();
		assertThat(authors, notNullValue());
		assertThat(authors.size(), equalTo(1));

		utx.commit();

	}

	private void printStatus(Object message) {
		LOG.info(message.toString());
	}

}
