function autenticationUser() {

	
	var jqxhr = $.ajax("/userAutentication/" + $("#user").val() + "/" + $("#pass").val())
			
	
	.done(function(jqXHR) 
			{
				if (jqXHR == "true") 
				{
					
					location.href = "/principal"
				} 
				else 
				{ 
					
					$("#logError").fadeIn(150);
				}
			})
			.fail(function(jqXHR) {
				
				$("#logError").fadeIn(150);
			})

}

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
