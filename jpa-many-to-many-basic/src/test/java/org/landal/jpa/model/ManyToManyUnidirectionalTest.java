package org.landal.jpa.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
import org.landal.jpa.model.BaseEntity;
import org.landal.jpa.model.Employee;
import org.landal.jpa.model.Project;

@RunWith(Arquillian.class)
public class ManyToManyUnidirectionalTest {

	private static final Logger LOGGER = Logger.getLogger(ManyToManyUnidirectionalTest.class
			.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackage(BaseEntity.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml").addAsManifestResource("test-ds.xml");
	}

	@PersistenceContext(unitName = "test")
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Before
	public void setUp() throws Exception {

		utx.begin();
		em.joinTransaction();

		utx.commit();

	}

	@Test
	public void test_many_to_many_bidirectional() throws Exception {

		utx.begin();
		em.joinTransaction();
		
		Employee employee = Employee.newInstance("George", "Katlin");
		Project project = new Project("best");
		employee.addProject(project);

		em.persist(employee);
		em.persist(project);
		em.flush();

		utx.commit();
		utx.begin();
		em.joinTransaction();

		employee = em.find(Employee.class, employee.getId());
		assertNotNull(employee);
		assertNotNull(employee.getProjects());
		assertFalse(employee.getProjects().isEmpty());

		project = employee.getProjects().get(0);
		assertEquals("best", project.getName());
		
		utx.rollback();

	}

}
