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

function editNombres(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#nombre-div")
		.html("<input type='text' id='names-input' name='names-input' placeholder='Ingresar Nombres' value='"+$("#nombre-div").html()+"' />");
		
		$("#fecha-edit-button").slideUp(150);
		$("#lastnames-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#email-edit-button").slideUp(150);
		$("#names-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#names-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#names-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
		   updateUser(1,$("#names-input").attr("value"),id);	   
		   $("#nombre-div").html($("#names-input").attr("value"));
		   $("#fecha-edit-button").slideDown(150);
		   $("#lastnames-edit-button").slideDown(150);
		   $("#nickname-edit-button").slideDown(150);
		   $("#email-edit-button").slideDown(150);
		   $("#names-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		   $('#names-edit-button').attr("title", "editar");
		   $("#modify").attr("value", "0");
		   
		}

	}
}

function editApellidos(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#apellidos-div")
		.html("<input type='text' id='lastnames-input' name='lastnames-input' placeholder='Ingresar Apellidos' value='"+$("#apellidos-div").html()+"'/>");
		
		$("#fecha-edit-button").slideUp(150);
		$("#names-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#email-edit-button").slideUp(150);
		$("#lastnames-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#lastnames-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#lastnames-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd

			updateUser(2,$("#lastnames-input").attr("value"),id);	
		$("#apellidos-div").html($("#lastnames-input").attr("value"));
		$("#fecha-edit-button").slideDown(150);
		$("#names-edit-button").slideDown(150);
		$("#nickname-edit-button").slideDown(150);
		$("#email-edit-button").slideDown(150);
		$("#lastnames-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#lastnames-edit-button').attr("title", "editar");
		$("#modify").attr("value", "0");
		}

	}
}

function editNickname(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#nickname-div")
		.html("<input type='text' id='nickname-input' name='nickname-input' placeholder='Ingresar Nickname' value='"+$("#nickname-div").html()+"' />");
		
		$("#fecha-edit-button").slideUp(150);
		$("#lastnames-edit-button").slideUp(150);
		$("#names-edit-button").slideUp(150);
		$("#email-edit-button").slideUp(150);
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
		$("#nickname-div").html($("#nickname-input").attr("value"));
		$("#fecha-edit-button").slideDown(150);
		$("#names-edit-button").slideDown(150);
		$("#lastnames-edit-button").slideDown(150);
		$("#email-edit-button").slideDown(150);
		$("#nickname-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#nickname-edit-button').attr("title", "editar");
		$("#modify").attr("value", "0");
		}

	}
}

function editEmail(id) 
{
	if ($("#modify").attr("value") == 0) 
	{
		$("#email-div")
		.html("<input type='text' id='email-input' name='email-input' placeholder='Ingresar Correo' value='"+$("#email-div").html()+"' />");
		$("#fecha-edit-button").slideUp(150);
		$("#lastnames-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#names-edit-button").slideUp(150);
		$("#email-edit-button").css("background-image","url(assets/images/save_icon.png)");
		$('#email-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) 
	{
		if($("#email-input").attr("value") == "")
		{
			
		}
		else
		{
			//programar manejo de errores de update en bd
			   updateUser(4,$("#email-input").attr("value"),id);
		$("#email-div").html($("#email-input").attr("value"));
		$("#fecha-edit-button").slideDown(150);
		$("#names-edit-button").slideDown(150);
		$("#lastnames-edit-button").slideDown(150);
		$("#nickname-edit-button").slideDown(150);
		$("#email-edit-button").css("background-image","url(assets/images/edit_icon.png)");
		$('#email-edit-button').attr("title", "editar");
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
				alert(jqXHR.statusText);
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
	if(country != null )
	{
	   if(state == "")
	   {
		   state = "null";
	   }
	   if(city == "")
	   {
		   city = "null";
	   }
	   location.href = "/registroPaso3/"+country+"/"+state+"/"+city;
	}
}