package org.landal.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", nullable = false, length = 10)
	private String name;

	@Column(name = "DESC", nullable = false)
	private String desc;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.category")
	private Set<StockCategory> stockCategories = new HashSet<StockCategory>(0);

	public Category() {
	}

	public Category(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public Category(String name, String desc, Set<StockCategory> stockCategories) {
		this.name = name;
		this.desc = desc;
		this.stockCategories = stockCategories;
	}

	public void addStockCategory(StockCategory stockCategory) {
		if (getStockCategories() == null) {
			setStockCategories(new HashSet<StockCategory>());
		}

		stockCategory.setCategory(this);
		getStockCategories().add(stockCategory);
	}

	// ////////////////////////////////////////////////////////////

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<StockCategory> getStockCategories() {
		return this.stockCategories;
	}

	public void setStockCategories(Set<StockCategory> stockCategories) {
		this.stockCategories = stockCategories;
	}

}
