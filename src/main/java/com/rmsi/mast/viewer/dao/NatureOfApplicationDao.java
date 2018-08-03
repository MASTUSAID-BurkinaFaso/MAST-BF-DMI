/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.NatureOfApplication;
/**
 * 
 * @author Abhishek.Dasgupta
 *
 */

public interface NatureOfApplicationDao extends GenericDAO<NatureOfApplication, Integer> {

	NatureOfApplication findNoaByID(int noaId);
	
}
