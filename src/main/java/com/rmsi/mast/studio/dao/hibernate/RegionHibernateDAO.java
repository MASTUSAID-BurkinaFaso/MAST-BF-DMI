
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.RegionDAO;
import com.rmsi.mast.studio.domain.Province;
import com.rmsi.mast.studio.domain.Region;
import com.rmsi.mast.viewer.dao.hibernate.LandRecordsHibernateDAO;


@Repository
public class RegionHibernateDAO extends GenericHibernateDAO<Region, Integer>
		implements RegionDAO {
	private static final Logger logger = Logger.getLogger(RegionHibernateDAO.class);
	@Override
	public List<Region> findBycountryId(Integer countryId) {
		
		
		try {

			Query query = getEntityManager().createQuery("Select p from Region p where p.country_id.country_id = :countryId");
			@SuppressWarnings("unchecked")
			List<Region> regionlst = query.setParameter("countryId", countryId.intValue()).getResultList();		

			if(regionlst.size() > 0){
				return regionlst;
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
