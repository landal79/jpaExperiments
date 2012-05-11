package org.landal.jpa.model;

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
public class EquipmentCyclePolymorphimsTest {

	private static final Logger log = Logger
			.getLogger(EquipmentCyclePolymorphimsTest.class.getName());

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
		em.createQuery("delete from EquipmentCycle").executeUpdate();
		em.createQuery("delete from Equipment").executeUpdate();

		printStatus("Inserting records...");

		FirstEquipment fe = new FirstEquipment();
		fe.setCode("FIRST");
		fe.setDescription("FIRST");

		fe = em.merge(fe);

		SecondEquipment se = new SecondEquipment();
		se.setCode("SECOND");
		se.setDescription("SECOND");

		se = em.merge(se);

		FirstEquipmentCycle fc = new FirstEquipmentCycle();
		fc.setCode("FIRST");
		fc.setDescription("FIRST");
		fc.setEquipment(fe);

		em.persist(fc);

		SecondEquipmentCycle sc = new SecondEquipmentCycle();
		sc.setCode("SECOND");
		sc.setDescription("SECOND");
		sc.setEquipment(se);

		em.persist(sc);

		utx.commit();
	}

	@Test
	public void testPolymorphic() throws Exception {

		insertSampleRecords();

		utx.begin();
		em.joinTransaction();

		printStatus("Selecting (using JPQL)...");
		List<EquipmentCycle> cycles = em.createQuery(
				"select ec from EquipmentCycle ec order by ec.code",
				EquipmentCycle.class).getResultList();
		printStatus("Found " + cycles.size() + " games (using JPQL)");

		utx.commit();

	}

	private void printStatus(Object message) {
		if (log instanceof Logger) {
			((Logger) log).info(message.toString());
		}
	}

}
