<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	java.lang.String s = null;
	java.lang.String principal = null;
	try{
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		principal = user.getUsername();
		//java.util.Collection<org.springframework.security.core.GrantedAuthority> authorities = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		java.util.Collection<org.springframework.security.core.GrantedAuthority> authorities  = user.getAuthorities();
	 	
		java.lang.StringBuffer sb = new java.lang.StringBuffer();
		for (java.util.Iterator<org.springframework.security.core.GrantedAuthority> itr = authorities.iterator(); itr.hasNext();) {       
	org.springframework.security.core.GrantedAuthority authority = itr.next();
	sb.append(authority.getAuthority()).append(", ");
		} 
		s = sb.toString();
		s = s.substring(0, s.length() - 2);
		
	}catch(Exception e){
		e.printStackTrace();	
	}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><spring:message code="Administration_Tool" htmlEscape="false" /></title>
<meta http-equiv="Content-Type"  content="text/html;charset=UTF-8">
	<!--  -->
	<link rel="stylesheet"
		href="<c:url value="resources/styles/studio/blueprint/reset.css" />"
		type="text/css" media="screen, projection">
		<link rel="stylesheet"
			href="<c:url value="resources/styles/studio/blueprint/screen.css" />"
			type="text/css" media="screen, projection">
			<link rel="stylesheet"
				href="<c:url value="resources/styles/studio/blueprint/print.css" />"
				type="text/css" media="print">

				<link rel="stylesheet" type="text/css" media="screen"
					href="<c:url value="resources/scripts/jquery-ui-1.8.13/css/redmond/jquery-ui-1.8.13.custom.css" />" />
				<link rel="stylesheet" type="text/css" media="screen"
					href="<c:url value="resources/scripts/jqGrid/css/ui.jqgrid.css" />" />

				<link rel="stylesheet" type="text/css"
					media="print, projection, screen"
					href="<c:url value="resources/scripts/tablesorter/themes/blue/style.css" />" />

				<link rel="stylesheet" type="text/css"
					media="print, projection, screen"
					href="<c:url value="resources/scripts/jquery-alert/jquery.alerts.css" />" />

				<link rel="stylesheet"
					href="<c:url value="resources/styles/studio/form-common.css" />"
					type="text/css" media="print">

					<link rel="stylesheet"
						href="<c:url value="resources/styles/studio/header.css" />"
						type="text/css" media="screen, projection">
						<link rel="stylesheet"
							href="<c:url value="resources/styles/font-awesome.min.css" />"
							type="text/css" media="screen, projection" />
						<script
							src="<c:url value="resources/scripts/Cloudburst-Studio.js"  />"
							type="text/javascript"></script>


						<!--[if IE 8]>
		<link rel="stylesheet" href="<c:url value="resources/styles/studio/blueprint/ie.css" />" type="text/css" media="screen, projection">
	<![endif]-->
						<!--[if IE 7]>
   <link rel="stylesheet" href="<c:url value="resources/styles/studio/blueprint/ie-style.css" />" type="text/css" media="screen, projection">
    <![endif]-->
						<style type="text/css">
/* body { font-size: 62.5%; } */
/* label, input { display:block; } */
input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

h1 {
	font-size: 1.2em;
	margin: .6em 0;
}

div#users-contain {
	width: 350px;
	margin: 20px 0;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td, div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-button {
	outline: 0;
	margin: 0;
	padding: .4em 1em .5em;
	text-decoration: none; ! important;
	cursor: pointer;
	position: relative;
	text-align: center;
}

.ui-dialog .ui-state-highlight, .ui-dialog .ui-state-error {
	padding: .3em;
}
</style>



						<!--  
    <script type="text/javascript">
        $(function() {
            
        });
    </script>
    -->

						<link rel="stylesheet" type="text/css"
							media="print, projection, screen"
							href="<c:url value="resources/styles/studio/vtav.css" />" />

						<link rel="stylesheet" type="text/css"
							media="print, projection, screen"
							href="<c:url value="resources/styles/studio/studio.css" />" />
</head>
<body>


	<div id="_splash" class="splash">
		<div id="splash-content">
		
						<img id="enter" alt="Meerkat" />
          
		</div>
	</div>

	<div id="_loader" class="loader">
		<img src="resources/images/studio/ajax-loader.gif" />
	</div>
	<script language="javascript">
	var token = null;
	var roles = '<%=s%>';
	var useremail='<%=principal%>';
	var lang="en";
	var DATA_SAVED="";
	var FAILED="";
	var Delete_Confirmation="";
	var DELETE_MSG="";
	var Master_Attribute="";
	var ALERT="";
	var Project_EXISTS="";
	var ENTER_PROJECT="";
	var ERROR_MSG="";
	var DELETE_ERR="";
	var NO_DATA="";
	var DELETE_SUCCESS="";
	var FILL_ALL="";
	var Update="";
	var Cancel="";
	var Save="";
	var Ok="";
	var Name_exists="";
	var Save_error="";
	var Alpha_num="";
	var Data_used="";
	var Enter_Pass="";
	var ReEnter_Pass="";
	var Conf_pass="";
	var Projectname_exists="";
	var Alert="";
	var Enter_pname="";
	var Select_one="";
	var Fill_Mandatory="";
	var PLEASE_SELECT="";
	var FIELD_REQ="";

	var currentLocale = getUrlVar('lang');
	if(currentLocale == undefined){
		
		if(currentLocale != "en" && currentLocale != "fr"){
			currentLocale = "en";
		}
	}
		lang= currentLocale.split("#")[0];	
	
		if(lang=='en')
			{
			DATA_SAVED="Data Sucessfully Saved";
			FAILED="Request Can not be completed";
			Delete_Confirmation='Delete Confirmation';
			DELETE_MSG='Are You Sure You Want To Delete';
			Master_Attribute='Master Attribute';

			ALERT="Alert";
			Project_EXISTS="Name already Exists";
			ENTER_PROJECT="Please Enter Project Name";
			ERROR_MSG="Error in Saving Data";
			USER_PROJECT="Select atleast one user Form Assign user section";
			DELETE_ERR="Data Can not be deleted";
			DELETE_SUCCESS="Data Successfully Deleted";
			NO_DATA="No Data Available";
			FILL_ALL="Please Fill Mandatory Details";

			Update='Update';
			Cancel='Cancel';
			Save='Save';
			Ok='Ok';
			Name_exists='Name already Exists';
			Save_error='Error in saving data';
			Alpha_num='Enter Alpha Numeric value in Username';
			Data_used='Data Can not be Deleted..Used as reporting Manager';
			Enter_Pass='Please enter Password';
			ReEnter_Pass='Please re-enter Password';
			Conf_pass='Confirm Password should be same as Password';
			Projectname_exists='Project Name already Exists';
			Alert='Alert';
			Enter_pname='Please Enter Project Name';
			Select_one='Select atleast one user';
			Fill_Mandatory='Please Fill Mandatory Details';
			PLEASE_SELECT='Please Select';
			FIELD_REQ="This Field is required";

			}
		else if(lang=='fr')
		{
			DATA_SAVED="Données enregistrées avec succès";
			FAILED='La requête ne peut pas être réalisée';
			Delete_Confirmation='Confirmer la suppression';
			DELETE_MSG='Etes-vous certain de vouloir supprimer ?';
			Master_Attribute='Attributs maîtres';

			ALERT="Alerte";
			Project_EXISTS="Le nom du existe déjà";
			ENTER_PROJECT="Merci d'entrer le nom du projet";
			ERROR_MSG="Erreur en enregistrant les données";
			USER_PROJECT="Sélectionner au moins un utilisateur";
			DELETE_ERR="Data Can not be deleted";
			DELETE_SUCCESS="Données supprimées avec succès";
			NO_DATA="Aucune donnée disponible";
			FILL_ALL="Merci de remplir les infos obligatoires";
			

			Update='Mise à jour';
			Cancel='Annuler';
			Save='Enregistrer';
			Ok='Ok';
			Name_exists='Le nom existe déjà';
			Save_error='Erreur en enregistrant les données';
			Alpha_num="Entre les caractères alphanumériques dans le nom d'utilisateur";
			Data_used='Les données ne peuvent pas être supprimées car utilisées comme gestionnaire de rapport';		
			Enter_Pass="Merci d'entrer le mot de passe";
			ReEnter_Pass="Merci d'entrer le mot de passe à nouveau";
			Conf_pass='Le mot de passe confirmé doit être le même que le mot de passe';
			Projectname_exists='Le nom du projet existe déjà';
			Alert='Alerte';
			Enter_pname="Merci d'entrer le nom du projet";
			Select_one='Sélectionner au moins un utilisateur';
			Fill_Mandatory='Merci de remplir les infos obligatoires';
			PLEASE_SELECT='Merci de sélectionner';
			FIELD_REQ="Ce champ est obligatoire";

		}
	$(document).ready(
			function() {
				
				$('.splash').meerkat({
					background: 'url(resources/images/studio/bg-splash.png) repeat-x left top',
					height: '100%',
					width: '100%',
					position: 'center',
					animationIn: 'none',
					animationOut: 'fade',
					animationSpeed: 500,
					timer: 2,
					removeCookie: '.reset'
				});
				
				if(lang=="fr"){
					$('#logo_text').attr('src','./resources/images/logo-french.png');
					$('#enter').attr('src','resources/images/studio/splash-logo-french.png');
					}
					else if(lang=="en"){
						$('#logo_text').attr('src','./resources/images/Logo_text.png');
					$('#enter').attr('src','resources/images/studio/splash-logo.png');
					}
	});
	
	function invokeViewer(){
		qryString = "<%=request.getSession().getAttribute("lang")%>";
		if(qryString == "null"){
			qryString = "en";
		}
		var url = "../viewer/?lang=" + qryString;
		document.location.href = url;
	}
	function invokeMobile(){
		qryString = "<%=request.getSession().getAttribute("lang")%>";
		if(qryString == "null"){
			qryString = "en";
		}
		var url = "../mobileconfig/?lang=" + qryString;
		document.location.href = url;
	}
	</script>
	<!--start:header-->
	<div class="header-alt">
		<div class="subHead">
			
					<img id="logo_text" style="width:100%"/>	
             
		</div>

		<div id="navigateStudio">
			<ul>
				<!-- <li><a href="../mobileconfig/">Mobile Config</a></li> -->
				
				<li><a href="javascript:invokeMobile();"><spring:message code="Mobile_Configuration" htmlEscape="false" /></a></li>
				<li><a href="javascript:invokeViewer();"><spring:message code="Data_Management" htmlEscape="false" /></a></li>


			</ul>

		</div>
		<!--/subHead-->
		<!-- <div class="loginInfo">
               <div class="userText">
               <div class="dropdown" style="cursor:pointer">
    				<i class="fa fa-user"></i>
             				  Welcome <span class="username"><%=principal%></span>
               	&nbsp;&nbsp;
          </div>
               <span class="logoutApp"><a href="/mast/j_spring_security_logout">Logout</a></span>
        </div>
               
               <img src="./resources/images/userInfo.png"/>
            </div>-->
		<div class="userinfo">
			<ul>
				<li style="cursor: pointer"><span> <i class="fa fa-user"></i>
						<spring:message code="Welcome" htmlEscape="false"/> <span class="username"><%=principal%></span></span></li>
				<li><a href="/mast/j_spring_security_logout"><spring:message code="Logout" htmlEscape="false"/></a></li>


			</ul>
		</div>
		<!--/loginInfo-->
	</div>
	<!--end:header-->




	<div id="vtab">
		<ul>
			<!--  <li id="home" class="home selected"><img src="resources/images/studio/vtab/home.png" /><span>Home</span></li> -->
			<li id="user" class="login selected"><img
				src="resources/images/studio/vtab/users.png" /><span> <spring:message code="Users" htmlEscape="false"/>  </span></li>
			<li id="layer" class="login"><img
				src="resources/images/studio/vtab/layers.png" /><span> <spring:message code="Data_Layers" htmlEscape="false"/>  </span></li>
			<li id="layergroup" class="login"><img
				src="resources/images/studio/vtab/layergroup.png" /><span> <spring:message code="Layer_Groups" htmlEscape="false"/>  </span></li>
			<li id="project" class="login"><img
				src="resources/images/studio/vtab/project.png" /><span> <spring:message code="Survey_Projects" htmlEscape="false"/>  </span></li>
			<li id="masterattribute" class="login"><img
				src="resources/images/studio/vtab/users.png" /><span> <spring:message code="Master_Attribute" htmlEscape="false"/>  </span></li>
			<li id="bookmark"  class="prefrences"><img src="resources/images/studio/vtab/bookmark.png" /><span>Villages of Commune</span></li>
				
			<!--li id="role"  class="module"><img src="resources/images/studio/vtab/role.png" /><span>Roles</span></li>
            <li id="bookmark"  class="prefrences"><img src="resources/images/studio/vtab/bookmark.png" /><span>Bookmarks</span></li>
            <li id="maptip"  class="maptip"><img src="resources/images/studio/vtab/maptip.png" /><span>Maptips</span></li>
			<li id="masterattribute"  class="layergroup"><img src="resources/images/studio/vtab/db-conn.png" /><span>Master Attributes</span></li>
            <li id="configsetting"  class="configsetting"><img src="resources/images/studio/vtab/configsetting.png" /><span>Configuration</span></li>
            <li id="calendarsetting"  class="calendarsetting"><img src="resources/images/studio/vtab/calendar.png" /><span>Calendar</span></li>
            < li id="importdata"  class="importdata"><img src="resources/images/studio/vtab/importdata.png" /><span>Import Data</span></li>
            <li id="style"  class="_style"><img src="resources/images/studio/vtab/styles.png" /><span>Styles</span></li>
            <li id="dbconnection" class="dbconn"><img src="resources/images/studio/vtab/db-conn.png" /><span>DB Connections</span></li -->
		</ul>

		<!--  <div id="home"></div> -->
		<div id="users"></div>
		<div id="layers"></div>
		<div id="layergroups"></div>
		<div id="projects"></div>
		<div id="masterattribute-div"></div>
		 <div id="bookmarks"></div>
		<!-- div id="roles"></div>
        <div id="bookmarks"></div>
        <div id="maptips"></div>
		 <div id="masterAttributes"></div>
         <div id="configsettings"></div>
          <div id="calendarsettings"></div>
           <div id="importdatas"></div>
        <!-- div id="dbconns"></div >
        <div id="styles"></div-->

	</div>
	<div id="footer">
		<span class="footer-s">© RMSI 2016.<spring:message code="Rights_reserved" htmlEscape="false"/></span>
	</div>
</body>
</html>