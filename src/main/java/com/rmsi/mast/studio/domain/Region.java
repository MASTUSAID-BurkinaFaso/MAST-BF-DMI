package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the region database table.
 * 
 */
@Entity
@NamedQuery(name="Region.findAll", query="SELECT r FROM Region r")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="region_id")
	private Integer regionId;

	@Column(name="region_name")
	private String regionName;

	@Column(name="region_name_fr")
	private String regionNameFr;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country_id;

	//bi-directional many-to-one association to Province
/*	@OneToMany(mappedBy="region")
	private List<Province> provinces;
*/
	public Region() {
	}

	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionNameFr() {
		return this.regionNameFr;
	}

	public void setRegionNameFr(String regionNameFr) {
		this.regionNameFr = regionNameFr;
	}

	public Country getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Country country_Id) {
		this.country_id = country_Id;
	}

	
	
	
	
/*	public List<Province> getProvinces() {
		return this.provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

	public Province addProvince(Province province) {
		getProvinces().add(province);
		province.setRegion(this);

		return province;
	}

	public Province removeProvince(Province province) {
		getProvinces().remove(province);
		province.setRegion(null);

		return province;
	}
*/
}