
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.CommuneDAO;
import com.rmsi.mast.studio.domain.Commune;
import com.rmsi.mast.studio.domain.User;


@Repository
public class CommuneHibernateDAO extends GenericHibernateDAO<Commune, Integer>
		implements CommuneDAO {

	
	private static final Logger logger = Logger.getLogger(CommuneHibernateDAO.class);
	
	@Override
	public List<Commune> findbyProvinceId(Integer provincenId) {
		
		
		try {

			Query query = getEntityManager().createQuery("Select p from Commune p where p.province.provinceId = :provinceId");
			@SuppressWarnings("unchecked")
			List<Commune> communelst = query.setParameter("provinceId", provincenId).getResultList();		

			if(communelst.size() > 0){
				return communelst;
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
	public List<Commune> getAllCommune(){
		@SuppressWarnings("unchecked")
		List<Commune> lstCommune =
				getEntityManager().createQuery("Select c from Commune c order by c.communeId asc").getResultList();
		if(lstCommune.size() > 0)
			return lstCommune;
		else
			return null;
	}

	@Override
	public Commune findCommuneById(Integer id) {
		try {

			Query query = getEntityManager().createQuery("Select p from Commune p where p.communeId = :id");
			@SuppressWarnings("unchecked")
			List<Commune> communelst = query.setParameter("id", id).getResultList();		

			if(communelst.size() > 0){
				return communelst.get(0);
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
