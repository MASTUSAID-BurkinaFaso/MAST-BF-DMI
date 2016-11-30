package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_category database table.
 * 
 */
@Entity
@Table(name="attribute_category")
public class AttributeCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long attributecategoryid;

	@Column(name="category_name")
	private String categoryName;
	private String category_name_fr;

	public AttributeCategory() {
	}

	public long getAttributecategoryid() {
		return this.attributecategoryid;
	}

	public void setAttributecategoryid(long attributecategoryid) {
		this.attributecategoryid = attributecategoryid;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategory_name_fr() {
		return category_name_fr;
	}

	public void setCategory_name_fr(String category_name_fr) {
		this.category_name_fr = category_name_fr;
	}
	
}