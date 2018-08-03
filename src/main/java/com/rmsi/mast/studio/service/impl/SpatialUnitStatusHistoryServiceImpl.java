package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.SUnitHistoryDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;
import com.rmsi.mast.studio.service.SpatialUnitStatusHistoryService;


@Service
public class SpatialUnitStatusHistoryServiceImpl  implements SpatialUnitStatusHistoryService{

	
	@Autowired
	SUnitHistoryDAO sUnitHistoryDAO;
	
	@Override
	public SpatialUnitStatusHistory addStatusHistory(SpatialUnitStatusHistory objSpatialUnitStatusHistory) {
		return sUnitHistoryDAO.makePersistent(objSpatialUnitStatusHistory);
	}

}
