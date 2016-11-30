var checkForm="0";
var checkoptions="0";
var spatialLst=null;

function workflowStatus(){


}

workflowStatus.prototype.AllworkFlow = function () {

	var workflowLst=[];

	jQuery.ajax({ 
		url: "landrecords/workflowLst/",
		async:false,							
		success: function (data) {	

			workflowLst=data;
		}


	});

	return workflowLst;

};


workflowStatus.prototype.spatialbyWorkFlow=function (rec_from){

	


	if(workflowTmp!=0){

		//$('input[type=checkbox][name="workflow"][value="1"]').prop('checked', false);
		$('input[type=checkbox][name="workflow"][value='+workflowTmp+']').prop('checked', true);

	}


	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/spatialunitbyworkflow/"+activeProject+"/"+rec_from,
		data: jQuery("#updatebyWorkflow").serialize(),
		async:false,							
		success: function (data) {
			spatialLst=data;

		}


	});

	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunit/"+activeProject,
		async:false,
		data: jQuery("#updatebyWorkflow").serialize(),
		success: function (data) {	

			totalRecords=data;
		}


	});

	return spatialLst;
};

function updatespatialwork(){

	workflowTmp=0;
	spatialRecords(0);

	/*var workFlows = new workflowStatus();
			var tmpLst=workFlows.spatialbyWorkFlow();
			$('#landRecordsRowData').empty();
			jQuery("#landRecordsAttrTemplate").tmpl(tmpLst).appendTo("#landRecordsRowData");*/


}



workflowStatus.prototype.landRegistry=function (rec_from){


	array =[9,14]; //for landregistry workflow
	//var spatialLst=[];
	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/spatialunitbyworkflow/"+activeProject+"/"+rec_from,
		datatype: "json",
		traditional: true,
		data: {"workflow":array} ,
		async:false,							
		success: function (data) {
			spatialLst=data;

		}


	});



	return spatialLst;

};

workflowStatus.prototype.landRegistrycount=function (){
	var totalRecordsRegistry=0;
	array =[9,14]; //for landregistry workflow
	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunit/"+activeProject,
		async:false,
		datatype: "json",
		traditional: true,
		data: {"workflow":array} ,
		success: function (data) {	

			totalRecordsRegistry=data;
		}


	});

	return totalRecordsRegistry;
};

function actionLandRecords(){


}

actionLandRecords.prototype.ApproveStatus=function(usin,workflowId){

	var approve=false;

	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/actionapprove/"+usin+"/"+workflowId,
		data: jQuery("#approveStatusformID").serialize(),
		async:false,							
		success: function (data) {

			approve=data;
		}


	});


	return approve;
};



actionLandRecords.prototype.RejectStatus=function(usin,workflowId){

	var reject=false;
	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/actionreject/"+usin+"/"+workflowId,
		data: jQuery("#approveStatusformID").serialize(),
		async:false,							
		success: function (data) {

			reject=data;
		}


	});

	return reject;

};

actionLandRecords.prototype.printDialog=function(usin,sharetype){

	if(sharetype==7)
	{
		$(".checkType").hide();
		$('label[for="sel-4"]').hide();

	}
	else if(sharetype==8){
		$(".checkType").show();
		$('label[for="sel-4"]').show();

	}
	Dialog = $( "#generatemap-dialog-form" ).dialog({
		autoOpen: false,
		height: 250,
		width: 280,
		resizable: false,
		modal: true,
		buttons: [{
			 text: "Ok",
		        "id": "generate_map_ok",
		        click: function () {
				var option1 = checkoptions;

				if(option1!='0')
				{
					if(option1=="1")
					{
						generateBoundaryMap(usin);
						Dialog.dialog( "destroy" );
					}
					else if(option1=="2")
					{
						generateAreaMap(usin);
						Dialog.dialog( "destroy" );
					}
					else if(option1=="3")
					{
						generateform1(usin,2);
						Dialog.dialog( "destroy" );
					}
					else if(option1=="4")
					{
						generateform2(usin,2);
						Dialog.dialog( "destroy" );
					}
				}else
				{
					jAlert("Please select an option","Alert");
				}


			},
		},{
			 text: "Cancel",
		        "id": "generate_map_cancel",
		        click: function () {
				Dialog.dialog( "destroy" );

			}
		}],

		close: function() {

			Dialog.dialog( "destroy" );

		}
	});
	 $("#generate_map_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
	 $("#generate_map_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	Dialog.dialog("open");
	$('input[type=radio][name=map]').change(function() {
		$('input:radio[name="map"][value='+this.value+']').prop('checked', true);
		checkoptions=this.value;

	});

	checkoptions="1";
	$('input:radio[name="map"][value="1"]').prop('checked', true);

}


actionLandRecords.prototype.ViewMap=function(usin){
	mapImage(usin);

};

actionLandRecords.prototype.ViewAttribute=function(usin){

	read=true;
	editAttribute(usin);
};


actionLandRecords.prototype.EditAttribute=function(usin){

	read=false;
	editAttribute(usin);
};



actionLandRecords.prototype.search=function(rec_from){
	var searchRecords=[];
	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunit/search/"+activeProject+"/"+rec_from,
		async:false,
		data: jQuery('#updatebyWorkflow, #landrecordsform').serialize(),
		success: function (data) {	

			searchRecords=data;
		}


	});
	return searchRecords;

};


actionLandRecords.prototype.searchCount=function(){
	var totalRecords=0;
	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunit/search/"+activeProject,
		async:false,
		data: jQuery('#updatebyWorkflow, #landrecordsform').serialize(),
		success: function (data) {	

			totalRecords=data;
		}


	});
	return totalRecords;

};


actionLandRecords.prototype.searchRegistry=function(rec_from){
	var searchRecords=[];
	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunit/search/"+activeProject+"/"+rec_from,
		async:false,
		data: jQuery('#landregistryform').serialize(),
		success: function (data) {	

			searchRecords=data;
		}


	});
	return searchRecords;

};


actionLandRecords.prototype.searchRegistryCount=function(){
	var totalRecords=0;
	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunit/search/"+activeProject,
		async:false,
		data: jQuery('#landregistryform').serialize(),
		success: function (data) {	

			totalRecords=data;
		}


	});
	return totalRecords;

};

actionLandRecords.prototype.generateParcelNumber=function(usin,workflowId,section){
	
	$('#section_no').text(section);
	$('#lot_no').text("000");
	$('#number_seq').text(usin);
/*
	jQuery.ajax({
		url: "landrecords/findcount/",
		async:false,
		success: function (data) {	
			$('#number_seq').val(data);
		}

	});

	jQuery.ajax({
		url: "landrecords/findexisting/"+usin,
		async:false,
		success: function (data) {	
			$('#section_no').val(data.section_no);
			$('#lot_no').val(data.lotno);
			$('#usin_parcel').text(usin);
			if(data.usin!=0)
			$('#usin_parcel').text(data.usin);
			 
		}

	});*/

	generateParcelDialog = $( "#generate-parcelno-form" ).dialog({
		autoOpen: false,
		height: 250,
		width: 300,
		resizable: false,
		modal: true,
		buttons:[{
			 text: "Ok",
		        "id": "gp_ok",
		        click: function () {
				generateParcelDialog.dialog( "destroy" );

			}
		}],

		close: function() {

			generateParcelDialog.dialog( "destroy" );

		}
	});
	 $("#gp_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
	generateParcelDialog.dialog("open");
};

function generateForms(){


}

generateForms.prototype.Form1=function(usin){

	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/form1/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;

};


generateForms.prototype.Form2=function(usin){

	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/form2/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;

};

generateForms.prototype.Form3=function(usin){

	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/form3/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;

};

generateForms.prototype.Form5=function(usin){

	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/form5/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;

};

generateForms.prototype.Form7=function(usin){

	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/form7/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;

};

generateForms.prototype.Form8=function(usin){

	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/form8/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;

};



generateForms.prototype.paymentDetails= function (usin){
	
	var formLst=[];
	jQuery.ajax({
		url: "landrecords/spatialunit/paymentdetail/"+usin,
		async:false,
		success: function (data) {	

			formLst=data;
		}


	});
	return formLst;
};

generateForms.prototype.checkDate= function (usin){
	
	var date=null;
	jQuery.ajax({
		url: "landrecords/checknoticedate/"+usin,
		async:false,
		success: function (data) {	

			date=data;
		}


	});
	return date;
};


generateForms.prototype.FeatureInfo=function(usin){

	var cql ="usin='"+usin+"'";

	var geomInfo="http://localhost:8080/geoserver/wfs?request=GetFeature&typeName=mast:spatial_unit&CQL_FILTER="+cql+"&version=1.0.0";

	console.log(geomInfo);

	var request = new OpenLayers.Request.GET({
		url: geomInfo,
		//data: postData,
		headers: {
			"Content-Type": "text/xml;charset=utf-8"
		},
		callback: function (response) {
			//read the response from GeoServer
			var gmlReader = new OpenLayers.Format.GML({ extractAttributes: true });
			var features = gmlReader.read(response.responseText);
			console.log(features);
			// do what you want with the features returned...
		},
		failure: function (response) {
			alert("Something went wrong in the request");
		}
	});


}

actionLandRecords.prototype.generateForms=function(usin ,workflowId ,sharetype){

		$('#radio2').show();
		$('#radio5').hide();
		$('#radio6').show();


	formDialog = $( "#applicationform-dialog-form" ).dialog({
		autoOpen: false,
		height: 370,
		width: 280,
		resizable: false,
		modal: true,
		buttons: [{
			 text: "Ok",
		        "id": "appform_ok",
		        click: function () {
				var option = checkForm;


				if(option!='0')
				{
					if(option=="1")
					{
						generateform1(usin,1);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
					else if(option=="2")
					{
						generateform2(usin,1);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
					else if(option=="3")
					{
						generateform3(usin);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
					else if(option=="4")
					{
						generateform7(usin);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
					else if(option=="5")
					{
						generateform5(usin);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
					else if(option=="6")
					{
						generateform8(usin);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
					else if(option=="7")
					{
						generatePaymentLetter(usin);
						formDialog.dialog( "destroy" );
						$('#applicationformID')[0].reset();
					}
				}else
				{
					jAlert(Option_sel,Alert);
				}


			},
		},
		{
			 text: "Cancel",
		        "id": "appform_cancel",
		        click: function () {
				formDialog.dialog( "destroy" );
				$('#applicationformID')[0].reset();

			}
		}],

		close: function() {

			formDialog.dialog( "destroy" );
			$('#applicationformID')[0].reset();

		}
	});


	$('input[type=radio][name=print]').change(function() {
		$('input:radio[name="print"][value='+this.value+']').prop('checked', true);
		checkForm=this.value;

	});


	checkForm="0";
	//$('input:radio[name="print"][value="1"]').prop('checked', true);	


	console.log(workflowId);
	switch(workflowId) {
	case "1":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'none');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');

		break;
	case "2":
		$('input[type=radio][name="print"][value="1"]').css('display', 'inline');
		if(sharetype==8){
			$('#radio2').css('display', 'inline');
			$('input[type=radio][name="print"][value="2"]').css('display', 'inline');
		}
		else if(sharetype==7){
			$('input[type=radio][name="print"][value="2"]').css('display', 'none');
			$('#radio2').css('display', 'none');
		}
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'none');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'inline');
		
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');

		break;
		
	case "3":
		$('input[type=radio][name="print"][value="1"]').css('display', 'inline');
		if(sharetype==8){
			$('#radio2').css('display', 'inline');
			$('input[type=radio][name="print"][value="2"]').css('display', 'inline');
		}
		else if(sharetype==7){
			$('input[type=radio][name="print"][value="2"]').css('display', 'none');
			$('#radio2').css('display', 'none');
		}
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'none');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'inline');
		//$('#radio2').css('display', 'inline');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');
		break;

	case "5":
		$('input[type=radio][name="print"][value="1"]').css('display', 'inline');
		if(sharetype==8){
			$('#radio2').css('display', 'inline');
			$('input[type=radio][name="print"][value="2"]').css('display', 'inline');
		}
		else if(sharetype==7){
			$('input[type=radio][name="print"][value="2"]').css('display', 'none');
			$('#radio2').css('display', 'none');
		}
		$('input[type=radio][name="print"][value="3"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="4"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="5"]').css('display', 'none');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'inline');
		//$('#radio2').css('display', 'inline');
		$('#radio3').css('display', 'inline');
		$('#radio4').css('display', 'inline');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');
		break;
	case "6":
		$('input[type=radio][name="print"][value="1"]').css('display', 'inline');
		if(sharetype==7){
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('#radio2').css('display', 'none');
		}
		if(sharetype==8){
			$('input[type=radio][name="print"][value="2"]').css('display', 'inline');
			$('#radio2').css('display', 'inline');
			}
		$('input[type=radio][name="print"][value="3"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="4"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="5"]').css('display', '');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'inline');
		
		$('#radio3').css('display', 'inline');
		$('#radio4').css('display', 'inline');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');
		break;

	case "7":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		if(sharetype==7){
			$('input[type=radio][name="print"][value="5"]').css('display', 'inline');
			$('#radio5').css('display', 'inline');
			$('input[type=radio][name="print"][value="6"]').css('display', 'none');
			$('#radio6').css('display', 'none');
		}
		
		else if(sharetype==8){
			$('input[type=radio][name="print"][value="5"]').css('display', 'none');
			$('#radio5').css('display', 'none');
			$('input[type=radio][name="print"][value="6"]').css('display', 'inline');
			$('#radio6').css('display', 'inline');
		}
		
		$('input[type=radio][name="print"][value="7"]').css('display', 'inline');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		
		
		$('#radio7').css('display', 'inline');

		break;

	case "8":
		$('input[type=radio][name="print"][value="1"]').css('display', 'inline');
		if(sharetype==8){
			$('#radio2').css('display', 'inline');
			$('input[type=radio][name="print"][value="2"]').css('display', 'inline');
		}
		else if(sharetype==7){
			$('input[type=radio][name="print"][value="2"]').css('display', 'none');
			$('#radio2').css('display', 'none');
		}
		$('input[type=radio][name="print"][value="3"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="4"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="5"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="6"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'inline');
		//$('#radio2').css('display', 'inline');
		$('#radio3').css('display', 'inline');
		$('#radio4').css('display', 'inline');
		$('#radio5').css('display', 'inline');
		$('#radio6').css('display', 'inline');
		$('#radio7').css('display', 'none');
		break;

	case "9":
		$('input[type=radio][name="print"][value="1"]').css('display', 'inline');
		
		$('input[type=radio][name="print"][value="3"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="4"]').css('display', 'inline');
		
		
		$('input[type=radio][name="print"][value="7"]').css('display', 'inline');
		
		if(sharetype==7){
			$('input[type=radio][name="print"][value="2"]').css('display', 'none');
			$('input[type=radio][name="print"][value="6"]').css('display', 'none');
			$('input[type=radio][name="print"][value="5"]').css('display', 'inline');
			$('#radio5').css('display', 'inline');
			$('#radio6').css('display', 'none');
			$('#radio2').css('display', 'none');
			
		}
		else if(sharetype==8){
			$('input[type=radio][name="print"][value="2"]').css('display', 'inline');
			$('input[type=radio][name="print"][value="6"]').css('display', 'inline');
			$('input[type=radio][name="print"][value="5"]').css('display', 'none');
			$('#radio5').css('display', 'none');
			$('#radio6').css('display', 'inline');
			$('#radio2').css('display', 'inline');
			
		
		$('#radio1').css('display', 'inline');
		$('#radio3').css('display', 'inline');
		$('#radio4').css('display', 'inline');
		$('#radio7').css('display', 'inline');
		}
		break;

	case "10":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'none');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');
		break;

	case "11":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'none');
		$('input[type=radio][name="print"][value="6"]').css('display', 'none');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'none');
		$('#radio6').css('display', 'none');
		$('#radio7').css('display', 'none');
		break;

	case "12":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="6"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="7"]').css('display', 'none');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'inline');
		$('#radio6').css('display', 'inline');
		$('#radio7').css('display', 'none');

		break;
	case "13":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="6"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="7"]').css('display', 'inline');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'inline');
		$('#radio6').css('display', 'inline');
		$('#radio7').css('display', 'inline');

		break;
	case "14":
		$('input[type=radio][name="print"][value="1"]').css('display', 'none');
		$('input[type=radio][name="print"][value="2"]').css('display', 'none');
		$('input[type=radio][name="print"][value="3"]').css('display', 'none');
		$('input[type=radio][name="print"][value="4"]').css('display', 'none');
		$('input[type=radio][name="print"][value="5"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="6"]').css('display', 'inline');
		$('input[type=radio][name="print"][value="7"]').css('display', 'inline');
		$('#radio1').css('display', 'none');
		$('#radio2').css('display', 'none');
		$('#radio3').css('display', 'none');
		$('#radio4').css('display', 'none');
		$('#radio5').css('display', 'inline');
		$('#radio6').css('display', 'inline');
		$('#radio7').css('display', 'inline');
		break;
	default:
		
	}
	 $("#appform_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
	 $("#appform_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	formDialog.dialog("open");
};


function comments() {

}

comments.prototype.commentsDisplay=function(usin){


	var commentList=[];
	jQuery.ajax({
		url: "landrecords/comments/"+usin,
		async:false,
		success: function (data) {	

			commentList=data;
		}


	});
	return  commentList ;
};

function validateParcelNumber(usin){
	
	$("#parcelnoformID").validate({

		rules: {
			section_no: {
		            required: true,
		            range: [ 1, 25 ]
		        },
		
			lot_no:"required"
		
			
		},
		messages: {
			//section_no: "Enter numeric value and range of section number is 1-25",
			lot_no:"Please enter lot number"
			
				}
	});

	if ($("#parcelnoformID").valid())
	{

		updateParcelNumber(usin);

	}

	else{

		jAlert("Please Fill Mandatory Details","Alert");
	}
}

function updateParcelNumber(usin){

	$('#usin_spa').val(usin);
	jQuery.ajax({
		type:"POST",        
		url: "landrecords/generateparcel" ,
		data: jQuery("#parcelnoformID").serialize(),
		success: function (result) 
		{  
			if(result!="")
			{
				jAlert('Data successfully saved Parcel No. is '+result,'Update');
				generateParcelDialog.dialog( "destroy" );
				generateParcelDialog.dialog( "close" );

			}
			else
			{
				jAlert(FAILED,Alert);
			}


		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(FAILED,Alert);
		}
	});

}



function reports(){


}

reports.prototype.ParcelCountByTenure=function(tag,villageId){

	window.open("landrecords/parcelcountbytenure/"+activeProject+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

}


reports.prototype.ParcelCountByGender=function(tag,villageId){

	window.open("landrecords/parcelcountbygender/"+activeProject+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

}

reports.prototype.RegistryParcel=function(tag,villageId){

	window.open("landrecords/registrytable/"+activeProject+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

}

function existinguse(){

}
existinguse.prototype.getExistingUseList=function(){

	var existingUselist=[];
	jQuery.ajax({
		url: "landrecords/landusertype/",
		async:false,
		success: function (data) {	

			existingUselist=data;
		}


	});
	return  existingUselist ;
};



actionLandRecords.prototype.paymentDialog=function(usin){
	
	var actiontmp=new actionLandRecords();
	
	paymentUpdateDialog = $( "#paymentInfo-dialog-form" ).dialog({
		autoOpen: false,
		height: 500,
		width: 450,
		resizable: false,
		modal: true,
		buttons: [{
			 text: "Ok",
		        "id": "payment_ok",
		        click: function () {
				//actiontmp.paymentUpdate(usin);
		        	actiontmp.validateFields(usin);
				//paymentUpdateDialog.dialog( "destroy" );

			},
		},
		{
			 text: "Cancel",
		        "id": "payment_cancel",
		        click: function () {
				paymentUpdateDialog.dialog( "destroy" );

			}
		}],

		close: function() {

			paymentUpdateDialog.dialog( "destroy" );

		}
	});
	 $("#payment_ok").html('<span class="ui-button-text">'+ Ok +'</span>');
	 $("#payment_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	paymentUpdateDialog.dialog("open");
};

actionLandRecords.prototype.validateFields=function(usin){
	var actiontmp=new actionLandRecords();
	$("#paymentInfoID").validate({

		rules: {

			receiptId: "required",
			paymentAmount:"required",
			paymentDate:"required"
			


		},
		messages: {
			receiptId: FIELD_REQ,
			paymentAmount:FIELD_REQ,
			paymentDate:FIELD_REQ
		
		}

	});

	if ($("#paymentInfoID").valid())
	{

		actiontmp.paymentUpdate(usin);

	}

	else{

		jAlert(Fill_Mandatory,Alert);
	}

	
};
actionLandRecords.prototype.paymentUpdate=function(usin){
	
	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatepayment/"+usin,
		data: jQuery("#paymentInfoID").serialize(),
		success: function (result) 
		{  
			if(result!="")
			{
				jAlert(Payment_confirm+" "+result,Payment_Info);
				paymentUpdateDialog.dialog( "destroy" );
				paymentUpdateDialog.dialog( "close" );

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
	
	
};


actionLandRecords.prototype.signatoryDialog=function(usin){
	
	var actiontmp=new actionLandRecords();
	$.ajax({

		type : 'GET',
		url: "landrecords/signatory"+usin,
		async:false,
		success: function(data){
			if(data.length!=undefined)
			jQuery('#signatoryDate').val(data);

		}
	});
	
	signatureDateUpdateDialog = $( "#signatory-dialog-form" ).dialog({
		autoOpen: false,
		height: 370,
		width: 370,
		resizable: false,
		modal: true,
		buttons: [{
			 text: "Update",
		        "id": "signatory_update",
		        click: function () {
				//actiontmp.updateSignatoryDate(usin);
		        	actiontmp.validateDate(usin);
		        	
				//signatureDateUpdateDialog.dialog( "destroy" );

			},
		},
		{
			 text: "Cancel",
		        "id": "signatory_cancel",
		        click: function () {
				signatureDateUpdateDialog.dialog( "destroy" );

			}
		}],

		close: function() {

			signatureDateUpdateDialog.dialog( "destroy" );

		}
	});
	 $("#signatory_update").html('<span class="ui-button-text">'+ Update +'</span>');
	 $("#signatory_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	signatureDateUpdateDialog.dialog("open");
};

actionLandRecords.prototype.validateDate=function(usin){
	var actiontmp=new actionLandRecords();
	$("#signatoryformID").validate({

		rules: {

			signatoryDate: "required"
	
		},
		messages: {
			signatoryDate: FIELD_REQ
	
		}

	});

	if ($("#signatoryformID").valid())
	{

		actiontmp.updateSignatoryDate(usin);

	}

	else{

		jAlert(Fill_Mandatory,Alert);
	}

	
};
actionLandRecords.prototype.updateSignatoryDate=function(usin){
	
	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatedate/"+usin,
		data: jQuery("#signatoryformID").serialize(),
		success: function (result) 
		{  
			if(result)
			{
				jAlert(DATA_SAVED,Update);
				signatureDateUpdateDialog.dialog( "destroy" );
				signatureDateUpdateDialog.dialog( "close" );

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
	
	
};

