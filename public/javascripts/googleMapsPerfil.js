var caracas = new google.maps.LatLng(10.491016, -66.902061);
var parliament = new google.maps.LatLng(10.491016, -66.902061);
var marker;
var map;

function initialize() {
  var mapOptions = {
    zoom: 14,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    center: caracas
  };
  

  map = new google.maps.Map(document.getElementById('map-canvas'),
          mapOptions);


  marker = new google.maps.Marker({
    map:map,
    animation: google.maps.Animation.DROP,
    position: parliament
   
  });
  google.maps.event.addListener(marker, 'click', toggleBounce);
  


}


function toggleBounce() {

  if (marker.getAnimation() != null) {
    marker.setAnimation(null);
  } else {
    marker.setAnimation(google.maps.Animation.BOUNCE);
  }
}

google.maps.event.addDomListener(window, 'load', initialize);

