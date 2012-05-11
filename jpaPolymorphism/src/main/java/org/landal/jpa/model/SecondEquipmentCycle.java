package org.landal.jpa.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SECOND")
public class SecondEquipmentCycle extends EquipmentCycle {

}
