package org.landal.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "STOCKS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "STOCK_NAME"),
		@UniqueConstraint(columnNames = "STOCK_CODE") })
public class Stock extends BaseEntity {
 
	private static final long serialVersionUID = 1L;
	
	public static Stock newInstance(String code, String name) {
		Stock stock = new Stock();
	    stock.setStockCode(code);
	    stock.setStockName(name);
	    return stock;
	}
	
	@Column(name = "STOCK_CODE", unique = true, nullable = false, length = 10)	
	private String stockCode;
	
	@Column(name = "STOCK_NAME", unique = true, nullable = false, length = 20)
	private String stockName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.stock", cascade=CascadeType.ALL)
	private Set<StockCategory> stockCategories = new HashSet<StockCategory>(0);
 
	public Stock() {
	}
 
	public Stock(String stockCode, String stockName) {
		this.stockCode = stockCode;
		this.stockName = stockName;
	}
 
	public Stock(String stockCode, String stockName,
			Set<StockCategory> stockCategories) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.stockCategories = stockCategories;
	}
	
	public void addStockCategory(StockCategory stockCategory) {
		
		if(getStockCategories() == null){
			setStockCategories(new HashSet<StockCategory>());
		}
		
		stockCategory.setStock(this);
		getStockCategories().add(stockCategory);				
	}
	
	/////////////////////////////////////////////////////
 
	
	public String getStockCode() {
		return this.stockCode;
	}
 
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
 
	public String getStockName() {
		return this.stockName;
	}
 
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
 
	public Set<StockCategory> getStockCategories() {
		return this.stockCategories;
	}
 
	public void setStockCategories(Set<StockCategory> stockCategories) {
		this.stockCategories = stockCategories;
	}

 
}
