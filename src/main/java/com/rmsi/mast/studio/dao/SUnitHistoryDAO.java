
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;

public interface SUnitHistoryDAO extends GenericDAO<SpatialUnitStatusHistory, Long> {

	List<SpatialUnitStatusHistory> findHistoryByUsin(Long id);

	long findSFRnameByUsin(Long id, int workflowId, int workflowStatus);


	
}
