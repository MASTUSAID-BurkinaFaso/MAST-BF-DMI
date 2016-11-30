package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mutation_type database table.
 * 
 */
@Entity
@Table(name="mutation_type")
@NamedQuery(name="MutationType.findAll", query="SELECT m FROM MutationType m")
public class MutationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mt_id")
	private Integer mtId;

	@Column(name="mutation_type")
	private String mutationType;

	@Column(name="mutation_type_fr")
	private String mutationTypeFr;

	public MutationType() {
	}

	public Integer getMtId() {
		return this.mtId;
	}

	public void setMtId(Integer mtId) {
		this.mtId = mtId;
	}

	public String getMutationType() {
		return this.mutationType;
	}

	public void setMutationType(String mutationType) {
		this.mutationType = mutationType;
	}

	public String getMutationTypeFr() {
		return this.mutationTypeFr;
	}

	public void setMutationTypeFr(String mutationTypeFr) {
		this.mutationTypeFr = mutationTypeFr;
	}

}