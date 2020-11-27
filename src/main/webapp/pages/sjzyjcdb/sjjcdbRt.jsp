<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/4/18
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp"></jsp:include>
<input  type="hidden" id="cbbmid" value="${cbbmid}"/>
<div class="layui-fluid" >
    <div class="layui-card">
        <div class="layui-card-header">
            重要决策台账汇总
        </div>
        <div class="layui-card-body layui-form">
            <div class="layui-row layui-col-space10" >
                <div class="layui-col-md6 layui-col-sm6">
                    <div class="layui-form">
                        上级决策事项：
                        <div class="layui-input-inline">
                            <select name="interest" lay-filter="sjjcsx" id="sjjcsx">

                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-col-md6 layui-col-sm6">
                    <span class="layui-btn layui-btn-primary" id="selecttTj">查询</span>
                    <span class="layui-btn layui-btn-normal">导出</span>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-md12" style="overflow:auto;height: 550px;">
                    <table class="layui-table" id="jdTjTable">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script language="JavaScript" type="text/javascript">
$(function () {
  var table = layui.table;
  var form = layui.form;
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

    var param={
      "url":"${ctx}/Tjtable/selectMenu.do",
      "data":"",
      "success" :function(d){
        if(d.length>0){
          var html="";
          for(var i=0;i<d.length;i++){
            html+="<option value='"+d[i].DJID+"'>"+d[i].LXMC+"</option>";
          }
          $("#sjjcsx").html(html);
        }else{
          layer.msg("您还没有上级决策事项",{icon:2});
        }
      }
    };
    //查询督查事项下拉列表
   ajax(param);
  form.render();

    table.render({
       id: 'testReload',
        elem: '#jdTjTable'
        ,limit:10
        ,page: true //开启分页
        ,url: '${ctx}/Tjtable/sjjcsxRt.do' //数据接口
        ,where:{"djid":$('#sjjcsx option:selected').val()}
        ,cols: [[ //表头
            {field: 'id', title: 'ID',  hide:true,sort: true}
            ,{field: 'dcsxmc', title: '督查事项名称',  }
            ,{field: 'dczq', title: '督查周期',  sort: true, }
            ,{field: 'qtbmid', title: '牵头部门名称',}
            ,{field: 'zzbmid', title: '主责部门名称',}
            ,{field: 'cbbmmc', title: '承办部门名称',sort: true, }
            ,{field: 'cbrq', title: '承办日期',sort: true, }
            ,{field: 'cbms', title: '承办描述',}
        ]]
    });

    $("#selecttTj").click(function () {
      var djid= $('#sjjcsx option:selected').val();
      table.reload('testReload', {
        page: {
          curr: 1 //重新从第 1 页开始
        },
        where:{
          "djid":djid
        }

      });
    })
})
</script>
<jsp:include page="../footer.jsp"></jsp:include>