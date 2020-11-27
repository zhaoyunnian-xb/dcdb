function add() {//新增角色
	clearData();
	layer.open({
		type : 1,
		title : '增加管理员角色',
		skin : 'layui-layer-rim', // 加上边框
		area : [ '820px', '570px' ], // 宽高
		content : $('#add_dlg'),
	});
	$('#add_dlg').show();
}

function clearData(){
	$("#id").val("");
	$("#name").val("");
	$("#description").val("");
	$("#org").val("");
	$("#include_user").val("");
	$("#businessUser").val("");
}

function edit() {//编辑角色
	clearData();
	var id = $("input:radio[name='checks']:checked").val();
	if(id == undefined){
		alert("请选择一条记录!");
		return;
	}
	$.ajax({//查询角色信息
		url: path + "/role/queryRoleById.do",
		async: false,
		data:{"id":id},
		dataType:"json",
		success: function(msg){
			$("#id").val(msg.id);
			$("#name").val(msg.roles);
			$("#description").val(msg.description);
			$("#org").val(msg.org_name);
//			$("#max_add").val(msg.max_person);
		}
	});
	$.ajax({//查询角色信息
		url: path + "/role/queryUserNameByRoleId.do",
		async: false,
		data:{"roleid":id},
		dataType:"html",
		success: function(msg){
			$("#include_user").val(msg);
		}
	});
	
	layer.open({
		type : 1,
		title : '修改管理员角色',
		skin : 'layui-layer-rim', // 加上边框
		area : [ '820px', '570px' ], // 宽高
		content : $('#add_dlg'),
	});
	$('#add_dlg').show();
	
	
}

//删除角色
function del() {
	var r=confirm("确认删除?");
	if(r == true){
		saveDel();
	}
}

//确认删除
function saveDel(){
	var id = $("input:radio[name='checks']:checked").val();
	$.ajax({
		url: path + "/role/delRole.do",
		async: false,
		data:{"id":id},
		dataType:"text",
		success: function(msg){
			if(msg == 1){
				alert("删除成功");
				window.location.href = path + "/role/queryAdminRoleList.do";
			}else{
				alert("删除失败");
			}
		}
	});
}

//保存
function saveAdd(){
	var roleid = $("#id").val();
	if(roleid == ""){//当ID为空时 保存
		var name = $("#name").val();
		var description = $("#description").val();
		var org = $("#org").val();
		var include_user = $("#include_user").val();
		if(name == "" || description == "" || org == ""){
			alert("请检查必添项!");
			return;
		}
		$.ajax({
			method : "post",
			url: path + "/role/addRole.do",
			async: false,
			data:{"roles":name,
				  "state":0,
				  "description":description,
				  "org_name":org
				 },
			dataType:"text",
			success: function(msg){
				if(msg != 0){
					alert("保存成功");
					$("#id").val(msg);
					closeAdd();
				}else{
					alert("保存失败");
				}
			}
		});
	}else{//当ID不为空时 修改
		var name = $("#name").val();
		var description = $("#description").val();
		var org = $("#org").val();
		if(name == "" || description == "" || org == ""){
			alert("请检查必添项!");
			return;
		}
		var id = $("#id").val();
		$.ajax({//修改
			url: path + "/role/editRole.do",
			async: false,
			data:{"roles":name,
				  "description":description,
				  "org_name":org,
				  "id":id
				 },
			dataType:"text",
			success: function(msg){
				if(msg != 0){
					alert("保存成功");
					closeAdd();
				}else{
					alert("保存失败");
				}
			}
		});
	}
	roleid = $("#id").val();
	if(roleid != ""){//当角色ID不为空时更新所关联的用户信息
		var treeObj = $.fn.zTree.getZTreeObj("userDemo");
		var selected = treeObj.getCheckedNodes(true);
		for(var i=0;i<selected.length;i++){
			$.ajax({//保存修改角色
				url: path + "/role/saveUserRoles.do",
				async: false,
				data:{"userid":selected[i].id,"rolesids":roleid},
				dataType:"text",
				success: function(msg){
					//alert(msg);
				}
			});
		}
	}
	window.location.href = path + "/role/queryAdminRoleList.do";
}

function closeAdd(){
	layer.closeAll();
}


/**组织结构下拉菜单 start********************************************/
var setting = {
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
		onCheck: onCheck
	}
};

function onClick(e, treeId, treeNode) {
	if(treeNode.name == "山东省人民检察院"){
		return false;
	}
	var cityObj = $("#org");
	hideMenu();
	cityObj.attr("value", treeNode.name);
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("unitDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	$("#org").val(v);
}

function showMenu() {
	var cityObj = $("#org");
	var cityOffset = $("#org").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "org" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

$(function($){
	$.ajax({
		type:"post",
		url : path + '/org/queryOrgTree.do',
		dataType:'json',
		async: false,
		success : function (resp){
           	$.fn.zTree.init($("#unitDemo"), setting, eval(resp));
           	zTree_Menu = $.fn.zTree.getZTreeObj("unitDemo");
		}
	});
})
/**组织结构下拉菜单 end********************************************/


/**包含人员下拉菜单 start*****************************************/
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
		onCheck: onCheckUser
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
		url : path + '/user/queryUserList.do',
		dataType:'json',
		async: false,
		success : function (resp){
           	$.fn.zTree.init($("#userDemo"), setting_user, eval(resp));
           	zTree_Menu = $.fn.zTree.getZTreeObj("userDemo");
		}
	});
})
/**包含人员下拉菜单 end********************************************/

function query(){
	$("#roles").val($("#role_name").val());
	$("#page").submit();
}