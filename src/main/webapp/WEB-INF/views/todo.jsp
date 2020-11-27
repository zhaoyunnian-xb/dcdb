<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE" />
<title>待办</title>
<link rel="stylesheet" href="${ctx}/common/css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/common/css/uumcss.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/common/css/common.css" type="text/css" />
<script type="text/javascript" src="${ctx}/common/js/util/jquery.js"></script>
<script type="text/javascript" src="${ctx}/common/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/common/js/util/publicJs.js"></script>
<link rel="stylesheet" href="${ctx}/static/plugin/layui-v2.4.3/layui/css/layui.css">
<script src="${ctx}/static/plugin/layui-v2.4.3/layui/layui.all.js"></script>
<%--<script type="text/javascript" src="${ctx}/common/js/user/businessUser.js"></script>--%>
<script type="text/javascript" src="${ctx}/common/js/ztree/jquery.ztree.all-3.5.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/ztree/ztree.js" language="javascript"></script>
<link rel="stylesheet" href="${ctx}/common/css/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/common/css/zTree/css/demo.css" type="text/css"/>
	<style>
		.layui-table th{
			background-color:#81a2ef;
			color: #fff;
		}
		a:link,a:visited,a:hover,a:active {
			text-decoration:none;  /*超链接无下划线*/
			color:blue;
		}
		.layui-table[lay-even] tr:nth-child(even){background-color:#f7f9fd}
		html,body{
			width: 100%;
			height: 100%;
			background-color:white;
		}
		.admin-btn-blue {
			display: inline-block !important;
			font-size: 12px;
			padding: 4px 14px;
			color: #5E8AF1;
			border: 1px dotted #5E8AF1;
			-webkit-border-radius: 14px;
			-moz-border-radius: 14px;
			border-radius: 14px;
			cursor: pointer;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
			margin-right: 8px;
			line-height: 14px;
			margin-bottom: 5px;
		}

		/*body .demo-class .layui-layer-title{background:#c00; color:#fff; border: none;}*/
		body .demo-class .layui-layer-btn{border-top:1px solid #E9E7E7}
		body .demo-class .layui-layer-btn a{background:#3189C9;}
		body .demo-class .layui-layer-btn .layui-layer-btn1{background:#999;}

		body .demo-class .layui-layer-btn0{
		width: 65px;
		}
		body .demo-class .layui-layer-btn1{
			width: 65px;
		}
	</style>

	<script type="text/html" id="zizeng">
		{{d.LAY_TABLE_INDEX+1}}
	</script>
</head>
<body>
<input type="hidden" id="ajzt" value="${ajzt}">
	<div height="100%" width="100%" class="table">


				<div class="bw_title">
				<c:if test="${ajzt=='1'}">
					-  待办事项
				</c:if>
				<c:if test="${ajzt=='2'}">
					-  在办事项
				</c:if>
				<c:if test="${ajzt=='3'}">
					-  已办事项
				</c:if>
				<c:if test="${ajzt=='4'}">
					-  办结事项
				</c:if>

				</div>
				<div class="bw_content">
					<div class="layui-col-md3 layui-col-xs3">
						<label class="layui-form-label" style="width: 110px;">督办事项</label>
						<div class="layui-input-block">
							<input type="text" name="hymc" id="hymc" class="layui-input" />
						</div>
					</div>
					<div class="layui-col-md3 layui-col-xs3">
						<label class="layui-form-label" style="width: 110px;">审批时间</label>
						<div class="layui-input-block">
							<input type="text" name="bbb" id="bbb" class="layui-input"/>
						</div>
					</div>
					<div class="layui-col-md1 layui-col-xs1" style="margin-left: 50px;">
						<input type="button" class="layui-btn layui-btn-primary " style="background-color:#81a2ef;height: 34px;line-height: 34px;" value="查询"  />
					</div>

					<%--<table class="table">
						<tr>
							<td>&nbsp;</td>
							<td>用户全名</td>
							<td>系统账号</td>
							<td>用户描述</td>
							<td>组织机构</td>
							<td>是否锁定</td>
						</tr>
						<tr>
							<td>1</td>
							<td>111111111</td>
							<td>11111111111111</td>
							<td>111111111</td>
							<td>111111111111</td>
								<td>否</td>
						</tr>
					</table>--%>
					<%--<div style="width: 900px">--%>
					<table id="tabe_db" lay-filter="tabe_db" class="layui-table"></table>
					<%--</div>--%>
				</div>
				<!-- 上一页 下一页 -->
				<div class="pull-left" style="margin-right: 10px;">
				</div>

				
	</div>
	
	<div id="userRoles_dlg" style="display: none">
		<table style="width: 100%">
			<tr>
				<td></td>
				<td>
					<div class="side-bar ml10">
				        <ul id="ec_ztree" class="ztree">
				        	
				        </ul>
					</div>
				</td>
			</tr>
			<tr>
				<td><input type="button" value="保存" onclick="saveUserRoles()" /></td>
				<td><input type="button" value="关闭" /></td>
			</tr>
		</table>
	</div>
	<form class="layui-form" >
		<div id="guidang" style="display: none" class="layui-form-item">
			<label class="layui-form-label" style="width: 90px">归档时间</label>
			<div class="layui-input-block">
				<input type="radio" name="gddate" value="10年" title="10年" checked />
				<input type="radio" name="gddate" value="30年" title="30年" />
				<input type="radio" name="gddate" value="永久" title="永久" />
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block admin-align-right" style="margin-top: 60px;text-align: right; margin-right: 15px;">
					<span class="layui-btn" id="savebtn" onclick="saveGdInfo(this)">保存</span>
					<span class="layui-btn layui-btn-primary" onclick="$('#guidang').hide();layer.closeAll();">关闭</span>
				</div>
			</div>
		</div>
	</form>
<script >
    $(function () {
        layui.use('laydate', function () {
            var laydate = layui.laydate;
                laydate.render({
                    elem: '#bbb',
                    format: 'yyyy-MM-dd',
					range:'~'
                    //theme: 'grid'
            })
        })
        var table = layui.table;
        var tf=true;
        var tf1=true;
        var tf2=true;
        if($("#ajzt").val()=='4'){
            tf=false;

		}
        if($("#ajzt").val()=='5'){
            tf1=false;

        }
        if($("#ajzt").val()=='1' || $("#ajzt").val()=='2'){
            tf2=false;

        }
        table.render({
            elem: '#tabe_db',
            url: '${ctx}/todo/todoTable.do?ajzt=${ajzt}',
            page: true, //开启分页
            even:true,
            limit: 20,
            cols: [[{field: 'jdjmc',  align: 'center', width:80,title: '状态',hide:tf2,templet:function (d){
                    var html="";
                        html+='<img src="${ctx}/common/images/top/liudeng.png" ></img>';
                    return html;
                }},
              			 {field:'zizeng',align: 'center', width:'10%', title: '序号',templet:'#zizeng'},
                //     {field:'zizeng', width:80, title: '序号',fixed: 'left',type:'numbers'},
                    {field: 'jdjmc',  align: 'center', title: '会议议题',width:'25%',templet:function (d){
                        var html="";
                            if(d.nodename=='内勤接收'){
                                html+='<a href="${ctx}/dcdb/index.do?id='+d.id+'&readOnly=1&nodeid='+d.nodeid+'&nodename='+d.nodename+'&ajzt='+d.ajzt+'" >'+d.jdjmc+'</a>';
                            }else if( d.nodename=='承办人办理' && d.ajzt=='1'){
                                html+='<a href="${ctx}/dcdb/index.do?id='+d.id+'&readOnly=1&isReback='+d.isReback+'&nodeid='+d.nodeid+'&nodename='+d.nodename+'&ajzt='+d.ajzt+'" >'+d.jdjmc+'</a>';
                            }else if(d.nodename=='承办人办理' && d.ajzt=='2'){
                                html+='<a href="${ctx}/jshf.do?id='+d.id+'&readOnly=0&isReback='+d.isReback+'&nodeid='+d.nodeid+'&nodename='+d.nodename+'&ajzt='+d.ajzt+'&jdjmc='+d.jdjmc+'" >'+d.jdjmc+'</a>';
                            }else {
                                var readOnly="0";
                                if(d.ajzt=='3' || d.ajzt=='4'){
                                    readOnly="1";
								}

                                html+='<a href="${ctx}/jshf.do?id='+d.id+'&readOnly='+readOnly+'&isReback='+d.isReback+'&nodeid='+d.nodeid+'&nodename='+d.nodename+'&ajzt='+d.ajzt+'&jdjmc='+d.jdjmc+'" >'+d.jdjmc+'</a>';
                            }
                        return html;
						}},
                    {field: 'username', align: 'center', title: '当前操作人',},
                    {field: 'spsj', align: 'center', title: '审批时间',},
                    {field: 'nodename', align: 'center', title: '审批节点',width:'15%'},
             	    {field: 'ajztname', align: 'center', title: '案件状态',width:'15%'},

                    {field: 'ajzt', align: 'center', title: '案件状态',hide:"true"},
                    {field: 'id', align: 'center', title: '主键',hide:"true"},
                   {field: 'gddate', align: 'center', title: '归档时间',hide:tf1},
               		 {field: '', align: 'center', title: '操作', hide:tf,templet:function (d){
               		//归档
							 var id=d.id;
							 var html='<i class="admin-btn-blue active" data-id='+id+' onclick="gdenvent(this)">归档</i>';
							 return html;
					}},
                ]]
        });
    })
    layui.use('table', function () {
        var table = layui.table;
        table.on('tool(tabe_db)', function (obj) {
            var data = obj.data;
            $.ajax({
                url: "${ctx}/todo/todoTableDetail.do",
                type: "post",
                async: false,
                data: {"id": data.id},
                dataType: "json",
                success: function (result) {

                }
			})
        })
    })
	function gdenvent(a){
        var id=$(a).data("id");
		$('#savebtn').attr('data-id',id);
        layui.form.render();
        layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '选择归档时间',
            area: ['500px', '200px'], //宽高
			closeBtn : 0,
            content: $('#guidang'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
	}
function saveGdInfo(obj){
	var id=$(obj).data("id");
	var gddate = $('input[name="gddate"]:checked').val();
	$.ajax({
		url: "${ctx}/new/updateGdById.do",
		type: "post",
		async: false,
		data: {"id":id,"gddate":gddate},
		dataType: "json",
		success: function (result) {
			layer.msg("归档成功",{icon:1},function () {
				//跳转首页
				window.location.href="${ctx}/todo.do?ajzt=5";
				$('#guidang').hide();
			});
		}
	})
}
</script>
	
</body>
</html>