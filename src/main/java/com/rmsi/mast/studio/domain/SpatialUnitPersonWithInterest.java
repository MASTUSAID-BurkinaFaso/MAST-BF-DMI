package com.rmsi.mast.studio.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.util.JsonDateSerializer;


/**
 * The persistent class for the spatialunit_pesonwithinterest database table.
 * 
 */
@Entity
@Table(name="spatialunit_personwithinterest")
public class SpatialUnitPersonWithInterest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERSON_WITH_INTEREST_ID_GENERATOR1", sequenceName="spatial_unit_person_with_interest_seq")
	@GeneratedValue(strategy=GenerationType.AUTO , generator="PERSON_WITH_INTEREST_ID_GENERATOR1")
	private Long id;

	@Column(name="person_name")
	private String personName;

	private Long usin;
	private String middle_name;
	private String last_name;
	private String idcard_refrence;
	private String address;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gender", nullable = false)
	private Gender gender;
	private Date dob;

	public SpatialUnitPersonWithInterest() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Long getUsin() {
		return this.usin;
	}

	public void setUsin(Long usin) {
		this.usin = usin;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getIdcard_refrence() {
		return idcard_refrence;
	}

	public void setIdcard_refrence(String idcard_refrence) {
		this.idcard_refrence = idcard_refrence;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}



}