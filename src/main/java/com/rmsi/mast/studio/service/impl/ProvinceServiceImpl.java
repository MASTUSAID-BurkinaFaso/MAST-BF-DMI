package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProvinceDAO;
import com.rmsi.mast.studio.domain.Province;
import com.rmsi.mast.studio.service.ProvinceService;

@Service
public class ProvinceServiceImpl  implements ProvinceService{


	@Autowired
	ProvinceDAO provinceDAO;

	@Override
	public List<Province> findByRegionId(Integer regionId) {
		return provinceDAO.findByRegionId(regionId);
	}

	@Override
	public List<Province> findAllProvince() {
		return provinceDAO.findAllProvince();
	}

	@Override
	public Province findProvinceById(Integer ProvinceId) {
		return provinceDAO.findProvinceById(ProvinceId);
	}
	
	

}
