package org.landal.jpa.model;

import static org.junit.Assert.*;

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

@RunWith(Arquillian.class)
public class JPATest {

	private static final Logger log = Logger
			.getLogger(JPATest.class.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackage(BaseEntity.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml",
						"persistence.xml");
	}

	@PersistenceContext(unitName = "test")
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	public void insertSampleRecords() throws Exception {
		utx.begin();
		em.joinTransaction();

		printStatus("Clearing the database...");
		em.createQuery("delete from Employee").executeUpdate();	
		em.createQuery("delete from Tag").executeUpdate();	

		printStatus("Inserting records...");

		Tag t = new Tag();
		t.setCode("TAG1");
		
		t = em.merge(t);
		
		Employee e = new Employee();
		e.setCode("EMP1");
		e.addTag(t);
		
		em.persist(e);
		
		utx.commit();
	}

	@Test
	public void testPolymorphic() throws Exception {

		insertSampleRecords();

		utx.begin();
		em.joinTransaction();

		printStatus("Selecting (using JPQL)...");
		List<Employee> cycles = em.createQuery(
				"select e from Employee e order by e.code",
				Employee.class).getResultList();	
		
		Employee employee = cycles.get(0);
		assertEquals(1, employee.getTags().size());

		utx.commit();

	}

	private void printStatus(Object message) {
		if (log instanceof Logger) {
			((Logger) log).info(message.toString());
		}
	}

}
