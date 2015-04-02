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
import org.landal.jpa.model.Person;

@RunWith(Arquillian.class)
public class OneToOneUnidirectionalTest {

	private static final Logger log = Logger.getLogger(OneToOneUnidirectionalTest.class
			.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackage(Person.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml",
						"persistence.xml").addAsManifestResource("test-ds.xml");
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
		em.createQuery("delete from Person").executeUpdate();
		em.createQuery("delete from Address").executeUpdate();

		printStatus("Inserting records...");

		Address addr = new Address();
		addr.setStreet("the street");
		addr.setCity("NYC");
		em.persist(addr);

		Person person = new Person();
		person.setName("David");
		person.setSurname("Latern");
		addr.setOwner(person);
		person.setAddress(addr);

		em.persist(person);

		utx.commit();
	}

	@Test
	public void test_read_one_to_one_unidirectional() throws Exception {

		insertSampleRecords();

		utx.begin();
		em.joinTransaction();

		printStatus("Selecting (using JPQL)...");
		List<Person> persons = em.createQuery(
				"select p from Person p join fetch p.address order by p.name", Person.class)
				.getResultList();

		Person person = persons.get(0);

		assertThat(person.getAddress().getStreet(), equalTo("the street"));

		utx.commit();

	}

	private void printStatus(Object message) {
		if (log instanceof Logger) {
			((Logger) log).info(message.toString());
		}
	}

}
