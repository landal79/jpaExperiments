package org.landal.jpa.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("SECOND")
public class SecondEquipmentStatus extends EquipmentStatus {

	private static final long serialVersionUID = 1L;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 20, nullable = false)
	private SecondStatus status;
	
	public SecondEquipmentStatus() {
		
	}

	public SecondStatus getStatus() {
		return status;
	}

	public void setStatus(SecondStatus status) {
		this.status = status;
	}
	
	
	
}
