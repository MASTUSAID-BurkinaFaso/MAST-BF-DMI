
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.VillageDAO;
import com.rmsi.mast.studio.domain.Commune;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.Village;


@Repository
public class VillageHibernateDAO extends GenericHibernateDAO<Village, Integer>
		implements VillageDAO {
	private static final Logger logger = Logger.getLogger(VillageHibernateDAO.class);
	@Override
	public List<Village> findBycommuneId(Integer communeId) {
		
		
		try {

			Query query = getEntityManager().createQuery("Select p from Village p where p.commune.communeId = :communeId and p.active=true");
			@SuppressWarnings("unchecked")
			List<Village> villagelst = query.setParameter("communeId", communeId).getResultList();		

			if(villagelst.size() > 0){
				return villagelst;
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
	
	
	
public List<Village> findactiveVillage() {
		
		
		try {

			Query query = getEntityManager().createQuery("Select p from Village p where p.active=true");
			@SuppressWarnings("unchecked")
			List<Village> villagelst = query.getResultList();		

			if(villagelst.size() > 0){
				return villagelst;
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
public Integer searchSize(String villageName) {
	Integer count=0;
	try
	{
		StringBuffer queryStr = new StringBuffer("Select v from Village v where v.active = true ");
		
		if(villageName!="")
		{
			queryStr.append("and lower(v.villageName) like :villageName");
		}
		Query query = getEntityManager().createQuery(queryStr.toString());
		
		if(villageName!="")
		{
			query.setParameter("villageName","%" +villageName.trim().toLowerCase()+"%");
		}
		
	
		@SuppressWarnings("unchecked")
		List<?> spatialUnit = query.getResultList();	
		if(spatialUnit.size() > 0){
			count=Integer.valueOf (spatialUnit.size());
		}		

		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return count;
	
	
}



@Override
public List<Village> searchVillage(String villageName, Integer startpos) {
	
	try
	{
		StringBuffer queryStr = new StringBuffer("Select v from Village v where v.active = true ");
		
		if(villageName!="")
		{
			queryStr.append("and lower(v.villageName) like :villageName");
		}
		Query query = getEntityManager().createQuery(queryStr.toString());
		
		if(villageName!="")
		{
			query.setParameter("villageName","%" +villageName.trim().toLowerCase()+"%");
		}
		
		@SuppressWarnings("unchecked")
		List<Village> user =	query.setFirstResult(startpos).setMaxResults(10).getResultList();	
		
		if(user.size() > 0)
		{
			return user;
		}
		else
		{	return null;
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return null;
}



@Override
public Village findVillageById(Integer id) {
	try {
		@SuppressWarnings("unchecked")
		List<Village> village = getEntityManager().createQuery("Select v from Village v where v.active = true and  v.villageId = :id").setParameter("id", id).getResultList();
		if(village.size() > 0)
			return village.get(0);
		else
			return null;
	} catch (Exception e) {
		logger.error(e);
	}
	return null;
}



@Override
public boolean deleteVillageByID(Integer id) {
	try{

		String qry = "UPDATE Village v SET v.active = false  where v.villageId = :id and v.active = true";
		Query query = getEntityManager().createQuery(qry).setParameter("id", id);
		int count = query.executeUpdate();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}catch(Exception e){
		logger.error(e);
		return false;
	}
}

	
}
