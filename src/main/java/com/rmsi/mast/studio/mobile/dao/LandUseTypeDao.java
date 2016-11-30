/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LandUseType;

/**
 * @author shruti.thakur
 *
 */
public interface LandUseTypeDao extends GenericDAO<LandUseType, Integer>{

	LandUseType getLandUseTypeById(int landUseTypeId);

	List<LandUseType> findEntriesById(String existing_use);
	
	Integer getExistingById(int landUseTypeId);
	
}
