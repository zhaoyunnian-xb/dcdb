<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
<%--待办列表（办公室使用）--%>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">年度工作任务办公室审批</div>
        <div class="layui-card-body">
          <div class="layui-row layui-col-space10" >
                <div class="layui-col-sm3">
                    <div class="layui-form">
                        年度任务：
                        <div class="layui-input-inline" >
                            <select name="interest" lay-filter="aihao" id="tjNd" >

                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm6 layui-col-md2">
                         <span class="layui-btn layui-btn-primary" id="selecttTj" lay-submit lay-filter="form1">搜索</span>
                 </div>
            </div>
            <div class="table">
                <div class="">
                    <table id="tabelOne" lay-filter="table_One" class="layui-table"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<style type="text/css">
    a{
        color: dodgerblue;
    }
</style>
<script>
    layui.use('laydate', function () {
            var laydate = layui.laydate;
        laydate.render({
            elem: '#data',
            type: 'year'
        });
    })
    var layuiForm = layui.form;
    $(function(){
        if($("#name").val()!='罗艳'){
            $("#tzgs").hide();
        }
        selectRw();
        loadData();
       // $("#selecttTj").click();
    })

    //选择年度任务
    function selectRw(){
        $.ajax({
            url: "${ctx}/Tjtable/ndrw.do",
            type: "get",
            async: false,
            data: {"cbbmid":$("#cbbmid").val(),"tjzt":"0","name":$("#name").val()},
            dataType: "json",
            success: function (result) {
                var html="";
                if(result.length>0){
                    for(var i=0;i<result.length;i++){
                        html+="<option value='"+result[i].ID+"' data-tjzt='"+result[i].TJZT+"'>"+result[i].NDRWMC+"</option>";
                    }
                    $("#tjNd").html(html);
                }
                layuiForm.render();
            }
        });
    }
    //$("#selecttTj").click(function(){
    //	loadData();
    //})
    function loadData() {
        var table = layui.table;
        var e = layer.load();
        var ndid=$('#tjNd option:selected').val();
        //tabelOne
        table.render({
            elem: '#tabelOne',
            url: '${ctx}/jdtb/queryBmdbListBgs.do?ndid='+ndid, //数据接口
            loading:true,
            page: true, //开启分页
            limit: 10, //开启分页
            done:function() {
                layer.close(e);
            },
            cols: [[ //表头
                {field: 'id', title: 'ID',hide:true},
                {field: 'xh', title: '序号',type:'numbers', width: 60},
                {field: 'ndrwmc', title: '年度任务名称'},
                {field: 'ssbm', title: '牵头部门', width: 120, align:'center'},
                {field: 'cbr', title: '填报人',width: 90, align:'center'},
                {field: 'czzt',title: '操作',templet:function(d){
                        var status=d.czzt;
                        var jd = status.substring(0,1); //季度
                        var ywType = status.substring(1,2); //业务类型
                        var nodeid = status.substring(2,3); //节点ID
                        var wz="审批";

                        if(ywType != '0'){
                            //填写完成情况
                            if(nodeid != '9'){
                                wz=To_SimplifiedChinese(jd)+"季度完成情况审批";
                            }else{
                                wz=To_SimplifiedChinese(jd)+"季度完成情况填写已完成";
                            }
                        }else{
                            if(nodeid == '105' ){
                                wz="全年任务目标填写已完成";
                            }else{
                                wz="全年任务目标审批";
                            }
                        }

                        return "<a class='admin-btn-blue active' href='javascript:void(0)' onclick=\"toPage('"+d.id+"','"+d.czzt+"','"+d.cbbmid+"','"+d.ssbm+"')\">"+wz+"</a>";
                    }}
            ]]
        });

        // 监听表单查询
        layui.form.on('submit(form1)', function(data){
            //var data = $("#data").val();
           // var czr = $("#czr").val();
           // var wjmc = $("#wjmc").val();
            var ndid=$('#tjNd option:selected').val();
            table.reload('tabelOne', {
                //url: '${ctx}/jdtb/queryBmdbList.do?nd='+data+'&ndrwmc='+wjmc+'&czr='+czr,
                url: '${ctx}/jdtb/queryBmdbListBgs.do?ndid='+ndid,
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });


    }

    function toPage(ndid,status,cbbmid,ssbm) {
        window.location.href = '${ctx}/jdtb/toPage.do?ndid='+ndid+'&status='+status+'&cbbmid='+cbbmid+'&ssbm='+ssbm;
    }
    //阿拉伯数字转换为简写汉字
    function To_SimplifiedChinese(Num) {
        var tmpnewchar;
        switch (Num) {
            case "0":  tmpnewchar = "零" ;break;
            case "1": tmpnewchar = "一" ; break;
            case "2": tmpnewchar = "二" ; break;
            case "3": tmpnewchar = "三" ; break;
            case "4": tmpnewchar = "四" ; break;
            case "5": tmpnewchar = "五" ; break;
            case "6": tmpnewchar = "六" ; break;
            case "7": tmpnewchar = "七" ; break;
            case "8": tmpnewchar = "八" ; break;
            case "9": tmpnewchar = "九" ; break;
        }

        return tmpnewchar;
    }

</script>




