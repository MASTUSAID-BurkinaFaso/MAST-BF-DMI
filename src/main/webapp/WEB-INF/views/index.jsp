
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server 
	
	String queryString = request.getQueryString();
	String token = (String)request.getSession().getAttribute("auth");
	//(String)request.getSession().getAttribute("lang");
	String lang_2 = "en";
	if(((String)request.getSession().getAttribute("lang"))!=null){
		
		lang_2=(String)request.getSession().getAttribute("lang").toString();	
	}	
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>MTP</title>
 
<link rel="stylesheet" type="text/css" href="./resources/styles/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/styles/cloudMain.css"/>


<script
							src="<c:url value="resources/scripts/cloudburst/studio/common.js"  />"
							type="text/javascript"></script>
<script type="text/javascript">
	var url;
	var qryString;
</script>
	<%
	if(request.getSession().getAttribute("url") != null){
		//System.out.println(" ------- URL on Landing " + request.getSession().getAttribute("url"));
	%>	
		<script type="text/javascript">
			var url = "<%=request.getSession().getAttribute("url")%>";
			qryString = "<%=request.getSession().getAttribute("lang")%>";
			if(qryString==null || qryString=='null')qryString = 'en';
			
			var url = url + "?lang=" + qryString;
			console.log(url);
			document.location.href = url;
		</script>
		
	<%
	}else if(s.equalsIgnoreCase("ROLE_PUBLICUSER") || s.equalsIgnoreCase("ROLE_USER") ||s.equalsIgnoreCase("ROLE_SFR") || s.equalsIgnoreCase("ROLE_DPI") ){
		System.out.println("---Authorities: " + s);
	%>
		<script type="text/javascript">
			var studio_url = window.location.href;
			var pos = studio_url.indexOf("index");
			studio_url = studio_url.substring(0, pos - 1);
			qryString = "<%=request.getSession().getAttribute("lang")%>";
			if(qryString==null || qryString=='null')qryString = 'en';
			studio_url = studio_url + "/viewer/?lang=" + qryString;
			document.location.href = studio_url;
		</script>
	<%
	}
	%>
</head>

<body>
    <div id="wrapper">
      <div class="header">
            <div class="subHead">
            <c:choose>
			  <c:when test="${lang=='fr'}">
					<img src="./resources/images/logo-french.png" style="width:100%"/>	
               </c:when>
               <c:otherwise>
					<img src="./resources/images/Logo_text.png" style="width:100%"/>	
               </c:otherwise>
            </c:choose>
               
            </div>
            <!--/subHead-->
			
<!-- <div class="loginInfo">
               <div class="userText">
               <div class="dropdown" style="cursor:pointer">
    				<i class="fa fa-user"></i>
             		  Welcome <span class="username"><%=principal%></span>
               	&nbsp;&nbsp;          </div>
               <span class="logoutApp"><a href="/mast/j_spring_security_logout">Logout</a></span>
        </div>
               
               <img src="./resources/images/userInfo.png"/>
            </div> -->
			<div class="userinfo" >
      <ul>
        <li  style="cursor:pointer"><span>	<i class="fa fa-user"></i>
             				  <spring:message code="Welcome" htmlEscape="false"/> <span class="username"><%=principal%></span></span></li>
        <li><a href="/mast/j_spring_security_logout"> <spring:message code="Logout" htmlEscape="false"/></a></li>
        
        
      </ul>
    </div>

            <!--/loginInfo-->
         </div>
           <div class="pageContent">
           
            <div class="toolBoxes">
               <div class="Bx-container">
                  <div class="row">
                     <div class="span4">
                        <div class="infoBox">
                           <div class="toolsHd">
                              <img src="./resources/images/icon-wrench.png"/>
                              <h2> <spring:message code="Administration_Tool" htmlEscape="false"/></h2>
                           </div>
                           <div class="toolsDec">
                              <p><spring:message code="Administration_description" htmlEscape="false"/></p>
                           </div>
                           <div class="PanelBottom">
                              <div class="BtmText">
                                 <a href="#" onclick="invokeStudio();" class="launch-text">
                                 <i class="fa fa-chevron-right ico-ft"></i> <spring:message code="Launch" htmlEscape="false"/>
                                 </a>
                              </div>
                           </div>
                           <!--/PanelBottom-->
                        </div>
                        <!--/infoBox-->
                     </div>
                     <div class="span4">
                        <div class="infoBox">
                           <div class="toolsHd">
                              <img src="./resources/images/icon-mobile.png" style="height:64px"/>
                              <h2> <spring:message code="Mobile_Configuration_Tool" htmlEscape="false"/></h2>
                           </div>
                           <div class="toolsDec">
                              <p><spring:message code="Mobile_description" htmlEscape="false"/></p>
                           </div>
                           <div class="PanelBottom">
                              <div class="BtmText">
                                 <a href = "#" onclick="invokeMobile();" class="launch-text">
                                 <i class="fa fa-chevron-right ico-ft"></i> <spring:message code="Launch" htmlEscape="false"/>
                                 </a>
                              </div>
                           </div>
                           <!--/PanelBottom-->
                        </div>
                        <!--/infoBox-->
                     </div>
                     <div class="span4">
                        <div class="infoBox">
                           <div class="toolsHd">
                              <img src="./resources/images/icon-data-layer.png" style="height:64px"/>
                              <h2> <spring:message code="Data_Management" htmlEscape="false"/></h2>
                           </div>
                           <div class="toolsDec">
                              <p><spring:message code="Land_description" htmlEscape="false"/></h2></p>
                           </div>
                           <div class="PanelBottom">
                              <div class="BtmText">
                                 <a href="#" onclick="invokeViewer();" class="launch-text">
                                 <i class="fa fa-chevron-right ico-ft"></i> <spring:message code="Launch" htmlEscape="false"/>
                                 </a>
                              </div>
                           </div>
                           <!--/PanelBottom-->
                        </div>
                        <!--/infoBox-->
                     </div>
                  </div>
                  <!--/row-->
                  <div class="space-10"></div>
                  <div class="row" style="display:none;">
                     <div class="span4 offset4">
                        <div class="infoBox">
                           <div class="toolsHd">
                              <img src="./resources/images/report.png" style="height:64px"/>
                              <h2>Reports</h2>
                           </div>
                           <div class="toolsDec">
                              <p>Generate Land Adjudication Form and CCRO certificate for selected property.</p>
                           </div>
                           <div class="PanelBottom">
                              <div class="BtmText">
                                 <a href = "report/" class="launch-text">
                                 <i class="fa fa-chevron-right ico-ft"></i> Launch 
                                 </a>
                              </div>
                           </div>
                           <!--/PanelBottom-->
                        </div>
                        <!--/infoBox-->
                     </div>
                  </div>
               </div>
               <!--/Bx-container-->
            </div>
            <!--/toolBoxes-->
         </div>
           <footer>
         <div class="footerText">© 2015. <spring:message code="Rights_reserved" htmlEscape="false"/></div>
         <div class="subtxt"><span class="logoTX"><spring:message code="Partners" htmlEscape="false"/></span> <img src="./resources/images/foot_img.png"/></div>
      </footer>
      </div>
<script type="text/javascript">
	var qryString = "<%=request.getSession().getAttribute("lang")%>";
	if(qryString == null){
		var _lang = "<%=request.getHeader("accept-language")%>";
		var pos = _lang.indexOf(",");
		if(pos > -1){
			_lang = _lang.substring(0, pos);
		}else{
			_lang = "en";
		}
	}else{
		_lang = qryString;
	}

	
	var lang="en";
	var currentLocale = getUrlVar('lang');
	if(currentLocale == undefined){
		
		if(currentLocale != "en" && currentLocale != "fr"){
			currentLocale = "en";
		}
	}
		lang= currentLocale.split("#")[0];
	
	var studio = document.getElementById("studio");
	var viewer = document.getElementById("viewer");
	var studio_link = document.getElementById("studiolink");
	var viewer_link = document.getElementById("viewerlink");

	function invokeViewer(){
		qryString = "<%=request.getSession().getAttribute("lang")%>";
		if(qryString == "null"){
			qryString = "en";
		}
		var url = "viewer/?lang=" + qryString;
		document.location.href = url;
	}
	
	function invokeMobile(){
		qryString = "<%=request.getSession().getAttribute("lang")%>";
		if(qryString == "null"){
			qryString = "en";
		}
		var url = "mobileconfig/?lang=" + qryString;
		document.location.href = url;
	}
	
	function invokeStudio(){
		qryString = "<%=request.getSession().getAttribute("lang")%>";
		if(qryString == "null"){
			qryString = "en";
		}
		var url = "studio/?lang=" + qryString;
		document.location.href = url;
	}
	
	
</script>
</body>
</html>
