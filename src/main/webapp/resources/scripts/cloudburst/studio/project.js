var selectedItem=null;
var optVals = [];
var projectAreas=[];
var userroledata=null;
var selectedText=null;
var content=null;
/*var array=[];
var hamletDetails=[];
var adjName=null;
var adjList=null;
var hamletList=null;
var checkAdjEdit=false;
var checkHamletEdit=false;
var HamletName=null;
var HamletAlias=null;
var HamletCode=null;
var checkeditHam=false;*/


function Project(_selectedItem)
{	

	//jQuery("#tableGrid").empty();	
	selectedItem=_selectedItem;
	checkAdjEdit=false;
	checkHamletEdit=false;

	jQuery.ajax({          
		type: 'GET',
		url: "project/userbyrole/",
		success: function (popdata) 
		{

			userroledata=popdata;

		}         
	});


	if( jQuery("#ProjectFormDiv").length<=0){

		displayRefreshedProject();

	}
	else{

		displayProject();
		displayRefreshedProject();

	}
}


function displayRefreshedProject(){
	/*$('body').find("#hamlet-dialog-form").remove();
	$('body').find("#adjudicator-dialog-form").remove();

	$("#hamlet-dialog-form").remove();*/
	jQuery.ajax({
		url: "project/" ,
		success: function (data) {
			jQuery("#projects").empty(); 	
			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {

				jQuery("#projects").append(template);
				
				var lanChnag=new changeLang();
				lanChnag.first();
				
				jQuery('#ProjectFormDiv').css("visibility", "visible");

				jQuery("#projectDetails").hide();	        	
				jQuery("#ProjectsRowData").empty();
				jQuery("#projectTable").show();	        		        	
				jQuery("#project_accordion").hide();		    	

				jQuery("#ProjectTemplate").tmpl(data).appendTo("#ProjectsRowData");

				jQuery("#project_btnSave").hide();
				jQuery("#project_btnBack").hide();
				jQuery("#project_btnNew").show();



				//$("#project_txtSearch").trigger("keyup");

				$("#projectTable").tablesorter({ 
					headers: {6: {sorter: false  },  7: {  sorter: false } },	
					debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
					.tablesorterPager({ container: $("#project_pagerDiv"), positionFixed: false })
					.tablesorterFilter({ filterContainer: $("#project_txtSearch"),                           
						filterColumns: [0],
						filterCaseSensitive: false,
						filterWaitTime:1000 
					});


			});

		}
	});

	GetActiveLayer('default','');	
}

function displayProjectNew(){

	/*checkAdjEdit=false;
	checkHamletEdit=false;*/
	displayProject();
}
function displayProject(){


	jQuery("#project_accordion").hide();
	jQuery("#projectTable").show();
	jQuery("#project_btnSave").hide();
	jQuery("#project_btnBack").hide();
	jQuery("#project_btnNew").show();

}


var proj_units = null;
var proj_projections = null;
var proj_formats = null;
var proj_editData = null;
var proj_layerTypes = null;
var proj_layerGroup = null;
var sortedLyrGroup = null;
var proj_users = null;
var proj_baselayer=null;
var proj_country=null;
var editableProject;
//var proj_region=null;

var createEditProject = function (_name) 
{	
	checkeditHam=false;
	editableProject=_name;
	var projurl=window.location.protocol+'//'+window.location.host+'/'+'spatialvue-viewer/?project='+_name+'&_token=';

	optVals = [];
	jQuery("#project_btnNew").hide();    
	jQuery("#project_btnSave").hide();
	jQuery("#project_btnBack").hide();
	//jQuery("#project_pagerDiv").hide();
	//jQuery("#searchDiv").hide();

	//jQuery("#projectTable").empty();
	jQuery("#projectTable").hide();
	jQuery("#projectDetails").show();    
	jQuery("#projectGeneralBody").empty();
	jQuery("#projectConfigurationBody").empty();
	jQuery("#projectLayergroupBody").empty();
	jQuery("#projectBaselayerBody").empty();

	//jQuery("#projectDisclaimerBody").empty();
	jQuery("#projectUserList").empty(); 

	/*APARESH
	jQuery("#layerGroupList").empty();
	 */



	jQuery.ajax({
		url: "unit/" + "?" + token,
		success: function (data) {
			proj_units = data;
		},
		async: false
	});

	jQuery.ajax({
		url: "layergroup/" + "?" + token,
		success: function (lgdata) {

			proj_layerGroup = lgdata;			


		},
		async: false
	});

	jQuery.ajax({
		url: "baselayer/" + "?" + token,
		success: function (bldata) {
			proj_baselayer = bldata;


		},
		async: false
	});



	jQuery.ajax({
		url: "projection/" + "?" + token,
		success: function (data) {
			proj_projections = data;
		},
		async: false
	});
	jQuery.ajax({
		url: "outputformat/" + "?" + token,
		success: function (data) {
			proj_formats = data;
		},
		async: false
	});

	jQuery.ajax({
		url: "user/" + "?" + token,
		success: function (userdata) {
			proj_users = userdata;        	                

		},
		async: false
	});

	//add for project configuration by nk
	jQuery.ajax({
		url: "projectcontry/",
		success: function (userdata) {
			proj_country = userdata;        	                

		},
		async: false
	});	



	if (_name) 
	{


	/*	jQuery.ajax({
			url: "adjudicators/"+_name,
			success: function (adjudicatorList) {
				adjList=adjudicatorList;


			},
			async: false
		});	

		jQuery.ajax({
			url: "hamlet/"+_name,
			success: function (lsthamlet) {

				hamletList=lsthamlet;
			},
			async: false
		});	*/

		jQuery('#name').attr('readonly', true);
		jQuery.ajax({
			url: selectedItem+"/" + _name + "?" + token,
			async:false,
			success: function (data) {

				sortedLyrGroup = new Array(proj_layerGroup.length);
				selectedLyrGroups = data.projectLayergroups;
				var _i;
				for(_i=0; _i<selectedLyrGroups.length; _i++){
					for(_j=0; _j<proj_layerGroup.length; _j++){
						if(selectedLyrGroups[_i].layergroups.name == proj_layerGroup[_j].name){
							sortedLyrGroup[_i] = proj_layerGroup[_j];
							proj_layerGroup.splice(_j, 1);
							break;
						}
					}
				}


				for(_k=0; _k<proj_layerGroup.length; _k++, _i++){
					sortedLyrGroup[_i] = proj_layerGroup[_k];
				}

				jQuery("#ProjectTemplateForm").tmpl(data,

						{

						}

				).appendTo("#projectGeneralBody");

				jQuery("#ProjectTemplateLayergroup").tmpl(data,

						{

						}

				).appendTo("#projectLayergroupBody");


				jQuery("#projectConfigurationTemplate").tmpl(data,


						{

						}

				).appendTo("#projectConfigurationBody");
				
				

				// add value for project configuration start by RMSI NK (also villagechairmanId for save text three field value)

				jQuery.each(proj_country, function (i, countryObj) {    	
					jQuery("#countryId").append(jQuery("<option></option>").attr("value", countryObj.country_id).text(countryObj.country_name)); 

				});

				jQuery("#hid_idseq").val(data.id);



				if(data.projectAreas.length>0)
				{

					jQuery("#countryId").val(data.projectAreas[0].commune_id.province.region.country_id.country_id);
					jQuery("#hid_id").val(data.projectAreas[0].areaId);
					getRegionOnCountryChange(data.projectAreas[0].commune_id.province.region.country_id.country_id);					
					jQuery("#regionId").val(data.projectAreas[0].commune_id.province.region.regionId);
					getProvinceOnRegionChange(data.projectAreas[0].commune_id.province.region.regionId);
					jQuery("#provinceId").val(data.projectAreas[0].commune_id.province.provinceId);
					getCommuneonProvinceChange(data.projectAreas[0].commune_id.province.provinceId);
					jQuery("#communeId").val(data.projectAreas[0].commune_id.communeId);
					
					/*
					jQuery("#districtId").val(data.projectAreas[0].districtName);
					jQuery("#hamletId").val(data.projectAreas[0].hamlet);
					jQuery("#villageId").val(data.projectAreas[0].village);	*/

					/*jQuery("#villagechairmanId").val(data.projectAreas[0].villageChairman);	
					jQuery("#executiveofficerId").val(data.projectAreas[0].approvingExecutive);	
					jQuery("#districtofficerId").val(data.projectAreas[0].districtOfficer);*/

					//Added Village Code and Village Postal Code
					jQuery("#villagecode").val(data.projectAreas[0].village_code);
					jQuery("#villagepostalcode").val(data.projectAreas[0].address);
					
					
					jQuery("#cfvpresident").val(data.projectAreas[0].presidentname);
					jQuery("#mayorname").val(data.projectAreas[0].mayorname);
				}


				// add value for project configuration end by RMSI NK 

/*
				jQuery("#ProjectTemplateDisclaimer").tmpl(data,

						{

						}

				).appendTo("#projectDisclaimerBody");*/

				jQuery("#projectLayergroupBody").empty();


				jQuery("#ProjectTemplateLayergroup").tmpl(sortedLyrGroup,

						{

						}

				).appendTo("#projectLayergroupBody");	 

				jQuery("#projectBaselayerBody").empty();
				jQuery("#ProjectTemplateBaselayer").tmpl(proj_baselayer,

						{

						}

				).appendTo("#projectBaselayerBody");





				jQuery("#projectUserList").empty();

				jQuery("#ProjectTemplateUser").tmpl(userroledata).appendTo("#projectUserList");
				
				

			/*	if (adjList.length>0)
				{
					checkAdjEdit=true;
					jQuery("#temporaryAdjDiv").empty();

		        	for (var i = 0; i < adjList.length; i++) {
		        		 selectedText=adjList[i].adjudicatorName;

		        		content = "<table>"
		        			content += '<tr><td>' + '<label id="adjudicator_name'+""+i+""+'">'+""+selectedText+""+'</label><input type="hidden" name="project_adjudicatorhid" value='+""+selectedText+""+'> '+'</td>';
		        			content += '<td align="center">' + '<div><a href="javascript:deleteAdjudicator('+i+');"><img src="resources/images/studio/delete.png" title="Delete"/></a></div>' + '</td></tr>';
		        			content += "</table>"

		        				jQuery("#temporaryAdjDiv").append(content);	
		        			jQuery('#adjudicator_name'+""+i+""+'').val(selectedText);
		        	}
					 
					array=[];
					for (var i = 0; i < adjList.length; i++) {
						selectedText=adjList[i].adjudicatorName;
						array.push(selectedText);
					}
					addPersonAdjudicator('new'); 
				}*/
				/*if (hamletList.length>0)
				{
					checkHamletEdit=true;
					hamletDetails=[];
					for (var i = 0; i < hamletList.length; i++) {
						HamletName=hamletList[i].hamletName;
						HamletAlias=hamletList[i].hamletNameSecondLanguage;
						HamletCode=hamletList[i].hamletCode;
						hamletDetails.push(HamletName);
						hamletDetails.push(HamletAlias);
						hamletDetails.push(HamletCode);
					}
					addHamlet('new'); 
				}*/

				jQuery('#name').attr('readonly', true);

				jQuery.each(proj_units, function (i, value) {    	
					jQuery("#project_unit").append(jQuery("<option></option>").attr("value", value.name).text(value.description));        
				});

				jQuery.each(proj_formats, function (i, value) {    	
					jQuery("#project_outputFormat").append(jQuery("<option></option>").attr("value", value.name).text(value.name));        
				});


				$('[class^="tr-"]').hide();

				/*APARESH 
                jQuery.each(proj_layerGroup, function (i, value) {    	
                	jQuery("#layerGroupList").append(jQuery("<option></option>").attr("value", value.name).text(value.name));        
                });
				 */
				//set DD value
				jQuery("#project_unit").val(data.unit.name);
				jQuery("#project_outputFormat").val(data.outputformat.name);

				if (data.disclaimer != null  &&  data.disclaimer != "") {
					jQuery("#chkDisclaimer").attr('checked', true);
					jQuery('#Disclaimer').css("visibility", "visible");
					jQuery('#Disclaimer').val(data.disclaimer);
				}
				else {
					jQuery("#chkDisclaimer").attr('checked', false);
					jQuery('#Disclaimer').val("");
					jQuery('#Disclaimer').css("visibility", "hidden");
				}

				//set layergroup list value  APARESH
				var layergrouporder={};

				jQuery.each(data.projectLayergroups, function (i, layergroupList) {      
					layergrouporder[layergroupList.grouporder]=layergroupList.layergroups.name;                	                	
				});

				for(var i=1; i<=data.projectLayergroups.length;i++){

					jQuery("#addedLayerGroupList").append(jQuery("<option></option>").attr("value", layergrouporder[i]).text(layergrouporder[i]));                        
					jQuery("#layerGroupList option[value=" + layergrouporder[i] + "]").remove();

				}

				jQuery.each(data.projectLayergroups, function (i, layergroupList) {                                            

					jQuery("#"+layergroupList.layergroups.name).attr('checked', true);

				});

				jQuery.each(data.projectBaselayers, function (i, baselayersList) {                                            

					//jQuery("#"+baselayersList.baselayers.name).attr('checked', true);
					$("input[id='"+baselayersList.baselayers.name+"']").attr('checked', true);  

				});

				if(data.projectBaselayers.length>0){
					if(data.projectBaselayers[0].baselayers.name.indexOf("Google_") > -1){
						populatebaselayer('Google',data);

					}
					else if(data.projectBaselayers[0].baselayers.name.indexOf("Bing") > -1){
						populatebaselayer('Bing',data);
					}		
					else if(data.projectBaselayers[0].baselayers.name.indexOf("Open_") > -1){
						populatebaselayer('Open',data);
					}
					else if(data.projectBaselayers[0].baselayers.name.indexOf("MapQuest") > -1){
						populatebaselayer('MapQuest',data);
					}
				}
				else{

					populatebaselayer('Google','');

				}
				/*jQuery.each(data.projectLayergroups, function (i, layergroupList) {                    
                        jQuery('#addedLayerGroupList').append(jQuery("<option></option>").attr("value", layergroupList.layergroups.name).text(layergroupList.layergroups.name));
                        jQuery("#layerGroupList option[value=" + layergroupList.layergroups.name + "]").remove();
                });*/

				GetActiveLayer('default','');

				//Setting activelayer and Overview layer
				$('#activelayer').val(data.activelayer);
				$('#overlaymap').val(data.overlaymap);

				jQuery.ajax({
					url: selectedItem+"/" + _name+"/user" + "?" + token,
					async:false,
					success: function (users) {
						jQuery.each(users, function (i, value) {    	
							//jQuery('#'+value).attr('checked', true);
							$("input[id='"+value+"']").attr('checked', true);
							jQuery(".tr-"+value).show();
						});

					}
				}); 

				//$("#lyrgrouptbl tr:odd").addClass('alt-tr'); 
				//$("#baselyrtbl tr:odd").addClass('alt-tr');
			},
			cache: false
		});
	} else {

		jQuery("#ProjectTemplateForm").tmpl(null,

				{        	

				}
		).appendTo("#projectGeneralBody");

		jQuery("#ProjectTemplateLayergroup").tmpl(null,

				{

				}

		).appendTo("#projectLayergroupBody");

		jQuery("#projectConfigurationTemplate").tmpl(null,

				{

				}

		).appendTo("#projectConfigurationBody");


/*		jQuery("#ProjectTemplateDisclaimer").tmpl(null,

				{
				}

		).appendTo("#projectDisclaimerBody");*/

		// add value for project configuration end 

		jQuery("#projectLayergroupBody").empty();		
		jQuery("#ProjectTemplateLayergroup").tmpl(proj_layerGroup,

				{

				}

		).appendTo("#projectLayergroupBody");	 

		jQuery("#projectBaselayerBody").empty();
		jQuery("#ProjectTemplateBaselayer").tmpl(proj_baselayer,

				{

				}

		).appendTo("#projectBaselayerBody");

		//hide all base layer

		
			
		
		jQuery("#projectBaselayerBody tr").hide();
		//$("#lyr_type").attr('selectedIndex', 2);
		$("#lyr_type").val('Google');
		populatebaselayer('Google');

		jQuery("#projectUserList").empty();

		/*jQuery("#ProjectTemplateUser").tmpl(proj_users,

			{
				//roles: user_roleList
			}

		 ).appendTo("#projectUserList");*/
		jQuery("#ProjectTemplateUser").tmpl(userroledata).appendTo("#projectUserList");
		jQuery('#name').removeAttr('readonly');
		jQuery('#numzoomlevels').val(18);

		jQuery.each(proj_country, function (i, value) {    	
			jQuery("#countryId").append(jQuery("<option></option>").attr("value", value.country_id).text(value.country_name)); 

		});
		jQuery.each(proj_units, function (i, value) {    	
			jQuery("#project_unit").append(jQuery("<option></option>").attr("value", value.name).text(value.description));        
		});

		jQuery.each(proj_formats, function (i, value) {    	
			jQuery("#project_outputFormat").append(jQuery("<option></option>").attr("value", value.name).text(value.name));        
		});
		/*jQuery("#temporaryAdjDiv").empty();
		jQuery("#temporaryHamletDiv").empty();*/

		/*APARESH
        jQuery.each(proj_layerGroup, function (i, value) {    	
        	jQuery("#layerGroupList").append(jQuery("<option></option>").attr("value", value.name).text(value.name));        
        });
		 */
	}
	var lanChnag=new changeLang();
	lanChnag.first();
	
	jQuery("#project_accordion").show();  
	jQuery("#project_accordion").accordion({fillSpace: true});
	jQuery("#project_btnSave").show();
	jQuery("#project_btnBack").show();

} 

function changeActiveValue(_this) {
	if (_this.checked) {

		jQuery('#active').val("true");
	}
	else {

		jQuery('#active').val("false");
	}

}

function changeCosmeticValue(_this) {
	if (_this.checked) {

		jQuery('#cosmetic').val("true");
	}
	else {

		jQuery('#cosmetic').val("false");
	}

}
function toggleDisclaimer(_this) {

	if (_this.checked) {

		jQuery('#Disclaimer').css("visibility", "visible");
	}
	else {
		// jQuery('#Disclaimer').val("");
		jQuery('#Disclaimer').css("visibility", "hidden");
	}
}

function populateActivelayer(_this){

	if(_this.checked){

		GetActiveLayer('add',_this.value);

	}
	else
	{
		GetActiveLayer('remove',_this.value);
	}

}

function GetActiveLayer(type,selectedGroup) {
	if(type=='add' || type=='remove'){

		jQuery.ajax({
			url: "layergroup/" + selectedGroup + "?" + token,
			async: false,
			success: function (data) 
			{ 
				
				for (var j = 0; j < data[0].layers.length; j++) 
				{	
					if (type == 'add') 
					{
						jQuery('#activelayer').empty();
						jQuery('#overlaymap').empty();
						if(!isTilecache(data[0].layers[j].layer))
						{								
							jQuery('#activelayer').append(jQuery("<option></option>").attr("value", data[0].layers[j].layer).text(data[0].layers[j].layer));									
						}
						jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", data[0].layers[j].layer).text(data[0].layers[j].layer));                          

					}							
					else if (type == 'remove') 
					{
						jQuery("#activelayer option[value="+data[0].layers[j].layer+"]").remove();
						jQuery("#overlaymap option[value="+data[0].layers[j].layer+"]").remove();								
					}

				}
			}



		});

	}
	else if(type=='default'){
		jQuery('#activelayer').empty();
		jQuery('#overlaymap').empty();

		jQuery('#activelayer').append(jQuery("<option></option>").attr("value", '').text(PLEASE_SELECT));
		jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", '').text(PLEASE_SELECT));

		$('#projectLayergroupBody input[type="checkbox"]:checked').each(function() { 
			var lyrGrp=$(this).val();

			jQuery.ajax({
				url: "layergroup/" + lyrGrp + "?" + token,
				async: false,
				success: function (data) {            	

					for (var j = 0; j < data[0].layers.length; j++) {                        

						if(!isTilecache(data[0].layers[j].layer)){
							jQuery('#activelayer').append(jQuery("<option></option>").attr("value", data[0].layers[j].layer).text(data[0].layers[j].layer));								                          
						}
						jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", data[0].layers[j].layer).text(data[0].layers[j].layer));

					}
				}

			});


		});

	}

	// comment for add
	if (type == 'add') {

		optVals.push(selectedGroup);
	}
	else if (type == 'remove') {

		optVals.pop(selectedGroup);
	}
	else {

		$('#projectLayergroupBody input[type="checkbox"]:checked').each(function() { 
			optVals.push($(this).val());		
		});


	}

	jQuery('#activelayer').empty();
	jQuery('#overlaymap').empty();

	jQuery('#activelayer').append(jQuery("<option></option>").attr("value", '').text(PLEASE_SELECT));
	jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", '').text(PLEASE_SELECT));

	for (var i = 0; i < optVals.length; i++) {
		var lyrGrp = optVals[i];
		jQuery.ajax({
			url: "layergroup/" + lyrGrp,
			async: false,
			success: function (data) {


				for (var j = 0; j < data[0].layers.length; j++) {                        
					jQuery('#activelayer').append(jQuery("<option></option>").attr("value", data[0].layers[j].layer).text(data[0].layers[j].layer));
					jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", data[0].layers[j].layer).text(data[0].layers[j].layer));


				}
			}

		});
	}

}

var saveProjectData= function () {
	jQuery.ajax({
		type: "POST",              
		url: "project/create" + "?" + token+"&emailid="+useremail,
		data: jQuery("#projectForm").serialize(),
		success: function (result) {        
			if(result=='ProjectAdded'){
				jAlert(DATA_SAVED, ALERT);

				displayRefreshedProject();
			}

			if(result=='DuplicateName'){

				jAlert(Project_EXISTS, ALERT);

			}

			if(result=='EnterName'){

				jAlert(ENTER_PROJECT, ALERT);


			}

			if(result=='false'){

				jAlert(Save_error,Alert);

				displayRefreshedProject();
			}

		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(ERROR_MSG, ALERT);

		}
	});

}

var saveProject= function () {
	/*checkAdjEdit=false;
	checkHamletEdit=false;*/
	$("#projectForm").validate({
		ignore:[],
		rules: {
			"name": "required",
			"activelayer": "required",
			"projection.code": "required",
			"unit.name": "required",
			"maxextent": "required",
			"minextent": "required",
			"restrictedextent": "required",
			"numzoomlevels": {
				required: true,
				digits: true
			},
			"displayProjection.code": "required",
			"outputFormat.name": "required",
			//"copyright": "required",
			//"watermask": "required",
			"overlaymap":"required",
			"countryId":"required"



		},
		messages: {

			"name": FIELD_REQ,

			"projection.code": FIELD_REQ,
			"unit.name": FIELD_REQ,

			"maxextent": FIELD_REQ,
			"minextent": FIELD_REQ,
			"restrictedextent": FIELD_REQ,
			"numzoomlevels": {
				required: FIELD_REQ,
				digits: FIELD_REQ 
			},
			"displayProjection.code": FIELD_REQ,
			"outputFormat.name": FIELD_REQ,
			//"copyright": "Please Enter  CopyRight ",
			//"watermask": "Please Enter  WaterMask ",

			"activelayer":FIELD_REQ,
			"overlaymap":  FIELD_REQ,
			"countryId":FIELD_REQ

		}

	});

	if ($("#projectForm").valid()) {

	/*	if (array.length>=2){


			if (!$('#temporaryHamletDiv').is(':empty')){*/



				if (jQuery('#active').val()=="") {

					jQuery('#active').val("false");
				}

				if (jQuery('#cosmetic').val()=="") {

					jQuery('#cosmetic').val("false");
				}

				//comment APARESH
				/*$("#addedLayerGroupList option").attr("selected", "selected");*/


				/*if ($("#chkDisclaimer").attr('checked')) {
					if(!$('#Disclaimer').val()){
						jAlert('Enter Disclaimer ', 'Project');
						return;
					}
				}*/   

				/*if (!jQuery("#chkDisclaimer").attr('checked')) {
					$('#Disclaimer').val("");
				}*/
				//comment APARESH
				if (jQuery('#addedLayerGroupList option').size() > 0) {
					jQuery("#addedLayerGroupList option").attr("selected", "selected");
					saveProjectData();
				}
				/*else {	    
	    jAlert('Add atleast one Layer Group', 'Project');
	    return;
	}*/

				if($('.userCheckbox').filter(":checked").length>0){
					saveProjectData();
				}
				else{


					jAlert(Select_one,Alert);

				}
			}
			/*else{
				jAlert("Add atleast one hamlet");
			}
		}
		else{
			jAlert("Add atleast two adjudicator");
			//checkAdjEdit=true;
		}
	}*/
	if (!$("#projectForm").valid()==true)

	{

		jAlert(Fill_Mandatory,Alert);


	}

}

//////////Delete Record/////////////////

var deleteProject= function (_projectName) {

	jConfirm(DELETE_MSG+' : <strong>' + _projectName + '</strong>', Delete_Confirmation, function (response) {

		if (response) {
			jQuery.ajax({          
				url: "project/delete/"+_projectName + "?" + token,
				success: function () { 


					jAlert(DELETE_SUCCESS, ALERT);

					displayRefreshedProject();
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {

					jAlert(FAILED);
				}
			});
		}

	});

}

/////////////////////////

//APARESH comment
jQuery(function () {

	jQuery("#addLayerGroup").live('click', function () {

		jQuery("#layerGroupList  option:selected").appendTo("#addedLayerGroupList");
	});

	jQuery("#removeLayerGroup").live('click', function () {
		jQuery("#addedLayerGroupList option:selected").appendTo("#layerGroupList");
	});


	jQuery("#upLayerGroup").live('click', function () {
		jQuery('#addedLayerGroupList option:selected').each(function () {
			var newPos = jQuery('#addedLayerGroupList option').index(this) - 1;
			if (newPos > -1) {
				jQuery('#addedLayerGroupList option').eq(newPos).before("<option value='" + jQuery(this).val() + "' selected='selected'>" + jQuery(this).text() + "</option>");
				jQuery(this).remove();
			}
		});
	});


	jQuery("#downLayerGroup").live('click', function () {
		var countOptions = jQuery('#addedLayerGroupList option').size();
		jQuery('#addedLayerGroupList option:selected').each(function () {
			var newPos = jQuery('#addedLayerGroupList option').index(this) + 1;
			if (newPos < countOptions) {
				jQuery('#addedLayerGroupList option').eq(newPos).after("<option value='" + jQuery(this).val() + "' selected='selected'>" + jQuery(this).text() + "</option>");
				jQuery(this).remove();
			}
		});
	});

});

function isTilecache(layeralias){
	var response=false;
	jQuery.ajax({
		url: "layer/" + layeralias+ "?" + token,
		async:false,
		success: function (layer) {

			if(layer.layertype.name=='Tilecache')
			{
				response=true;
			}
			else{
				response=false;
			}

		}
	});
	return response;
}

jQuery(function () {

	$(".lgup,.lgdown").live('click',function(){
		var row = $(this).parents("tr:first");
		if ($(this).is(".lgup")) {
			row.insertBefore(row.prev());
		} else {
			row.insertAfter(row.next());
		}
	});

});

function populatebaselayer(_type){

	jQuery("#lyr_type").val(_type);
	if(_type=='Google'){
		//uncheck bing
		jQuery('input[id^="Bing_"]').attr('checked', false);
		jQuery('input[id^="Open_"]').attr('checked', false);
		jQuery('input[id^="MapQuest"]').attr('checked', false);

	}
	else if(_type=='Bing'){
		//uncheck google
		jQuery('input[id^="Google_"]').attr('checked', false);
		jQuery('input[id^="Open_"]').attr('checked', false);
		jQuery('input[id^="MapQuest"]').attr('checked', false);
	}
	else if(_type=='Open'){

		jQuery('input[id^="Google_"]').attr('checked', false);
		jQuery('input[id^="Bing_"]').attr('checked', false);
		jQuery('input[id^="MapQuest"]').attr('checked', false);
	}
	else if(_type=='MapQuest'){

		jQuery('input[id^="Google_"]').attr('checked', false);
		jQuery('input[id^="Bing_"]').attr('checked', false);
		jQuery('input[id^="Open_"]').attr('checked', false);
	}

	jQuery("#projectBaselayerBody tr").hide();

	jQuery('tr[id^=tr-'+_type+'_]').show();

}	

function toggleUserChecked(status) {
	$(".userCheckbox").each( function() {
		$(this).attr("checked",status);		
	})

	if(status){
		$('[class^="tr-"]').show();
	}
	else{
		$('[class^="tr-"]').hide();
	}
}

function manageProjLink(_this){

	if(_this.checked==true){
		jQuery('.tr-'+_this.value).show();
	}
	else{
		jQuery('.tr-'+_this.value).hide();
	}

}

/* ************@RMSI/NK add for region to district*******************************/

function getRegionOnCountryChange(countryname)
{

	if(countryname!='')
	{

		jQuery.ajax({
			url: "projectregion/"+countryname,
			async: false,
			success: function (regiondata) {

				var proj_region = regiondata; 

				jQuery("#regionId").empty();
				jQuery("#regionId").append(jQuery("<option></option>").attr("value", 0).text(PLEASE_SELECT)); 
				jQuery.each(proj_region, function (i, regionObj) {
					//jQuery("select#regionId") .append('<option>select</option>');;

					//$("#regionId").append("<option value='select' ></option>");
					//jQuery.each(proj_country, function (i, value) {    	
					//jQuery("#countryId").append(jQuery("<option></option>").attr("value", value).text(value)); 
					jQuery("#regionId").append(jQuery("<option></option>").attr("value", regionObj.regionId).text(regionObj.regionName)); 

				});
			},
		}); 
	}
	
}


function getProvinceOnRegionChange(regionname)
{

	if(regionname!='')
	{

		jQuery.ajax({
			url: "projectprovince/"+regionname,
			async: false,
			success: function (provincedata) {

				var proj_province = provincedata;      
				
				jQuery("#provinceId").empty();
				
				jQuery("#provinceId").append(jQuery("<option></option>").attr("value", 0).text(PLEASE_SELECT));

				//jQuery("#districtId").append(jQuery("<option></option>").attr("value", "").text("select");


				jQuery.each(proj_province, function (i, value) {   
					jQuery("#provinceId").append(jQuery("<option></option>").attr("value", value.provinceId).text(value.provinceName));

				});


			},

		}); 
 
	}
	
}


function getCommuneonProvinceChange(provincename)
{

	if(provincename!='')
	{	jQuery.ajax({
		url: "projectcommune/"+provincename,
		async: false,
		success: function (communedata) {

			var proj_commune = communedata; 

			jQuery("#communeId").empty();
			
			jQuery("#communeId").append(jQuery("<option></option>").attr("value", 0).text(PLEASE_SELECT));
			//jQuery("#villageId").empty();

			//jQuery("#villageId").append(jQuery("<option></option>").attr("value", "select").text("select"));

			jQuery.each(proj_commune, function (i, value) {    	
				jQuery("#communeId").append(jQuery("<option></option>").attr("value", value.communeId).text(value.communeName));
				

			});   	


		},      	                

	});  
}
	
}

