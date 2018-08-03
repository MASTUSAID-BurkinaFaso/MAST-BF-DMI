package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitExtension;
import com.rmsi.mast.viewer.dao.SpatialUnitExtensionDao;

/**
 * 
 * @author Abhishek.Dasgupta
 *
 */

@Repository
public class SpatialUnitExtensionHibernateDAO extends GenericHibernateDAO<SpatialUnitExtension, Long>
implements SpatialUnitExtensionDao {
	private static final Logger logger = Logger.getLogger(SpatialUnitExtensionHibernateDAO.class);

	@Override
	public SpatialUnitExtension findSpatialunitbyUsin(Long usin) {

		try {

			Query query = getEntityManager().createQuery("Select se from SpatialUnitExtension se where se.usin = :usin");
			@SuppressWarnings("unchecked")
			List<SpatialUnitExtension> spatialUnitextList = query.setParameter("usin", usin).getResultList();		

			if(spatialUnitextList.size() > 0){
				return (SpatialUnitExtension) spatialUnitextList.get(0);
			}		
			else
			{
				return new SpatialUnitExtension();
			}
		} catch (Exception e) {

			logger.error(e);
			return new SpatialUnitExtension();
		}

	}


	
}




