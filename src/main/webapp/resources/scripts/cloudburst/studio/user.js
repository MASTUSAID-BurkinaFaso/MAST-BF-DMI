
var selectedItem=null;
var _userdata=null;
var functionalRoles=null;
var username=null;
function User(_selectedItem)

{
	
	selectedItem=_selectedItem;
	if( jQuery("#userFormDiv").length<=0){
		displayRefreshedUser();
	
	}
	else{
		
		displayUser();
	}
}

function displayRefreshedUser(){
	jQuery.ajax({
		url: "user/" + "?" + token,
         success: function (data) {
        	jQuery("#users").empty();
			_userdata=data;
			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {
		    	
				
		    	jQuery("#users").append(template);
		    	jQuery('#userFormDiv').css("visibility", "visible");		    			    	
		    	
		    	var lanChnag=new changeLang();
				lanChnag.first();
		    	
		    	jQuery("#userDetails").hide();	        	
	        	jQuery("#UsersRowData").empty();
	        	jQuery("#userTable").show();	        	
	        	jQuery("#user_accordion").hide();		    	
		    	
	        	if(data!= null && data!="" && data!=undefined)
	        		{
		    	jQuery("#UserTemplate").tmpl(data).appendTo("#UsersRowData");
	        	} 		    	
		    	jQuery("#user_btnSave").hide();
		    	jQuery("#user_btnBack").hide();
		    	jQuery("#user_btnNew").show();
		    	
		    	//$("#user_txtSearch").trigger("keyup");
                $("#userTable").tablesorter({ 
                		headers: {7: {sorter: false  },  8: {  sorter: false } },	
                		debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                       .tablesorterPager({ container: $("#user_pagerDiv"), positionFixed: false })
                       .tablesorterFilter({ filterContainer: $("#user_txtSearch"),                           
                           filterColumns: [0],
                           filterCaseSensitive: false
                       });
		    	
			
			});			
         }
	 });
	
}

function displayUser(){
	
	jQuery("#user_accordion").hide();	        	
	jQuery("#userTable").show();
	
	jQuery("#user_btnSave").hide();
	jQuery("#user_btnBack").hide();
	jQuery("#user_btnNew").show();
	
}



var user_role=null;
var user_roleList=null;
var user_ProjectList=null;
var user_ManagerList=null;

var CreateEditUser = function (_userId) {
  
	    jQuery("#user_btnNew").hide();    
	    jQuery("#user_btnSave").hide();
	    jQuery("#user_btnBack").hide();
	   
	    jQuery("#userTable").hide();
	    jQuery("#userDetails").show();
	    
	    jQuery("#userDetailBody").empty();
	    
	    jQuery("#userRoleList").empty();
	    
//	    jQuery("#user_accordion").show();
//    	jQuery("#user_accordion").accordion({fillSpace: true});
	    
    	/*jQuery.ajax({
            url: "role/?" + token,
            success: function (data) {
            	user_roleList = data;
            	
            	jQuery("#UserTemplateRole").tmpl(data,

                        {
            				//roles: user_roleList
                        }

                     ).appendTo("#userRoleList");
            	
            }
        });*/
    	
    	jQuery.ajax({
            url: "defaultproject/",
            async:false,
            success: function (data) {
            	user_ProjectList = data;      
            	
            }
        });
    	
    	//add for edit for reporTo dropdown data start
    	jQuery.ajax({
            url: "user/?" + token,
            async:false,
            success: function (data) {
            	user_ManagerList = data;      
            	
            }
        });
    	
    	jQuery.ajax({
            url: "role/?" + token,
            async:false,
            success: function (data) {
            	functionalRoles = data;      
            	
            }
        });
    	
	    
    if (_userId) {
    		
            jQuery.ajax({
            	 type: 'GET',
            	 
            	
            	 url:"user/userid/"+_userId,
            	success: function (data) {
            		 user_role=data.roles;	
            	
            		 jQuery("#UserTemplateForm").tmpl(data, {
			    		addDatePicker: function () {
	                        $("#passwordexpires").live('click', function () {
	                        	$(this).datepicker('destroy').datepicker({ dateFormat: 'yy-mm-dd',minDate: 0}).focus(); 
	                        });
	                    }	
                     }).appendTo("#userDetailBody");

            		 var lanChnag=new changeLang();
     				lanChnag.first();
     		    	
                               
                //create DD                 
                jQuery.each(user_ProjectList, function (i, projects) {    	
					jQuery("#user_defaultproject").append(jQuery("<option></option>").attr("value", projects.name).text(projects.name));        
				});
                
              //add for new reportTo start
                jQuery.each(user_ManagerList, function (i, manager) { 
						username=$("#name").val();
						
						if(user_ManagerList[i].active!=false){
						if(username!=manager.username)
						{
        			jQuery("#manager_name").append(jQuery("<option></option>").attr("value", manager.id).text(manager.username)); 
        			}
					}
        		}); 
                

				 jQuery.each(functionalRoles, function (i, role) { 
					 if(lang=="en")
						jQuery("#functionalRole").append(jQuery("<option></option>").attr("value", role.name).text(role.description));
					 else if(lang=="fr")
						 jQuery("#functionalRole").append(jQuery("<option></option>").attr("value", role.name).text(role.description_fr));
					});
                
                //set DD value
                jQuery("#user_defaultproject").val(data.defaultproject);
                jQuery("#user_active").val((data.active).toString());
                jQuery("#manager_name").val(data.manager_name);
                 jQuery("#functionalRole").val(data.roles[0].name);
                                
					jQuery('.accessKey').show();
				
                //set selected  role
                
             /*   jQuery.each(user_role, function (i, role) {

                		jQuery.each(value.Roles, function (name, value) {
                		jQuery('[id=' + role.name + ']').attr('checked', true);
                        
                    });
                });
                */
					
					 
					 
                jQuery('#name').attr('readonly', true);
				
            },
            cache: false
        });
    } else {

        jQuery("#UserTemplateForm").tmpl(null,

                {
        			addDatePicker: function () {
        			$("#passwordexpires").live('click', function () {
                    //$(this).datepicker('destroy').datepicker().focus();
        			$(this).datepicker('destroy').datepicker({ dateFormat: 'yy-mm-dd',minDate: 0}).focus();	
                    
        			});
        			}	
               }
            ).appendTo("#userDetailBody");
        
        var lanChnag=new changeLang();
		lanChnag.first();
    	
        
        jQuery('#name').removeAttr('readonly');        
        jQuery('[id="ROLE_USER"]').attr('checked', true);
        
        jQuery.each(functionalRoles, function (i, role) { 
			 if(lang=="en")
				jQuery("#functionalRole").append(jQuery("<option></option>").attr("value", role.name).text(role.description));
			 else if(lang=="fr")
				 jQuery("#functionalRole").append(jQuery("<option></option>").attr("value", role.name).text(role.description_fr));
			});
		
        jQuery.each(user_ProjectList, function (i, projects) {    	
    	jQuery("#user_defaultproject").append(jQuery("<option></option>").attr("value", projects.name).text(projects.name));        
		});
        
        jQuery.each(user_ManagerList, function (i, manager) { 
if(user_ManagerList[i].active==true){		
			jQuery("#manager_name").append(jQuery("<option></option>").attr("value", manager.id).text(manager.username)); 
			}
		}); 
        
        
		/*
        jQuery.each(user_roleList, function (i, role) {
			if(role.name=='ROLE_USER'){
				jQuery('[id=' + role.name + ']').attr('checked', true);
				return;
            }  
				
		});
		*/
		jQuery('.accessKey').hide();
		
		//jQuery('[id="ROLE_USER"]').attr('checked', true);
		
    }
    
    jQuery("#user_accordion").show();
	jQuery("#user_accordion").accordion({fillSpace: true});
	
    jQuery("#user_btnSave").show();
    jQuery("#user_btnBack").show();
    
    
} 


var saveUserData= function () {
	
	jQuery.ajax({
        type: "POST",              
        url: "user/create" + "?" + token,
        data: jQuery("#userForm").serialize(),
        success: function (result) {        
            
        	if(result=='true')
        		{
        		

               jAlert(DATA_SAVED, ALERT);

               displayRefreshedUser();
        		}
        	else if(result=='duplicate')
        		{

        		jAlert(Name_exists,Alert);

        		 // displayRefreshedUser();
        		}
       	 else  if(result=='false')
			{
				 jAlert(ERROR_MSG, ALERT);	

				 
			}
               
            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            

            jAlert(FAILED);

        }
    });
	
}




function saveUser(){
	var ck_username = /^[A-Za-z0-9_]{3,20}$/;
  //alert($(".roleCheckbox").filter(':checked').length);
    $("#userForm").validate({

        rules: {
            name: "required",
            //password: "required",
            //confirmPassword: {
           //     equalTo: "#password"
           // },

            defaultproject: "required",
            email: {
                required: true,
                email: true
            },
            active: "required",
            passwordexpires: "required",
            lastactivitydate: "required",
            //managerName: "required",
            functionalRole:"required"
			
        },
        messages: {
            name: FIELD_REQ,
           // password: "Please enter Password",
           // confirmPassword: "Confirm Password should be same as Password",
            defaultproject:FIELD_REQ,
            email: FIELD_REQ,
            active: FIELD_REQ,
            passwordexpires: FIELD_REQ,
            lastactivitydate: FIELD_REQ,
            //managerName:"Select Reporting To",
            functionalRole:FIELD_REQ
			
        }

    });

    
    
    if ($("#userForm").valid()) {
        // alert($(".roleCheckbox").filter(':checked').length);
        //saveRecord();
 if (!ck_username.test($('#name').val())) {
jAlert(Alpha_num,Alert);	
 }
    	else if(validatePassword()){
	       
	        	saveUserData();
	       
	        
    }
    }


}

var deleteUser= function (id,name)
{
	
	
		
		jConfirm(DELETE_MSG+' : <strong>' + name + '</strong>', Delete_Confirmation, function (response) {

			if (response) {
	        	jQuery.ajax({          
	        		 type: 'GET',
	        		 url: "user/delete/"+id,
	        		 
	                    success: function (result) { 
	                    	if(result==true){
	                	jAlert(DELETE_SUCCESS, ALERT);

	                  
	                	displayRefreshedUser();
	                    	}
	                    	
	                    	if(result==false)
	                    		{
	                    		
	                    		jAlert(Data_used,Alert);
	      	                  
	    	                	displayRefreshedUser();
	                    		}
	                	
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown) {
	                    
              jAlert(FAILED);

	                }
	            });
	        }

	    });

}

var validatePassword= function (_userName) {
	
	var valid=true;
	if ( $.trim($("#password").val()) == "" )
    {
        jAlert(Enter_Pass,Alert);
        valid = false;
        return valid;
    }
	
	if ( $.trim($("#confirmPassword").val()) == "" )
    {
		jAlert(ReEnter_Pass,Alert);
        valid = false;
        return valid;
    }
	
	if ( $.trim($("#password").val()) != $.trim($("#confirmPassword").val()))
    {
		jAlert(Conf_pass,Alert);
        valid = false;
        return valid;
		
    }
	return valid;
	
}
