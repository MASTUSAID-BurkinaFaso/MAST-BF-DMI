
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;

/**
 * @author Vaibhav.Agarwal
 *
 */
public interface LandRecordsDao extends GenericDAO<SpatialUnitTable, Long> {

	List<SpatialUnitTable> findallspatialUnit(String defaultProject);

	boolean updateApprove(Long id);

	boolean rejectStatus(Long id);

	List<SpatialUnitTable> search(String usinStr, String ukaNumber,String
			projname, String dateto, String datefrom, Long status, Integer startpos);

	List<SpatialUnitTable> findSpatialUnitById(Long id);

	String findukaNumberByUsin(Long id);

	boolean updateFinal(Long id);

	boolean updateAdjudicated(Long id);


	boolean deleteSpatial(Long id);

	Integer searchSize(String usinStr, String ukaNumber, String projname,
			String dateto, String datefrom, Long status);

	List<SpatialUnitTable> getSpatialUnitByBbox(String bbox, String project_name);

	boolean findExistingHamlet(long hamlet_id);

	boolean deleteAllVertexLabel();

	boolean addAllVertexLabel(int k, String lat, String lon);

	List<Object> findAllVerteces();

	boolean actionUpdateWorkflow(Long id, Integer workflowId,Integer statusId);

	List<?> getSearchResult(String usin,String appno, String pvno, String apfr,
			String name, int apptype, int[] workids,String projname,Integer startpos,int status);

	int getSearchCount(String usin,String appno, String pvno, String apfr, String name,
			int apptype, int[] workids, String projectname,int status);

	List<Object> findparcelcountbytenure(String project);

	List<Object> findparcelcountbygender(String project,String tag, Integer villageId);

	List<Object> findregparcelcountbyTenure(String project,String tag, Integer villageId);

	List<Object> findRegistrytable(String project, String tag, Integer villageId);



}
