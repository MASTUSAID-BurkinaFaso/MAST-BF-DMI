package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ActionTools;
import com.rmsi.mast.viewer.dao.ActionToolsDao;



@Repository
public class ActionToolsHibernateDAO extends GenericHibernateDAO<ActionTools, Integer>
implements ActionToolsDao {
	private static final Logger logger = Logger.getLogger(ActionToolsHibernateDAO.class);

	@Override
	public List<ActionTools> findByRoleId(Integer id,String workId) {

		try {
			Query query = getEntityManager().createQuery("Select at from ActionTools at where at.role_id = :id and (at.workflow_list like :workId or at.workflow_list ='A') order by at.id asc");
			@SuppressWarnings("unchecked")
			List<ActionTools> actionTools = query.setParameter("id", id).setParameter("workId", "%,"+workId+",%").getResultList();


			if(actionTools.size() > 0){
				return actionTools;
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




