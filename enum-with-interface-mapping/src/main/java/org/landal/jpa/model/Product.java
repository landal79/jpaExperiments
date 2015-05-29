package org.landal.jpa.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
//@DiscriminatorValue("prod")
public class Product extends BaseEntity<ProductStatus> {

}
