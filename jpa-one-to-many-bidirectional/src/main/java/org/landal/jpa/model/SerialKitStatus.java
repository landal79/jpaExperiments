package org.landal.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SERIAL_KIT_STATUS")
@NamedQueries({ @NamedQuery(name = SerialKitStatus.DELETE_ALL, query = "delete from SerialKitStatus") })
public class SerialKitStatus extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String DELETE_ALL = "SerialKitStatus.deleteAll";

	@ManyToOne
	@JoinColumn(name = "SERIAL_KIT_ID", referencedColumnName = "ID", nullable = false)
	private SerialKit serialKit;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 20, nullable = false)
	private Status status;

	@Column(name = "OPERATION_DATE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationDate;

	public SerialKitStatus() {
	}

	// ////////////////////////////////////////////////

	public SerialKit getSerialKit() {
		return serialKit;
	}

	public void setSerialKit(SerialKit serialKit) {
		this.serialKit = serialKit;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

}
