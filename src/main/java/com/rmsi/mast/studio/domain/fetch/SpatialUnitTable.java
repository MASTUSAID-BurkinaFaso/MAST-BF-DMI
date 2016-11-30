package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MutationType;
import com.rmsi.mast.studio.domain.NatureOfApplication;
import com.rmsi.mast.studio.domain.ParcelType;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TitleExisting;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.util.JsonDateSerializer;
import com.rmsi.mast.studio.util.JsonDateSerializer2;

@Entity
@Table(name = "spatial_unit")


public class SpatialUnitTable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long usin;

	

	@Column(name = "project_name")
	private String project;


	private String existing_use;


	

	@Column(name = "total_househld_no")
	private int househidno;


	
	private Double area;



	@Column(name = "family_name")
	private String family_name; //will use as family name

	
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

	private String address1;

	private String address2;

	/*private String postal_code;*/

	private String neighbor_north;
	private String neighbor_south;
	private String neighbor_east;
	private String neighbor_west;

	@Column(name = "usin_str")
	private String usinStr;

	private Boolean active;

	@Column(name = "villageno")
	private String villageno;
	
	@ManyToOne
	@JoinColumn(name = "village_id")
	private Village village_id;
	

	
	@Column(name = "registrationno")
	private String registrationno;
	
	
	
	
	@Column(name = "parcelno")
	private String parcelno;
	
	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private Workflow workflow_id;
	
	@ManyToOne
	@JoinColumn(name = "parceltype_id")
	private ParcelType parceltype_id;
	
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
	
	
	private String application_no;
	private String pv_no;
	private String apfr_no;
	private int section;
	
	@ManyToOne
	@JoinColumn(name = "title_id")
	private TitleExisting title_id;
	
	private String title_number;
	
	private Date title_date;
	
	@Column(name = "other_use_type")
	private String otherUseType;

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

	@ManyToOne
	@JoinColumn(name = "noa_id")
	private NatureOfApplication noa_id;
	
	@ManyToOne
	@JoinColumn(name = "mt_id")
	private MutationType mt_id;
	


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

	public String getRegistrationno() {
		return registrationno;
	}

	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
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



	public long getUsin() {
		return usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}


	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}


	public void setExisting_use(String existing_use) {
		this.existing_use = existing_use;
	}

	public int getHousehidno() {
		return househidno;
	}

	public void setHousehidno(int househidno) {
		this.househidno = househidno;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}



	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
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

	public String getNeighbor_north() {
		return neighbor_north;
	}

	public void setNeighbor_north(String neighbor_north) {
		this.neighbor_north = neighbor_north;
	}

	public String getNeighbor_south() {
		return neighbor_south;
	}

	public void setNeighbor_south(String neighbor_south) {
		this.neighbor_south = neighbor_south;
	}

	public String getNeighbor_east() {
		return neighbor_east;
	}

	public void setNeighbor_east(String neighbor_east) {
		this.neighbor_east = neighbor_east;
	}

	public String getNeighbor_west() {
		return neighbor_west;
	}

	public void setNeighbor_west(String neighbor_west) {
		this.neighbor_west = neighbor_west;
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



	public String getPv_no() {
		return pv_no;
	}

	public void setPv_no(String pv_no) {
		this.pv_no = pv_no;
	}

	public String getApfr_no() {
		return apfr_no;
	}

	public void setApfr_no(String apfr_no) {
		this.apfr_no = apfr_no;
	}

	public String getApplication_no() {
		return application_no;
	}

	public void setApplication_no(String application_no) {
		this.application_no = application_no;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getApfr_date() {
		return apfr_date;
	}

	public void setApfr_date(Date apfr_date) {
		this.apfr_date = apfr_date;
	}

	public String getExisting_use() {
		return existing_use;
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

	public String getOtherUseType() {
		return otherUseType;
	}

	public void setOtherUseType(String otherUseType) {
		this.otherUseType = otherUseType;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	


}
