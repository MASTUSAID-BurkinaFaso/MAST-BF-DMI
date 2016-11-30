package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the village database table.
 * 
 */
@Entity
@NamedQuery(name="Village.findAll", query="SELECT v FROM Village v")
public class Village implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="village_id")
	private Integer villageId;

	@Column(name="village_name")
	private String villageName;

	@Column(name="village_name_fr")
	private String villageNameFr;

	//bi-directional many-to-one association to Commune
	@ManyToOne
	@JoinColumn(name="commune_id")
	private Commune commune;
	
	private boolean active;
	
	
	@Column(name="cfv_agent")
	private String cfv_agent;

	public Village() {
	}

	public Integer getVillageId() {
		return this.villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return this.villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getVillageNameFr() {
		return this.villageNameFr;
	}

	public void setVillageNameFr(String villageNameFr) {
		this.villageNameFr = villageNameFr;
	}

	public Commune getCommune() {
		return this.commune;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCfv_agent() {
		return cfv_agent;
	}

	public void setCfv_agent(String cfv_agent) {
		this.cfv_agent = cfv_agent;
	}

}