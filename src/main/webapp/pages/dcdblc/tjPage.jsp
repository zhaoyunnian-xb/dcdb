<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/4/3
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${ctx}/pages/header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <input type="hidden" id="lclx" value="${lclx}">
            <div class="layui-tab layui-tab-card" lay-filter="demo">
                <ul class="layui-tab-title">
                    <li class="layui-this" data-tj="bmtj">部门统计</li>
                    <li data-tj="lxtj">类型统计</li>
                    <li data-tj="zttj">状态统计</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <div class="layui-row">
                            <div class="layui-form">
                                <div class="layui-form-item">
                                    <div class="layui-col-md10">
                                        <%--<label class="layui-form-label">年份：</label>
                                        <div class="layui-input-inline">
                                            <select id="nf_bm"></select>
                                        </div>--%>
                                            <label class="layui-form-label">开始时间：</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="startDate_bm" id="startDate_bm" class="layui-input"/>
                                            </div>
                                            <label class="layui-form-label">结束时间：</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="endDate_bm" id="endDate_bm" class="layui-input"/>
                                            </div>
                                        <label class="layui-form-label">状态：</label>
                                        <div class="layui-input-inline">
                                            <select id="zt">
                                                <option value="1" selected >未完结</option>
                                                <option value="3" >已完结</option>
                                                <option value="0" >全部</option>
                                            </select>
                                        </div>
                                        <div class="layui-input-inline">
                                            <span class="layui-btn layui-btn-normal" onclick="tjQuery('bmtj');">查询</span>
                                            <span class="layui-btn layui-btn-normal" onclick="importExecl('bmtj');">导出</span>
                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <div class="table-wrapper">
                            <table class="layui-table" id="bmtjTable" lay-filter="bmtjTable" ></table>
                        </div>
                    </div>
                    <%--第二个页面--%>
                    <div class="layui-tab-item">
                        <div class="layui-row">
                            <div class="layui-form">
                                <div class="layui-form-item">
                                    <div class="layui-col-md12">
                                        <%--<label class="layui-form-label">年份：</label>
                                        <div class="layui-input-inline">
                                            <select id="nf_lx"></select>
                                        </div>--%>
                                            <label class="layui-form-label">开始时间：</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="startDate_lx" id="startDate_lx" class="layui-input"/>
                                            </div>
                                            <label class="layui-form-label">结束时间：</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="endDate_lx" id="endDate_lx" class="layui-input"/>
                                            </div>
                                            <label class="layui-form-label">状态：</label>
                                            <div class="layui-input-inline">
                                                <select id="lxtjzt">
                                                    <option value="1" selected>未完结</option>
                                                    <option value="3" >已完结</option>
                                                    <option value="0" >全部</option>
                                                </select>
                                            </div>
                                            <span class="layui-btn layui-btn-normal" onclick="tjQuery('lxtj');">查询</span>
                                            <span class="layui-btn layui-btn-normal" onclick="importExecl('lxtj');">导出</span>
                                    </div>
                                    <div class="layui-col-md10">
                                        <div class="filter_box">
                                            <label class="layui-form-label">查询类型：</label>
                                            <div class="layui-input-block">
                                                <input type="radio" name="typechecked" value="bllx" title="办理类型" lay-filter='typechecked' checked />
                                                <input type="radio" name="typechecked" value="pslx" title="批示类型"  lay-filter='typechecked'/>
                                                <input type="radio" name="typechecked" value="issjysj" title="省级以上批示件"  lay-filter='typechecked'/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="table-wrapper">
                            <table class="layui-table" id="lxtjTable" lay-filter="lxtjTable" ></table>
                        </div>
                    </div>
                    <%--第三个页面--%>
                    <div class="layui-tab-item">
                        <div class="layui-row">
                            <div class="layui-form">
                                <div class="layui-form-item">
                                   <%-- <div class="layui-col-md6">
                                        <label class="layui-form-label">年份：</label>
                                        <div class="layui-input-inline">
                                            <select id="nf_zt"></select>
                                        </div>
                                    </div>--%>
                                       <label class="layui-form-label">开始时间：</label>
                                       <div class="layui-input-inline">
                                           <input type="text" name="startDate_lx" id="startDate_zt" class="layui-input"/>
                                       </div>
                                       <label class="layui-form-label">结束时间：</label>
                                       <div class="layui-input-inline">
                                           <input type="text" name="endDate_lx" id="endDate_zt" class="layui-input"/>
                                       </div>
                                    <div class="layui-col-md6" >
                                        <span class="layui-btn layui-btn-normal" onclick="tjQuery('zttj');">查询</span>
                                        <span class="layui-btn layui-btn-normal" onclick="importExecl('zttj');">导出</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="table-wrapper">
                            <table class="layui-table" id="zttjTable" lay-filter="zttjTable" ></table>
                        </div>
                    </div>
                </div>
            </div>

            <form>
                <input type="hidden" id="lxdetail_zt">
                <input type="hidden" id="lxdetail_lclx">
                <input type="hidden" id="lxdetail_startDate">
                <input type="hidden" id="lxdetail_endDate">
                <input type="hidden" id="lxdetail_checkedtype">
                <input type="hidden" id="lxdetail_lxtypename">
                <div id="pzcolumns" style="display: none">
                    <div class="" id="dclb" style="text-align: right;padding:10px 15px 0px 0px;">
                    </div>
                    <div class="layui-card">
                        <div class="layui-card-body" style="padding:0px 15px">
                            <table id="tabledetail_lx" lay-filter="tabledetail_lx" class="layui-table" style="margin-top:0px;"></table>
                        </div>
                    </div>
                </div>
            </form>


        </div>
    </div>
</div>

</body>
<script>
    layui.use('element', function(){
        var element = layui.element;
        element.on('tab(demo)', function(data){
            tjQuery($(this).data("tj"));
        });
    });

    layui.use('laydate', function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#startDate_lx', //
            format: 'yyyy年MM月dd日',
            value:  new Date().getFullYear()+"年"+"01月01日",
            isInitValue: true
        });
        laydate.render({
            elem: '#endDate_lx', //指定元素
            format: 'yyyy年MM月dd日',
            value:  new Date(),
            isInitValue: true
        });
        laydate.render({
            elem: '#startDate_zt', //指定元素
            format: 'yyyy年MM月dd日',
            value:  new Date().getFullYear()+"年"+"01月01日",
            isInitValue: true
        });
        laydate.render({
            elem: '#endDate_zt', //指定元素
            format: 'yyyy年MM月dd日',
            value:  new Date(),
            isInitValue: true
        });
        laydate.render({
            elem: '#startDate_bm', //指定元素
            format: 'yyyy年MM月dd日',
            value:  new Date().getFullYear()+"年"+"01月01日",
            isInitValue: true
        });
        laydate.render({
            elem: '#endDate_bm', //指定元素
            format: 'yyyy年MM月dd日',
            value:  new Date(),
            isInitValue: true
        });

    })

    $(function () {
        getYear();
        tjQuery('bmtj');

      layui.use('form', function(){
        var form = layui.form;
        var table = layui.table;
        form.render();
        //各种基于事件的操作，下面会有进一步介绍
          //监听是否省级以上领导批示件的单选按钮事件
          form.on('radio(typechecked)', function (obj) {
               var checkedtype = $(this).val();
              var typemc = "";
              var typeBs ="";
              if(checkedtype == 'bllx'){
                  typemc = "办理类型";
              }else if(checkedtype == 'pslx'){
                  typemc = "批示类型";
              }else if(checkedtype == 'issjysj'){
                  typemc = "是否省级以上批示件 ";
              }
               var newcols = [ //表头
                    {field: "zizeng", title: "序号",width:120,align:'center', templet: '#zizeng'},
                    {field: "LXTYPENAME", title: typemc,align:'center'}
                    ,{field: "LXTJ", title:"数量",align:'center',event:"caseDetal" }];
               table.reload('lxtjTable', {
                  url: '${ctx}/Tjtable/lxtj.do',
                  where:{"checkedtype":checkedtype},
                  page: {
                      curr: 1 //重新从第 1 页开始
                  },
                   cols:[newcols]
              });

          });

      });

    })
    function importExecl(v){
        var zt="";
        var checkedtype ="";
        var lclx ="";
        var startDate_lx ="";
        var endDate_lx ="";
        var nf = "";
        if(v=='bmtj'){
            zt=$('#zt option:selected').val();
            nf = $('.nf option:selected').val();
            startDate_lx = $('#startDate_lx').val();
            endDate_lx = $('#endDate_lx').val();
        }else if(v=='lxtj'){
            zt=$('#lxtjzt option:selected').val();
            checkedtype= $("input:radio[name='typechecked']:checked").val();
            lclx = $("#lclx").val();
            startDate_lx = $('#startDate_lx').val();
            endDate_lx = $('#endDate_lx').val();
        }else if(v=='zttj'){
            zt=$('#zttjzt option:selected').val();
            lclx = $("#lclx").val();
            startDate_lx = $('#startDate_zt').val();
            endDate_lx = $('#endDate_zt').val();
        }
       // window.location.href='${ctx}/Tjtable/importBm.do?zt='+zt+'&lx='+v+"&lclx="+$("#lclx").val()+"&nf="+nf;
        window.location.href='${ctx}/Tjtable/importBm.do?zt='+zt+'&lx='+v+"&lclx="+$("#lclx").val()+"&nf="+nf+
            "&checkedtype="+checkedtype+"&startTime="+startDate_lx+"&endTime="+endDate_lx;

    }

    function importExeclDetail(type){
            var lxdetail_zt = $("#lxdetail_zt").val();
            var lxdetail_lclx = $("#lxdetail_lclx").val();
            var lxdetail_startDate = $("#lxdetail_startDate").val();
            var lxdetail_endDate = $("#lxdetail_endDate").val();
            var lxdetail_checkedtype = $("#lxdetail_checkedtype").val();
            var lxdetail_lxtypename = $("#lxdetail_lxtypename").val();
            window.location.href='${ctx}/Tjtable/importExeclDetail.do?lxdetail_zt='+lxdetail_zt+'&lxdetail_lclx='+lxdetail_lclx+
                      "&lxdetail_startDate="+lxdetail_startDate+"&lxdetail_endDate="+lxdetail_endDate+"&lxdetail_checkedtype="+lxdetail_checkedtype+
                      "&lxdetail_lxtypename="+lxdetail_lxtypename+"&tjlx="+type;

    }

    function tjQuery(v){
        var zt="";
        var table = layui.table;
        var table1= layui.table;
        var table2= layui.table;
        var nf_bm = $('#nf_bm option:selected').val();
        //var nf_lx = $('#nf_lx option:selected').val();年份
        var startDate_lx = $('#startDate_lx').val();
        var endDate_lx = $('#endDate_lx').val();
        var nf_zt = $('#nf_zt option:selected').val();

      var startDate_zt = $('#startDate_zt').val();
      var endDate_zt = $('#endDate_zt').val();

        var startDate_bm = $('#startDate_bm').val();
        var endDate_bm = $('#endDate_bm').val();

        if(v=='bmtj'){
            zt=$('#zt option:selected').val();
            table.render({
                elem: '#bmtjTable'
                ,url: '${ctx}/Tjtable/bmtj.do' //数据接口
                //,toolbar:true
                ,where:{"zt":zt,"lclx":$("#lclx").val(),'startTime':startDate_bm, 'endTime':endDate_bm}
                //,defaultToolbar:['exports']
                //,page: true //开启分页
                ,cols: [[ //表头
                    {field: "zizeng", title: "序号",width:120,align:'center', templet: '#zizeng'},
                    {field: 'cbbmmc', title: '部门名称',align:'center'}
                    <%--,{field: 'countM', title: '数量',align:'center', event:"caseDetal"}--%>
                    ,{field: 'countM', title: '数量',align:'center', event:"caseDetal",templet: function(obj){
                         var html = '';
                            return html = '<a style="color:#1b9cff;cursor:pointer;">'+obj.countM+'</a>'
                            }
                    }
                ]]
            });

            table.on('tool(bmtjTable)', function (obj) {//table_db:table的ID
                var cbbmid = obj.data.cbbmid;
                var cbbmmc = obj.data.cbbmmc;
                if (obj.event == 'caseDetal') {//caseDetal:cols中 evet属性值
                    table.render({
                        elem: '#tabledetail_lx',
                        url: '${ctx}/Tjtable/queryDetailbm.do',
                        page: true, //开启分页
                        where:{"zt":zt,"lclx":$("#lclx").val(),"startTime":startDate_bm,"endTime":endDate_bm,"cbbmid":cbbmid},
                        limit: 10,
                        cols: [[
                            {field: 'zizeng', align: 'center', title: '序号',width:60, templet: '#zizeng'},
                            {field: 'wh', align: 'center', title: '批示编号',width:120},
                            {field: 'psrq', align: 'center', title: '批示日期',width:140},
                            {field: 'psjmc', align: 'left', title: '被批示件',event:"caseDetail",templet:function(d){
                                var html = '';
                                return html = '<a style="color:#1b9cff;cursor:pointer;">'+d.psjmc+'</a>';
                                }},
                            {field: 'psnr', align: 'left', title: '批示内容'},
                            {field: 'blqk', align: 'left', title: '批示办理情况'}
                        ]]
                    });
                    showColumns(zt,$("#lclx").val(),startDate_bm,endDate_bm,cbbmid,cbbmmc,"bmtj");
                }
            })


        }else if(v=='lxtj'){
            zt=$('#lxtjzt option:selected').val();
            var checkedtype = $("input:radio[name='typechecked']:checked").val(); //所选择查询类别
            var typemc = "";
            if(checkedtype == 'bllx'){
                typemc = "办理类型";
            }else if(checkedtype == 'pslx'){
                typemc = "批示类型";
            }else if(checkedtype == 'issjysj'){
                typemc = "是否省级以上批示件";
            }
            table1.render({
                elem: '#lxtjTable'
                ,url: '${ctx}/Tjtable/lxtj.do' //数据接口
                //,toolbar:true
                //,where:{"zt":zt,"lclx":$("#lclx").val(),"nf":nf_lx}
                ,where:{"zt":zt,"lclx":$("#lclx").val(),"startDate_lx":startDate_lx,"endDate_lx":endDate_lx,"checkedtype":checkedtype}
                //,defaultToolbar:['exports']
                //,page: true //开启分页
                ,cols: [[ //表头
                {field: "zizeng", title: "序号",width:120,align:'center', templet: '#zizeng'},
                    {field: "LXTYPENAME", title: typemc,align:'center'}
                    ,{field: "LXTJ", title:"数量",align:'center',event:"caseDetal",templet: function(obj){
                        var html = '';
                        return html = '<a style="color:#1b9cff;cursor:pointer;">'+obj.LXTJ+'</a>'
                    }
                    }
                ]]
            })

            table.on('tool(lxtjTable)', function (obj) {//table_db:table的ID
                var data = obj.data;
                var lxtypename = obj.data.LXTYPENAME;
                var checkedtype = $("input:radio[name='typechecked']:checked").val(); //所选择查询类别
                if (obj.event == 'caseDetal') {//caseDetal:cols中 evet属性值
                    table.render({
                        elem: '#tabledetail_lx',
                        url: '${ctx}/Tjtable/queryDetaillx.do',
                        page: true, //开启分页
                        where:{"zt":zt,"lclx":$("#lclx").val(),"startDate_lx":startDate_lx,"endDate_lx":endDate_lx,"checkedtype":checkedtype,"lxtypename":lxtypename},
                        limit: 10,
                        cols: [[
                            {field: 'zizeng', align: 'center', title: '序号',width:60, templet: '#zizeng'},
                            {field: 'wh', align: 'center', title: '批示编号',width:120},
                            {field: 'psrq', align: 'center', title: '批示日期',width:140},
                            {field: 'psjmc', align: 'left', title: '被批示件',event:"caseDetail",templet:function(d){
                                    var html = '';
                                    return html = '<a style="color:#1b9cff;cursor:pointer;">'+d.psjmc+'</a>';
                                }},
                            {field: 'psnr', align: 'left', title: '批示内容'},
                            {field: 'blqk', align: 'left', title: '批示办理情况'}
                        ]]
                    });
                    showColumns(zt,$("#lclx").val(),startDate_lx,endDate_lx,checkedtype,lxtypename,"lxtj");
                }
            })


        }else if(v=='zttj'){
            zt=$('#zttjzt option:selected').val();
            table1.render({
                elem: '#zttjTable'
                ,url: '${ctx}/Tjtable/zttj.do' //数据接口
                //,toolbar:true
                ,where:{"zt":zt,"lclx":$("#lclx").val(),"nf":nf_zt,'startTime':startDate_zt, 'endTime':endDate_zt}
                //,defaultToolbar:['exports']
                //,page: true //开启分页
                ,cols: [[ //表头
                {field: "zizeng", title: "序号",width:120,align:'center', templet: '#zizeng'},
                    {field: 'tf', title: '状态',align:'center'}
                    ,{field: 'm1', title: '数量',align:'center',event:"caseDetail",templet: function(obj){
                        var html = '';
                        return html = '<a style="color:#1b9cff;cursor:pointer;">'+obj.m1+'</a>'
                        }
                    }
                ]]
            })
            table.on('tool(zttjTable)', function (obj) {//table_db:table的ID
                var zt = obj.data.m2;
                if (obj.event == 'caseDetail') {//caseDetal:cols中 evet属性值
                    table.render({
                        elem: '#tabledetail_lx',
                        url: '${ctx}/Tjtable/queryDetailzt.do',
                        page: true, //开启分页
                        where:{"zt":zt,"lclx":$("#lclx").val(),"nf":nf_zt,'startTime':startDate_zt, 'endTime':endDate_zt},
                        limit: 10,
                        cols: [[
                            {field: 'zizeng', align: 'center', title: '序号',width:60, templet: '#zizeng'},
                            {field: 'wh', align: 'center', title: '文号',width:120},
                            {field: 'psrq', align: 'center', title: '批示日期',width:140},
                            {field: 'psjmc', align: 'left', title: '被批示件',event:"caseDetail",templet:function(d){
                                    var html = '';
                                    return html = '<a style="color:#1b9cff;cursor:pointer;">'+d.psjmc+'</a>';
                                }},
                            {field: 'psnr', align: 'left', title: '批示内容'},
                            {field: 'blqk', align: 'left', title: '批示办理情况'}
                        ]]
                    });
                    showColumns(zt,$("#lclx").val(),startDate_zt,endDate_zt,"","","zttj");
                }
            })
        }
    }
    function getYear() {
      var curYear=new Date().getFullYear();
      var html="";
      for (var i = 0; i <3; i++) {
       var year=curYear-i;
        html += "<option value='" + year + "'>" + year + "</option>";
      }
      $("#nf_bm").html(html);
      $("#nf_lx").html(html);
      $("#nf_zt").html(html);
    }

    function showColumns(zt,lclx,startDate_lx,endDate_lx,checkedtype,lxtypename,tjlx) {
        $("#lxdetail_zt").val(zt);
        $("#lxdetail_lclx").val(lclx);
        $("#lxdetail_startDate").val(startDate_lx);
        $("#lxdetail_endDate").val(endDate_lx);
        $("#lxdetail_checkedtype").val(checkedtype);
        $("#lxdetail_lxtypename").val(lxtypename);
        $("#dclb").empty().html("<span class='layui-btn layui-btn-normal' onclick=\"importExeclDetail('"+tjlx+"')\">导出</span>");
       layer.open({
            type: 1,
            shade: 0.3,
            skin: 'demo-class',
            title: '数据详情',
            area: ['1200px', '80%'], //宽高
            closeBtn: 1,
            content: $('#pzcolumns'),
            cancel: function (index, layero) {
                layer.close(index)
                $("#pzcolumns").hide();
            }
        });
        var table = layui.table;
        table.on('tool(tabledetail_lx)', function (obj) {//table_db:table的ID
            var data = obj.data;
            if (obj.event == 'caseDetail') {//caseDetal:cols中 evet属性值
                //window.open(encodeURI('${ctx}/db/toshenhe.do?isReadOnly=1&id=""&wh='+data.wh+'&bllx=""&nodeid=""&nodename=""&cjr=""&cbbmid=""&lclx='+data.lclx));
                window.location.href = encodeURI('${ctx}/db/toshenhe.do?isReadOnly=1&id='+data.id+'&wh='+data.wh+'&bllx='+data.bllx
                    +'&nodeid=8888&nodename=批示存档&cjr=""&cbbmid=""&lclx='+data.lclx);
            }
        })
    }


</script>
<jsp:include page="${ctx}/pages/footer.jsp"></jsp:include>
