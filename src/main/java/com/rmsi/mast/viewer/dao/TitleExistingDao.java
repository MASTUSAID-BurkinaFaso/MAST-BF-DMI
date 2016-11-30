/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.TitleExisting;


public interface TitleExistingDao extends GenericDAO<TitleExisting, Integer> {

	TitleExisting findParcelTypeById(int parseInt);

	
}
