package org.landal.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STOCK_CATEGORY")
@Access(AccessType.FIELD)
@AssociationOverrides({ @AssociationOverride(name = "pk.stock", joinColumns = @JoinColumn(name = "STOCK_ID")),
		@AssociationOverride(name = "pk.category", joinColumns = @JoinColumn(name = "CATEGORY_ID")) })
public class StockCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StockCategoryId pk = new StockCategoryId();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 10)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false, length = 10)
	private String createdBy;

	public StockCategory() {
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StockCategory that = (StockCategory) o;

		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

	// @Transient
	public Stock getStock() {
		return getPk().getStock();
	}

	public void setStock(Stock stock) {
		getPk().setStock(stock);
	}

	// @Transient
	public Category getCategory() {
		return getPk().getCategory();
	}

	public void setCategory(Category category) {
		getPk().setCategory(category);
	}

	// ///////////////////////////////////////////////////////////

	public StockCategoryId getPk() {
		return pk;
	}

	public void setPk(StockCategoryId pk) {
		this.pk = pk;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}

@Embeddable
class StockCategoryId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Stock stock;
	
	@ManyToOne
	private Category category;

	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StockCategoryId that = (StockCategoryId) o;

		if (stock != null ? !stock.equals(that.stock) : that.stock != null)
			return false;
		if (category != null ? !category.equals(that.category) : that.category != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (stock != null ? stock.hashCode() : 0);
		result = 31 * result + (category != null ? category.hashCode() : 0);
		return result;
	}

}
