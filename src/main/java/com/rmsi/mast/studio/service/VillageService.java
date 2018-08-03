package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Village;

public interface VillageService {

	public Integer searchSize(String villageName);
	public List<Village> searchVillage(String villageName,Integer startpos);
	public Village findVillageById(Integer id);
	
	@Transactional
	public Village addVillage(Village village);
	
	@Transactional
	public boolean deleteVillageByID(Integer id);
}
