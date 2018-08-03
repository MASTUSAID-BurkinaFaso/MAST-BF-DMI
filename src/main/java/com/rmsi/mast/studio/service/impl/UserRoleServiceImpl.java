package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	
	
	@Autowired
	UserRoleDAO  userRoleDAO;
	
	
	@Override
	public UserRole getUserRoleByUser(int userid) {
		return userRoleDAO.getUserRoleByUserID(userid);
	}

}
