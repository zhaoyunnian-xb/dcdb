<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
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
                    工作任务部门目标已办
                </div>
                <span class="layui-btn layui-btn-normal" onclick="exportExcel()">导出excel</span>
                <div class="layui-card-body">
                    <div class="table-wrapper">
                        <table id="tabelOne" lay-filter="table_One" class="layui-table"></table>
                        </table>
                    </div>

                    <div id="ydnr" style="display: none; margin:10px">
                        <input type="hidden" id="rwmbId"/>
                        <div style="color: red;margin-bottom: 15px">注意：每个季度的目标都需要填写，然后一并提交审批！</div>
                      <%--  <div class="layui-form-item">
                            <label class="layui-form-label">第一季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="yjdmb" class="layui-textarea jdmb"   placeholder="最多输入200个字"  onblur="check(this.value)"></textarea>
                            </div>
                        </div>--%>
                        <div class="layui-form-item">
                            <label class="layui-form-label">第二季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="bnmb" class="layui-textarea jdmb"  placeholder="最多输入200个字"  onblur="check(this.value)"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">第三季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="sjdmb" class="layui-textarea jdmb"  placeholder="最多输入200个字" onblur= "check(this.value)"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">第四季度目标：</label>
                            <div class="layui-input-block">
                                <textarea name="ydnrtext" id="qnmb" class="layui-textarea jdmb"  placeholder="最多输入200个字" onblur="check(this.value)"></textarea>
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
                            <label class="layui-form-label">部门负责人审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_2" placeholder="" class="layui-textarea" id="bmspyj"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper" >
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
                            <label class="layui-form-label">办公室复核备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_3" placeholder="" class="layui-textarea" id="bgsspyj" ></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper" >
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
                            <label class="layui-form-label">办公室负责人审批备注:
                            </label>
                            <div class="layui-input-block">
                                <textarea name="desc_4" placeholder="" class="layui-textarea" id="bgsldpyj" ></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text qm-wrapper" >
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
<script>
    var index2 = "";
    var table = layui.table;
    var ndrwId = $("#ndrwId").val();
    var cbbmId = $("#cbbmId").val();
    var status = $("#status").val();

    $(function() {
        selectSpyj();
        var e = layer.load();
        table.render({
            id: 'testReload',
            elem: '#tabelOne',
            height: 500,
            url: '/jdtb/queryZyrwListYb.do' //数据接口
           , where: {'ndid': ndrwId, 'status': status, 'cbbmid': cbbmId},
            loading:true,
            page: true, //开启分页
            done:function() {
                layer.close(e);
            },
            cols: [[ //表头
               /* {field: 'ID', title: '序号', width:'8%', align:'center'},*/
                {field: 'zizeng', width: '10%', title: '序号', templet: '#zizeng'},
                {field: 'ydnrmc', title: '要点内容', width:'15%'},
                {field: 'zrld', title: '责任领导', width:'15%'},
                {field: 'zyrwmc', title: '主要任务', width:'15%'},
               {field: 'yjdmb', title: '一季度目标', width:'15%'},
                {field: 'bnmb', title: '半年目标', width:'15%'},
                {field: 'sjdmb', title: '三季度目标', width:'15%'},
                {field: 'qnmb', title: '全年目标', width:'15%'},
                {field: 'ssbm', title: '牵头部门', width:'15%'},
                {field: 'phbm', title: '配合部门', width:'15%'}

            ]]
        });

      $("textarea[class='layui-textarea']").attr('disabled','true');
    })

    function  selectSpyj(){
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
                if(result.dcdbkhpz  != null){
                    $("#bmspyj").val(result.dcdbkhpz.bmmbfzryj)
                    if(lcjd == '2'){
                        if(result.dcdbkhpz.bmfzrqm == "" || result.dcdbkhpz.bmfzrqm  == null){
                            $("#bmspqm").val(currentUserName);
                        }else{
                            $("#bmspqm").val(result.dcdbkhpz.bmfzrqm);
                        }
                        if(result.dcdbkhpz.bmfzrrq == "" || result.dcdbkhpz.bmfzrrq == null ){
                            $("#bmsprq").val(currentdate);
                        }else{
                            $("#bmsprq").val(result.dcdbkhpz.bmfzrrq);
                        }

                    }
                }else{
                    if(lcjd == '2'){
                        $("#bmspqm").val(currentUserName);
                        $("#bmsprq").val(currentdate);
                    }
                    if(lcjd == '3'){
                        $("#bgsspqm").val(currentUserName);
                        $("#bgssprq").val(currentdate);
                    }
                    if(lcjd == '4'){
                        $("#bgsldpqm").val(currentUserName);
                        $("#bgsldprq").val(currentdate);
                    }
                }
            }
        })
    }

    function exportExcel() {
      window.location.href="${ctx}/jdtb/exportExcel.do?ndrwId="+ndrwId+"&status="+status+"&cbbmId="+cbbmId+"&flag=jdmb"
    }
</script>




