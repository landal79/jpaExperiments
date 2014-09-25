package org.landal.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JPAOneToManyArquillianExtensionTest {

	private static final Logger log = Logger.getLogger(JPAOneToManyArquillianExtensionTest.class
			.getName());

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

	@Test
	@UsingDataSet("datasets/employees.yml")
	public void testExtension_withYML() throws Exception {

		printStatus("Selecting (using JPQL)...");
		List<Employee> employees = em.createQuery(
				"select e from Employee e order by e.code", Employee.class)
				.getResultList();

		assertFalse(employees.isEmpty());
		printStatus("size: " + employees.size());
		assertEquals(3, employees.size());

	}

	@Test
	@ApplyScriptBefore("insert.sql")
	public void testExtension_withSQL() throws Exception {

		List<Employee> employees = em.createQuery(
				"select e from Employee e order by e.code", Employee.class)
				.getResultList();

		assertFalse(employees.isEmpty());
		assertEquals(3, employees.size());

	}

	private void printStatus(Object message) {
		if (log instanceof Logger) {
			((Logger) log).info(message.toString());
		}
	}

}
