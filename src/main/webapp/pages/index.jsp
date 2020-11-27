<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/plugin/layui-v2.4.3/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/common/css/global.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/common/css/adminstyle.css" media="all">


</head>
<body>
<input type="hidden" id="dbPath" value="${path}"/>
<input type="hidden" id="flag" value="${flag}"/>
<div class="layui-layout layui-layout-admin" id="layui_layout">
    <!-- 顶部区域 -->
    <div class="layui-header header header-admin">
        <div class="layui-main">
            <!-- logo区域 -->
            <div class="admin-logo-box">
                <a class="logo" href="#" title="logo"><img src="/common/images/top/logo.jpg" alt=""></a>
            </div>

            <!-- 右侧导航 -->
            <ul class="layui-nav larry-header-item">
                <li class="layui-nav-item">
                    <a href="">
                        <img src="/common/images/top/fhdbsx.jpg" alt="">
                        <p>返回待办页面</p>
                    </a>

                </li>
                <li class="layui-nav-item first">
                    <a href="javascript:;">
                        <img src="/common/images/top/zxtqh.png" alt="">
                        <%--<span class="layui-nav-more"></span>--%>
                    </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="http://141.112.1.9:8080">
                                <img src="/common/images/top/bwxt.png" alt="">
                                <div>办文系统</div>
                            </a>
                        </dd>
                        <dd>
                            <a href="http://141.112.1.9:28080">
                                <img src="/common/images/top/bsxt.png" alt="">
                                <div>办事系统</div>
                            </a>
                        </dd>
                        <dd>
                            <a href="http://141.112.1.9:8085/pages/getUser.html">
                                <img src="/common/images/top/daxt.png" alt="">
                                <div>档案系统</div>
                            </a>
                        </dd>
                        <dd>
                            <a href="http://141.112.1.9:8085/pages/getUser.html">
                                <img src="/common/images/top/jstx.png" alt="">
                                <div>即时通讯</div>
                            </a>
                        </dd>
                        <dd>
                            <a href="http://141.112.1.9:28081">
                                <img src="/common/images/top/yjxt.png" alt="">
                                <div>邮件系统</div>
                            </a>
                        </dd>
                        <dd>
                            <a href="http://141.112.1.9/a">
                                <img src="/common/images/top/xxfb.png" alt="">
                                <div>信息发布</div>
                            </a>
                        </dd>

                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <img src="/common/images/top/fh.png" alt="">
                        <p>返回主页</p>
                    </a>
                </li>
                <li class="layui-nav-item">
                    <a href="login.html">
                        <img src="/common/images/top/rs.png" alt="">
                        <p>0人在线</p>
                    </a>
                </li>
                <li class="layui-nav-item">
                    <a href="login.html">
                        <img src="/common/images/top/mn.png" alt="">
                        <p>修改密码</p>
                    </a>
                </li>
                <li class="layui-nav-item" onclick="down()" style="cursor: pointer">
                    <img src="/common/images/top/bz.png" alt="">
                    <p>操作手册</p>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧侧边导航开始 -->
    <div class="layui-side layui-side-bg layui-larry-side" id="larry-side">
        <div class="layui-side-scroll" id="larry-nav-side" lay-filter="side">

            <!-- 左侧菜单 -->
            <ul class="layui-nav layui-nav-tree">
                <!-- 年度任务督办 -->
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <span>年度工作任务督办</span>
                        <em class="layui-nav-more"></em>
                    </a>
                    <dl class="layui-nav-child">
                        <c:if test="${username=='罗艳'}">
                            <dd>
                                <a href="javascript:;" data-url="/ndrw/index.do">
                                    <span>工作任务发起</span>
                                </a>
                            </dd>

                        </c:if>
                        <c:if test="${username=='罗艳' || username=='孟宪伟'}">
                            <dd>
                                <a href="javascript:;" data-url="/pages/ndgzrw/bmrwlist_bgs.jsp">
                                    <span>待办事项</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="/pages/ndgzrw/tbjdlist.jsp">
                                    <span>填报进度</span>
                                </a>
                            </dd>
                        </c:if>

                        <c:if test="${username!='罗艳' && username!='孟宪伟'}">
                            <dd>
                                <a href="javascript:;" data-url="/pages/ndgzrw/bmrwlist.jsp">
                                    <span>正在办理事项</span>
                                </a>
                            </dd>

                        </c:if>

                        <%--  <dd>
                              <a href="javascript:;" data-url="/pages/ndgzrw/yblist.jsp">
                                  <span>已办事项</span>
                              </a>
                          </dd>--%>


                        <dd>
                            <a href="javascript:;" data-url="${ctx}/Tjtable/jdIndex.do">
                                <span>季度台账</span>
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="${ctx}/Tjtable/ndIndex.do">

                                <span>年度台账</span>
                            </a>
                        </dd>

                        <dd>
                            <a href="javascript:;" data-url="${ctx}/Tjtable/tzgsIndex.do">

                                <span>台账公示</span>
                            </a>
                        </dd>
                    </dl>
                </li>

                <!-- 领导批示流程督办 -->

                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <span>领导批示督办</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="/db/index.do?lclx=lzps">
                                    <span>待办列表</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="/yb/index.do?lclx=lzps">
                                    <span>已办列表</span>
                                </a>
                            </dd>
                            <c:if test="${username=='刘超'}">
                            <dd>
                                <a href="javascript:;" data-url="/db/zfIndex.do?lclx=lzps">
                                    <span>转办列表</span>
                                </a>
                            </dd>
                            </c:if>
                            <c:if test="${username=='罗艳'|| username=='孙业鹏'}">
                                <dd>
                                    <a href="javascript:;" data-url="${ctx}/db/gdIndex.do?lclx=lzps">
                                        <span>办结列表</span>
                                    </a>
                                </dd>
                                <dd>
                                    <a href="javascript:;" data-url="${ctx}/Tjtable/tjIndex.do?lclx=lzps">
                                        <span>统计报表</span>
                                    </a>
                                </dd>
                            </c:if>
                        </dl>
                    </li>
                    <!-- 党组检办督办 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <span>党组决策事项督办</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="/db/index.do?lclx=dzjb">
                                    <span>待办列表</span>
                                </a>
                            </dd>

                            <dd>
                                <a href="javascript:;" data-url="/yb/index.do?lclx=dzjb">
                                    <span>已办列表</span>
                                </a>
                            </dd>
                            <c:if test="${username=='罗艳'|| username=='孙业鹏'}">
                                <dd>
                                    <a href="javascript:;" data-url="${ctx}/db/gdIndex.do?lclx=dzjb">
                                        <span>办结列表</span>
                                    </a>
                                </dd>
                                <dd>
                                    <a href="javascript:;" data-url="${ctx}/Tjtable/tjIndex.do?lclx=dzjb">
                                        <span>统计报表</span>
                                    </a>
                                </dd>
                            </c:if>
                        </dl>
                    </li>
                     <!-- 交办事项督办 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <span>交办事项督办</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="/db/index.do?lclx=jbsx">
                                    <span>待办列表</span>
                                </a>
                            </dd>

                            <dd>
                                <a href="javascript:;" data-url="/yb/index.do?lclx=jbsx">
                                    <span>已办列表</span>
                                </a>
                            </dd>
                            <c:if test="${username=='罗艳'|| username=='孙业鹏'}">
                                <dd>
                                    <a href="javascript:;" data-url="${ctx}/db/gdIndex.do?lclx=jbsx">
                                        <span>办结列表</span>
                                    </a>
                                </dd>
                                <dd>
                                    <a href="javascript:;" data-url="${ctx}/Tjtable/tjIndex.do?lclx=jbsx">
                                        <span>统计报表</span>
                                    </a>
                                </dd>
                            </c:if>
                        </dl>
                    </li>
                    <!-- 上级重要决策督办 -->
                <c:if test="${username=='罗艳' || username=='孙业鹏'}">
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <span>上级重要决策督办</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="/db/index.do?lclx=zyjc">
                                    <span>待办列表</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="/yb/index.do?lclx=zyjc">
                                    <span>已办列表</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="/Tjtable/sjjcsxRtIndex.do">
                                    <span>统计报表</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>
                <c:if test="${username=='罗艳' || username=='孙业鹏'}">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <span>系统管理</span>
                        <em class="layui-nav-more"></em>
                    </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="reback/int.do">
                                <span>退回管理</span>
                            </a>
                        </dd>
                    </dl>
                </li>
                </c:if>

                <!-- 系统管理 -->
            <!-- <li class="layui-nav-item">
                   <a href="javascript:;">
                       <span>系统管理</span>
                       <em class="layui-nav-more"></em>
                   </a>
                   <dl class="layui-nav-child">
                       <dd>
                           <a href="javascript:;" data-url="/pages/xtgl/roleList.jsp">
                               <span>角色管理</span>
                           </a>
                       </dd>

                       <dd>
                           <a href="javascript:;" data-url="/pages/xtgl/roleUserList.jsp">
                               <span>用户管理</span>
                           </a>
                       </dd>
                       <dd>
                           <a href="javascript:;" data-url="/pages/xtgl/roleNode.jsp">
                               <span>用户节点管理</span>
                           </a>
                       </dd>
                         <dd>
                             <a href="javascript:;" data-url="/pages/xtgl/lbpz.jsp">
                                 <span>配置管理</span>
                             </a>
                         </dd>

                   </dl>
                </li> -->
            </ul>
        </div>
    </div>

    <!-- 左侧侧边导航结束 -->

    <!-- 右侧主体内容 -->
    <div class="layui-body" id="larry-body" style="bottom: 0;border-left: solid 2px #2299ee;">
        <iframe class="larry-iframe" data-id='0' src="/pages/home.jsp?name=${username}" id="iframe" frameborder="0" scrolling="auto" style="width: 100%;height: 99%;"></iframe>
    </div>
</div>
<!-- 加载js文件-->
<script type="text/javascript" src="${ctx}/static/plugin/jquery/jquery-1.9.1.min.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/static/plugin/layui-v2.4.3/layui/layui.all.js"
        language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/larry.js"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/index.js"></script>
<script>

    function down() {
        window.location.href = encodeURI("index/download.do?fileName=督查督办系统操作手册V1.0.doc");
    }

    $(function () {
        if ($("#flag").val() == 'mh') {
            $("#iframe").attr("src", encodeURI($("#dbPath").val()));
        }

    })

</script>

</body>
</html>