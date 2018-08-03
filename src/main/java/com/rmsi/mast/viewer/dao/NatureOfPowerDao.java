
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.NatureOfPower;

/**
 * 
 * @author Abhishek.Dasgupta
 *
 */
public interface NatureOfPowerDao extends GenericDAO<NatureOfPower, Integer> {

	NatureOfPower getNopById(int nopId);
	
}
