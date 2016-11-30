
package com.rmsi.mast.studio.dao;



import java.util.List;

import com.rmsi.mast.studio.domain.Region;

public interface RegionDAO extends GenericDAO<Region, Integer> {

	List<Region> findBycountryId(Integer countryId);}
