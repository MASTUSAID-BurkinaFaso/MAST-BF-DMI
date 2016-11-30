package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the nature_of_application database table.
 * 
 */
@Entity
@Table(name="nature_of_application")
@NamedQuery(name="NatureOfApplication.findAll", query="SELECT n FROM NatureOfApplication n")
public class NatureOfApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="noa_id")
	private Integer noaId;

	@Column(name="nature_of_application")
	private String natureOfApplication;

	@Column(name="nature_of_application_fr")
	private String natureOfApplicationFr;

	public NatureOfApplication() {
	}

	public Integer getNoaId() {
		return this.noaId;
	}

	public void setNoaId(Integer noaId) {
		this.noaId = noaId;
	}

	public String getNatureOfApplication() {
		return this.natureOfApplication;
	}

	public void setNatureOfApplication(String natureOfApplication) {
		this.natureOfApplication = natureOfApplication;
	}

	public String getNatureOfApplicationFr() {
		return this.natureOfApplicationFr;
	}

	public void setNatureOfApplicationFr(String natureOfApplicationFr) {
		this.natureOfApplicationFr = natureOfApplicationFr;
	}

}