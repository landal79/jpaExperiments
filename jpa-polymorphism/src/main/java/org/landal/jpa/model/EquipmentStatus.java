package org.landal.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "EQUIPMENT_STATUS")
@DiscriminatorColumn(name = "DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING, length = 10)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EquipmentStatus implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Version
	@Column(name = "VERSION")
	protected Integer version;
	
	public EquipmentStatus() {
	
	}

	public Long getId() {
		return id;
	}
	
	

}
