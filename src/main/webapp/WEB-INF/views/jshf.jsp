<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE" />
    <title>接收回复</title>--%>
    <link rel="stylesheet" href="${ctx}/static/plugin/layui-v2.4.3/layui/css/layui.css">
    <script src="${ctx}/static/plugin/layui-v2.4.3/layui/layui.all.js"></script>
    <link rel="stylesheet" href="${ctx}/common/js/util/admin-jcjy.css">
    <script type="text/javascript" src="${ctx}/common/js/util/jquery.js"></script>
   <script src="${ctx}/common/js/dcdb/common.js?v=2"></script>
   <script src="${ctx}/common/js/dcdb/dcdbjshf.js?v=2"></script>
<style>
    html,body{
        width: 100%;
        height: 100%;
        background-color:white;
    }
</style>
<%--</head>
<body>--%>
    <div class="layui-tab">
    <ul class="layui-tab-title">
        <li class="layui-this">回复项</li>
        <li>党组会决定事项通知单/检察长办公会决定事项通知单</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <div class="layui-form">
                <input type="hidden" name="ajid"  id = "ajid" class="layui-input" value="${dcdb.id}"/>
                <input type="hidden" name="nodeid"  id = "nodeid" class="layui-input" value="${dcdb.nodeid}"/>
                <input type="hidden" name="nodename" id = "nodename" class="layui-input" value="${dcdb.nodename}"/>
                <input type="hidden" name="jdjmc" id = "jdjmc" class="layui-input" value="${dcdb.jdjmc}"/>
                <input type="hidden" name="isReback" id = "isReback" class="layui-input" value="${dcdb.isReback}"/>
                <input type="hidden" name="username" id = "username" class="layui-input" value=""/>
                <div class="layui-form-item layui-form-text">
                    <div class="layui-col-md4 layui-col-xs4">
                        <label class="layui-form-label"><span style="color:red;">*</span>督办单编号：</label>
                        <div class="layui-input-block">
                            <input type="text" name="dbdbh" id ="dbdbh" class="layui-input" value="${dcdb.dbdbh}"/>
                        </div>
                    </div>
                    <div class="layui-col-md4 layui-col-xs4">
                        <label class="layui-form-label"><span style="color:red;">*</span>督办事项：</label>
                        <div class="layui-input-block">
                            <input type="text" name="dbsx" id = "dbsx" class="layui-input" value="${dcdb.dbsx}"/>
                        </div>
                    </div>
                    <div class="layui-col-md4 layui-col-xs4">
                        <label class="layui-form-label">承办人:</label>
                        <div class="layui-input-block">
                            <input type="text" id="hfr" name="hfr" class="layui-input jshfinput" value="戴鹏翔">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <lable class="layui-form-label"><span style="color:red;">*</span>承办人意见(回复内容)：</lable>
                    <div class="layui-input-block">
                        <textarea name="hfnr" id = "hfnr" class="layui-textarea">${dcdb.hfnr}</textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text" id="bmfzrDiv">
                    <lable class="layui-form-label"><span style="color:red;">*</span>部门负责人意见：</lable>
                    <div class="layui-input-block">
                        <textarea name="bmfzryj" id ="bmfzryj" class="layui-textarea">${dcdb.bmfzryj}</textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text" id="dbsDiv">
                    <lable class="layui-form-label"><span style="color:red;">*</span>督办室意见：</lable>
                    <div class="layui-input-block">
                        <textarea name="dbsyj" id ="dbsyj" class="layui-textarea">${dcdb.dbsyj}</textarea>
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
                                    <div class="layui-btn layui-btn-sm layui-btn-primary delete-files"id="fileDelete">删除</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
              <%--  <div class="layui-form-item" id = "jshfxjspr">
                    <label class="layui-form-label" >下一节点审批人：</label>
                    <div class="layui-input-inline" style="width:260px">
                        <select id="xgpg_nextperson" name="xgpg_nextperson"  lay-search="" /><
                        <option value="0">请选择</option>
                        <optgroup label="公诉科" id="0010">
                            <option value="110">张三</option>
                        </optgroup>
                        <optgroup label="侦查监督科" id="0011">
                            <option value="110">张三</option>
                        </optgroup>
                        <optgroup label="民事行政科" id="0012">
                            <option value="110">张三</option>
                        </optgroup>
                        </select>
                    </div>
                </div>--%>

                <div class="layui-form-item" id = "jshfxjspr">
                    <label class="layui-form-label" >审批部门：</label>
                    <div class="layui-input-inline" style="width:260px">
                        <select id="xgpg_nextpersonbm" name="xgpg_nextpersonbm"  lay-search="" />
                        <option value="">请选择</option>
                        <option value="办公室">办公室</option>
                        <option value="督办室">督办室</option>
                        <option value="公诉一处">公诉一处</option>
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

                    <label class="layui-form-label" >办理人：</label>
                    <div class="layui-input-inline" style="width:260px">
                        <select id="xgpg_nextperson" name="xgpg_nextperson"  lay-filter='user' lay-search="" />
                        <option value="">请选择</option>

                        <c:if test="${dcdb.nodeid !='5'}">
                        <option value="相阳阳">相阳阳</option>
                        <option value="戴鹏翔">戴鹏翔</option>
                        <option value="王洪涛">王洪涛</option>
                        </c:if>
                        <c:if test="${dcdb.nodeid =='5'}">
                            <option value="闫世程">闫世程</option>
                            <option value="赵启林">赵启林</option>
                            <option value="杨洋">杨洋</option>
                        </c:if>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <lable class="layui-form-label">文书列表：</lable>
                    <div class="layui-input-block admin-download-input" id="wslist">
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-tab-item">
            <iframe id="agx" src="${ctx}/dcdb/index.do?id=${dcdb.id}&readOnly=1&nodeid=${dcdb.nodeid}" style="width: 100%;height: 800px;border: 0px;"></iframe>
        </div>
        <div class="layui-form-item" id="culeBtn">
            <div class="layui-input-block admin-align-right">
            <c:if test="${dcdb.isReback=='1'}">
                <!--承办人办理节点-->
                <c:if test="${dcdb.nodeid=='2'}">
                    <span class="admin-save-btn"   onclick="save(3)">办理回复</span>
                </c:if>
                <!--督查室办理节点-->
                <c:if test="${dcdb.nodeid=='3'}">
                    <span class="admin-save-btn"   onclick="save(4)">办结</span>
                    <span class="admin-save-btn"   onclick="save(5)">退回</span>
                </c:if>
            </c:if>

            <c:if test="${dcdb.isReback=='0'}">
                <!--承办人办理节点-->
                <c:if test="${dcdb.nodeid=='3'}">
                    <span class="admin-save-btn" onclick="save(1)">生成办理情况报告</span>
                    <span class="admin-save-btn"  onclick="save(2)">发送部门负责人</span>
                </c:if>
                <!--部门负责人办理节点-->
                <c:if test="${dcdb.nodeid=='4'}">
                    <span class="admin-save-btn"  onclick="save(2)">审核</span>
                </c:if>
                <!--承办人办理节点-->
                <c:if test="${dcdb.nodeid=='5'}">
                    <span class="admin-save-btn"   onclick="save(3)">办理回复</span>
                </c:if>
                <!--督查室办理节点-->
                <c:if test="${dcdb.nodeid=='6'}">
                    <span class="admin-save-btn"   onclick="save(4)">办结</span>
                    <span class="admin-save-btn"   onclick="save(5)">退回</span>
                </c:if>
            </c:if>


            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var nodeId = $("#nodeid").val();
        if(nodeId == '7'){
            $("#jshfxjspr").hide();
            $('input').attr('readonly','readonly');
            $('textarea').attr('readonly','readonly');
            $('select').attr('disabled','disabled');

            $('input').addClass('layui-bg-gray');
            $('textarea').addClass('layui-bg-gray');
            $('select').addClass('layui-bg-gray');
            $('#fileUpload').hide();
            $('#fileDelete').hide();
        }else{
            var back= $("#isReback").val();
            if(back =='0'){
                if (nodeId == '3') {
                    /*$('#bmfzryj').attr('readonly', 'readonly');
                    $('#dbsyj').attr('readonly', 'readonly');*/
                    $('#bmfzrDiv').hide();
                    $('#dbsDiv').hide();
                    $("#xgpg_nextpersonbm option[value='公诉一处']").attr("selected","selected");
                    $("#xgpg_nextpersonbm").attr("disabled","disabled");
                } else if (nodeId == '4') {

                    $('#dbdbh').attr('readonly', 'readonly');
                    $('#dbsx').attr('readonly', 'readonly');
                    $('#hfr').attr('readonly', 'readonly');
                    $('#hfnr').attr('readonly', 'readonly');

                    $('#dbdbh').addClass('layui-bg-gray');
                    $('#dbsx').addClass('layui-bg-gray');
                    $('#hfr').addClass('layui-bg-gray');
                    $('#hfnr').addClass('layui-bg-gray');
                   /* $('#dbsyj').attr('readonly', 'readonly');*/
                    $('#dbsDiv').hide();
                    $('#jshfxjspr').hide();
                    $('#username').val('戴鹏翔');
                } else if (nodeId == '5') {
                    $('#bmfzryj').attr('readonly', 'readonly');
                    $('#bmfzryj').addClass('layui-bg-gray');;
                    $('#dbsDiv').hide();
                    $("#xgpg_nextpersonbm option[value='督办室']").attr("selected","selected");
                    $("#xgpg_nextpersonbm").attr("disabled","disabled");
                } else if (nodeId == '6') {
                    $('#dbdbh').attr('readonly', 'readonly');
                    $('#dbsx').attr('readonly', 'readonly');
                    $('#hfr').attr('readonly', 'readonly');
                    $('#hfnr').attr('readonly', 'readonly');
                    $('#bmfzryj').attr('readonly', 'readonly');

                    $('#dbdbh').addClass('layui-bg-gray');
                    $('#dbsx').addClass('layui-bg-gray');
                    $('#hfr').addClass('layui-bg-gray');
                    $('#hfnr').addClass('layui-bg-gray');
                    $('#bmfzryj').addClass('layui-bg-gray');
                    $('#jshfxjspr').hide();
                    $('#username').val('闫世程');
                }
            }else{
                if (nodeId == '2') {
                    $('#bmfzryj').attr('readonly', 'readonly');
                    $('#dbsyj').attr('readonly', 'readonly');

                    $('#bmfzryj').addClass('layui-bg-gray');
                    $('#dbsyj').addClass('layui-bg-gray');
                }else if (nodeId == '6') {
                    $('#dbdbh').attr('readonly', 'readonly');
                    $('#dbsx').attr('readonly', 'readonly');
                    $('#hfr').attr('readonly', 'readonly');
                    $('#hfnr').attr('readonly', 'readonly');
                    $('#bmfzryj').attr('readonly', 'readonly');

                    $('#dbdbh').addClass('layui-bg-gray');
                    $('#dbsx').addClass('layui-bg-gray');
                    $('#hfr').addClass('layui-bg-gray');
                    $('#hfnr').addClass('layui-bg-gray');
                    $('#bmfzryj').addClass('layui-bg-gray');
                }
            }

        }


        var form = layui.form;
        form.on('select(user)', function (data) {
            $('#username').val(data.value)
        });
    })
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;

        //…
    });
    //保存
    function save(re) {
        if(!($("#nodeid").val() =='4' || $("#nodeid").val() == '6')){
            var nextuserId = $("#xgpg_nextperson").val();
            if(nextuserId  == "" || nextuserId  == null){
                layer.msg("请选择办理人",{icon:2});
                return;
            }
        }
        var id = $("#ajid").val();
        var dbdbh = $("#dbdbh").val();//督办单编号
        var dbsx = $("#dbsx").val();//督办事项
        var hfr = $("#hfr").val();//回复人
        var hfnr = $("#hfnr").val();//回复内容
        var bmfzryj = $("#bmfzryj").val();//部门负责人意见
        var nodeid = $("#nodeid").val();//流程id
        var nodename = $("#nodename").val();//流程名称
        var jdjmc = $("#jdjmc").val();//督办案件名称
        var param = {};
        param.id = id;
        param.dbdbh = dbdbh;
        param.dbsx = dbsx;
        param.hfr = hfr;
        param.hfnr = hfnr;
        param.bmfzryj = bmfzryj;
        param.nodeid = nodeid;
        param.nodename = nodename;
        param.jdjmc = jdjmc;
        param.flag = re;
        param.isReback = $("#isReback").val();
        param.dbsyj = $("#dbsyj").val();
        param.username = $('#username').val();
        $.ajax({
            type: "post",
            url: "/jshf/insertHfx.do",//注意路径  
            data: param,
            dataType: "json",
            success: function (data) {
                if (data.status == 'success') {
                    layer.msg("操作成功", {icon: 1}, function () {
                        if(re == '1'){

                            window.location.href="${ctx}/jshf.do?id="+id+"&readOnly=0&isReback="+$("#isReback").val()+"&nodeid="+nodeid+"&nodename="+nodename+"&ajzt=2";
                        }else{
                            //跳转首页
                            window.location.href="${ctx}/todo.do?ajzt=2";
                        }

                    });
                }
            },
            error: function (result) {
                layer.msg("操作失败"+result.msg, {icon: 2});
            }
        })
    }


        /*if(re == 2 ){
            if (nodeId == '0701') {
                if (jcgyj == "" || jcgyj == null) {
                    layer.msg("请填写承办人意见",{icon:2});
                    return;
                }
                if(level ==  '03' || level ==  '07'){

                }else{
                    var nextuserId = $("#xgpg_nextperson").val();
                    if(nextuserId  == "" || nextuserId  == null){
                        layer.msg("请选择下级审批人",{icon:2});
                        return;
                    }

                }

            }
            if (nodeId == '0702') {
                if (bmyj == "" || bmyj == null) {
                    layer.msg("请填写部门负责人意见",{icon:2});
                    return;
                }
                var nextuserId = $("#xgpg_nextperson").val();
                if(nextuserId  == "" || nextuserId  == null){
                    layer.msg("请选择下级审批人",{icon:2});
                    return;
                }

            }
            if (nodeId == '0703') {
                if (jczyj == "" || jczyj == null) {
                    layer.msg("请填写检察长意见",{icon:2});
                    return;
                }

            }
            // }

        }else if(re == 4){
            if(nodeId == '0701'){
                if (jcgyj == "" || jcgyj == null) {
                    layer.msg("请填写承办人意见",{icon:2});
                    return;
                }
                var nextuserId = $("#xgpg_nextperson").val();
                if(nextuserId  == "" || nextuserId  == null){
                    layer.msg("请选择下级审批人",{icon:2});
                    return;
                }
            }else{
                if (jczyj == "" || jczyj == null) {
                    layer.msg("请填写检察长意见",{icon:2});
                    return;
                }
                var nextuserId = $("#xgpg_nextperson").val();
                if(nextuserId  == "" || nextuserId  == null){
                    layer.msg("请选择下级审批人",{icon:2});
                    return;
                }
            }

        }

        var bz = $("#xgpg_bz").val();//备注
        var bjydwmc = $("#xgpg_bjydwmc").val();//被建议单位名称
        var dwzz = $("#xgpg_dwzz").val();//单位住址
        var fddbr = $("#xgpg_fddbranddh").val();//法定代表人及电话
        var ajly = $("#xgpg_ajly").val();//案件来源
        var sarq = $("#xgpg_slrq").val();//受案日期
        var larq = $("#xgpg_larq").val(); //立案日期
        var hfrq = $("#xgpg_hfrq").val();//回复日期
        var jyaq = $("#xgpg_jyaq").val();//简要案情
        var cjry = $("#xgpg_cjry").val()//参与人员

*/
       /* var param = {};

        param.procinstId = procinstId;
        param.ajbh = ajbh;
        param.xsbh = xsbh;
        param.ajmc = ajmc;
        param.nodeId = nodeId;
        param.xgname = xgname;

        param.gksdrq = gksdrq;
        param.fccbrq = fccbrq;
        param.hfyjjzgqk = hfyjjzgqk;

        param.jcgyj = jcgyj;
        param.jcgqm = jcgqm;
        param.jcgspsj = jcgspsj;

        param.bmyj = bmyj;
        param.bmqm = bmqm;
        param.bmspsj = bmspsj;

        param.jczyj = jczyj;
        param.jczqm = jczqm;
        param.jczspsj = jczspsj;

        param.zjczyj = zjczyj;
        param.zjczqm = zjczqm;
        param.zjczspsj = zjczspsj;

        param.bz = bz;
        param.bjydwmc = bjydwmc;
        param.dwzz = dwzz;
        param.fddbr = fddbr;
        param.ajly = ajly;
        param.sarq = sarq;
        param.larq = larq;
        param.hfrq = hfrq;
        param.jyaq = jyaq;
        param.cjry = cjry;
*/
       /* $.ajax({
            type: "post",
            url: "/jcjypg/insert.do",//注意路径  
            data: param,
            dataType: "json",
            success: function (s) {
                if (re == 1) {
                    if (s == 1) {
                        layer.msg("保存成功",{icon:1});

                    } else {
                        layer.msg("保存失败",{icon:2});
                    }
                }
                if (re == 2 || re == 4) {
                    if (s == 1) {
                        afterTijiao(re);
                    } else {
                        layer.msg("保存失败",{icon:2});
                    }
                }
                if(re == 3){
                    if(s == 1){
                        $.flowBack(param.xsbh, param.nodeId, function (data) {
                            layer.msg(data.msg, {icon: data.code,time: 1000}, function () {
                                window.location.href = "${ctx}/jcjy/index_db.do";
                            });
                        });
                    }
                }
            },
            error: function (data) {
                layer.msg("保存失败",{icon:2});
                ra = "1";
            }
        });
*/
    //}
</script>
<%--</body>
</html>--%>
