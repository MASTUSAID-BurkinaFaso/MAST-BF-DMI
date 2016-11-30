
package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "action_tools")
public class ActionTools implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String name;
	private int role_id;
	private String workflow_list;
	private String  name_fr;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getWorkflow_list() {
		return workflow_list;
	}
	public void setWorkflow_list(String workflow_list) {
		this.workflow_list = workflow_list;
	}
	public String getName_fr() {
		return name_fr;
	}
	public void setName_fr(String name_fr) {
		this.name_fr = name_fr;
	}
	

}