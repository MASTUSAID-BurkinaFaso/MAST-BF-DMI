package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.MutationType;
import com.rmsi.mast.viewer.dao.MutationTypeDao;


/**
 * 
 * @author Abhishek.Dasgupta
 *
 */
@Repository
public class MutationTypeHibernateDAO extends GenericHibernateDAO<MutationType, Integer>
implements MutationTypeDao {
	private static final Logger logger = Logger
			.getLogger(MutationTypeHibernateDAO.class);

	@Override
	public MutationType findMtById(int mtId) {
		try {

			String query = "select mt.* from mutation_type mt inner join"
					+ " attribute_options ao on ao.parent_id = mt.mt_id where "
					+ "ao.id =" + mtId;

			@SuppressWarnings("unchecked")
			List<MutationType> mutationTypeObj = getEntityManager()
					.createNativeQuery(query, MutationType.class).getResultList();

			if (mutationTypeObj != null && mutationTypeObj.size() > 0) {
				return mutationTypeObj.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
	
}




