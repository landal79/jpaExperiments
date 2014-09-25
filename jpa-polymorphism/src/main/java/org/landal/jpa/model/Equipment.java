package org.landal.jpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Equipment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DIS", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class Equipment extends BaseEntity {

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinTable(name = "EQUIPMENT_STATUS_HISTORY", joinColumns = @JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "STATUS_ID", referencedColumnName = "ID"))
	private List<EquipmentStatus> statusList;

	public Equipment() {

	}

	public void addStatus(EquipmentStatus equipmentStatus) {
		if (getStatusList() == null) {
			setStatusList(new ArrayList<EquipmentStatus>());
		}

		getStatusList().add(equipmentStatus);

	}

	public List<EquipmentStatus> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<EquipmentStatus> statusList) {
		this.statusList = statusList;
	}

}
