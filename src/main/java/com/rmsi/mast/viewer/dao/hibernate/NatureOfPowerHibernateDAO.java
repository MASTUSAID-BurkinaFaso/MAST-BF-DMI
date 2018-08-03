package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.NatureOfPower;

import com.rmsi.mast.viewer.dao.NatureOfPowerDao;


/**
 * 
 * @author Abhishek.Dasgupta
 *
 */
@Repository
public class NatureOfPowerHibernateDAO extends GenericHibernateDAO<NatureOfPower, Integer>
implements NatureOfPowerDao {
	private static final Logger logger = Logger
			.getLogger(NatureOfPowerHibernateDAO.class);
	@Override
	public NatureOfPower getNopById(int nopId) {
		try {

			String query = "select np.* from nature_of_power np inner join"
					+ " attribute_options ao on ao.parent_id = np.nop_id where "
					+ "ao.id =" + nopId;

			@SuppressWarnings("unchecked")
			List<NatureOfPower> natureofPowerObj = getEntityManager()
					.createNativeQuery(query, NatureOfPower.class).getResultList();

			if (natureofPowerObj != null && natureofPowerObj.size() > 0) {
				return natureofPowerObj.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
	
}




