var selectedItem=null;
var dataList=null;
var projList=null;
var spatialList=null;
var socialtenureList=null;
var associatednaturalPersonList=null;
var genderList=null;
var maritalList=null;
var tenuretypeList=null;
var multimediaList=null;
var educationsList=null;
var landUserList=null;
var tenureclassList=null;
var occtypeList=null;
var workflowhistoryList=null;
var socialEditTenureList=null;
var project=null;
var sourceDocList=null;
var usinId=null;
var read=null;
var soilQualityList=null;
var typeofLandList=null;
var slopeList=null;
var groupTypeList=null;
var attributeList=null;
var vectors=null;
var DeletedNaturalList=null;
var DeletedNonNaturalList=null;
var deletedAdminList=null;
var ProjectAreaList=null;
var personList=null;
var ID=null;
var year=null;
var activeProject="";
var URL=null;
var resultDeleteNatural=null;
var records_from=0;
var totalRecords=null;
var searchRecords=null;
var eduList=null;
var naturalPerson_gid=null
var administratorID=null;
var adminDataList=null;
var checkNewNatural=false;
var hamletList=null;
var person_subtype=null;
var selectedHamlet=null;
var deceasedPersonList=null;
var validator=null;
var adjudicatorList=null;
var checkpersonType=false;
var workFlowLst=[];
var noaList=null;
var mutationTypeList=null;
var natureOfPowerList=null;
var vertexTempList=[];
var actionList=null;
var commentsStatusList=null;
var features=null;
var titleList=null;
var workflowTmp=0;
var sharetype=null;
var villageList=null;
function LandRecords(_selectedItem)
{


	selectedItem=_selectedItem;
	URL="landrecords/spatialunit/default/"+0;
	if(activeProject!="")
	{  


	}
	else{
		activeProject='default';

	}

	searchRecords=null;
	jQuery.ajax({ 
		url: "landrecords/status/",
		async:false,							
		success: function (data) {	

			statusList=data;
		}


	});

	var workFlows = new workflowStatus();
	workFlowLst=workFlows.AllworkFlow();

	workflowTmp=0;

	displayRefreshedLandRecords('All');
	$('body').find("#editattribute-dialog-form").remove();

}



function displayRefreshedLandRecords(_landRecord)
{

	$('body').find("#approveParcel-dialog-form").remove();
	var URL="landrecords/";
	if(activeProject!=""){

		URL="landrecords/"+activeProject;
	}
	jQuery.ajax({
		url: URL,
		success: function (data) {
			projList=data;

			jQuery("#landrecords-div").empty();

			jQuery.get("resources/templates/viewer/" + selectedItem + ".html", function (template) {

				jQuery("#landrecords-div").append(template);

				jQuery('#landRecordsFormdiv').css("visibility", "visible");		    			    	

				jQuery("#landRecordsRowData").empty();

				jQuery("#landRecordsTable").show();	        	
				

				var lanChnag=new changeLang();
				lanChnag.first();
				if(lang=="en"){
					jQuery("#status_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select")); 
				}
				else if(lang=="fr"){
				jQuery("#status_id").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				}
				
				jQuery.each(statusList, function (i, statusobj) {
					if(lang=="en")
						jQuery("#search_appStatus").append(jQuery("<option></option>").attr("value", statusobj.workflowStatusId).text(statusobj.workflowStatus)); 
					else if(lang=="fr")
						jQuery("#search_appStatus").append(jQuery("<option></option>").attr("value", statusobj.workflowStatusId).text(statusobj.workflow_status_fr)); 

				});
				

				$( "#workflow-accordion" ).accordion(

				);


				jQuery("#workFlowTemplate").tmpl(workFlowLst).appendTo("#newtbl");

				/*$('#workflowDiv').append('<span class="workflow-head">Existing</span>');*/

				jQuery("#workFlowTemplate2").tmpl(workFlowLst).appendTo("#existingtbl");


				/*		var tmphtml="";
				tmphtml+='<div style=" position: absolute; bottom: 0px; right: 0px;  background: #f5f5f5; width: 100%; "><button  onclick="javascript:generateReport()"';
				tmphtml+='class="btn1  pull-left" id="generatereport" style="margin-bottom: 5px; margin-right: 5px;">Generate Report</button>';
				tmphtml+='<button  onclick="javascript:updatespatialwork()" class="btn1  pull-right" id="workflowupdate"';
				tmphtml+='style="margin-bottom: 5px; margin-right: 5px;">Filter</button></div>';

				$('#workflowDiv').append(tmphtml);*/
				if(roleId==9){
					$('#vtab').removeClass( "vtab" );
					$('#workflowDiv').hide();
				}

				$('.roleCheckbox').each(function(){
					this.checked = true;
				});
				spatialRecords(0);



				jQuery("#projectName").text(data.name);
				jQuery("#country").text(data.countryName);
				jQuery("#region").text(data.region);
				jQuery("#province").text(data.commune_id.province.provinceName);
				jQuery("#commune").text(data.commune_id.communeName);
				/*	jQuery("#hamlet").text("--");
				if(data.hamlet!="" && data.hamlet!=null ){

					jQuery("#hamlet").text(data.hamlet);
				}*/
				$("#landRecordsTable").trigger("update");


				$("#landRecordsTable").tablesorter( {sortList: [[0,1], [1,0]] } ); 


				langChangeStrings();

			});		

		}
	});

}

function displayRefreshedTenure()
{
	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/socialtenure/"+id,
		async:false,							
		success: function (data) {	

			jQuery("#tenureRowData").empty();
			if(data!="")
			{

				jQuery("#tenureinfoTemplate").tmpl(data).appendTo("#tenureRowData");

			}

		}

	});
}


function editAttributeNew(usin,statusId)

{
	if(statusId==1 || statusId==4 || statusId==6)
	{
		read=false;
		editAttribute(usin);


	}

	else{
		read=true;
		editAttribute(usin);

	}

}
function editAttribute(id)
{
	$('#_loader').show();
	mapCoordinatesUrl(id);

	//jQuery(".hideButton").show();
	//displayAttributeCategory(1,id);


	jQuery.ajax({ 
		url: "landrecords/landusertype/",
		async:false,							
		success: function (data) {	

			landUserList=data;
		}


	});


	jQuery.ajax({ 
		url: "landrecords/appnature/",
		async:false,							
		success: function (data) {	

			noaList=data;
		}


	});

	jQuery.ajax({ 
		url: "landrecords/mutationtype/",
		async:false,							
		success: function (data) {	

			mutationTypeList=data;
		}


	});

	jQuery.ajax({ 
		url: "landrecords/title/",
		async:false,							
		success: function (data) {	

			titleList=data;
		}


	});

	jQuery.ajax({ 
		url: "landrecords/personwithinterest/"+id,
		async:false,							
		success: function (data) {	

			nxtTokin=data;

			jQuery("#nxtTokinRowData").empty();
			if(nxtTokin.length!=0 && nxtTokin.length!=undefined)
			{
				for(var i=0;i<nxtTokin.length;i++)
				{
					jQuery("#nxtTokinTemplate").tmpl(nxtTokin[i]).appendTo("#nxtTokinRowData");
				}
				jQuery("#hideTable1").show();
			}
			else{

				jQuery("#hideTable1").hide();
			}

		}


	});

	jQuery.ajax({ 
		url: "landrecords/multimedia/edit/"+id,
		async:false,							
		success: function (data) {
			multimediaList=data;
			jQuery("#multimediaRowData").empty();
			if(data.length!=0 && data.length!=undefined)
			{
				jQuery("#multimediaTemplate").tmpl(data).appendTo("#multimediaRowData");
				jQuery("#multimediaTable").show();
			}

		}

	});

	jQuery("#primary").val(id);

	jQuery.ajax({ 
		url: "landrecords/socialtenure/"+id,
		async:false,							
		success: function (data) {	

			socialtenureList=data;

			jQuery("#tenureRowData").empty();
			jQuery("#naturalpersonRowData").empty();
			jQuery("#non_naturalpersonRowData").empty();
			jQuery("#associatedNaturalRowData").empty();
			jQuery('#naturalTable').hide();
			jQuery("#associatedNaturalTable").hide();
			jQuery("#non_naturalTable").hide();
			jQuery("#tenureTable").hide();
			//jQuery("#multimediaTable").hide();




			if (data!='' )
			{
				if(data[0].share_type.gid==7)
				{
					$('.hidePOI').hide();
				}
				if(data[0].share_type.gid==8)
				{
					$('.hidePOI').show();
				}
				jQuery("#tenureRowData").empty();

				jQuery("#tenureinfoTemplate").tmpl(data[0]).appendTo("#tenureRowData");

				for(i=0;i<data.length;i++)
				{	



					if(data[i].person_gid.person_type_gid.person_type_gid==1)
					{
						checkpersonType=false;
						jQuery('.hidenonTable').hide();
						if(data[i].person_gid.active==true)
						{	
							jQuery("#naturalTable").show();
							$(".showNatural").show();

							jQuery("#associatedNaturalTable").hide();
							jQuery("#naturalpersonTemplate").tmpl(data[i]).appendTo("#naturalpersonRowData");

						}



					}

					if(data[i].person_gid.person_type_gid.person_type_gid==2)
					{
						checkpersonType=true;
						jQuery('#person_subType').hide();
						jQuery('#subtypehide').hide();
						if(data[i].person_gid.active==true)
						{

							jQuery("#non_naturalpersonTemplate").tmpl(data[i]).appendTo("#non_naturalpersonRowData");

							jQuery('.hidenonTable').show();
							jQuery('#person_subType').attr('disabled', true);
							var poc_gid=data[i].person_gid.poc_gid;

							jQuery.ajax({ 
								url: "landrecords/naturalpersondata/"+poc_gid,
								async:false,							
								success: function (result) {

									associatednaturalPersonList=result;
									$(".delHide").hide();
									$('#naturalTable td:nth-child(8)').hide();
									jQuery("#naturalTable").hide();

									jQuery("#associatedNaturalTable").show();
									jQuery("#naturalpersonTemplate_2").tmpl(associatednaturalPersonList).appendTo("#associatedNaturalRowData");
								}


							});
						}
					}
				}
			}

		}
	});


	var workflowId="";
	jQuery.ajax({ 
		url: "landrecords/editattribute/"+id,
		async:false,							
		success: function (data) {	

			editList=data;

			jQuery("#projname").text(projList.name);
			jQuery("#usinStr_key").val(data[0].usinStr);
			jQuery("#household").val(data[0].househidno);
			jQuery("#landowner").val(data[0].landOwner);
			jQuery("#house_type").empty();
			jQuery("#type").empty();
			jQuery("#person_type").empty();

			jQuery("#app_nature").empty();
			jQuery("#mutation_type").empty();
			jQuery("#title_name").empty();
			jQuery("#existing_use").empty();

			if(lang=="en"){
				jQuery("#app_nature").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(noaList, function (i, noaobj) {
					jQuery("#app_nature").append(jQuery("<option></option>").attr("value", noaobj.noaId).text(noaobj.natureOfApplication)); 

				});


				jQuery("#mutation_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));

				jQuery.each(mutationTypeList, function (i, mtobj) {

					jQuery("#mutation_type").append(jQuery("<option></option>").attr("value", mtobj.mtId).text(mtobj.mutationType)); 

				});


				jQuery("#title_name").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(titleList, function (i, titleobj) {
					jQuery("#title_name").append(jQuery("<option></option>").attr("value", titleobj.id).text(titleobj.tile_name)); 
				});

				//set existing use multicheck values

				jQuery.each(landUserList, function (i, landuseobj) {
					jQuery("#existing_use").append(jQuery("<option></option>").attr("value",  landuseobj.landUseTypeId).text(landuseobj.landUseType));

				});
			}
			else if(lang=="fr"){
				jQuery("#app_nature").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(noaList, function (i, noaobj) {
					jQuery("#app_nature").append(jQuery("<option></option>").attr("value", noaobj.noaId).text(noaobj.natureOfApplicationFr)); 

				});


				jQuery("#mutation_type").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));

				jQuery.each(mutationTypeList, function (i, mtobj) {

					jQuery("#mutation_type").append(jQuery("<option></option>").attr("value", mtobj.mtId).text(mtobj.mutationTypeFr)); 

				});


				jQuery("#title_name").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(titleList, function (i, titleobj) {
					jQuery("#title_name").append(jQuery("<option></option>").attr("value", titleobj.id).text(titleobj.title_name_fr)); 
				});

				//set existing use multicheck values

				jQuery.each(landUserList, function (i, landuseobj) {
					jQuery("#existing_use").append(jQuery("<option></option>").attr("value",  landuseobj.landUseTypeId).text(landuseobj.landUseType_sw));

				});
			}

			jQuery("#person_type").val(data[0].otherUseType);
			if(data[0].existing_use!=null){
				var existing_useArray =data[0].existing_use.split(",");
				jQuery("#existing_use").val(existing_useArray);

			}
			$('select[multiple]').multiselect({
				columns: 1,
				placeholder: 'Select options'
			});

			if(data[0].otherUseType!=null){
				$('label[for="other_use"]').show();
				$('.checkOther').show();
			}
			else{
				$('label[for="other_use"]').hide();
				$('.checkOther').hide();
			}
			$("#existing_use").change(function() {
				var selected = $("#existing_use option:selected");
				selected.each(function () {
					if($(this).val()=="6")
					{
						$('label[for="other_use"]').show();
						$('.checkOther').show();
					}
					else{
						$('.checkOther').hide();
						$('label[for="other_use"]').hide();
					}
				});
			});

			jQuery("#other_useType").val(data[0].otherUseType);
			jQuery("#survey_date").val(data[0].surveyDate);

			jQuery("#area").val(((data[0].area)*area_constant).toFixed(2));

			jQuery("#imei_no").val(data[0].imeiNumber);

			jQuery("#address_1").val(data[0].address1);
			jQuery("#address_2").val(data[0].address2);
			jQuery("#usertable_id").val(data[0].user.id);
			jQuery("#project_key").val(data[0].project);


			jQuery("#neighbor_north").val(data[0].neighbor_north);
			jQuery("#neighbor_south").val(data[0].neighbor_south);
			jQuery("#neighbor_east").val(data[0].neighbor_east);
			jQuery("#neighbor_west").val(data[0].neighbor_west);

			if(data[0].apfr_no!=null){
				jQuery("#apfr_no").text(data[0].apfr_no);
			}
			else{
				jQuery("#apfr_no").text("--");
			}
			if(data[0].pv_no!=null){
				jQuery("#pv_no").text(data[0].pv_no);
			}
			else{
				jQuery("#pv_no").text("--");
			}
			if(data[0].application_no!=null){
				jQuery("#application_no").text(data[0].application_no);
			}
			else{
				jQuery("#application_no").text("--");
			}
			jQuery("#app_issueDate").val(data[0].applicationdate);
			if(data[0].noa_id!=null){
				jQuery("#app_nature").val(data[0].noa_id.noaId);
			}
			jQuery("#village_no").val(data[0].villageno);
			jQuery("#plot_area").val(((data[0].area)*area_constant).toFixed(2));

			jQuery("#registration_no").val(data[0].registrationno);
			if(data[0].mt_id!=null){
				jQuery("#mutation_type").val(data[0].mt_id.mtId);
			}
			jQuery("#issuance_date").val(data[0].issuancedate);

/*			$("#notice_startDate").live('click', function () {
				$(this).datepicker('destroy').datepicker({minDate: 0,dateFormat: 'yy-mm-dd'}).focus();
				//$(this).datepicker( {minDate: '0', dateFormat: 'yy-dd-mm' } );
			});
			jQuery("#notice_startDate").val(data[0].public_notice_startdate);
			jQuery("#notice_endDate").val(data[0].public_notice_enddate);*/


			workflowId=data[0].workflow_id.workflowId;
			if(data[1]!=null){

				jQuery("#recognition_date").val(data[1].recognition_rights_date);
				jQuery("#contradictory_date").val(data[1].contradictory_date);
				jQuery("#parcel_no").val(data[1].parcelno);

			}

			if(data[0].title_id!=null){
				jQuery("#title_name").val(data[0].title_id.id);
			}
			jQuery("#title_date").val(data[0].title_date);
			jQuery("#title_number").val(data[0].title_number);


			if(data[1]!=null){
				jQuery("#recognition_date").val(data[1].recognition_rights_date);
				jQuery("#contradictory_date").val(data[1].contradictory_date);
			}
			jQuery("#family_name").val(data[0].family_name);

		}

	});

	$('#_loader').hide();

	////
	editAttrDialog=$("#editattribute-dialog-form").dialog({
		autoOpen: false,
		height: 520,
		closed: false,
		cache: false,
		width: 953,
		resizable: false,
		modal: true,
		buttons: [{
			text: "Update Attributes",
			"id": "edit_update",
			click: function () {
				updateattributesGen();
			},

		}, {
			text: "Cancel",
			"id": "edit_cancel",
			click: function () {
				editAttrDialog.dialog( "destroy" ); 
				$('#tabs').tabs("select","#tabs-1");
			},
		}],
		close: function () {
			editAttrDialog.dialog( "destroy" ); 
			$('#tabs').tabs("select","#tabs-1");
		}
	});


	if(read){
		$('#editattribute-dialog-form').dialog('option', 'title', 'View Attribute');
		if(lang=='fr')
			$('#editattribute-dialog-form').dialog('option', 'title', 'Visualiser les attributs');
		$("#edit_update").hide();
	}
	
	$("#edit_update").html('<span class="ui-button-text">'+ Update +'</span>');
	$("#edit_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');

	editAttrDialog.dialog( "open" );	
	jQuery('.gendisable').attr('disabled', false);

	if(workflowId<=3){
		$("#notice_startDate").prop('disabled', true);
		$("#notice_endDate").prop('disabled', true);
	}

	else{
		$("#notice_startDate").prop('disabled', false);
		$("#notice_endDate").prop('disabled', false);
	}

	jQuery('.justread').attr('readonly', false);
	$(".hideNatural").hide();
	$('#naturalTable td:nth-child(9)').hide();
	/*$('#naturalTable td:nth-child(10)').hide();*/
	/*$('#naturalTable td:nth-child(11)').hide();
	$('#naturalTable td:nth-child(12)').hide();*/

	$(".hideAssociate").hide();
	$('#associatedNaturalTable td:nth-child(8)').hide();

	$(".showNatural").show();
	$('#naturalTable td:nth-child(8)').show();

	/*$('#naturalTable td:nth-child(11)').show();
	$('#naturalTable td:nth-child(12)').show();*/


	$(".hideNon").hide();
	$('#non_naturalTable td:nth-child(7)').hide();


	$(".showTenure").show();
	$('#tenureTable td:nth-child(5)').show();
	$('#tenureTable td:nth-child(6)').show();

	$(".showMul").show();
	$('#multimediaTable td:nth-child(6)').show();
	$('#multimediaTable td:nth-child(7)').show();

	$(".showkin").show();
	$('#nxtTokinTable td:nth-child(8)').show();
	$('#nxtTokinTable td:nth-child(9)').show();
	$(".hidekin").hide();
	$('#nxtTokinTable td:nth-child(10)').hide();

	$(".hidedeceased").hide();
	$('#deceasedTable td:nth-child(7)').hide();
	$(".showdeceased").show();
	$('#deceasedTable td:nth-child(5)').show();
	$('#deceasedTable td:nth-child(6)').show();

	$(".hideTenure").hide();
	$('#tenureTable td:nth-child(7)').hide();
	$(".showTenure").show();

	$(".hideMul").hide();
	$('#multimediaTable td:nth-child(9)').hide();
	$(".showMul").show();

	$(".showAddPerson").show();
	$(".showNonAddPerson").show();

	$(".hideNew").show();


	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();

		$(".hideNatural").show();
		$('#naturalTable td:nth-child(9)').show();
		/*$('#naturalTable td:nth-child(10)').show();
		 */

		$(".hideAssociate").show();
		$('#associatedNaturalTable td:nth-child(8)').show();

		$(".hideNon").show();
		$('#non_naturalTable td:nth-child(7)').show();

		$(".hideTenure").show();
		$('#tenureTable td:nth-child(7)').show();

		$(".hideMul").show();
		$('#multimediaTable td:nth-child(8)').show();
		$('#multimediaTable td:nth-child(9)').show();

		jQuery('.gendisable').attr('disabled', true);
		jQuery('.justread').attr('readonly', true);
		$(".showNatural").hide();
		$('#naturalTable td:nth-child(8)').hide();

		/*	$('#naturalTable td:nth-child(11)').hide();
		$('#naturalTable td:nth-child(12)').hide();*/

		$(".showAssociate").hide();
		$('#associatedNaturalTable td:nth-child(7)').hide();
		$('#associatedNaturalTable td:nth-child(9)').hide();
		$('#associatedNaturalTable td:nth-child(10)').hide();


		$(".showNon").hide();
		$('#non_naturalTable td:nth-child(5)').hide();
		$('#non_naturalTable td:nth-child(6)').hide();

		$(".showTenure").hide();
		$('#tenureTable td:nth-child(5)').hide();
		$('#tenureTable td:nth-child(6)').hide();

		$(".showMul").hide();
		$('#multimediaTable td:nth-child(6)').hide();
		$('#multimediaTable td:nth-child(7)').hide();

		$(".hidekin").show();
		$('#nxtTokinTable td:nth-child(10)').show();
		$(".showkin").hide();
		$('#nxtTokinTable td:nth-child(8)').hide();
		$('#nxtTokinTable td:nth-child(9)').hide();

		$(".hidedeceased").show();
		$('#deceasedTable td:nth-child(7)').show();
		$(".showdeceased").hide();
		$('#deceasedTable td:nth-child(5)').hide();
		$('#deceasedTable td:nth-child(6)').hide();


		$(".showAddPerson").hide();
		$(".showNonAddPerson").hide();
		$(".hideNew").hide();

	}

	// for vertex table
	jQuery('#vertexTableBody').empty();
	vertexTempList=[];
	for (var int = 1; int <= vertexlist.length; int++) {
		var tmp=[];
		tmp["index"]=int;
		tmp["x"]=(vertexlist[int-1].x).toFixed(2);
		tmp["y"]=(vertexlist[int-1].y).toFixed(2);
		vertexTempList.push(tmp);
	}

	jQuery("#vertexTable").tmpl(vertexTempList).appendTo("#vertexTableBody");

}


function updateattributesGen()
{	

	$("#editAttributeformID").validate({

		rules: {

			household: "required",
			neighbor_north:"required",
			neighbor_south:"required",
			neighbor_east:"required",
			neighbor_west:"required",
			//app_issueDate:"required",
			//village_no:"required",
			plot_area:"required",
			//parcel_no:"required",
			//issuance_date:"required",
			//notice_startDate:"required",
			//notice_endDate:"required"

		},
		messages: {
			household: FIELD_REQ,
			neighbor_north:FIELD_REQ,
			neighbor_south:FIELD_REQ,
			neighbor_east:FIELD_REQ,
			neighbor_west:FIELD_REQ,
			//app_issueDate:"Please enter application issue date",
			//village_no:"Please enter village number",
			plot_area:FIELD_REQ
			//parcel_no:"Please enter parcel number",
			//issuance_date:"Please enter issuance date",
			//notice_startDate:"Please enter notice start date",
			//notice_endDate:"Please enter notice end date"
		}

	});

	if ($("#editAttributeformID").valid())
	{

		updateattributes();
//		if($('#existing_use').val()!=0){

//		if($('#mutation_type').val()!=0){

//		if($('#app_nature').val()!=0){


//		updateattributes();

//		}
//		else{
//		alert("Please select nature of application");
//		}


//		}
//		else{
//		alert("Please select mutation type");
//		}

//		}
//		else{
//		alert("Please select current use");
//		}

	}

	else{

		jAlert(Fill_Mandatory,Alert);
	}

} 



function updateattributes()
{
	var selected = $("#existing_use option:selected");
	var selected_use = "";
	selected.each(function () {
		selected_use += $(this).val() + ",";
	});

	$("#existing_hidden").val(selected_use.substring(0,selected_use.length - 1));
	var id=editList[0].usin;
	var length_gen=0;
	if(attributeList!=null)
	length_gen=attributeList.length;
	jQuery("#general_length").val(0);
	if(length_gen!=0 && length_gen!=undefined)
		jQuery("#general_length").val(length_gen);
	jQuery("#projectname_key1").val(project);
	jQuery('#primary').val(id);


	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updateattributes" ,
		data: jQuery("#editAttributeformID").serialize(),
		success: function (result) 
		{  
			if(result!=0)
			{
				workflowTmp=result;
				jAlert(DATA_SAVED,Alert);
				$('body').find("#editattribute-dialog-form").remove();
				editAttrDialog.dialog( "destroy" );
				displayRefreshedLandRecords("LandRecords");
				//finalValidation();

			}
			else
			{
				jAlert(FAILED);
			}


		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(FAILED);
		}
	});

}



function editNaturalData(id)

{

	jQuery(".hidden_alias").hide();
	jQuery(".mandatechk").hide();
	jQuery('label[for="mandateLabel"]').hide();

	displayAttributeCategory(2,id);


	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	

			genderList=data;
		}


	});

	/*	jQuery.ajax({ 
		url: "landrecords/personsubtype/",
		async:false,							
		success: function (data) {	

			person_subtype=data;
		}


	});*/

	jQuery.ajax({ 
		url: "landrecords/maritalstatus/",
		async:false,							
		success: function (data) {	

			maritalList=data;
		}


	});
	jQuery.ajax({ 
		url: "landrecords/nop/",
		async:false,							
		success: function (data) {	

			natureOfPowerList=data;
		}


	});



	jQuery.ajax({ 
		url: "landrecords/naturalperson/"+id,
		async:false,							
		success: function (data) {
			jQuery("#gender").empty();
			jQuery("#marital_status").empty();
			jQuery("#person_nop").empty();

			if(lang=="en"){
				jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(genderList, function (i, genderobj) {
					jQuery("#gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender)); 

				});

				jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(maritalList, function (i, maritalobj) {
					jQuery("#marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus)); 

				});


				jQuery("#person_nop").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(natureOfPowerList, function (i, nopobj) {
					jQuery("#person_nop").append(jQuery("<option></option>").attr("value",nopobj.nopId).text(nopobj.natureOfPower)); 

				});

			}
			else if(lang=="fr"){
				jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(genderList, function (i, genderobj) {
					jQuery("#gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender_sw)); 

				});

				jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(maritalList, function (i, maritalobj) {
					jQuery("#marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus_sw)); 

				});


				jQuery("#person_nop").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(natureOfPowerList, function (i, nopobj) {
					jQuery("#person_nop").append(jQuery("<option></option>").attr("value",nopobj.nopId).text(nopobj.natureOfPowerFr)); 

				});

			}


			if(data!=null && data!="" && data!=undefined )
			{


				if(data[0].marital_status!=null)
				{
					jQuery("#marital_status").val(data[0].marital_status.maritalStatusId);
				}
				if(data[0].gender!=null){
					jQuery("#gender").val(data[0].gender.genderId);
				}
				if(data[0].personSubType!=null){
					jQuery("#person_subType").val(data[0].personSubType.person_type_gid);
				}
				if(data[0].citizenship_id!=null)
				{
					jQuery("#citizenship").val(data[0].citizenship_id.id);
				}

				jQuery("#natural_key").val(id);	
				jQuery("#name").val(data[0].alias);
				//jQuery("#tenure").val(data[0].tenure_Relation);
				jQuery("#fname").val(data[0].firstName);
				jQuery("#mname").val(data[0].middleName);
				jQuery("#lname").val(data[0].lastName);
				jQuery("#mobile_natural").val(data[0].mobile);
				if(data[0].age!=0){
					jQuery("#age").val(data[0].age);
				}
				else{
					jQuery("#age").val( );
				}
				jQuery("#occ_age").val(data[0].occAgeBelow);

				//hide show according to nature of power


				if(data[0].nop_id!=null)
				{
					if(data[0].nop_id.nopId!=1){
						jQuery(".mandatechk").show();
						jQuery('label[for="mandateLabel"]').show();
					}
					jQuery("#person_nop").val(data[0].nop_id.nopId);
				}
				jQuery("#dob").val(data[0].dob);


				jQuery("#birth_place").val(data[0].birthplace);
				jQuery("#person_address").val(data[0].address);
				jQuery("#profession").val(data[0].occupation);
				jQuery("#refrence_IdCard").val(data[0].idcard);
				jQuery("#id_establishment_date").val(data[0].idcard_establishment_date);
				jQuery("#father_name").val(data[0].fathername);
				jQuery("#mother_name").val(data[0].mothername);
				jQuery("#mandate_issuance_date").val(data[0].mandate_issuanceDate);
				jQuery("#mandate_location").val(data[0].mandate_location);
			}
		}




	});


	naturalPersonDialog = $( "#naturalperson-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: [{

			text: "Update",
			"id": "natural_update",
			click: function () {

				updateAttributeNaturalPerson(false);

			},
		},
		{
			text: "Cancel",
			"id": "natural_cancel",
			click: function () {

				naturalPersonDialog.dialog( "destroy" );
				naturalPersonDialog.dialog( "close" );
				$('#tab-natural').tabs("select","#tabs-1");

			}
		}],
		close: function() {
			naturalPersonDialog.dialog( "destroy" );
			$('#tab-natural').tabs("select","#tabs-1");

		}
	});
	$("#natural_update").html('<span class="ui-button-text">'+ Update +'</span>');
	$("#natural_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	naturalPersonDialog.dialog( "open" );	
	jQuery('.justread').attr('readonly', false);
	jQuery('.justdisable').attr('disabled', false);
	if(checkpersonType==true){
		jQuery('#person_subType').hide();
		jQuery('#subtypehide').hide();


	}
	else{
		jQuery('#person_subType').show();
		jQuery('#subtypehide').show();
	}
	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();
		$('.ui-dialog-buttonpane button:contains("Mise")').button().hide();
		jQuery('.justread').attr('readonly', true);
		jQuery('.justdisable').attr('disabled', true);
	}

}

function updateAttributeNaturalPerson(newPerson)

{
	$("#editNaturalPersonformID").validate({

		rules: {
			//name: "required",
			fname: "required",
			lname: "required",
			dob : "required",
			birth_place : "required",
			person_address: "required",
			profession: "required",
			refrence_IdCard: "required",
			id_establishment_date: "required",
			father_name: "required",
			mother_name: "required"



		},
		messages: {
			//name: "Please enter Alias Name",
			fname: FIELD_REQ,
			lname: FIELD_REQ,
			dob: FIELD_REQ,
			birth_place: FIELD_REQ,
			person_address: FIELD_REQ,
			profession: FIELD_REQ,
			refrence_IdCard: FIELD_REQ,
			id_establishment_date: FIELD_REQ,
			father_name: FIELD_REQ,
			mother_name: FIELD_REQ


		},


		ignore:[]
	});

	if ($("#editNaturalPersonformID").valid())
	{

		if($('#gender').val()!=0){

			if($('#marital_status').val()!=0)
			{

				if($('#person_nop').val()!=0)
				{

					if(newPerson){
						updateNewNaturalPerson();
						naturalPersonDialog.dialog( "destroy" );
						naturalPersonDialog.dialog( "close" );
					}
					else{
						updateNaturalPerson();

					}

				}
				else{
					jAlert(Fill_Mandatory);
				}
			}
			else{
				jAlert(Fill_Mandatory);
			}

		}
		else{
			jAlert(Fill_Mandatory);
		}
	}

	else {

		jAlert(Fill_Mandatory,Alert);
	}

}

function updateNaturalPerson()
{	

	var id=editList[0].usin;
	project=editList[0].project;
	var length_natural=attributeList.length;
	jQuery("#natual_length").val(0);
	if(length_natural!=0 && length_natural!=undefined)
		jQuery("#natual_length").val(length_natural);
	jQuery("#natural_usin").val(id);

	jQuery("#projectname_key").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatenatural" ,
		data: jQuery("#editNaturalPersonformID").serialize(),
		success: function (data) 
		{
			if(data)
			{
				naturalPersonDialog.dialog( "destroy" );
				jAlert(DATA_SAVED,Alert);
				editAttribute(id);
			}

			else
			{

				jAlert(FAILED);

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(FAILED);
		}
	});

}
function editMultimedia(id)
{
	displayAttributeCategory(3,id);
	jQuery.ajax({ 
		url: "landrecords/multimedia/"+id,
		async:false,							
		success: function (data) {	

			jQuery("#entity_name").val(data[0].entity_name);
			jQuery("#scanned_srs").val(data[0].scanedSourceDoc);
			jQuery("#quality_type").val(data[0].qualityType);
			jQuery("#recordation").val(data[0].recordation);
			jQuery("#inventory_type").val(data[0].socialTenureInvantoryType);
			jQuery("#comments_multimedia").val(data[0].comments);
			jQuery("#primary_key").val(data[0].gid);
			jQuery("#multimedia_id").val(data[0].id);
			jQuery("#source_path").val(data[0].locScannedSourceDoc);
			jQuery("#usink").val(data[0].usin);
			jQuery("#person_gidk").val(data[0].person_gid);
			jQuery("#social_gid").val(data[0].social_tenure_gid);

		}

	});

	multimediaDialog = $( "#multimedia-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons:[{

			text: "Update",
			"id": "multi_update",
			click: function () {

				updateAttributeMultimedia();

			},
		},{
			text: "Cancel",
			"id": "multi_cancel",
			click: function () {

				multimediaDialog.dialog( "destroy" );
				multimediaDialog.dialog( "close" );
				$('#tab-multimedia').tabs("select","#tabs-7");
			}
		}],
		close: function() {
			multimediaDialog.dialog( "destroy" );
			$('#tab-multimedia').tabs("select","#tabs-7");

		}
	});
	$("#multi_update").html('<span class="ui-button-text">'+ Update +'</span>');
	$("#multi_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	multimediaDialog.dialog( "open" );	
	jQuery('.read-mul').attr('readonly', false);
	jQuery('.disablemul').attr('disabled', false);

	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();
		$('.ui-dialog-buttonpane button:contains("Mise")').button().hide();
		jQuery('.read-mul').attr('readonly', true);
		jQuery('.disablemul').attr('disabled', true);

	}

}

function updateAttributeMultimedia()

{
	$("#editmultimediaformID").validate({

		rules: {
			scanned_srs: "required",
			//inventory_type: "required"

		},
		messages: {
			scanned_srs: FIELD_REQ,
			//inventory_type: "Please enter  Inventory Type"
		},


		ignore:[]

	});

	if ($("#editmultimediaformID").valid())
	{
		updateMultimedia();
	}
	else
	{

		jAlert(Fill_Mandatory,Alert);
	}

}
function updateMultimedia()

{
	var id=editList[0].usin;

	var length_multimedia=attributeList.length;
	jQuery("#multimedia_length").val(0);
	if(length_multimedia!=0 && length_multimedia!=undefined)
		jQuery("#multimedia_length").val(length_multimedia);
	jQuery("#projectname_multimedia_key").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatemultimedia" ,
		data: jQuery("#editmultimediaformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				multimediaDialog.dialog( "destroy" );
				editAttribute(id);


				jAlert(DATA_SAVED,Alert);
			}

			else
			{

				jAlert(FAILED);

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(FAILED);
		}
	});

}


var deleteMultimedia= function (id,name) 
{	

	var usinid=editList[0].usin;

	jConfirm(DELETE_MSG+' : <strong>' + name +  '</strong>',Delete_Confirmation , function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "landrecords/delete/"+id,
				success: function (result) 
				{ 
					if(result==true)
					{
						jAlert('Data Successfully Deleted', 'Multimedia');	                  
						editAttribute(usinid);
					}

					if(result==false)

					{
						jAlert(Delete_confirm, Alert);	                  

					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert(FAILED);
				}
			});
		}

	});

}

var deleteNatural= function (id,name) 
{	
	var usinid=editList[0].usin;

	if(socialtenureList.length==1)
	{

		jAlert('Person can not be deleted..single person exists', 'Person');	

	}

	else {




		jConfirm(DELETE_MSG +' : <strong>' + name + '</strong>', Delete_Confirmation, function (response) {

			if (response) {
				jQuery.ajax({          
					type: 'GET',
					url: "landrecords/deleteNatural/"+id,
					success: function (result) 
					{ 
						resultDeleteNatural=result;
						if(resultDeleteNatural==true)
						{
							jAlert('Data Successfully Deleted', 'Person');	                  
							editAttribute(usinid);
						}

						if(resultDeleteNatural==false)

						{
							jAlert(Delete_confirm, Alert);	                  

						}
					},

					error: function (XMLHttpRequest, textStatus, errorThrown) 
					{	                    
						jAlert(FAILED);
					}
				});
			}

		});
	}
}


function displayAttributeCategory(id,gid)
{


	jQuery.ajax({ 
		url: "landrecords/attributedata/"+id+"/"+gid+"/",
		async:false,							
		success: function (data) {	

			attributeList=data;

		}


	});
	jQuery("#customnewnatural-div").empty();

	jQuery("#custom-div").empty();
	jQuery("#customtenure-div").empty();
	jQuery("#customnatural-div").empty();
	jQuery("#customnonnatural-div").empty();
	jQuery("#customgeneral-div").empty();
	jQuery("#custommultimedia-div").empty();


	if(attributeList.length>0)
	{	
		//jQuery('.datepicker').attr('readonly', true);
		$(".datepicker").datepicker();
		$(".datepicker").live('click', function() {
			$(this).datepicker({
				/*dateFormat : 'MM/dd/yyyy'*/
			}).focus();
		});


		for (var i = 0; i < attributeList.length; i++) {
			selectedUnitText=attributeList[i].alias;

			selectedUnitValue=attributeList[i].value;
			var datatype=attributeList[i].datatypeid;
			selectedUnitValues=attributeList[i].attributevalueid;

			if(id==1)
			{
				if(datatype==2){
					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_general'+""+i+""+'" class="input-medium justread datepicker"  name="alias_general'+""+i+""+'" type="text" value=""/><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_general'+""+i+""+'').val(selectedUnitValue);

				}
				else if(datatype==3){

					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_general'+""+i+""+'" class="input-medium gendisable" name="alias_general'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_general'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_general'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium justread" name="alias_general'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else {

					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_general'+""+i+""+'" type="text" class="input-medium justread" name="alias_general'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}

			else if(id==4)
			{
				if(datatype==2)
				{
					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_tenure'+""+i+""+'" class="input-medium tenure-read" name="alias_tenure'+""+i+""+'" type="Date" value=""/><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_tenure'+""+i+""+'').val(selectedUnitValue);

				}

				else if(datatype==3)

				{

					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_tenure'+""+i+""+'" class="input-medium read-tenure" name="alias_tenure'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_tenure'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{
					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_tenure'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium tenure-read" name="alias_tenure'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');

				}
				else{
					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_tenure'+""+i+""+'" class="inputField01 splitpropclass tenure-read" name="alias_tenure'+""+i+""+'" type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}
			else if(id==2)
			{
				if(datatype==2)
				{

					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_natural'+""+i+""+'" class="input-medium justread" name="alias_natural'+""+i+""+'" type="Date" value=""/><input  id="alias_natural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_natural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==3)
				{

					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_natural'+""+i+""+'" class="input-medium justdisable" name="alias_natural'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_natural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_natural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_natural'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium justread" name="alias_natural'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_natural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_natural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else
				{
					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_natural'+""+i+""+'" class="inputField01 splitpropclass justread" name="alias_natural'+""+i+""+'"  type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_natural_key'+""+i+""+'"  name="alias_natural_key'+""+i+""+'"  type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}
			else if(id==5)
			{
				if(datatype==2)
				{

					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_nonnatural'+""+i+""+'" class="input-medium read-non" name="alias_nonnatural'+""+i+""+'" type="Date" value=""/><input  id="alias_nonnatural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_nonnatural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_nonnatural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==3)
				{

					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_nonnatural'+""+i+""+'" class="input-medium disablenon" name="alias_nonnatural'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_nonnatural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_nonnatural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_nonnatural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_nonnatural'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium read-non" name="alias_nonnatural'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_nonnatural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_nonnatural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else{
					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_nonnatural'+""+i+""+'" class="inputField01 splitpropclass read-non" name="alias_nonnatural'+""+i+""+'"  type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_nonnatural_key'+""+i+""+'"  name="alias_nonnatural_key'+""+i+""+'"  type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}
			else if(id==3)
			{

				if(datatype==2)
				{

					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_multimedia'+""+i+""+'" class="input-medium read-mul" name="alias_multimedia'+""+i+""+'" type="Date" value=""/><input  id="alias_multimedia_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_multimedia_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_multimedia'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==3)
				{

					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_multimedia'+""+i+""+'" class="input-medium disablemul" name="alias_multimedia'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_multimedia_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_multimedia_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_multimedia'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_multimedia'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium read-mul" name="alias_multimedia'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_multimedia_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_multimedia_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else{
					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_multimedia'+""+i+""+'" class="inputField01 splitpropclass read-mul" name="alias_multimedia'+""+i+""+'" type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_multimedia_key'+""+i+""+'"  name="alias_multimedia_key'+""+i+""+'"  type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}

		}	

	}

	else{

		$("#customtenure-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#customnatural-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#customnonnatural-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#custommultimedia-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#customgeneral-div").append("<strong style='color:#1366c5'>No Attributes</strong>");

	}

}


function viewMultimedia(id)
{
	window.open("landrecords/download/"+id,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');		

}


function showOnMap(usin)

{

	$.ajaxSetup ({cache: false});

	var relLayerName="spatial_unit";
	var fieldName="usin";
	var fieldVal=usin;

	zoomToLayerFeature(relLayerName,fieldName,fieldVal);

}

function zoomToLayerFeature(relLayerName,fieldName,fieldVal){// relLayerName=’spatial_unit’, fieldname=”usin”, fieldval= usin value

	if(map.getLayersByName("vector")[0]!=undefined)
		map.removeLayer(vectors);
	OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';
	vectors = new OpenLayers.Layer.Vector("vector", {isBaseLayer: true});
	map.addLayers([vectors]);
	var layer = map.getLayersByName(relLayerName)[0];

	if(layer==null)
		return;

	var url=map.getLayersByName(relLayerName)[0].url;
	var layerName='spatial_unit';
	var type;   
	var featureNS=getFeatureNS(layerName,url);
	var prefix; 
	var geomFieldName='the_geom';
	var featuresGeom=null;

	var pos = layerName.indexOf(":");
	prefix = layerName.substring(0, pos);
	type = layerName.substring(++pos);

	var filters=getFilter(fieldName,fieldVal);

	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
	});

	createXML();
	var xml = new OpenLayers.Format.XML();
	var xmlFilter = xml.write(filter_1_0.write(filters));

	dataPost = dataPost.replace("${layer}", "\"" + "spatial_unit" + "\"");
	dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
	dataPost = dataPost.replace("${filter}", xmlFilter);
	dataPost = dataPost.replace("${uniqueFld}", fieldName);
	valAsc = "ASC";
	dataPost = dataPost.replace("${SortOrder}", valAsc);
	var mapProj = map.projection;
	var reverseCoords = false;
	if (mapProj == "EPSG:4326") {
		reverseCoords = false;
	}

	$.ajax({
		url : url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : dataPost,
		success : function(data) {
			var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : 'spatial_unit',
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, 
				featurePrefix : 'spatial_unit',
				extractAttributes : true
			}).read(data);

			if(gmlFeatures.length>0){
				featuresGeom=gmlFeatures[0].geometry;
				zoomToAnyFeature(featuresGeom);
			}else{
				alert("Site not found on Map");
			}
		}
	});

}


function zoomSiteOnMap(usin){
	zoomToLayerFeature('World Heritage Sites','Polygon','wdpa_id',wdpaid);
}


function getFeatureNS(layerName,url){
	if(url==null)
		return url;
	var _wfsurl = url.replace(new RegExp( "wms", "i" ), "wfs");
	var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + layerName;        

	var featureNS='';
	$.ajax({
		url: _wfsSchema,
		dataType: "xml",
		async:false,
		success: function (data) {
			var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
			var responseText = featureTypesParser.read(data);         
			featureNS = responseText.targetNamespace;
		}
	});
	return featureNS;
}

function zoomToAnyFeature(geom) {

	var biggerArea=0.0;
	var biggerPart=0;
	if(geom.components!=undefined && geom.components!=null)
	{
		$('#tab').tabs("select","#map-tab");
		$('#sidebar').show();
		$('#collapse').show();

		for (intpartCnt = 0; intpartCnt<geom.components.length;intpartCnt++){
			if (biggerArea < geom.components[intpartCnt].getArea()){
				biggerArea = geom.components[intpartCnt].getArea();
				biggerPart = intpartCnt;
			}
		}

		var bounds = null;
		//bounds = geom.getBounds();
		bounds = geom.components[biggerPart].getBounds();

		var feature = new OpenLayers.Feature.Vector(
				OpenLayers.Geometry.fromWKT(
						geom.toString()
				));
		vectors.addFeatures([feature]);
		/*var newBounds = bounds.transform(new OpenLayers.Projection("EPSG:4326"),
		map.getProjectionObject());
		map.setCenter(new OpenLayers.LonLat(bounds.bottom,bounds.left),12);
		map.adjustZoom(6);*/
		map.zoomToExtent(bounds,true);


	}
	else{
		$('#tab').tabs("select","#landrecords-div");
		$('#sidebar').hide();
		$('#collapse').hide();
		jAlert(Site_not_found,Alert);
	}
}

function getFilter(fieldName,fieldVal) {
	var filter;

	filter = new OpenLayers.Filter.Comparison({
		type: OpenLayers.Filter.Comparison.EQUAL_TO,
		matchCase: false,
		property: fieldName,
		value: fieldVal
	});

	return filter;
}

function adjudicationFormDialog(usinID,statusId)
{
	if(statusId==5 || statusId==3)
	{

		jAlert('Adjudication form can be generated for New Status','Alert');

	}
	else{
		adjudicationDialog = $( "#adjudication-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 232,
			resizable: false,
			modal: true,

			buttons: [{
				text: "Ok",
				"id": "adjudication_ok",
				click: function () {
					var lang = "";
					var selected = $("#radioLang input[type='radio']:checked");
					if (selected.length > 0) {
						lang = selected.val();
					}

					if(lang!='0')
					{

						get_Adjuticator_detail(usinID,lang);

						adjudicationDialog.dialog( "destroy" );
					}else
					{
						jAlert("Please select language","Alert");
					}


				},
			},
			{
				text: "Cancel",
				"id": "adjudication_cancel",
				click: function () {

					adjudicationDialog.dialog( "destroy" );

				}
			}],
			close: function() {

				adjudicationDialog.dialog( "destroy" );

			}
		});

		$('input[type=radio][name=lang]').change(function() {
			if (this.value == 'Sw') {
				$('input:radio[name="lang"][value="Sw"]').prop('checked', true);
			}
			else if (this.value == 'En') {
				$('input:radio[name="lang"][value="En"]').prop('checked', true);
			}
		});


		$('input:radio[name="lang"][value="Sw"]').prop('checked', true);
		$("#adjudication_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
		$("#adjudication_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
		adjudicationDialog.dialog( "open" );	
	}


}

function viewProjectName(project){

	activeProject=project;

}

function defaultProject()

{
	document.location.href ="http://"+location.host+"/mast/viewer/";

}
/* To add a natural person if deleted from Edit Attribute Natural Person Tab */

function addNaturalPerson(empty)
{

	var idUsin=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/shownatural/"+idUsin,
		async:false,							
		success: function (shownaturaldata) {	



			DeletedNaturalList=shownaturaldata;

		}


	});
	jQuery("#deletedNaturalpersonRowData").empty();
	if(DeletedNaturalList.toString()==""){
		if(empty!="empty")
			jAlert(No_record,Alert);
	}
	else{

		jQuery("#naturalpersonTemplate_add").tmpl(DeletedNaturalList).appendTo("#deletedNaturalpersonRowData");
		addDeletedNaturalDialog = $( "#deletednat-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 500,
			resizable: false,
			modal: true,
			buttons: {
				"Close": function() 
				{
					addDeletedNaturalDialog.dialog( "close" );
					addDeletedNaturalDialog.dialog( "destroy" );
				}
			},

		});
		addDeletedNaturalDialog.dialog( "open" );	

	}
}

function addDeletedNatural(personGid)
{
	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/addnatural/"+personGid,
		async:false,							
		success: function (result) {	

			if(result)
			{
				jAlert(DATA_SAVED,Alert);
				if(DeletedNaturalList.length==1)
				{

					addDeletedNaturalDialog.dialog( "close" );
					addDeletedNaturalDialog.dialog( "destroy" );
				}
				addNaturalPerson("empty");
				editAttribute(id);
			}
			else{
				jAlert('Error in Adding Data', Alert);
			}

		}


	});
}



function previousRecords()

{
	//$("#landRecordsTable").tablesorter( {widgets: ['zebra']} ); 
	if(records_from>1)
	{


		if(searchRecords!=null)
		{
			records_from= $('#records_from').val();
			records_from=parseInt(records_from);
			records_from=records_from-6;
			spatialSearch(records_from);
		}
		else{

			records_from= $('#records_from').val();
			records_from=parseInt(records_from);
			records_from=records_from-6;
			spatialRecords(records_from);	
		}

	}

	else{

		alert(FAILED);

	}

}

function nextRecords()

{
	//$("#landRecordsTable").tablesorter( {widgets: ['zebra']} ); 
	records_to= $('#records_to').val();

	if(records_to<=totalRecords-1)
	{

		if(searchRecords!=null)
		{
			if(records_to<=searchRecords-1)
			{
				records_from= $('#records_from').val();
				records_from=parseInt(records_from);
				records_from=records_from+4;
				spatialSearch(records_from);
			}

			else
				alert(FAILED);

		}
		else{
			records_from= $('#records_from').val();
			records_from=parseInt(records_from);
			records_from=records_from+4;
			spatialRecords(records_from);	
		}

	}

	else
	{
		alert(FAILED);
	}

}
/*function lastRecords()

{
	//$("#landRecordsTable").tablesorter( {widgets: ['zebra']} ); 
	records_to= totalRecords-1;

	if(records_to<=totalRecords-1)
	{

		if(searchRecords!=null)
		{
			if(records_to<=searchRecords-1)
			{
				records_from=totalRecords-5 ;
				records_from=parseInt(records_from);
				//records_from=records_from+4;
				spatialSearch(records_from);
			}

			else
				alert(FAILED);

		}
		else{
			records_from= totalRecords-5;
			records_from=parseInt(records_from);
			//records_from=records_from+4;
			spatialRecords(records_from);	
		}

	}

	else
	{
		alert(FAILED);
	}

}*/


function spatialRecords(records_from)
{

	searchRecords=null;
	records_from=records_from;
	var workflow=new workflowStatus();
	dataList=workflow.spatialbyWorkFlow(records_from);

	if(dataList!="" && dataList!=null)
	{
		$('#landRecordsRowData').empty();
		jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
		$("#landRecordsTable").trigger("update");
		$('#records_from').val(records_from+1);
		$('#records_to').val(totalRecords);
		if(records_from+5<=totalRecords)
			$('#records_to').val(records_from+5);
		$('#records_all').val(totalRecords);
	}
	else{
		jAlert(No_record,Alert);
	}



}




function spatialSearch(records_from)

{
	records_from=records_from;


	var actionLand=new actionLandRecords();
	dataList=actionLand.search(records_from);
	searchRecords=actionLand.searchCount();



	if(dataList!="" && dataList!=null)
	{
		$('#landRecordsRowData').empty();
		jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
		$("#landRecordsTable").trigger("update");
		$('#records_from').val(records_from+1);
		$('#records_to').val(searchRecords);
		if(records_from+5<=searchRecords)
			$('#records_to').val(records_from+5);
		$('#records_all').val(searchRecords);
	}
	else{
		jAlert(No_record,Alert);
	}



}

function addPerson()
{

	addPersonDialog = $( "#add_person-dialog-form" ).dialog({
		autoOpen: false,
		height: 138,
		width: 255,
		resizable: false,
		modal: true,
		buttons: {
			"Close": function() 
			{
				addPersonDialog.dialog( "close" );
				addPersonDialog.dialog( "destroy" );
			}
		},

	});
	addPersonDialog.dialog( "open" );	

}
/*
function addNewNaturalPerson()
{

	naturalAdditonalAttributes();
	jQuery(".hidden_alias").show();


	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	

			jQuery("#gender").empty();
			if(lang=="en"){
				jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(data, function (i, genderobj) {
					jQuery("#gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender)); 

				});
			}
			else if(lang=="fr"){
				jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(data, function (i, genderobj) {
					jQuery("#gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender_sw)); 

				});
			}

		}

	});

	jQuery.ajax({ 
		url: "landrecords/maritalstatus/",
		async:false,							
		success: function (data) {	
			jQuery("#marital_status").empty();
			if(lang=="en"){
				jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(data, function (i, maritalobj) {
					jQuery("#marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus)); 
				});
			}
			else if(lang=="fr"){
				jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Select"));
				jQuery.each(data, function (i, maritalobj) {
					jQuery("#marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus)); 
				});
			}
		}
	});

		jQuery.ajax({ 
		url: "landrecords/citizenship/",
		async:false,							
		success: function (data) {	
			jQuery("#citizenship").empty();
			jQuery("#citizenship").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(data, function (i, citizenobj) {
				if(citizenobj.id!=0)
					jQuery("#citizenship").append(jQuery("<option></option>").attr("value",citizenobj.id ).text(citizenobj.citizenname)); 

			});

		}

	});


		jQuery.ajax({ 
		url: "landrecords/personsubtype/",
		async:false,							
		success: function (person_subtype) {	

			jQuery("#person_subType").empty();
			jQuery("#person_subType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(person_subtype, function (i, personSubtypeobj) {
				if(personSubtypeobj.person_type_gid!=1 && personSubtypeobj.person_type_gid!=2)
					jQuery("#person_subType").append(jQuery("<option></option>").attr("value",personSubtypeobj.person_type_gid).text(personSubtypeobj.personType)); 

			});

		}


	});

	jQuery.ajax({ 
		url: "landrecords/nop/",
		async:false,							
		success: function (data) {	

			jQuery("#person_nop").empty();
			if(lang=="en"){
				jQuery("#person_nop").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
				jQuery.each(data, function (i, nopobj) {
					jQuery("#person_nop").append(jQuery("<option></option>").attr("value",nopobj.nopId).text(nopobj.natureOfPower)); 
				});
			}
			else if(lang=="fr"){

				jQuery("#person_nop").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
				jQuery.each(data, function (i, nopobj) {
					jQuery("#person_nop").append(jQuery("<option></option>").attr("value",nopobj.nopId).text(nopobj.natureOfPowerFr)); 
				});

			}
		}


	});
	document.getElementById("editNaturalPersonformID").reset();
	naturalPersonDialog = $( "#naturalperson-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Save": function() 
			{

				updateAttributeNaturalPerson(true);
				//updateNewNaturalPerson();



			},
			"Cancel": function() 
			{
				naturalPersonDialog.dialog( "destroy" );
				naturalPersonDialog.dialog( "close" );
				$('#tab-natural').tabs("select","#tabs-1");

			}
		},
		close: function() {
			naturalPersonDialog.dialog( "destroy" );
			$('#tab-natural').tabs("select","#tabs-1"); 

		}
	});

	naturalPersonDialog.dialog( "open" );	
}

function updateNewNaturalPerson()
{

	jQuery("#natural_key").val(0);	
	var id=editList[0].usin;
	project=editList[0].project;
	var length_natural=attributeList.length;
	jQuery("#natural_usin").val(id);
	jQuery("#natual_length").val(0);
	if(length_natural!=0 && length_natural!=undefined)
		jQuery("#natual_length").val(length_natural);
	jQuery("#projectname_key").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatenatural" ,
		data: jQuery("#editNaturalPersonformID").serialize(),
		success: function (data) 
		{
			if(data.toString()!="")
			{
				naturalPerson_gid=data.person_gid;
				//updateNewTenure();

				editAttribute(id);
				//addPersonDialog.dialog("destroy");
				jAlert('Data Sucessfully Saved','Tenure Info');
			}
			else{

				jAlert("Request Not Completed","Natural Person");
			}

		}

	});	


}

 */
function naturalAdditonalAttributes()
{
	jQuery.ajax({ 
		url: "landrecords/naturalcustom/"+activeProject,
		async:false,							
		success: function (data) {	

			customList=data;

			$(".datepicker").datepicker();
			$(".datepicker").live('click', function() {
				$(this).datepicker('destroy').datepicker({
					dateFormat : 'yy-mm-dd'
				}).focus();
			});
		}


	});
	var length_new=(customList.length)/3;

	var j=0;
	jQuery("#customnatural-div").empty();
	jQuery("#customnewnatural-div").empty();
	jQuery("#customnewnatural-div").append('<div><input type="hidden" name="newnatural_length" value='+length_new+'></div>');
	for (var i = 0; i < customList.length; i++) {
		j=j+1;
		var selectedcustomText=customList[i];
		var datatype=customList[i+2];
		var custom_uid=customList[i+1];

		if(datatype=='2')
		{

			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'" >'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><input  id="alias_nat_custom'+""+j+""+'" class="input-medium justread datepicker" readonly name="alias_nat_custom'+""+j+""+'" type="Date" value=""/><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+""+custom_uid+""+'"/></div></div>');
			//jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
		}
		else if(datatype=='3')
		{

			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'" >'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><select  id="alias_nat_custom'+""+j+""+'" class="input-medium justdisable" name="alias_nat_custom'+""+j+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+""+custom_uid+""+'"/></div></div>');
			//jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
		}
		else if(datatype=='4')
		{

			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'" >'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><input  id="alias_nat_custom'+""+j+""+'" type="number" pattern="[0-9]" class="input-medium justread" name="alias_nat_custom'+""+j+""+'" value=""/><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+""+custom_uid+""+'"/></div></div>');
		}
		else
		{
			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'">'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><input  id="alias_nat_custom'+""+j+""+'" class="inputField01 splitpropclass" name="alias_nat_custom'+""+j+""+'" type="text" value=""/><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+custom_uid+'"/></div></div>');
		}

		i=i+2;	
	}


}


function editnxtTokin(id)

{
	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	

			genderList=data;
		}


	});
	jQuery("#gender_kin").empty();
	if(lang=="en"){
		jQuery("#gender_kin").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
		jQuery.each(genderList, function (i, genderobj) {

			jQuery("#gender_kin").append(jQuery("<option></option>").attr("value",genderobj.genderId).text(genderobj.gender)); 

		});
	}
	else if(lang=="fr"){
		jQuery("#gender_kin").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
		jQuery.each(genderList, function (i, genderobj) {

			jQuery("#gender_kin").append(jQuery("<option></option>").attr("value",genderobj.genderId).text(genderobj.gender_sw)); 

		});	
	}


	jQuery.ajax({ 
		url: "landrecords/poi/"+id,
		async:false,							
		success: function (data) {	

			$('#fname_kin').val(data.personName);
			$('#mname_kin').val(data.middle_name);
			$('#lname_kin').val(data.last_name);
			$('#ref_id_kin').val(data.idcard_refrence);
			$('#address_kin').val(data.address);
			$('#gender_kin').val(data.gender.genderId);
			$('#usin_kin').val(data.usin);
			$('#id_kin').val(data.id);

		}
	});

	nxtTokinDialog = $( "#nxtTokin-dialog-form" ).dialog({
		autoOpen: false,
		height: 414,
		width: 304,
		resizable: false,
		modal: true,

		buttons: [{
			text: "Update",
			"id": "poi_update",
			click: function () {
				validateUpdatePWI(id);



			},
		},
		{
			text: "Cancel",
			"id": "poi_cancel",
			click: function () {

				nxtTokinDialog.dialog( "destroy" );
				nxtTokinDialog.dialog( "close" );
				$('#editnxtTokinformID')[0].reset();


			}
		}],
		close: function() {

			nxtTokinDialog.dialog( "destroy" );
			nxtTokinDialog.dialog( "close" );
			$('#editnxtTokinformID')[0].reset();

		}
	});
	$("#poi_update").html('<span class="ui-button-text">'+ Update +'</span>');
	$("#poi_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');

	nxtTokinDialog.dialog( "open" );
	jQuery('.justread').attr('readonly', false);


	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();
		jQuery('.justread').attr('readonly', true);

	}


}
function validateUpdatePWI(pwi_id)

{

	$("#editnxtTokinformID").validate({

		rules: {

			fname_kin:"required",
			//mname_kin:"required",
			lname_kin:"required",
			address_kin:"required",
			ref_id_kin:"required"


		},
		messages: {

			fname_kin:FIELD_REQ,
			//mname_kin:"Please enter middle name",
			lname_kin:FIELD_REQ,
			address_kin:FIELD_REQ,
			ref_id_kin:FIELD_REQ
		}

	});


	if ($("#editnxtTokinformID").valid())
	{

		if($('#gender_kin').val()!=0){
			updatePWI(pwi_id);
		}
		else{
			jAlert(Gender,Alert);
		}
	}
	else{
		jAlert(Fill_Mandatory,Alert);
	}
}


function updatePWI(pwi_id)
{
	var usin=editList[0].usin;
	$('#usin_kin').val(usin);
	$('#id_kin').val(pwi_id);
	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatepwi",
		async: false,
		data: jQuery("#editnxtTokinformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				nxtTokinDialog.dialog("destroy");
				nxtTokinDialog.dialog( "close");
				$('#editnxtTokinformID')[0].reset();
				editAttribute(usin);
				jAlert(DATA_SAVED,Alert);
			}

			else
			{

				jAlert(FAILED);

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(FAILED);
		}
	});	

}

function addPWI()
{

	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	

			genderList=data;
		}


	});
	jQuery("#gender_kin").empty();
	if(lang=="en"){
		jQuery("#gender_kin").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
		jQuery.each(genderList, function (i, genderobj) {

			jQuery("#gender_kin").append(jQuery("<option></option>").attr("value",genderobj.genderId).text(genderobj.gender)); 

		});
	}
	else if(lang=="fr"){
		jQuery("#gender_kin").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner"));
		jQuery.each(genderList, function (i, genderobj) {

			jQuery("#gender_kin").append(jQuery("<option></option>").attr("value",genderobj.genderId).text(genderobj.gender_sw)); 

		});
	}
	nxtTokinDialog = $( "#nxtTokin-dialog-form" ).dialog({
		autoOpen: false,
		height: 414,
		width: 304,
		resizable: false,
		modal: true,

		buttons: [{
			text: "Save",
			"id": "poi_save",
			click: function () {
				validateUpdatePWI(0);



			},
		},
		{
			text: "Cancel",
			"id": "poi_canc",
			click: function () {
				nxtTokinDialog.dialog( "destroy" );
				nxtTokinDialog.dialog( "close" );
				$('#editnxtTokinformID')[0].reset();


			}
		}],
		close: function() {

			nxtTokinDialog.dialog( "destroy" );
			nxtTokinDialog.dialog( "close" );
			$('#editnxtTokinformID')[0].reset();

		}
	});
	$("#poi_save").html('<span class="ui-button-text">'+ Save +'</span>');
	$("#poi_canc").html('<span class="ui-button-text">'+ Cancel +'</span>');
	nxtTokinDialog.dialog( "open" );
	jQuery('.justread').attr('readonly', false);

}

function deletenxtTokin(id,name)
{		
	var usin=editList[0].usin;

	jConfirm(DELETE_MSG+' : <strong>' + name + '</strong>',Delete_Confirmation , function (response) {

		if (response) {
			jQuery.ajax({

				url: "landrecords/deletekin/"+id,
				async: false,
				success: function (result) 
				{
					if(result==true)
					{
						jAlert(DELETE_SUCCESS, Alert);	                  
						editAttribute(usin);
					}

				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert(FAILED);
				}
			});
		}

	});


}

var finalValidation= function(){

	$('#validationCheck').show();
	$(function() {
		$( "#dialog-confirm" ).dialog({
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"Validate and Update": function() {

					validationchecks();
					$( this ).dialog( "close" );
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			}
		});
	});
}

var validationchecks = function(){

	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/validator/"+id,
		async:false,							
		success: function (data) {	

			validator=data;
			if(validator=='Success')
				jAlert('Data successfully validated','Validation Info');
			else
				jAlert(validator,'Validation Error','Validation Error');

		}

	});

	if(validator=='Success')
	{

		editAttrDialog.dialog( "destroy" ); 
		$('#tabs').tabs("select","#tabs-1");
	}

}
function changeResident(val) {
	$('#tenureclass_id').val(val.value);
}

function exportData(){


	window.open("landrecords/exportdata/",'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

}

function Actionfill(usin,workflowId ,appTypeId,section){

	sharetype=appTypeId;
	var appid='#'+usin;
	jQuery.ajax({ 
		url: "landrecords/action/"+roleId+"/"+workflowId,
		async:false,							
		success: function (data) {	

			actionList=data;
		}


	});


	$(".containerDiv").empty();  
	$(""+appid+"").empty();
	html="";

	if (actionList.length==undefined){
		jAlert(No_action_req);
	}
	else{
		/*	for(var i=0;i<actionList.length;i++){
		if(workflowId==7 || workflowId==12){
			if(actionList[i].name!='Approve' && actionList[i].name!='Reject')
				html+="<li> <a title='"+(actionList[i].name)+"' id="+workflowId+" name="+usin+" href='#' onclick='CustomAction(this)'>"+actionList[i].name+"</a></li>";	
		}
		else{
			html+="<li> <a title='"+(actionList[i].name)+"' id="+workflowId+" name="+usin+" href='#' onclick='CustomAction(this)'>"+actionList[i].name+"</a></li>";
		}

	}*/
		for(var i=0;i<actionList.length;i++){
			if(lang=="en")
			html+="<li> <a title='"+(actionList[i].name)+"' id="+workflowId+" name="+usin+" type="+section+" href='#' onclick='CustomAction(this)'>"+actionList[i].name+"</a></li>";
			else if (lang=="fr")
				html+="<li> <a title='"+(actionList[i].name)+"' id="+workflowId+" name="+usin+" type="+section+" href='#' onclick='CustomAction(this)'>"+actionList[i].name_fr+"</a></li>";
		}
		$(""+appid+"").append('<div class="signin_menu"><div class="signin"><ul>'+html+'</ul></div></div>');


		$(".signin_menu").toggle();
		$(".signin").toggleClass("menu-open");


		$(".signin_menu").mouseup(function() {
			return false
		});
		$(document).mouseup(function(e) {
			if($(e.target).parent("a.signin").length==0) {
				$(".signin").removeClass("menu-open");
				$(".signin_menu").hide();
			}
		});
	}
}

function CustomAction(refrence){

	var actiontmp=new actionLandRecords();
	if((refrence.title).trim() == "Approve")
	{
		
		dialogueAction(refrence.name,refrence.id,refrence.title);
	}

	else if((refrence.title).trim() == "Reject")

	{
		dialogueAction(refrence.name,refrence.id,refrence.title);

	}

	else if((refrence.title).trim()=="Edit Attributes"){

		actiontmp.EditAttribute(refrence.name);
	}

	else if((refrence.title).trim()=="View Attributes"){

		actiontmp.ViewAttribute(refrence.name);
	}
	else if((refrence.title).trim() == "View Map")
	{
		showOnMap(refrence.name);
	}
	else if((refrence.title).trim() == "Generate Map")
	{
		actiontmp.printDialog(refrence.name,sharetype);
	}
	else if((refrence.title).trim() == "Generate Forms")
	{
		actiontmp.generateForms(refrence.name,refrence.id,sharetype);
	}


	else if((refrence.title).trim() == "View Parcel Number")
	{
		actiontmp.generateParcelNumber(refrence.name,refrence.id,refrence.type);
	}

	else if((refrence.title).trim() == "Payment Info")
	{
		actiontmp.paymentDialog(refrence.name);
	}
	else if((refrence.title).trim() == "Signatory Info")
	{
		actiontmp.signatoryDialog(refrence.name);
	}

}
function approveParcel(actionName,workId,usin){

	var Status_confirmation=""; 
	var do_you_want="";
	var selectd_parcel="";
	var action="";
	if(lang=='en'){
		 do_you_want="Do you want to ";
		 selectd_parcel=" selected parcel ?";
		 action=actionName;
		 Status_confirmation="Status Confirmation";
	}
	else if(lang=='fr'){
		 do_you_want="Etes-vous certain de vouloir ";
		 selectd_parcel=" la parcelle sélectionnée ?";
		
		if(actionName=='Approve')
			action="valider";
		else if(actionName=='Reject')
			action="rejeter";
		Status_confirmation="Confirmation du statut";
	}
	jConfirm(do_you_want+'<strong> '+action+' </strong> '+selectd_parcel, Status_confirmation, function (response) {

		if (response) {

			var actiontmp=new actionLandRecords();

			if(actionName=="Approve")
			{
				var status=actiontmp.ApproveStatus(usin,workId);
				if(status!=0){

					workflowTmp=status;
					//document.getElementById("approveParcel-dialog-form").reset();
					$('#commentsStatus').val("");
					approveStatusDialog.dialog("refresh");
					approveStatusDialog.dialog( "destroy");
					approveStatusDialog.dialog( "close" );

					jAlert(Approve,Alert);

					displayRefreshedLandRecords("LandRecords");


				}

				else {
					$('#commentsStatus').val("");

					approveStatusDialog.dialog( "destroy");
					approveStatusDialog.dialog( "close" );

					jAlert(FAILED);
				}

			}
			else if(actionName=="Reject")
			{
				var status=actiontmp.RejectStatus(usin,workId);
				if(status!=0){
					workflowTmp=status;
					//document.getElementById("approveParcel-dialog-form").reset();
					$('#commentsStatus').val("");
					approveStatusDialog.dialog("refresh");
					approveStatusDialog.dialog( "destroy" );
					approveStatusDialog.dialog( "close" );

					jAlert(Reject,Alert);

					displayRefreshedLandRecords("LandRecords");

				}

				else {
					$('#commentsStatus').val('');
					approveStatusDialog.dialog( "destroy");
					approveStatusDialog.dialog( "close" );

					jAlert(FAILED);

				}

			}

		}

	});

}


function dialogueAction(usin,workId,actionName){



	var val_gen = "Generate the following form before approval :" +"<br/>" + "i) Application Form";
	var prep_adj = "Generate the following forms before approval :" +"<br/>" + "i) Application Form" +"<br/>" + "ii) Public Notice"+"<br/>" + "iii) PV Contradictory Form"+"<br/>";
	var apfr ="Generate the following forms before approval :" +"<br/>" + "i) Payment Request Letter"+"<br/>" + "ii) APFR Form";
	var val_gen_fr= "Production des formulaires suivant avant approbation :" +"<br/>" + "i) Formulaire de demande";
	var prep_adj_fr="Production des formulaires suivant avant approbation :" +"<br/>" + "i) Formulaire de demande" +"<br/>" + "ii) Avis de publicité"+"<br/>" + "iii) PV de constatation foncière"+"<br/>";
	var apfr_fr ="Production des formulaires suivant avant approbation :" +"<br/>" + "i) Lettre de demande de paiement"+"<br/>" + "ii) APFR";
	if((workId ==2 || workId ==5 || workId ==7  || workId ==13) && (actionName=="Approve"))
	{
if(lang=="en"){
		if(workId ==2){
			$('#approveInfoDiv').html(val_gen).css("color", "blue")
		}
		if(workId ==5){
			$('#approveInfoDiv').html(prep_adj).css("color", "blue")
		}
		if(workId ==7 || workId ==13){
			$('#approveInfoDiv').html(apfr).css("color", "blue")
		}
}
else if(lang=="fr"){
	if(workId ==2){
		$('#approveInfoDiv').html(val_gen_fr).css("color", "blue")
	}
	if(workId ==5){
		$('#approveInfoDiv').html(prep_adj_fr).css("color", "blue")
	}
	if(workId ==7 || workId ==13){
		$('#approveInfoDiv').html(apfr_fr).css("color", "blue")
	}
}
		approveInfoDialog = $( "#approveInfo-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 370,
			resizable: false,
			modal: true,

			buttons: [{
				text: "Ok",
				"id": "info_ok",
				click: function () {
					approveDialog(usin,workId,actionName);
					approveInfoDialog.dialog( "destroy" );
					approveInfoDialog.dialog( "close" );

				},
			},
			{
				text: "Cancel",
				"id": "info_cancel",
				click: function () {

					approveInfoDialog.dialog( "destroy" );
					approveInfoDialog.dialog( "close" );

				}
			}],
			close: function() {

				approveInfoDialog.dialog( "destroy" );

			}
		});
		$("#info_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
		$("#info_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
		approveInfoDialog.dialog( "open" );
	}
	else{
		approveDialog(usin,workId,actionName);
	}
}

function approveDialog(usin,workId,actionName){
	approveStatusDialog = $( "#approveParcel-dialog-form" ).dialog({
		autoOpen: false,
		height: 200,
		width: 410,
		resizable: false,
		modal: true,

		/*	buttons: {
			"Ok": function() 
			{

				var dpicomment=$('#commentsStatus').val();
				if((dpicomment==undefined || dpicomment=='') && actionName=='Reject'){
					jAlert("Please provide comment","Alert");
				}
				else{
					approveParcel(actionName,workId,usin);	
				}


			},
			"Cancel": function() 
			{
				$('#commentsStatus').text("");
				//$('body').find("#approveParcel-dialog-form").remove();
				$('#approveStatusformID')[0].reset();
				approveStatusDialog.dialog( "destroy" );
				approveStatusDialog.dialog( "close" );

			}
		},*/
		close: function() {
			$('#commentsStatus').text("");
			approveStatusDialog.dialog( "destroy" );


		}
	});
	$('#commentOption').text(optional);
	if(roleId==9){
		$('#commentOption').text('');
	}
	if(actionName=='Reject'){
		$('#commentOption').text('');
		if(lang=="en")
		$('#action_name').text("Reject");
		else if(lang=="fr")
			$('#action_name').text("Rejeter");
	}
	else{
		if(lang=="en")
		$('#action_name').text("Approve");
		else if(lang=="fr")
			$('#action_name').text("Valider");
	}

	$("#approveButton").click(function(){
		approveRecords(usin,workId,actionName);
	});
	approveStatusDialog.dialog( "open" );	

}
function approveRecords(usin,workId,actionName){

	var dpicomment=$('#commentsStatus').val();
	if((dpicomment==undefined || dpicomment=='') && actionName=='Reject'){
		jAlert(Comment_confirmation,Alert);
	}
	else if(roleId==9 && (dpicomment==undefined || dpicomment=='') ){
		jAlert(Comment_confirmation,Alert);
	}
	else{
		approveParcel(actionName,workId,usin);	
	}
}
function closeDialogbox(){
	$('#commentsStatus').text("");
	//$('body').find("#approveParcel-dialog-form").remove();
	$('#approveStatusformID')[0].reset();
	approveStatusDialog.dialog( "destroy" );
	approveStatusDialog.dialog( "close" );
}
function search(){
	records_from=0;
	spatialSearch(records_from);


}

function commentsDialog(usin)
{


	var commentsobj = new comments();
	commentsStatusList= commentsobj.commentsDisplay(usin);

	jQuery('#commentsHistoryTableBody').empty();

	if(commentsStatusList!=null || commentsStatusList!=undefined || commentsStatusList.toString()!=""){
		for (var i=0;i<commentsStatusList.length;i++)
		{
			jQuery("#commentstemplate").tmpl(commentsStatusList[i]).appendTo("#commentsHistoryTableBody");
			jQuery("#commentsTable").show();
		}

		//	
		commentHistoryDialog = $( "#commentsDialog" ).dialog({
			autoOpen: false,
			height: 242,
			width: 666,
			resizable: true,
			modal: true,

			buttons: [{
				text: "Cancel",
				"id": "comment_cancel",
				click: function () {
					commentHistoryDialog.dialog( "destroy" );
					commentHistoryDialog.dialog( "close" );

				}
			}],
			close: function() {
				commentHistoryDialog.dialog( "destroy" );


			}
		});
		$("#comment_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
		//jQuery('#commentsTable').hide();
		commentHistoryDialog.dialog( "open" );

	}
	else{
		jAlert(No_record,Alert);
	}

}
function mapCoordinatesUrl(usin){

	var cql ="usin='"+usin+"'";
	var bbox;
	var wfsurl="http://"+location.host+"/geoserver/wfs?";
	var wmsurl="http://"+location.host+"/geoserver/wms?";
	var geomInfo=wfsurl+"request=GetFeature&typeName=BF_Pilot:spatial_unit&CQL_FILTER="+cql+"&version=1.0.0";

	var request = new OpenLayers.Request.GET({
		url: geomInfo,
		async:false,
		//data: postData,
		headers: {
			"Content-Type": "text/xml;charset=utf-8"
		},
		callback: function (response) {
			//read the response from GeoServer
			var gmlReader = new OpenLayers.Format.GML({ extractAttributes: true });
			features = gmlReader.read(response.responseText);
			boundsList= features[0].geometry.getBounds();
			// do what you want with the features returned...
		},
		failure: function (response) {
			jAlert(FAILED);
		}
	});

	//vertex list

	Proj4js.defs["EPSG:32630"] = "+proj=utm +zone=30 +ellps=WGS84 +datum=WGS84 +units=m +no_defs";

	var epsg32630 = new OpenLayers.Projection('EPSG:32630');
	var epsg4326   = new OpenLayers.Projection('EPSG:4326');

	var meterGeomVertex = features[0].geometry.clone();
	meterGeomVertex = meterGeomVertex.transform(epsg4326,epsg32630);
	vertexlist=meterGeomVertex.getVertices();

	var tempStr="";
	for(var i=0;i<vertexlist.length;i++)
	{
		if (tempStr=="") {
			tempStr = vertexlist[i].x.toFixed(4) + "," + vertexlist[i].y.toFixed(4);

		} 
		else {
			tempStr = tempStr + "," + vertexlist[i].x.toFixed(4) + "," + vertexlist[i].y.toFixed(4);
		}
	}
	var serverData = {"vertexList":tempStr};
	$.ajax({

		type : 'POST',
		url: "landrecords/vertexlabel",
		data: serverData,
		async:false,
		success: function(data){

		}
	});

	var bounds=createSquareBounds(boundsList);
	bbox = bounds.left + ',' + bounds.bottom
	+ ',' + bounds.right + ','
	+ bounds.top;


	var generateMapCoordinatesUrl = wmsurl+"bbox="+bbox+"&FORMAT=image/png&REQUEST=GetMap&layers=BF_Pilot:spatial_unit&width=250&height=250&srs=EPSG:4326"+"&cql_filter=usin="+usin+"";
	$('#parcel_map').empty();
	$('#parcel_map').append('<img id="theImg" src='+generateMapCoordinatesUrl+'>');

}







function generateReport(){

	jQuery.ajax({ 
		url: "landrecords/village/",
		async:false,							
		success: function (data) {	

			villageList=data;
		}


	});

	reportDialog = $( "#reportform-dialog-form" ).dialog({
		autoOpen: false,
		height: 370,
		width: 280,
		resizable: false,
		modal: true,
		buttons: [{

			text: "Cancel",
			"id": "report_cancel",
			click: function () {
				$('#reportByTenure').hide();
				$('#registryReport').hide();
				reportDialog.dialog( "destroy" );

			}
		}],
			close: function() {

				$('#reportByTenure').hide();
				$('#registryReport').hide();
				reportDialog.dialog( "destroy" );

			}
	});

	$("#report_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	reportDialog.dialog("open");

}

function reportButtonClick(){

	$('#reportByTenure').show();
	$('#registryReport').hide();
}

function registryButtonClick(){

	$('#reportByTenure').hide();
	$('#registryReport').show();
}
function getVillage(){
	jQuery("#village_sel").empty();
	if(lang=="en"){
		jQuery("#village_sel").append(jQuery("<option></option>").attr("value", 0).text("Please Select")); 
		jQuery.each(villageList, function (i, villobj) {
			jQuery("#village_sel").append(jQuery("<option></option>").attr("value", villobj.villageId).text(villobj.villageName)); 

		});
	}
	else if(lang=="fr"){
		jQuery("#village_sel").append(jQuery("<option></option>").attr("value", 0).text("Merci de sélectionner")); 
		jQuery.each(villageList, function (i, villobj) {
			jQuery("#village_sel").append(jQuery("<option></option>").attr("value", villobj.villageId).text(villobj.villageNameFr)); 

		});
	}
	villageSelDialog = $( "#villageSel-dialog-form" ).dialog({
		autoOpen: false,
		height: 240,
		width: 242,
		resizable: false,
		modal: true,

		buttons: [{
			text: "Ok",
			"id": "village_ok",
			click: function () {
				$('#reportByTenure').hide();
				$('#registryReport').hide();
				var rep = "";
				var selected = $("#radioReport input[type='radio']:checked");
				var villageSelected = $('#village_sel').val();
				if (selected.length > 0 && villageSelected!=0) {
					rep = selected.val();
				}
				else{
					jAlert(Village,Alert);
				}


				if(rep!='0')
				{

					var reportTmp=new reports();
					if(rep=="1")
					{
						console.log(villageSelected);
						reportTmp.ParcelCountByTenure("NEW",villageSelected);
						reportDialog.dialog( "destroy" );
					}
					else if(rep=="2")
					{
						reportTmp.ParcelCountByGender("NEW",villageSelected);
						reportDialog.dialog( "destroy" );
					}
					else if(rep=="3")
					{
						reportTmp.ParcelCountByTenure("REGISTERED",villageSelected);
						reportDialog.dialog( "destroy" );
					}
					else if(rep=="4")
					{
						reportTmp.ParcelCountByGender("REGISTERED",villageSelected);
						reportDialog.dialog( "destroy" );
					}
					else if(rep=="5")
					{
						reportTmp.ParcelCountByTenure("APFR",villageSelected);
						reportDialog.dialog( "destroy" );
					}
					else if(rep=="6")
					{
						reportTmp.ParcelCountByGender("APFR",villageSelected);
						reportDialog.dialog( "destroy" );
					}

					else if(rep=="7")
					{
						reportTmp.RegistryParcel("PROCESSEDAPPLICATION",villageSelected);
						reportDialog.dialog( "destroy" );
					}

					else if(rep=="8")
					{
						reportTmp.RegistryParcel("PUBLISHEDAPPLICATION",villageSelected);
						reportDialog.dialog( "destroy" );
					}

					else if(rep=="9")
					{
						reportTmp.RegistryParcel("PROCESSEDAPFR",villageSelected);
						reportDialog.dialog( "destroy" );
					}

				}else
				{
					jAlert(Option_sel,Alert);
				}
			},
		},
		{
			text: "Cancel",
			"id": "village_cancel",
			click: function () {
				villageSelDialog.dialog( "destroy" );
				villageSelDialog.dialog( "close" );

			}
		}],
		close: function() {
			villageSelDialog.dialog( "destroy" );


		}
	});
	$("#village_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
	$("#village_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	villageSelDialog.dialog( "open" );
}

function langChangeStrings(){
	$('#reportform-dialog-form').attr("title",$._('dashboard_reportform'));
	$('#upload-dialog-form').attr("title",$._('dashboard_upload'));
	$('#editattribute-dialog-form').attr("title",$._('dashboard_editattribute'));
	$('#naturalperson-dialog-form').attr("title",$._('dashboard_naturalperson'));
	$('#nxtTokin-dialog-form').attr("title",$._('dashboard_nxtTokin'));
	$('#multimedia-dialog-form').attr("title",$._('dashboard_multimedia'));
	$('#approveParcel-dialog-form').attr("title",$._('dashboard_approveParcel'));
	$('#generatemap-dialog-form').attr("title",$._('dashboard_generatemap'));
	$('#paymentInfo-dialog-form').attr("title",$._('dashboard_paymentInfo'));
	$('#commentsDialog').attr("title",$._('dashboard_comments'));
	$('#villageSel-dialog-form').attr("title",$._('dashboard_villageSel'));
	$('#approveInfo-dialog-form').attr("title",$._('dashboard_approveInfo'));
	$('#signatory-dialog-form').attr("title",$._('dashboard_signatory'));
	$('.master_attrib').html($._('dashboard_masterattrib'));
	$('.custom').html($._('dashboard_custom'));
	
	
}



function firstRecords()

{
	//$("#landRecordsTable").tablesorter( {widgets: ['zebra']} ); 
	


		if(searchRecords!=null)
		{
		
			spatialSearch(0);
		}
		else{

			
			spatialRecords(0);	
		}


	

}

function LastRecords()

{

	if(searchRecords!=null)
	{
		if(searchRecords-5>=0){
			spatialSearch(searchRecords-5);	
		}
		else{
			spatialSearch(0);
		}
		
	}
	else{
		
		if(totalRecords-5>=0){
			spatialRecords(totalRecords-5);	
		}
		else{
			spatialRecords(0);
		}
		
		
	}

}
