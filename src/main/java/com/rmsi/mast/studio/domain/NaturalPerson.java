package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;
import com.rmsi.mast.studio.util.JsonDateSerializer2;

/**
 * Entity implementation class for Entity: NaturalPerson
 * 
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "natural_person")
@PrimaryKeyJoinColumn(name = "GID", referencedColumnName = "PERSON_GID")
public class NaturalPerson extends Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(nullable = false)
	private String alias;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gender", nullable = false)
	private Gender gender;

	private String mobile;

	private String identity;

	private int age;

	private String occupation;
	
	private boolean active;

	@Column(name = "occ_age_below")
	private int occAgeBelow;

	@Column(name = "tenure_relation")
	private String tenure_Relation;

	@Column(name = "household_relation")
	private String householdRelation;

	private String witness;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "marital_status")
	private MaritalStatus marital_status;

	@Column(name = "household_gid")
	private int householdGid;

	@ManyToOne
	@JoinColumn(name = "education")
	private EducationLevel education;
	private String administator;
	private String citizenship;
	private Boolean owner;
	private Boolean resident_of_village;
	@ManyToOne
	@JoinColumn(name="personsub_type")
	private PersonType personSubType;
	@ManyToOne
	@JoinColumn(name="citizenship_id")
	private Citizenship citizenship_id;
	private Date dob;
	private String address;
	private String idcard;
	private String fathername;
	private String mothername;
	@ManyToOne
	@JoinColumn(name = "nop_id")
	private NatureOfPower nop_id;
	private Date idcard_establishment_date;
	private String birthplace;
	private String idcard_origin;
	private Date mandate_issuance_date;
	private String mandate_location;
	
	

	public Citizenship getCitizenship_id() {
		return citizenship_id;
	}

	public void setCitizenship_id(Citizenship citizenship_id) {
		this.citizenship_id = citizenship_id;
	}

	public NaturalPerson() {
		super();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/*
	 * public int getGid() { return gid; }
	 * 
	 * public void setGid(int gid) { this.gid = gid; }
	 */

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public EducationLevel getEducation() {
		return education;
	}

	public void setEducation(EducationLevel education) {
		this.education = education;
	}

	public int getOccAgeBelow() {
		return occAgeBelow;
	}

	public void setOccAgeBelow(int occAgeBelow) {
		this.occAgeBelow = occAgeBelow;
	}

	public String getTenure_Relation() {
		return tenure_Relation;
	}

	public void setTenure_Relation(String tenure_Relation) {
		this.tenure_Relation = tenure_Relation;
	}

	public String getHouseholdRelation() {
		return householdRelation;
	}

	public void setHouseholdRelation(String householdRelation) {
		this.householdRelation = householdRelation;
	}

	public String getWitness() {
		return witness;
	}

	public void setWitness(String witness) {
		this.witness = witness;
	}

	public MaritalStatus getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(MaritalStatus marital_status) {
		this.marital_status = marital_status;
	}

	public int getHouseholdGid() {
		return householdGid;
	}

	public void setHouseholdGid(int householdGid) {
		this.householdGid = householdGid;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAdministator() {
		return administator;
	}

	public void setAdministator(String administator) {
		this.administator = administator;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public Boolean getOwner() {
		return owner;
	}

	public void setOwner(Boolean owner) {
		this.owner = owner;
	}

	public Boolean getResident_of_village() {
		return resident_of_village;
	}

	public void setResident_of_village(Boolean resident_of_village) {
		this.resident_of_village = resident_of_village;
	}

	public PersonType getPersonSubType() {
		return personSubType;
	}

	public void setPersonSubType(PersonType personSubType) {
		this.personSubType = personSubType;
	}
	@JsonSerialize(using = JsonDateSerializer2.class)
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public String getMothername() {
		return mothername;
	}

	public void setMothername(String mothername) {
		this.mothername = mothername;
	}

	public NatureOfPower getNop_id() {
		return nop_id;
	}

	public void setNop_id(NatureOfPower nop_id) {
		this.nop_id = nop_id;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getIdcard_establishment_date() {
		return idcard_establishment_date;
	}

	public void setIdcard_establishment_date(Date idcard_establishment_date) {
		this.idcard_establishment_date = idcard_establishment_date;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getIdcard_origin() {
		return idcard_origin;
	}

	public void setIdcard_origin(String idcard_origin) {
		this.idcard_origin = idcard_origin;
	}
	@JsonSerialize(using = JsonDateSerializer2.class)
	public Date getMandate_issuanceDate() {
		return mandate_issuance_date;
	}

	public void setMandate_issuanceDate(Date mandate_issuance_date) {
		this.mandate_issuance_date = mandate_issuance_date;
	}

	public String getMandate_location() {
		return mandate_location;
	}

	public void setMandate_location(String mandate_location) {
		this.mandate_location = mandate_location;
	}
	
	
}
