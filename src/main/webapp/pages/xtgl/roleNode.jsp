<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">用户节点状态</div>
                <div class="layui-card-body">
                    <div class="table">
                        <div class="">
                            <div class="layui-col-sm6 layui-col-md3">
                                业务类型：
                                <div class="layui-input-inline">
                                    <input type="text" name="yw" id="yw" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                角色名称：
                                <div class="layui-input-inline">
                                    <input type="text" name="js" id="js" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                节点：
                                <div class="layui-input-inline">
                                    <input type="text" name="yh" id="yh" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                <span class="layui-btn layui-btn-primary " onclick="ndrwquery();">
                                    查询
                                </span>
                                <span class="layui-btn  layui-btn-normal" onclick="xinzeng();">
                                    新增用户节点状态
                                </span>
                            </div>

                            <table id="tabe_db" lay-filter="tabe_filter" class="layui-table"></table>
                        </div>
                        <!-- 上一页 下一页 -->
                        <div class="pull-left" style="margin-right: 10px;">
                        </div>
                    </div>


                    <form class="layui-form" id="zynrxinzengForm">
                        <div id="jueseDiv" style="display: none; margin: 15px ">
                            <div class="layui-form" style="margin-top: 10px;">
                                <label class="layui-form-label" style="width: 120px;">请选择业务类型：</label>
                                <div class="layui-input-inline">
                                    <select name="ywtype" id="ywtype" lay-filter='ywtype'>
                                        <option value="归档">归档</option>
                                        <option value="编发要情">编发要情</option>
                                        <option value="一般性工作批示">一般性工作批示</option>
                                        <option value="专项督办省院">专项督办省院</option>
                                        <option value="专项督办市院">专项督办市院</option>
                                        <option value="党组检办">党组检办</option>
                                    </select>

                                </div>
                            </div>
                            <div class="layui-form" style="margin-top: 10px;">
                                <label class="layui-form-label" style="width: 120px;">请选择角色名称：</label>
                                <div class="layui-input-inline">
                                    <select name="rolename" id="rolename" lay-filter='rolename'>
                                    </select>

                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">节点ID：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="nodeid" id="nodeid" placeholder="请输入" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">对应状态：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="status" id="status" placeholder="请输入" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block ">
                                    <span class="layui-btn layui-btn-sm layui-btn-normal" id="zynrsavebtn"
                                          onclick="savezynrxzInfo()">保存</span>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var index1 = "";
    var table = layui.table;
    var form =layui.form;
    $(function () {
        //年份时间插件
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#nf',
                format: 'yyyy',
                type:'year'
            })
        })
        ndrwquery();

        form.render();

    })
    form.on('select(ywtype)', function (data) {
        queryRole(data.value);

    })
    //年度任务总表格返回数据
    function ndrwquery(){
        var ywtype = $("#yw").val();
        var rolename = $("#js").val();
        var nodeid = $("#yh").val();
        table.render({
            id: 'testReload',
            elem: '#tabe_db',
            url: '${ctx}/role/queryNodeStatus.do',
            page: true, //开启分页
            where:{"ywtype":ywtype,"rolename":rolename},
            even: true,
            limit: 20,
            cols: [[
                {field: 'zizeng', align: 'center', width: '5%', title: '序号', templet: '#zizeng'},
                {field: 'ywtype', align: 'center', title: '业务类型', width: '10%',sort:true},
                {field: 'roleid', align: 'center', title: '角色ID', width: '15%'},
                {field: 'rolename', align: 'center', title: '角色名称',event:'ndrwbj',  width: '15%',sort:true},
                {field: 'nodeid', align: 'center', title: '节点ID',width: '15%',sort:true},
                {field: 'status', align: 'center', title: '状态', width: '7%'},
                {field: 'czzt', align: 'center', width: '20%', title: '操作', templet:function (d) {
                        var roleid = d.roleid;
                        var ywtype = d.ywtype;
                        var nodeid = d.nodeid;
                        var status = d.status;
                        var html = "<div>";
                        html += "<a class='layui-btn layui-btn-sm layui-btn-danger' href='javascript:void(0)' onclick=\"shanchu('"+roleid+"','"+ywtype+"','"+nodeid+"','"+status+"')\">" + "删除" + "</a></div>";
                    return html;
                }},
            ]]
        });
    }



    //保存
    function savezynrxzInfo() {
        var ywtype = $('#ywtype option:selected').val();
        var roleid = $('#rolename option:selected').val();
        var rolename = $('#rolename option:selected').text();
        var status = $('#status').val();
        var nodeid = $('#nodeid').val();
        $.ajax({
            url: "${ctx}/role/saveNodeStatus.do",
            type: "post",
            async: false,
            data: {"status":status,"ywtype":ywtype,"roleid":roleid,"rolename":rolename,'nodeid':nodeid},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:1},function () {
                    if(result.code =='1' ){
                        layer.close(index1);
                        $("#jueseDiv").hide();
                        table.reload('testReload', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }

                        });
                    }

                });
            }
        })
    }

    //添加角色人员
    function xinzeng() {
        $("#nodeid").val("");
        $("#status").val("");
        index1 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '添加节点状态',
            area: ['650px', '80%'], //宽高
            closeBtn: 1,
            content: $('#jueseDiv'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (index, layero) {
                layer.close(index1)
                $("#jueseDiv").hide();
                return false;
            }
        });
    }

    function shanchu(roleid,ywtype,nodeid,status){
        layer.confirm("确定要删除？", {btn: ['确定', '取消']}, function (index){
            $.ajax({
                url: "${ctx}/role/deleteRoleNode.do",
                type: "post",
                async: false,
                data: {"roleid":roleid,"ywtype":ywtype,"nodeid":nodeid,"status":status},
                dataType: "json",
                success: function (result) {
                    layer.close(index);
                    layer.msg(result.msg,{icon:result.code,time:1000},function () {
                        ndrwquery();
                    });
                }
            })
        });
    }

    function queryRole(ywtype) {
        $.ajax({
            url: "${ctx}/role/queryXzJsList.do",
            type: "post",
            async: false,
            data: {"ywtype":ywtype},
            dataType: "json",
            success: function (data) {
                $("#rolename").empty();
                for (var i = 0; i < data.data.length; i++) {
                    $("#rolename").append("<option value=" + data.data[i].roleid + ">" + data.data[i].rolename + "</option>");
                }
                form.render();
            }
        })
    }
</script>