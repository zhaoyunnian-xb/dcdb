$(document).ready(function(){
	avarsso.get_username(getUsernameCallback);
})

function getUsernameCallback(token){
	if(token.username == undefined){
		window.location.href = "http://10.37.0.177";
	}else{
		$("#username").val(token.username);
		$("#index").submit();
	}
}