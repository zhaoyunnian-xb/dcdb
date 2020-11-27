$(document).ready(function() {
	$("#menu").height($(window).height() - 60 + 'px');
	$("#main_iframe").height($(window).height() - 60 + 'px');
	$(window).resize(function() {
		$("#menu").height($(window).height() - 65 + 'px');
		$("#main_iframe").height($(window).height() - 65 + 'px');
	})
	/*$.ajax({
		url: "/c/pub/getOnlineCount.do",
		async: false,
		dataType:"text",
		success: function(msg){
			$("#onlineCount").html(msg);
		}
	});*/
})

function changepwd() {
	layer.open({
		type : 1,
		title : '修改密码',
		skin : 'layui-layer-rim', // 加上边框
		area : [ '520px', '370px' ], // 宽高
		content : $('#changepwd'),
	});
	$('#changepwd').show();
}


/**
 * 在线人员列表
 */
var openflag = false;
function onlinelist(cpage) {
	$.ajax({
		url: "/c/pub/getOnlineList.do",
		async: false,
		data : {"cpage":cpage},
		dataType:"json",
		success: function(msg){
			if(msg.ret == 1){
				var html = "";
				var list = msg.list;
				for(var i=0;i<list.length;i++){
					html += "<tr>";
					html += "<td>"+list[i].unit_name+"</td>"
					html += "<td>"+list[i].name+"</td>"
					html += "<td>"+list[i].login_time+"</td>"
					html += "</tr>";
				}
				$("#onlinebody").html(html);
				var count = parseInt(msg.count);//总条数
				var page = parseInt(msg.page);//当前页
				$("#totalPage").html(Math.ceil(count/30));
				$("#totalCount").html(count);
				$("#cpage").val(cpage);
			}else{
				alert("获取人员列表失败!");
			}
		}
	});
	if(openflag == false){
		layer.open({
			type : 1,
			title : '在线人员列表',
			skin : 'layui-layer-rim', // 加上边框
			area : [ '820px', '570px' ], // 宽高
			content : $('#onlinelist'),
			success: function(layero, index){
				openflag = true;
			},
			cancel: function(index, layero){ 
				openflag = false;
			}
		});
	}
//	$('#onlinelist').show();
}

/** 在线人员列表 分页按钮 start ***************************************/
function page(type){
	if(type == 'first'){
		$("#cpage").val("1");
		onlinelist(1);
		$("#first").prop("src","../common/images/page/page-first-disabled-new.png");
		$("#back").prop("src","../common/images/page/page-prev-disabled-new.png");
		var totalPage = $("#totalPage").text();
		if(totalPage != 1){
			$("#next").prop("src","../common/images/page/page-next-new.png");
			$("#last").prop("src","../common/images/page/page-last-new.png");
		}
	}else if(type == 'back'){
		var cpage = parseInt($("#cpage").val());
		if(cpage == 1){
			$("#first").prop("src","../common/images/page/page-first-disabled-new.png");
			$("#back").prop("src","../common/images/page/page-prev-disabled-new.png");
			return;
		}else{
			$("#cpage").val(cpage - 1);
			onlinelist(cpage - 1);
			if((cpage - 1) == 1){
				$("#first").prop("src","../common/images/page/page-first-disabled-new.png");
				$("#back").prop("src","../common/images/page/page-prev-disabled-new.png");
			}else{
				$("#next").prop("src","../common/images/page/page-next-new.png");
				$("#last").prop("src","../common/images/page/page-last-new.png");
			}
		}
	}else if(type == 'next'){
		var cpage = parseInt($("#cpage").val());
		var totalPage = parseInt($("#totalPage").text());
		if(cpage == totalPage){
			return;
		}else{
			$("#cpage").val(cpage + 1);
			onlinelist(cpage + 1);
			$("#first").prop("src","../common/images/page/page-first-new.png");
			$("#back").prop("src","../common/images/page/page-prev-new.png");
			var totalPage = parseInt($("#totalPage").text());
			if((cpage+1) == totalPage){
				$("#next").prop("src","../common/images/page/page-next-disabled-new.png");
				$("#last").prop("src","../common/images/page/page-last-disabled-new.png");
			}
		}
	}else if(type == 'last'){
		var totalPage = $("#totalPage").text();
		$("#cpage").val(totalPage);
		onlinelist(totalPage);
		if(totalPage != 1){
			$("#last").prop("src","../common/images/page/page-last-disabled-new.png");
			$("#next").prop("src","../common/images/page/page-next-disabled-new.png");
			$("#first").prop("src","../common/images/page/page-first-new.png");
			$("#back").prop("src","../common/images/page/page-prev-new.png");
		}
	}
}
/** 在线人员列表 分页按钮 end ***************************************/

function displaySubMenu(li) {
	var subMenu = li.getElementsByTagName("ul")[0];
	subMenu.style.display = "block";
}
function hideSubMenu(li) {
	var subMenu = li.getElementsByTagName("ul")[0];
	subMenu.style.display = "none";
}
function hiddenDiv() {
	document.getElementById('navigation1').style.display = 'none';
}
function displayDiv() {
	document.getElementById('navigation1').style.display = 'block';
}
function hiddenChildDiv(obj, pic) {
	obj.src = "/phframework/ph/pages/xlimages/" + pic + "A.png";
}
function displayChildDiv(obj, pic) {
	document.getElementById('navigation1').style.display = 'block';
	obj.src = "/phframework/ph/pages/xlimages/" + pic + "B.png";
}

function toHome() {
	window.location.href = "http://10.37.0.177:8080/index/logoutto";
}

function toDoPage() {
	window.top.location.href = "http://192.168.1.205:8080/main";
}

// 校验密码
function checkPwd() {
	var newpwd = $("#newpwd").val();
	var newpwd2 = $("#newpwd2").val();
	if (newpwd == "" || newpwd2 == "") {
		alert("请检查必添项!");
		return;
	}
	if (newpwd != newpwd2) {
		alert("两次密码不一致!");
		return;
	}
	var ss = /^[a-zA-Z0-9_]{4,10}$/;
	if (!ss.test(newpwd)) {
		alert("密码格式不正确!");
		return;
	}
}

// 执行更改密码
function doChangePwd() {
	var username = $("#username").val();
	var old = $("#old").val();
	var newpwd = $("#newpwd").val();
	var newpwd2 = $("#newpwd2").val();
	if (old == "" || newpwd == "" || newpwd2 == "") {
		alert("请检查必添项!");
		return;
	}
	if (newpwd != newpwd2) {
		alert("两次密码不一致!");
		return;
	}
	var ss = /^[a-zA-Z0-9_]{4,10}$/;
	if (!ss.test(newpwd)) {
		alert("密码格式不正确!");
		return;
	}
	$.ajax({
		url : "/users/changePwd.ajax",
		async : false,
		dataType : "text",
		data : {
			"username" : username,
			"old" : old,
			"newpwd" : newpwd
		},
		success : function(msg) {
			if (msg.result == "SUCCESS") {
				alert("修改成功");
			} else {
				alert(msg);
			}
		}
	});
}