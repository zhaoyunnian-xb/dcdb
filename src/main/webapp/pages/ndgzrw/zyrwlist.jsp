<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
<%--全年目标填报（填报部门使用）--%>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<input type="hidden" id="ndrwId" value="${ndid}">
<input type="hidden" id="status" value="${status}">
<input type="hidden" id="lcjd" value="${lcjd}">
<input type="hidden" id="cbbmId" value="${cbbmid}">
<input type="hidden" id="currentUserName" value="${currentUserName}">
<input type="hidden" id="currentdate" value="${currentdate}">
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    工作任务部门目标填报
                </div>

                <div class="layui-card-body">
                    <div class="table-wrapper">
                        <div style="text-align: right;"><span class="layui-btn layui-btn-normal"
                                                              onclick="exportExcel()">导出excel
                        </span></div>
                        <table id="tabelOne" lay-filter="table_One" class="layui-table"></table>
                        </table>
                    </div>

                    <div id="ydnr" style="display: none; margin:10px">
                        <input type="hidden" id="rwmbId"/>
                        <div style="color: red;margin-bottom: 15px">注意：每个季度的目标都需要填写，然后一并提交审批！</div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">第二季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="bnmb" class="layui-textarea jdmb" placeholder="最多输入200个字"
                                          onblur="check(this.value)"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">第三季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="sjdmb" class="layui-textarea jdmb" placeholder="最多输入200个字"
                                          onblur="check(this.value)"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">第四季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="qnmb" class="layui-textarea jdmb" placeholder="最多输入200个字"
                                          onblur="check(this.value)"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" id="savebtn"
                                        onclick="editJdMb()">确认
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form" id="yj_div">
                        <div class="layui-form-item layui-form-text" id="bmsp">
                            <label class="layui-form-label">部门负责人</br>审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_2" placeholder="" class="layui-textarea" id="bmspyj"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper">
                            <label class="layui-form-label">签名:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="desc_2" class="layui-input" id="bmspqm">
                            </div>
                            <label class="layui-form-label">日期:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="desc_2" class="layui-input" id="bmsprq">
                            </div>
                        </div>

                        <div class="layui-form-item layui-form-text" id="bgssp">
                            <label class="layui-form-label">办公室</br>复核备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_3" placeholder="" class="layui-textarea" id="bgsspyj"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper">
                            <label class="layui-form-label">签名:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="desc_3" class="layui-input" id="bgsspqm">
                            </div>
                            <label class="layui-form-label">日期:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="desc_3" class="layui-input" id="bgssprq">
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text" id="bgsldsp">
                            <label class="layui-form-label">办公室负责人</br>审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_4" placeholder="" class="layui-textarea" id="bgsldpyj"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper">
                            <label class="layui-form-label">签名:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bgsldpqm" name="desc_4">
                            </div>
                            <label class="layui-form-label">日期:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bgsldprq" name="desc_4">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-normal" onclick="newsave()" id="save">保存</button>
                                <c:if test="${lcjd == 2}">
                                    <%--   <button type="reset" class="layui-btn layui-btn-primary" onclick="tijiao(2,'')" id = "reback">退回</button>--%>
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="send()"
                                            id="through">审核通过
                                    </button>
                                </c:if>
                                <c:if test="${lcjd == 1}">
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="send()"
                                            id="through">提交
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script>
    var index2 = "";
    var table = layui.table;
    var ndrwId = $("#ndrwId").val();
    var cbbmId = $("#cbbmId").val();
    var lcjd = $("#lcjd").val();
    var currentUserName = $("#currentUserName").val();
    var currentdate = $("#currentdate").val();
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#bmsprq', //指定元素
            format: 'yyyy年MM月dd日',
        });
        laydate.render({
            elem: '#bgssprq', //指定元素
            format: 'yyyy年MM月dd日',
        });
        laydate.render({
            elem: '#bgsldprq', //指定元素
            format: 'yyyy年MM月dd日',

        });
    })

    function check(va) {
        if (va.length > 200) {
            layer.msg("输入字数超过200，请修改", {icon: 2, time: 1000, offset: ['255px', '500px']});
        }

    }

    $(function () {

        selectSpyj();
        var e = layer.load();
        var tableH = ($('body').height())*0.8;
        //tabelOne
        table.render({
            id: 'testReload',
            elem: '#tabelOne',
            height: tableH,
            url: '${ctx}/mbsp/queryZyrwList.do?ndrwId=' + ndrwId + '&cbbmId=' + cbbmId, //数据接口
            loading: true,
            page: {
                limit: 10,
                layout: ['prev', 'page', 'next', 'skip', 'count'],
            }, //开启分页
            done: function () {
                layer.close(e);
            },
            parseData: function(res) {
                var data = res.data;
                $.each(data, function (index,item) {
                    if (item.phbm) {
                        item.phbm = item.phbm.replace(/;/g, '<br>')
                    }
                    if (item.ssbm) {
                        item.ssbm = item.ssbm.replace(/;/g, '<br>')
                    }
                    if (item.zrld) {
                        item.zrld = item.zrld.replace(/,/g, '<br>')
                    }
                });
                return {
                    code: res.code,
                    msg: res.msg,
                    count: res.count,
                    data: data
                }

            },
            cols: [[ //表头
                /* {field: 'ID', title: '序号', width:'8%', align:'center'},*/
                {field: 'zizeng', width: '40', title: '序号', templet: '#zizeng', align: 'center'},
                {field: 'ydnrmc', title: '要点内容', width: '15%'},
                {field: 'zrld', title: '责任领导', width: '80', align: 'center'},
                {field: 'zyrwmc', title: '主要任务', width: '15%'},
                {field: 'bnmb', title: '半年目标', width: '15%'},
                {field: 'sjdmb', title: '三季度目标', width: '15%'},
                {field: 'qnmb', title: '全年目标', width: '15%'},
                {field: 'ssbm', title: '牵头部门', width: '100', align: 'center'},
                {field: 'phbm', title: '配合部门', width: '80', align: 'center'},
                {
                    field: 'bmlx', title: '操作', width: '100', align: 'center', templet: function (d) {
                        var id = d.id;
                        if (d.bmlx == 'zz' || d.bmlx == 'qt') {
                            return "<a class='layui-btn layui-btn-sm layui-btn-normal' href='javascript:void(0)' onclick=\"editrw('" + id + "')\">" + "编辑" + "</a>";
                        } else {
                            return "<span>配合部门不可编辑</span>";
                        }
                    }
                }
            ]]
        });
        //判断意见框是否可编辑
        $("#yj_div textarea[class='layui-textarea']").each(function () {
            var name = $(this).attr('name');
            if (name.split("_")[1] != lcjd) {
                $(this).attr('disabled', 'true');
            }
        })
        $("#yj_div input[class='layui-input']").each(function () {
            var name = $(this).attr('name');
            if (name.split("_")[1] != lcjd) {
                $(this).attr('disabled', 'true');
            }
        })
    })

    function editrw(id) {
        $.ajax({
            url: "${ctx}/mbsp/detail.do",
            type: "post",
            // async: false,
            data: {"id": id},
            dataType: "json",
            success: function (result) {
                if (result != null) {
                    $("#rwmbId").val(result.id);
                    $("#yjdmb").val(result.yjdmb);
                    $("#bnmb").val(result.bnmb);
                    $("#sjdmb").val(result.sjdmb);
                    $("#qnmb").val(result.qnmb)
                }
                openedit();
            }
        })

        // window.location.href="${ctx}/mbsp/detail.do?id="+id;
    }

    function save(type, userId, lcjd) {
        //承办人提交流程  ： type:2  userId :非空  lcjd;1

        if (lcjd == '1') {
            if (type == 2) {
                var nextuserId = userId;
                var bmspyj = "";
                var bmspqm = "";
                var bmsprq = "";
                var bgsspyj = "";
                var bgsspqm = "";
                var bgssprq = "";
                var bgsldpyj = "";
                var bgsldpqm = "";
                var bgsldprq = "";

            }
        } else if (lcjd == '2') {
            if (type == 1) {
                var bmspyj = $("#bmspyj").val();
                var bmspqm = $("#bmspqm").val();
                var bmsprq = $("#bmsprq").val();
                var nextuserId = userId;
                var bgsspyj = "";
                var bgsspqm = "";
                var bgssprq = "";
                var bgsldpyj = "";
                var bgsldpqm = "";
                var bgsldprq = "";
            }
            if (type == 2) {
                var bmspyj = $("#bmspyj").val();
                var bmspqm = $("#bmspqm").val();
                var bmsprq = $("#bmsprq").val();
                var nextuserId = userId;
                var bgsspyj = "";
                var bgsspqm = "";
                var bgssprq = "";
                var bgsldpyj = "";
                var bgsldpqm = "";
                var bgsldprq = "";
            }
        }


        var param = {};
        param.ndrwId = ndrwId;
        param.cbbmId = cbbmId;
        param.bmspyj = bmspyj;
        param.bmspqm = bmspqm;
        param.bmsprq = bmsprq;
        param.bgsspyj = bgsspyj;
        param.bgsspqm = bgsspqm;
        param.bgssprq = bgssprq;
        param.bgsldpyj = bgsldpyj;
        param.bgsldpqm = bgsldpqm;
        param.bgsldprq = bgsldprq;
        param.nextuserId = nextuserId;
        param.lcjd = lcjd;
        param.type = type;
        $.ajax({
            url: "${ctx}/mbsp/savespyj.do",
            type: "post",
            // async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                if (lcjd == '1') {
                    if (result == '1') {
                        //承办人点击提交按钮：保存以后 进行数据的承办人和状态的更新
                        var zt = $("#status").val();
                        commontijiao(userId, ndrwId, type, zt, cbbmId, lcjd)
                    }
                }
                if (lcjd == '2') {
                    if (type == 1) {
                        //部门负责人保存
                        if (result == '1') {
                            layer.msg("保存成功", {icon: 1, offset: ['255px', '500px'], time: 1000});

                        }
                    } else if (type == 2) {
                        //部门负责人提交
                        if (result == '1') {
                            //部门负责人点击提交按钮：保存以后 进行数据的承办人和状态的更新
                            var zt = $("#status").val();
                            commontijiao(userId, ndrwId, type, zt, cbbmId, lcjd)
                        }
                    }

                }


                if (type == 1) {
                    if (result == '1') {
                        layer.msg("编辑成功", {icon: 1, offset: ['255px', '500px'], time: 1000});
                    }
                } else if (type == 2) {
                    if (result == '1') {
                        var nextuserId = ""; //下一级处理人

                    }
                }

            }
        })

    }

    function tijiao(type, userId, lcjd) {//type:  1:提交  2：退回    userId：下一级审批人  lcjd:流程处理节点
        // lcjd : 1  type:1, userId;userId
        if (lcjd == '1') {
            if (type == 1) {
                save(2, userId, lcjd);
            }
        }
        if (lcjd == '2') {
            if (type == 1) {
                save(2, userId, lcjd);
            }
        }

    }

    function selectSpyj() {
        var param = {};
        param.ndId = ndrwId;
        param.cbbmId = cbbmId;
        $.ajax({
            url: "${ctx}/mbsp/selectSpyj.do",
            type: "post",
            // async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                if (result.dcdbkhpz != null) {
                    $("#bmspyj").val(result.dcdbkhpz.bmmbfzryj)
                    if (lcjd == '2') {
                        if (result.dcdbkhpz.bmfzrqm == "" || result.dcdbkhpz.bmfzrqm == null) {
                            $("#bmspqm").val(currentUserName);
                        } else {
                            $("#bmspqm").val(result.dcdbkhpz.bmfzrqm);
                        }
                        if (result.dcdbkhpz.bmfzrrq == "" || result.dcdbkhpz.bmfzrrq == null) {
                            $("#bmsprq").val(currentdate);
                        } else {
                            $("#bmsprq").val(result.dcdbkhpz.bmfzrrq);
                        }

                    }
                    /* $("#bgsspyj").val(result.bgsmbyj);
                     $("#bgsspqm").val(result.bgsqm);
                     $("#bgssprq").val(result.bgsrq);
                     $("#bgsldpyj").val(result.bgsldmbyj);
                     $("#bgsldpqm").val(result.bgsfzrqm);
                     $("#bgsldprq").val(result.bgsfzrrq);*/
                } else {
                    if (lcjd == '2') {
                        $("#bmspqm").val(currentUserName);
                        $("#bmsprq").val(currentdate);
                    }
                    if (lcjd == '3') {
                        $("#bgsspqm").val(currentUserName);
                        $("#bgssprq").val(currentdate);
                    }
                    if (lcjd == '4') {
                        $("#bgsldpqm").val(currentUserName);
                        $("#bgsldprq").val(currentdate);
                    }
                }
            }
        })
    }

    function reback() {
        save(2);
    }

    function openedit() {
        index2 = layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '年度目标填写',
            offset: ['30px'],
            area: ['60%', '80%'], //宽高
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
        var rwmbId = $("#rwmbId").val();
        var yjdmb = $("#yjdmb").val();
        var bnmb = $("#bnmb").val();
        var sjdmb = $("#sjdmb").val();
        var qnmb = $("#qnmb").val();
        /* if(yjdmb.length > 200){
             layer.msg("第一季度目标输入字数超过200，请修改",{icon:2,time:1000,offset: ['255px', '500px']});
             return;
         }*/
        if (bnmb.length > 200) {
            layer.msg("第二季度目标输入字数超过200，请修改", {icon: 2, time: 1000, offset: ['255px', '500px']});
            return;
        }
        if (sjdmb.length > 200) {
            layer.msg("第三季度目标输入字数超过200，请修改", {icon: 2, time: 1000, offset: ['255px', '500px']});
            return;
        }
        if (qnmb.length > 200) {
            layer.msg("第四季度目标输入字数超过200，请修改", {icon: 2, time: 1000, offset: ['255px', '500px']});
            return;
        }
        var param = {}
        param.rwmbId = rwmbId;
        param.yjdmb = yjdmb;
        param.bnmb = bnmb;
        param.sjdmb = sjdmb;
        param.qnmb = qnmb;
        $.ajax({
            url: "${ctx}/mbsp/updaterwmb.do",
            type: "post",
            // async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                if (result == '1') {
                    layer.msg("编辑成功", {icon: 1, time: 1000});
                    $('#ydnr').hide();
                    layer.closeAll();
                    table.reload('testReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }

                    });
                }
            }
        })

    }

    function send() {
        var layer = layui.layer;
        var lcjd = $("#lcjd").val();
        var flag = 'normal';
        if (lcjd == '2') {
            flag = 'dcs';
        }
        layer.open({
            type: 2,
            title: '请选择发送人',
            content: '/pages/dcdblc/transforEle.jsp?isMul=false&flag=' + flag,
            area: ['60%', '80%'],
            offset: ['30px'],
            scrollbar: false,
            btn: ['确认'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getPersonArr();
                var str = "";
                var str2 = "";
                var userid = "";
                $.each(a, function (i, item) {
                    userid = item.selfid;
                    username = item.selfname;
                    var depid = item.parentid;
                    var depname = item.parentname;
                    str += depid + "@" + depname + "@" + userid + "@" + username + ";";
                    str2 += username;
                })
                if (userid == '') {
                    layer.alert("请选择下一级审批人！");
                    layer.close(index);
                    return false;
                }
                if (lcjd == '1') {
                    tijiao(1, userid, lcjd);
                }
                if (lcjd == '2') {
                    tijiao(1, userid, lcjd);
                }
                //tijiao(1,userid); //提交逻辑
                layer.close(index);
            }
        })


    }


    function commontijiao(nextuserId, ndrwId, type, zt, cbbmId, lcjd) {
        var param = {};
        param.nextuserId = nextuserId;
        param.ndrwId = ndrwId;
        param.type = type;
        param.zt = zt;
        param.cbbmId = cbbmId;
        param.lcjd = lcjd;
        $.ajax({
            url: "${ctx}/mbsp/tijiaoSpyj.do",
            type: "post",
            // async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg, {icon: 1, offset: ['255px', '500px'], time: 1000}, function () {
                    //  if( type !=1){
                    window.location.href = "../../../pages/ndgzrw/bmrwlist.jsp";
                    //  }
                })
            }
        })
    }

    function newsave() {
        var lcjd = $("#lcjd").val();
        save(1, '', lcjd)
    }

    function onkeydown(val) {
        if (val.length > 200) {
            layer.msg("输入字数已达200字", {icon: 1, time: 1000})
            return;
        }
    }


    function exportExcel() {
        window.location.href = "${ctx}/jdtb/exportExcel.do?ndrwId=" + ndrwId + "&status=" + status + "&cbbmId=" + cbbmId + "&flag=jdmb"
    }
</script>




