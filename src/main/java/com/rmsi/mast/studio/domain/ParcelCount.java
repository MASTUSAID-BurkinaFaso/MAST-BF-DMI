
package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the action database table.
 * 
 */
@Entity
@Table(name = "parcelcount")
public class ParcelCount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	private String type;
	
	private long count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	
	
	
}