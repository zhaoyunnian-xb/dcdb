<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
<%--填报进度明细页面（办公室使用）--%>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<input type="hidden" id="ndrwId" value="${ndid}">
<input type="hidden" id="status" value="${status}">
<input type="hidden" id="lcjd" value="${lcjd}">
<input type="hidden" id="cbbmId" value="${cbbmid}">
<input type="hidden" id="userid" value="${userid}">
<input type="hidden" id="tblx" value="${tblx}">
<input type="hidden" id="ssbm" value="${ssbm}">
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    填报明细查看
                </div>
                <div class="layui-card-body">
                    <div class="table-wrapper">
                        <table id="tabelOne" lay-filter="table_One" class="layui-table"></table>
                        </table>
                    </div>

                    <div id="ydnr" style="display: none; margin:10px">
                        <input type="hidden" id="rwmbId"/>
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
                            <%--  <div class="layui-input-block">
                                  <button class="layui-btn" onclick="newsave()"  id = "save">保存</button>
                                  <c:if test="${lcjd == 2}">
                               &lt;%&ndash;   <button type="reset" class="layui-btn layui-btn-primary" onclick="tijiao(2,'')" id = "reback">退回</button>&ndash;%&gt;
                                  <button type="reset" class="layui-btn layui-btn-primary" onclick="send()" id="through">审核通过</button>
                                  </c:if>
                                  <c:if test="${lcjd == 1}">
                                      <button type="reset" class="layui-btn layui-btn-primary" onclick="send()" id="through">提交</button>
                                  </c:if>
                              </div>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script type="text/html" id="switchTpl">
    <div style="height: auto;">
        <ul>
            <li style="margin: 10px 0;">
                <input type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="1" data-id="{{d.id}}"
                       data-bmlx="{{d.bmlx}}"
                       title="已完成" {{ d.yjdpg== '1' ? 'checked' : '' }} />
            </li>
            <li style="margin: 10px 0;">
                <input type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="2" data-id="{{d.id}}"
                       data-bmlx="{{d.bmlx}}"
                       title="达到序时进度" {{ d.yjdpg== '2' ? 'checked' : '' }} />
            </li>
            <li style="margin: 10px 0;">
                <input class="layui-input" type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="3"
                       data-id="{{d.id}}" data-bmlx="{{d.bmlx}}"
                       title="未达到序时进度" {{ d.yjdpg== '3' ? 'checked' : '' }} />
            </li>
            <li style="margin: 10px 0;">
                <input class="layui-input" type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="4"
                       data-id="{{d.id}}" data-bmlx="{{d.bmlx}}"
                       title="尚未启动" {{ d.yjdpg== '4' ? 'checked' : '' }} />
            </li>
        </ul>

    </div>


</script>


<script>
    var table = layui.table;
    var upload = layui.upload;
    var form = layui.form;

    var tblx = $("#tblx").val();
    var ndrwId = $("#ndrwId").val();
    var cbbmId = $("#cbbmId").val();
    var status = $("#status").val();
    var userid = $("#userid").val();
    var lcjd = $("#lcjd").val();
    var ssbm = $("#ssbm").val();

    $(function () {
        if (tblx == '0') {
            selectSpyj();
            var e = layer.load();
            var tableH = $('body').height() * 0.8;
            table.render({
                id: 'testReload',
                elem: '#tabelOne',
                url: '/jdtb/selectZyrwListTbjd.do', //数据接口
                where: {'ndid': ndrwId, 'status': status, 'cbbmid': cbbmId, 'userid': userid, 'ssbm': ssbm},
                loading: true,
                height: tableH,
                page: true, //开启分页
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
                    {field: 'zizeng', width: 60, title: '序号', templet: '#zizeng'},
                    {field: 'ydnrmc', title: '要点内容', minWidth: 200},
                    {field: 'zrld', title: '责任领导', width: 80,align:'center'},
                    {field: 'zyrwmc', title: '主要任务', minWidth: 200},
                    /*  {field: 'yjdmb', title: '一季度目标', minWidth:200},*/
                    {field: 'bnmb', title: '半年目标', minWidth: 200},
                    {field: 'sjdmb', title: '三季度目标', minWidth: 200},
                    {field: 'qnmb', title: '全年目标', minWidth: 200},
                    {field: 'ssbm', title: '牵头部门', width: 100, align:'center'},
                    {field: 'phbm', title: '配合部门', width: 100, align:'center'}

                ]]
            });

            $("textarea[class='layui-textarea']").attr('disabled', 'true');

        } else {

            //加载列表
            loadTableListTbjd(ndrwId, status, cbbmId, true, false, false);

            //查询意见
            queryPsyj(ndrwId, status, cbbmId);


            //判断意见框是否可编辑
            $("#yj_div input[class='layui-input']").each(function () {
                $(this).attr('disabled', 'true');
            })

        }
        disableKj(status);
    })


    function selectSpyj() {
        var param = {};
        param.ndId = ndrwId;
        param.cbbmId = cbbmId;
        $.ajax({
            url: "${ctx}/mbsp/selectSpyj.do",
            type: "post",
            data: param,
            dataType: "json",
            success: function (result) {
                $("#bmspyj").val(result.dcdbkhpz.bmmbfzryj);
                $("#bmspqm").val(result.dcdbkhpz.bmfzrqm);
                $("#bmsprq").val(result.dcdbkhpz.bmfzrrq);

                $("#bgsspyj").val(result.dcdbkhpz.bgsmbyj);
                $("#bgsspqm").val(result.dcdbkhpz.bgsqm);
                $("#bgssprq").val(result.dcdbkhpz.bgsrq);

                $("#bgsldpyj").val(result.dcdbkhpz.bgsldmbyj);
                $("#bgsldpqm").val(result.dcdbkhpz.bgsfzrqm);
                $("#bgsldprq").val(result.dcdbkhpz.bgsfzrrq);

            }
        })
    }

    //查询意见
    function queryPsyj(ndid, status, cbbmid) {
        var ssbm = $("#ssbm").val();
        var param = {
            url: '/jdtb/queryPsyj.do',
            data: {'ndid': ndid, 'status': status, 'cbbmid': cbbmid, 'ssbm': ssbm},
            success: function (data) {
                if (data.code != '1') {
                    layer.msg(data.msg, {icon: data.code});
                }

                $("#bmspyj").val(data.data.bmfzryj);
                $("#bgsspyj").val(data.data.bgsyj);
                $("#bgsldpyj").val(data.data.bgsfzryj);

                $("#bmspqm").val(data.data.bmfzrqm);
                $("#bgsspqm").val(data.data.bgsqm);
                $("#bgsldpqm").val(data.data.bgsfzrqm);

                $("#bmsprq").val(data.data.bmfzrrq);
                $("#bgssprq").val(data.data.bgsrq);
                $("#bgsldprq").val(data.data.bgsfzrrq);


            },
        };
        ajax(param);
    }

    function disableKj(status) {
        $("input[type='text']").attr('disabled', 'true');
        $("input[type='radio']").attr('disabled', 'true');
        $("input[type='file']").attr('disabled', 'true');
        $("textarea[class='layui-textarea']").attr('disabled', 'true');
        $("button").hide(); //保存、提交按钮隐藏
    }


    function loadTableListTbjd(ndid, status, cbbmid, hid_upload, hid_fj, hid_xnpg) {
        var ssbm = $("#ssbm").val();
        var jd = To_SimplifiedChinese(status.substring(0, 1));
        var tableH = $('body').height() * 0.8;
        // table render
        table.render({
            id: 'testReload',
            elem: '#tabelOne'
            , url: '/jdtb/selectZyrwListTbjd.do' //数据接口
            , where: {'ndid': ndrwId, 'status': status, 'cbbmid': cbbmId, 'userid': userid, 'ssbm': ssbm}
            , page: true //开启分页
            , height:tableH
            , cols: [[ //表头
                {field: 'ydnrmc', title: '要点内容', minWidth:200},
                {field: 'zrld', title: '责任领导', width: 80,align:'center'},
                {field: 'zyrwmc', title: '主要任务', minWidth:200},
                {field: 'jdmb', title: jd + '季度目标', minWidth:200},
                {field: 'yjdwcqk', title: jd + '季度完成情况', event: 'setWcqk', style: 'cursor: pointer;', minWidth:200},
                {field: 'ssbm', title: '牵头部门' ,width: 100,align:'center'},
                {field: 'phbm', title: '配合部门', width: 100,align:'center'},
                {
                    field: 'classify', hide: hid_upload, width: 120, title: '上传附件',
                    templet: function (d) {
                        return '<div><button class="layui-btn layui-btn-sm upload-btn" \n' +
                            '                    id="test1" lay-event="upload" data-id="' + d.id + '" data-bmlx="' + d.bmlx + '"  lay-data="{data:{bmrwid:\'' + d.id + '\', ywtype: ' + status.substring(0, 1) + ' }}">上传文件</button></div>'
                    }
                },
                {
                    field: 'fj', title: '附件', hide: hid_fj, width: 120, templet: function (d) {
                        var html = '<ul>';
                        for (var i = 0; i < d.file.length; i++) {
                            html += '<li><a href="/jdtb/downLoadFileDcdb.do?id=' + d.file[i].id + '&ywtype=' + status.substring(0, 1) + '">' + d.file[i].filename + '</a></li>';
                        }
                        html += '</ul>';
                        return html;
                    }
                },
                {field: 'yjdpg', title: '效能评估', hide: hid_xnpg, width: 160, templet: '#switchTpl'}

            ]],
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
            done: function (res, curr, count) {
                var tableElem = this.elem.next('.layui-table-view');
                var uploadInst = upload.render({
                    elem: '.upload-btn',
                    url: '/jdtb/filesUploadDcdb.do', //上传接口
                    data: {
                        // bmrwid: function () {
                        //                     //     debugger;
                        //                     //   // var id = uploadInst.config.item.data('id');
                        //                     //   var id = $('#test1').data('id');
                        //                     //     return id;
                        //                     // },
                        //   ywtype: function () {
                        //       var ywtype = status.substring(0, 1);
                        //       return ywtype;
                        //   }
                    },
                    accept: 'file',
                    multiple: true,
                    before: function () {
                        /*var bmlx = uploadInst.config.item.data('bmlx');
                       if(bmlx=="ph"){
                           console.log(bmlx);
                           layer.msg("对于该任务来说，您所在部门属于配合部门，不允许上传证明材料！", {icon: 1});
                           return false;
                       }*/
                    },
                    allDone: function (res) {
                        //上传完毕回调, 执行重载
                        table.reload('testReload', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }

                        });
                    },
                    error: function () {

                    }
                })

            }


        });
    }

    function To_SimplifiedChinese(Num) {
        var tmpnewchar;
        switch (Num) {
            case "0":
                tmpnewchar = "零";
                break;
            case "1":
                tmpnewchar = "一";
                break;
            case "2":
                tmpnewchar = "二";
                break;
            case "3":
                tmpnewchar = "三";
                break;
            case "4":
                tmpnewchar = "四";
                break;
            case "5":
                tmpnewchar = "五";
                break;
            case "6":
                tmpnewchar = "六";
                break;
            case "7":
                tmpnewchar = "七";
                break;
            case "8":
                tmpnewchar = "八";
                break;
            case "9":
                tmpnewchar = "九";
                break;
        }

        return tmpnewchar;
    }

    var ajax = function (param) {
        $.ajax({
            url: param.url,
            type: "post",
            async: false,
            data: param.data,
            dataType: "json",
            success: param.success,
            error: function () {
                layer.closeAll();
                layer.msg("操作失败", {icon: 2});
            }
        });
    };
</script>




