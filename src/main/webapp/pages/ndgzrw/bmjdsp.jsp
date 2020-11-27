<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp"></jsp:include>
<%--完成情况填报页面（填报部门使用）--%>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <input type="hidden" id="ndid" value="${ndid}"/>
        <input type="hidden" id="status" value="${status}"/>
        <input type="hidden" id="cbbmid" value="${cbbmid}"/>
        <input type="hidden" id="name" value="${name}"/>
        <input type="hidden" id="ssbm" value="${ssbm}"/>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header" id="bt_id"></div>
                <div class="layui-card-body">
                    <div style="text-align: right;">
                        <span class="layui-btn layui-btn-normal" onclick="exportExcel()">导出excel</span>
                    </div>
                    <div class="table-wrapper">
                        <table id="demo" lay-filter="demoEvent"></table>
                    </div>
                    <div class="layui-form" id="yj_div">
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">部门负责人<br>审批备注:
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
                            <label class="layui-form-label">办公室</br>审批备注:
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
                            <label class="layui-form-label">办公室负责人</br>审批备注:
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
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-normal" onclick="send('save')">保存
                                </button>
                                <%--<button type="reset" class="layui-btn layui-btn-primary" onclick="send('rebak')">退回
                                </button>--%>
                                <c:if test="${status.substring(2,3)=='5'}">
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="volidateJdtb('submit')">提交</button>
                                </c:if>
                                <c:if test="${status.substring(2,3)=='6'}">
                                    <button type="reset" class="layui-btn layui-btn-primary" onclick="send('submit')">审核通过</button>
                                </c:if>
                            </div>
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
        if(status.substring(2,3) == '9'){
            loadTableList(ndid,status,cbbmid,true,false,true);
        }else{
            loadTableList(ndid,status,cbbmid,false,false,true);
        }

        //查询意见
        queryPsyj(ndid,status,cbbmid);
        $("#bt_id").html("工作任务部门完成情况填报("+To_SimplifiedChinese(status.substring(0,1))+"季度)");
        //初始化签名和日期
        initQmSj();
        //判断意见框是否可编辑
        $("#yj_div textarea[class='layui-textarea']").each(function(){
            var name = $(this).attr('name');
            if(name.split("_")[1] !=status.substring(2,3) ){
                $(this).attr('disabled','true');
            }
        })
        $("#yj_div input[class='layui-input']").each(function () {
            var name = $(this).attr('name');
            if (name.split("_")[1] != status.substring(2, 3)) {
                $(this).attr('disabled', 'true');
            }
        })
        disableKj(status);

    })

    function disableKj(status) {
        if(status == '119' || status=='219' || status=='319' || status=='419') {
            $("input[type='text']").attr('disabled', 'true');
            $("input[type='radio']").attr('disabled', 'true');
            $("input[type='file']").attr('disabled', 'true');
            $("textarea[class='layui-textarea']").attr('disabled','true');
            $("button").hide(); //保存、提交按钮隐藏
        }
    }

    function exportExcel() {
      var ndrwId=$("#ndid").val();
      var status=$("#status").val();
      var cbbmId=$("#cbbmid").val();
      window.location.href="${ctx}/jdtb/exportExcel.do?ndrwId="+ndrwId+"&status="+status+"&cbbmId="+cbbmId+"&flag=wcqk"
    }


</script>

<jsp:include page="../footer.jsp"></jsp:include>