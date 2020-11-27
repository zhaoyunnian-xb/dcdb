<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/static/plugin/layui-v2.4.3/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/css/dcdb/admin-dcdb.css" media="all">
    <link rel="stylesheet" href="${ctx}/css/dcdb/dcdbView.css">
    <script src="${ctx}/static/plugin/jquery/jquery-3.3.1.min.js"></script>
    <script src="${ctx}/static/plugin/layui-v2.4.3/layui/layui.all.js"></script>
    <style>
        .laybtm{
            background-color: #f7f9fd;
            margin-top: 7px;
        }
    </style>
</head>
<body>
<div class="layui-col-md12">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="dcdb-chuhe-2">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md12">
                        <div class="form form-2 layui-form">
                            <div class="layui-form-item layui-form-text">
                                <h3 style="text-align: center;font-size: 20px;">党组会决定事项通知单/检察长办公会决定事项通知单</h3>
                            </div>
                            <c:if test="${readOnly=='0'}">
                            <div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>会议纪要：</lable>
                                <div class="layui-input-block">
                                    <lable id="filehyjy"></lable>
                                    <span class="admin-save-btn upload-hyjy" id="hyjytext">上传会议纪要</span>
                                    <lable><span style="color:red;">(只能上传doc,docx格式文件)</span></lable>
                                    <lable><a style="cursor:pointer;color:#0d1bff;" href="/dcdb/downloadmb.do">下载模板</a></lable>
                                </div>
                            </div>
                            </c:if>
                            <div class="layui-row layui-col-space10 layui-form-item">
                                <div class="layui-col-md6 layui-col-xs6">
                                    <label class="layui-form-label"><span style="color:red;"></span>会议名称：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="hymc" id="hymc" class="layui-input" value="${dcdb.hymc}"/>
                                    </div>
                                </div>
                                <div class="layui-col-md6 layui-col-xs6">
                                    <label class="layui-form-label"><span style="color:red;"></span>会议时间：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="hysj" id="hysj" class="layui-input" value="${dcdb.hysj}"/>
                                    </div>
                                </div>
                                <div class="layui-col-md6 layui-col-xs6">
                                    <label class="layui-form-label"><span style="color:red;"></span>会议议题：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="yjyt" id="yjyt" class="layui-input" value="${dcdb.yjyt}"/>
                                    </div>
                                </div>
                                <div class="layui-col-md6 layui-col-xs6">
                                    <label class="layui-form-label"><span style="color:red;"></span>系统编号：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="id" id="id" class="layui-input" value="${id}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>会议决定事项：</lable>
                                <div class="layui-input-block">
                                    <textarea name="hyjdsx" id="hyjdsx"  class="layui-textarea">${dcdb.hyjdsx}</textarea>
                                </div>
                            </div>
                            <div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>会议督办事项：</lable>
                                <div class="layui-input-block">
                                    <textarea name="dbsx" id="dbsx" class="layui-textarea">${dcdb.dbsx}</textarea>
                                </div>
                            </div>
                            <div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>任务要求：</lable>
                                <div class="layui-input-block">
                                    <input type="text" name="rwyq" id='rwyq' class="layui-input" value="${dcdb.rwyq}"/>
                                </div>
                            </div>
                            <div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>办理期限：</lable>
                                <div class="layui-input-block">
                                    <input type="text" name="blqx" id='blqx' class="layui-input" value="${dcdb.blqx}"/>
                                </div>
                            </div>
                            <%--<div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>办理部门：</lable>
                                <div class="layui-input-block">
                                    <select name="blbm" id="blbm" lay-filter="blbm">
                                        <option value="">请选择办理部门</option>
                                        &lt;%&ndash;<option value="公诉科" <c:if test="${dcdb.blbm == '公诉科'}">selected</c:if>>公诉科</option>
                                        <option value="侦查监督科" <c:if test="${dcdb.blbm == '侦查监督科'}">selected</c:if>>侦查监督科</option>
                                        <option value="民事行政科" <c:if test="${dcdb.blbm == '民事行政科'}">selected</c:if>>民事行政科</option>&ndash;%&gt;
                                        <option value="办公室" <c:if test="${dcdb.blbm == '办公室'}">selected</c:if>>办公室</option>
                                        <option value="督办室" <c:if test="${dcdb.blbm == '督办室'}">selected</c:if>>督办室</option>
                                        <option value="公诉一处" <c:if test="${dcdb.blbm == '公诉一处'}">selected</c:if>>公诉一处</option>
                                        <option value="公诉二处" <c:if test="${dcdb.blbm == '公诉二处'}">selected</c:if>>公诉二处</option>
                                        <option value="公诉三处" <c:if test="${dcdb.blbm == '公诉三处'}">selected</c:if>>公诉三处</option>
                                        <option value="侦监一处" <c:if test="${dcdb.blbm == '侦监一处'}">selected</c:if>>侦监一处</option>
                                        <option value="侦监二处" <c:if test="${dcdb.blbm == '侦监二处'}">selected</c:if>>侦监二处</option>
                                        <option value="民行一处" <c:if test="${dcdb.blbm == '民行一处'}">selected</c:if>>民行一处</option>
                                        <option value="民行二处" <c:if test="${dcdb.blbm == '民行二处'}">selected</c:if>>民行二处</option>
                                        <option value="检委会" <c:if test="${dcdb.blbm == '检委会'}">selected</c:if>>检委会</option>
                                        <option value="案件管理办公室" <c:if test="${dcdb.blbm == '案件管理办公室'}">selected</c:if>>案件管理办公室</option>
                                        <option value="反贪污贿赂局" <c:if test="${dcdb.blbm == '反贪污贿赂局'}">selected</c:if>>反贪污贿赂局</option>
                                        <option value="信息中心" <c:if test="${dcdb.blbm == '信息中心'}">selected</c:if>>信息中心</option>
                                        <option value="检察技术处" <c:if test="${dcdb.blbm == '检察技术处'}">selected</c:if>>检察技术处</option>
                                        <option value="控告申诉检察处" <c:if test="${dcdb.blbm == '控告申诉检察处'}">selected</c:if>>控告申诉检察处</option>
                                        <option value="职务犯罪预防处" <c:if test="${dcdb.blbm == '职务犯罪预防处'}">selected</c:if>>职务犯罪预防处</option>
                                    </select>
                                </div>
                            </div>--%>
                            <div class="layui-form-item layui-form-text">
                                <lable class="layui-form-label"><span style="color:red;"></span>登记人：</lable>
                                <div class="layui-input-block">
                                    <input type="text" name="djr" id="djr" class="layui-input" value="${dcdb.djr}"/>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <lable class="layui-form-label">附件上传：</lable>
                                <div class="layui-input-block attachment-wrapper">
                                    <div class="attachment-box">
                                        <div class="attachment-lists" id="fileList"></div>
                                        <div class="footer">
                                            <div class="layui-col-xs6 selectAll">
                                                <input type="checkbox" name="" title="全选" lay-skin="primary" id="selectAll" lay-filter="selectAll">
                                            </div>
                                            <div class="layui-col-xs6 btn-wrapper" >
                                                <div class="layui-btn layui-btn-sm layui-btn-primary" id="fileUpload">上传</div>
                                                <div class="layui-btn layui-btn-sm layui-btn-normal download-files">下载</div>
                                                <div class="layui-btn layui-btn-sm layui-btn-primary delete-files" id="deleteFiles">删除</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item" id = "xjspr">
                                <div id="spbm" style="display: none">
                                    <label class="layui-form-label" > 审批部门：</label>
                                    <div class="layui-input-inline" style="width:260px" >
                                        <select id="xgpg_nextbm" name="xgpg_nextbm"  lay-search=""/>
                                        <option value="0">请选择</option>
                                        <option value="办公室">办公室</option>
                                        <option value="督办室">督办室</option>
                                        <option value="公诉一处" selected>公诉一处</option>
                                        <option value="公诉二处">公诉二处</option>
                                        <option value="公诉三处">公诉三处</option>
                                        <option value="侦监一处">侦监一处</option>
                                        <option value="侦监二处">侦监二处</option>
                                        <option value="民行一处">民行一处</option>
                                        <option value="民行二处">民行二处</option>
                                        <option value="检委会">检委会</option>
                                        <option value="案件管理办公室">案件管理办公室</option>
                                        <option value="反贪污贿赂局">反贪污贿赂局</option>
                                        <option value="信息中心">信息中心</option>
                                        <option value="检察技术处">检察技术处</option>
                                        <option value="控告申诉检察处">控告申诉检察处</option>
                                        <option value="职务犯罪预防处">职务犯罪预防处</option>
                                        </select>
                                    </div>
                                </div>
                                <%--<div class="layui-form-item layui-form-text">--%>
                                    <lable class="layui-form-label" id="banli"><span style="color:red;"></span>办理部门：</lable>
                                    <div class="layui-input-inline" id="banlidiv">
                                        <select name="blbm" id="blbm" lay-filter="blbm">
                                            <option value="">请选择办理部门</option>
                                            <%--<option value="公诉科" <c:if test="${dcdb.blbm == '公诉科'}">selected</c:if>>公诉科</option>
                                            <option value="侦查监督科" <c:if test="${dcdb.blbm == '侦查监督科'}">selected</c:if>>侦查监督科</option>
                                            <option value="民事行政科" <c:if test="${dcdb.blbm == '民事行政科'}">selected</c:if>>民事行政科</option>--%>
                                            <option value="办公室" <c:if test="${dcdb.blbm == '办公室'}">selected</c:if>>办公室</option>
                                            <option value="督办室" <c:if test="${dcdb.blbm == '督办室'}">selected</c:if>>督办室</option>
                                            <option value="公诉一处" <c:if test="${dcdb.blbm == '公诉一处'}">selected</c:if>>公诉一处</option>
                                            <option value="公诉二处" <c:if test="${dcdb.blbm == '公诉二处'}">selected</c:if>>公诉二处</option>
                                            <option value="公诉三处" <c:if test="${dcdb.blbm == '公诉三处'}">selected</c:if>>公诉三处</option>
                                            <option value="侦监一处" <c:if test="${dcdb.blbm == '侦监一处'}">selected</c:if>>侦监一处</option>
                                            <option value="侦监二处" <c:if test="${dcdb.blbm == '侦监二处'}">selected</c:if>>侦监二处</option>
                                            <option value="民行一处" <c:if test="${dcdb.blbm == '民行一处'}">selected</c:if>>民行一处</option>
                                            <option value="民行二处" <c:if test="${dcdb.blbm == '民行二处'}">selected</c:if>>民行二处</option>
                                            <option value="检委会" <c:if test="${dcdb.blbm == '检委会'}">selected</c:if>>检委会</option>
                                            <option value="案件管理办公室" <c:if test="${dcdb.blbm == '案件管理办公室'}">selected</c:if>>案件管理办公室</option>
                                            <option value="反贪污贿赂局" <c:if test="${dcdb.blbm == '反贪污贿赂局'}">selected</c:if>>反贪污贿赂局</option>
                                            <option value="信息中心" <c:if test="${dcdb.blbm == '信息中心'}">selected</c:if>>信息中心</option>
                                            <option value="检察技术处" <c:if test="${dcdb.blbm == '检察技术处'}">selected</c:if>>检察技术处</option>
                                            <option value="控告申诉检察处" <c:if test="${dcdb.blbm == '控告申诉检察处'}">selected</c:if>>控告申诉检察处</option>
                                            <option value="职务犯罪预防处" <c:if test="${dcdb.blbm == '职务犯罪预防处'}">selected</c:if>>职务犯罪预防处</option>
                                        </select>
                                    </div>
                                <%--</div>--%>
                                <label class="layui-form-label" >办理人：</label>
                                <div class="layui-input-inline" style="width:260px">
                                    <select id="xgpg_nextperson" name="xgpg_nextperson"  lay-search="" />
                                    <option value="0">请选择</option>
                                    <option value="相阳阳">相阳阳</option>
                                    <option value="戴鹏翔">戴鹏翔</option>
                                    <option value="王洪涛">王洪涛</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <lable class="layui-form-label">文书列表：</lable>
                                <div class="layui-input-block admin-download-input" id="wslist">

                                </div>
                            </div>
                            <div class="layui-form-item" id="culeBtn">
                                <div class="layui-input-block admin-align-right" >
                                    <span class="admin-save-btn" onclick="saveInfo(1)">保存</span>
                                    <span class="admin-save-btn" onclick="saveInfo(2)">发送办理</span>
                                </div>
                            </div>
                            <div class="layui-form-item" id="neiqinjieshouBtn" hidden>
                                <div class="layui-input-block admin-align-right">
                                    <span class="admin-save-btn" onclick="nqJsInfo(3)">接收</span>
                                    <span class="admin-save-btn" onclick="nqJsInfo(4)">拒绝</span>
                                </div>
                            </div>
                            <div class="layui-form-item" id="neiqinfasongBtn" hidden>
                                <div class="layui-input-block admin-align-right">
                                    <span class="admin-save-btn" onclick="nqJsInfo(3)">发送</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="selectType" id="selectType" style="display: none;text-align: center;">
    <div class="layui-btn layui-btn-primary laybtm" style="margin-top: 12px; margin-left: 9px;width: 262px;">党组会、检察长办公会决策事项督办</div>
    <div class="layui-btn layui-btn-primary laybtm" style="width: 262px;">领导同志批示件督办</div>
    <div class="layui-btn layui-btn-primary laybtm" style="width: 262px;">代表委员转交件督办</div>
    <div class="layui-btn layui-btn-primary laybtm" style="width: 262px;">上级单位交办事项督办</div>
    <div class="layui-btn layui-btn-primary laybtm" style="width: 262px;">重大会议、重要文件督办</div>
</div>
<input type="hidden" id="readOnly" value="${readOnly}">
<input type="hidden" id="nodeId" value="${nodeId}">
<input type="hidden" id="ajzt" value="${ajzt}">
<input type="hidden" id="first" value="0">
<script src="${ctx}/static/plugin/jquery-plugins/jquery.nicescroll.js"></script>
<script src="${ctx}/common/js/dcdb/common.js?v=2"></script>
<script src="${ctx}/common/js/dcdb/dcdbView.js?v=2"></script>
<script>
    var addLayer = null;
    $(function () {
        var first = $('#first').val();
        if(first == 1){
            var layer = layui.layer;
            addLayer = layer.open({
                type: 1,
                title: '请选择督查督办类型',
                content: $('#selectType'),
                area: ['300px', '300px'],
                scrollbar: false,
                cancel: function (index, layero) {
                    layer.close(index);
                    $('#selectType').hide();
                }
            })
            $('.laybtm').on('click',function(){
                layer.closeAll();
                $('#selectType').hide();
            })
        }

        var readOnly = $('#readOnly').val();
        var nodeId = $('#nodeId').val();
        var ajzt = $('#ajzt').val();
        if(readOnly == 1){
            $('input').attr('readonly','readonly');
            $('textarea').attr('readonly','readonly');
            $('select').attr('disabled','disabled');
            $('input').addClass('layui-bg-gray');
            $('textarea').addClass('layui-bg-gray')
            $('#fileUpload').hide();
            $('#culeBtn').hide();
            $('#deleteFiles').hide();
            if(nodeId == '2' && ajzt =='1'){
                $('#neiqinjieshouBtn').show();
                $("#xjspr").hide();
            }else if(nodeId == '2' && ajzt =='2'){
                $("#spbm").show();
                $('#neiqinfasongBtn').show()
                $("#xgpg_nextperson").removeAttr("disabled");
                $("#banli").hide();
                $("#banlidiv").hide();
            }else {
                $("#xjspr").hide();
            }
            $('#hyjytext').hide();
            var callback = {
                fileList :function(data){
                    var code = data.code;
                    if(code == 1){
                        $('#filehyjy').html('')
                        var fileList = data.fileList;
                        var fileHtml = "";
                        for(var i = 0;i<fileList.length;i++){
                            if(fileList[i] != null){
                                fileHtml = '<a style="cursor:pointer;" onclick="downloadfile(\''+fileList[i].FILENAME+'\')">'+fileList[i].TARGETFILENAME+'</a>'
                            }
                        };
                        $('#filehyjy').html(fileHtml);
                    }
                }
            }
            ajaxQryJY(callback)
        }
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            if(readOnly == 0){
                laydate.render({
                    elem: '#hysj',
                    format: 'yyyy年MM月dd日',
                    value: $("#hysj").val() == "" ? new Date() : $("#hysj").val(),
                    //theme: 'grid'
                });
                laydate.render({
                    elem: '#blqx',
                    format: 'yyyy年MM月dd日',
                    value: $("#blqx").val() == "" ? new Date() : $("#blqx").val(),
                    min: (function(){
                       return  $("#hysj").val();
                    })()
                    //theme: 'grid'
                });
            }
        });
        var element = layui.element;
        element.init();
        var liuchengLi = $('.nav-liucheng li')
        liuchengLi.on('click', function () {
            var _self = $(this);
            var curIndex = _self.index();
            console.log(curIndex)
            _self.addClass('active').siblings().removeClass('active')
            forms.eq(curIndex).addClass('on').siblings().removeClass('on')
        });

        $('.form-wrapper').niceScroll({
            cursorcolor: '#d8d8d8'
        });
    });

    function saveInfo(type){
        var operate = null;
        if(type==2){
            operate = "submit"
        }else{
            operate = "save"
        }
        var username='';
        var userid='';
        if(nodeId == '2' && ajzt =='1'){
            var username='相阳阳';
            var userid='相阳阳';
        }else{
            var username=$("#xgpg_nextperson option:selected").text();
            var userid= $("#xgpg_nextperson option:selected").val();
        }
        var param = {
            "hymc":$('#hymc').val(),
            'hysj':$('#hysj').val(),
            'yjyt':$('#yjyt').val(),
            'bh':$('#id').val(),
            'hyjdsx':$('#hyjdsx').val(),
            'dbsx':$('#dbsx').val(),
            'rwyq':$('#rwyq').val(),
            'blqx':$('#blqx').val(),
            'blbm':$('#blbm').val(),
            'djr':$('#djr').val(),
            'username':username,
            'userid':userid,
           // 'bmbm':$("#xgpg_nextperson option:selected").val(),
           // 'bmmc':$("#xgpg_nextperson option:selected").text(),
            'operate':operate
        }
        var url = '${ctx}/new/insertAkx.do';
        var success = function(){
            layer.msg("操作成功",{icon:1},function(){
                if(type==1){
                    window.location.href ='${ctx}/dcdb/index.do?id='+$('#id').val()
                }else {
                    window.location.href ='${ctx}/todo.do?ajzt=1'
                }

            })
        }
        $.commonAjax(url,param,success);
    }
    //内勤第一步   只需要更新状态
    function nqJsInfo(type){
        var nodeId = $('#nodeId').val();
        var ajzt = $('#ajzt').val();
        var username='';
        var userid='';
        if(nodeId == '2' && ajzt =='1'){
             username='相阳阳';
             userid='相阳阳';
        }else{
             username=$("#xgpg_nextperson option:selected").text();
             userid= $("#xgpg_nextperson option:selected").val();
        }
       if(type==3){
           var url="${ctx}/index/updateDb.do";
           var param={
               "ajzt":$("#ajzt").val(),
               "nodeid":$("#nodeId").val(),
               "id":$("#id").val(),
               'username':username,
               'userid':userid,

           }
           var success = function(){
               layer.msg("操作成功",{icon:1},function(){
                   window.location.href ='${ctx}/todo.do?ajzt=2'
               })
           }
           $.commonAjax(url,param,success);
       }else{
           layer.msg("操作成功",{icon:1},function(){
               window.location.href ='${ctx}/todo.do?ajzt=4'
           });
       }
    }
    //内勤在办 发送承办人
   /* fucntion nqFsInfo(type){

    }*/

</script>
</body>
</html>