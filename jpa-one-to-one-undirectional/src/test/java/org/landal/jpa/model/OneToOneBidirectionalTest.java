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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.landal.jpa.model.Book;

@RunWith(Arquillian.class)
public class OneToOneBidirectionalTest {

	private static final Logger log = Logger.getLogger(OneToOneBidirectionalTest.class
			.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackage(Book.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml",
						"persistence.xml").addAsManifestResource("test-ds.xml");
	}

	@PersistenceContext(unitName = "test")
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	public void insertSampleRecords() throws Exception {
		utx.begin();
		em.joinTransaction();

		printStatus("Clearing the database...");
		em.createQuery("delete from Book").executeUpdate();
		em.createQuery("delete from Publisher").executeUpdate();

		printStatus("Inserting records...");

		Publisher p = new Publisher();
		p.setName("Manning");
		p.setWebsite("www.manning.com");
		em.persist(p);

		Book b = new Book();
		b.setIsbn("000000000000000");
		b.setTitle("title");
		b.setDescription("description");
		b.setPublisher(p);

		em.persist(b);

		utx.commit();
	}

	@Test
	public void test_element_collection() throws Exception {

		insertSampleRecords();

		utx.begin();
		em.joinTransaction();

		printStatus("Selecting (using JPQL)...");
		List<Book> books = em.createQuery(
				"select b from Book b order by b.title", Book.class)
				.getResultList();

		Book book = books.get(0);

		assertThat(book.getPublisher().getName(), equalTo("Manning"));

		utx.commit();

	}

	private void printStatus(Object message) {
		if (log instanceof Logger) {
			((Logger) log).info(message.toString());
		}
	}

}
