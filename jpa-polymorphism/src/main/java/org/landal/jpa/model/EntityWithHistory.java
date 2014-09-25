package org.landal.jpa.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "ENTITY_WITH_HISTORY")
public class EntityWithHistory extends BaseEntity{
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 20, nullable = false)	
	private EntityWithHistoryStatus status;

	@OneToMany(mappedBy = "entityWithHistory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StatusHistory> stateHistory;
	
	public EntityWithHistory() {
	
	}

	public EntityWithHistoryStatus getStatus() {
		return status;
	}

	public void setStatus(EntityWithHistoryStatus status) {
		this.status = status;
	}

	public List<StatusHistory> getStateHistory() {
		return stateHistory;
	}

	public void setStateHistory(List<StatusHistory> stateHistory) {
		this.stateHistory = stateHistory;
	}

	
	
	
	

}
