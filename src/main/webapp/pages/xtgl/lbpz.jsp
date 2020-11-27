<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<style>
    .layui-form-label {
        width: 100px
    }

    .layui-form-item .layui-input-inline {
        width: 170px
    }

    .layui-form-item .layui-input-block {
        margin-left: 130px
    }
</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                   配置管理
                </div>
                <div class="layui-card-body">
                    <div class="layui-form">
                        <div class="layui-row">
                            <div class="layui-col-sm6 layui-col-md3 ">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">类别名称：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="selectlbmc" id="selectlbmc" class="layui-input"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3 ">
                                <div class="layui-form-item" style="text-align: right;">
                                    <span class="layui-btn layui-btn-normal" onclick="queryAllParentLb();">检索</span>
                                    <span class="layui-btn layui-btn-primary" onclick="insertPlb();">创建</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-wrapper">
                        <%-- <table class="layui-table" id="djlb_db" lay-filter="djlb_db" ></table>--%>
                        <table class="layui-table" id="parentLb" lay-filter="parentLb"></table>
                    </div>
                    <div id="ydnr" style="display: none; margin:10px">
                        <input type="hidden" id="newPid"/>
                        <input type="hidden" id="newPtype"/>
                        <input type="hidden" id="zlbid"/>
                        <div class="layui-form-item">
                            <label class="layui-form-label">类别名称：</label>
                            <div class="layui-input-block">
                                <input class="layui-input" id="zlbmc" name="zlbmc">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">排序：</label>
                            <div class="layui-input-block">
                                <input class="layui-input" id="px" name="px">
                            </div>
                        </div>
                      <%--  <div class="layui-form-item">
                            <label class="layui-form-label">类别别名：</label>
                            <div class="layui-input-block">
                                <input class="layui-input" id="lbmctype" name="lbmctype">
                            </div>
                        </div>--%>
                        <div class="layui-form-item">
                            <label class="layui-form-label">所属类别：</label>
                            <div class="layui-input-block">
                                <input class="layui-input" id="sslb" name="sslb" disabled>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" id="savebtn3"
                                        onclick="editJdMb()">确认
                                </button>
                            </div>
                        </div>
                    </div>
                    <div id="flbdetail" style="display: none; margin:10px">
                        <input type="hidden" id="plbid"/>
                        <div class="layui-form-item">
                            <label class="layui-form-label">类别名称：</label>
                            <div class="layui-input-block">
                                <input class="layui-input" id="plbmc" name="plbmc">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">类别标志：</label>
                            <div class="layui-input-block">
                                <input class="layui-input" id="psslb" name="psslb">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" id="savebtn2"
                                        onclick="editJdMb2()">确认
                                </button>
                            </div>
                        </div>
                    </div>
                    <form id="ndrwForm">
                        <input type="hidden" id="zlbnewType"/>
                        <input type="hidden" id="zlbnewId"/>
                        <div id="ndrw" style="display: none">
                            <div class="layui-card">
                                <div style="text-align: right;padding-right: 15px;padding-top: 10px">
                                    <span onclick="insertZlb()" class="layui-btn layui-btn-sm">新增</span>
                                </div>
                                <div class="layui-card-body" style="padding:0px 15px">
                                    <table class="layui-table" id="djlb_db" lay-filter="djlb_db"></table>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
</div>

<script>
    var table = layui.table;
    var form = layui.form;
    var index1 = "";
    var index2 = "";
    var index3 = "";
    $(function () {
        queryAllParentLb();
    })

    function queryAllParentLb() {
        var lbmc = $("#selectlbmc").val();
        table.render({
            id: 'testReload',
            elem: '#parentLb',
            url: '${ctx}/lb/queryPAlltype.do',
            page: true, //开启分页
            where: {"lbmc": lbmc},
            even: true,
            limit: 10,
            cols: [[
                {field: 'zizeng', align: 'center', width: '10%', title: '序号', templet: '#zizeng'},
                {field: 'lbmc', align: 'center', title: '名称', width: '20%'},
                {field: 'lbtype', align: 'center', title: '类别标识', width: '30%', style: 'color:blue;cursor:pointer'},
                {
                    field: 'cz', align: 'center', title: '操作', templet: function (d) {
                        var html = "<div>";
                        html +=
                            "<a class='layui-btn layui-btn-sm layui-btn-normal' href='javascript:void(0)' onclick=\"peditrw('" + d.id + "','" + d.lbmc + "','" + d.lbtype + "')\">" + "编辑" + "</a>";
                        html += "<a class='layui-btn layui-btn-sm layui-btn-danger' href='javascript:void(0)' onclick=\"pdel('" + d.id + "','" + d.lbtype + "')\">" + "删除" + "</a>";
                        html += "<a class='layui-btn layui-btn-sm ' href='javascript:void(0)' onclick=\"selectChildrendLb('" + d.id + "','" + d.lbtype + "')\">" + "查看子类别" + "</a></div>";

                        return html;
                    }
                }
            ]]
        });
    }

    //小类别编辑：
    function editrw(id, pid, ptype, lbmc, lbmctype,px) {
        $("#zlbid").val(id);
        $("#zlbmc").val(lbmc);
        $("#lbmctype").val(lbmctype);
        $("#sslb").val(ptype);
        $("#newPtype").val(ptype);
        $("#newPid").val(pid);
        $("#px").val(px);
        openChildrendEdit();
    }

    //大类别编辑
    function peditrw(id, lbmc, lbtype) {
        $("#plbid").val(id);
        $("#plbmc").val(lbmc);
        $("#psslb").val(lbtype);
        openedit();
    }

    //小类别删除：
    function del(re) {
        $.ajax({
            url: '/lb/delChildrenLb.do',
            type: "post",
            async: false,
            data: {"id": re},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, offset: ["255px", "500px"], time: 1000});
                table.reload('testReload2', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });
            },
        });
    }

    //大类别删除：
    function pdel(id, lbtype) {
        var id = id;
        var lbtype = lbtype;
        var param = {};
        param.id = id;
        param.lbtype = lbtype;
        $.ajax({
            url: '/lb/delParentLb.do',
            type: "post",
            async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, offset: ["255px", "500px"], time: 1000});
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });
            },
        });
    }

    function selectChildrendLb(id, lbtype) {
        var pid = id;
        var ptype = lbtype;
        $("#zlbnewType").val(ptype);
        $("#zlbnewId").val(pid);
        table.render({
            id: 'testReload2',
            elem: '#djlb_db',
            url: '${ctx}/lb/queryTable.do',
            page: true, //开启分页
            where: {"pid": pid, "ptype": ptype},
            even: true,
            limit: 10,
            cols: [[
                {field: 'zizeng', align: 'center', width: 40, title: '序号', templet: '#zizeng'},
                {field: 'lbmc', align: 'center', title: '名称', width: '45%'},
                {field: 'px', align: 'center', title: '排序', width: '10%',templet: function (d) {
                    var px = d.px;
                    if(px == 0){
                      px="";
                    }
                    return px;
                  }},
                {
                    field: 'ptype',
                    align: 'center',
                    title: '所属类别',
                    event: 'psjmc',
                    width: '15%',
                    style: 'color:blue;cursor:pointer'
                },
                {
                    field: 'cz', align: 'center', title: '操作', width: 120,templet: function (d) {
                    var px = d.px;
                    if(px == 0){
                      px="";
                    }
                        var html = "<div>";
                        html +=
                            "<a class='layui-btn layui-btn-sm layui-btn-normal' href='javascript:void(0)' onclick=\"editrw('" + d.id + "','" + d.pid + "','" + d.ptype + "','" + d.lbmc + "','" + d.lbmctype + "','" + px + "')\">" + "编辑" + "</a>";
                        html += "<a class='layui-btn layui-btn-sm layui-btn-danger' href='javascript:void(0)' onclick=\"del('" + d.id + "')\">" + "删除" + "</a><div>";
                        return html;
                    }
                },
            ]]
        });
        showChildrendLb();

    }

    //大类别编辑弹框 openChildrendEdit
    function openedit() {
        var layer = layui.layer;
        index2 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '类别编辑',
            offset: ['300px'],
            area: ['50%', '50%'], //宽高
            closeBtn: 1,
            content: $('#flbdetail'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (index) {
                layer.close(index);
                $("#flbdetail").hide();
                return false;
            }
        });
    }

    //小类别编辑弹框
    function openChildrendEdit() {
        var layer = layui.layer;
        index3 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '小类别编辑',
           offset: ['300px'],
           area: ['50%', '50%'], //宽高
            closeBtn: 1,
            content: $('#ydnr'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (index) {
                layer.close(index);
                $("#ydnr").hide();
                return false;
            }
        });
    }


    function editJdMb() {
        var id = $("#zlbid").val();
        var lbmc = $("#zlbmc").val();
        var lbmctype = $("#lbmctype").val();
        var ptype = $("#newPtype").val();
        var px = $("#px").val();
        var pid = $("#newPid").val();
        var param = {}
        param.id = id;
        param.lbmc = lbmc;
        param.lbmctype = lbmctype;
        param.pid = pid;
        param.ptype = ptype;
        param.px = px;
        $.ajax({
            url: "${ctx}/lb/updaterXlb.do",
            type: "post",
            // async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, offset: ["255px", "500px"], time: 1000});
                $('#ydnr').hide();
                layer.close(index3);
                table.reload('testReload2', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        })
    }

    function showChildrendLb() {
        var layer = layui.layer;
        index1 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '子类别列表',
            area: ['60%', '80%'], //宽高
            //closeBtn: 1,
            content: $('#ndrw'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (index, layero) {
                layer.close(index)
                $("#ndrw").hide();
                return false;
            }
        });
    }


    function editJdMb2() {
        var plbid = $("#plbid").val();
        var plbmc = $("#plbmc").val();
        var psslb = $("#psslb").val();
        if (psslb == '' || psslb == null) {
            alert("类别标识不能为空");
            return;
        }
        var param = {}
        param.id = plbid;
        param.lbmc = plbmc;
        param.lbtype = psslb;
        $.ajax({
            url: "${ctx}/lb/updaterDlb.do",
            type: "post",
            // async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: result.code, offset: ["255px", "500px"], time: 1000});
                $('#flbdetail').hide();
                //layer.closeAll();
                layer.close(index2);
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });

            }
        })
    }

    function insertPlb() {
        $("#plbid").val("");
        $("#plbmc").val("");
        $("#psslb").val("");
        openedit();
    }

    function insertZlb() {
        $("#zlbid").val("");
        $("#zlbmc").val("");
        $("#sslb").val($("#zlbnewType").val());
        $("#newPtype").val($("#zlbnewType").val());
        $("#newPid").val($("#zlbnewId").val());
        openChildrendEdit();
    }
</script>
<jsp:include page="../footer.jsp"></jsp:include>
