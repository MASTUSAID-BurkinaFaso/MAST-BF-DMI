
package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitExtension;


public interface SpatialUnitExtensionDao extends GenericDAO<SpatialUnitExtension, Long> {

	SpatialUnitExtension findSpatialunitbyUsin(Long usin);

}
