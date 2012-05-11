package org.landal.jpa.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("FIRST")
public class FirstEquipmentStatus extends EquipmentStatus {
	
	private static final long serialVersionUID = 1L;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 20, nullable = false)	
	private FirstStatus status;
	
	public FirstEquipmentStatus() {
		
	}

	public FirstStatus getStatus() {
		return status;
	}

	public void setStatus(FirstStatus status) {
		this.status = status;
	}
	
	

}
