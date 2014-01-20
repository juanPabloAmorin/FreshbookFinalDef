
SC.initialize({
  //client_id: '126e9fbcc71de142ab714d2951d9f855'  //localhost
	client_id: '23838439364ec9faa337132e4b14aaa8'  //heroku
});

function playSound(sound)
{

	var track_url = sound;
	
	SC.oEmbed(track_url, { auto_play: true }, function(oEmbed) {
	    alert("works");
	});

}

var track_url = 'http://soundcloud.com/forss/flickermood';
SC.oEmbed(track_url, { auto_play: true }, function(oEmbed) {
  console.log('oEmbed response: ' + oEmbed);
});

// find all sounds of buskers licensed under 'creative commons share alike'
function getSoundsOfSoundcloud()
{

	var tag = $('#multi-input-search').val();
	$('#soundcloud-div').html('<div align="center" style="margin-top:20px;font-size:18px"><img src="assets/images/Logos/SoundcloudLogo.png" class="logo"/></div>');
	
   SC.get('/tracks', { q: tag }, function(tracks) {
  
	  if (tracks.length > 0) {

		  
			for (var key = 1;key <=15; key++) {
				
				var src = "assets/images/musicnote.jpg";
				
				var track = tracks[key];
			
				if(track.artwork_url != null)
				{
					src = track.artwork_url;
				}
				
				
				$('#soundcloud-div')
				.append(
						'<div class="firstSoundCel"><img src="'
								+ src
								+ '" /><input type="checkbox" name="checkS" id="'
								+ track.uri
								+ '" class="checkMultimedia" data-name="'+ track.title +'" title="'
								+ src
								+ '" /></div>'
								
				         + '<span>' + track.title + '<span>');   

			}

		} else {

			$("#soundcloud-div")
					.append(
							"No hay resultados para la busqueda solicitada");
		}
	  

  });

}