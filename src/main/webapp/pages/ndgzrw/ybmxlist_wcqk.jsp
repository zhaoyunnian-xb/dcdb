<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp"></jsp:include>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <input type="hidden" id="ndid" value="${ndid}"/>
        <input type="hidden" id="status" value="${status}"/>
        <input type="hidden" id="cbbmid" value="${cbbmid}"/>
        <input type="hidden" id="name" value="${name}"/>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header" id="bt_id">
                </div>
                <span class="layui-btn layui-btn-normal" onclick="exportExcel()">导出excel</span>
                <div class="layui-card-body">
                    <div class="table-wrapper">
                        <table id="demo" lay-filter="demoEvent">

                        </table>
                    </div>
                    <div class="layui-form" id="yj_div">
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">部门负责人审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_6" id="bmfzryj" placeholder=""
                                          class="layui-textarea"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper" >
                            <label class="layui-form-label">签名:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bmfzrqm" name="desc_6">
                            </div>
                            <label class="layui-form-label">日期:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bmfzrrq" name="desc_6">
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">办公室审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_7" id="bgsyj" placeholder="" class="layui-textarea"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper" >
                            <label class="layui-form-label">签名:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bgsqm" name="desc_7">
                            </div>
                            <label class="layui-form-label">日期:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bgsrq" name="desc_7">
                            </div>
                        </div>

                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">办公室负责人审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_8" id="bgsfzryj" placeholder=""
                                          class="layui-textarea"></textarea>
                            </div>
                        </div>

                        <div class="layui-form-item layui-form-text qm-wrapper" >
                            <label class="layui-form-label">签名:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bgsfzrqm" name="desc_8">
                            </div>
                            <label class="layui-form-label">日期:
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="bgsfzrrq" name="desc_8">
                            </div>
                        </div>
                        <div class="layui-form-item">
                           <%-- <div class="layui-input-block">
                                <button class="layui-btn" onclick="send('save')">保存</button>
                                &lt;%&ndash;<button type="reset" class="layui-btn layui-btn-primary" onclick="send('rebak')">退回
                                </button>&ndash;%&gt;
                                <c:if test="${status.substring(2,3)=='5'}">
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="send('submit')">提交</button>
                                </c:if>
                                <c:if test="${status.substring(2,3)=='6'}">
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="send('submit')">审核通过</button>
                                </c:if>
                            </div>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbJdwcqk.js" language="javascript"></script>

<script type="text/html" id="test-table-operate-barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="upload" id="test1">上传文件</a>
</script>
<script type="text/html" id="switchTpl">
    <div style="height: auto;">
        <ul>
            <li>
                <input type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="1" data-id="{{d.id}}"
                       title="已完成" {{ d.yjdpg== '1' ? 'checked' : '' }} />
            </li>
            <li>
                <input  type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="2" data-id="{{d.id}}"
                       title="达到序时进度" {{ d.yjdpg== '2' ? 'checked' : '' }} />
            </li>
            <li>
                <input class="layui-input" type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="3" data-id="{{d.id}}"
                       title="未达到序时进度" {{ d.yjdpg== '3' ? 'checked' : '' }} />
            </li>
            <li>
                <input class="layui-input" type="radio" lay-filter='xgpg' name="xgpg{{d.id}}" value="4" data-id="{{d.id}}"
                       title="尚未启动" {{ d.yjdpg== '4' ? 'checked' : '' }} />
            </li>
        </ul>


    </div>


</script>
<script>
    $(function () {
        var ndid=$("#ndid").val();
        var status=$("#status").val();
        var cbbmid=$("#cbbmid").val();

        //加载列表
       loadTableListYb(ndid,status,cbbmid,true,false,true);


        //查询意见
        queryPsyj(ndid,status,cbbmid);

        $("#bt_id").html("工作任务部门完成情况已办("+To_SimplifiedChinese(status.substring(0,1))+"季度)");

        //判断意见框是否可编辑
        $("#yj_div input[class='layui-input']").each(function () {
                $(this).attr('disabled', 'true');
        })

        disableKj(status);

    })

    function disableKj(status) {
            $("input[type='text']").attr('disabled', 'true');
            $("input[type='radio']").attr('disabled', 'true');
            $("input[type='file']").attr('disabled', 'true');
            $("textarea[class='layui-textarea']").attr('disabled','true');
            $("button").hide(); //保存、提交按钮隐藏
    }

    function exportExcel() {
      var ndrwId=$("#ndid").val();
      var status=$("#status").val();
      var cbbmId=$("#cbbmid").val();
      window.location.href="${ctx}/jdtb/exportExcel.do?ndrwId="+ndrwId+"&status="+status+"&cbbmId="+cbbmId+"&flag=wcqk"
    }


    function loadTableListYb(ndid, status, cbbmid, hid_upload, hid_fj, hid_xnpg) {
      var jd = To_SimplifiedChinese(status.substring(0, 1));
      // table render
      table.render({
        id: 'testReload',
        elem: '#demo'
        , url: '/jdtb/queryZyrwListYb.do' //数据接口
        , where: {'ndid': ndid, 'status': status, 'cbbmid': cbbmid}
        , page: true //开启分页
        , cols: [[ //表头
          {field: 'ydnrmc', title: '要点内容'},
          {field: 'zrld', title: '责任领导', width: 100},
          {field: 'zyrwmc', title: '主要任务'},
          {field: 'jdmb', title: jd + '季度目标'},
          {field: 'yjdwcqk', title: jd + '季度完成情况', event: 'setWcqk', style: 'cursor: pointer;'},
          {field: 'ssbm', title: '牵头部门'},
          {field: 'phbm', title: '配合部门'},
          {
            field: 'classify', hide: hid_upload, width:120, title: '上传附件',
            templet: function (d) {
              return '<div><button class="layui-btn layui-btn-sm upload-btn" \n' +
                  '                    id="test1" lay-event="upload" data-id="' + d.id + '" data-bmlx="' + d.bmlx + '"  lay-data="{data:{bmrwid:\'' +d.id+'\', ywtype: '+status.substring(0, 1)+' }}">上传文件</button></div>'
            }
          },
          {
            field: 'fj', title: '附件', hide: hid_fj,  width:120,templet: function (d) {
              var html = '<ul>';
              for (var i = 0; i < d.file.length; i++) {
                html += '<li><a href="/jdtb/downLoadFileDcdb.do?id=' + d.file[i].id + '&ywtype=' + status.substring(0, 1) + '">' + d.file[i].filename + '</a></li>';
              }
              html += '</ul>';
              return html;
            }
          },
          {field: 'yjdpg', title: '效能评估', hide: hid_xnpg, width:160, templet: '#switchTpl'}

        ]],
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

</script>

<jsp:include page="../footer.jsp"></jsp:include>