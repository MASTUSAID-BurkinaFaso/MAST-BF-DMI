
package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Commune;

public interface CommuneDAO extends GenericDAO<Commune, Integer> {

	List<Commune> findbyProvinceId(Integer provincenId);
    List<Commune> getAllCommune();
    public Commune findCommuneById(Integer id);
}