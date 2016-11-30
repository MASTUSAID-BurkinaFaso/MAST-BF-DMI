package com.rmsi.mast.viewer.dao.hibernate;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ParcelCount;
import com.rmsi.mast.viewer.dao.ParcelCountDao;



@Repository
public class ParcelCountHibernateDAO extends GenericHibernateDAO<ParcelCount, Integer>
implements ParcelCountDao {
	private static final Logger logger = Logger.getLogger(ParcelCountHibernateDAO.class);


	
	
}




