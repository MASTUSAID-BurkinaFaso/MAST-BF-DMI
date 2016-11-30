<%@ page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page
	import="org.springframework.security.core.AuthenticationException"%>


<%@ page session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>MAST-DMI Login</title>
<script src="<c:url value="./resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"  />"
	type="text/javascript"></script>
<link rel="stylesheet"
				href="<c:url value="./resources/styles/style_login.css" />"
				type="text/css" media="screen, projection" />
</head>
<body onload='document.loginForm.j_username.focus();'>
<% 
String LOGIN_ERR = "";
String auth_fail_status = "";
String getURL=request.getQueryString();
String requestParam = request.getQueryString();
System.out.println("SpatialVue-auth querystring: " + requestParam);
 String urlLang = request.getQueryString();
    String lang = null;

    if(urlLang != null){
    	int pos = urlLang.indexOf("=");
    	if(pos > -1){
			lang = urlLang.substring(++pos);
		}else{
			lang = "fr";
		}
    }else{	
		lang = request.getHeader("accept-language");
		int pos = lang.indexOf(",");
		if(pos > -1){
			lang = lang.substring(0, pos);
			if(lang.indexOf("en") != -1){
				response.sendRedirect("./login?lang=fr"); 
			}else{
				response.sendRedirect("./login?lang=fr"); 
			}
		}else{
			response.sendRedirect("./login?lang=fr"); 
		}
    }
if(requestParam == null){
	requestParam = "param=1";
}
if(requestParam.indexOf("login_error") == -1){
	
	String[] params = requestParam.split("&");
	for(int i=0; i<params.length; i++){
		System.out.println("request attributes: " + i + "is " + params[i]);
		String[] reqParam = params[i].split("=");
		System.out.println("request attribute: " + reqParam[0] + " " + reqParam[1]);
		session.setAttribute(reqParam[0], reqParam[1]);
	}
	//session.setAttribute("param", requestParam);
}
if(getURL != null){
	if(getURL.equalsIgnoreCase("login_error=1")){
		

		auth_fail_status = (lang.equalsIgnoreCase("fr")?"login incorrect_fr":"Login credentials incorrect");
	}else if(getURL.equalsIgnoreCase("access_denied=1")){
		//out.println("<div class='auth-fail'>You are not authorized to access the application</div>");
		auth_fail_status = (lang.equalsIgnoreCase("fr")?"Un-authorized access not allowed_fr":"Un-authorized access not allowed");
	}
}
  
 %>
<div class="header_bluebar_top"></div>
<div class="header_wrapper">
<div style="float:right;">
<!-- <select onchange="javascript:changeLang(this.value);">
<option value=''>Select</option>
<option value='en'>ENGLISH</option>
<option value='fr' >FRENCH</option>

</select> -->
<div id="languageButton">
<ul>
<li>
<a href="javascript:changeLang('fr');">French</a>
</li>
<li>
<a href="javascript:changeLang('en');">English</a>
</li>
</ul>
</div>
<!-- <a href="javascript:changeLang('fr');">FRENCH</a> || <a href="javascript:changeLang('en');">ENGLISH</a> -->
</div>
<div id="usaid_logo" onClick="window.location = 'http://usaid.gov/land-tenure';"></div>
<div class="header_title">
<c:choose>
			  <c:when test="${lang=='fr'}">
					Application Mobile de Sécurisation Foncière
               </c:when>
               <c:otherwise>
					Mobile Application to Secure Tenure (MAST)
               </c:otherwise>
            </c:choose>

<br />
<c:choose>
			  <c:when test="${lang=='fr'}">
					Infrastructure de Gestion des Données
               </c:when>
               <c:otherwise>
					Data Management Infrastructure
               </c:otherwise>
            </c:choose>
</div>

</div>


<!-- <div id="languageButton">
<ul>
<li>
<a href="javascript:changeLang('fr');">FRENCH</a>
</li>
<li>
<a href="javascript:changeLang('en');">ENGLISH</a>
</li>
</ul>
</div> -->
<!-- <a href="javascript:changeLang('fr');">FRENCH</a> || <a href="javascript:changeLang('en');">ENGLISH</a> -->

<div class="header_bluebar_bottom"></div>
<div class="home_background">
<div class="homebox" id="homebox_whatismast">
<div class="homebox_title">
<c:choose>
			  <c:when test="${lang=='fr'}">
					Qu'est-ce-que MAST ?
               </c:when>
               <c:otherwise>
					What is MAST ?
               </c:otherwise>
            </c:choose>
</div>
<p>
<c:choose>
			  <c:when test="${lang=='fr'}">
					Partout dans le monde, des millions de personnes manquent de documentation des droits fonciers. Cela peut être le résultat de la faiblesse des systèmes de gouvernance foncière ou de la capacité limitée à fournir des services d'administration des terres accessibles et responsables. Ces défis institutionnels sont très répandus et entraînent des problèmes coûteux et systémiques sur le terrain. USAID se penche sur ces problèmes grâce à un pilote innovant appelé l'application mobile de sécurisation foncière (MAST), une application libre sur smartphone facile à utiliser qui permet de saisir les informations nécessaires pour publier une documentation officielle des droits fonciers à sécuriser. Couplé avec un système de gestion de données sur un serveur dit "cloud" pour stocker des informations géospatiales et démographiques, le projet est conçu pour réduire les coûts et le temps nécessaires à l'enregistrement des droits fonciers et, surtout, rendre le processus plus transparent et accessible à la population locale. En savoir plus sur ce projet à
               </c:when>
               <c:otherwise>
					Around the world, millions of people lack documented land rights. This may be the result of weak land governance systems or limited capacity to provide accessible and accountable land administration services. These institutional challenges are widespread and drive costly and systemic problems on the ground. USAID is addressing these problems through an innovative pilot called the Mobile Applications to Secure Tenure (MAST) project, an easy-to-use, open-source smartphone application that can capture the information needed to issue formal documentation of land rights. Coupled with a cloud-based data management system to store geospatial and demographic information, the project is designed to lower costs and time involved in registering land rights and, importantly, to make the process more transparent and accessible to local people. Learn more about this project at
               </c:otherwise>
            </c:choose>


 <a target="_new" href="http://www.google.com/url?q=http%3A%2F%2Fwww.usaidlandtenure.net%2Fproject%2Fmobile-applications-secure-tenure-tanzania&sa=D&sntz=1&usg=AFQjCNFWVKqfDObjPCu_W4K--WQ3nPnNVw">
 <c:choose>
			  <c:when test="${lang=='fr'}">
					Portail foncier de l'USAID
               </c:when>
               <c:otherwise>
					USAID's Land Tenure Portal
               </c:otherwise>
            </c:choose>

 </a>.</p>
</div>
<div class="homebox" id="homebox_projects">

<div class="homebox_title">
	<c:choose>
			  <c:when test="${lang=='fr'}">
					Connexion au système MAST
               </c:when>
               <c:otherwise>
					Login to MAST System
               </c:otherwise>
            </c:choose>
</div>
       <form id="loginForm" name="loginForm" action="j_spring_security_check"
		method="post">
	
	<div id="main">
		<div id="login-box">
<div class="formElement">
					<div class="lg-login-form-row">
						<div id="authfail_div" class="auth"> </div>
                        <div id="login" class="vlabel"></div>
						<div id="login-box-field"><input class="form-login" id="usernameField" type="text" name="j_username" /></div>
						<div id="helptxt" >
						</div>
						</div>
						</div>
						<div class="formElement">
						<div class="lg-login-form-row">
                        <div id="pwd" class="vlabel"></div>
						<div id="login-box-field">
						 <input  class="form-login" id="passwordField" type="password" name="j_password" />
						</div>
						</div>
						</div>
						<div class="lg-login-form-row">
						
						 <input class="login-btn" id="btnLogin" type="submit" value="Login" />
						 
						 
					
					<!-- 	 <a href="#" id="forgot_pwd" class="forget-password">Forgot password</a> | 
					   <a href="#" id="register" class="forget-password"></a>           -->
					
						 </div>
</div>
</div>
</div>
</form>
<!--- // We will be sending you a YouTube video to embedd in this box.
<div class="homebox" id="homebox_projects">
<div class="homebox_title">Embedded Video</div>
<p>Youtube code here</p>
</div>
-->

<div style="clear: both;"></div>

<div id="brochure_button" onClick="window.open('./resources/pdf/MAST_Brochure_web.pdf');"></div>
<!-- <div id="android_logo"></div> -->
</div>
<div class="header_bluebar_bottom"></div>
<div id="copyright">
	<c:choose>
			  <c:when test="${lang=='fr'}">
					&copy; 2016. Tous droits réservés.
               </c:when>
               <c:otherwise>
					&copy; 2016. All Rights Reserved.
               </c:otherwise>
            </c:choose>

</div>
<div id="ccg_logo" onClick="window.location = 'http://cloudburstgroup.com';"></div>
<!-- <div id="rmsi_logo" onClick="window.location = 'http://www.rmsi.com';"></div> -->
<script language="javascript">			
		var auth_status = "<%=auth_fail_status%>";
		var language = "<%=lang%>";
		displayLanguageStrings(language);
		var authDiv = document.getElementById("authfail_div");
		authDiv.appendChild(document.createTextNode(auth_status));
		
		function displayLanguageStrings(lang){
			//alert(lang);
			var login = document.getElementById("login");
			var password = document.getElementById("pwd");
			var btn = document.getElementById("btnLogin");
		//	var register = document.getElementById("register");
			var helptxt = document.getElementById("helptxt");
		if(lang=="en"){
				login.appendChild(document.createTextNode("Username:"));
				password.appendChild(document.createTextNode("Password:"));
				btn.value = "Login";
		}
		else if(lang=="fr"){
			login.appendChild(document.createTextNode("Nom d'utilisateur:"));
			password.appendChild(document.createTextNode("Mot de passe:"));
			btn.value = "Connexion";	
		}
		}
		
		function changeLang(langchan){
			
			window.location="http://"+location.host+"/mast/login?lang="+langchan;
			//displayLanguageStrings(langchan);
		}
</script>
</body>

</html>