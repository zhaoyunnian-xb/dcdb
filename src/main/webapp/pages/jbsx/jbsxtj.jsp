<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <div class="layui-card">
        <div class="layui-card-header">
            交办事项督办监控列表
        </div>
        <div class="layui-card-body">
            <div class="layui-form">
                <div class="layui-row layui-col-space10">
                    <div class="layui-col-sm6 layui-col-md3">
                        <div class="layui-form-item">
                            <label class="layui-form-label">会议日期：</label>
                            <div class="layui-input-block">
                                <input type="text" name="swrq" id="swrq" class="layui-input"/>
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
                    <div class="layui-col-sm6 layui-col-md3">
                        <div class="layui-form-item">
                            <label class="layui-form-label" id="pslxLab">任务类型 ：</label>
                            <div class="layui-input-block">
                                <select name="pslx" id="pslx" lay-verify="required">
                                    <option value="">请选择</option>
                                    <option value="1">请选择111</option>
                                </select>
                            </div>
                        </div>

                    </div>
                    <%--<div class="layui-col-sm6 layui-col-md3 ">
                        <div class="layui-form-item">
                            <label class="layui-form-label">被批示件名称：</label>
                            <div class="layui-input-block">
                                <input type="text" name="psjmc" id="psjmc" class="layui-input"/>
                            </div>
                        </div>

                    </div>
                    <div class="layui-col-sm6 layui-col-md3">
                        <div class="layui-form-item">
                            <label class="layui-form-label">收文日期：</label>
                            <div class="layui-input-block">
                                <input type="text" name="swrq" id="swrq" class="layui-input"/>
                            </div>
                        </div>
                    </div>--%>

                    <%--  <div class="layui-col-sm6 layui-col-md3">
                          <div class="layui-form-item">
                              <label class="layui-form-label">创建日期 ：</label>
                              <div class="layui-input-block">
                                  <input type="text" name="cjrq" id="cjrq" class="layui-input"/>
                              </div>
                          </div>

                      </div>--%>
                </div>
                <div class="layui-row">
                    <div class="layui-col-sm6 layui-col-md3">
                        <div class="layui-form-item">
                            <label class="layui-form-label">办理类型 ：</label>
                            <div class="layui-input-block">
                                <select name="bllx" id="bllx" lay-verify="required">
                                    <option value="">请选择</option>
                                    <option value="党组委会督办">党组委会督办</option>
                                    <option value="检察长办公会督办">检察长办公会督办</option>
                                </select>
                            </div>
                        </div>

                    </div>
                    <div class="layui-col-sm6 layui-col-md8 ">
                        <div class="layui-form-item" style="text-align: right;">
                            <span class="layui-btn layui-btn-normal" onclick="tjQuery();">检索</span>
                        </div>

                    </div>
                    <%-- <div class="layui-col-sm6 layui-col-md3">
                         <div class="layui-form-item">
                             <label class="layui-form-label">状态 ：</label>
                             <div class="layui-input-inline">
                                 <select name="zt" id= 'zt'lay-verify="required">
                                     <option value=""></option>
                                     <option value="0">北京</option>
                                     <option value="1">上海</option>
                                     <option value="2">广州</option>
                                     <option value="3">深圳</option>
                                     <option value="4">杭州</option>
                                 </select>
                             </div>
                         </div>

                     </div>--%>
                    <%--  <div class="layui-col-sm6 layui-col-md3">
                          <div class="layui-form-item">
                              <label class="layui-form-label">创建人 ：</label>
                              <div class="layui-input-inline">
                                  <input type="text" name="cjr" id="cjr" class="layui-input"/>
                              </div>
                          </div>

                      </div>--%>
                </div>
                <%--<div class="layui-row">--%>
                <%--<div class="layui-col-sm6 layui-col-md3">
                    <div class="layui-form-item">
                        <label class="layui-form-label">状态 ：</label>
                        <div class="layui-input-inline">
                            <select name="city" lay-verify="required">
                                <option value=""></option>
                                <option value="0">北京</option>
                                <option value="1">上海</option>
                                <option value="2">广州</option>
                                <option value="3">深圳</option>
                                <option value="4">杭州</option>
                            </select>
                        </div>
                    </div>

                </div>--%>

                <%--<div class="layui-col-sm6 layui-col-md3 layui-col-md-offset9">--%>
                <%--<div class="layui-form-item">--%>
                <%--<span class="layui-btn" onclick="dbQuery();">检索</span>--%>
                <%--<span class="layui-btn layui-btn-primary" onclick="xinjian();">创建</span>--%>
                <%--</div>--%>

                <%--</div>--%>
                <%--</div>--%>
            </div>
            <div class="table-wrapper">
                <table class="layui-table" id="dzjb_tj" lay-filter="dzjb_tj" ></table>
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
                elem: '#swrq',
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
        selectPeizhiData();
        var form = layui.form;
        form.render();
        tjQuery();
    })
    function tjQuery() {
        var psjmc = $("#psjmc").val();
        var swrq = $("#swrq").val();
        var cbbmmc = $("#cbbmmc").val();
        var cjrq = $("#cjrq").val();
        var pslx = $("#pslx").val();
        var psrq = $("#psrq").val();
        var bllx = $("#bllx").val();
        var zt = $("#lczt").val();
        var lclx = $("#lclx").val();
        table.render({
            elem: '#dzjb_tj',
            url: '${ctx}/psdbTj/queryTable.do',
            page: true, //开启分页
            where:{"lclx":lclx,"psjmc":psjmc,"swrq":swrq,"cbbmmc":cbbmmc,"cjrq":cjrq,"pslx":pslx,"psrq":psrq,"bllx":bllx,"zt":zt},
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
                {field: 'zizeng', align: 'center', width: 60, title: '序号', templet: '#zizeng'},
                {field: 'zxdbh', align: 'center', title: '督办单号',event:'dbdh',  minWidth: 120,
                    style:'color:blue;cursor:pointer'},
                {field: 'swrq', align: 'center', title: '收文日期', width: 120},
                {field: 'pslx', align: 'center', title: '任务类型', minWidth: 120},
                {field: 'cbbmmc', align: 'center', title: '承办部门', width: 100},
                {field: 'cbbmid', align: 'center', title: '承办部门id',hide:"true", width: '10%'},
                {field: 'cjrq', align: 'center', title: '创建日期', width: 120},
                /*{field: 'cjr', align: 'center', title: '创建人', width: '10%'},*/
                {field: 'bllx', align: 'center', title: '办理类型', minWwidth: 120},
                {field: 'nodeId', align: 'center', title: '节点id',hide: "true", width: '10%'},
                {field: 'nodeName', align: 'center', title: '节点名称',hide: "true", width: '10%'},
                {field: 'id', align: 'center', title: '主键', hide: "true"},
                {field: 'zt', align: 'center', title: '状态', width:100},
                {field: 'preid', align: 'center', title: '分案前主键', hide: true},
            ]]
        });
    }
    //监听待办列表事件
    layui.use('table', function () {
        table.on('tool(dzjb_tj)', function (obj) {
            var data = obj.data;
            if (obj.event == 'dbdh') {
                window.location.href = encodeURI('${ctx}/flow/flowPage.do?isReadOnly=0&id='+data.id+'&wh='+data.wh+'&bllx='+data.bllx
                    +'&nodeid='+data.nodeId+'&nodename='+data.nodeName+'&cjr='+data.cjr+'&cbbmid='+data.cbbmid+'&cbbmmc='+data.cbbmmc+'&lclx='+data.lclx+'&isfa='+data.isfa+'&preid='+data.preid);
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
    //批示类型下拉框赋值
    function selectPeizhiData(){
        var type = "PSLX";
        $.ajax({
            url: "/lb/selectPeizhiData.do",
            type: "post",
            async: false,
            data: {"ptype": type},
            dataType: "json",
            success: function (result) {
                if(result.data.length > 0){
                    $("#pslx").empty();
                    $("#pslx").append("<option value="+''+">"+'请选择'+"</option>");
                    for (var i = 0; i < result.data.length; i++) {
                        $("#pslx").append("<option value=" + result.data[i].lbmc + ">" + result.data[i].lbmc + "</option>");
                    }
                }
            }
        })
    }
</script>
<jsp:include page="../footer.jsp"></jsp:include>
