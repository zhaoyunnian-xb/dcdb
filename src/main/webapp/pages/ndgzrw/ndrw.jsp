<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>

<div class="layui-fluid">
    <input type="hidden" id="zyrwFlag" value="">
    <input type="hidden" id="zyrwid" value="">
    <div class="">
        <div class="">
            <div class="layui-card">
                <div class="layui-card-header">年度任务</div>
                <div class="layui-card-body">
                    <div class="query-wrapper layui-row layui-col-space5">
                        <div class="layui-col-sm6 layui-col-md3">
                            <label class="layui-form-label">年份：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="nf" id="nf" class="layui-input"/>
                                <input id="ndid" type="hidden" value="${ndid}">
                            </div>
                        </div>
                        <div class="layui-col-sm6 layui-col-md3">
                            <label class="layui-form-label">发起人：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="czr" id="czr" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-sm6 layui-col-md3">
                            <label class="layui-form-label">年度任务名称：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="rwmc" id="rwmc" class="layui-input"/>
                            </div>
                        </div>
                        <%--<input id="ndid" name="ndid" value="${ndid}" type="text">--%>
                        <div class="layui-col-sm6 layui-col-md3">
                            <label class="layui-form-label"></label>
                            <div class="layui-input-inline">
                                <span class="layui-btn layui-btn-primary " onclick="ndrwquery();">
                                    查询
                                </span>
                                <span class="layui-btn  layui-btn-normal" onclick="xinzeng($('#ndid').val());">
                                    新增
                                </span>
                            </div>

                            <%--<a href="http://141.112.36.121:80/index.do?id=56D1D5FFFC014D54BCAA9BB43DE4FA5D" target="_blank">aaaa</a>--%>
                        </div>
                    </div>
                    <div class="table">
                        <table id="tabe_db" lay-filter="tabe_filter" class="layui-table"></table>
                        <!-- 上一页 下一页 -->
                        <div class="pull-left" style="margin-right: 10px;">
                        </div>
                    </div>
                    <form id="ndrwForm">
                        <div id="ndrw" style="display: none">
                            <div class="" style="text-align: right;margin: 10px">
                                <div class="layui-card">
                                    <div class="layui-card-body">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">年度任务名称：</label>
                                            <div class="layui-input-inline">
                                                <input name="rwmctext" id="rwmctext"
                                                          class="layui-input">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="layui-card">
                                <div class="layui-card-body" style="padding:0px 15px">
                                    <table id="tabe_ydnr" lay-filter="tabe_ydnr" class="layui-table"></table>
                                </div>
                            </div>
                            <div style="float: right;padding: 20px;" id="xinzengdiv">
                                <span class="layui-btn layui-btn-primary layui-btn-sm" onclick="xinzengYdnr();">新增
                                </span>
                                <span class="layui-btn layui-btn-normal layui-btn-sm " onclick="baocunYdnr();">保存</span>
                                <span class="layui-btn layui-btn-normal layui-btn-sm " onclick="tijiaoYdnr();">提交</span>
                                <input id="ndidnew" type="hidden">
                            </div>
                        </div>
                    </form>
                    <form class="layui-form" id="ydnrForm">
                        <div id="ydnr" style="display: none; margin:10px">
                            <div class="layui-form-item">
                                <label class="layui-form-label">要点内容：</label>
                                <div class="layui-input-block">
                                    <textarea name="ydnrtext" id="ydnrtext" class="layui-textarea"></textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <span class="layui-btn layui-btn-sm layui-btn-normal" id="savebtn"
                                          onclick="saveYdnrInfo()">保存
                                    </span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <form class="layui-form" id="zynrForm">
                        <div id="zynr" style="display: none">
                            <div style="text-align: right;margin: 10px;">
                                <span class="layui-btn layui-btn-sm layui-btn-normal" onclick="xinzengZynr();"
                                >新增</span>
                                <input id="zynrydnr" type="hidden">
                                <input id="zynrndid" type="hidden">
                            </div>
                            <div class="layui-card">
                                <div class="layui-card-body" style="padding:0px 15px">
                                    <table id="tabe_zynr" lay-filter="tabe_zynr" class="layui-table"></table>
                                </div>
                            </div>

                        </div>
                    </form>
                    <form class="layui-form" id="zynrxinzengForm">
                        <div id="zynrxinzeng" style="display: none; margin: 15px ">
                            <div class="layui-form-item layui-form-text">
                                <label class="layui-form-label">主要任务：</label>
                                <div class="layui-input-block">
                                    <textarea placeholder="请输入内容" name="zynrtext" id="zynrtext"
                                              class="layui-textarea"></textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">责任领导：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="zrld" id="zrld" placeholder="请输入" autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <%-- <div class="layui-form-item">
                                 <label class="layui-form-label">主责部门：</label>
                                 <div class="layui-input-block">
                                     <input type="text" name="zybm" id="zybm" placeholder="请输入" autocomplete="off" class="layui-input">
                                 </div>
                             </div>--%>
                            <div class="layui-form-item">
                                <label class="layui-form-label">牵头部门：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="qtbm" id="qtbm" placeholder="请输入" autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">配合部门：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="phbm" id="phbm" placeholder="请输入" autocomplete="off"
                                           class="layui-input">
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
    var index2 = "";
    var index3 = "";
    var index4 = "";
    var index5 = "";
    var table = layui.table;
    $(function () {
        //年份时间插件
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#nf',
                format: 'yyyy',
                type: 'year'
            })
        })
        ndrwquery();
    })

    //年度任务总表格返回数据
    function ndrwquery() {
        var nd = $("#nf").val();
        var czr = $("#czr").val();
        var ndrwmc = $("#rwmc").val();
        table.render({
            elem: '#tabe_db',
            url: '${ctx}/ndrw/queryTable.do',
            page: true, //开启分页
            where: {"nd": nd, "czr": czr, "ndrwmc": ndrwmc},
            even: true,
            limit: 10,
            cols: [[
                {field: 'zizeng', align: 'center', title: '序号', templet: '#zizeng', width:60},
                {field: 'nd', align: 'center', title: '年份', width:80},
                {
                    field: 'ndrwmc',
                    align: 'left',
                    minWidth:240,
                    title: '年度任务名称',
                    event: 'ndrwbj',
                    style: 'color:#4084f0;cursor:pointer'
                },
                {field: 'czr', align: 'center', title: '发起人',width:80},
                {field: 'czsj', align: 'center', title: '操作时间', width:90 },
                {field: 'id', align: 'center', title: '主键', hide: "true"},
                {
                    field: 'czzt', align: 'center', width: 380, title: '操作', templet: function (d) {
                        var html = "";
                        var status = d.czzt;
                        if (status == '00') {
                            html = "<div><span class='layui-btn layui-btn-xs layui-btn-disabled'>一季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>二季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>三季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                        } else {
                            if (status == '11') {
                                html = "<div><span class='layui-btn layui-btn-xs' onclick=\"faqi('" + d.id + "','1');\">一季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>二季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>三季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                            } else if (status == '12') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-normal'style='cursor:not-allowed'>一季度已发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>二季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>三季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                            } else if (status == '21') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed' >一季度已结束</span><span class='layui-btn layui-btn-xs' onclick=\"faqi('" + d.id + "','2');\">二季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>三季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                            } else if (status == '22') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>一季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-normal' style='cursor:not-allowed'>二季度发起中</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>三季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                            } else if (status == '31') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>一季度已结束</span><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>二季度已结束</span><span class='layui-btn layui-btn-xs'onclick=\"faqi('" + d.id + "','3');\">三季度发起</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                            } else if (status == '32') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>一季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-danger' style='cursor:not-allowed'>二季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-normal' style='cursor:not-allowed'>三季度发起中</span><span class='layui-btn layui-btn-xs  layui-btn-disabled'>四季度发起</span></div>"
                            } else if (status == '41') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>一季度已结束</span><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>二季度已结束</span><span class='layui-btn layui-btn-xs layui-btn-danger ' style='cursor:not-allowed'>三季度已结束</span><span class='layui-btn layui-btn-xs 'onclick=\"faqi('" + d.id + "','4');\">四季度发起</span></div>"
                            } else if (status == '42') {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>一季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-danger' style='cursor:not-allowed'>二季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-danger' style='cursor:not-allowed'>三季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-normal' style='cursor:not-allowed'>四季度发起中</span></div>"
                            } else {
                                html = "<div><span class='layui-btn layui-btn-xs layui-btn-danger' style='cursor:not-allowed'>一季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-danger' style='cursor:not-allowed'>二季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-danger' style='cursor:not-allowed'>三季度已结束</span><span class='layui-btn layui-btn-xs  layui-btn-danger' style='cursor:not-allowed'>四季度已结束</span></div>"
                            }
                        }
                        return html;
                    }
                },
            ]]
        });
    }

    //发起方法
    function faqi(id, jidu) {
        $.ajax({
            url: "${ctx}/ndrw/faqi.do",
            type: "post",
            async: false,
            data: {"id": id, "jidu": jidu},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, time: 1000}, function () {
                    ndrwquery();
                });
            }
        })
    }

    //监听年度任务表格年度任务编辑
    layui.use('table', function () {
        table.on('tool(tabe_filter)', function (obj) {
            var data = obj.data;
            var id = data.id;
            var czzt = data.czzt;
            if (obj.event === 'ndrwbj') {
                if (czzt != '00') {
                    $("#xinzengdiv").hide();
                }
                xinzeng(id, data.ndrwmc);
            }
        });
    });

    //主要内容表格返回数据
    function zynrquery() {
        table.render({
            elem: '#tabe_zynr',
            url: '${ctx}/ndrw/queryynr.do',
            where: {"zynrydnr": $("#zynrydnr").val(), "zynrndid": $("#zynrndid").val()},
            page: false, //开启分页
            even: true,
            limit: 20,
            cols: [[
                {field: 'zizeng', align: 'center', width: 65, title: '序号', templet: '#zizeng'},
                {field: 'zyrwmc', align: 'center', title: '主要任务',},
                {field: 'zrld', align: 'center', title: '责任领导',},
                /* {field: 'zybm', align: 'center', title: '主责部门',},*/
                {field: 'qtbm', align: 'center', title: '牵头部门',},
                {field: 'phbm', align: 'center', title: '配合部门'},
                {field: 'id', align: 'center', title: '主键', hide: "true"},
                {field: 'ndid', align: 'center', title: '主键', hide: "true"},
                {field: 'ydnrid', align: 'center', title: '主键', hide: "true"},
                {
                    field: 'czzt', align: 'center', title: '操作',  templet: function (d) {
                        var id = d.id;
                        var ndid = d.ndid;
                        var ydnrid = d.ydnrid;
                        var html = "<div>";
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"zynrBianji('" +
                            id + "','" + ndid + "','" + ydnrid + "')\">" + "编辑" + "</a>";
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"shanchuzynr('" + id + "')\">" + "删除" + '</a></div>';
                        return html;
                    }

                }
            ]]
        });
    }

    //要点内容表格返回数据
    function ydnrquery(id) {
        $("#ndidnew").val(id);
        table.render({
            elem: '#tabe_ydnr',
            url: '${ctx}/ndrw/queryYdnr.do',
            where: {"ndid": id},
            page: false, //开启分页
            even: true,
            height: 300,
            limit: 20,
            cols: [[
                {field: 'zizeng', align: 'center', width: 65, title: '序号', templet: '#zizeng'},
                {field: 'ydnrmc', align: 'center', title: '要点内容', width: 400},
                {field: 'id', align: 'center', title: '主键', hide: "true"},
                {field: 'ndid', align: 'center', title: '主键', hide: "true"},
                {
                    field: 'czzt', align: 'center', title: '操作', templet: function (d) {
                        var id = d.id;
                        var ndid = d.ndid;
                        var html = "<div>";
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"ydnrBianji('" +
                            id + "','" + ndid + "')\">" + "编辑" + "</a>";
                        html += "<a class='layui-btn layui-btn-sm layui-btn-danger' href='javascript:void(0)' onclick=\"shanchuydnr('" + id + "','" + ndid + "')\">" + "删除" + "</a></div>";
                        return html;
                    }
                },
            ]]
        });
    }


    //要点内容保存
    function saveYdnrInfo() {
        var ndid = $("#ndidnew").val();
        var ydnrText = $("#ydnrtext").val();
        $.ajax({
            url: "${ctx}/ndrw/saveYdnr.do",
            type: "post",
            async: false,
            data: {"ydnrText": ydnrText, "ndid": ndid},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, time: 1000}, function () {
                    $("#ydnrtext").val("");
                    layer.close(index2);
                    $("#ydnr").hide();
                    $("#ndidnew").val(ndid);
                    //刷新要点内容表格
                    ydnrquery(ndid);
                });
            }
        })

    }

    //保存年度任务
    function baocunYdnr() {
        var ndid = $("#ndidnew").val();
        var rwmctext = $("#rwmctext").val();
        $.ajax({
            url: "${ctx}/ndrw/baocunYdnr.do",
            type: "post",
            async: false,
            data: {"ndid": ndid, "rwmctext": rwmctext},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, time: 1000}, function () {
                    layer.close(index1);
                    $("#ndrw").hide();
                    //刷新年度任务表格
                    ndrwquery();
                });
            }
        });
    }

    //提交整个年度任务
    function tijiaoYdnr() {
        var ndid = $("#ndidnew").val();
        var rwmctext = $("#rwmctext").val();
        if (rwmctext == '') {
            layer.msg("请填写任务名称", {icon: 2, time: 1000});
        } else {
            $.ajax({
                url: "${ctx}/ndrw/tijiaoYdnr.do",
                type: "post",
                async: false,
                data: {"ndid": ndid, "rwmctext": rwmctext},
                dataType: "json",
                success: function (result) {
                    layer.msg(result.msg, {icon: result.code, time: 1000}, function () {
                        layer.close(index1);
                        $("#ndrw").hide();
                        //刷新年度任务表格
                        ndrwquery();
                    });
                }
            });
        }

    }

    //年度任务新增弹框
    function xinzeng(id, ndrwmc) {
        $("#rwmctext").val(ndrwmc || '');
        index1 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '工作任务下发',
            area: ['650px', '80%'], //宽高
            closeBtn: 1,
            content: $('#ndrw'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (index, layero) {
                layer.close(index)
                $("#ndrw").hide();
                return false;
            }
        });
        ydnrquery(id);
    }

    //要点内容新增弹框
    function xinzengYdnr() {
        index2 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '要点内容新增',
            area: ['500px', '50%'], //宽高
            closeBtn: 1,
            content: $('#ydnr'),
            cancel: function (index, layero) {
                layer.close(index)
                $("#ydnr").hide();
                return false;
            }
        });
    }

    //要点内容编辑弹框
    /*function ydnrBianji(id,ndid) {
        $("#zynrydnr").val(id);
        $("#zynrndid").val(ndid);
        index3 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '要点内容编辑',
            area: ['800px', '80%'], //宽高
            closeBtn : 1,
            content: $('#zynr'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
        zynrquery();
    }*/
    //主要内容编辑弹窗
    function zynrBianji(id, ndid, ydnrid) {
        $.ajax({
            url: "${ctx}/ndrw/queryZynrById.do",
            type: "post",
            async: false,
            data: {"id": id},
            dataType: "json",
            success: function (result) {
                $("#zyrwFlag").val("modify");
                $("#zyrwid").val(id);
                $("#zynrtext").val(result.zyrw.zyrwmc);
                $("#zrld").val(result.zyrw.zrld);
                $("#qtbm").val(result.zyrw.qtbm);
                $("#zybm").val(result.zyrw.zybm);
                $("#phbm").val(result.zyrw.phbm);
                $("#qtbm").attr("qtbmqb", result.zyrw.qtbm1);
                $("#zybm").attr("zybmqb", result.zyrw.zybm1);
                $("#phbm").attr("phbmqb", result.zyrw.phbm1);
                index4 = layer.open({
                    type: 1,
                    shade: 0.3,
                    skin: 'demo-class',
                    title: '主要任务编辑',
                    area: ['700px', '80%'], //宽高
                    closeBtn: 1,
                    content: $('#zynrxinzeng'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    cancel: function (index, layero) {
                        layer.close(index)
                        $("#zynrxinzeng").hide();
                        return false;
                    }
                });
            }
        })
    }

    //主要内容新增保存
    function savezynrxzInfo() {
        var zynrtext = $("#zynrtext").val();
        var zrld = $("#zrld").val();
        var qtbm = $("#qtbm").attr("qtbmqb");
        var zybm = $("#zybm").attr("zybmqb");
        // var phbm = $("#phbm").attr("phbmqb");
        var phbm = $("#phbm").val();
        var ndid = $("#zynrndid").val();
        var zynrydnr = $("#zynrydnr").val();
        var flag = $("#zyrwFlag").val();
        var zyrwid = $("#zyrwid").val();
        $.ajax({
            url: "${ctx}/ndrw/savezynrxzInfo.do",
            type: "post",
            async: false,
            data: {
                "zynrtext": zynrtext,
                "zrld": zrld,
                "qtbm": qtbm,
                "zybm": zybm,
                "phbm": phbm,
                "ndid": ndid,
                "zynrydnr": zynrydnr,
                "flag": flag,
                "zyrwid": zyrwid
            },
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: 1, time: 1000}, function () {
                    layer.close(index5);
                    layer.close(index4);
                    $("#zynrtext").val("");
                    $("#zrld").val("");
                    $("#qtbm").val("");
                    $("#zybm").val("");
                    $("#phbm").val("");
                    $("#qtbm").attr("qtbmqb", "");
                    $("#zybm").attr("zybmqb", "");
                    $("#phbm").attr("phbmqb", "");
                    $("#zynrxinzeng").hide();
                    zynrquery();
                });
            }
        })
    }

    //要点内容编辑弹框
    function ydnrBianji(id, ndid) {
        $("#zynrydnr").val(id);
        $("#zynrndid").val(ndid);
        index3 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '主要任务编辑',
            area: ['800px', '80%'], //宽高
            closeBtn: 1,
            content: $('#zynr'),
            cancel: function (index, layero) {
                layer.close(index)
                $("#zynr").hide();
                return false;
            }

        });
        zynrquery();
    }

    //主要内容新增弹框
    function xinzengZynr() {
        $("#zyrwFlag").val("add");
        $("#zynrtext").val("");
        $("#zrld").val("");
        $("#qtbm").val("");
        $("#zybm").val("");
        $("#phbm").val("");
        $("#qtbm").attr("qtbmqb", "");
        $("#zybm").attr("zybmqb", "");
        $("#phbm").attr("phbmqb", "");
        index5 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '主要任务新增',
            area: ['700px', '80%'], //宽高
            closeBtn: 1,
            content: $('#zynrxinzeng'),
            cancel: function (index, layero) {
                layer.close(index)
                $("#zynrxinzeng").hide();
                return false;
            }
        });
    }

    //要点内容删除
    function shanchuydnr(id, ndid) {
        layer.confirm("确定要删除？", {btn: ['确定', '取消']}, function (index) {
            $.ajax({
                url: "${ctx}/ndrw/deleteYdnr.do",
                type: "post",
                async: false,
                data: {"id": id},
                dataType: "json",
                success: function (result) {
                    layer.close(index);
                    layer.msg(result.msg, {icon: result.code, time: 1000}, function () {
                        ydnrquery(ndid);
                    });
                }
            })
        });
    }

    //主要内容删除
    function shanchuzynr(id) {
        layer.confirm("确定要删除？", {btn: ['确定', '取消']}, function (index) {
            $.ajax({
                url: "${ctx}/ndrw/deleteZynr.do",
                type: "post",
                async: false,
                data: {"id": id},
                dataType: "json",
                success: function (result) {
                    layer.close(index);
                    layer.msg(result.msg, {icon: result.code, time: 1000}, function () {
                        zynrquery();
                    });
                }
            })
        });
    }

    //回显方法
    function gandiao(id, str) {
        var a = str.split(",");
        var b = $("#" + id).val() || '';
        var str1 = "";
        if (b != null) {
            $.each(a, function (i, item) {
                str1 += item.split("@")[2] + "@";
            });
        }
        return str1;
    }

    //去掉已选择部门方法
    function qudiao(id, str) {
        var a = str.split(",");
        var b = $("#" + id).val() || '';
        var str1 = "";
        if (b != null) {
            $.each(a, function (i, item) {
                str1 += item.split("@")[0] + "@";
            });
        }
        return str1;
    }

    //责任领导选择
    $('#zrld').on('click', function () {
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择责任领导',
            content: '/pages/eleTree.jsp',
            area: ['650px', '80%'],
            scrollbar: false,
            btn: ['确认'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                // var str2 = "";
                $.each(a, function (i, item) {
                    //var userid = item.id;
                    var username = item.label;
                    //var depid = item.parent.id;
                    // var depname = item.parent.label;
                    // str += depid+"@"+depname+"@"+userid+","
                    str += username + ",";
                })
                str = str.substr(0, str.length - 1);
                //  str2 = str2.substr(0,str2.length-1);
                // $("#zrld").attr("zrldqb",str);
                $("#zrld").val(str);
                layer.close(index);
            }
        })
    })
    //牵头部门选择
    $('#qtbm').on('click', function () {
        var qtbmhx = gandiao("qtbm", $("#qtbm").attr("qtbmqb") || '');
        var zybmhx = qudiao("zybm", $("#zybm").attr("zybmqb") || '');
        var phbmhx = qudiao("phbm", ($("#phbm").attr("phbmqb") || ''));
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择牵头部门',
            content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds=' + qtbmhx/*+'&isRemove=true&removeIds='+zybmhx+phbmhx*/,
            area: ['650px', '80%'],
            scrollbar: false,
            btn: ['确认'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                var str2 = "";
                $.each(a, function (i, item) {
                    var userid = item.id;
                    var depid = item.parent.id;
                    var depname = item.parent.label;
                    str += depid + "@" + depname + "@" + userid + ","
                    str2 += depname + ",";
                })
                str = str.substr(0, str.length - 1);
                str2 = str2.substr(0, str2.length - 1);
                $("#qtbm").attr("qtbmqb", str);
                $("#qtbm").val(str2);
                layer.close(index);
            }
        })
    })
    //主要部门选择
    $('#zybm').on('click', function () {
        debugger
        var zybmhx = gandiao("zybm", ($("#zybm").attr("zybmqb") || ''));
        var qtbmhx = qudiao("qtbm", $("#qtbm").attr("qtbmqb") || '');
        var phbmhx = qudiao("phbm", ($("#phbm").attr("phbmqb") || ''));
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择主要部门',
            content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds=' + zybmhx/*+'&isRemove=true&removeIds='+qtbmhx+phbmhx*/,
            area: ['650px', '80%'],
            scrollbar: false,
            btn: ['确认'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                var str2 = "";
                $.each(a, function (i, item) {
                    var userid = item.id;
                    var depid = item.parent.id;
                    var depname = item.parent.label;
                    str += depid + "@" + depname + "@" + userid + ","
                    str2 += depname + ",";
                })
                str = str.substr(0, str.length - 1);
                str2 = str2.substr(0, str2.length - 1);
                $("#zybm").attr("zybmqb", str);
                $("#zybm").val(str2);
                layer.close(index);
            }
        })
    })
    //配合部门选择
    /* $('#phbm').on('click',function(){
         var phbmhx = gandiao("phbm",($("#phbm").attr("phbmqb")||'')) ;
         var zybmhx = qudiao("zybm",$("#zybm").attr("zybmqb") ||'') ;
         var qtbmhx = qudiao("qtbm",($("#qtbm").attr("qtbmqb")||'')) ;
         var layer = layui.layer;
         layer.open({
             type: 2,
             title: '请选择配合部门',
             content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds='+phbmhx/!*+'&isRemove=true&removeIds='+zybmhx+qtbmhx*!/,
             area: ['650px', '80%'],
             scrollbar: false,
             btn: ['确认'],
             yes:function(index,layero) {
                 var body = layer.getChildFrame('body', index);
                 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                 var a = iframeWin.getCurrentArr();
                 var str = "";
                 var str2 = "";
                 $.each(a,function (i,item) {
                     var userid = item.id;
                     var depid = item.parent.id;
                     var depname = item.parent.label;
                     str += depid+"@"+depname+"@"+userid+","
                     str2 += depname+",";
                 })
                 str = str.substr(0,str.length-1);
                 str2 = str2.substr(0,str2.length-1);
                 $("#phbm").attr("phbmqb",str);
                 $("#phbm").val(str2);
                 layer.close(index);
             }
         })
     })*/
</script>