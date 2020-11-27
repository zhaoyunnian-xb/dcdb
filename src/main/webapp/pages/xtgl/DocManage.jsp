<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/4/9
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp"></jsp:include>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">文件管理</div>
                <div class="layui-card-body">
                  <%--  <table id="demo" lay-lay-filter="test"></table>--%>
                   <table  class="layui-table" lay-filter="demo">
                        <thead>
                        <tr>
                            <th lay-data="{field:'username', width:100}">序号</th>
                            <th lay-data="{field:'username1', width:100}">文件名称</th>
                            <th lay-data="{field:'username2', width:100}">备注</th>
                            <th lay-data="{field:'username3', width:100}">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>督查督办系统操作手册.doc</td>
                            <td>第一版本</td>
                            <td><span class="layui-btn layui-btn-sm" onclick="queryBt('督查督办系统操作手册V1.0.doc')" >下载</span></td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>谷歌浏览器</td>
                            <td>xp系统版本</td>
                            <td><span class="layui-btn layui-btn-sm" onclick="queryBt('Chromestable for xp.exe')" >下载</span></td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>谷歌浏览器</td>
                            <td>windows7系统版本</td>
                            <td><span class="layui-btn layui-btn-sm"  onclick="queryBt('ChromeStandalone_62.0.3202.62_Setup.exe')">下载</span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    //执行渲染
/*    layui.use('demo', function(){
        var table = layui.table;
        //第一个实例
        table.render();
    });*/
    function queryBt(a) {
        window.location.href=encodeURI("${ctx}/index/download.do?fileName="+a);
    }
</script>
<jsp:include page="${ctx}/pages/footer.jsp"></jsp:include>
