
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProvinceDAO;
import com.rmsi.mast.studio.domain.Province;


@Repository
public class ProvinceHibernateDAO extends GenericHibernateDAO<Province, Integer>
		implements ProvinceDAO {

	private static final Logger logger = Logger.getLogger(ProvinceHibernateDAO.class);
	@Override
	public List<Province> findByRegionId(Integer regionId) {
		
		
		try {

			Query query = getEntityManager().createQuery("Select p from Province p where p.region.regionId = :regionId");
			@SuppressWarnings("unchecked")
			List<Province> provincelst = query.setParameter("regionId", regionId).getResultList();		

			if(provincelst.size() > 0){
				return provincelst;
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
