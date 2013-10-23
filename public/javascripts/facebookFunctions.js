var firstName;
var lastName;
var middleName;
var email;
var birthday;


window.fbAsyncInit = function() {
FB.init({
  appId      : '672888336062808', // App ID
  //channelUrl : 'http://localhost:9000', // Channel File
  channelUrl : 'http://agile-sands-9357.herokuapp.com', // Channel File
  status     : true, // check login status
  cookie     : true, // enable cookies to allow the server to access the session
  xfbml      : true  // parse XFBML
});

FB.Event.subscribe('auth.authResponseChange', function(response) {
  
  if (response.status === 'connected') {
 
	  fectchUserInformation();
   
  } 
}, {scope: 'email,user_likes,user_birthday'});
};

// Load the SDK asynchronously
(function(d){
 var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
 if (d.getElementById(id)) {return;}
 js = d.createElement('script'); js.id = id; js.async = true;
 js.src = "//connect.facebook.net/en_US/all.js";
 ref.parentNode.insertBefore(js, ref);
}(document));


//var uri = encodeURI('http://localhost:9000');
var uri = encodeURI('agile-sands-9357.herokuapp.com');

      function login() {
                
              window.location = encodeURI("https://www.facebook.com/dialog/oauth?client_id=672888336062808&redirect_uri="+uri+"&response_type=token&scope=email,user_birthday");
          }

function fectchUserInformation() {

          
          
          FB.api('/me', function(response) {
                   
              
              birthday = response.birthday;
              firstName = response.first_name;
              middleName = response.middle_name;
              lastName = response.last_name;
              email = response.email;
              linkFacebook = response.link;
              
              window.location = encodeURI("/userAutentication/"+email+"/"+linkFacebook+"/"+firstName+"/"+middleName+"/"+lastName);
          });
        }
