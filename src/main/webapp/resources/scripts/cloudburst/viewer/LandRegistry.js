var selectedItem=null;
var recordList=null;
var activeProject="";
var records_from=0;
var totalRecords=null;
var totalRecordsLandRegistry=null;

function LandRegistry(_selectedItem)
{

	selectedItem=_selectedItem;


	displayRefreshedLandRegistry();

}

function displayRefreshedLandRegistry()
{

	jQuery("#landregistry-div").empty();

	jQuery.get("resources/templates/viewer/" + selectedItem + ".html", function (template) {

		jQuery("#landregistry-div").append(template);

		jQuery('#landRegistryFormdiv').css("visibility", "visible");		    			    	

		var lanChnag=new changeLang();
		lanChnag.first();
		jQuery("#landRegistryTable").show();

		spatialRecordsRegistry(0);
		$("#landRegistryTable").tablesorter( {sortList: [[0,1], [1,0]]} ); 




	});		



}

function spatialRecordsRegistry(records_from)
{


	records_from=records_from;
	var workflow=new workflowStatus();
	dataList=workflow.landRegistry(records_from);
	totalRecords=null;
	totalRecordsLandRegistry=workflow.landRegistrycount();
	$('#landRegistryRowData').empty();
	jQuery("#landRegistryAttrTemplate").tmpl(dataList).appendTo("#landRegistryRowData");
	$("#landRegistryTable").trigger("update");
	$('#records_from_Registry').val(records_from+1);
	$('#records_to_Reg').val(totalRecordsLandRegistry);
	if(records_from+5<=totalRecordsLandRegistry)
		$('#records_to_Reg').val(records_from+5);
	$('#records_all_Reg').val(totalRecordsLandRegistry);



}

function nextRegistryRecords()

{
	records_from= $('#records_from_Registry').val();
	records_from=parseInt(records_from);
	records_from=records_from+4;
	var records_to_Reg=$('#records_to_Reg').val();



	if(totalRecords!=null)
	{
		if(records_to_Reg<=totalRecords-1)
			spatialRegistrySearch(records_from);
		else
			alert(FAILED);

	}
	else{
		if(records_to_Reg<=totalRecordsLandRegistry-1)

			spatialRecordsRegistry(records_from);	
		else
			alert(FAILED);

	}



}

function previousRegistryRecords()

{
	records_from= $('#records_from_Registry').val();
	records_from=parseInt(records_from);
	records_from=records_from-6;
	if(records_from>=0)
	{

		if(totalRecords!=null)
		{

			spatialRegistrySearch(records_from);
		}
		else{

			spatialRecordsRegistry(records_from);	
		}

	}

	else{

		alert(FAILED);

	}

}

function spatialRegistrySearch(records_from){

	records_from=records_from;

	var actionRegistry=new actionLandRecords();
	dataList=actionRegistry.searchRegistry(records_from);
	totalRecords=actionRegistry.searchRegistryCount();
	totalRecordsLandRegistry=null;
	$('#landRegistryRowData').empty();
	jQuery("#landRegistryAttrTemplate").tmpl(dataList).appendTo("#landRegistryRowData");
	$("#landRegistryTable").trigger("update");
	$('#records_from_Registry').val(records_from+1);
	$('#records_to_Reg').val(totalRecords);
	if(records_from+5<=totalRecords)
		$('#records_to_Reg').val(records_from+5);
	$('#records_all_Reg').val(totalRecords);

}

function firstRegistryRecords()

{
	//$("#landRecordsTable").tablesorter( {widgets: ['zebra']} ); 
	


		if(totalRecords!=null)
		{
		
			spatialRegistrySearch(0);
		}
		else{

			
			spatialRecordsRegistry(0);	
		}


	

}

function LastRegistryRecords()

{

	if(totalRecords!=null)
	{
		if(totalRecords-5>=0){
			spatialRegistrySearch(totalRecords-5);	
		}
		else {
			spatialRegistrySearch(0);
		}
		
	}
	else{
		if(totalRecordsLandRegistry-5>=0){
			spatialRecordsRegistry(totalRecordsLandRegistry-5);	
		}
		else {
			spatialRecordsRegistry(0);
		}
		
		
	}

}


