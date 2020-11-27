/**
 * 资源管理
 */
$(document).ready(function() {
	$("#orglist").height($(window).height() - 100 + 'px');
	$(window).resize(function() {
		$("#orglist").height($(window).height() - 105 + 'px');
	})
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : zTreeOnClick
		}
	};
	$.fn.zTree.init($("#ec_ztree"), setting, eval($("#treeData").val()));
	zTree_Menu = $.fn.zTree.getZTreeObj("ec_ztree");
});

function clearData(){
	$("#id").val("");
	$("#name").val("");
	$("#link").val("");
//	$("#state").find("option[value='1']").attr("selected", true);
	$("#parentId").val("");
	$("#sys").val("");
}

function zTreeOnClick(event, treeId, treeNode) {
	clearData();
	if(treeNode.name == "办事子系统"){
		$("#sys").val("0");
		return;
	}else if(treeNode.name == "系统管理控制台"){
		$("#sys").val("1");
		return;
	}
	$.ajax({
		type : "POST",
		url : "toEditResource.do",
		data : {
			"id" : treeNode.id,
		},
		async : false,
		success : function(msg) {
			$("#id").val(msg.id);
			$("#name").val(msg.cnName);
			$("#link").val(msg.link);
			//$("#orderNum").val(msg.orderNum);
//			if (msg.state == 1) {// 启用
//				$("#state").find("option[value='1']").attr("selected", true);
//			} else if (msg.state == 0) {// 禁用
//				$("#state").find("option[value='0']").attr("selected", true);
//			}
			$("#parentId").val(msg.parentId);
			$("#sys").val(msg.sys);
		}
	});
};

// 新增
function add() {
	var id = $("#id").val();
	var parentId = $("#parentId").val();
	var name = $("#name").val();
	var link = $("#link").val();
//	var state = $("#state").val();
//	var orderNum = $("#orderNum").val();
	var sys = $("#sys").val();
	if (name == "") {
		alert("请检查必填项!");
		return;
	}
	if(link == ""){
		link = "#";
	}
	$.ajax({
		type : "POST",
		url : "addResource.do",
		data : {
			"id" : id,
			"cnName" : name,
			"link" : link,
			"state" : 1,
			//"orderNum" : orderNum,
			"parentId" : parentId,
			"sys" : sys
		},
		async : false,
		success : function(msg) {
			window.location.href = "queryResourceList.do";
		}
	});
}

// 删除
function del() {
	var id = $("#id").val();
	$.ajax({
		type : "POST",
		url : "delResource.do",
		data : {
			"id" : id
		},
		async : false,
		success : function(msg) {
			window.location.href = "queryResourceList.do";
		}
	});
}

// 保存
function save() {
	var id = $("#id").val();
	var name = $("#name").val();
	var parentId = $("#parentId").val();
	var link = $("#link").val();
//	var state = $("#state").val();
	//var orderNum = $("#orderNum").val();
	var sys = $("#sys").val();
	if (name == "" || link == "") {
		alert("请检查必填项!");
		return;
	}
	$.ajax({
		type : "POST",
		url : "editResource.do",
		data : {
			"id" : id,
			"cnName" : name,
			"link" : link,
			"state" : 1,
			//"orderNum" : orderNum,
			"parentId" : parentId,
			"sys" : sys
		},
		async : false,
		success : function(msg) {
			var treeObj = $.fn.zTree.getZTreeObj("ec_ztree");
		    //获取节点
			var nodes = treeObj.getSelectedNodes();
			if (nodes.length>0) 
			{
			    for(var i=0;i<nodes.length;i++) {
			        // 更新根节点中第i个节点的名称
			        nodes[i].name = name;
					treeObj.updateNode(nodes[i]);
			    }
			}
		}
	});
}

// 上移
function up() {
	var zTree = $.fn.zTree.getZTreeObj("ec_ztree");
	nodes = zTree.getSelectedNodes();
	treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("请先选择一个节点");
		return;
	}
	if (treeNode.isFirstNode) {
		alert("无法上移！");
		return;
	}
	var prenode = treeNode.getPreNode();
	zTree.moveNode(prenode, treeNode, "prev");
	var childrens = treeNode.getParentNode().children;
	saveOrder(childrens);
}

// 下移
function down() {
	var zTree = $.fn.zTree.getZTreeObj("ec_ztree");
	nodes = zTree.getSelectedNodes();
	treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("请先选择一个节点");
		return;
	}
	if (treeNode.isLastNode) {
		alert("无法下移！");
		return;
	}
	var prenode = treeNode.getNextNode();
	zTree.moveNode(prenode, treeNode, "next");
	var childrens = treeNode.getParentNode().children;
	saveOrder(childrens);
}

function saveOrder(childrens){
	for(var i=0;i<childrens.length;i++){
		$.ajax({
			url: "updateOrder.do",
			data:{"id":childrens[i].id,"orderNum":(i+1)},
			async: false,
			dataType:"json",
			success: function(msg){
			}
		});
	}
}