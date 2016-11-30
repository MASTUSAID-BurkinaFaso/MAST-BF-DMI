/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.MutationType;


public interface MutationTypeDao extends GenericDAO<MutationType, Integer> {

	MutationType findMtById(int mtId);
	
}
