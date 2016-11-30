package com.rmsi.mast.studio.domain.fetch;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.MutationType;
import com.rmsi.mast.studio.domain.NatureOfApplication;
import com.rmsi.mast.studio.domain.ParcelType;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.util.JsonDateSerializer;

@Entity
@Table(name = "spatial_unit")

public class SpatialUnitTemp implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long usin;

	
	@Column(name = "project_name")
	private String project;



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
	
	
	private String application_no;
	private String pv_no;
	private String apfr_no;
	private Integer section;


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



	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
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


	public Usertable getUser() {
		return user;
	}


	public void setUser(Usertable user) {
		this.user = user;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getSurveyDate() {
		return surveyDate;
	}


	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}


	public String getUsinStr() {
		return usinStr;
	}


	public void setUsinStr(String usinStr) {
		this.usinStr = usinStr;
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


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getApplication_no() {
		return application_no;
	}


	public void setApplication_no(String application_no) {
		this.application_no = application_no;
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


	public Integer getSection() {
		return section;
	}


	public void setSection(Integer section) {
		
		this.section = section;
	}
	

	
	
	
}
