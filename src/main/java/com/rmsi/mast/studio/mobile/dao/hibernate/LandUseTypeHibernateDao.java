/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;

/**
 * @author shruti.thakur
 *
 */
@Repository
public class LandUseTypeHibernateDao extends
		GenericHibernateDAO<LandUseType, Integer> implements LandUseTypeDao {
	private static final Logger logger = Logger.getLogger(GenderHibernateDao.class);

	@Override
	public LandUseType getLandUseTypeById(int landUseTypeId) {

		try {
			String query = "select lu.* from land_use_type lu inner join "
					+ "attribute_options ao on ao.parent_id = lu.use_type_id where "
					+ "ao.id = " + landUseTypeId;

			@SuppressWarnings("unchecked")
			List<LandUseType> landUseType = getEntityManager()
					.createNativeQuery(query, LandUseType.class)
					.getResultList();

			if (landUseType != null && landUseType.size() > 0) {
				return landUseType.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
	
	
	@Override
	public Integer getExistingById(int landUseTypeId) {

		try {
			String query = "select lu.* from land_use_type lu inner join "
					+ "attribute_options ao on ao.parent_id = lu.use_type_id where "
					+ "ao.id = " + landUseTypeId;

			@SuppressWarnings("unchecked")
			List<LandUseType> landUseType = getEntityManager()
					.createNativeQuery(query, LandUseType.class)
					.getResultList();

			if (landUseType != null && landUseType.size() > 0) {
				return landUseType.get(0).getLandUseTypeId();
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
	

	@Override
	public List<LandUseType> findEntriesById(String existing_use) {

		try {
					 
			String query = "Select * from land_use_type where use_type_id in ("+existing_use+")";
			Query executeQuery = getEntityManager().createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<LandUseType> landtypeList = executeQuery.getResultList();

			if(landtypeList.size() > 0){
				return landtypeList;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {

			logger.error(e);
			return null;
		}

	}

}
