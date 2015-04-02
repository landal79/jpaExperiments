package org.landal.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
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
public class ManyToManyWithAdditionalAttributesAssociationTest {

	private static final Logger LOGGER = Logger.getLogger(ManyToManyWithAdditionalAttributesAssociationTest.class
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

		Stock stock = Stock.newInstance("7052", "PADINI");
		Category category = new Category("CONSUMER", "CONSUMER COMPANY");
		StockCategory stockCategory = new StockCategory();
		Date createdDate = new Date();
		stockCategory.setCreatedDate(createdDate);
		String createdBy = "system";
		stockCategory.setCreatedBy(createdBy);

		stock.addStockCategory(stockCategory);
		category.addStockCategory(stockCategory);

		em.persist(category);
		em.persist(stock);
		em.flush();

		utx.commit();
		utx.begin();
		em.joinTransaction();

		stock = em.find(Stock.class, stock.getId());
		assertNotNull(stock);
		assertNotNull(stock.getStockCategories());
		assertFalse(stock.getStockCategories().isEmpty());

		stockCategory = stock.getStockCategories().iterator().next();
		assertEquals(createdDate, stockCategory.getCreatedDate());
		assertEquals(createdBy, stockCategory.getCreatedBy());
		assertNotNull(stockCategory.getCategory());

		category = stockCategory.getCategory();

		assertEquals("CONSUMER", category.getName());
		assertEquals("CONSUMER COMPANY", category.getDesc());

	}

}
