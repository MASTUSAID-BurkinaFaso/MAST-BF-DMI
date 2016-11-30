
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Village;

public interface VillageDAO extends GenericDAO<Village, Integer> {

	List<Village> findBycommuneId(Integer communeId);

	List<Village> findactiveVillage();
	
}
