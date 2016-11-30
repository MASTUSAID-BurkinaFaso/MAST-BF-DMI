package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.fetch.Usertable;
import com.rmsi.mast.studio.util.JsonDateSerializer;
import com.vividsolutions.jts.geom.Geometry;


/**
 * Entity implementation class for Entity: SpatialUnit
 *
 */
@Entity
@Table(name = "spatial_unit")
public class SpatialUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SPATIAL_UNIT_ID_GENERATOR", sequenceName = "SPATIAL_UNIT_USIN_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SPATIAL_UNIT_ID_GENERATOR")
	private long usin;

	@Column(name = "spatial_unit_type")
	private String type;

	@Column(name = "project_name")
	private String project;


	private String existing_use;

	@ManyToOne
	@JoinColumn(name = "proposed_use")
	private LandUseType proposedUse;

	@ManyToOne
	@JoinColumn(name = "type_name")
	private LandType typeName;

	@Column(name = "identity", unique = true)
	private String identity;

	@Column(name = "house_type")
	private String houseType;

	@Column(name = "total_househld_no")
	private int househidno;

	@Column(name = "other_use_type")
	private String otherUseType;

	private float perimeter;

	@Column(name = "house_shape")
	private String houseshape;

	private double area;

	@ManyToOne
	@JoinColumn(name = "measurement_unit", nullable = false)
	private Unit measurementUnit;

	@Column(name = "family_name")
	private String family_name;

	@Column(name = "uka_propertyno", nullable = false)
	private String propertyno;

	@Column(nullable = false)
	private String comments;

	@Column(nullable = false)
	private String gtype;

	@ManyToOne
	@JoinColumn(name = "current_workflow_status_id", nullable = false)
	private Status status;

	@Column(name = "workflow_status_update_time", nullable = false)
	private Date statusUpdateTime;

	@Column(nullable = false)
	private int userid;

	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private Usertable user;

	@Column(name = "survey_date", nullable = false)
	private Date surveyDate;

	@Column(name = "imei_number", nullable = false)
	private String imeiNumber;

	@Type(type = "org.hibernate.spatial.GeometryType")	
	@Column(name = "the_geom")
	private Geometry theGeom;

	private String address1;

	private String address2;

	private String postal_code;
	
	@Column(name = "neighbor_north")
	private String neighborNorth;
	
	@Column(name = "neighbor_south")
	private String neighborSouth;

	@Column(name = "neighbor_east")
	private String neighborEast;
	
	@Column(name = "neighbor_west")
	private String neighborWest;

	@Column(name = "witness_1")
	private String witness1;
	
	@Column(name = "witness_2")
	private String witness2;

	@Column(name = "witness_3")
	private String witness3;

	@Column(name = "witness_4")
	private String witness4;
	
	@ManyToOne
	@JoinColumn(name = "quality_of_soil")
	private SoilQualityValues soilQuality;
	
	@ManyToOne
	@JoinColumn(name = "slope")
	private SlopeValues slope;
	
	@Column(name = "usin_str")
	private String usinStr;
	
	private Boolean active;
	
	@Column(name = "hamlet_id")
	private int hamletId;
	
	@Column(name = "villageno")
	private String villageno;
	
	@ManyToOne
	@JoinColumn(name = "village_id")
	private Village village_id;
	
	@Column(name = "applicationdate")
	private Date applicationdate;
	
	@Column(name = "issuancedate")
	private Date issuancedate;
	
	@Column(name = "public_notice_startdate")
	private Date public_notice_startdate;
	
	@Column(name = "public_notice_enddate")
	private Date public_notice_enddate;
	
	@Column(name = "apfr_date")
	private Date apfr_date;
	
	@Column(name = "registrationno")
	private String registrationno;
	
	@ManyToOne
	@JoinColumn(name = "noa_id")
	private NatureOfApplication noa_id;
	
	@ManyToOne
	@JoinColumn(name = "mt_id")
	private MutationType mt_id;
	
	@Column(name = "parcelno")
	private String parcelno;
	
	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private Workflow workflow_id;
	
	@ManyToOne
	@JoinColumn(name = "parceltype_id")
	private ParcelType parceltype_id;
	
	@ManyToOne
	@JoinColumn(name = "title_id")
	private TitleExisting title_id;
	
	private String title_number;
	
	private Date title_date;
	
	private int section;
	
	/*
	 * @OneToMany(fetch=FetchType.EAGER)
	 * 
	 * @JoinColumn(name="usin") private List<SocialTenureRelationship>
	 * socialTenureRelationships;
	 */

	public int getHamletId() {
		return hamletId;
	}

	public void setHamletId(int hamletId) {
		this.hamletId = hamletId;
	}

	public Unit getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(Unit measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getGeometry() {
		return geometry;
	}

	public long getUsin() {
		return usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	@Transient
	private String geometry;

	@JsonIgnore
	public Geometry getTheGeom() {
		return theGeom;
	}

	public void setTheGeom(Geometry theGeom) {
		this.theGeom = theGeom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public LandUseType getProposedUse() {
		return proposedUse;
	}

	public void setProposedUse(LandUseType proposedUse) {
		this.proposedUse = proposedUse;
	}

	public LandType getTypeName() {
		return typeName;
	}

	public void setTypeName(LandType typeName) {
		this.typeName = typeName;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public int getHousehidno() {
		return househidno;
	}

	public void setHousehidno(int househidno) {
		this.househidno = househidno;
	}

	public String getOtherUseType() {
		return otherUseType;
	}

	public void setOtherUseType(String otherUseType) {
		this.otherUseType = otherUseType;
	}

	public float getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(float perimeter) {
		this.perimeter = perimeter;
	}

	public String getHouseshape() {
		return houseshape;
	}

	public void setHouseshape(String houseshape) {
		this.houseshape = houseshape;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}



	public String getPropertyno() {
		return propertyno;
	}

	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	

	public String getGtype() {
		return gtype;
	}

	public void setGtype(String gtype) {
		this.gtype = gtype;
	}



	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public Usertable getUser() {
		return user;
	}

	public void setUser(Usertable user) {
		this.user = user;
	}

	public String getNeighborNorth() {
		return neighborNorth;
	}

	public void setNeighborNorth(String neighborNorth) {
		this.neighborNorth = neighborNorth;
	}

	public String getNeighborSouth() {
		return neighborSouth;
	}

	public void setNeighborSouth(String neighborSouth) {
		this.neighborSouth = neighborSouth;
	}

	public String getNeighborEast() {
		return neighborEast;
	}

	public void setNeighborEast(String neighborEast) {
		this.neighborEast = neighborEast;
	}

	public String getNeighborWest() {
		return neighborWest;
	}

	public void setNeighborWest(String neighborWest) {
		this.neighborWest = neighborWest;
	}

	public String getWitness1() {
		return witness1;
	}

	public void setWitness1(String witness1) {
		this.witness1 = witness1;
	}

	public String getWitness2() {
		return witness2;
	}

	public void setWitness2(String witness2) {
		this.witness2 = witness2;
	}

	public String getWitness3() {
		return witness3;
	}

	public void setWitness3(String witness3) {
		this.witness3 = witness3;
	}

	public String getWitness4() {
		return witness4;
	}

	public void setWitness4(String witness4) {
		this.witness4 = witness4;
	}

	public SoilQualityValues getSoilQuality() {
		return soilQuality;
	}

	public void setSoilQuality(SoilQualityValues soilQuality) {
		this.soilQuality = soilQuality;
	}

	public SlopeValues getSlope() {
		return slope;
	}

	public void setSlope(SlopeValues slope) {
		this.slope = slope;
	}

	public String getUsinStr() {
		return usinStr;
	}

	public void setUsinStr(String usinStr) {
		this.usinStr = usinStr;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getVillageno() {
		return villageno;
	}

	public void setVillageno(String villageno) {
		this.villageno = villageno;
	}

	public Village getVillage_id() {
		return village_id;
	}

	public void setVillage_id(Village village_id) {
		this.village_id = village_id;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getApplicationdate() {
		return applicationdate;
	}

	public void setApplicationdate(Date applicationdate) {
		this.applicationdate = applicationdate;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getIssuancedate() {
		return issuancedate;
	}

	public void setIssuancedate(Date issuancedate) {
		this.issuancedate = issuancedate;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPublic_notice_startdate() {
		return public_notice_startdate;
	}

	public void setPublic_notice_startdate(Date public_notice_startdate) {
		this.public_notice_startdate = public_notice_startdate;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPublic_notice_enddate() {
		return public_notice_enddate;
	}

	public void setPublic_notice_enddate(Date public_notice_enddate) {
		this.public_notice_enddate = public_notice_enddate;
	}
	
	

	public String getRegistrationno() {
		return registrationno;
	}

	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
	}

	public NatureOfApplication getNoa_id() {
		return noa_id;
	}

	public void setNoa_id(NatureOfApplication noa_id) {
		this.noa_id = noa_id;
	}

	public MutationType getMt_id() {
		return mt_id;
	}

	public void setMt_id(MutationType mt_id) {
		this.mt_id = mt_id;
	}

	public String getParcelno() {
		return parcelno;
	}

	public void setParcelno(String parcelno) {
		this.parcelno = parcelno;
	}

	public Workflow getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(Workflow workflow_id) {
		this.workflow_id = workflow_id;
	}

	public ParcelType getParceltype_id() {
		return parceltype_id;
	}

	public void setParceltype_id(ParcelType parceltype_id) {
		this.parceltype_id = parceltype_id;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getApfr_date() {
		return apfr_date;
	}

	public void setApfr_date(Date apfr_date) {
		this.apfr_date = apfr_date;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}


	public String getExisting_use() {
		return existing_use;
	}

	public void setExisting_use(String existing_use) {
		this.existing_use = existing_use;
	}


	public TitleExisting getTitle_id() {
		return title_id;
	}

	public void setTitle_id(TitleExisting title_id) {
		this.title_id = title_id;
	}

	public String getTitle_number() {
		return title_number;
	}

	public void setTitle_number(String title_number) {
		this.title_number = title_number;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getTitle_date() {
		return title_date;
	}

	public void setTitle_date(Date title_date) {
		this.title_date = title_date;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}
	

}
