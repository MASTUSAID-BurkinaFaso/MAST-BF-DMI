package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.Province;

public interface ProvinceService {

	List<Province> findByRegionId(Integer regionId);
	List<Province> findAllProvince();
	Province findProvinceById(Integer ProvinceId);
}
