var form1attributeObject=null;
var form2attributeObject=null;
var poiList=null;
var URL=null;
var URL2=null;
var establishmentDate=null;
var areaMapUrl=null;
var usinId=null;

function generateform1(usin,id){

	var generateForm = new generateForms();
	form1attributeObject = generateForm.Form1(usin)

	if(id==1){
		URL= 'resources/templates/forms/form1.html';
	}
	else if(id==2){
		URL='resources/templates/forms/landrecord_individual.html';
		areaMapUrl=setAreaMap(usin);
		
	}

	jQuery.ajax(
			{
				type: 'GET',
				url: URL,
				dataType: 'html',
				success: function (data1) 
				{
					//jQuery("#printDiv").empty();
					jQuery("#printDiv div").empty();

					jQuery("#printDiv").append(data1);
					
					
					var lanChnag=new changeLang();
					lanChnag.first();
					
					var generateForm = new generateForms();
					form1attributeObject = generateForm.Form1(usin);
					$('#region_1').text(form1attributeObject.region);
					$('#province_1').text(form1attributeObject.province);
					$('.commune_1').text(form1attributeObject.commune);
					$('.village_1').text(form1attributeObject.village);
					$('#villageno_1').text(form1attributeObject.villageno);
					$('.application_no1').text(form1attributeObject.applicationno);
					$('.application_date1').text(form1attributeObject.dateofapplication);
					$('.person_lastname1').text(form1attributeObject.lastname);
					$('.person_firstname1').text(form1attributeObject.firstname);
					$('#birthdate1').text(form1attributeObject.birthdate);
					$('#birthplace1').text(form1attributeObject.birthplace);
					$('#profession_1').text(form1attributeObject.profession);
					$('.person_address1').text(form1attributeObject.address);
					$('#refrenceId_1').text(form1attributeObject.referenceofId);
					$('#father_name1').text(form1attributeObject.fathername);
					$('#mother_name1').text(form1attributeObject.mothername);
					$('#marital_status1').text(form1attributeObject.maritialStatus);
					$('#nop1').text(form1attributeObject.natureofpower);
					$('#issuance_date1').text(form1attributeObject.issuancedate);
					//$('#location1').text(form1attributeObject.location);
					$('.parcel_location1').text(form1attributeObject.location);
					$('.area_1').text(((form1attributeObject.area)*area_constant).toFixed(2));
					$('.neighbour_north1').text(form1attributeObject.neighbour_north);
					$('.neighbour_east1').text(form1attributeObject.neighbour_east);
					$('.neighbour_south1').text(form1attributeObject.neighbour_south);
					$('.neighbour_west1').text(form1attributeObject.neighbour_west);
					$('.noa_1').text(form1attributeObject.natureofapplication);
					$('#establishDate_1').text(form1attributeObject.idcardestablishment_date);
					$('#id_origin1').text(form1attributeObject.idcard_origin);
					$('#mandate_issuanceDate1').text(form1attributeObject.mandate_establishmentDate);
					$('#mandate_location1').text(form1attributeObject.mandate_location);
					$('#application_type1').text(form1attributeObject.typeoftenancy);
					
					if(form1attributeObject.tenancyId==7)
						$('#mandataire_1').text("requérant individuel");
					else if(form1attributeObject.tenancyId==8)
						$('#mandataire_1').text("mandataire");
					
					

					$('#user_CFV').text(form1attributeObject.cfvname);
					
					jQuery('#areamap_url').append('<img  src='+areaMapUrl+'>');
					
					
					var printWindow=window.open('','form1'+usin, 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+ ' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}



function generateform2(usin,id){
	if(id==1){
		URL2= 'resources/templates/forms/form2.html';
	}
	else if(id==2){
		URL2= 'resources/templates/forms/landrecord_collective.html';
		areaMapUrl=setAreaMap(usin);
	}
	jQuery.ajax(
			{
				type: 'GET',
				url: URL2,
				dataType: 'html',
				success: function (data1) 
				{
					jQuery("#printDiv div").empty();
					//$('#masterdiv div').empty();

					jQuery("#printDiv").append(data1);

					var fromTmp=new generateForms();
					var form2attributeObject=fromTmp.Form2(usin);

					$('#region_2').text(form2attributeObject.region);
					$('#province_2').text(form2attributeObject.province);
					$('.commune_2').text(form2attributeObject.commune);
					$('.village_2').text(form2attributeObject.village);
					$('#familyname_2').text(form2attributeObject.family_name);
					$('.mandate_date2').text(form2attributeObject.dateofMandate);
					$('.mandate_commune').text(form2attributeObject.mandate_origin);
					$('#person_lastname2').text(form2attributeObject.lastname);
					$('#person_firstname2').text(form2attributeObject.firstname);
					$('#birthdate_2').text(form2attributeObject.birthdate);
					$('#birthplace_2').text(form2attributeObject.birthplace);
					$('#refrenceId_2').text(form2attributeObject.refrenceID);
					$('#idcard_date2').text(form2attributeObject.idEstablishDate);


					jQuery("#poiTableRowData").empty();

					poiList=[];
					//if((form2attributeObject.poiLst.length!=0) || (form2attributeObject.poiLst.length!=undefined) || (form2attributeObject.poiLst!=null))
					if(form2attributeObject.poiLst!=null &&  form2attributeObject.poiLst!=undefined && form2attributeObject.poiLst.length!=0)
					{
						$('#family_address2').text(form2attributeObject.poiLst[0].address);
						for (var i = 1; i <= form2attributeObject.poiLst.length; i++) {
							var tempArray=[];
							tempArray["sno"]=i;
							tempArray["name"]=form2attributeObject.poiLst[i-1].personName + " " + form2attributeObject.poiLst[i-1].last_name;
							tempArray["refrenceId"]=form2attributeObject.poiLst[i-1].idcard_refrence;
							tempArray["address"] = form2attributeObject.poiLst[i-1].address;
							poiList.push(tempArray);
						}


						jQuery("#poi_template").tmpl(poiList).appendTo("#poiTableRowData");

					}

					$('#user_CFV2').text(form2attributeObject.cfvname);
					jQuery('#areamap_url2').append('<img  src='+areaMapUrl+'>');
					var printWindow=window.open('','form2'+usin, 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');

					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}

function generateform3(usin){
	/*	var use = new existinguse();
	var existinguseList=use.getExistingUseList();*/

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/forms/form3.html',
				dataType: 'html',
				success: function (data1) 
				{
					jQuery("#printDiv div").empty();
					jQuery("#printDiv").append(data1);

					var fromTmp=new generateForms();
					var form3obj=fromTmp.Form3(usin);
					
					var str1 ="pour son propre compte";
					var str2= "pour le compte de la famille";
					var str3 ="pour le compte de la famille";
					
					var firstname_cfv = "";
					var lastname_cfv="";
					
					if(cfv!=null){
						var cfv = form3obj.cfv_president.split(" ");
						 firstname_cfv = cfv[0];
						 lastname_cfv = cfv[1];
					}
					
					var endDate=getCurrentDate();
					var extendedDate=getExtendedDate();
						
					
					$('#region3').text(form3obj.region);
					$('#province3').text(form3obj.province);
					$('.commune3').text(form3obj.commune);
					$('.village3').text(form3obj.village);
					$('#person_firstname3').text(form3obj.firstname);
					$('#person_lastname3').text(form3obj.lastname);
					$('#person_address3').text(form3obj.address);
					$('#person_birthdate3').text(form3obj.dob);
					$('#person_birthplace3').text(form3obj.birthplace);
					if(form3obj.tennancytypeID==7){
						$('#choice_ind3').text(str1);
						$('#choice_col3').text(str2);
						$('#choice_col3').css('textDecoration', 'line-through');
					}
					else if(form3obj.tennancytypeID==8){
						$('#choice_ind3').text(str1);
						$('#choice_ind3').css('textDecoration', 'line-through');
						$('#choice_col3').text(str3 +" "+form3obj.familyname);
					}
					$('#section_no3').text(form3obj.section_no);
					$('#lotno3').text(form3obj.lot_no);
					$('#parcel_no3').text(form3obj.parcel_no);
					$('#area3').text(((form3obj.area)*area_constant).toFixed(2));
					$('#neighbour_north3').text(form3obj.neighbor_north);
					$('#neighbour_south3').text(form3obj.neighbor_south);
					$('#neighbour_east3').text(form3obj.neighbor_east);	
					$('#neighbour_west3').text(form3obj.neighbor_west);
					$('#public_notice_enddate3').text(endDate);
					$('#cfv_lastname3').text(lastname_cfv);
					$('#cfv_firstname3').text(firstname_cfv);
					$('#extended_date').text(extendedDate);
					$('#other_use3').text(form3obj.other_use);
					 $('#flag').text(form3obj.flag);


					if(form3obj.existing_use!=null){
						for (var i=0;i<form3obj.existing_use.length;i++){
							jQuery("#existing_use-div").append("o "+ form3obj.existing_use[i][2] +"<br>");
						}
					}
					var printWindow=window.open('form3','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');

					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+ ' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/cloudburst/viewer/LandRecordTemp.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'<script src="../resources/scripts/jquery-alert/jquery.alerts.js"></script>'+
							' <link rel="stylesheet" href="../resources/scripts/jquery-alert/jquery.alerts.css" type="text/css" />'+
				
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}


function generateform8(usin){

	/*	var use = new existinguse();
	var existinguseList=use.getExistingUseList();*/

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/forms/form8.html',
				dataType: 'html',
				success: function (data1) 
				{
					jQuery("#printDiv div").empty();
					jQuery("#printDiv").append(data1);

					var fromTmp=new generateForms();
					var form8obj=fromTmp.Form8(usin);

					$('#region8').text(form8obj.region);
					$('#province8').text(form8obj.province);
					$('.commune8').text(form8obj.commune);
					$('.village8').text(form8obj.village);
					$('.village_no8').text(form8obj.village_no);
					$('#apfrno8').text(form8obj.apfrno);
					$('.apfrdate8').text(form8obj.apfr_date);
					$('.applicationdate8').text(form8obj.application_date);
					$('.familyname8').text(form8obj.familyname);
					$('#familyaddress8').text(form8obj.address);
					$('.person_lastname8').text(form8obj.last_name);
					$('.person_firstname8').text(form8obj.first_name);
					$('#birthdate8').text(form8obj.dob);
					$('#birthplace8').text(form8obj.birthplace);
					$('#gender8').text(form8obj.sex);
					$('#refrenceId8').text(form8obj.refrence_id_card);
					$('#Idestablishmentdate8').text(form8obj.id_card_date_place);//to be updated
					$('#IdestablishmentPlace8').text(" ");//to be updated
					$('#person_profession8').text(form8obj.profession);
					$('#person_address8').text(form8obj.address);
					$('#village8').text(form8obj.village);
					$('#area8').text(((form8obj.area)*area_constant).toFixed(2));
					$('#neighnour_north8').text(form8obj.neighbour_north);
					$('#neighbour_east8').text(form8obj.neighbour_east);
					$('#neighbour_south8').text(form8obj.neighbour_south);
					$('#neighbour_west8').text(form8obj.neighbour_west);
					//$('#existing_use8').text(" "); //to be updated
					$('#mayor_name8').text(form8obj.mayor_name);
					$('#pvnumber8').text(form8obj.pv_no);
					$('#rightsDate8').text(form8obj.date_recognition_right);
					/*	$('#area_ares8').text(form8obj.area_ares);
					$('#area_centiares8').text(form8obj.area_centiares);*/
					$('#apfr_commune').text(form8obj.commune);
					$('#mandateDate').text(form8obj.mandateDate);
					$('#applicationno8').text(form8obj.application_no);
					
					
					
/*
					var mayorname = form8obj.mayor_name.split(" ");
					var firstname_mayor = mayorname[0];
					var lastname_mayor = mayorname[1];*/

					$('#mayor_firstname').text(form8obj.mayor_name);
					/*$('#mayor_lastname').text(lastname_mayor);*/
					$('#other_use8').text(form8obj.other_use);
					if(form8obj.existing_use!=null){
						for (var i=0;i<form8obj.existing_use.length;i++){
							jQuery("#existing_use8").append("o "+ form8obj.existing_use[i][2] +"<br>");
						}
					}

					jQuery("#APFRTableRowData").empty();

					APFRpoiList=[];
					//if((form2attributeObject.poiLst.length!=0) || (form2attributeObject.poiLst.length!=undefined) || (form2attributeObject.poiLst!=null))
					if(form8obj.poiLst!=null)
					{
						for (var i = 1; i <= form8obj.poiLst.length; i++) {
							var tempArray=[];
							tempArray["sno"]=i;
							tempArray["name"]=form8obj.poiLst[i-1].personName + " " + form8obj.poiLst[i-1].last_name;
							tempArray["gender"]=form8obj.poiLst[i-1].gender.gender_sw;
							tempArray["birthdate"] = form8obj.poiLst[i-1].dob;
							APFRpoiList.push(tempArray);
						}


						jQuery("#APFR_template").tmpl(APFRpoiList).appendTo("#APFRTableRowData");

					}

					var printWindow=window.open('form8','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');

					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}


function generateform7(usin){

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/forms/form7.html',
				dataType: 'html',
				success: function (data7) 
				{
					jQuery("#printDiv div").empty();
					//$('#masterdiv div').empty();

					jQuery("#printDiv").append(data7);

					var fromTmp=new generateForms();
					var form7attributeObject=fromTmp.Form7(usin);

					$('#region_7').text(form7attributeObject.region);
					$('#province_7').text(form7attributeObject.province);
					$('.commune7').text(form7attributeObject.commune);
					$('.village_7').text(form7attributeObject.village);
					$('#villageno_7').text(form7attributeObject.village_no);

					$('.familyname7').text(form7attributeObject.family_name);

					$('.application_date7').text(form7attributeObject.application_date);
					$('.application_no7').text(form7attributeObject.application_no);
					$('#application_year7').text(form7attributeObject.application_year); 
					$('#application_dd7').text(form7attributeObject.application_dd);
					$('#application_month7').text(form7attributeObject.application_month);
					$('.name7').text(form7attributeObject.name);
					$('.profession7').text(form7attributeObject.profession);
					$('.address7').text(form7attributeObject.address);
					$('#cfv_president7').text(form7attributeObject.cfv_president);
					$('#area7').text(((form7attributeObject.area)*area_constant).toFixed(2));
					$('#nr_north7').text(form7attributeObject.neighbour_north);
					$('#nr_east7').text(form7attributeObject.neighbour_east);
					$('#nr_south7').text(form7attributeObject.neighbour_south);
					$('#nr_west7').text(form7attributeObject.neighbour_west);
					$('#public_issue_date7').text(form7attributeObject.public_issuansedate);
					$('#date_recognition7').text(form7attributeObject.date_recognition_rights);
					$('#location').text(form7attributeObject.location);
					/*	$('#area_ares7').text(form7attributeObject.area_ares);
					$('#area_centiares7').text(form7attributeObject.area_centiares);*/

					jQuery("#poiTableRowData7").empty();

					poiList=[];
					if(form7attributeObject.poiLst!=null && form7attributeObject.poiLst.length!=undefined && form7attributeObject.poiLst.length!=0)
					{
						for (var i = 1; i <= form7attributeObject.poiLst.length; i++) {
							var tempArray=[];
							tempArray["sno"]=i;
							tempArray["name"]=form7attributeObject.poiLst[i-1].personName + " " + form7attributeObject.poiLst[i-1].last_name;

							poiList.push(tempArray);
							
						}
						//push neighbour north
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+1;
						tempArray["name"]=form7attributeObject.neighbour_north;
						poiList.push(tempArray);
						//push neighbour south
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+2;
						tempArray["name"]=form7attributeObject.neighbour_south;
						poiList.push(tempArray);
						
						//push neighbour east
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+3;
						tempArray["name"]=form7attributeObject.neighbour_east;
						poiList.push(tempArray);
						//push neighbour west
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+4;
						tempArray["name"]=form7attributeObject.neighbour_west;
						poiList.push(tempArray);
						
						for (var i = 1; i <=4; i++) {
							var tempArray1=[];
							tempArray1["sno"]=form7attributeObject.poiLst.length+4+i;
							tempArray1["name"]="";

							poiList.push(tempArray1);
							
						}
			
					}
					else{
						//push neighbour north
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+1;
						tempArray["name"]=form7attributeObject.neighbour_north;
						poiList.push(tempArray);
						//push neighbour south
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+2;
						tempArray["name"]=form7attributeObject.neighbour_south;
						poiList.push(tempArray);
						
						//push neighbour east
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+3;
						tempArray["name"]=form7attributeObject.neighbour_east;
						poiList.push(tempArray);
						//push neighbour west
						var tempArray=[];
						tempArray["sno"]=form7attributeObject.poiLst.length+4;
						tempArray["name"]=form7attributeObject.neighbour_west;
						poiList.push(tempArray);
						
						for (var i = 1; i <=4; i++) {
							var tempArray1=[];
							tempArray1["sno"]=form7attributeObject.poiLst.length+4+i;
							tempArray1["name"]="";

							poiList.push(tempArray1);
					}
					}
					jQuery("#poi_template7").tmpl(poiList).appendTo("#poiTableRowData7");



					var printWindow=window.open('','form7'+usin, 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');

					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}



function generateform5(usin){

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/forms/form5.html',
				dataType: 'html',
				success: function (data5) 
				{
					jQuery("#printDiv div").empty();
					//$('#masterdiv div').empty();

					jQuery("#printDiv").append(data5);

					var fromTmp=new generateForms();
					var form5attributeObject=fromTmp.Form5(usin);

					$('#region_5').text(form5attributeObject.region);
					$('#province_5').text(form5attributeObject.province);
					$('.commune_5').text(form5attributeObject.commune);
					$('.village_5').text(form5attributeObject.village);
					$('.villageno_5').text(form5attributeObject.village_no);
					$('.application_date5').text(form5attributeObject.application_date);
					//$('.application_no5').text(form5attributeObject.application_no);
					$('.apfrno5').text(form5attributeObject.apfrno);
					
					$('#lastname_5').text(form5attributeObject.last_name);
					$('#firstname_5').text(form5attributeObject.first_name);
					$('#gender_5').text(form5attributeObject.sex);
					$('#idcard5').text(form5attributeObject.refrence_id_card);
					$('#birthdate5').text(form5attributeObject.dob);
					$('#birthplace5').text(form5attributeObject.birthplace);
					$('#profession5').text(form5attributeObject.profession);
					$('#address_5').text(form5attributeObject.address);
					$('#location5').text(form5attributeObject.location);
					$('#section5').text(form5attributeObject.section);
					$('#lot5').text(form5attributeObject.lot);
					$('#parcel_no5').text(form5attributeObject.parcel_no);
					$('#area5').text(((form5attributeObject.area)*area_constant).toFixed(2));
					$('#nr-north5').text(form5attributeObject.neighbour_north);
					$('#nr-east5').text(form5attributeObject.neighbour_east);
					$('#nr-south5').text(form5attributeObject.neighbour_south);
					$('#nr-west5').text(form5attributeObject.neighbour_west);
					$('.mayor5').text(form5attributeObject.mayor_name);
					$('#pv_no5').text(form5attributeObject.pv_no);
					$('#recognition_date5').text(form5attributeObject.date_recognition_right);
					$('#apfrdate5').text(form5attributeObject.apfr_date);
					//$('#existing_use5').text(form5attributeObject.existing_use);
					$('#other_use5').text(form5attributeObject.other_use);

					if(form5attributeObject.existing_use!=null){
						for (var i=0;i<form5attributeObject.existing_use.length;i++){
							jQuery("#existing_use_div5").append("o "+ form5attributeObject.existing_use[i][2] +"<br>");
						}
					}

					var printWindow=window.open('','form5'+usin, 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');

					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}
function generatePaymentLetter(usin){

	/*jQuery.ajax({ 
		url: "landrecords/checkdate/"+usin,
		async:false,							
		success: function (data) {	
			establishmentDate = data;

		}


	});*/

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/forms/paymentLetter.html',
				dataType: 'html',
				success: function (data) 
				{
					jQuery("#printDiv div").empty();
					jQuery("#printDiv").append(data);

					var generateForm = new generateForms();
					attrObject = generateForm.paymentDetails(usin);
					$('#_region').text(attrObject.region);
					$('#_province').text(attrObject.province);
					$('.commune').text(attrObject.commune);
					$('.application_no').text(attrObject.application_no);
					$('.application_date').text(attrObject.applicationDate);
					$('.village').text(attrObject.village);
					$('#first_name').text(attrObject.firstname);
					$('#last_name').text(attrObject.lastname);
					$('#letter_date').text(attrObject.printDate);
					$('#area_print').text(((attrObject.area)*area_constant).toFixed(2));
					

					/*if(establishmentDate!=""){
						$('#letter_date').text(establishmentDate);
					}
					else{
						var date = new Date();

						var month = date.getMonth()+1;
						var day = date.getDate();

						var output = date.getFullYear() + '-' +
						    (month<10 ? '0' : '') + month + '-' +
						    (day<10 ? '0' : '') + day;
						$('#letter_date').text(output);
						//update letter establishment date
						jQuery.ajax({ 
							url: "landrecords/savedate/"+usin,
							async:false,							
							success: function (data) {	

							}
						});
					}*/

					var printWindow=window.open('','paymentLetter'+usin, 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');

					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/form.css" type="text/css" />'+' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/Form.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'<script src="../resources/scripts/jquery-alert/jquery.alerts.css"></script>'+
							
							
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.document.close();
					printWindow.focus();


				}
			});
}

function printForm(){

	document.getElementsByClassName('print_form')[0].style.visibility = "hidden";
	location.reload();
	window.print();
}
function printNotice(){
	
	var noticeDate=null;
	var displayDate=null;
	var generateForm = new generateForms();
	usinId = $('#usin_primerykey').val();
	noticeDate = generateForm.checkDate(usinId);
	displayDate =noticeDate;
	
	var flag= $('#flag').text();

/*	jAlert('Public Notice start date is :'+displayDate, 'Alert', function () { 
		
		printForm();
	});*/
	

	//printForm();
	
jConfirm('Avis de publicité courant de : <strong>' + displayDate +  '.</strong> Voulez-vous vraiment imprimer ?','Confirmer' , function (response) {

		if (response ) {
			if(flag=="true"){
				//displayDate=getToday();
				jQuery.ajax({          
					type: 'GET',
					url: "landrecords/updatenoticedate/"+usinId,
					success: function (result) 
					{ 
						printForm();
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {

						jAlert("Error in printing");
					}
				});
				
			}
			
		}
		
	});

}


function setAreaMap(usin){
	
	
	var cql ="usin='"+usin+"'";
	var tmp;
	var wmsurl="http://"+location.host+"/geoserver/wfs?";
	var geomInfo=wmsurl+"request=GetFeature&typeName=BF_Pilot:spatial_unit&CQL_FILTER="+cql+"&version=1.0.0";
	
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
	        var features = gmlReader.read(response.responseText);
	        boundsList= features[0].geometry.getBounds();
	        // do what you want with the features returned...
	    },
	    failure: function (response) {
	        alert("Something went wrong in the request");
	    }
	});
	
	
	var newBounds=createSquareBounds(boundsList);
	newBounds.left=newBounds.left-0.02;
	newBounds.bottom=newBounds.bottom-0.02;
	newBounds.right=newBounds.right+0.02;
	newBounds.top=newBounds.top+0.02;
	bbox = newBounds.left + ',' + newBounds.bottom
	+ ',' + newBounds.right + ','
	+ newBounds.top;

		
	var generateAreaMapUrl = "http://"+location.host+"/geoserver/wms?" +"bbox="+bbox+"&FORMAT=image/png&styles=a,b,c,d,generate_map&REQUEST=GetMap&layers=BF_Pilot:HAB_Villages,BF_Pilot:HAB_Roads,BF_Pilot:HYD_Rivers,BF_Pilot:spatial_unit,BF_Pilot:spatial_unit&width=690&height=690&srs=EPSG:4326"+"&cql_filter=INCLUDE;INCLUDE;INCLUDE;INCLUDE;usin="+usin+"";
	return generateAreaMapUrl;
}

function getCurrentDate(){
	 var myDate = new Date(new Date().getTime()+(45*24*60*60*1000));
	var dd = myDate.getDate();
	var mm = myDate.getMonth()+1; //January is 0!
	var yyyy = myDate.getFullYear();
	if(dd<10){
	    dd='0'+dd
	} 
	if(mm<10){
	    mm='0'+mm
	} 
	var myDate = dd+'/'+mm+'/'+yyyy;
	return myDate;
}

function getExtendedDate(){
    var myDate = new Date(new Date().getTime()+(47*24*60*60*1000));
    var dd = myDate.getDate();
	var mm = myDate.getMonth()+1; //January is 0!
	var yyyy = myDate.getFullYear();
	if(dd<10){
	    dd='0'+dd
	} 
	if(mm<10){
	    mm='0'+mm
	} 
	var myDate = dd+'/'+mm+'/'+yyyy;
	return myDate;
}
function getToday(){
	   var myDate = new Date();
	    var dd = myDate.getDate();
		var mm = myDate.getMonth()+1; //January is 0!
		var yyyy = myDate.getFullYear();
		if(dd<10){
		    dd='0'+dd
		} 
		if(mm<10){
		    mm='0'+mm
		} 
		var myDate = dd+'/'+mm+'/'+yyyy;
		return myDate;
	
}