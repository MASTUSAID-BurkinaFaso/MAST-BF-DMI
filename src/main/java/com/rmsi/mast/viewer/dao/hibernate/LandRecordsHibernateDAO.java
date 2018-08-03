package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.custom.dto.LandRecordDto;
import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.viewer.dao.LandRecordsDao;


/**
 * 
 * @author Vaibhav.Agarwal
 *
 */

@Repository
public class LandRecordsHibernateDAO extends GenericHibernateDAO<SpatialUnitTable, Long>
implements LandRecordsDao {
	private static final Logger logger = Logger.getLogger(LandRecordsHibernateDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<SpatialUnitTable> findallspatialUnit(String defaultProject) {

		try {
			Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.project = :project_name and su.active=true order by su.usin desc");
			List<SpatialUnitTable> spatialUnit = query.setParameter("project_name", defaultProject).getResultList();


			if(spatialUnit.size() > 0){
				return spatialUnit;
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

	@Override
	public boolean updateApprove(Long id) {

		try {
			int finalstatus=4;
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
			int updateFinal = query.setParameter("statusId", finalstatus).setParameter("usin",id).executeUpdate();	

			if(updateFinal>0)
			{
				return true;
			}


		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}

	@Override
	public boolean rejectStatus(Long id) {

		try {
			int finalstatus=5;
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
			int rejectStatus = query.setParameter("statusId", finalstatus).setParameter("usin",id).executeUpdate();	

			if(rejectStatus>0)
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}

	@Override
	public List<SpatialUnitTable> search(String usinStr, String ukaNumber,String projname , String dateto, String datefrom,Long status,Integer startpos) 
	{

		ArrayList<Long> newUsin=new ArrayList<Long>();
		try 
		{
			// and (str(su.surveyDate) BETWEEN :stDate AND :edDate) and su.project = :project_name ")
			StringBuffer queryStr = new StringBuffer("Select su from SpatialUnitTable su where su.project = :project_name and su.active=true ");
			if(ukaNumber!="")
			{
				queryStr.append("and su.propertyno like :propertyno ");
			}
			if(usinStr!="")
			{

				queryStr.append("and su.usin in :usin ");

			}
			if(!dateto.isEmpty() || !datefrom.isEmpty())
			{
				queryStr.append("and (str(su.surveyDate) BETWEEN :stDate AND :edDate) ");
			}
			if(status!=0)
			{
				queryStr.append("and su.status.workflowStatusId=:workflowStatusId ");
			}
			//added 4-sep-15
			queryStr.append("order by su.usin desc ");
			Query query = getEntityManager().createQuery(queryStr.toString());
			query.setParameter("project_name", projname);

			if(ukaNumber!="")
			{
				query.setParameter("propertyno","%" +ukaNumber.trim()+"%");
			}
			if(usinStr!="")
			{
				for (String retval: usinStr.split(",")){
					newUsin.add(Long.parseLong(retval.trim()));
				}

				query.setParameter("usin",newUsin);
			}
			if(!dateto.isEmpty() || !datefrom.isEmpty())
			{
				query.setParameter("stDate", datefrom).setParameter("edDate",dateto);
			}
			if(status!=0)
			{
				query.setParameter("workflowStatusId", status.intValue());
			}

			System.out.println(queryStr.toString());

			@SuppressWarnings("unchecked")
			List<SpatialUnitTable> spatialUnit = query.setFirstResult(startpos).setMaxResults(5).getResultList();	
			if(spatialUnit.size() > 0){
				return spatialUnit;
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

	@Override
	public List<SpatialUnitTable> findSpatialUnitById(Long id) {

		try {

			Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.usin = :usin and su.active = true");
			@SuppressWarnings("unchecked")
			List<SpatialUnitTable> spatialUnitlst = query.setParameter("usin", id).getResultList();		

			if(spatialUnitlst.size() > 0){
				return spatialUnitlst;
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

	@Override
	public String findukaNumberByUsin(Long id) {


		try {
			Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.usin = :usin and su.active = true");
			@SuppressWarnings("unchecked")
			List<SpatialUnitTable> spatialUnitlst = query.setParameter("usin", id).getResultList();		
			String uka="";

			if(spatialUnitlst.size() > 0){
				return uka;
			}		
			else
			{
				return "";
			}
		} catch (Exception e) {

			logger.error(e);
			return "";
		}

	}

	@Override
	public boolean updateFinal(Long id) {

		try {
			int finalstatus=7;
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
			int updateFinal = query.setParameter("statusId", finalstatus).setParameter("usin",id).executeUpdate();	

			if(updateFinal>0)
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}

	@Override
	public boolean updateAdjudicated(Long id) {

		try {
			int finalstatus=2;
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.status.workflowStatusId = :statusId  where su.usin = :usin");
			int updateFinal = query.setParameter("statusId", finalstatus).setParameter("usin",id).executeUpdate();	

			if(updateFinal>0)
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}



	@Override
	public boolean deleteSpatial(Long id) {

		try {
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.active = false  where su.usin = :usin");
			int updateFinal = query.setParameter("usin",id).executeUpdate();	

			if(updateFinal>0)
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}
		return false;
	}

	@Override
	public Integer searchSize(String usinStr, String ukaNumber,
			String projname, String dateto, String datefrom, Long status) {

		Integer count=0;

		ArrayList<Long> newUsin=new ArrayList<Long>();
		try 
		{
			// and (str(su.surveyDate) BETWEEN :stDate AND :edDate) and su.project = :project_name ")
			StringBuffer queryStr = new StringBuffer("Select count(*) from SpatialUnitTable su where su.project = :project_name and su.active=true ");
			if(ukaNumber!="")
			{
				queryStr.append("and su.propertyno like :propertyno ");
			}
			if(usinStr!="")
			{

				queryStr.append("and su.usin in :usin ");

			}
			if(!dateto.isEmpty() || !datefrom.isEmpty())
			{
				queryStr.append("and (str(su.surveyDate) BETWEEN :stDate AND :edDate) ");
			}
			if(status!=0)
			{
				queryStr.append("and su.status.workflowStatusId=:workflowStatusId ");
			}

			Query query = getEntityManager().createQuery(queryStr.toString());
			query.setParameter("project_name", projname);

			if(ukaNumber!="")
			{
				query.setParameter("propertyno","%" +ukaNumber+"%");
			}
			if(usinStr!="")
			{
				for (String retval: usinStr.split(",")){
					newUsin.add(Long.parseLong(retval));
				}

				query.setParameter("usin",newUsin);
			}
			if(!dateto.isEmpty() || !datefrom.isEmpty())
			{
				query.setParameter("stDate", datefrom).setParameter("edDate",dateto);
			}
			if(status!=0)
			{
				query.setParameter("workflowStatusId", status.intValue());
			}

			System.out.println(queryStr.toString());

			@SuppressWarnings("unchecked")
			List<?> spatialUnit = query.getResultList();	
			if(spatialUnit.size() > 0){
				count=Integer.valueOf (spatialUnit.get(0).toString());
			}		

		} catch (Exception e) {

			logger.error(e);

		}

		return count;

	}

	@Override
	public List<SpatialUnitTable> getSpatialUnitByBbox(String bbox,String project_name) {

		List<SpatialUnitTable> spatialUnit =new ArrayList<SpatialUnitTable>();
		try {

			Query query = getEntityManager().createNativeQuery("SELECT * from spatial_unit where ST_WITHIN(the_geom, ST_MakeEnvelope("+bbox+",4326)) and (project_name="+"'"+project_name+"'"+" and active=true) ",SpatialUnitTable.class);
			spatialUnit = query.getResultList();
		} catch (Exception e) {
			logger.error(e);
		}

		return spatialUnit;			

	}

	@Override
	public boolean findExistingHamlet(long hamlet_id) {
		try {
			Query query = getEntityManager().createQuery("Select su from SpatialUnitTable su where su.hamlet_Id.id = :hamlet_id");
			@SuppressWarnings("unchecked")
			List<SpatialUnitTable> spatialUnitlst = query.setParameter("hamlet_id", hamlet_id).getResultList();	
			if(spatialUnitlst.size() > 0){
				return true;
			}		
			else
			{
				return false;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}
	}

	@Override
	public boolean deleteAllVertexLabel() {

		try {
			Query query = getEntityManager().createNativeQuery("DELETE FROM vertexlabel");
			int spatialUnit = query.executeUpdate();


			if(spatialUnit> 0){
				return true;
			}		
			else
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}
	}

	@Override
	public boolean addAllVertexLabel(int k, String lat, String lon) {try {
		Query query = getEntityManager().createNativeQuery("insert into vertexlabel(gid,the_geom) values("+k+",ST_SetSRID(ST_MakePoint("+lon+","+lat+"), 4326));");
		int spatialUnit = query.executeUpdate();


		if(spatialUnit> 0){
			return true;
		}		
		else
		{
			return true;
		}
	} catch (Exception e) {

		logger.error(e);
		return false;
	}
	}

	@Override
	public List<Object> findAllVerteces() {
		
		
		//List<vertexLabel> vertexTmp=new ArrayList<vertexLabel>();
		try {
			
			
			Query query = getEntityManager().createNativeQuery(
					 "SELECT  gid,ST_X(the_geom), ST_Y(the_geom)"
					+ " FROM  vertexlabel ");
			 @SuppressWarnings("unchecked")
			 List<Object> vertexTmp= query.getResultList();


			if(vertexTmp.size()> 0){
				return vertexTmp;
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

	@Override
	public boolean actionUpdateWorkflow(Long id, Integer workflowId,Integer statusId) {

		try {
	
			Query query = getEntityManager().createQuery("UPDATE SpatialUnitTable su SET su.workflow_id.workflowId = :workflowId where su.usin = :usin");
			int rejectStatus = query.setParameter("workflowId", workflowId).setParameter("usin",id).executeUpdate();	

			if(rejectStatus>0)
			{
				return true;
			}
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

		return false;

	}

	@Override
	public List<?> getSearchResult(String usin,String appno, String pvno, String apfr,
			String name, int apptype, int[] workids,String projname,Integer startpos,int status) {

		List<?> spatialUnit;
		ArrayList<Long> newUsin=new ArrayList<Long>();

		try {
			String query="select su.application_no,a.share,a.first_name,a.last_name,su.pv_no,su.usin,su.workflow_id,su.current_workflow_status_id,cast(su.section AS Integer),su.apfr_no " 
					+"from (select np.first_name,np.last_name,st.gid,st.share,st.usin"
					+" FROM social_tenure_relationship st"
					+" INNER JOIN  natural_person np"
					+" ON np.gid=st.person_gid)a"
					+" inner join spatial_unit su"
					+" ON su.usin=a.usin where su.active=true and su.project_name=:project_name";

			
			
			if(usin!=""){

				query=query+" and su.usin in :usin";	
			}
			
			if(pvno!=""){

				query=query+" and lower(su.pv_no) like :pv_no";	
			}
			if(appno!=""){
				query=query+" and lower(su.application_no) like :application_no";
			}
			if(apfr!=""){
				query=query+" and lower(su.apfr_no) like :apfr_no";
			}
			if(workids!=null){
				query=query+" and su.workflow_id in :workflow_id";

			}
			if(apptype!=0){
				query=query+" and a.share = :share";
			}
			if(name!=""){
				query=query+" and lower(a.first_name) like :first_name";
			}
			
			if(status!=0){
				query=query+" and su.current_workflow_status_id = :status";

			}
			
			Query executeQuery = getEntityManager().createNativeQuery(query).setFirstResult(startpos).setMaxResults(5);
			executeQuery.setParameter("project_name", projname);

			if(usin!=""){

				for (String retval: usin.split(",")){
					newUsin.add(Long.parseLong(retval));
				}

				executeQuery.setParameter("usin",newUsin);	
			}
			
			if(appno!=""){

				executeQuery.setParameter("application_no", "%"+appno.toLowerCase()+"%");
			}
			if(pvno!=""){
				executeQuery.setParameter("pv_no", "%"+pvno.toLowerCase()+"%");
			}
			if(apfr!=""){
				executeQuery.setParameter("apfr_no", "%"+apfr.toLowerCase()+"%");
			}
			if(workids!=null){
				List<Integer> tmpMapped_uids = new LinkedList<Integer>(Arrays.asList(ArrayUtils.toObject(workids)));
				executeQuery.setParameter("workflow_id", tmpMapped_uids);

			}
			if(apptype!=0){
				executeQuery.setParameter("share", apptype);
			}
			if(name!=""){
				executeQuery.setParameter("first_name", "%"+name.toLowerCase()+"%");
			}
			if(status!=0){
				executeQuery.setParameter("status", Long.valueOf(status));
			}

			spatialUnit = executeQuery.getResultList();	
			//int count=0;

			if(spatialUnit.size() > 0){
				return spatialUnit;
			}
			else{
				return null;
			}

		} catch (Exception e) {
			logger.error(e);
			return null;
		}


	}

	@Override
	public int getSearchCount(String usin,String appno, String pvno, String apfr,
			String name, int apptype, int[] workids, String projectname,int status) {

		List<?> spatialUnit;
		ArrayList<Long> newUsin=new ArrayList<Long>();
		int count=0;
		try {
			String query="Select count(*) " 
					+"from (select np.first_name,np.last_name,st.gid,st.share,st.usin"
					+" FROM social_tenure_relationship st"
					+" INNER JOIN  natural_person np"
					+" ON np.gid=st.person_gid)a"
					+" inner join spatial_unit su"
					+" ON su.usin=a.usin where su.active=true and su.project_name=:project_name";
			
			
			
			
			if(usin!=""){

				query=query+" and su.usin in :usin";	
			}
			
			if(pvno!=""){

				query=query+" and lower(su.pv_no) like :pv_no";	
			}
			if(appno!=""){
				query=query+" and lower(su.application_no) like :application_no";
			}
			if(apfr!=""){
				query=query+" and lower(su.apfr_no) like :apfr_no";
			}
			if(workids!=null){
				query=query+" and su.workflow_id in :workflow_id";

			}
			if(apptype!=0){
				query=query+" and a.share = :share";
			}
			if(name!=""){
				query=query+" and lower(a.first_name) like :first_name";
			}
			if(status!=0){
				query=query+" and su.current_workflow_status_id = :status";

			}

			Query executeQuery = getEntityManager().createNativeQuery(query);
			executeQuery.setParameter("project_name", projectname);
			
			
			if(usin!=""){

				for (String retval: usin.split(",")){
					newUsin.add(Long.parseLong(retval));
				}

				executeQuery.setParameter("usin",newUsin);	
			}
			
			if(appno!=""){

				executeQuery.setParameter("application_no", "%"+appno.toLowerCase()+"%");
			}
			if(pvno!=""){
				executeQuery.setParameter("pv_no", "%"+pvno.toLowerCase()+"%");
			}
			if(apfr!=""){
				executeQuery.setParameter("apfr_no", "%"+apfr.toLowerCase()+"%");
			}
			if(workids!=null){
				List<Integer> tmpMapped_uids = new LinkedList<Integer>(Arrays.asList(ArrayUtils.toObject(workids)));
				executeQuery.setParameter("workflow_id", tmpMapped_uids);

			}
			if(apptype!=0){
				executeQuery.setParameter("share", apptype);
			}
			if(name!=""){
				executeQuery.setParameter("first_name", "%"+name.toLowerCase()+"%");
			}
			if(status!=0){
				executeQuery.setParameter("status", Long.valueOf(status));
			}

			spatialUnit = executeQuery.getResultList();	


			if(spatialUnit.size() > 0){
				count=Integer.valueOf (spatialUnit.get(0).toString());

			}


		} catch (Exception e) {
			logger.error(e);

		}
		return count;

	}

	@Override
	public List<Object> findparcelcountbytenure(String project) {
		
		try {
			List<Object> spatialUnit;
			String query="select st.share_type,count(distinct sr.usin) from social_tenure_relationship sr" 
					+" inner join share_type st on st.gid=sr.share"
					+" where usin in" 
					+" (select usin from spatial_unit where project_name = :project_name"
					+ " and active=true)"
					+" group by st.share_type" ;
			
			Query executeQuery = getEntityManager().createNativeQuery(query);
			executeQuery.setParameter("project_name", project);
			spatialUnit = executeQuery.getResultList();	
			if(spatialUnit.size() > 0){
				
				return spatialUnit;
			}
			
			else{
				
				return null;
			}
		} catch (Exception e) {
		logger.error(e);
		return null;
		}
		
		
	}

	@Override
	public List<Object> findparcelcountbygender(String project,String tag,Integer villageId) {
		
		int newworkflow_id=0;
		int existingworkflow_id=0;
		
		if(tag.equalsIgnoreCase("NEW")){
				newworkflow_id=1;
				existingworkflow_id=10;
			
			
		}
		else if(tag.equalsIgnoreCase("REGISTERED")){
			
				newworkflow_id=2;
				existingworkflow_id=11;
		}
		
		else if(tag.equalsIgnoreCase("APFR")){
			
				newworkflow_id=7;
				existingworkflow_id=13;
		}
		
		try {
			List<Object> spatialUnit;
			
		String query="select g.gender_sw,usin from gender g inner join"
					+" (select p.gender as id,count(a.usin)as usin from natural_person p inner join"
					+" (select st.person_gid as pgid,su.usin as usin from spatial_unit su"
					+" inner join social_tenure_relationship st on su.usin=st.usin"
					+" where su.project_name=:project_name and"
					+" ((su.workflow_id>=:newworkflow_id and parceltype_id=1)"
					+" or (su.workflow_id>=:existingworkflow_id and parceltype_id=2))"
					+" and su.active=true and su.village_id=:villageId) a"
					+" on a.pgid=p.gid"
					+" group by p.gender)b"
					+" on g.gender_id=b.id";
					
					
			
			Query executeQuery = getEntityManager().createNativeQuery(query);
			executeQuery.setParameter("project_name", project).setParameter("newworkflow_id", newworkflow_id)
						.setParameter("existingworkflow_id", existingworkflow_id).setParameter("villageId", villageId);
			spatialUnit = executeQuery.getResultList();	
			if(spatialUnit.size() > 0){
				
				return spatialUnit;
			}
			
			else{
				
				return null;
			}
		} catch (Exception e) {
		logger.error(e);
		return null;
		}
		
	}

	@Override
	public List<Object> findregparcelcountbyTenure(String project,String tag,Integer villageId) {try {
		List<Object> spatialUnit;
		int existingworkflow=0;
		int newworkflow=0;
		
		 if(tag.equalsIgnoreCase("NEW")){
			 existingworkflow=10;
			 newworkflow=1;	
		}
		else if(tag.equalsIgnoreCase("REGISTERED")){
			 existingworkflow=11;
			 newworkflow=3;	
		}
		else if(tag.equalsIgnoreCase("APFR")){
			 existingworkflow=13;
			 newworkflow=7;	
		}
		
		String query="select a.share,a.count"
				+" from (select count(sp.usin) as count,pt.parceltype as share"
				+" from spatial_unit sp inner join parcel_type pt"
				+" on sp.parceltype_id=pt.parceltype_id"
				+" where sp.project_name = :project_name"
				+" and sp.active=true and workflow_id>=:existingworkflow and"
				+" sp.current_workflow_status_id!=5 and sp.parceltype_id=2 and sp.village_id= :villageId"
				+" group by pt.parceltype_id  )a"
				+" union"
				+" select b.share,b.count from"
				+" (select st.share_type as share,count(distinct sr.usin)"
				+" as count from social_tenure_relationship sr"
				+" inner join share_type st on st.gid=sr.share"
				+" where usin in"
				+" (select usin from spatial_unit where project_name = :project_name"
				+" and active=true and workflow_id>=:newworkflow and"
				+" current_workflow_status_id!=5 and parceltype_id=1 and sp.village_id= :villageId)"
				+" group by st.share_type) b";
		
		Query executeQuery = getEntityManager().createNativeQuery(query);
		executeQuery.setParameter("project_name", project).setParameter("existingworkflow", existingworkflow).setParameter("newworkflow",newworkflow).setParameter("villageId", villageId);
		spatialUnit = executeQuery.getResultList();	
		if(spatialUnit.size() > 0){
			
			return spatialUnit;
		}
		
		else{
			
			return null;
		}
	} catch (Exception e) {
	logger.error(e);
	return null;
	}
	}

	@Override
	public List<Object> findRegistrytable(String project, String tag,Integer villageId) {
		
		try{
			int workflowId=0;
		if(tag.equalsIgnoreCase("PROCESSEDAPPLICATION")){
			workflowId=3;
		}
		else if(tag.equalsIgnoreCase("PUBLISHEDAPPLICATION")){
			workflowId=6;
		}
		else if(tag.equalsIgnoreCase("PROCESSEDAPFR")){
			workflowId=7;	
		}
		
		
		String query="select first_name,last_name,a.appno,a.appdate from natural_person np inner join"
			+" (select sp.application_no as appno ,sp.applicationdate as appdate ,st.person_gid"
			+ " as gid from spatial_unit sp inner join social_tenure_relationship st"
			+" on sp.usin=st.usin where sp.project_name =:project_name and sp.workflow_id=:workId and sp.active='true' and sp.village_id=:villageId) a on np.gid=a.gid";
 
		
		
	Query executeQuery = getEntityManager().createNativeQuery(query);
	executeQuery.setParameter("project_name", project).setParameter("workId",workflowId).setParameter("villageId", villageId);
	List<Object> spatialUnit = executeQuery.getResultList();	
	if(spatialUnit.size() > 0){
	
		return spatialUnit;
		}

		
		}
		
		
		
		catch(Exception e){
			logger.error(e);
			return null;
		}
		return null;
		
		
	}


}




