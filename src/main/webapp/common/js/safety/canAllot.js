$(document).ready(function() {
	//加载所有角色
	var setting = {
		view: {
			showLine: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
	$.ajax({
		url: "../role/queryAllRoles.do",
		async: false,
		data:{"state":0},
		dataType:"json",
		success: function(msg){
			$.fn.zTree.init($("#roles_ztree"), setting, eval(msg));
		}
	});
});

//角色单击事件
function zTreeOnClick(event, treeId, treeNode) {
	$("#save").attr("name","role");
	var setting = {
		view: {
			showLine: false
		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	$("#id").val(treeNode.id);
	$("#rolename").html(treeNode.name);
	$.ajax({
		url: "../roleResource/getRoleResourceListByRoleId.do",
		async: false,
		data:{"roleid":treeNode.id},
		dataType:"json",
		success: function(msg){
			$.fn.zTree.init($("#resource_ztree"), setting, eval(msg));
		}
	});
}

//保存
function saveRoleResource(name){
	var treeObj = $.fn.zTree.getZTreeObj("resource_ztree");
	var nodes = treeObj.getCheckedNodes();
	var radioIdsVal="";
	if(nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].id!=null&&nodes[i].id!=undefined){
				if(nodes.length==1){
					radioIdsVal=nodes[i].id;
				}else if((i+1)==nodes.length){
					radioIdsVal+=nodes[i].id;
				}else{
					radioIdsVal+=nodes[i].id+","
				}
			}
		}
	}
	var roleid = $("#id").val();
	if(roleid == ""){
		return;
	}
	if(name == "role"){
		$.ajax({//保存角色资源
			url: "../roleResource/updateRoleResource.do",
			async: false,
			data:{"roleid":roleid,"resources":radioIdsVal},
			dataType:"text",
			success: function(msg){
				if(msg == 1){
					alert("保存成功");
				}else{
					alert("保存失败");
				}
			}
		});
	}else if(name == "org"){
		$.ajax({//保存角色资源
			url: "../org_res/updateOrgResource.do",
			async: false,
			data:{"orgid":roleid,"resources":radioIdsVal},
			dataType:"text",
			success: function(msg){
				if(msg == 1){
					alert("保存成功");
				}else{
					alert("保存失败");
				}
			}
		});
	}
}