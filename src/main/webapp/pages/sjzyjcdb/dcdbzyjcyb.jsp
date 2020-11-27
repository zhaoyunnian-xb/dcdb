<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<style>
    .layui-form-label{width:100px}
    .layui-form-item .layui-input-inline{width:170px}
    .layui-form-item  .layui-input-block{margin-left:130px}
</style>
<div class="layui-fluid">
    <input id="lclx" type="hidden" value="${lclx}">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    重要决策已办列表
                </div>
                <div class="layui-card-body">
                    <div class="layui-form">
                        <div class="layui-row">
                            <div class="layui-col-sm6 layui-col-md3 ">
                                <div class="layui-form-item" id="psmcDiv">
                                    <label class="layui-form-label">立项名称：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="lxmc" id="lxmc" class="layui-input"/>
                                    </div>
                                </div>

                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label" id="swrqLab">立项日期：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="lxrq" id="lxrq" class="layui-input"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">承办部门：</label>
                                    <div class="layui-input-block">
                                        <select name="cbbmmc" id = "cbbmmc"lay-verify="required" lay-search>
                                            <option value="">请选择</option>
                                        </select>
                                    </div>
                                </div>

                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">创建日期 ：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="cjrq" id="cjrq" class="layui-input"/>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="layui-row">
                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label" id="pslxLab">重要决策类型：</label>
                                    <div class="layui-input-block">
                                        <select name="zyjclx" id="zyjclx" lay-verify="required">
                                            <option value="">请选择</option>
                                            <option value="1">党中央重大决策</option>
                                            <option value="2">党中央重要会议</option>
                                            <option value="3">党中央重要文件</option>
                                            <option value="4">党中央重要决定</option>
                                            <option value="5">党中央重要指示</option>
                                            <option value="6">省委重要会议</option>
                                            <option value="7">省委重要文件工作部署</option>
                                            <option value="8">最高检重要会议</option>
                                            <option value="9">最高检重要文件工作部署</option>
                                        </select>
                                    </div>
                                </div>

                            </div>
                            <div class="layui-col-sm6 layui-col-md8 ">
                                <div class="layui-form-item" style="text-align: right;">
                                    <span class="layui-btn layui-btn-normal" onclick="zyjcybQuery();">检索</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-wrapper">
                        <table class="layui-table" id="djlb_db" lay-filter="djlb_db" ></table>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</div>

<script>
    var table = layui.table;
    $(function () {
        //年份时间插件
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#lxrq',
                format: 'yyyy年MM月dd日'
            })
            laydate.render({
                elem: '#cjrq',
                format: 'yyyy年MM月dd日'
            })
            laydate.render({
                elem: '#psrq',
                format: 'yyyy年MM月dd日'
            })
        })
        queryCbBm();
        var form = layui.form;
        form.render();
        zyjcybQuery();
    })
    function zyjcybQuery() {
        var lxmc = $("#lxmc").val();
        var lxrq = $("#lxrq").val();
        var cbbmmc = $("#cbbmmc").val();
        var cjrq = $("#cjrq").val();
        var zyjclx = $("#zyjclx").val();
        var lclx = $("#lclx").val();
        table.render({
            elem: '#djlb_db',
            url: '${ctx}/yb/queryZyjcTableyb.do',
            page: true, //开启分页
            where:{"lxmc":lxmc,"lxrq":lxrq,"cbbmmc":cbbmmc,"cjrq":cjrq,"zyjclx":zyjclx,"lclx":lclx},
            even: true,
            limit: 10,
          parseData: function(res) {
            var data = res.data;
            $.each(data, function (index,item) {
              if (item.cbbmmc) {
                item.cbbmmc = item.cbbmmc.replace(/,/g, '<br>')
              }
            });
            return {
              code: res.code,
              msg: res.msg,
              count: res.count,
              data: data
            }
          },
            cols: [[
                {field: 'zizeng', align: 'center', width: '5%', title: '序号', templet: '#zizeng'},
                {field: 'lxh', align: 'center', title: '立项号', minWidth: 120},
                {field: 'lxmc', align: 'center', title: '立项名称',event:'psjmc',   minWidth: 200, style:'color:blue;cursor:pointer'},
              {field: 'nodeName', align: 'center', title: '节点名称',hide: false, minWidth: 120},
                {field: 'zyjclx', align: 'center', title: '重要决策类型', width: 120},
                {field: 'lxrq', align: 'center', title: '立项日期', width: 120},
                {field: 'cbbmmc', align: 'center', title: '承办部门', width: 100},
                {field: 'cbbmid', align: 'center', title: '承办部门id',hide:true, width: '10%'},
                {field: 'cjrq', align: 'center', title: '创建日期', width: 120},
                /*{field: 'cjr', align: 'center', title: '创建人', width: '10%'},*/
                {field: 'nodeId', align: 'center', title: '节点id',hide: true, width: '10%'},
              /*  {field: 'status', align: 'center', title: '状态', width: '10%'},*/
                {field: 'id', align: 'center', title: '主键', hide: true}
            ]]
        });
    }
    //监听待办列表事件
    layui.use('table', function () {
        table.on('tool(djlb_db)', function (obj) {
            var data = obj.data;
            if (obj.event === 'psjmc') {
                    window.location.href = encodeURI('${ctx}/db/toshenhe.do?isReadOnly=1&id='+data.id+'&wh='+data.wh+'&bllx='+data.bllx
                        +'&nodeid='+data.nodeId+'&nodename='+data.nodeName+'&cjr='+data.cjr+'&cbbmid='+data.cbbmid+'&cbbmmc='+data.cbbmmc+'&lclx='+data.lclx+'&isfa='+data.isfa);
            }
        });
    });
    //查询部门下拉列表
    function queryCbBm() {
        $.ajax({
            url: '/db/queryCbBm.do',
            type: "post",
            async: false,
            dataType: "json",
            success: function (data) {
                $("#cbbmmc").empty();
                $("#cbbmmc").append("<option value="+''+">"+'请选择'+"</option>");
                for (var i = 0; i < data.data.length; i++) {
                    $("#cbbmmc").append("<option value=" + data.data[i].NAME + ">" + data.data[i].NAME + "</option>");
                }
            },
        });
    }

</script>
<jsp:include page="../footer.jsp"></jsp:include>
