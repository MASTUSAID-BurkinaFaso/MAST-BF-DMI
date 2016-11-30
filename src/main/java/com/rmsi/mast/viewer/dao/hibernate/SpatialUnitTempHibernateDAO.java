package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.viewer.dao.SpatialUnitTempDao;


@Repository
public class SpatialUnitTempHibernateDAO extends GenericHibernateDAO<SpatialUnitTemp, Long>
implements SpatialUnitTempDao {
	
	private static final Logger logger = Logger.getLogger(SpatialUnitTempHibernateDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<SpatialUnitTemp> findOrderedSpatialUnit(String defaultProject,int startfrom,int[]workID) {
		try {
			Query query = getEntityManager().createQuery("Select st from SpatialUnitTemp st where (st.project = :project_name and st.active=true) and st.workflow_id.workflowId in :workID order by st.usin desc");
			List<Integer> tmpMapped_uids = new LinkedList<Integer>(Arrays.asList(ArrayUtils.toObject(workID)));
			List<SpatialUnitTemp> spatialUnitlst = query.setParameter("project_name", defaultProject).setParameter("workID", tmpMapped_uids).setFirstResult(startfrom).setMaxResults(5).getResultList();		

			if(spatialUnitlst.size() > 0){
				return spatialUnitlst;
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
	public Integer AllSpatialUnitTemp(String defaultProject,int[]workID) {
		Integer count=0;
		List<Integer> tmpMapped_uids = new LinkedList<Integer>(Arrays.asList(ArrayUtils.toObject(workID)));
		try {
			Query query = getEntityManager().createQuery("Select count(*) from SpatialUnitTemp st where (st.project = :project_name and st.active=true) and st.workflow_id.workflowId in :workID");
			List<?> spatialUnitlst = query.setParameter("project_name", defaultProject).setParameter("workID", tmpMapped_uids).getResultList();		
			
			if(null!=spatialUnitlst&&spatialUnitlst.size() > 0){
				
				count=Integer.valueOf (spatialUnitlst.get(0).toString());
			}
		} catch (Exception e) {
			logger.error(e);
		}
	return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpatialUnitTemp> findSpatialUnitforUKAGeneration(String project) {
		try {
			Query query = getEntityManager().createQuery("Select st from SpatialUnitTemp st where st.project = :project_name and (st.active=true and st.propertyno is null) order by st.usin asc");
			List<SpatialUnitTemp> spatialUnitlst = query.setParameter("project_name", project).getResultList();		

			if(spatialUnitlst.size() > 0){
				return spatialUnitlst;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findUsinforUKAGeneration(String project, String hamletCode) {
		try {
			Query query = getEntityManager().createQuery("Select st.usin from SpatialUnitTemp st where (st.project = :project_name and st.hamlet_Id.hamletCode = :hamletCode ) and (st.active=true and (st.propertyno is null or st.propertyno='')) order by st.usin asc");
			List<Long> usinList = query.setParameter("project_name", project).setParameter("hamletCode", hamletCode).getResultList();		

			if(usinList.size() > 0){
				return usinList;
			}		
			else
			{
				return new ArrayList<Long>();
			}
		} catch (Exception e) {

			logger.error(e);
			return new ArrayList<Long>();
		}
	}

	@Override
	public boolean updateUKAnumber(Long long1, String uka) {

		try {
		
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTemp st SET st.propertyno= :uka  where st.usin = :usin");
			int updateFinal = query.setParameter("uka", uka).setParameter("usin",long1).executeUpdate();	

			if(updateFinal>0)
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}
	
}




