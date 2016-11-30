
package com.rmsi.mast.studio.dao;
import java.util.List;

import com.rmsi.mast.studio.domain.Workflow;

public interface WorkflowDAO extends GenericDAO<Workflow, Integer> {

	List<Workflow> findSFRWorkflow();
	
	
	
}
