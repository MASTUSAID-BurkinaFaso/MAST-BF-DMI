package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the nature_of_power database table.
 * 
 */
@Entity
@Table(name="nature_of_power")
@NamedQuery(name="NatureOfPower.findAll", query="SELECT n FROM NatureOfPower n")
public class NatureOfPower implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nop_id")
	private Integer nopId;

	@Column(name="nature_of_power")
	private String natureOfPower;

	@Column(name="nature_of_power_fr")
	private String natureOfPowerFr;

	public NatureOfPower() {
	}

	public Integer getNopId() {
		return this.nopId;
	}

	public void setNopId(Integer nopId) {
		this.nopId = nopId;
	}

	public String getNatureOfPower() {
		return this.natureOfPower;
	}

	public void setNatureOfPower(String natureOfPower) {
		this.natureOfPower = natureOfPower;
	}

	public String getNatureOfPowerFr() {
		return this.natureOfPowerFr;
	}

	public void setNatureOfPowerFr(String natureOfPowerFr) {
		this.natureOfPowerFr = natureOfPowerFr;
	}

}