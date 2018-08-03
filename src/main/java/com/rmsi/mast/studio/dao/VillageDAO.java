
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Village;

public interface VillageDAO extends GenericDAO<Village, Integer> {

	public List<Village> findBycommuneId(Integer communeId);
	public List<Village> findactiveVillage();
	public Integer searchSize(String villageName);
	public List<Village> searchVillage(String villageName,Integer startpos);
	public Village findVillageById(Integer id);
	public boolean deleteVillageByID(Integer id);
}
