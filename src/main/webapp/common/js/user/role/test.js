var setting_user = {
	check: {
		enable: true,
		chkboxType: {"Y":"", "N":""}
	},
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onCheckUser: onCheckUser
	}
};

function onClickUser(e, treeId, treeNode) {
	if(treeNode.name == "山东省人民检察院"){
		return false;
	}
	var cityObj = $("#include_user");
	hideUserMenu();
	cityObj.attr("value", treeNode.name);
}

function onCheckUser(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("userDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	$("#include_user").val(v);
}

function showMenuUser() {
	var cityObj = $("#include_user");
	var cityOffset = $("#include_user").offset();
	$("#userContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDownUser);
}
function hideUserMenu() {
	$("#userContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownUser);
}
function onBodyDownUser(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "include_user" || event.target.id == "userContent" || $(event.target).parents("#userContent").length>0)) {
		hideUserMenu();
	}
}

$(function($){
	$.ajax({
		type:"post",
		url : '/org/queryOrgTree.do',
		dataType:'json',
		async: false,
		success : function (resp){
           	$.fn.zTree.init($("#userDemo"), setting_user, eval(resp));
           	zTree_Menu = $.fn.zTree.getZTreeObj("userDemo");
		}
	});
})
/**组织结构下拉菜单 end********************************************/