package org.landal.jpa.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EquipmentCycle")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DIS", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class EquipmentCycle extends BaseEntity{

	

	@JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "ID")
	@ManyToOne
	private Equipment equipment;

	public EquipmentCycle() {

	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	

}
