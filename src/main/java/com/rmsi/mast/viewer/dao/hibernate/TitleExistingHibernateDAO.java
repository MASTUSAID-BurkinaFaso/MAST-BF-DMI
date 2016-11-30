package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ParcelType;
import com.rmsi.mast.studio.domain.TitleExisting;
import com.rmsi.mast.viewer.dao.TitleExistingDao;



@Repository
public class TitleExistingHibernateDAO extends GenericHibernateDAO<TitleExisting, Integer>
implements TitleExistingDao {
	private static final Logger logger = Logger.getLogger(TitleExistingHibernateDAO.class);

	@Override
	public TitleExisting findParcelTypeById(int parseInt) {
		try {

			String query = "select pt.* from title_existing pt inner join"
					+ " attribute_options ao on ao.parent_id = pt.id where "
					+ "ao.id =" + parseInt;

			@SuppressWarnings("unchecked")
			List<TitleExisting> parcelTypeObj = getEntityManager()
					.createNativeQuery(query, TitleExisting.class).getResultList();

			if (parcelTypeObj != null && parcelTypeObj.size() > 0) {
				return parcelTypeObj.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

		
}




