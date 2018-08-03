/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;

/**
 * 
 * @author Abhishek.Dasgupta
 *
 */

public interface SpatialUnitPersonWithInterestDao extends GenericDAO<SpatialunitPersonwithinterest, Long> {

	List<SpatialunitPersonwithinterest> findByUsin(Long usin);
	
}
