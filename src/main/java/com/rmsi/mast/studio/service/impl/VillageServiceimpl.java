package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.VillageDAO;
import com.rmsi.mast.studio.domain.Village;
import com.rmsi.mast.studio.service.VillageService;


@Service
public class VillageServiceimpl  implements VillageService{

	@Autowired
	VillageDAO villageDAO;

	@Override
	public Integer searchSize(String villageName) {
		return villageDAO.searchSize(villageName);
	}

	@Override
	public List<Village> searchVillage(String villageName, Integer startpos) {
		return villageDAO.searchVillage(villageName, startpos);
	}

	@Override
	public Village findVillageById(Integer id) {
		return villageDAO.findVillageById(id);
	}

	@Override
	public Village addVillage(Village village) {
		return villageDAO.makePersistent(village);
	}

	@Override
	public boolean deleteVillageByID(Integer id) {
		return villageDAO.deleteVillageByID(id);
	}
	
	
	
	
	
	
	
	
}
