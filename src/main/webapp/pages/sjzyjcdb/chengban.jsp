<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<style>
    .layui-table-cell {
        height: 28px;
    }

    .layui-table-cell .layui-form-checkbox[lay-skin="primary"] {
        top: 50%;
        transform: translateY(-50%);
    }
    #uploadFiles a{
        color: blue;
    }

</style>
<div class="layui-fluid">
    <input type="hidden" id="idyc" value="${id}">
    <input type="hidden" id="whyc" value="${wh}">
    <input type="hidden" id="bllxyc" value="${bllx}">
    <input type="hidden" id="nodeidyc" value="${nodeid}">
    <input type="hidden" id="nodenameyc" value="${nodename}">
    <input type="hidden" id="cjryc" value="${cjr}">
    <input type="hidden" id="cbbmidyc" value="${cbbmid}">
    <input type="hidden" id="cbbmmcyc" value="${cbbmmc}">
    <input type="hidden" id="isReadOnly" value="${isReadOnly}">
    <input type="hidden" id="lclxyc" value="${lclx}">
    <input type="hidden" id="isfa" value="${isfa}">
    <input type="hidden" id="newId" value="">
    <input type="hidden" id="curname" value="${curname}">

    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="layui-card">
                <div class="layui-card-header">
                    立项信息
                </div>
                <div class="layui-card-body">
                    <iframe src="" id="djb_iframe" frameborder="0" style="width: 100%; height: 300px;"></iframe>
                </div>
            </div>
        </div>
        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    内勤分案：
                </div>
                <div class="layui-card-body">
                    <div class="table-wrapper">
                        <div class="layui-form">
                            <div class="layui-form-item ">
                                <label class="layui-form-label">内勤人</label>
                                <div class="layui-input-block">
                                    <input type="text" name="title" id="nqr" required lay-verify="required" placeholder="请输入标题"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item ">
                                <label class="layui-form-label">分案日期</label>
                                <div class="layui-input-block">
                                    <input type="text" name="title" id="farq"required lay-verify="required" placeholder="请输入标题"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>

        </div>


    </div>
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    督办办事项列表
                </div>
                <div class="layui-card-body">
                    <div class="layui-row layui-col-space15">
                        <div class="layui-col-md12">
                            <table id="table" lay-filter="table"></table>
                        </div>
                        <div class="layui-col-md6">
                            <div class="layui-form " id="addForm" style="display: none">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">承办日期</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="title" id="cbrq" required lay-verify="required"
                                               placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text">
                                    <label class="layui-form-label">承办描述</label>
                                    <div class="layui-input-block">
                                        <textarea name="desc" id="cbms" placeholder="请输入内容"
                                                  class="layui-textarea"></textarea>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">上传附件</label>
                                    <div class="layui-input-block">
                                        <span class="layui-btn layui-btn-primary" id="uploadCb">
                                            <i class="layui-icon layui-icon-upload"></i>上传附件
                                        </span>
                                    </div>
                                </div>
                                <div class="layui-row">
                                    <div class="layui-col-md12">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">上传文件列表：</label>
                                            <div class="layui-input-block">
                                                <ul class="files-wrapper" id="uploadFiles">
                                                </ul>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <hr>
                                <h4>部门负责人审核 :</h4>
                                <div class="layui-form-item cbBmfzr">
                                    <label class="layui-form-label">审核人</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="title" id="bmfzrmc" required lay-verify="required"
                                               placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item cbBmfzr">
                                    <label class="layui-form-label">审核日期 </label>
                                    <div class="layui-input-block">
                                        <input type="text" name="title" id="bmfzrsprq" required lay-verify="required"
                                               placeholder="" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item cbBmfzr">
                                    <label class="layui-form-label">审核意见</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="title" id="bmfzrspyj" required lay-verify="required"
                                               placeholder="请输入内容" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text cbBmfzr">
                                    <label class="layui-form-label">审核备注</label>
                                    <div class="layui-input-block">
                                        <textarea name="desc" id="bmfzrspbz" placeholder="请输入内容"
                                                  class="layui-textarea"></textarea>
                                    </div>
                                </div>
                                <div class="layui-form-item cbBmfzr">
                                    <div class="layui-input-block">
                                        <div class="layui-btn layui-btn-normal" onclick="saveSjjcSpyjCb()">保存</div>
                                    </div>
                                </div>

                                <input id="id" type="hidden" value=""/>
                                <input id="djid" type="hidden" value=""/>
                                <input id="dcsxid" type="hidden" value=""/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <hr>
            <div class="fr">
                <c:if test="${nodeid=='0106'}">
                <span class="layui-btn layui-btn-normal" onclick="submitPre(1)">提交</span>
                <span class="layui-btn layui-btn-primary" id="close">关闭</span></div>
                </c:if>
                <c:if test="${nodeid=='0107'}">
              <%--  <span class="layui-btn layui-btn-primary" onclick=" reback()">退回</span>--%>
                <span class="layui-btn layui-btn-primary" id="close">关闭</span></div>
                </c:if>
            </div>
    </div>

        <div class="layui-card-body" id="dczqdiv" style="display: none">
            <span style="color: red">注意：</span>填报时间为指定日期的前三天内(包括指定日期当天)。
            <table id="dczqtable"></table>
        </div>
        <div class="layui-card-body" id="wqckqdiv" style="display: none">
            <table id="wqcktable"></table>
        </div>
</div>
<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlowSjzyjc.js" language="javascript"></script>

<script>
    $(function () {
      var table = layui.table,
          layer = layui.layer,
          upload = layui.upload,
          form = layui.form;
          form.render();
        var path = '${ctx}/db/toshenhe.do?isReadOnly=1&id=' + $("#idyc").val() + '&wh=' + $("#whyc").val() + '&bllx=' + $("#bllxyc").val()
            + '&nodeid=8888&nodename=院领导审批&cjr=' + $("#cjryc").val() + '&cbbmid=' + $("#cbbmidyc").val() + '&cbbmmc=' + $("#cbbmmc").val() + '&lclx=' + $("#lclxyc").val() + '&isfa=' + $("#isfa").val();
        $('#djb_iframe').attr('src', encodeURI(path));

      //关闭
      $("#close").click(function(){
        //跳转待办页
        window.location.href = "/db/index.do?lclx=zyjc";
      });
        table.render({
            id: 'idTest',
            elem: '#table',
            url: '${ctx}/sjjccb/queryCbrDcsxList.do',
            page: true, //开启分页
            where: {"djid": $("#whyc").val(),"nodeid":$("#nodeidyc").val()},
            even: true,
            limit: 10,
            cols: [[
                {type: 'checkbox'},
               {field: 'zizeng', align: 'center', width: '5%', title: '序号', templet: '#zizeng'},
                {
                    field: 'dcsxmc',
                    align: 'center',
                    title: '督查事项',
                    event: 'dcsx',
                    width: '30%',
                    style: 'color:blue;cursor:pointer'
                },
                {
                    field: 'dczqtype',
                    align: 'center',
                    title: '督查周期',
                    event: 'dczq',
                    width: '10%',templet: function (d) {
                    var dczqtype = d.dczqtype;
                    var dcrq;
                    switch (dczqtype){
                      case "0":
                       dcrq="每周";
                        break;
                      case "1":
                       dcrq="每月";
                        break;
                      case "2":
                       dcrq="每季度";
                        break;
                      case "3":
                       dcrq="每半年";
                        break;
                      case "4":
                       dcrq="定制天数";
                        break;
                    }
                    return dcrq;
                  }
                }, {
                field: 'tbzq',
                align: 'center',
                title: '可填报时段',
                width: '20%'
              },
                {
                    field: 'iszz',
                    align: 'center',
                    title: '任务状态',
                    width: '15%',templet: function (d) {
                    var iszz = d.iszz;
                    if(iszz =='0'){
                      return "正常";
                    }else if(iszz =='1'){
                      return "已终止";
                    }
                  }
                },
                {field: 'dcsxid', align: 'center', title: '督查事项id', hide: true},
                {field: 'id', align: 'center', title: '主键', hide: true},
                {
                    field: 'cz', align: 'center',title: '操作', templet: function (d) {
                        var id = d.id;
                        var dcsxid = d.dcsxid;
                        var dczqtype = d.dczqtype;
                        var html = "<div>";
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"queryDcsxHistory('" +
                            id + "','" + dcsxid + "','" + dczqtype + "')\">" + "往期查看" + "</a>";
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"queryDcsxTbgz('" +
                        id + "','" + dcsxid + "','" + dczqtype + "')\">" + "填报时间规则" + "</a>";
                        return html;
                    }
                }
            ]]

        });

        //监听待办列表事件
        layui.use('table', function () {
            table.on('tool(table)', function (obj) {
                var data = obj.data;
                if (obj.event === 'dcsx') {
                  $("#nqr").val(data.nqusername);
                  $("#farq").val(data.farq);
                  if(data.iszz == '1'){
                    layer.msg("该督查事项已终止，无法填报！", {icon: 6,time:2000});
                    return;
                  }
                    $("#id").val(data.id);
                    $("#djid").val(data.djid);
                    $("#dcsxid").val(data.dcsxid);
                  var param = {
                    url: "/sjjccb/validateTb.do",
                    data: {"dcsxid": data.dcsxid, "dczqtype": data.dczqtype, "dcsxmc": data.dcsxmc},
                    success: function (data) {
                      if (data.code == "1") {
                      /*  $("#addForm").show();
                        querySjjcSpyjCb(obj.data.id);
                        //查询附件
                        selectUploadFiles( $("#id").val(),$("#cbrq").val());*/
                        cbmsTb(obj.data.id);
                      } else {
                        layer.msg("该事项无法填报，"+data.msg, {icon: data.code});
                      }
                    }
                  };
                  ajax(param);

                }
            });
        });

        //监听表格复选框选择
        table.on('checkbox(table)', function (obj) {
            var checkStatus = table.checkStatus('idTest');
            data = checkStatus.data;
            if(obj.type == 'one'){
              if(obj.checked){
                if( obj.data.iszz == '1'){
                  layer.msg("该督查事项已终止，无法提交！", {icon: 6,time:2000});
                  obj.tr.find('.layui-form-checked').click();
                  return;
                }
                var param = {
                  url: "/sjjccb/validateTb.do",
                  data: {"dcsxid": obj.data.dcsxid, "dczqtype": obj.data.dczqtype, "dcsxmc": obj.data.dcsxmc},
                  success: function (data) {
                    if (data.code == "1") {
                    } else {
                      layer.msg("该事项无法提交，"+data.msg, {icon: data.code,time:1500}, function () {
                        obj.tr.find('.layui-form-checked').click()
                      });
                    }
                  }
                };
                ajax(param);
              }

            }

        });
      //上传附件-承办
      upload.render({
        elem: '#uploadCb',
        url: '/jdtb/filesUploadDcdb.do', //上传接口
        data: {
            bmrwid: function () {
               var id = $("#id").val();
               return id;
         },
            ywtype: function () {
               var ywtype = $("#cbrq").val();
               return ywtype;
             }
        },
        accept: 'file',
        multiple: true,
        before: function () {
        },
        allDone: function (res) {
          //上传完毕回调
          var id = $("#id").val();
          var ywtype = $("#cbrq").val();
          selectUploadFiles(id,ywtype);
        },
        error: function () {

        }
      })

    })



</script>

<jsp:include page="../footer.jsp"></jsp:include>