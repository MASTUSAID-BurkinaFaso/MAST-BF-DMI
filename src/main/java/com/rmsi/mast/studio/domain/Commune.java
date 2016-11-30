package com.rmsi.mast.studio.domain;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the commune database table.
 * 
 */
@Entity
@NamedQuery(name="Commune.findAll", query="SELECT c FROM Commune c")
public class Commune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="commune_id")
	private Integer communeId;

	@Column(name="commune_name")
	private String communeName;

	@Column(name="commune_name_fr")
	private String communeNameFr;

	//bi-directional many-to-one association to Province
	@ManyToOne
	@JoinColumn(name="province_id")
	private Province province;

	//bi-directional many-to-one association to Village
	/*@OneToMany(mappedBy="commune")
	private List<Village> villages;*/

	public Commune() {
	}

	public Integer getCommuneId() {
		return this.communeId;
	}

	public void setCommuneId(Integer communeId) {
		this.communeId = communeId;
	}

	public String getCommuneName() {
		return this.communeName;
	}

	public void setCommuneName(String communeName) {
		this.communeName = communeName;
	}

	public String getCommuneNameFr() {
		return this.communeNameFr;
	}

	public void setCommuneNameFr(String communeNameFr) {
		this.communeNameFr = communeNameFr;
	}

	public Province getProvince() {
		return this.province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

/*	public List<Village> getVillages() {
		return this.villages;
	}

	public void setVillages(List<Village> villages) {
		this.villages = villages;
	}

	public Village addVillage(Village village) {
		getVillages().add(village);
		village.setCommune(this);

		return village;
	}

	public Village removeVillage(Village village) {
		getVillages().remove(village);
		village.setCommune(null);

		return village;
	}*/

}