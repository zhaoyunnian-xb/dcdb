function add() {//新增角色
	$("#person").hide();
	clearData();
	layer.open({
		type : 1,
		title : '增加业务角色',
		skin : 'layui-layer-rim', // 加上边框
		area : [ '820px', '570px' ], // 宽高
		content : $('#add_dlg'),
		end: function () {
			window.location.href = path + "/role/queryBusinessRoleList.do";
        }
	});
	$('#add_dlg').show();
}

function clearData(){
	$("#id").val("");
	$("#name_add").val("");
	$("#description_add").val("");
	$("#org_add").val("");
	$("#max_add").val("");
	$("#add_selectable").html("<tr><td><input type='checkbox' id='check1' onclick='checks(this.id)' /></td><td>帐号</td><td>姓名</td><td>组织机构</td></tr>");
	$("#add_selected").html("<tr><td><input type='checkbox' id='check2' onclick='checks(this.id)' /></td><td>帐号</td><td>姓名</td></tr>");
}

function edit() {//编辑角色
	$("#person").show();
	clearData();
	var id = $("input:radio[name='checks']:checked").val();
	if(id == undefined){
		alert("请选择一条记录!");
		return;
	}
	$.ajax({//查询角色信息
		url: path + "/role/queryRoleById.do",
		type:"POST",
		async: false,
		data:{"id":id},
		dataType:"json",
		success: function(msg){
			$("#id").val(msg.id);
			$("#name_add").val(msg.roles);
			$("#description_add").val(msg.description);
			$("#org_add").val(msg.org_name);
			$("#max_add").val(msg.max_person);
		}
	});
	$.ajax({//查询角色信息
		url: path + "/role/queryUsersByRoleId.do",
		type:"POST",
		async: false,
		data:{"roleid":id},
		dataType:"html",
		success: function(msg){
			$("#add_selected").append(msg);
		}
	});
	
	layer.open({
		type : 1,
		title : '修改业务角色',
		skin : 'layui-layer-rim', // 加上边框
		area : [ '820px', '570px' ], // 宽高
		content : $('#add_dlg'),
		end: function () {
			window.location.href = path + "/role/queryBusinessRoleList.do";
        }
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
		type:"POST",
		async: false,
		data:{"id":id},
		dataType:"text",
		success: function(msg){
			if(msg == 1){
				alert("删除成功!");
				window.location.href = path + "/role/queryBusinessRoleList.do";
			}
		}
	});
}


//保存
function saveAdd(){
	var roleid = $("#id").val();
	if(roleid == ""){//当ID为空时 保存
		var name = $("#name_add").val();
		var description = $("#description_add").val();
		var org = $("#org_add").val();
		var max = $("#max_add").val();
		if(name == "" || org == ""){
			alert("请检查必添项!");
			return;
		}
		if(max == ""){
			max = 0;
		}
		$.ajax({
			type:"POST",
			url: path + "/role/addRole.do",
			async: false,
			data:{"roles":name,
				  "description":description,
				  "org_name":org,
				  "max_person":max
				 },
			dataType:"text",
			success: function(msg){
				$("#id").val(msg);
				$("#person").show();
			}
		});
	}else{
		var name = $("#name_add").val();
		var description = $("#description_add").val();
		var org = $("#org_add").val();
		var max = $("#max_add").val();
		if(name == "" || org == ""){
			alert("请检查必添项!");
			return;
		}
		if(max == ""){
			max = 0;
		}
		var id = $("#id").val();
		$.ajax({//修改
			url: path + "/role/editRole.do",
			type:"POST",
			async: false,
			data:{"roles":name,
				  "description":description,
				  "org_name":org,
				  "max_person":max,
				  "id":id
				 },
			dataType:"text",
			success: function(msg){
			}
		});
	}
	
	if(roleid != ""){//当角色ID不为空时更新所关联的用户信息
		var selected = $("#add_selected input[name='r']");
		var ids = "";
		for(var i=0;i<selected.length;i++){
			$.ajax({//保存修改角色
				url: path + "/role/saveUserRoles.do",
				type:"POST",
				async: false,
				data:{"userid":selected[i].id,"rolesids":roleid},
				dataType:"text",
				success: function(msg){
					
				}
			});
		}
	}
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
	var cityObj = $("#org_add");
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
	$("#org_add").val(v);
}

function showMenu() {
	var cityObj = $("#org_add");
	var cityOffset = $("#org_add").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "org_add" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

$(function($){
	$.ajax({
		type:"POST",
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

//根据组织结构查询用户列表
function queryUser(){
	$.ajax({
		type:"POST",
		url : path + '/user/queryUserByUnit.do',
		data : {"units":$("#org_add").val()},
		dataType:'html',
		async: false,
		success : function (resp){
           	$("#add_selectable").append(resp);
		}
	});
}

//对应人员 全选
function checks(id){
	if(id == "check1"){
		var ch = $("#check1").prop('checked');
		$("#add_selectable").find("input[type='checkbox']").prop("checked",ch);
	}else{
		var ch = $("#check2").prop('checked');
		$("#add_selected").find("input[type='checkbox']").prop("checked",ch);
	}
}

//对应人员中 选择人员
function changeUser(){
	var rows = document.getElementById("add_selectable").rows;
	var a = document.getElementsByName("r");
	var table = document.getElementById("add_selectable");
	
	for(var i=0;i<a.length;i++){
		if(a[i].checked){
		var row = a[i].parentElement.parentElement.rowIndex;
			$("#add_selected").append("<tr>"+rows[row].innerHTML+"</tr>");
			$("#add_selectable").find("tr").eq(row).remove();
			i--;
		}
	}
}

//对应人员中 取消选择人员
function unChangeUser(){
	var rows = document.getElementById("add_selected").rows;
	var a = document.getElementsByName("r");
	var  table = document.getElementById("add_selected");
	
	for(var i=0;i<a.length;i++){
		if(a[i].checked){
		var row = a[i].parentElement.parentElement.rowIndex;
			$("#add_selectable").append("<tr>"+rows[row].innerHTML+"</tr>");
			$("#add_selected").find("tr").eq(row).remove();
			i--;
		}
	}
}

function query(){
	$("#roles").val($("#role_name").val());
	$("#page").submit();
}

function closeAdd(){
	layer.closeAll();
}

