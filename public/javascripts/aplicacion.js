function updateUser(opcion,atributo,id) {
    

	var jqxhr = $.ajax("/userUpdate/"+ opcion + "/" + atributo + "/" + id)
			.done(function(jqXHR) 
			{
				 //programar manejo de errores de update
			})
			.fail(function(jqXHR) {
				 
				//programar manejo de errores de update
			})

}

function fadeError() {
	$("#logError").fadeOut(50);

}

function editFirstName(id) 
{
	
	if ($("#modify").attr("value") == 0) 
	{
		$("#first-name-div")
		.html("<input type='text' id='first-name-input' name='first-name-input' maxlength='15' placeholder='Ingresar Nombre' value='"+$("#first-name-div").html()+"' />");
		
		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-name-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#first-name-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#first-name-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
		   updateUser(1,$("#first-name-input").attr("value"),id);	
		   $("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
		   $("#first-name-div").html($("#first-name-input").attr("value"));
		   $("#fecha-edit-button").slideDown(150);
		   $("#first-lastname-edit-button").slideDown(150);
		   $("#second-lastname-edit-button").slideDown(150);
		   $("#nickname-edit-button").slideDown(150);
		   $("#second-name-edit-button").slideDown(150);
		   $("#first-name-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		   $('#first-name-edit-button').attr("title", "editar");
		   $("#modify").attr("value", "0");
		   
		}

	}
}

function editSecondName(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#second-name-div")
		.html("<input type='text' id='second-name-input' name='second-name-input' maxlength='15' placeholder='Ingresar Nombre' value='"+$("#second-name-div").html()+"' />");
		
		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#second-name-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#second-name-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#second-name-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
		   updateUser(5,$("#second-name-input").attr("value"),id);	 
		   $("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
		   $("#second-name-div").html($("#second-name-input").attr("value"));
		   $("#fecha-edit-button").slideDown(150);
		   $("#first-lastname-edit-button").slideDown(150);
		   $("#second-lastname-edit-button").slideDown(150);
		   $("#nickname-edit-button").slideDown(150);
		   $("#first-name-edit-button").slideDown(150);
		   $("#second-name-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		   $('#second-name-edit-button').attr("title", "editar");
		   $("#modify").attr("value", "0");
		   
		}

	}
}

function editFirstLastname(id) 
{
	
	if ($("#modify").attr("value") == 0) 
	{
		$("#first-lastname-div")
		.html("<input type='text' id='first-lastname-input' name='first-lastname-input' maxlength='15' placeholder='Ingresar Nombre' value='"+$("#first-lastname-div").html()+"' />");
		
		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#first-lastname-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#first-lastname-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
		   updateUser(2,$("#first-lastname-input").attr("value"),id);
		   $("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
		   $("#first-lastname-div").html($("#first-lastname-input").attr("value"));
		   $("#fecha-edit-button").slideDown(150);
		   $("#second-name-edit-button").slideDown(150);
		   $("#second-lastname-edit-button").slideDown(150);
		   $("#nickname-edit-button").slideDown(150);
		   $("#first-name-edit-button").slideDown(150);
		   $("#first-lastname-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		   $('#first-lastname-edit-button').attr("title", "editar");
		   $("#modify").attr("value", "0");
		   
		}

	}
}

function editSecondLastname(id) 
{
	
	if ($("#modify").attr("value") == 0) 
	{
		$("#second-lastname-div")
		.html("<input type='text' id='second-lastname-input' name='second-lastname-input' maxlength='15' placeholder='Ingresar Nombre' value='"+$("#second-lastname-div").html()+"' />");
		
		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#second-lastname-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#second-lastname-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#second-lastname-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
		   updateUser(6,$("#second-lastname-input").attr("value"),id);	 
		   $("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
		   $("#second-lastname-div").html($("#second-lastname-input").attr("value"));
		   $("#fecha-edit-button").slideDown(150);
		   $("#second-name-edit-button").slideDown(150);
		   $("#first-lastname-edit-button").slideDown(150);
		   $("#nickname-edit-button").slideDown(150);
		   $("#first-name-edit-button").slideDown(150);
		   $("#second-lastname-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		   $('#second-lastname-edit-button').attr("title", "editar");
		   $("#modify").attr("value", "0");
		   
		}

	}
}

function editNickname(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#nickname-div")
		.html("<input type='text' id='nickname-input' name='nickname-input' maxlength='35' placeholder='Ingresar Nickname' value='"+$("#nickname-div").html()+"' />");
		
		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#nickname-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#nickname-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#nickname-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
			   updateUser(3,$("#nickname-input").attr("value"),id);
			   
			   $("#twitter-edit-button").slideDown(150);
				$("#facebook-edit-button").slideDown(150);
				$("#google-edit-button").slideDown(150);
	    
		$("#nickname-div").html($("#nickname-input").attr("value"));
		$("#fecha-edit-button").slideDown(150);
		$("#second-name-edit-button").slideDown(150);
		$("#first-lastname-edit-button").slideDown(150);
		$("#second-lastname-edit-button").slideDown(150);
		$("#first-name-edit-button").slideDown(150);
		$("#nickname-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#nickname-edit-button').attr("title", "editar");
		$("#modify").attr("value", "0");
		}

	}
}

function editTwitter(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#twitter-div")
		.html("<input type='text' id='twitter-input' name='twitter-input' maxlength='100' placeholder='Ingresar dirección URL' value='"+$("#twitter-link").html()+"' />");
		
		$("#nickname-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#twitter-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#twitter-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#twitter-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
			   updateUser(7,$("#twitter-input").attr("value"),id);
			   
			   $("#nickname-edit-button").slideDown(150);
				$("#facebook-edit-button").slideDown(150);
				$("#google-edit-button").slideDown(150);
	    
		$("#twitter-div").html("<a id='twitter-link' href='"+$("#twitter-input").attr("value")+"'>"+$("#twitter-input").attr("value")+"</a>");
		$("#fecha-edit-button").slideDown(150);
		$("#second-name-edit-button").slideDown(150);
		$("#first-lastname-edit-button").slideDown(150);
		$("#second-lastname-edit-button").slideDown(150);
		$("#first-name-edit-button").slideDown(150);
		$("#twitter-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#twitter-edit-button').attr("title", "editar");
		$("#modify").attr("value", "0");
		}

	}
}


function editFacebook(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#facebook-div")
		.html("<input type='text' id='facebook-input' name='facebook-input' maxlength='100'  placeholder='Ingresar dirección URL' value='"+$("#facebook-link").html()+"' />");
		
		$("#nickname-edit-button").slideUp(150);
		$("#twitter-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#facebook-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#facebook-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#facebook-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
			   updateUser(8,$("#facebook-input").attr("value"),id);
			   
			   $("#nickname-edit-button").slideDown(150);
				$("#twitter-edit-button").slideDown(150);
				$("#google-edit-button").slideDown(150);
	    
		$("#facebook-div").html("<a id='facebook-link' href='"+$("#facebook-input").attr("value")+"'>"+$("#facebook-input").attr("value")+"</a>");
		$("#fecha-edit-button").slideDown(150);
		$("#second-name-edit-button").slideDown(150);
		$("#first-lastname-edit-button").slideDown(150);
		$("#second-lastname-edit-button").slideDown(150);
		$("#first-name-edit-button").slideDown(150);
		$("#facebook-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#facebook-edit-button').attr("title", "editar");
		$("#modify").attr("value", "0");
		}

	}
}

function editGoogle(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#google-div")
		.html("<input type='text' id='google-input' name='google-input' maxlength='100' placeholder='Ingresar dirección URL' value='"+$("#google-link").html()+"' />");
		
		$("#nickname-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#twitter-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#google-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#google-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#twitter-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
			   updateUser(9,$("#google-input").attr("value"),id);
			   
			   $("#nickname-edit-button").slideDown(150);
				$("#facebook-edit-button").slideDown(150);
				$("#twitter-edit-button").slideDown(150);
	    
		$("#google-div").html("<a id='google-link' href='"+$("#google-input").attr("value")+"'>"+$("#google-input").attr("value")+"</a>");
		$("#fecha-edit-button").slideDown(150);
		$("#second-name-edit-button").slideDown(150);
		$("#first-lastname-edit-button").slideDown(150);
		$("#second-lastname-edit-button").slideDown(150);
		$("#first-name-edit-button").slideDown(150);
		$("#google-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#google-edit-button').attr("title", "editar");
		$("#modify").attr("value", "0");
		}

	}
}


function createAlbum()
{
	var name = document.getElementById("input-name").value;
	var description = document.getElementById("input-description").value;
	var privacy;
	//modificar esta variable con el valor de la ruta
	var imageRoute = "folder.jpg";
	
	if(document.newAlbumForm.optionsRadios[0].checked)
		privacy = 0;
	else
		privacy = 1;
	
    var jqxhr = $.ajax("/insertAlbum/"+name+"/"+description+"/"+privacy+"/"+imageRoute)
			
	
	.done(function(jqXHR) 
			{
				if (jqXHR == "true") 
				{
					
					location.href = "/albumesPag"
				} 
				else 
				{ 
					
					//mensaje de error en campos
				}
			})
			.fail(function(jqXHR) {
				
				//mensaje de error en conexion
			})
	
	    
}

function validateUserPersonalData()
{
	var firstName = $("#first-name").val();
	var secondName = $("#second-name").val();
	var firstLastName = $("#first-lastname").val();
	var secondLastName = $("#second-lastname").val();
	var username = $("#username").val();
	var photo = "assets/images/user_images/userImage.jpg";
	var dia = $("#dia").val();
	var mes = $("#mes").val();
	var anio = $("#anio").val();
	var validUserData = true;
	var privacy;
	
	if(document.newUserForm.optionsRadios[0].checked)
		privacy = 0;
	else
		privacy = 1;
	
	if(firstName == ""){
		
		$("#div-first-name").addClass("has-error");
		validUserData = false;
	}
    if(secondName == ""){
		
		secondName = "null";
	}
    if(firstLastName == ""){
		
		$("#div-first-lastname").addClass("has-error");
		validUserData = false;
	}
    if(secondLastName == ""){
		
		secondLastName = "null";
	}
    if(username == ""){
		
		$("#div-username").addClass("has-error");
		validUserData = false;
	}
    if(dia == 0 || mes==0 || anio==0)
    {
    	validUserData = false;
    }
    
    if(validUserData)
    {
    	location.href = "/registroPaso2/"+firstName+"/"+secondName+"/"+firstLastName+"/"+secondLastName+"/"+username+"/"+dia+"/"+mes+"/"+anio+"/"+privacy+"/"+photo;
    }
    
}

function removeError(object)
{
	
	$("#"+object).removeClass("has-error");
}

function goUserRegisterStep3()  
{
	
	if(country != null && country != "")
	{
		
		
	   if(state == "")
	   {
		   state="null";
	   }
	   if(city == "")
	   {
		   city="null";
	   }
	   location.href = "/addingNewUser/"+country+"/"+state+"/"+city+"/"+latitud+"/"+longitud;
	}
}

function searchForFriends()
{
	var nameUserPattern = $("#input-search").val();

	var jqxhr = $.getJSON("/searchForFriends/"+nameUserPattern)
	.done(function(json) 
	{
		document.getElementById('div-search-results').innerHTML = "";
		$.each(json.users, function() {
			document.getElementById('div-search-results').innerHTML = document.getElementById('div-search-results').innerHTML +
			'<div class="founded-user-next" style="border-bottom:solid 1px black;background-color:#DCC">'+
	           '<a href="perfil/'+this.id+'"><img src="assets/images/user_images/userImage.jpg" /></a>'+
	            '<div id="div-user-fullname" style="margin-left:55px;"><a href="perfil/'+this.id+'"><span>'+this.primerNombre+' '+this.segundoNombre+' '+
	            this.primerApellido+' '+this.segundoApellido+'</span></a></div>'+
	             '<div id="div-username"  style="margin-left:55px;margin-top:12px"><span>'+this.username+'</span></div>'+
	       '</div>';
		})
		 //programar manejo de errores de get json
	})
	.fail(function(jqXHR) {
		document.getElementById('div-search-results').innerHTML = "";
		 
		//programar manejo de errores de get json
	})
}

function modifyUserLocation()  
{
	
	if(country != null && country != "")
	{
			
	   if(state == "")
	   {
		   state="null";
	   }
	   if(city == "")
	   {
		   city="null";
	   }
	   location.href = "/modifyUserLocation/"+country+"/"+state+"/"+city+"/"+latitud+"/"+longitud;
	}
}


function createFriendshipNotification()
{
	
	var jqxhr = $.ajax("/createFriendshipNotification")
	.done(function(jqXHR) 
	{
		 $("#friendship-button").removeClass("btn-primary");
		 $("#friendship-button").addClass("btn-info");
		 $("#friendship-button").html("Esperando Respuesta");
		 $("#friendship-button").removeAttr("onclick");
		 //programar manejo de errores de update
	})
	.fail(function(jqXHR) {
		 
		
		//programar manejo de errores de update
	})
}

function mostrarSolicitudesAmistad(userId)
{
	
	var jqxhr = $.getJSON("/getSolicitudesAmistad/"+userId)
	.done(function(json) 
	{
		
		
		document.getElementById('div-friendship-sol').innerHTML = "";
		
		$.each(json.notification, function() {
			document.getElementById('div-friendship-sol').innerHTML = document.getElementById('div-friendship-sol').innerHTML +
			'<div id="'+this.notificationid+'" class="friendship-next" style="border-bottom:solid 1px black;background-color:#DCC">'+
	           '<a href="perfil/'+this.id+'"><img src="assets/images/user_images/userImage.jpg" /></a>'+
	            '<div id="div-user-fullname" align="center" style="margin-left:55px;"><a href="perfil/'+this.id+'"><span>'+this.primerNombre+' '+this.segundoNombre+' '+
	            this.primerApellido+' '+this.segundoApellido+'</span></a></div>'+
	             '<div id="confirm-button" align="center" style="margin-left:55px;margin-top:12px"><button class="btn btn-primary input-sm" style="border-radius:0px" onclick="confirmFriendship('+this.id+','+this.notificationid+')">Confirmar</button></div>'+
	             '<input type="hidden" id="input-user-'+this.notificationId+'" value="'+this.id+'" />'+
	       '</div>';
		})
		
		 //programar manejo de errores de get json
	})
	.fail(function(jqXHR) {
		
		document.getElementById('div-friendship-sol').innerHTML = "No hay solicitudes de amistad pendientes";
		//programar manejo de errores de get json
	})
}

function confirmFriendship(idAmigo,idNotificacion){

	
	var jqxhr = $.ajax("/confirmFriendship/"+idAmigo+"/"+idNotificacion)
	.done(function(jqXHR) 
	{
		 //done
	})
	.fail(function(jqXHR) {
	      //errores
		
	})
}

function deleteFriendship()
{
	
}

function index()
{
	FB.logout(function(response) {
		location.href="/";
		});
	 
}

function usersSearch(namePart){
	
	var jqxhr = $.getJSON("/userSearch/"+namePart)
	.done(function(json) 
	{
		alert("done");
		 //programar manejo de errores de update
	})
	.fail(function(jqXHR) {
		alert("fail");
		//programar manejo de errores de update
	})

	
}