package org.landal.jpa.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

@RunWith(Arquillian.class)
public class OneToOneBidirectionalTest {

	private static final Logger LOG = Logger.getLogger(OneToOneBidirectionalTest.class.getName());

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
		em.createQuery("delete from Book").executeUpdate();
		em.createQuery("delete from Publisher").executeUpdate();

		printStatus("Inserting records...");

		Publisher p = Publisher.newInstance("Manning", "www.manning.com");
		em.persist(p);
		em.persist(Book.newInstance("000000000000000", "title", "description", p));

		utx.commit();
	}

	@Test
	public void test_one_to_one_unidirectional() throws Exception {

		utx.begin();
		em.joinTransaction();

		printStatus("Selecting (using JPQL)...");
		List<Book> books = em.createNamedQuery(Book.FIND_ALL, Book.class).getResultList();

		Book book = books.get(0);

		assertThat(book.getPublisher().getName(), equalTo("Manning"));

		utx.commit();

	}

	/**
	 * Test with criteria API.
	 * 
	 * select b from Book b fetch join b.publisher where p.name = 'Manning'
	 */
	@Test
	public void test_one_to_one_unidirectional__with_criteria() {

		final String publisherName = "Manning";

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> c = cb.createQuery(Book.class);
		// Define query root
		Root<Book> bookRoot = c.from(Book.class);
		Fetch<Book, Publisher> publisher = bookRoot.fetch(Book_.publisher);
		c.select(bookRoot);
					
		List<Predicate> criteria = new ArrayList<Predicate>();		
		criteria.add(cb.equal(bookRoot.get(Book_.publisher).get(Publisher_.name), publisherName));
		criteria.add(cb.equal(bookRoot.get(Book_.title), "title"));
		
		Predicate disjunction = cb.disjunction();
		disjunction.getExpressions().addAll(criteria);
		
		c.where(disjunction);
		
		TypedQuery<Book> query = em.createQuery(c);

		printStatus(query.unwrap(org.hibernate.Query.class).getQueryString());

		List<Book> books = query.getResultList();

		assertThat(books, notNullValue());

		for (Book book : books) {
			assertThat(book.getPublisher().getName(), equalTo(publisherName));
		}

	}

	private void printStatus(Object message) {
		LOG.info(message.toString());
	}

}
