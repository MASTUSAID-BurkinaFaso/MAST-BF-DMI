
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.VillageDAO;
import com.rmsi.mast.studio.domain.Commune;
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

	
}
