$("#generateCode").click(function() {
	var choos = $("input[name='choose']");
	var com = "";
	for(var i=0;i<choos.length;i++){
		com += ","+choos[i].value;
	}
	$.ajax({
		type : "POST",
		url : "some.php",
		async: false,
		data : {"choose":com},
		success : function(msg) {
			alert("Data Saved: " + msg);
		}
	});

	var html = "";
	html += "111111111";
	html += "222222222222";
	html += "33333333";
	html += "<br/>";
	html += "33333333";

	$("#content").append(html);
})