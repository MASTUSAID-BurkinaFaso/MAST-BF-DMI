package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the workflow database table.
 * 
 */
@Entity
@NamedQuery(name="Workflow.findAll", query="SELECT w FROM Workflow w")
public class Workflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="workflow_id")
	private Integer workflowId;

	private Boolean isactive;

	private Integer order;

	private String workflow;
	
	private String workflow_fr;

	//bi-directional many-to-one association to ParcelType
	@ManyToOne
	@JoinColumn(name="parceltype_id")
	private ParcelType parcelType;

	public Workflow() {
	}

	public Integer getWorkflowId() {
		return this.workflowId;
	}

	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getWorkflow() {
		return this.workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public ParcelType getParcelType() {
		return this.parcelType;
	}

	public void setParcelType(ParcelType parcelType) {
		this.parcelType = parcelType;
	}

	public String getWorkflow_fr() {
		return workflow_fr;
	}

	public void setWorkflow_fr(String workflow_fr) {
		this.workflow_fr = workflow_fr;
	}
}