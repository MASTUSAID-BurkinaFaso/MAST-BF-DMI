
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Province;

public interface ProvinceDAO extends GenericDAO<Province, Integer> {

	List<Province> findByRegionId(Integer regionId);}
