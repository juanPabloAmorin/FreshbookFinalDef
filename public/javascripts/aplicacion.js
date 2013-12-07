function updateUser(opcion, atributo, id) {


	var jqxhr = $.post("/userUpdate",{ opcion: opcion,atributo: atributo ,id:id})
			.done(function(jqXHR) {
				// programar manejo de errores de update
			})

}

function updateAlbum(opcion,atributo,id)
{
	var jqxhr = $.post("/albumUpdate",{ opcion: opcion,atributo: atributo ,id:id})
	.done(function(jqXHR) {
		
		// programar manejo de errores de update
	})
}

function fadeError() {
	$("#logError").fadeOut(50);
	$("#dateError").fadeOut(50);

}

function editFirstName(id) {

	if ($("#modify").attr("value") == 0) {
		$("#first-name-div")
				.html(
						"<input type='text' id='first-name-input' name='first-name-input' maxlength='15' placeholder='Ingresar Nombre' value='"
								+ $("#first-name-div").html() + "' />");

		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-name-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#first-name-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#first-name-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(1, $("#first-name-input").val(), id);
			$("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
			$("#first-name-div").html($("#first-name-input").attr("value"));
			$("#fecha-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#nickname-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#first-name-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#first-name-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");

		}

	}
}

function editSecondName(id) {
	if ($("#modify").attr("value") == 0) {
		$("#second-name-div")
				.html(
						"<input type='text' id='second-name-input' name='second-name-input' maxlength='15' placeholder='Ingresar Nombre' value='"
								+ $("#second-name-div").html() + "' />");

		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#second-name-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#second-name-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#second-name-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(5, $("#second-name-input").attr("value"), id);
			$("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
			$("#second-name-div").html($("#second-name-input").attr("value"));
			$("#fecha-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#nickname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#second-name-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#second-name-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");

		}

	}
}

function editFirstLastname(id) {

	if ($("#modify").attr("value") == 0) {
		$("#first-lastname-div")
				.html(
						"<input type='text' id='first-lastname-input' name='first-lastname-input' maxlength='15' placeholder='Ingresar Nombre' value='"
								+ $("#first-lastname-div").html() + "' />");

		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#first-lastname-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#first-lastname-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(2, $("#first-lastname-input").attr("value"), id);
			$("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
			$("#first-lastname-div").html(
					$("#first-lastname-input").attr("value"));
			$("#fecha-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#nickname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#first-lastname-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#first-lastname-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");

		}

	}
}

function editSecondLastname(id) {

	if ($("#modify").attr("value") == 0) {
		$("#second-lastname-div")
				.html(
						"<input type='text' id='second-lastname-input' name='second-lastname-input' maxlength='15' placeholder='Ingresar Nombre' value='"
								+ $("#second-lastname-div").html() + "' />");

		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#nickname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#second-lastname-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#second-lastname-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	}

	else if ($("#modify").attr("value") == 1) {
		if ($("#second-lastname-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(6, $("#second-lastname-input").attr("value"), id);
			$("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);
			$("#second-lastname-div").html(
					$("#second-lastname-input").attr("value"));
			$("#fecha-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#nickname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#second-lastname-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#second-lastname-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");

		}

	}
}

function editNickname(id) {
	if ($("#modify").attr("value") == 0) {
		$("#nickname-div")
				.html(
						"<input type='text' id='nickname-input' name='nickname-input' maxlength='35' placeholder='Ingresar Nickname' value='"
								+ $("#nickname-div").html() + "' />");

		$("#twitter-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#nickname-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#nickname-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#nickname-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(3, $("#nickname-input").attr("value"), id);

			$("#twitter-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);

			$("#nickname-div").html($("#nickname-input").attr("value"));
			$("#fecha-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#nickname-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#nickname-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");
		}

	}
}

function editTwitter(id) {
	if ($("#modify").attr("value") == 0) {
		$("#twitter-div")
				.html(
						"<input type='text' id='twitter-input' name='twitter-input' maxlength='100' placeholder='Ingresar direccion URL' value='"
								+ $("#twitter-link").html() + "' />");

		$("#nickname-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#twitter-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#twitter-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#twitter-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(7, $("#twitter-input").attr("value"), id);

			$("#nickname-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);

			$("#twitter-div").html(
					"<a id='twitter-link' href='"
							+ $("#twitter-input").attr("value") + "'>"
							+ $("#twitter-input").attr("value") + "</a>");
			$("#fecha-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#twitter-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#twitter-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");
		}

	}
}

function editFacebook(id) {
	if ($("#modify").attr("value") == 0) {
		$("#facebook-div")
				.html(
						"<input type='text' id='facebook-input' name='facebook-input' maxlength='100'  placeholder='Ingresar direccion URL' value='"
								+ $("#facebook-link").html() + "' />");

		$("#nickname-edit-button").slideUp(150);
		$("#twitter-edit-button").slideUp(150);
		$("#google-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#facebook-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#facebook-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#facebook-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(8, $("#facebook-input").attr("value"), id);

			$("#nickname-edit-button").slideDown(150);
			$("#twitter-edit-button").slideDown(150);
			$("#google-edit-button").slideDown(150);

			$("#facebook-div").html(
					"<a id='facebook-link' href='"
							+ $("#facebook-input").attr("value") + "'>"
							+ $("#facebook-input").attr("value") + "</a>");
			$("#fecha-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#facebook-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#facebook-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");
		}

	}
}

function editGoogle(id) {
	if ($("#modify").attr("value") == 0) {
		$("#google-div")
				.html(
						"<input type='text' id='google-input' name='google-input' maxlength='100' placeholder='Ingresar direccion URL' value='"
								+ $("#google-link").html() + "' />");

		$("#nickname-edit-button").slideUp(150);
		$("#facebook-edit-button").slideUp(150);
		$("#twitter-edit-button").slideUp(150);
		$("#fecha-edit-button").slideUp(150);
		$("#second-name-edit-button").slideUp(150);
		$("#first-lastname-edit-button").slideUp(150);
		$("#second-lastname-edit-button").slideUp(150);
		$("#first-name-edit-button").slideUp(150);
		$("#google-edit-button").css("background-image",
				"url(assets/images/save_icon.png)");
		$('#google-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} else if ($("#modify").attr("value") == 1) {
		if ($("#twitter-input").attr("value") == "") {

		} else {
			// programar manejo de errores de update en bd
			updateUser(9, $("#google-input").attr("value"), id);

			$("#nickname-edit-button").slideDown(150);
			$("#facebook-edit-button").slideDown(150);
			$("#twitter-edit-button").slideDown(150);

			$("#google-div").html(
					"<a id='google-link' href='"
							+ $("#google-input").attr("value") + "'>"
							+ $("#google-input").attr("value") + "</a>");
			$("#fecha-edit-button").slideDown(150);
			$("#second-name-edit-button").slideDown(150);
			$("#first-lastname-edit-button").slideDown(150);
			$("#second-lastname-edit-button").slideDown(150);
			$("#first-name-edit-button").slideDown(150);
			$("#google-edit-button").css("background-image",
					"url(assets/images/edit_icon.png)");
			$('#google-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");
		}

	}
}

function createAlbum() {
	var name = document.getElementById("input-name").value;
	var description = document.getElementById("input-description").value;
	var privacy;
	// modificar esta variable con el valor de la ruta
	var imageRoute = "folder.jpg";
	
	var arrayPosition = 0;

	if (document.newAlbumForm.optionsRadios[0].checked)
		privacy = 0;
	else
		privacy = 1;
	
	var photos = [];
	var tumbs = [];
	
	$("input[name=checkB]:checked").each(function() {
	    photos.push(this.id);
	});
	
	$("input[name=checkB]:checked").each(function() {
	    tumbs.push(this.title);
	});
	
	var jqxhr = $.post("/insertAlbum",{ name: name, description: description, privacy: privacy,
		                                imageRoute: imageRoute,'photos[]':photos,'tumbs[]':tumbs})

	.done(function(jqXHR) {
	  
		
		if (jqXHR == "true") {
            
			location.href = "/albumesPag"
		} else {
           
			// mensaje de error en campos
		}
	}).fail(function(jqXHR) {
		
		// mensaje de error en conexion
	})
	
}

function validateUserPersonalData() {
	var firstName = $("#first-name").val();
	var secondName = $("#second-name").val();
	var firstLastName = $("#first-lastname").val();
	var secondLastName = $("#second-lastname").val();
	var username = $("#username").val();
	var photo = "assets/images/user_images/userImage.jpg";
	var dia = $("#dia").val();
	var mes = $("#mes").val();
	var anio = $("#anio").val();
	var id = $("#facebookid-input").val()
	var validUserData = true;
	var privacy;

	if (document.newUserForm.optionsRadios[0].checked)
		privacy = 0;
	else
		privacy = 1;

	if (firstName == "") {

		$("#div-first-name").addClass("has-error");
		$( "#first-name" ).attr( "placeholder", "Indique un nombre" )
		validUserData = false;
	}
	if (secondName == "") {

		secondName = "null";
	}
	if (firstLastName == "") {

		$("#div-first-lastname").addClass("has-error");
		$( "#first-lastname" ).attr( "placeholder", "Indique un apellido" )
		validUserData = false;
	}
	if (secondLastName == "") {

		secondLastName = "null";
	}
	if (username == "") {

		$("#div-username").addClass("has-error");
		$( "#username" ).attr( "placeholder", "Debe indicar un nombre de usuario" )
		validUserData = false;
	}
	if (dia == 0 || mes == 0 || anio == 0) {
		$("#dateError").fadeIn(200);
		validUserData = false;
	}

	if (validUserData) {
		location.href = "/registroPaso2/" + firstName + "/" + secondName + "/"
				+ firstLastName + "/" + secondLastName + "/" + username + "/"
				+ dia + "/" + mes + "/" + anio + "/" + privacy + "/" + photo+"/"+id;
	}

}

function removeError(object,object2,valor) {

	$("#" + object).removeClass("has-error");
	$("#" + object2 ).attr( "placeholder", valor );
	
}

function goUserRegisterStep3() {

	if (country != null && country != "") {

		if (state == "") {
			state = "null";
		}
		if (city == "") {
			city = "null";
		}
		location.href = "/addingNewUser/" + country + "/" + state + "/" + city
				+ "/" + latitud + "/" + longitud;
	}
}

function searchForFriends() {
	var nameUserPattern = $("#input-search").val();

	var jqxhr = $
			.getJSON("/searchForFriends/" + nameUserPattern)
			.done(
					function(json) {
						document.getElementById('div-search-results').innerHTML = "";
						$
								.each(
										json.users,
										function() {
											document
													.getElementById('div-search-results').innerHTML = document
													.getElementById('div-search-results').innerHTML
													+ '<div class="founded-user-next" style="border-bottom:solid 1px black;background-color:#DCC">'
													+ '<a href="perfil/'
													+ this.id
													+ '"><img src="http://graph.facebook.com/'+this.facebookid+'/picture?type=large" /></a>'
													+ '<div id="div-user-fullname" style="margin-left:55px;"><a href="perfil/'
													+ this.id
													+ '"><span>'
													+ this.primerNombre
													+ ' '
													+ this.segundoNombre
													+ ' '
													+ this.primerApellido
													+ ' '
													+ this.segundoApellido
													+ '</span></a></div>'
													+ '<div id="div-username"  style="margin-left:55px;margin-top:12px"><span>'
													+ this.username
													+ '</span></div>'
													+ '</div>';
										})
						// programar manejo de errores de get json
					}).fail(function(jqXHR) {
				document.getElementById('div-search-results').innerHTML = "";

				// programar manejo de errores de get json
			})
}

function searchForFriends2() {
	var nameUserPattern = $("#input-search-input").val();

	var jqxhr = $
			.getJSON("/searchForFriends/" + nameUserPattern)
			.done(
					function(json) {
						document.getElementById('div-search-results-div').innerHTML = "";
						$
								.each(
										json.users,
										function() {
											document
													.getElementById('div-search-results-div').innerHTML = document
													.getElementById('div-search-results-div').innerHTML
													+ '<div class="founded-user-next" style="border-bottom:solid 1px black;background-color:#DCC">'
													+ '<a href="perfil/'
													+ this.id
													+ '"><img src="http://graph.facebook.com/'+this.facebookid+'/picture?type=large" /></a>'
													+ '<div id="div-user-fullname" style="margin-left:75px;"><a href="perfil/'
													+ this.id
													+ '"><span>'
													+ this.primerNombre
													+ ' '
													+ this.segundoNombre
													+ ' '
													+ this.primerApellido
													+ ' '
													+ this.segundoApellido
													+ '</span></a></div>'
													+ '<div id="div-username"  style="margin-left:75px;margin-top:12px"><span>'
													+ this.username
													+ '</span></div>'
													+ '</div>';
										})
						// programar manejo de errores de get json
					})
			.fail(
					function(jqXHR) {
						document.getElementById('div-search-results-div').innerHTML = "";

						// programar manejo de errores de get json
					})
}

function modifyUserLocation() {

	if (country != null && country != "") {

		if (state == "") {
			state = "null";
		}
		if (city == "") {
			city = "null";
		}
		location.href = "/modifyUserLocation/" + country + "/" + state + "/"
				+ city + "/" + latitud + "/" + longitud;
	}
}

function createFriendshipNotification() {

	var jqxhr = $.ajax("/createFriendshipNotification").done(function(jqXHR) {
		$("#friendship-button").removeClass("btn-primary");
		$("#friendship-button").addClass("btn-info");
		$("#friendship-button").html("Esperando Respuesta");
		$("#friendship-button").removeAttr("onclick");
		// programar manejo de errores de update
	}).fail(function(jqXHR) {

		// programar manejo de errores de update
	})
}

function mostrarSolicitudesAmistad(userId) {

	var jqxhr = $
			.getJSON("/getSolicitudesAmistad/" + userId)
			.done(
					function(json) {

						document.getElementById('div-friendship-sol').innerHTML = "";

						$
								.each(
										json.notification,
										function() {
											document
													.getElementById('div-friendship-sol').innerHTML = document
													.getElementById('div-friendship-sol').innerHTML
													+ '<div id="'
													+ this.notificationid
													+ '" class="friendship-next" style="border-bottom:solid 1px black;background-color:#DCC">'
													+ '<a href="perfil/'
													+ this.id
													+ '"><img src="http://graph.facebook.com/'+this.facebookid+'/picture?type=large" /></a>'
													+ '<div id="div-user-fullname" align="center" style="margin-left:55px;"><a href="perfil/'
													+ this.id
													+ '"><span>'
													+ this.primerNombre
													+ ' '
													+ this.segundoNombre
													+ ' '
													+ this.primerApellido
													+ ' '
													+ this.segundoApellido
													+ '</span></a></div>'
													+ '<div id="confirm-button" align="center" style="margin-left:55px;margin-top:12px"><button class="btn btn-primary input-sm" style="border-radius:0px" onclick="confirmFriendship('
													+ this.id
													+ ','
													+ this.notificationid
													+ ')">Confirmar</button></div>'
													+ '<input type="hidden" id="input-user-'
													+ this.notificationId
													+ '" value="'
													+ this.id
													+ '" />' + '</div>';
										})

						// programar manejo de errores de get json
					})
			.fail(
					function(jqXHR) {

						document.getElementById('div-friendship-sol').innerHTML = "No hay solicitudes de amistad pendientes";
						// programar manejo de errores de get json
					})
}

function confirmFriendship(idAmigo, idNotificacion) {

	var jqxhr = $.ajax("/confirmFriendship/" + idAmigo + "/" + idNotificacion)
			.done(function(jqXHR) {
				fadeWindows();
			}).fail(function(jqXHR) {
				//errores

			})
}

function deleteFriendship() {

}

function index() {
	FB.logout(function(response) {
		location.href = "/";
	});

}

function busquedaDeAmigos() {

	var namePart = $("#input-search-input").val();

	var jqxhr = $
			.getJSON("/userSearch/" + namePart)
			.done(
					function(json) {

						document.getElementById('search-container').innerHTML = "";
						document.getElementById('div-search-results-div').innerHTML = "";
						$
								.each(
										json.users,
										function() {

											document
													.getElementById('search-container').innerHTML = document
													.getElementById('search-container').innerHTML
													+ '<div class= "div-userFriend marginTM marginBM cfix" style="border: 1px solid black">'
													+ '<a href="/perfil/'
													+ this.id
													+ '"><img src="http://graph.facebook.com/'+this.facebookid+'/picture?type=large" /></a>'
													+ '<div id="div-user-fullname" align="center"><a href="/perfil/'
													+ this.id
													+ '"><span>'
													+ this.primerNombre
													+ ' '
													+ this.primerApellido
													+ '</span></a></div>'
													+ '<div id="div-username" align="center" style="margin-top:18px"><span>'
													+ this.username
													+ '</span></div>'
													
													+ '</div>';
										})

						//programar manejo de errores de get json
					})
			.fail(
					function(jqXHR) {

						document.getElementById('search-container').innerHTML = "No se encontraron resultados para la busqueda"
						//document.getElementById('div-friendship-sol').innerHTML = "No hay solicitudes de amistad pendientes";
						//programar manejo de errores de get json
					})
}


function searchForFriends3() {
	var nameUserPattern = $("#input-search-input").val();

	var jqxhr = $
			.getJSON("/searchForFriends/" + nameUserPattern)
			.done(
					function(json) {
						document.getElementById('div-search-results-div').innerHTML = "";
						$
								.each(
										json.users,
										function() {
											document
													.getElementById('div-search-results-div').innerHTML = document
													.getElementById('div-search-results-div').innerHTML
													+ '<div class="founded-user-next" style="border-bottom:solid 1px black;background-color:#DCC">'
													+ '<a href="perfilPublic/'
													+ this.id
													+ '"><img src="http://graph.facebook.com/'+this.facebookid+'/picture?type=large" /></a>'
													+ '<div id="div-user-fullname" style="margin-left:75px;"><a href="perfilPublic/'
													+ this.id
													+ '"><span>'
													+ this.primerNombre
													+ ' '
													+ this.segundoNombre
													+ ' '
													+ this.primerApellido
													+ ' '
													+ this.segundoApellido
													+ '</span></a></div>'
													+ '<div id="div-username"  style="margin-left:75px;margin-top:12px"><span>'
													+ this.username
													+ '</span></div>'
													+ '</div>';
										})
						// programar manejo de errores de get json
					})
			.fail(
					function(jqXHR) {
						document.getElementById('div-search-results-div').innerHTML = "";

						// programar manejo de errores de get json
					})
}


function busquedaDeAmigosPublic() {

	var namePart = $("#input-search-input").val();

	var jqxhr = $
			.getJSON("/userSearch/" + namePart)
			.done(
					function(json) {

						document.getElementById('search-container').innerHTML = "";
						document.getElementById('div-search-results-div').innerHTML = "";
						$
								.each(
										json.users,
										function() {

											document
													.getElementById('search-container').innerHTML = document
													.getElementById('search-container').innerHTML
													+ '<div class= "div-userFriend marginTM marginBM cfix" style="border: 1px solid black">'
													+ '<a href="/perfilPublic/'
													+ this.id
													+ '"><img src="http://graph.facebook.com/'+this.facebookid+'/picture?type=large" /></a>'
													+ '<div id="div-user-fullname" align="center"><a href="/perfilPublic/'
													+ this.id
													+ '"><span>'
													+ this.primerNombre
													+ ' '
													+ this.primerApellido
													+ '</span></a></div>'
													+ '<div id="div-username" align="center" style="margin-top:18px"><span>'
													+ this.username
													+ '</span></div>'
													
													+ '</div>';
										})

						//programar manejo de errores de get json
					})
			.fail(
					function(jqXHR) {

						document.getElementById('search-container').innerHTML = "No se encontraron resultados para la busqueda"
						//document.getElementById('div-friendship-sol').innerHTML = "No hay solicitudes de amistad pendientes";
						//programar manejo de errores de get json
					})
}


function fadeWindows()
{
	
	$("#div-friendship-sol").fadeOut(100);
	
}

function getInstagramPhotos()
{
	var tag = $('#multi-input-search').val();
	var count = "20";
	var acces_token = "190007719.5b9e1e6.74388794c5e54e4498623088e702536d";
	var access_parameters = {"access_token":"190007719.5b9e1e6.74388794c5e54e4498623088e702536d"};
	var instagramUrl = 'https://api.instagram.com/v1/tags/' + tag + '/media/recent?callback=?&count='+count;
	var photoId = 0;
	
	$('#instagram-div').html('');
	

	var jqxhr = $.getJSON( instagramUrl,access_parameters)
	       .done(function(json) {
		
	    	if(json.meta.code == 200) 
	    	{
	    		var photos = json.data;
	    		
	    		 if(photos.length > 0) {
	    			 
	    			 for (var key in photos ){
	    				 
	    				 var photo = photos[key];
	    				 $('#instagram-div').append('<div class="left firstImgCel"><img src="' + photo.images.thumbnail.url 
	    						                      + '" /><input type="checkbox" name="checkB" id="'+ photo.images.standard_resolution.url
	    						                      +'" class="checkMultimedia" title="'+photo.images.thumbnail.url+'" /></div>');
	    				 
	    			 }
	    			 
	    		 }
	    		 else 
	    		 {
	    		     
	    		      $("#instagram-div").append("Hmm.  I couldnt find anything!");
	    		 }
	    
	         }
	    	 else  
	    	 {
	    		  
	    		      var error = json.data.meta.error_message;
	    		      $('#multimedia-div').append('Something happened, Instagram said: ' + error);
	    	 }
	     
	    	  
	    	   
	    	   
	    	   
	})
		  
	
}

function mostrarAreaComent()
{ 
	 $('#input-coment').slideDown(250);
}


function checkKeyAlbum(key,currentUserId,selectedUserId,currentUserFB,currentUserFirstName,currentUserLastname)
{

    var unicode
    
    if(key.charCode)
    {
    	unicode=key.charCode;
    }
    else
    {
    	unicode=key.keyCode;
    }
 
    if (unicode == 13)
    {
    	$('#input-coment').slideUp(200);
    	
    	var albumId = $("#albumId").val();
    	var contenido = $('#input-coment').val();
   
    	
    	var jqxhr = $.post("/nuevoComentario",{elementId: albumId,contenido: contenido,
    		                                   tipo: 'ALBUM'})
		.done(function(jqXHR) {
					
			var contenido = '<div class="comment" id="'+jqXHR+'">'+
			
			'<div style="height:55px">'+
			'<a class="imga left" href="perfil/'+currentUserId+'"> <img class="imgi"' +
			 'src=http://graph.facebook.com/'+currentUserFB+'/picture />'+
			'</a>'+
			'<div class="left" style="margin: 15px 0 0 10px"><a class="userName" href="perfil/'+currentUserId+'">'+currentUserFirstName+" " +currentUserLastname +' dice: </a></div>'+
			'</div>'+
			$('#input-coment').val()+
			
	          '<div id="interaction-options" style="height:50px;zoom:0.7">'+
	          
		       '<span id="like-boton'+jqXHR+'">'+			             

	          '<button class="btn btn-info left like-button" onclick = "meGustaComment('+jqXHR+')">Me Gusta</button>'+
              '<input id="isLike'+jqXHR+'" type="hidden" value="0" />'+
              
              '</span>'+
              
              '<span id="unlike-boton'+jqXHR+'">'+
              '<input id="isUnlike'+jqXHR+'" type="hidden" value="0" />'+
	          '<button class="btn btn-info left no-like-button" onclick = "noMeGustaComment('+jqXHR+')">No Me Gusta</button>'+
	          '</span>'+
	          
	          '<abel class="label-coment" onclick="commentReply('+jqXHR+')">Responder</label>'+
	          '<span style="margin: 20px 8px 0 550px; color:green"><img src="assets/images/like.png" id="likes"><span id="likeCount'+jqXHR+'">0</span></span>'+
	          '<span style="color:red"><img src="assets/images/unlike.png" id="unlikes"><span id="unlikeCount'+jqXHR+'">0</span></span>';
	         
			if(currentUserId == selectedUserId ){
             	contenido = contenido + '<button class="right" onclick=eliminarComentario('+jqXHR+')>Eliminar</button>';
             }
			
			contenido = contenido + '</div></div>'+
			                        '<textarea id="text'+jqXHR+'" class="input-coment"'+
			                        'maxlength="255" style="display:none" onkeypress="checkKeyComment(event,'+currentUserId+','+selectedUserId+',this.id,\''+currentUserFB+'\',\''+currentUserFirstName+'\',\''+currentUserLastname+'\')">'+
			                        '</textarea>';
		
			$('#comentarios').html(contenido + $('#comentarios').html());
			
			$('#input-coment').val("");
			
		}).fail(function(jqXHR) {

			// programar manejo de errores 
		})
    	
    }
    
}


function meGustaAlbum()
{
	var albumId = $("#albumId").val();
	var isUnlike = $("#isUnlike").val();
	
	if(isUnlike == 1)
	    podriaGustarmeAlbum();
	
	var jqxhr = $.post("/nuevoLike",{elementId: albumId, element: 'ALBUM'})

    .done(function(jqXHR) {
            
    	var contenido = '<button class="btn btn-danger left like-button"'+
                       'onclick="yaNoMeGustaAlbum()" style="width:90px">Ya no me gusta</button>'+		       
                       '<input id="isLike" type="hidden" value="1" />';

        $("#like-boton").html(contenido);
    	$("#likeCount").html(parseInt($("#likeCount").html())+1);
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function noMeGustaAlbum()
{
	var albumId = $("#albumId").val();
    var isLike = $("#isLike").val();
	
    if(isLike == 1)
	   yaNoMeGustaAlbum();
	
	var jqxhr = $.post("/nuevoUnlike",{elementId: albumId, element: 'ALBUM'})

    .done(function(jqXHR) {
    	
    	var contenido = '<button class="btn btn-primary left like-button"'+
        'onclick="podriaGustarmeAlbum()" style="width:90px">Podria Gustarme</button>'+		       
        '<input id="isUnlike" type="hidden" value="1" />';
    	
        $("#unlike-boton").html(contenido);

    	$("#unlikeCount").html(parseInt($("#unlikeCount").html())+1);
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function eliminarComentario(commentId)
{
	var jqxhr = $.ajax("/deleteComment/" + commentId)
	.done(function(jqXHR) {
		   $("#"+commentId).slideUp(300);
	}).fail(function(jqXHR) {

		// programar manejo de errores
	})
}

function commentReply(areaNumber)
{
	$('#text'+areaNumber).slideDown(250);
}

function checkKeyComment(key,userId,selectedUserId,textCommentId,currentUserFB,currentUserFirstName,currentUserLastname)
{
	
    var unicode
    
    if(key.charCode)
    {
    	unicode=key.charCode;
    }
    else
    {
    	unicode=key.keyCode;
    }
 
    if (unicode == 13)
    {
    
    	$('#'+textCommentId).slideUp(200);
        
    	
    	var commentId = textCommentId.substring(4,textCommentId.length);
    	var contenido = $('#'+textCommentId).val();
    	
    	
    	var jqxhr = $.post("/nuevoComentario",{elementId: commentId,contenido: contenido,
    		                                   tipo: 'COMENTARIO'})
		.done(function(jqXHR) {
					
			var contenido = '<div class="comment" id="'+jqXHR+'">'+
			
			'<div style="height:55px">'+
			'<a class="imga left" href="perfil/'+userId+'"> <img class="imgi"'+
			'src=http://graph.facebook.com/'+currentUserFB+'/picture />'+
			'</a>'+
			'<div class="left" style="margin: 15px 0 0 10px"><a class="userName" href="perfil/'+userId+'">'+currentUserFirstName+" " +currentUserLastname +' dice: </a></div>'+
			'</div>'+ $('#'+textCommentId).val()+
			
	          '<div id="interaction-options" style="height:50px;zoom:0.7">'+
	          
		      '<span id="like-boton'+jqXHR+'">'+			             

	          '<button class="btn btn-info left like-button" onclick = "meGustaComment('+jqXHR+')">Me Gusta</button>'+
	          '<input id="isLike'+jqXHR+'" type="hidden" value="0" />'+
	          
	          '</span>'+
	          
		      '<span id="unlike-boton'+jqXHR+'">'+			             

	          '<button class="btn btn-info left no-like-button" onclick = "noMeGustaComment('+jqXHR+')">No Me Gusta</button>'+
              '<input id="isUnlike'+jqXHR+'" type="hidden" value="0" />'+
	          
              '</span>'+
              
	          '<label class="label-coment" onclick="commentReply('+jqXHR+')">Responder</label>'+
	          '<span style="margin: 20px 8px 0 550px; color:green"><img src="assets/images/like.png" id="likes"><span id="likeCount'+jqXHR+'">0</span></span>'+
	          '<span style="color:red"><img src="assets/images/unlike.png" id="unlikes"><span id="unlikeCount'+jqXHR+'">0</span></span>';
	         
	         
			if(userId == selectedUserId ){
             	contenido = contenido + '<button class="right" onclick=eliminarComentario('+jqXHR+')>Eliminar</button>';
             }
			
			
			contenido = contenido + '</div></div>'+
			                        '<textarea id="text'+jqXHR+'" class="input-coment"'+
			                        'maxlength="255" style="display:none" onkeypress="checkKeyComment(event,'+userId+','+selectedUserId+',this.id,\''+currentUserFB+'\',\''+currentUserFirstName+'\',\''+currentUserLastname+'\')">'+
			                        '</textarea>';
		
		
			$('#'+commentId).html($('#'+commentId).html()+contenido);
			
			$('#'+textCommentId).val(""); 
			
		}).fail(function(jqXHR) {

			 //errores
		})
    	
    }
    
}


function meGustaComment(commentId)
{
	
    var isUnlike = $("#isUnlike"+commentId).val();
	
    if(isUnlike == 1)
	   podriaGustarmeComment(commentId);
	
	var jqxhr = $.post("/nuevoLike",{elementId: commentId, element: 'COMENTARIO'})

    .done(function(jqXHR) {
            
    	var contenido = '<button class="btn btn-danger left like-button"'+
        'onclick="yaNoMeGustaComment('+commentId+')" style="width:90px">Ya no me gusta</button>'+		       
        '<input id="isLike'+commentId+'" type="hidden" value="1" />';

        $("#like-boton"+commentId).html(contenido);

        $("#likeCount"+commentId).html(parseInt($("#likeCount"+commentId).html())+1);
     
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function noMeGustaComment(commentId)
{
    var isLike = $("#isLike"+commentId).val();
	
    if(isLike == 1)
	   yaNoMeGustaComment(commentId);
	
	var jqxhr = $.post("/nuevoUnlike",{elementId: commentId, element: 'COMENTARIO'})

    .done(function(jqXHR) {
    	
    	var contenido = '<button class="btn btn-primary left like-button"'+
        'onclick="podriaGustarmeComment('+commentId+')" style="width:90px">Podria Gustarme</button>'+		       
        '<input id="isUnlike'+commentId+'" type="hidden" value="1" />';
    	
        $("#unlike-boton"+commentId).html(contenido);
   
    	$("#unlikeCount"+commentId).html(parseInt($("#unlikeCount"+commentId).html())+1);
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function addContentToAlbum()
{
	var arrayPosition = 0;

	var photos = [];
    var tumbs = [];
  	
  	$("input[name=checkB]:checked").each(function() {
  	    photos.push(this.id);
  	});
  	
  	$("input[name=checkB]:checked").each(function() {
  	    tumbs.push(this.title);
  	});
  	
  	if(photos.length > 0)
  	{
  	   var jqxhr = $.post("/addContentToAlbum",{'photos[]':photos,'tumbs[]':tumbs})
  	
  	   .done(function(jqXHR) {
	  
		
	   	   if (jqXHR == "true") {
            
			   location.href = "/showAlbumContent";
		   } 
		
	   }).fail(function(jqXHR) {
		
		   // mensaje de error en conexion
	   })
  	}
  	else
  	{
  		location.href = "/showAlbumContent";
  	}
}

function deleteContentInAlbum()
{
	var photos = [];
	

	$("input[name=checkB]:checked").each(function() {
	    photos.push(this.id);
	});

	if(photos.length > 0)
	{
       var jqxhr = $.post("/deleteContentInAlbum",{'photos[]':photos})
  	
  	   .done(function(jqXHR) {
	  
		
		   if (jqXHR == "true") {
            
			   location.href = "/showAlbumContent";
		   } 
		
	   }).fail(function(jqXHR) {
		
		
		   // mensaje de error en conexion
	   })
	}
	else
	{
		location.href = "/showAlbumContent";
	}
 
}





function editAlbumTitle(id) {

	
	if ($("#modify").attr("value") == 0) {
		$("#span-titulo")
				.html(
						"<input type='text' id='title-input' name='title-input' maxlength='30' placeholder='Ingresar Titulo' value='"
								+ $("#span-titulo").html() + "' />");
		
		$("#description-edit-button").slideUp(150);
		$("#title-edit-button").attr("src",
				"assets/images/save_icon.png");
		$('#title-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) {
		if ($("#title-input").attr("value") == "") {

		} else {
			
			// programar manejo de errores de update en bd
			updateAlbum(1, $("#title-input").val(), id);
		
			$("#description-edit-button").slideDown(150);
			$("#span-titulo").html($("#title-input").attr("value"));
			
			$("#title-edit-button").attr("src",
					"assets/images/edit_icon.png");
			$('#title-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");

		}

	}
}

function editAlbumDescription(id) {

	if ($("#modify").attr("value") == 0) {
		$("#span-description")
				.html(
						"<input type='text' id='description-input' name='description-input' maxlength='50' placeholder='Ingresar Titulo' value='"
								+ $("#span-description").html() + "' />");
		
		$("#title-edit-button").slideUp(150);
		$("#description-edit-button").attr("src",
				"assets/images/save_icon.png");
		$('#description-edit-button').attr("title", "guardar");
		$("#modify").attr("value", "1");
	} 
	else if ($("#modify").attr("value") == 1) {
		if ($("#description-input").attr("value") == "") {

		} else {
			
			// programar manejo de errores de update en bd
		    updateAlbum(2, $("#description-input").val(), id);
		
			$("#title-edit-button").slideDown(150);
			$("#span-description").html($("#description-input").attr("value"));
			
			$("#description-edit-button").attr("src",
					"assets/images/edit_icon.png");
			$('#description-edit-button').attr("title", "editar");
			$("#modify").attr("value", "0");

		}

	}
}

function yaNoMeGustaAlbum()
{
    var albumId = $("#albumId").val();
	
	var jqxhr = $.post("/deleteLikeAlbum",{elementId: albumId})

    .done(function(jqXHR) {
            
    	   var contenido = '<button class="btn btn-info left like-button"'+ 
    		               'onclick="meGustaAlbum()">Me Gusta</button>'+
	                       '<input id="isLike" type="hidden" value="0" />';
    	   
    	   $("#likeCount").html(parseInt($("#likeCount").html())-1);
    	   $("#like-boton").html(contenido);
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function podriaGustarmeAlbum()
{
    var albumId = $("#albumId").val();
	
	var jqxhr = $.post("/deleteUnlikeAlbum",{elementId: albumId})

    .done(function(jqXHR) {
            
    	   var contenido = '<button class="btn btn-info left no-like-button"'+
    		               'onclick="noMeGustaAlbum()">No Me Gusta</button>'+
	                       '<input id="isUnlike" type="hidden" value="0" />';
    	   
    	   $("#unlikeCount").html(parseInt($("#unlikeCount").html())-1);
    	   $("#unlike-boton").html(contenido);
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
}


function yaNoMeGustaComment(commentId)
{

	var jqxhr = $.post("/deleteLikeComment",{elementId: commentId})

    .done(function(jqXHR) {
            
    	var contenido = '<button class="btn btn-info left like-button"'+ 
        'onclick="meGustaComment('+commentId+')">Me Gusta</button>'+
        '<input id="isLike'+commentId+'" type="hidden" value="0" />';
    	
        $("#likeCount"+commentId).html(parseInt($("#likeCount"+commentId).html())-1);
        
 	   $("#like-boton"+commentId).html(contenido);

     
   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function podriaGustarmeComment(commentId)
{
	
	var jqxhr = $.post("/deleteUnlikeComment",{elementId: commentId})

    .done(function(jqXHR) {
   
    	var contenido = '<button class="btn btn-info left no-like-button"'+
        'onclick="noMeGustaComment('+commentId+')">No Me Gusta</button>'+
        '<input id="isUnlike'+commentId+'" type="hidden" value="0" />';
    	
    	$("#unlikeCount"+commentId).html(parseInt($("#unlikeCount"+commentId).html())-1);
    	
  	   $("#unlike-boton"+commentId).html(contenido);

   
    }).fail(function(jqXHR) {

        // programar manejo de errores 
    })
	
}

function mostrarBoton(albumId)
{
  $("#firstImgCel"+albumId ).hover(
		  function() {
		    $("#btnImage"+albumId).show();
		  }, function() {
		    $("#btnImage"+albumId).hide();
		  }
		);

}


function modifyAlbumPhoto(albumId)
{
     location.href="/editPortadaAlbum/"+albumId;	
}

function updateAlbumCaratula(albumId,numeroFotos)
{
	for (var cuentaFotos = 0;cuentaFotos<numeroFotos;cuentaFotos++)
	{
		if (document.formFoto.radioB[cuentaFotos].checked)
		{
			updateAlbum(3,document.formFoto.radioB[cuentaFotos].value, albumId);		
			break;
		}
			
		
	}
	
	location.href = "/albumesPag";
	
}
