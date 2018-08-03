package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.ParcelCount;
import com.rmsi.mast.studio.service.ParcelCountService;
import com.rmsi.mast.viewer.dao.ParcelCountDao;
import com.rmsi.mast.viewer.dao.ParcelTypeDao;

@Service
public class ParcelCountServiceImpl implements ParcelCountService {

	
	@Autowired
	ParcelCountDao  parcelCountDao;
	
	
	@Override
	public ParcelCount findParcelCountByTypeAndProjectName(String Type,String projectName) {
		
		return parcelCountDao.findParcelCountByTypeAndProjectName(Type, projectName);
		
	}


	@Override
	@Transactional
	public ParcelCount addParcelCount(ParcelCount ParcelCount) {
		// TODO Auto-generated method stub
		return parcelCountDao.makePersistent(ParcelCount);
	} 

}
