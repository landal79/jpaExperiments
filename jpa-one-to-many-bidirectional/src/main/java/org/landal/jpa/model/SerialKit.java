package org.landal.jpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "SERIAL_KIT")
public class SerialKit extends BaseEntity {
		
	private static final long serialVersionUID = 3216607157436474794L;

	/**
	 * Barcode inventariale
	 */	
	@Column(name = "BARCODE", nullable = false, length = 100, unique = true)
	private String barcode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 20, nullable = false)
	private Status status;
	
	@OneToMany(mappedBy = "serialKit", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(value = "operationDate")
	private List<SerialKitStatus> statusHistory;
	
	public SerialKit() {
		
	}
	
	public void addStatus(SerialKitStatus status) {
		
		if(status == null){
			throw new NullPointerException();
		}
		
		if (getStatusHistory() == null) {
			setStatusHistory(new ArrayList<SerialKitStatus>());
		}

		status.setSerialKit(this);
		getStatusHistory().add(status);
		
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<SerialKitStatus> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<SerialKitStatus> statusHistory) {
		this.statusHistory = statusHistory;
	}
	
	

}
