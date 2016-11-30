package com.rmsi.mast.studio.domain.fetch;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rmsi.mast.studio.domain.Commune;

@Entity
@Table(name="project_area")
public class ProjectTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	private String name;
	
	
	@Column(name="country_name")
	private String countryName;

	@Column(name="district_name")
	private String districtName;
	
	private String village;
	private String region;
	
	
	@ManyToOne
	@JoinColumn(name="commune_id")
	private Commune commune_id;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Commune getCommune_id() {
		return commune_id;
	}

	public void setCommune_id(Commune commune_id) {
		this.commune_id = commune_id;
	}

}