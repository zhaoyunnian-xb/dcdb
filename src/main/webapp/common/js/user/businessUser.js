$(function($){
	$("#orglist").height($(window).height() - 100 + 'px');
	$(window).resize(function() {
		$("#orglist").height($(window).height() - 105 + 'px');
	})
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
		type:"post",
		url : path + '/org/queryOrgTree.do',
		dataType:'json',
		async: false,
		success : function (resp){
           	$.fn.zTree.init($("#untis_ztree"), setting, eval(resp));
           	zTree_Menu = $.fn.zTree.getZTreeObj("untis_ztree");
		}
	});
})

function zTreeOnClick(event, treeId, treeNode){
	$("#unitid_sel").val(treeNode.id);
	$("#cpage").val(1);
	$("#page").submit();
}

function userRoles(){
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
	var id = $("input:radio[name='checks']:checked").val();
	$.ajax({//查询所有角色
		url: path + "/role/queryRoleByUserId.do",
		async: false,
		data:{"id":id},
		dataType:"json",
		success: function(msg){
			$.fn.zTree.init($("#ec_ztree"), setting, eval(msg));
		}
	});
	
	layer.open({
		type : 1,
		title : '分配角色',
		skin : 'layui-layer-rim', // 加上边框
		area : [ '820px', '570px' ], // 宽高
		content : $('#userRoles_dlg'),
	});
	$('#userRoles_dlg').show();
}

function saveUserRoles(){
	var treeObj = $.fn.zTree.getZTreeObj("ec_ztree");
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
	var userid = $("input:radio[name='checks']:checked").val();
	$.ajax({//保存修改角色
		url: path + "/role/saveUserRoles.do",
		async: false,
		data:{"userid":userid,"rolesids":radioIdsVal},
		dataType:"text",
		success: function(msg){
			alert(msg);
		}
	});
}

//查询
function query(){
	$("#username").val($("#username_sel").val());
	$("#page").submit();
}