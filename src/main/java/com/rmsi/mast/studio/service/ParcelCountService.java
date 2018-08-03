package com.rmsi.mast.studio.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.ParcelCount;

public interface ParcelCountService {

	public ParcelCount findParcelCountByTypeAndProjectName(String Type,String projectName);
	
	
	@Transactional
	public ParcelCount addParcelCount(ParcelCount ParcelCount);
}
