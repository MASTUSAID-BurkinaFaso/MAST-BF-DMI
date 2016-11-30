package com.rmsi.mast.studio.domain;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "title_existing")
public class TitleExisting implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String tile_name;
	private String title_name_fr;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTile_name() {
		return tile_name;
	}
	public void setTile_name(String tile_name) {
		this.tile_name = tile_name;
	}
	public String getTitle_name_fr() {
		return title_name_fr;
	}
	public void setTitle_name_fr(String title_name_fr) {
		this.title_name_fr = title_name_fr;
	}

	

}