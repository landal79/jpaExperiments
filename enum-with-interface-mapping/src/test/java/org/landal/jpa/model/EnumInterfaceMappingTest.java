package org.landal.jpa.model;

import static org.junit.Assert.assertEquals;

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

@RunWith(Arquillian.class)
public class EnumInterfaceMappingTest {

	private static final Logger log = Logger.getLogger(EnumInterfaceMappingTest.class
			.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addPackage(BaseEntity.class.getPackage())
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
//		em.joinTransaction();

		Product p = new Product();
		p.setCode("code");
		p.setDescription("description");
		p.setStatus(ProductStatus.Stable);
		
		em.persist(p);
		
		Project pr = new Project();
		pr.setCode("code");
		pr.setDescription("description");
		pr.setStatus(ProjectStatus.Design);
		
		em.persist(pr);
		
		utx.commit();
	}

	@Test
	public void test_enum_interface() throws Exception {

		utx.begin();
//		em.joinTransaction();

		List<Product> products = em.createQuery(
				"select p from Product p", Product.class)
				.getResultList();

		Product product = products.get(0);
		assertEquals(ProductStatus.Stable, product.getStatus());
		
		List<Project> projects = em.createQuery(
				"select p from Project p", Project.class)
				.getResultList();

		Project project = projects.get(0);
		assertEquals(ProjectStatus.Design, project.getStatus());

		utx.commit();

	}

}
