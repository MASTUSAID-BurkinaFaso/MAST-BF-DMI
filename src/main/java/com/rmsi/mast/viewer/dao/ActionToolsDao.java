/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ActionTools;


public interface ActionToolsDao extends GenericDAO<ActionTools, Integer> {

	List<ActionTools> findByRoleId(Integer id,String workId);
}
