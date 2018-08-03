package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.Commune;

public interface CommuneService {

	public List<Commune> getAllCommune(); 
	public Commune findCommuneById(Integer id);
	
}
