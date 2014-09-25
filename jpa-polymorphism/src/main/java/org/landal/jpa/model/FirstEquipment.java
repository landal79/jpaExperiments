package org.landal.jpa.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FIRST")
public class FirstEquipment extends Equipment {

}
