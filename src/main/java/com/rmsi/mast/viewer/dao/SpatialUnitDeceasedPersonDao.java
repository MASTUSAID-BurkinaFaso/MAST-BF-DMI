/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;

/**
 * 
 * @author Abhishek.Dasgupta
 *
 */
public interface SpatialUnitDeceasedPersonDao extends GenericDAO<SpatialunitDeceasedPerson, Long> {

	List<SpatialunitDeceasedPerson> findPersonByUsin(Long usin);

	SpatialunitDeceasedPerson addDeceasedPerson(List<SpatialunitDeceasedPerson> deceasedPersonList,
			long usin);
	
}
