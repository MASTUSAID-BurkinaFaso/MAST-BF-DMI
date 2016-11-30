package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the datatype_id database table.
 * 
 */
@Entity
@Table(name="datatype_id")
public class DatatypeId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="datatype_id")
	private long datatypeId;

	private String datatype;
	private String datatype_fr;

	
	public DatatypeId() {
	}

	public long getDatatypeId() {
		return this.datatypeId;
	}

	public void setDatatypeId(long datatypeId) {
		this.datatypeId = datatypeId;
	}

	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDatatype_fr() {
		return datatype_fr;
	}

	public void setDatatype_fr(String datatype_fr) {
		this.datatype_fr = datatype_fr;
	}

}