package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.CommuneDAO;
import com.rmsi.mast.studio.domain.Commune;
import com.rmsi.mast.studio.service.CommuneService;

@Service
public class CommuneServiceImpl implements CommuneService {

	@Autowired
	CommuneDAO  communeDAO;

	@Override
	public List<Commune> getAllCommune() {
		return communeDAO.getAllCommune();
	}

	@Override
	public Commune findCommuneById(Integer id) {
	 return communeDAO.findCommuneById(id);
	}
	
}
