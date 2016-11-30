package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parcel_type database table.
 * 
 */
@Entity
@Table(name="parcel_type")
@NamedQuery(name="ParcelType.findAll", query="SELECT p FROM ParcelType p")
public class ParcelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="parceltype_id")
	private Integer parceltypeId;

	private String parceltype;
	
	private String parceltype_fr;

/*	//bi-directional many-to-one association to Workflow
	@OneToMany(mappedBy="parcelType")
	private List<Workflow> workflows;*/

	public ParcelType() {
	}

	public Integer getParceltypeId() {
		return this.parceltypeId;
	}

	public void setParceltypeId(Integer parceltypeId) {
		this.parceltypeId = parceltypeId;
	}

	public String getParceltype() {
		return this.parceltype;
	}

	public void setParceltype(String parceltype) {
		this.parceltype = parceltype;
	}

	public String getParceltype_fr() {
		return parceltype_fr;
	}

	public void setParceltype_fr(String parceltype_fr) {
		this.parceltype_fr = parceltype_fr;
	}

	/*public List<Workflow> getWorkflows() {
		return this.workflows;
	}

	public void setWorkflows(List<Workflow> workflows) {
		this.workflows = workflows;
	}

	public Workflow addWorkflow(Workflow workflow) {
		getWorkflows().add(workflow);
		workflow.setParcelType(this);

		return workflow;
	}

	public Workflow removeWorkflow(Workflow workflow) {
		getWorkflows().remove(workflow);
		workflow.setParcelType(null);

		return workflow;
	}*/

}