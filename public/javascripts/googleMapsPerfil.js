
var caracas = new google.maps.LatLng($('#latitud-input').val(), $('#longitud-input').val());
var parliament = new google.maps.LatLng($('#latitud-input').val(), $('#longitud-input').val());
var marker;
var map;
var geocoder;
var country;
var state;
var city;
var latitud;
var longitud;
var popup = null;

function initialize() {
  var mapOptions = {
    zoom: 11,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    center: caracas
  };
  

  map = new google.maps.Map(document.getElementById('map-canvas'),
          mapOptions);

  popup = new google.maps.InfoWindow();

  geocoder = new google.maps.Geocoder();

  marker = new google.maps.Marker({
    map:map,
    
    animation: google.maps.Animation.DROP,
    position: parliament
   
  });
  
  
 
	  codeLatLng(marker);
	 

	  


}


function codeLatLng(marker) {
	  var markerLatLng = marker.getPosition();
	  latitud = markerLatLng.lat();
	  longitud = markerLatLng.lng();
	  country = "";
	  state = "";
	  city = "";
	 
	  var latlng = new google.maps.LatLng(latitud, longitud);
	  geocoder.geocode({'latLng': latlng}, function(results, status) {
	    if (status == google.maps.GeocoderStatus.OK) {
	      if (results[0]) {
	       
	                marker.setMap(map);
	                marker.setPosition(latlng);
	        
	        
	        for (var i = 0; i < results[0].address_components.length; i++) {
                var addr = results[0].address_components[i];
                if (addr.types[0] == 'country') 
                country = addr.long_name;                       
                else if (addr.types[0] == ['administrative_area_level_1'])
                state = addr.long_name;            
                else if (addr.types[0] == ['locality'])
                city = addr.long_name;
                
            }

            if(typeof state === "undefined") 
            {
                
                state = "";
                city = "";
                popup.setContent(country);
                $('#ubicacion').html(country)
            }

            else if(typeof city === "undefined")
            {
                
                city = "";
                popup.setContent(country+", "+state);
                $('#ubicacion').html(country+", "+state)
            }
            else
            {
            	popup.setContent(country+", "+state+", "+city);
            	$('#ubicacion').html(country+", "+state+", "+city);
            }
                
	       
	        //popup.open(map, marker);
            
	         
	      } 
	    } 
	  });
	}

function toggleBounce() {

  if (marker.getAnimation() != null) {
    marker.setAnimation(null);
  } else {
    marker.setAnimation(google.maps.Animation.BOUNCE);
  }
}

google.maps.event.addDomListener(window, 'load', initialize);

 