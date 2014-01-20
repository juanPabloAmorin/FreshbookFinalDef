function getYoutubeVideos() {

	var tag = $('#multi-input-search').val();
	
	var youtubeUrl = 'http://gdata.youtube.com/feeds/api/videos?q='+tag+'&alt=json&max-results=30&format=5';
	
	$('#youtube-div').html('');

	
	var jqxhr = $.getJSON(youtubeUrl)
			.done(
					
					function(json) {

					  var feed = json.feed;
					  var entries = feed.entry || [];
					  var html = [''];	

					  html.push('<div align="center" style="margin-top:20px;font-size:18px"><img src="assets/images/Logos/YoutubeLogo.png" class="logo"/></div>');
					  
					  for (var i = 0; i < entries.length; i++) {
						    var entry = entries[i];
						    var title = entry.title.$t.substr(0, 20);
						    var thumbnailUrl = entries[i].media$group.media$thumbnail[0].url;
						    var playerUrl = entries[i].media$group.media$content[0].url;

						   
						    
						    html.push('<div class="left firstVideoCel">',
						    		'<input type="checkbox" name="checkV" id="',entries[i].media$group.media$content[0].url,'" class="checkMultimedia" data-name="',title,'" title="',
									  thumbnailUrl,'" />',
						              '<img src="', 
						              thumbnailUrl, '" width="130" height="97"/>', '<br/><span class="titlec">', title, '...</span></div>');

						    
						  }

						  document.getElementById('youtube-div').innerHTML = html.join('');
						  
						
					})    

			.fail(
					function(json){

						//funcion falla
					})

}


function loadVideo(playerUrl, autoplay) {
	  swfobject.embedSWF(
	      playerUrl + '&rel=1&border=0&fs=1&autoplay=' + 
	      (autoplay?1:0), 'player', '290', '250', '9.0.0', false, 
	      false, {allowfullscreen: 'true'});
	}
