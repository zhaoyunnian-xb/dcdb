<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../header.jsp"></jsp:include>
<style>
    .layui-table-cell{
        height: 28px;
    }
    .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
        top: 50%;
        transform: translateY(-50%);
    }

</style>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
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
<input type="hidden" id="bmid" value="${bmid}">
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="layui-card">
                <div class="layui-card-header">
                    分案
                </div>
                <div class="layui-card-body">
                    <iframe src=""  id="djb_iframe"  frameborder="0" style="width: 100%; height: 300px;"></iframe>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-card-header">
                    工作分工 <span class="layui-btn layui-btn-sm layui-btn-normal fr" id="distBtn"><i
                        class="layui-icon layui-icon-release"></i>分配</span>
                </div>
                <div class="layui-card-body">
                    <table id="table"></table>

                </div>

            </div>
        </div>
        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    已分配列表
                </div>
                <div class="layui-card-body">
                    <div class="table-wrapper">
                        <div class="layui-form">
                            <div class="layui-form-item ">
                                <label class="layui-form-label">分配承办人</label>
                                <div class="layui-input-block">
                                    <select name="city" id="fpcbr" lay-filter="fpcbr">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div style="overflow:hidden;">
                            <table id="distributeTable"></table>
                            <div class="layui-btn layui-btn-normal fr"><span class="layui-btn layui-btn-sm layui-btn-normal fr" id="distBtn1"><i
                                    class="layui-icon layui-icon-release"></i>撤销</span></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div>
            <hr>
            <div class="layui-btn layui-btn-normal fr"><span class="layui-btn layui-btn-sm layui-btn-normal fr" id="distBtn2"><i
                    class="layui-icon layui-icon-release"></i>分案</span></div>
        </div>
    </div>
</div>




<script>
    var table = layui.table,
        layer = layui.layer,
        form = layui.form;
        layer = layui.layer;
    $(function () {
        //查询部门承办人列表
        queryBmPerson($("#bmid").val());
        //内嵌上级审批案件详情页面
        var path = '${ctx}/db/toshenhe.do?isReadOnly=1&id=' + $("#idyc").val() + '&wh=' + $("#whyc").val() + '&bllx=' + $("#bllxyc").val()
            + '&nodeid=8888&nodename=院领导审批&cjr=' + $("#cjryc").val() + '&cbbmid=' + $("#cbbmidyc").val() + '&cbbmmc=' + $("#cbbmmc").val() + '&lclx=' + $("#lclxyc").val() + '&isfa=' + $("#isfa").val();
        $('#djb_iframe').attr('src', encodeURI(path));
        form.render();
        //初始化工作分工列表
         table.render({
            elem: '#table',
             id:"gzfg"
            ,url: '${ctx}/nqfa/queryNqfaTable.do'//数据接口
            ,page: true //开启分页
            ,limit: 10
            ,where: {"djid": $("#whyc").val(),"zt":"0","isfa":0,"iszz":"0"}
            ,cols: [[ //表头
                {type:'checkbox'},
                 {field:'zizeng',align: 'center',  title: '序号',templet:'#zizeng'}
                ,{field: 'dcsxmc', title: '督查事项' }
                ,{field: 'dczqtype', title: '督查周期',templet: function (d) {
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
                     }}
                ,{field: 'qtbmid', title: '牵头部门'}
                ,{field: 'zzbmid', title: '主责部门'}
            ]]
        });

        //初始化已分配任务列表
       table.render({
            id:"yfp",
            elem: '#distributeTable'
            ,url: '${ctx}/nqfa/queryNqfaTable.do'//数据接口
            ,page: true //开启分页
            ,limit: 10
            , where: {"djid": $("#whyc").val(),"zt":"1","isfa":0,"iszz":"0"}
            ,limits:[5]
            ,cols: [[ //表头
                {type:'checkbox'},
                {field:'zizeng',align: 'center', title: '序号',templet:'#zizeng'}
                ,{field: 'dcsxmc', title: '督查事项'}
                ,{field: 'dczqtype', title: '督查周期',templet: function (d) {
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
                   }}

            ]]
        });
      //分配
        $('#distBtn').on('click', function () {
            var checkStatus = table.checkStatus('gzfg'),
                data = checkStatus.data;
            if(data == null || data.length == 0){
                layer.msg("请至少选择一个任务进行分配", {icon: '0',offset:['250px'],time:1000});
                return;
            }
            if($("#fpcbr").val() =="" || $("#fpcbr").val() == null){
                layer.msg("请选择承办人", {icon: '0',offset:['250px'],time:1000});
                return;
            }
            updateFp(data,$("#fpcbr").val(),$("#fpcbr  option:selected").text());
    })
     //撤销
        $('#distBtn1').on('click', function () {
            var checkStatus = table.checkStatus('yfp'),
                data = checkStatus.data;
            if(data == null || data.length == 0){
                layer.msg("请至少选择一个任务进行撤销", {icon: '0',offset:['250px'],time:1000});
                return;
            }
            updateCx(data);
        })

        //分案
        $('#distBtn2').on('click', function () {
            $.ajax({
                url: '${ctx}/nqfa/updateFa.do',//数据接口
                type: "post",
                data: {"djid": $("#whyc").val(),"nodeid": "0105","zt":"1","isfa":"0","iszz":"0","bllx":$("#bllxyc").val()},
                dataType: "json",
                success: function (data) {
                    if(data.code == '1'){
                        table.reload('gzfg', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }

                        });
                        table.reload('yfp', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }

                        });
                        layer.msg(data.msg, {icon: data.code,offset:['250px'],time:1000},function () {
                            window.location.href = "/db/index.do?lclx=zyjc";
                        });
                    }else{
                        layer.msg(data.msg, {icon: data.code,offset:['250px'],time:1000});
                    }
                },
                error: function () {
                    layer.closeAll();
                    layer.msg("操作失败", {icon: 2});
                }
            });
        })
        //监听承办人下拉框事件
        form.on('select(fpcbr)', function(data){
            table.reload('yfp', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where:{
                    "lzuserid":data.value //额外参数用户
                }

            });

        })
    })




    function queryBmPerson(id) {
        $.ajax({
            url: '/db/queryBmPerson.do',
            type: "post",
            data:{'unitid':id,'flag':"normal"},
            async: false,
            dataType: "json",
            success: function (data) {
                $("#fpcbr").empty();
                var str= "<option value=''>请选择</option>";
                for (var i = 0; i < data.data.length; i++) {
                    str+="<option value='"+data.data[i].USERID+"'>"+data.data[i].USERNAME+"</option>";
                }
                $("#fpcbr").html(str);
            },
        });
    }

    function  updateFp(data,cbr,cbrname){
        $.ajax({
            url: '${ctx}/nqfa/updateFp.do',//数据接口
            type: "post",
            data: {
                "data": JSON.stringify(data), "lzuserid": cbr,"lzusername":cbrname},
            //async: false,
            dataType: "json",
            success: function (data) {
                layer.msg(data.msg, {icon: data.code,offset:['250px'],time:1000});
                table.reload('gzfg', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });
                table.reload('yfp', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });
            },
            error: function () {
                layer.closeAll();
                layer.msg("操作失败", {icon: 2});
            }
        });
    }

    function updateCx(data){
        $.ajax({
            url: '${ctx}/nqfa/updateCx.do',//数据接口
            type: "post",
            data: {"data": JSON.stringify(data)},
            //async: false,
            dataType: "json",
            success: function (data) {
                layer.msg(data.msg, {icon: data.code,offset:['250px'],time:1000});
                table.reload('gzfg', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });
                table.reload('yfp', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }

                });
            },
            error: function () {
                layer.closeAll();
                layer.msg("操作失败", {icon: 2});
            }
        });
    }
</script>

<jsp:include page="../footer.jsp"></jsp:include>