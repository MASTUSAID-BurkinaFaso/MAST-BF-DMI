package com.rmsi.mast.studio.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;

public interface SpatialUnitStatusHistoryService {

	
	@Transactional
	public  SpatialUnitStatusHistory addStatusHistory(SpatialUnitStatusHistory objSpatialUnitStatusHistory);
	
}
