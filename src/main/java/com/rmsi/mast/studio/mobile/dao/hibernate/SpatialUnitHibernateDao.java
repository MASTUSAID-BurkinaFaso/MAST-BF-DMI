/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class SpatialUnitHibernateDao extends
		GenericHibernateDAO<SpatialUnit, Long> implements SpatialUnitDao {

	private static final Logger logger = Logger
			.getLogger(SpatialUnitHibernateDao.class);

	@Override
	public SpatialUnit addSpatialUnit(SpatialUnit spatialUnit) {

		try {
			return makePersistent(spatialUnit);

		} catch (Exception ex) {
			System.out.println("Exception while adding data...." + ex);
			logger.error(ex);
			throw ex;
		}
	}

	@Override
	public List<SpatialUnit> getSpatialUnitByProject(String projectId) {
		String query = "select s from SpatialUnit s where s.project = :projectId";

		try {
			@SuppressWarnings("unchecked")
			List<SpatialUnit> spatialUnit = getEntityManager()
					.createQuery(query).setParameter("projectId", projectId)
					.getResultList();

			if (!spatialUnit.isEmpty()) {
				return spatialUnit;
			}
		} catch (Exception ex) {
			System.out
					.println("Exception while fetching the data from data base "
							+ ex);
			logger.error(ex);
		}
		return null;
	}

	@Override
	public SpatialUnit findByImeiandTimeStamp(String imeiNumber, Date surveyDate) {

		String query = "select s from SpatialUnit s where s.imeiNumber =:imeiNumber"
				+ " and s.surveyDate =:surveyDate";

		try {

			@SuppressWarnings("unchecked")
			List<SpatialUnit> spatialUnit = getEntityManager()
					.createQuery(query).setParameter("imeiNumber", imeiNumber)
					.setParameter("surveyDate", surveyDate).getResultList();

			if (spatialUnit.size() > 0) {
				return spatialUnit.get(0);
			}
		} catch (Exception ex) {
			System.out.println("Exception while fetching data: : : " + ex);
			logger.error(ex);
			throw ex;
		}
		return null;
	}

	@Override
	public SpatialUnit getSpatialUnitByUsin(long usin) {

		String query = "select s from SpatialUnit s where s.usin =:usin";

		try {

			@SuppressWarnings("unchecked")
			List<SpatialUnit> spatialUnits = getEntityManager()
					.createQuery(query).setParameter("usin", usin)
					.getResultList();

			if (spatialUnits.size() > 0) {
				return spatialUnits.get(0);
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return null;
	}

	

	@Override
	@SuppressWarnings("unchecked")
	public List<SpatialUnit> findSpatialUnitByStatusId(String projectId,
			int statusId) {
		ArrayList<Integer> staList=new ArrayList<Integer>();
		staList.add(6);
		staList.add(7);
		String query = "select su from SpatialUnit su where "
				+ "su.status.workflowStatusId in :statusId and "
				+ "su.project =:projectId";
		

		try {
			List<SpatialUnit> spatialUnitList=null;
			if(statusId!=7){
				
				 spatialUnitList = getEntityManager()
						.createQuery(query).setParameter("statusId", statusId)
						.setParameter("projectId", projectId).getResultList();
	
				
			}

			else{
				
				 spatialUnitList = getEntityManager()
						.createQuery(query).setParameter("statusId", staList)
						.setParameter("projectId", projectId).getResultList();
	
				
			}
			if (spatialUnitList.size() > 0) {
				return spatialUnitList;
			}
		} catch (Exception ex) {
			logger.error(ex);
			System.out.println("Exception while fetching SPATIAL UNIT:::: "+ ex);
		}
		return new ArrayList<SpatialUnit>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpatialUnit> findBbox(long id,String project) {

		List<SpatialUnit> bbox =new ArrayList<SpatialUnit>();
		try {

			Query query = getEntityManager().createQuery("select st from SpatialUnit st where st.usin=:usin").setParameter("usin", id);
			
			bbox = query.getResultList();
		} catch (Exception e) {
			logger.error(e);
		}

		return bbox;			

	}



	
}
