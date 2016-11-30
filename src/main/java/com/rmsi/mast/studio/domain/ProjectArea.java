package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the project_area database table.
 * @author Prashant.Nigam
 */
@Entity
@Table(name="project_area")
public class ProjectArea implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROJECT_AREA_AREAID_GENERATOR", sequenceName="PROJECT_AREA_GID_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PROJECT_AREA_AREAID_GENERATOR")
	@Column(name="area_id")
	private Long areaId;

	@Column(name="village_chairman")
	private String villageChairman;

	@Column(name="approving_executive")
	private String approvingExecutive;

	@Temporal(TemporalType.DATE)
	@Column(name="village_chairman_approval_date")
	private Date villageChairmanApprovalDate;

	@Column(name="district_officer")
	private String districtOfficer;

	@Column(name="authority_approve")
	private Boolean authorityApprove;

	@Column(name="bounding_box")
	private String boundingBox;

	private String category;


	@Temporal(TemporalType.DATE)
	@Column(name="executive_approval_date")
	private Date executiveApprovalDate;

	private String municipality;

	
	

	@Column(name="district_name")
	private String districtName;

	@Column(name="executive_approve")
	private Boolean executiveApprove;

	private Integer gid;

	@Temporal(TemporalType.DATE)
	@Column(name="initiation_date")
	private Date initiationDate;

	private String location;

	private String perimeter;

	private Integer projectid;

	

	@Column(name="state_name")
	private String stateName;

	//@Column(name="village")
	private String village;

	private String wards;

	private String village_code;
	private String address;

	@Temporal(TemporalType.DATE)
	@Column(name="recommendation_date")
	private Date recommendationDate;

	@Column(name="name")
	private String projectName;
	
	
	private String presidentname;
	
	private String mayorname;
	
	
	
	
	@ManyToOne
	@JoinColumn(name="commune_id")
	private Commune commune_id;
	
	private String country_name;
	private String region;
	private String province;
	private String commune;

	public ProjectArea() {
	}

	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getApprovingExecutive() {
		return this.approvingExecutive;
	}

	public void setApprovingExecutive(String approvingExecutive) {
		this.approvingExecutive = approvingExecutive;
	}

	public Boolean getAuthorityApprove() {
		return this.authorityApprove;
	}

	public void setAuthorityApprove(Boolean authorityApprove) {
		this.authorityApprove = authorityApprove;
	}

	public String getBoundingBox() {
		return this.boundingBox;
	}

	public void setBoundingBox(String boundingBox) {
		this.boundingBox = boundingBox;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



	public Date getExecutiveApprovalDate() {
		return this.executiveApprovalDate;
	}

	public void setExecutiveApprovalDate(Date executiveApprovalDate) {
		this.executiveApprovalDate = executiveApprovalDate;
	}

	public Boolean getExecutiveApprove() {
		return this.executiveApprove;
	}

	public void setExecutiveApprove(Boolean executiveApprove) {
		this.executiveApprove = executiveApprove;
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Date getInitiationDate() {
		return this.initiationDate;
	}

	public void setInitiationDate(Date initiationDate) {
		this.initiationDate = initiationDate;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPerimeter() {
		return this.perimeter;
	}

	public void setPerimeter(String perimeter) {
		this.perimeter = perimeter;
	}

	public Integer getProjectid() {
		return this.projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public Date getRecommendationDate() {
		return this.recommendationDate;
	}

	public void setRecommendationDate(Date recommendationDate) {
		this.recommendationDate = recommendationDate;
	}

	

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getVillage() {
		return this.village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getWards() {
		return this.wards;
	}

	public void setWards(String wards) {
		this.wards = wards;
	}
	
	public String getMunicipality() {
		return this.municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getVillageChairman() {
		return villageChairman;
	}

	public void setVillageChairman(String villageChairman) {
		this.villageChairman = villageChairman;
	}

	public Date getVillageChairmanApprovalDate() {
		return villageChairmanApprovalDate;
	}

	public void setVillageChairmanApprovalDate(Date villageChairmanApprovalDate) {
		this.villageChairmanApprovalDate = villageChairmanApprovalDate;
	}

	public String getDistrictOfficer() {
		return districtOfficer;
	}

	public void setDistrictOfficer(String districtOfficer) {
		this.districtOfficer = districtOfficer;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getVillage_code() {
		return village_code;
	}

	public void setVillage_code(String village_code) {
		this.village_code = village_code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPresidentname() {
		return presidentname;
	}

	public void setPresidentname(String presidentname) {
		this.presidentname = presidentname;
	}

	public String getMayorname() {
		return mayorname;
	}

	public void setMayorname(String mayorname) {
		this.mayorname = mayorname;
	}

	

	public Commune getCommune_id() {
		return commune_id;
	}

	public void setCommune_id(Commune commune_id) {
		this.commune_id = commune_id;
	}


	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	
	
	
	
}