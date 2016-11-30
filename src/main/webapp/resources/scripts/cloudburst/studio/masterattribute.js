
var DataList=null;
var selectedItem=null;
var AttributeCategoryList=null;

function MasterAttribute(_selectedItem)
{
	selectedItem=_selectedItem;

	/*if( jQuery("#masterAttrFormDiv").length<=0){

			displayRefreshedMasterAttr('All');

		}
		else{*/


	jQuery.ajax({
		url: "dataType/",
		async:false,
		success: function (data) {
			DataList = data;    

		}
	});


	jQuery.ajax({
		url: "attribcategory/",
		async:false,
		success: function (data) {
			AttributeCategoryList = data;    

		}
	});


	displayRefreshedMasterAttr('All');


}

function displayRefreshedMasterAttr(_attrcategory)
{

	jQuery.ajax({
		url: "masterattrib/",
		success: function (data) {

			jQuery("#masterattribute-div").empty();

			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {

				jQuery("#masterattribute-div").append(template);

				var lanChnag=new changeLang();
				lanChnag.first();

				jQuery('#masterAttrFormDiv').css("visibility", "visible");		    			    	


				$("#masterAttr_category").val(_attrcategory);

				jQuery("#masterAttrDetails").hide();	        	
				jQuery("#masterAttrsRowData").empty();

				jQuery("#masterAttrTable").show();	        	
				jQuery("#masterAttr_accordion").hide();		    	

				jQuery("#masterAttrTemplate").tmpl(data).appendTo("#masterAttrsRowData");


				jQuery("#masterAttr_btnNew").show();



				/*jQuery.each(DataList, function (i, _dataobj) {
					if(DataList[i].datatypeId!=5 && DataList[i].datatypeId!=6){
						jQuery("#type").append(jQuery("<option></option>").attr("value", _dataobj.datatypeId).text(_dataobj.datatype)); 
					}
				}); */
				if(lang=="en"){
					
					jQuery.each(DataList, function (i, _dataobj) {
						if(DataList[i].datatypeId!=5 && DataList[i].datatypeId!=6){
							jQuery("#type").append(jQuery("<option></option>").attr("value", _dataobj.datatypeId).text(_dataobj.datatype)); 
						}
					});
					jQuery("#category_sel").append(jQuery("<option></option>").attr("value", 0).text("All"));

					jQuery.each(AttributeCategoryList, function (i, _categoryobj) { 

						jQuery("#category").append(jQuery("<option></option>").attr("value", _categoryobj.attributecategoryid).text(_categoryobj.categoryName)); 
						jQuery("#category_sel").append(jQuery("<option></option>").attr("value", _categoryobj.attributecategoryid).text(_categoryobj.categoryName));

					}); 
				}
				else if(lang=="fr"){
					jQuery.each(DataList, function (i, _dataobj) {
						if(DataList[i].datatypeId!=5 && DataList[i].datatypeId!=6){
							jQuery("#type").append(jQuery("<option></option>").attr("value", _dataobj.datatypeId).text(_dataobj.datatype_fr)); 
						}
					});
					jQuery("#category_sel").append(jQuery("<option></option>").attr("value", 0).text("Tout"));
					jQuery.each(AttributeCategoryList, function (i, _categoryobj) { 
						jQuery("#category").append(jQuery("<option></option>").attr("value", _categoryobj.attributecategoryid).text(_categoryobj.category_name_fr)); 
						jQuery("#category_sel").append(jQuery("<option></option>").attr("value", _categoryobj.attributecategoryid).text(_categoryobj.category_name_fr));
					}); 		
				}

				$("#masterAttrTable").tablesorter({ 
					headers: {7: {sorter: false  },  5: {  sorter: false } },	
					debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
					.tablesorterPager({ container: $("#masterAttr_pagerDiv"), positionFixed: false })
					.tablesorterFilter({ filterContainer: $("#masterAttr_txtSearch"),                           
						filterColumns: [0],
						filterCaseSensitive: false,
						//filterWaitTime:1000 
					});

			});		

		}
	});
}

function changeMandatoryValue(_this) {
	if (_this.checked) {

		jQuery('#mandatory').val("true");
	}
	else {

		jQuery('#mandatory').val("false");
	}

}

function displaymasterAttr(){

	jQuery("#masterAttr_accordion").hide();


	jQuery("#masterAttrTable").show();
	jQuery("#masterAttr_btnNew").show();
}




var savemasterAttrData= function () 
{	



	jQuery.ajax({
		type: "POST",              
		url: "masterattrib/create" + "?" + token,
		data: jQuery("#addAttributeformID").serialize(),
		success: function (result) 
		{       
			if(result=='true')
			{
				jAlert(DATA_SAVED,Master_Attribute);
				displayRefreshedMasterAttr();
				attrDialog.dialog("destroy");
				$('body').find("#attribute-dialog-form").remove();
				$("#attribute-dialog-form").remove();
			}
			else  if(result=='duplicate')
			{
				var tempLAng="";
				if(lang="en")
					tempLAng="Duplicate alias/fieldname";
				else if(lang="fr")
				tempLAng="Dupliquer alias/nom du champ";
				jAlert(tempLAng, Master_Attribute);


			}
			else  if(result=='false')
			{
				jAlert(ERROR_MSG, Master_Attribute);	
				//attrDialog.dialog("destroy");


			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(ERROR_MSG, Master_Attribute);	
		}
	});

}




function savemasterAttr()
{	
	$("#addAttributeformID").validate({
		rules: {
			alias: "required",
			fieldName: "required",
			type: "required",
			size: {

				required: true,
				number: true,
				min : 1
			},
			category: "required",

		},
		messages: {
			alias:FIELD_REQ,
			fieldName:FIELD_REQ,
			type: FIELD_REQ,
			size: FIELD_REQ,
			category: FIELD_REQ,

		}

	});

	if ($("#addAttributeformID").valid())
	{


		savemasterAttrData();

	}
} 



var deleteAttribute= function (id,alias) 
{	


	jConfirm(DELETE_MSG+' : <strong>' + alias + '</strong>',Delete_Confirmation , function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "masterattrib/delete/"+id,
				success: function (result) 
				{ 
					if(result==true)
					{
						jAlert(DELETE_SUCCESS, Master_Attribute);	                  
						displayRefreshedMasterAttr();
					}

					if(result==false)

					{
						var tmpLang="";
						if(lang="en")
							tmpLang="Data Can not be deleted..Used by Project";
						else if(lang="fr")
							tmpLang="Les données ne peuvent pas être supprimées car utilisées pour le projet";
						jAlert(tmpLang, Master_Attribute);	                  
						displayRefreshedMasterAttr();

					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert(DELETE_ERR, Master_Attribute);
				}
			});
		}

	});

}
function  displaySelectedCategory (id) 
{	

	if(id==0)

	{

		displayRefreshedMasterAttr('All');

	}


	if(id!=0)	
	{

		jQuery.ajax({          
			type: 'GET',
			url: "masterattrib/display/"+id,

			success: function (categorydata) { 	


				if(categorydata!='')
				{
					jQuery("#masterAttrsRowData").empty();
					jQuery("#masterAttrTemplate").tmpl(categorydata).appendTo("#masterAttrsRowData");

					var lanChnag=new changeLang();
					lanChnag.first();

					$("#masterAttrTable").tablesorter({ 
						headers: {5: {sorter: false  },  7: {  sorter: false } },	
						debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
						.tablesorterPager({ container: $("#masterAttr_pagerDiv"), positionFixed: false })
						.tablesorterFilter({ filterContainer: $("#masterAttr_txtSearch"),                           
							filterColumns: [0],
							filterCaseSensitive: false,
							filterWaitTime:1000 
						});


				}

				else
				{

					jAlert(NO_DATA,ALERT);
				}
			},

		});
	}

}

function cancelMasterAttr()

{
	displayRefreshedMasterAttr();
	attrDialog.dialog("destroy");
	$('body').find("#attribute-dialog-form").remove();
	$("#attribute-dialog-form").remove();


}






function editmasterAttr(id)
{

	jQuery.ajax({
		url: "masterattrib/"+id,
		async:false,
		success: function (data) {

			jQuery("#primeryky").val(data[0].id);
			jQuery("#alias").val(data[0].alias);
			jQuery("#alias_other").val(data[0].alias_second_language);
			jQuery("#fieldName").val(data[0].fieldname);
			jQuery("#size").val(data[0].size);
			jQuery("#category").val(data[0].attributeCategory.attributecategoryid);  

			/*jQuery("#mandatory").val("false");*/
			console.log(data[0].mandatory);
			jQuery("#mandatory").prop("unchecked", "false");
			if(data[0].mandatory==true){
				jQuery("#mandatory").val("true");
				jQuery("#mandatory").prop("checked", "true");
			}
			jQuery("#type").val(data[0].datatypeIdBean.datatypeId);
		}
	});






	attrDialog = $( "#attribute-dialog-form" ).dialog({
		autoOpen: false,
		height: 350,
		width: 325,
		resizable: true,
		modal: true,

		buttons: [{

			text: "Update",
			"id": "attr_update",
			click: function () {
				updateEditAttribute();



			},
		},
		{
			text: "Cancel",
			"id": "attr_cancel",
			click: function () {
				cancelMasterAttr();
			}
		}],
		close: function() {
			cancelMasterAttr();

		}
	});
	$("#attr_update").html('<span class="ui-button-text">'+ Update +'</span>');
	$("#attr_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
	attrDialog.dialog( "open" );	
	var lanChnag=new changeLang();
	lanChnag.first();


}


function updateEditAttribute()
{

	$("#addAttributeformID").validate({
		rules: {
			alias: "required",
			fieldName: "required",
			type: "required",
			size: {

				required: true,
				number: true,
				min : 1
			},
			category: "required",

		},
		messages: {
			alias: FIELD_REQ,
			fieldName: FIELD_REQ,
			type: FIELD_REQ,
			size: FIELD_REQ,
			category: FIELD_REQ,

		}

	});

	if ($("#addAttributeformID").valid())
	{


		updateAttribute();

	}


}

function updateAttribute()

{



	jQuery.ajax({
		type:"POST",        
		url: "masterattrib/updateattribute" ,
		data: jQuery("#addAttributeformID").serialize(),
		success: function (data) 
		{

			if(data=='true')
			{
				//attrDialog.dialog( "destroy" );
				$("#attribute-dialog-form").remove();
				displayRefreshedMasterAttr();

				jAlert(DATA_SAVED,Master_Attribute);
			}

			else  if(data=='duplicate')
			{
				var tempLAng="";
				if(lang="en")
					tempLAng="Duplicate alias/fieldname";
				else if(lang="fr")
				tempLAng="Dupliquer alias/nom du champ";
				jAlert(tempLAng, Master_Attribute);


			}
			else  if(data=='false')
			{
				jAlert(ERROR_MSG,Master_Attribute);	
				//attrDialog.dialog("destroy");


			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert(ERROR_MSG,Master_Attribute);	
		}
	});





}

function saveNewAttribute(){
	

				attrDialog=$("#attribute-dialog-form").dialog({
				height : 550,
				width : 500,
				resizable : false,
				modal : true,
				buttons: [{

					text: "Update",
					"id": "attr_update",
					click: function () {
						savemasterAttr();



					},
				},
				{
					text: "Cancel",
					"id": "attr_cancel",
					click: function () {
						cancelMasterAttr();
					}
				}],
				close : function() {
					cancelMasterAttr();
				}
				});
				if(lang=='en')
				$("#attr_update").html('<span class="ui-button-text">ADD</span>');
				else if(lang=='fr')
					$("#attr_update").html('<span class="ui-button-text">Ajouter</span>');
				$("#attr_cancel").html('<span class="ui-button-text">'+ Cancel +'</span>');
				attrDialog.dialog( "open" );	

}
