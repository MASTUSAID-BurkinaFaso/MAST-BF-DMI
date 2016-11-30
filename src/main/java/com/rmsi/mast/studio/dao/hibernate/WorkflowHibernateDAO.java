
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.WorkflowDAO;
import com.rmsi.mast.studio.domain.Workflow;


@Repository
public class WorkflowHibernateDAO extends GenericHibernateDAO<Workflow, Integer>
		implements WorkflowDAO {

	private static final Logger logger = Logger.getLogger(WorkflowHibernateDAO.class);
	@Override
	public List<Workflow> findSFRWorkflow() {
		
		
		
		try {
			Query query = getEntityManager().createQuery("Select wf from Workflow wf order by wf.workflowId asc");
			List<Workflow> workLst = query.getResultList();


			if(workLst.size() > 0){
				return workLst;
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
