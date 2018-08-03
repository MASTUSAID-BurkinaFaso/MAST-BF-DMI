
var vertexTableList=[];
var neighbourList=null;
var activeProject="";
var infoList=null;
var boundsList=null;
var bbox="";

function generateAreaMap(usin)
{
	
	jQuery.ajax({ 
		url: "landrecords/neighbour/"+usin,
		async:false,							
		success: function (result) {
			tmpList =result;
			
		}
	});
	
	
	var formImage=getFormImage();
	
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

		
	//var generateAreaMapUrl = "http://"+location.host+"/geoserver/wms?" +"bbox="+bbox+"&FORMAT=image/png&styles=a,b,c,d,generate_map&REQUEST=GetMap&layers=BF_Pilot:HAB_Villages,BF_Pilot:HAB_Roads,BF_Pilot:HYD_Rivers,BF_Pilot:spatial_unit,BF_Pilot:spatial_unit&width=690&height=690&srs=EPSG:4326"+"&cql_filter=INCLUDE;INCLUDE;INCLUDE;INCLUDE;usin="+usin+"";
	
/*	var generateAreaMapUrl = "http://"+location.host+"/geoserver/wms?" +"bbox="+bbox+"&FORMAT=image/png&styles=a,b,c,d,generate_map&REQUEST=GetMap" +
							"&layers=BF_Pilot:HAB_Villages,BF_Pilot:HAB_Roads,BF_Pilot:HYD_Rivers,BF_Pilot:spatial_unit,BF_Pilot:spatial_unit" +
							"&width=690&height=690&srs=EPSG:4326"+"&cql_filter=INCLUDE;INCLUDE;INCLUDE;INCLUDE;usin="+usin+"";*/
	

	
/*	var generateAreaMapUrl ="http://"+location.host+"/geoserver/wms?" +"bbox="+bbox+"&FORMAT=image/png"+
								"&styles=b,c,d,generate_map&REQUEST=GetMap&" +
								"layers=BF_Pilot:HAB_Roads,BF_Pilot:HYD_Rivers,BF_Pilot:spatial_unit,BF_Pilot:spatial_unit" +
								"&width=690&height=690&srs=EPSG:4326" +
								"&cql_filter=INCLUDE;INCLUDE;INCLUDE;usin="+usin; */
	var generateAreaMapUrl = "http://"+location.host+"/geoserver/wms?" +"bbox="+bbox+"&FORMAT=image/png&REQUEST=GetMap&layers=BF_Pilot:HAB_Villages,BF_Pilot:HAB_Roads,BF_Pilot:HYD_Rivers,BF_Pilot:spatial_unit&width=690&height=690&srs=EPSG:4326"+"&cql_filter=INCLUDE;INCLUDE;INCLUDE;usin="+usin+"";
	



	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/viewer/areamap.html',
				dataType: 'html',
				success: function (data1) 
				{
					//jQuery("#printDiv").empty();
					
					jQuery("#printDiv div").empty();
					
					jQuery("#printDiv").append(data1);
					jQuery('#area_map_url').append('<img  src='+generateAreaMapUrl+'>');	

					$('.commune_logo').append("<img width='125' height='100' src='../../"+formImage+"'>");
					$('#region_area').text(tmpList.region);
					$('#commune_area').text(tmpList.commune);
					$('#province_area').text(tmpList.province);
					
					var printWindow=window.open('','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
					printWindow.document.close();
					var html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/style-map.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/AreaMap.js"></script>'+
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
