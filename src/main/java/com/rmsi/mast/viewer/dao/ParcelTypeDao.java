/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ParcelType;
/**
 * 
 * @author Abhishek.Dasgupta
 *
 */

public interface ParcelTypeDao extends GenericDAO<ParcelType, Integer> {

	ParcelType findParcelTypeById(int parcelId);


	
}
