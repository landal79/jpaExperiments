package org.landal.jpa.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BidirectionalAssociationPersistenceExtensionTest {

	private static final Logger log = Logger
			.getLogger(BidirectionalAssociationPersistenceExtensionTest.class.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackage(BaseBusinessEntity.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml");
	}

	@PersistenceContext(unitName = "test")
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	@UsingDataSet({ "datasets/serialkits.yml", "datasets/serialkitstatus.yml" })
	public void test_one_to_many_bidirectional_delete() throws Exception {

		// delete all previously inserted history items
		List<SerialKit> skList = em.createNamedQuery(SerialKit.FIND_ALL, SerialKit.class).getResultList();
		assertNotNull(skList);
		assertFalse(skList.isEmpty());

		SerialKit serialKit = skList.get(0);
		serialKit.deleteHistory();

		utx.commit();
		utx.begin();
		em.joinTransaction();

		skList = em.createNamedQuery(SerialKit.FIND_ALL, SerialKit.class).getResultList();
		assertTrue(skList.get(0).isHistoryEmpty());

	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	@UsingDataSet({ "datasets/serialkits.yml", "datasets/serialkitstatus.yml" })
	public void test_one_to_many_bidirectional_with_fetch() throws Exception {

		List<SerialKit> skList = em.createNamedQuery(SerialKit.FIND_ALL_WITH_HISTORY, SerialKit.class).getResultList();

		 utx.commit();

		assertNotNull(skList);
		assertFalse(skList.isEmpty());
		assertFalse(skList.get(0).isHistoryEmpty());

		//Needed because arquillian persistence rolls back the transaction
		utx.begin();

	}

}
