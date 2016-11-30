package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.SUnitHistoryDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;


@Repository

public class SUnitHistoryHibernateDAO extends GenericHibernateDAO<SpatialUnitStatusHistory, Long>
		implements SUnitHistoryDAO {
	private static final Logger logger = Logger.getLogger(SUnitHistoryHibernateDAO.class);

	@Override
	public List<SpatialUnitStatusHistory> findHistoryByUsin(Long id) {
		
		try {
			Query query = getEntityManager().createQuery("Select sh from SpatialUnitStatusHistoryDto sh where sh.usin = :usin order by sh.status_hist_id desc");
			@SuppressWarnings("unchecked")
			List<SpatialUnitStatusHistory> spatialUnitHistory = query.setParameter("usin", id).getResultList();


			if(spatialUnitHistory.size() > 0){
				return spatialUnitHistory;
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

	@Override
	public long findSFRnameByUsin(Long id, int workflowId, int workflowStatus) {
		try {
			Query query = getEntityManager().createQuery("Select sh from SpatialUnitStatusHistory sh where sh.usin = :usin and sh.workflow_status_id= :workflowStatus and sh.workflow_id= :workflowId");
			@SuppressWarnings("unchecked")
			List<SpatialUnitStatusHistory> spatialUnitHistory = query.setParameter("usin", id).setParameter("workflowStatus", workflowStatus).setParameter("workflowId", workflowId).getResultList();


			if(spatialUnitHistory.size() > 0){
				return spatialUnitHistory.get(0).getUserid();
			}		
			else
			{
				return 0l;
			}
		} catch (Exception e) {

			logger.error(e);
			return 0l;
		}
	}
	
}
