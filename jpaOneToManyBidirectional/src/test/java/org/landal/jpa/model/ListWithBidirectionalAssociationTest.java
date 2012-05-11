package org.landal.jpa.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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
public class ListWithBidirectionalAssociationTest {

	private static final Logger log = Logger.getLogger(ListWithBidirectionalAssociationTest.class.getName());

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
		em.createQuery("delete from SerialKit").executeUpdate();
		em.createQuery("delete from SerialKitStatus").executeUpdate();

		printStatus("Inserting records...");
		
		SerialKit sk = new SerialKit();
		sk.setCode("SK_CODE");
		sk.setBarcode("SK_BARCODE");
		sk.setStatus(Status.NEW);
		
		SerialKitStatus sks = new SerialKitStatus();
		sks.setStatus(Status.NEW);
		sks.setOperationDate(new Date());		
		sk.addStatus(sks);
		
		SerialKit mergedSK = em.merge(sk);
		em.flush();
		
//		for (SerialKitStatus status : mergedSK.getStatusHistory()) {
//			em.remove(status);
//		}
		
		mergedSK.getStatusHistory().clear();					

		utx.commit();
		
	}

	@Test
	public void testDeleteListWithBidirectionalAssociation() throws Exception {

		insertSampleRecords();

		utx.begin();
		em.joinTransaction();

		List<SerialKit> skList = em.createQuery("from SerialKit sk", SerialKit.class).getResultList();				

		assertNotNull(skList);
		assertFalse(skList.isEmpty());
		
		assertTrue(skList.get(0).getStatusHistory().isEmpty());
		
		utx.commit();
		
		

	}

	private void printStatus(Object message) {
		if (log instanceof Logger) {
			((Logger) log).info(message.toString());
		}
	}

}
