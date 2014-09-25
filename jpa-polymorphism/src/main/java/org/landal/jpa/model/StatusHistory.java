package org.landal.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "STATUS_HISTORY")
public class StatusHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Version
	@Column(name = "VERSION")
	protected Integer version;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 20, nullable = false)
	private EntityWithHistoryStatus status;
	
	@ManyToOne
	@JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID", nullable = false)
	private EntityWithHistory entityWithHistory;

	@JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "ID")
	@ManyToOne
	private Equipment equipment;

	@JoinColumn(name = "EQUIPMENT_CYCLE_ID", referencedColumnName = "ID")
	@ManyToOne
	private EquipmentCycle equipmentCycle;

	public StatusHistory() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public EntityWithHistoryStatus getStatus() {
		return status;
	}

	public void setStatus(EntityWithHistoryStatus status) {
		this.status = status;
	}

	public EntityWithHistory getEntityWithHistory() {
		return entityWithHistory;
	}

	public void setEntityWithHistory(EntityWithHistory entityWithHistory) {
		this.entityWithHistory = entityWithHistory;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public EquipmentCycle getEquipmentCycle() {
		return equipmentCycle;
	}

	public void setEquipmentCycle(EquipmentCycle equipmentCycle) {
		this.equipmentCycle = equipmentCycle;
	}

}
