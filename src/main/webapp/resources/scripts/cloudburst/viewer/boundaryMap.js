
var vertexTableList=[];
var tmpList=null;

function generateBoundaryMap(usin)
{
	jQuery.ajax({ 
		url: "landrecords/neighbour/"+usin,
		async:false,							
		success: function (result) {
			tmpList =result;
			
		}
	});

	//mapImage(usin);
	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/viewer/boundarymap.html',
				dataType: 'html',
				success: function (data1) 
				{
					//jQuery("#printDiv").empty();
					jQuery("#printDiv div").empty();
					
					jQuery("#printDiv").append(data1);
					mapImage(usin);
					//add verteces
					jQuery('#vertexTableBody_map').empty();
					vertexTableList=[];
					for (var int = 1; int <= vertexlist.length; int++) {
						var tempList=[];
						tempList["index"]=int;
						tempList["x"]=(vertexlist[int-1].x).toFixed(2);
						tempList["y"]=(vertexlist[int-1].y).toFixed(2);
						vertexTableList.push(tempList);
					}
					
					jQuery("#vertexTable_map").tmpl(vertexTableList).appendTo("#vertexTableBody_map");
						
					//add neighbour details
					
					jQuery('#neighbourTableBody').empty();
					var neighbourList=tmpList.neighbourLst;
					var neighbourFinalList=[];
					for (var int = 0; int < neighbourList.length; int++) {
						var tempNeighbourList=[];
						if(int==0){
							tempNeighbourList["location"]="North";
							tempNeighbourList["name"]=neighbourList[int];
						}
						if(int==1){
							tempNeighbourList["location"]="South";
							tempNeighbourList["name"]=neighbourList[int];
						}
						if(int==2){
							tempNeighbourList["location"]="East";
							tempNeighbourList["name"]=neighbourList[int];
						}
						if(int==3){
							tempNeighbourList["location"]="West";
							tempNeighbourList["name"]=neighbourList[int];
						}
				
						
						neighbourFinalList.push(tempNeighbourList);
					}
					
					jQuery("#neighbourTable").tmpl(neighbourFinalList).appendTo("#neighbourTableBody");
				
					$('#boudary_villagename').text(tmpList.villagename);
					$('#boudary_application_no').text(tmpList.application_no);
					$('#boudary_name').text(tmpList.name);
					$('#boudary_applicationdate').text(tmpList.applicationdate);
					$('#sfr_name').text(tmpList.sfr_name);
					
					


					var printWindow=window.open('','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
					printWindow.document.close();
					var html =null;
					html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/style-map.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/boundaryMap.js"></script>'+
							'<script src="../resources/scripts/cloudburst/viewer/mapImage.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+usin+'></body></html>');

					printWindow.focus();


				}
			});
}

function printForm(){

	document.getElementsByClassName('print_form')[0].style.visibility = "hidden";
	location.reload();
	window.print();
}


