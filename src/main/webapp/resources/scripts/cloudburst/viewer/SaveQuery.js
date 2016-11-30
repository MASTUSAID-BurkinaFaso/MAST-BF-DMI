
var qryProject;
var qryLayer;
var qryName;
var criteria;
var description;
var newQry = false;
var Query_Exists="";

function SaveQuery(project, layer, criterion, name, desc, newQuery) {
    qryProject = project;
    qryLayer = layer;
    qryName = name;
    criteria = criterion;
    description = desc;
    newQry = newQuery;

    if (name != null && desc == null) {
        loadDescription();
    }
//    $('input[name=WhereExpression]').val(criteria);
//    $('input[name=Layer]').val(qryLayer);
//    $('input[name=Project]').val(qryProject);
}

var queryNameExists = function(queryName){
	$.ajax({
        url: STUDIO_URL + 'savedquery/' + queryName + "?" + token,
        success: function (data) {
        	if(data.name != undefined){
        		if(lang=="en")
	            jAlert("Query name already exists", "Search");
        		else if (lang=="fr")
        			jAlert("Nom de la requête existe déjà", "Alert");
        	}else{
        		if(lang=="en")
    	            jAlert("Query name already exists", "Search");
            		else if (lang=="fr")
            			jAlert("Nom de la requête existe déjà", "Alert");
        	}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert(FAILED);
        }
    });
}

SaveQuery.prototype.toggleSaveQry = function(){
	 if($("#saveQuery").css("display") == 'none'){
			$("#saveQuery").show();
		}else{
			$("#saveQuery").hide();
		}
}

function loadDescription() {
    $.ajax({
        type: "GET",
        url: STUDIO_URL + 'savedquery/' + "description/" + qryName + "?" + token,
        success: function (data) {
            $("#querydesc").val(data);

            $("#qryName").val(qryName);
            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            
        }
    });
}

SaveQuery.prototype.Save = function() {

    if ($('#qryName').val() == "") {
        jAlert("Please enter query name");
    } else {
        //store value in respective hidden field
        $('input[name=Name]').val($('#qryName').val());
    }
    if ($('#querydesc').val() == "") {
        jAlert("Please enter description for query");
    } else {
        $('input[name=Description]').val($('#querydesc').val());
    }

    $('input[name=WhereExpression]').val(criteria);
    $('input[name=Layer]').val(qryLayer);
    $('input[name=Project]').val(qryProject);

    //alert($('#Name').val() + "---" + $('#Description').val() + "---" + $('#WhereExpression').val() + "---" + $('#Layer').val() + "---" + $('#Project').val());
    var name = $('#qryName').val();
    var urlCreate = STUDIO_URL + 'savedquery' + "/create?";
    var urlEdit = STUDIO_URL + 'savedquery' + "/edit?";
    $.ajax({
        type: "POST",
        url: (newQry) ? urlCreate : urlEdit,
		//url: urlCreate,
        data: $("#frmSaveQry").serialize(),
        cache: false,
        async: false,
        success: function (data) {
        	if(data.name != undefined){
        		jAlert("Data successfully saved.", "Save Query");
        	}else{
        		if(lang=="en")
        		jAlert("Failed to save query. Please check if query name is unique", "Save Query");
        		else if (lang=="fr")
        			jAlert("Impossible d'enregistrer la requête. S'il vous plaît vérifier si le nom de requête est unique", "Alerte");
        	}
           hideDiv();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            jAlert(FAILED)
        }
    });
}

function hideDiv() {
	 if($("#saveQuery").css("display") != 'none'){
		$("#saveQuery").hide();
	 }
}