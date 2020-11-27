<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
	<link rel="stylesheet" href="${ctx}/common/css/bootstrap.css" type="text/css" />
	<%--<link rel="stylesheet" href="${ctx}/common/css/uumcss.css" type="text/css" />--%>
	<script type="text/javascript" src="${ctx}/common/js/util/jquery.js" language="javascript"></script>
	<script type="text/javascript" src="${ctx}/common/js/layer/layer.js" language="javascript"></script>
	<script type="text/javascript" src="${ctx}/common/js/index/top.js" language="javascript"></script>
	<script type="text/javascript" src="${ctx}/common/js/index/menu.js" language="javascript"></script>
	<link rel="stylesheet" href="${ctx}/common/css/menu.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/static/plugin/layui-v2.4.3/layui/css/layui.css" media="all">
	<style type="text/css">
		html,body{
			width: 100%;
			height: 100%;
			background-color:white;
		}
		.layer_notice{
			float: left;
			height: 75px;
			width: 290px;
			overflow: hidden;
			padding: 10px;
		}
		.layui-layer-title{
			background-color: #1E9FFF;
			color: #f3ebeb;
		}
	</style>
</head>
<body>
	<div
		style="background: url(${ctx}/common/images/top/head_bg_black.jpg); background-repeat: repeat-x; height: 60px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td rowspan="1" colspan="1">
                <img width="454" height="60" src="${ctx}/common/images/top/logo.png" />
            </td>
            <td width="95" rowspan="1" colspan="1" onclick="toDoPage();">
                <a href="javascript:void(0);" style="cursor: hand">
                    <img width="90" height="37" src="${ctx}/common/images/top/fhdbsx.jpg" border="0" />
                </a>
                <div>
                    <a style="cursor: hand;color: #fff;font-size:14px;text-decoration:none">返回待办页面</a>
                </div>
            </td>
            <td width="100" rowspan="1" colspan="1">
                <a href="javascript:void(0);" style="cursor: hand" onmouseout="hiddenDiv();" onclick="displayDiv();">
                    <img width="100" height="60" src="${ctx}/common/images/top/zxtqh.png" border="0" />
                </a>
            </td>
            <td width="85" rowspan="1" colspan="1" style="text-align: center" onclick="toHome()">
                <a href="javascript:void(0);" style="cursor: hand">
                    <img width="85" height="37" src="${ctx}/common/images/top/fh.png" border="0" />
                </a>
                <div>
                    <a style="cursor: hand;color: #fff;font-size:14px;text-decoration:none" >返回主页</a>
                </div>
            </td>
            <td width="85" rowspan="1" colspan="1" style="text-align: center">
                <a href="javascript:void(0)" onclick="onlinelist(1)" title="单击，查看在线人员详细信息" href="javascript:void(0);" style="cursor: hand">
                    <img width="85" height="37" src="${ctx}/common/images/top/rs.png" border="0" />
                </a>
                <div>
                    <a href="javascript:void(0)" onclick="onlinelist()" title="单击，查看在线人员详细信息" style="cursor: hand;color: #fff;font-size:14px;text-decoration:none">
                        <span id="onlineCount">0</span>人在线
                    </a>
                </div>
            </td>
            <td width="85" rowspan="1" colspan="1" style="text-align: center">
                <a href="javascript:void(0);" onclick="changepwd()" style="cursor: hand">
                    <img width="85" height="37" src="${ctx}/common/images/top/mn.png" border="0" />
                </a>
                <div>
                    <a href="javascript:void(0)" onclick="changepwd()" style="cursor: hand;color: #fff;font-size:14px;text-decoration:none">修改密码</a>
                </div>
            </td>
            <td width="85" rowspan="1" colspan="1" style="text-align: center">
                <a href="javascript:void(0);" style="cursor: hand">
                    <img width="85" height="37" src="${ctx}/common/images/top/bz.png" border="0" />
                </a>
                <div>
                    <a style="cursor: hand;color: #fff;font-size:14px;text-decoration:none">帮助</a>
                </div>
            </td>
        </tr>
    </table>
	</div>
	<!--displayDIV-->
	<ul id="navigation1" style="position: absolute;z-index: 9999; width: 134px; display: none; float: right; top: 1px; right: 305px;" onmouseover="displayDiv();" onmouseout="hiddenDiv();">
	    <li class="xl" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
	        <a href="" style="cursor: hand">
	            <img width="100" height="60" border="0" src="${pageContext.request.contextPath}/common/images/top/zxtqh02.png">
	        </a>
	        <ul class="xl" style="margin-top: -3px;display: none;margin-left:8px;text-align: center">
	            <li class="xt">
	                <a href="http://10.37.0.177:8080" id="bwxt" style="text-decoration: none">
	                    <img title="办文系统" align="center" src="${pageContext.request.contextPath}/common/images/top/bwxt.png" border="0" />
	                    <div style="font-size:14px;text-decoration:none">办文系统</div>
	                </a>
	            </li>
	            <li class="xt">
	                <a href="http://10.37.0.177:28080" id="bsxt" style="text-decoration: none">
	                    <img title="办事系统" align="center" src="${pageContext.request.contextPath}/common/images/top/bsxt.png" border="0" />
	                    <div style="font-size:14px;text-decoration:none">办事系统</div>
	                </a>
	            </li>
	            <li class="xt">
	                <a href="" id="daxt" onclick="forwardSystem(daxt);" style="text-decoration: none">
	                    <img title="档案系统" align="center" src="${pageContext.request.contextPath}/common/images/top/daxt.png" border="0" />
	                    <div style="font-size:14px;text-decoration:none">档案系统</div>
	                </a>
	            </li>
	            <li class="xt">
	                <a href="" id="jstx" onclick="forwardSystem(jstx);" style="text-decoration: none">
	                    <img title="即时通讯" align="center" src="${pageContext.request.contextPath}/common/images/top/jstx.png" border="0" />
	                    <div style="font-size:14px;text-decoration:none">即时通讯</div>
	                </a>
	            </li>
	            <li class="xt">
	                <a href="http://10.37.0.177:28081" id="yjxt" style="text-decoration: none">
	                    <img title="邮件系统" align="center" src="${pageContext.request.contextPath}/common/images/top/yjxt.png" border="0" />
	                    <div style="font-size:14px;text-decoration:none">邮件系统</div>
	                </a>
	            </li>
	            <li class="xtl">
	                <a href="http://10.37.0.177/a" id="xxfb" style="text-decoration: none">
	                    <img title="信息发布" align="center" src="${pageContext.request.contextPath}/common/images/top/xxfb.png" border="0" />
	                    <div style="font-size:14px;text-decoration:none">信息发布</div>
	                </a>
	            </li>
	        </ul>
	    </li>
	</ul>
	<!--更改密码-->
	<div style="display: none" id="changepwd">
		<div style="margin-top: 20px; text-align: center">新密码为4-10位字母、数字或下划线</div>
		<div class="text-center" style="margin-top: 20px;">
			<table class="table">
				<tr>
					<td width="200" style="text-align: right">账号：</td>
					<td style="text-align: left"><input type="text"
						class="input-sm" /></td>
				</tr>
				<tr>
					<td width="200" style="text-align: right">旧密码：</td>
					<td style="text-align: left"><input type="text"
						class="input-sm" /></td>
				</tr>
				<tr>
					<td width="200" style="text-align: right">新密码：</td>
					<td style="text-align: left"><input type="text"
						class="input-sm" /> <a href=""
						class="editbutton editbtn editmedium">校验密码</a></td>
				</tr>
				<tr>
					<td width="200" style="text-align: right">确认新密码：</td>
					<td style="text-align: left"><input type="text"
						class="input-sm" /></td>
				</tr>
				<tr>
					<td colspan="2"><a href="" style="text-align: center"
						class="editbutton editbtn editmedium">提 交</a></td>
				</tr>
			</table>
		</div>
	</div> 

	<!--在线列表-->
	<div style="display: none" id="onlinelist">
		<table class="table text-center">
			<thead>
				<tr class="success">
					<td>部门</td>
					<td>用户名</td>
					<td>登陆时间</td>
				</tr>
			</thead>
			<tbody id="onlinebody">
				
			</tbody>
		</table>
		<div class="pull-left" style="margin-right: 10px;">
			<img alt="" id="first" class="page" src="${ctx}/common/images/page/page-first-disabled-new.png" onclick="page('first');" />
			<img alt="" id="back" class="page" src="${ctx}/common/images/page/page-prev-disabled-new.png" onclick="page('back')" />
			<input type="text" size="2" style="text-align: center;" id="cpage" />/<span id="totalPage"></span>页
			<img alt="" id="next" class="page" src="${ctx}/common/images/page/page-next-new.png" onclick="page('next')" />
			<img alt="" id="last" class="page" src="${ctx}/common/images/page/page-last-new.png" onclick="page('last')" />
		</div>
		<div class="pull-right" style="margin-right: 10px;">
			本页<span>30</span>条记录&nbsp;
			共<span id="totalCount"></span>条记录
		</div>
	</div>
	<div id="menu"
		style="width: 15%; height: 100%; background-color: #eafeff; float: left;border-right: 2px solid #f5f5f5;overflow-y: auto;">
		<div class="">
	        <ul id="ec_ztree" class="ztree" style="">
	        	
	        </ul>
	        <input id="treeData" type="hidden" value='${treeList}'/>
		</div>
	</div>
	<iframe class="" id="main_iframe" name="main_iframe" src="" style="width: 85%; height: 100%; float: right;" > 您的浏览器不支持嵌入式框架，或者当前配置为不显示嵌入式框架。</iframe>

</body>
<script>
	openwin()
	function openwin(){
		layer.closeAll();
		index = layer.open({
			type: 1,
			title: '待办消息',
			closeBtn: 1, //不显示关闭按钮
			shade: false,
			area: ['300px', '200px'],
			offset: 'rb',
		//	time: 5000, //2秒后自动关闭
			anim: 2,
			content: $('.layer_notice'), //iframe的url，no代表不显示滚动条
			end: function(){ //此处用于演示
				$('.layer_notice').hide();
				layer.closeAll();
			}
		});
	}
</script>
</html>