package com.rmsi.mast.viewer.dao.hibernate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ParcelType;
import com.rmsi.mast.viewer.dao.ParcelTypeDao;


@Repository
public class PacelTypeHibernateDAO extends GenericHibernateDAO<ParcelType, Integer>
implements ParcelTypeDao {
	private static final Logger logger = Logger
			.getLogger(PacelTypeHibernateDAO.class);

	@Override
	public ParcelType findParcelTypeById(int parcelId) {
		try {

			String query = "select pt.* from parcel_type pt inner join"
					+ " attribute_options ao on ao.parent_id = pt.parceltype_id where "
					+ "ao.id =" + parcelId;

			@SuppressWarnings("unchecked")
			List<ParcelType> parcelTypeObj = getEntityManager()
					.createNativeQuery(query, ParcelType.class).getResultList();

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




