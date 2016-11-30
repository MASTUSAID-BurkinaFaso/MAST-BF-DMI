package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: WorkflowStatusHistory
 *
 */
@Entity
@Table(name = "sunit_workflow_status_history")
public class WorkflowStatusHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "sunit_workflow_status_history_id", sequenceName = "sunit_workflow_status_history_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sunit_workflow_status_history_id")
	private long status_hist_id;
	private long usin;
	private int workflow_status_id;
	private long userid;
	private Date status_change_time;
	@Column(name="workflow_id", nullable=false)
	private int workflow_id;
	
	
	@Column(name="comments", nullable=false)
	private String comments;

	public WorkflowStatusHistory() {
		super();
	}

	public long getStatus_hist_id() {
		return this.status_hist_id;
	}

	public void setStatus_hist_id(long status_hist_id) {
		this.status_hist_id = status_hist_id;
	}

	public long getUsin() {
		return this.usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}

	public int getWorkflow_status_id() {
		return this.workflow_status_id;
	}

	public void setWorkflow_status_id(int workflow_status_id) {
		this.workflow_status_id = workflow_status_id;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public Date getStatus_change_time() {
		return this.status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	public int getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(int workflow_id) {
		this.workflow_id = workflow_id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
