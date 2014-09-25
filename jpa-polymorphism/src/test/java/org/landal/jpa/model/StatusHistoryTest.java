package org.landal.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class StatusHistoryTest {

	private static final Logger log = Logger.getLogger(StatusHistoryTest.class.getName());

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackage(BaseEntity.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml");
	}

	@PersistenceContext(unitName = "test")
	private EntityManager em;

	@Test
	@UsingDataSet("status_history_datasets.yml")
	public void testStatusHistory() throws Exception {

		EntityWithHistory found = em.find(EntityWithHistory.class, 1L);

		assertNotNull(found);

		assertNotNull(found.getStateHistory());

		assertFalse(found.getStateHistory().isEmpty());
		assertEquals(2, found.getStateHistory().size());

		List<StatusHistory> stateHistory = found.getStateHistory();

		assertTrue(stateHistory.get(0).getEquipmentCycle().getClass().isAssignableFrom(FirstEquipmentCycle.class));
		assertTrue(stateHistory.get(0).getEquipment().getClass().isAssignableFrom(FirstEquipment.class));
		assertTrue(stateHistory.get(1).getEquipmentCycle().getClass().isAssignableFrom(SecondEquipmentCycle.class));
		assertTrue(stateHistory.get(1).getEquipment().getClass().isAssignableFrom(SecondEquipment.class));

	}

}
