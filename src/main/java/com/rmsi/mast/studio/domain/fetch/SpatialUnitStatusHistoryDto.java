package com.rmsi.mast.studio.domain.fetch;

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



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.util.JsonDateSerializer;


/**
 * The persistent class for the users database table. 
 */
@Entity
@Table(name="sunit_workflow_status_history")
public class SpatialUnitStatusHistoryDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	private long status_hist_id;
	
	@Column(name="usin", nullable=false)
	private long usin;
	
	@ManyToOne
	@JoinColumn(name = "workflow_status_id")
	private Status workflow_status_id;
	
	
	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private Workflow workflow_id;
	
	
	@Column(name="comments", nullable=false)
	private String comments;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private Usertable userid;
	
	@Column(name="status_change_time", nullable=false)
	private Date status_change_time;

	public long getStatus_hist_id() {
		return status_hist_id;
	}

	public void setStatus_hist_id(long status_hist_id) {
		this.status_hist_id = status_hist_id;
	}

	public long getUsin() {
		return usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}


	public Usertable getUserid() {
		return userid;
	}

	public void setUserid(Usertable userid) {
		this.userid = userid;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStatus_change_time() {
		return status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Status getWorkflow_status_id() {
		return workflow_status_id;
	}

	public void setWorkflow_status_id(Status workflow_status_id) {
		this.workflow_status_id = workflow_status_id;
	}

	public Workflow getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(Workflow workflow_id) {
		this.workflow_id = workflow_id;
	}

	



	




}