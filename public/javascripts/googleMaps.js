
// The following example creates a marker in Stockholm, Sweden
// using a DROP animation. Clicking on the marker will toggle
// the animation between a BOUNCE animation and no animation.

var stockholm = new google.maps.LatLng(59.32522, 18.07002);
var parliament = new google.maps.LatLng(59.327383, 18.06747);
var marker;
var map;
var geocoder;
var country;
var state;
var city;

var popup = null;

function initialize() {
  var mapOptions = {
    zoom: 11,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    center: stockholm
  };
  

  map = new google.maps.Map(document.getElementById('map-canvas'),
          mapOptions);

  popup = new google.maps.InfoWindow();

  geocoder = new google.maps.Geocoder();

  marker = new google.maps.Marker({
    map:map,
    draggable:true,
    animation: google.maps.Animation.DROP,
    position: parliament
   
  });
  google.maps.event.addListener(marker, 'click', toggleBounce);
  
  google.maps.event.addListener(marker, 'mouseup', function(){

	  codeLatLng(marker);
	 // toggleBounce();

	  });


}


function codeLatLng(marker) {
	  var markerLatLng = marker.getPosition();
	  var lat = markerLatLng.lat();
	  var lng = markerLatLng.lng();
	  country = "";
	  state = "";
	  city = "";
	 
	  var latlng = new google.maps.LatLng(lat, lng);
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
                $('#input-location').val(country)
            }

            else if(typeof city === "undefined")
            {
                
                city = "";
                popup.setContent(country+", "+state);
                $('#input-location').val(country+", "+state)
            }
            else
            {
            	popup.setContent(country+", "+state+", "+city);
            	$('#input-location').val(country+", "+state+", "+city);
            }
                
	       
	        popup.open(map, marker);
            
	         
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

 