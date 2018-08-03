package com.rmsi.mast.viewer.dao.hibernate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.NatureOfApplication;
import com.rmsi.mast.viewer.dao.NatureOfApplicationDao;

/**
 * 
 * @author Abhishek.Dasgupta
 *
 */
@Repository
public class NatureOfApplicationHibernateDAO extends GenericHibernateDAO<NatureOfApplication, Integer>
implements NatureOfApplicationDao {
	private static final Logger logger = Logger
			.getLogger(NatureOfApplicationHibernateDAO.class);

	@Override
	public NatureOfApplication findNoaByID(int noaId) {
		try {

			String query = "select na.* from nature_of_application na inner join"
					+ " attribute_options ao on ao.parent_id = na.noa_id where "
					+ "ao.id =" + noaId;

			@SuppressWarnings("unchecked")
			List<NatureOfApplication> natureofAppObj = getEntityManager()
					.createNativeQuery(query, NatureOfApplication.class).getResultList();

			if (natureofAppObj != null && natureofAppObj.size() > 0) {
				return natureofAppObj.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
	
}




