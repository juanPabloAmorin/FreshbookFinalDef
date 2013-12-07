window.fbAsyncInit = function() {
FB.init({
  appId      : '672888336062808', // FreshbookApp ID
  //appId      : '1422966607919695', //localhost
  //appId      : '596938223701048',  //amazon
  //channelUrl : 'http://localhost:9000', // Channel Local
  /channelUrl : 'http://agile-sands-9357.herokuapp.com', // Channel Heroku
  //channelUrl : 'http://54.200.53.226:9000', //Amazon
  status     : true, // check login status
  cookie     : true, // enable cookies to allow the server to access the session
  xfbml      : true  // parse XFBML
});


};

(function(d){
	 var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	 if (d.getElementById(id)) {return;}
	 js = d.createElement('script'); js.id = id; js.async = true;
	 js.src = "//connect.facebook.net/en_US/all.js";
	 ref.parentNode.insertBefore(js, ref);
	}(document));
