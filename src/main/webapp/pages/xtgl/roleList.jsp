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
                <div class="layui-card-header">角色管理</div>
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
                                <span class="layui-btn layui-btn-primary " onclick="ndrwquery();">
                                    查询
                                </span>
                                <span class="layui-btn  layui-btn-normal" onclick="xinzeng();">
                                    新增角色
                                </span>
                            </div>

                            <table id="tabe_db" lay-filter="tabe_filter" class="layui-table"></table>
                        </div>
                        <!-- 上一页 下一页 -->
                        <div class="pull-left" style="margin-right: 10px;">
                        </div>
                    </div>

                    <form class="layui-form" id="jueseForm">
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
                            <div class="layui-form-item layui-form-text">
                                <label class="layui-form-label">角色名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="rolename" id="rolename" placeholder="请输入" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block ">
                                    <span class="layui-btn layui-btn-sm layui-btn-normal" id="zynrsavebtn"
                                          onclick="saveRole()">保存</span>
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
        var form =layui.form;
        form.render();
    })
    //角色表格返回数据
    function ndrwquery(){
        var ywtype = $("#yw").val();
        var rolename = $("#js").val();
        table.render({
            id: 'testReload',
            elem: '#tabe_db',
            url: '${ctx}/role/queryRoleList.do',
            page: true, //开启分页
            where:{"ywtype":ywtype,"rolename":rolename},
            even: true,
            limit: 20,
            cols: [[
                {field: 'zizeng', align: 'center', width: '5%', title: '序号', templet: '#zizeng'},
                {field: 'ywtype', align: 'center', title: '业务类型', width: '10%',sort:true},
                {field: 'roleid', align: 'center', title: '角色ID', width: '25%'},
                {field: 'rolename', align: 'center', title: '角色名称',event:'ndrwbj',  width: '15%',sort:true},
                {field: 'czzt', align: 'center', width: '60', fixed:'right', title: '操作', templet:function (d) {
                        var roleid = d.roleid;
                        var ywtype = d.ywtype;
                        var html = "<div>";
                        html += "<a class='layui-btn layui-btn-sm layui-btn-danger' href='javascript:void(0)' onclick=\"shanchu('"+roleid+"','"+ywtype+"')\">" + "删除" + "</a></div>";
                    return html;
                }}
            ]]
        });
    }


    //保存角色
    function saveRole() {
        var ywtype =$('#ywtype option:selected').val();
        var rolename = $("#rolename").val();
        $.ajax({
            url: "${ctx}/role/saveRole.do",
            type: "post",
            async: false,
            data: {"ywtype":ywtype,"rolename":rolename},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:result.code,time:1000},function () {
                    if(result.code == '1'){
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


    //角色新增弹框
    function xinzeng(id,ndrwmc) {
        $("#rolename").val("");
        index1 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '新增角色',
            area: ['650px', '80%'], //宽高
            closeBtn: 1,
            content: $('#jueseDiv'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (index, layero) {
                layer.close(index)
                $("#jueseDiv").hide();
                return false;
            }
        });
    }

    //主要内容删除
    function shanchu(roleid,ywtype){
        layer.confirm("此操作，会把该角色下的所有用户及节点状态数据，都删除！", {btn: ['确定', '取消']}, function (index){
            $.ajax({
                url: "${ctx}/role/deleteRole.do",
                type: "post",
                async: false,
                data: {"roleid":roleid,"ywtype":ywtype},
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

</script>