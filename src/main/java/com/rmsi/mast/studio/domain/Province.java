package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the province database table.
 * 
 */
@Entity
@NamedQuery(name="Province.findAll", query="SELECT p FROM Province p")
public class Province implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="province_id")
	private Integer provinceId;

	@Column(name="province_name")
	private String provinceName;

	@Column(name="province_name_fr")
	private String provinceNameFr;

	//bi-directional many-to-one association to Commune
/*	@OneToMany(mappedBy="province")
	private List<Commune> communes;
*/
	//bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;

	public Province() {
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceNameFr() {
		return this.provinceNameFr;
	}

	public void setProvinceNameFr(String provinceNameFr) {
		this.provinceNameFr = provinceNameFr;
	}

/*	public List<Commune> getCommunes() {
		return this.communes;
	}

	public void setCommunes(List<Commune> communes) {
		this.communes = communes;
	}

	public Commune addCommune(Commune commune) {
		getCommunes().add(commune);
		commune.setProvince(this);

		return commune;
	}

	public Commune removeCommune(Commune commune) {
		getCommunes().remove(commune);
		commune.setProvince(null);

		return commune;
	}
*/
	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}