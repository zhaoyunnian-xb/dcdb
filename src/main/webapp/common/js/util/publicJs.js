//获取当前网址，如： http://localhost:8088/test/test.jsp  
var curPath = window.document.location.href;
// 获取主机地址之后的目录，如： test/test.jsp
var pathName = window.document.location.pathname;
var pos = curPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8088
var localhostPath = curPath.substring(0, pos);
// 获取带"/"的项目名，如：/test
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

var path = localhostPath + projectName + "/";

function goPage(type) {
	if (type == "first") {
		$("#cpage").val("1");
		$("#page").submit();
	}else if (type == "back") {
		if (Number($.trim($("#cpage").val())) >= 2) {
			$("#cpage").val(parseInt($("#cpage").val()) - 1);
			$("#page").submit();
		} else {
			alert("已经是首页了");
			return;
		}
		
	}else if (type == "next") {
		if (Number($.trim($("#cpage").val())) < Number($.trim($("#totalPage").html()))) {
			$("#cpage").val(parseInt($("#cpage").val()) + 1);
			$("#page").submit();
		} else {
			alert("已经是末页了");
			return;
		}
		
	}else if (type == "last") {
		$("#cpage").val($("#totalPage").html());
		$("#page").submit();
	}
}
