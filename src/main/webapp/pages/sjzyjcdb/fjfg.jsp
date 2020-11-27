<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../header.jsp"></jsp:include>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<div class="layui-fluid">
    <input id ="djid" value="${wh}" type="hidden">
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
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    立项信息
                </div>
                <div class="layui-card-body">
                    <iframe src="" frameborder="0" id = "djb_iframe" style="width: 100%; height: 300px;"></iframe>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-card-header">
                    工作分工 <span class="layui-btn layui-btn-sm layui-btn-normal fr" id="addBtn"><i
                        class="layui-icon layui-icon-add-circle"></i>新增</span>
                </div>
                <div class="layui-card-body">
                    <table id="table"></table>
                </div>
            </div>
            <hr>
            <div class="">
                <div class="layui-btn layui-btn-normal fr" onclick="scfg();">生成分工</div>
            </div>
        </div>
    </div>
</div>

<div class="layui-form "   id="addForm" style="padding: 15px;display: none;" >
    <input id="dczqid" value="${dczqid}" type="hidden">
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">督查事项</label>
        <div class="layui-input-block">
            <textarea name="dcsxText" id="dcsxText"  placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item ">
        <label class="layui-form-label">督查周期</label>
        <div class="layui-input-inline">
            <select id="dczq" name="dczq" lay-filter='dczq' autocomplete="off">
                <option value=""></option>
                <option value="0">每周</option>
                <option value="1">每月</option>
                <option value="2">每季度</option>
                <option value="3">每半年</option>
                <option value="4">定制天数</option>
            </select>
        </div>
        <div id="dctsDiv" class="layui-input-inline" >
            <div id="selectDiv" class="layui-input-inline" style="display: none">
                <select id="dcts" name="dcts" class="layui-select" autocomplete="off">
                    <option value=""></option>
                    <option value="1">星期一</option>
                    <option value="2">星期二</option>
                    <option value="3">星期三</option>
                    <option value="4">星期四</option>
                    <option value="5">星期五</option>
                    <option value="6">星期六</option>
                    <option value="7">星期七</option>
                </select>
            </div>
            <input type="text" id="dctsin" name="dctsin" required  lay-verify="required" placeholder="请输入天数" autocomplete="off"
                   class="layui-input" style="display: none">
            <div class="layui-btn layui-btn-normal" id="dcdqbtn" onclick="xinzengdcdq();" style="display: none">新增督查周期</div>
        </div>

    </div>
    <div class="layui-card-body" id="dczqdiv" style="display: none">
        <table id="dczqtable"></table>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">牵头部门</label>
        <div class="layui-input-block">
            <input type="text" id="qtbm" name="qtbm" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">主责部门</label>
        <div class="layui-input-block">
            <input type="text" id="zybm" name="zybm" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <div class="layui-btn layui-btn-normal" onclick="saveGzfg();">保存</div>
        </div>
    </div>
</div>
<div class="layui-form "   id="editForm" style="padding: 15px;display: none;" >
    <input id="dczqidedit" value="" type="hidden">
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">督查事项</label>
        <div class="layui-input-block">
            <textarea name="dcsxTextedit" id="dcsxTextedit"  placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item ">
        <label class="layui-form-label">督查周期</label>
        <div class="layui-input-inline">
            <select id="dczqedit" name="dczqedit"  class="layui-disabled" lay-filter='dczqedit' autocomplete="off">
                <option value=""></option>
                <option value="0">每周</option>
                <option value="1">每月</option>
                <option value="2">每季度</option>
                <option value="3">每半年</option>
                <option value="4">定制天数</option>
            </select>
        </div>
        <div class="layui-input-inline">
            <div id="selectDivedit" class="layui-input-inline" style="display: none">
                <select id="dctsedit" name="dctsedit" lay-filter='dctsedit' autocomplete="off">
                    <option value=""></option>
                    <option value="1">星期一</option>
                    <option value="2">星期二</option>
                    <option value="3">星期三</option>
                    <option value="4">星期四</option>
                    <option value="5">星期五</option>
                    <option value="6">星期六</option>
                    <option value="7">星期七</option>
                </select>
            </div>
            <input type="text" id="dctsinedit" name="dctsinedit" required  lay-verify="required" placeholder="请输入天数" autocomplete="off"
                   class="layui-input" style="display: none">
            <div class="layui-btn layui-btn-normal" id="dcdqbtnedit" onclick="xinzengdcdq();" style="display: none">新增督查周期</div>
        </div>

    </div>
    <div class="layui-card-body" id="dczqdivedit" style="display: none">
        <table id="dczqtableedit"></table>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">牵头部门</label>
        <div class="layui-input-block">
            <input type="text" id="qtbmedit" name="qtbmedit" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">主责部门</label>
        <div class="layui-input-block">
            <input type="text" id="zybmedit" name="zybmedit" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <div class="layui-btn layui-btn-normal" onclick="saveGzfgedit();">保存</div>
        </div>
    </div>
</div>
<div class="layui-form " id="dcdpForm" style="padding: 15px;display: none;" >
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">督办周期</label>
        <div class="layui-input-block">
            <input type="text" id="dbzqInput" name="dbzqInput" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input" disabled>
            <input type="hidden" id="dbzqValue" value="">
            <input type="text" id="flag" type="hidden">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">督办日期</label>
        <div class="layui-input-block">
            <input type="text" id="dbrq" name="dbrq" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <div class="layui-btn layui-btn-normal" onclick="saveDbrq();">保存</div>
        </div>
    </div>
</div>
<div class="layui-form " id="scfgForm" style="padding: 15px;display: none;" >
    <div class="layui-card-body" id="dczqdivscfg">
        <table id="dczqtablescfg"></table>

    </div>
</div>
<div class="layui-card-body" id="wqckqdiv" style="display: none">
    <table id="wqcktable"></table>
</div>
<script type="text/html" id="table-operate">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script>
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;
    var  laydate = layui.laydate;
    var index1 ="";
    var index2 ="";
    var index3 ="";
    var index4 ="";
    $(function () {
        var path = '${ctx}/db/toshenhe.do?isReadOnly=1&id=' + $("#idyc").val() + '&wh=' + $("#whyc").val() + '&bllx=' + $("#bllxyc").val()
            + '&nodeid=8888&nodename=院领导审批&cjr=' + $("#cjryc").val() + '&cbbmid=' + $("#cbbmidyc").val() + '&cbbmmc=' + $("#cbbmmc").val() + '&lclx=' + $("#lclxyc").val() + '&isfa=' + $("#isfa").val();
        $('#djb_iframe').attr('src', encodeURI(path));
        layui.use('laydate', function () {
            laydate.render({
                elem: '#dbrq',
                format: 'yyyy-MM-dd'
            })
        })

        form.render();
        $('#addBtn').on('click', function () {
            $("#selectDiv").css("display", "none");
            $("#dcdqbtn").css("display", "none");
            $("#dczqdiv").css("display", "none");
            $("#dctsin").css("display", "none");
            $("#dczqid").val(uuid());
            $("#flag").val('1');
            index1 = layer.open({
                type: 1,
                title:'工作分工',
                area: ['700px', '500px'], //宽高
                offset:['50px'],
                content: $('#addForm'),
                cancel: function(index, layero){
                    layer.close(index);
                    $("#dcsxText").val("");
                    $("#dcts").val("");
                    $("#zybm").val("");
                    $("#qtbm").val("");
                    $("#dczq").val("");//find("option[value = '']").attr("selected","selected");
                    $("#dctsin").val("");
                    $("#qtbm").attr("qtbmqb","");
                    $("#zybm").attr("zybmqb","");
                    $('#addForm').hide();
                    form.render();
                    return false;
                }

            });
        })
        queryGzfg();


    })
    form.on('select(dczq)', function (data) {
        //如果周期为周或者定制天数，则直接确定哪天填写
        if(data.value == '0'){
            $("#selectDiv").css("display","block");
            $("#dcdqbtn").css("display","none");
            $("#dczqdiv").css("display","none");
            $("#dctsin").css("display","none");
        }else if(data.value == '4'){
            $("#selectDiv").css("display","none");
            $("#dcdqbtn").css("display","none");
            $("#dczqdiv").css("display","none");
            $("#dctsin").css("display","block");
            $('#dctsin').blur(function() {
                var reg = /^[0-9]*$/;
                if(!reg.test($('#dctsin').val())){
                    layer.alert("定制天数只能为数字，请重新填写");
                }
            })
        }else if(data.value == '') {
            $("#selectDiv").css("display", "none");
            $("#dcdqbtn").css("display", "none");
            $("#dczqdiv").css("display", "none");
            $("#dctsin").css("display", "none");
        }else {
            $("#selectDiv").css("display","none");
            $("#dcdqbtn").css("display","block");
            $("#dczqdiv").css("display","block");
            $("#dctsin").css("display","none");
            queryDbrq();
        }
    })
    form.on('select(dczqedit)', function (data) {
        //如果周期为周或者定制天数，则直接确定哪天填写
        if(data.value == '0'){
            $("#selectDivedit").css("display","block");
            $("#dcdqbtnedit").css("display","none");
            $("#dczqdivedit").css("display","none");
            $("#dctsinedit").css("display","none");
        }else if(data.value == '4'){
            $("#selectDivedit").css("display","none");
            $("#dcdqbtnedit").css("display","none");
            $("#dczqdivedit").css("display","none");
            $("#dctsinedit").css("display","block");
            $('#dctsinedit').blur(function() {
                var reg = /^[0-9]*$/;
                if(!reg.test($('#dctsinedit').val())){
                    layer.alert("定制天数只能为数字，请重新填写");
                }
            })
        }else if(data.value == ''){
            $("#selectDivedit").css("display","none");
            $("#dcdqbtnedit").css("display","none");
            $("#dczqdivedit").css("display","none");
            $("#dctsinedit").css("display","none");
        }else {
            $("#selectDivedit").css("display","none");
            $("#dcdqbtnedit").css("display","block");
            $("#dczqdivedit").css("display","block");
            $("#dctsinedit").css("display","none");
            queryDbrqedit();
        }
    })
    //查询工作分工列表
    function queryGzfg() {
        var djid = $("#djid").val();
        table.render({
            elem: '#table'
            ,height: 312
            ,url: '${ctx}/sjjc/queryFgTable.do' //数据接口
            ,page: true //开启分页
            , where:{"djid":djid}
            ,cols: [[ //表头
                {field: 'zizeng', align: 'center', width: '5%', title: '序号', templet: '#zizeng'}
                ,{field: 'dcsxmc',align: 'center', title: '督查事项', width:'20%'}
                ,{field: 'dczqtype',align: 'center', title: '督查周期', width:'10%'}
                ,{field: 'qtbmid',align: 'center', title: '牵头部门', width:'25%'}
                ,{field: 'zzbmid',align: 'center', title: '主责部门', width: '25%'}
                ,{field: 'id',align: 'center', title: '主键', hide: "true", width: '25%'}
                ,{field: 'isfq',align: 'center', title: '是否发起', hide: "true", width: '25%'}
                ,{field: 'czzt', align: 'center', title: '操作', templet: function (d) {
                        var id = d.id;
                        var isfq = d.isfq;
                        var html = "";
                        if(isfq == '0'||isfq == ''){
                            html += "<div>";
                            html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"gzfgBianji('" +
                                id + "')\">" + "编辑" + "</a>";
                            html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"shanchugzfg('"+id+"')\">" + "删除" + '</a></div>';
                        }else if(isfq == '2'){
                            html += "<div>已终止</div>";
                        }else if(isfq == '1'){
                            html += "<div>已发起</div>";
                        }
                        return html;
                    }
                }
            ]]
        });
    }
    //工作分工删除
    function shanchugzfg(id){
        layer.confirm("确定要删除？", {btn: ['确定', '取消']}, function (index){
            $.ajax({
                url: "${ctx}/sjjc/shanchugzfg.do",
                type: "post",
                async: false,
                data: {"id":id},
                dataType: "json",
                success: function (result) {
                    layer.close(index);
                    layer.msg(result.msg,{icon:result.code,time:1000},function () {
                        queryGzfg();
                    });
                }
            })
        });
    }
    //新建牵头部门选择
    $('#qtbm').on('click',function(){
        var qtbmhx = gandiao("qtbm",$("#qtbm").attr("qtbmqb") ||'') ;
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择牵头部门',
            content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds='+qtbmhx/*+'&isRemove=true&removeIds='+zybmhx+phbmhx*/,
            area: ['650px', '80%'],
            offset:['20px'],
            scrollbar: false,
            btn: ['确认'],
            yes:function(index,layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                console.log(a)
                var str = "";
                var str2 = "";
                $.each(a,function (i,item) {
                    var userid = item.id;
                    var username = item.label;
                    var depid = item.parent.id;
                    var depname = item.parent.label;
                    str += depid+"@"+depname+"@"+userid+"@"+username+","
                    str2 += depname+",";
                })
                str = str.substr(0,str.length-1);
                str2 = str2.substr(0,str2.length-1);
                $("#qtbm").attr("qtbmqb",str);
                $("#qtbm").val(str2);
                layer.close(index);
            }
        })
    })
    //新建主要部门选择
    $('#zybm').on('click',function(){
        var zybmhx = gandiao("zybm",($("#zybm").attr("zybmqb")||'')) ;
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择主责部门',
            content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds='+zybmhx/*+'&isRemove=true&removeIds='+qtbmhx+phbmhx*/,
            area: ['650px', '80%'],
            offset:['20px'],
            scrollbar: false,
            btn: ['确认'],
            yes:function(index,layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                var str2 = "";
                $.each(a,function (i,item) {
                    var userid = item.id;
                    var username = item.label;
                    var depid = item.parent.id;
                    var depname = item.parent.label;
                    str += depid+"@"+depname+"@"+userid+"@"+username+","
                    str2 += depname+",";
                })
                str = str.substr(0,str.length-1);
                str2 = str2.substr(0,str2.length-1);
                $("#zybm").attr("zybmqb",str);
                $("#zybm").val(str2);
                layer.close(index);
            }
        })
    })
    //编辑牵头部门选择
    $('#qtbmedit').on('click',function(){
        var qtbmhx = gandiao("qtbmedit",$("#qtbmedit").attr("qtbmqb") ||'') ;
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择牵头部门',
            content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds='+qtbmhx/*+'&isRemove=true&removeIds='+zybmhx+phbmhx*/,
            area: ['650px', '80%'],
            scrollbar: false,
            btn: ['确认'],
            yes:function(index,layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                var str2 = "";
                $.each(a,function (i,item) {
                    var userid = item.id;
                    var username = item.label;
                    var depid = item.parent.id;
                    var depname = item.parent.label;
                    str += depid+"@"+depname+"@"+userid+"@"+username+","
                    str2 += depname+",";
                })
                str = str.substr(0,str.length-1);
                str2 = str2.substr(0,str2.length-1);
                $("#qtbmedit").attr("qtbmqb",str);
                $("#qtbmedit").val(str2);
                layer.close(index);
            }
        })
    })
    //编辑主要部门选择
    $('#zybmedit').on('click',function(){
        var zybmhx = gandiao("zybmedit",($("#zybmedit").attr("zybmqb")||'')) ;
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择主责部门',
            content: '/pages/eleTree.jsp?isSctSamDept=false&isEcho=true&echoIds='+zybmhx/*+'&isRemove=true&removeIds='+qtbmhx+phbmhx*/,
            area: ['650px', '80%'],
            scrollbar: false,
            btn: ['确认'],
            yes:function(index,layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                var str2 = "";
                $.each(a,function (i,item) {
                    var userid = item.id;
                    var username = item.label;
                    var depid = item.parent.id;
                    var depname = item.parent.label;
                    str += depid+"@"+depname+"@"+userid+"@"+username+","
                    str2 += depname+",";
                })
                str = str.substr(0,str.length-1);
                str2 = str2.substr(0,str2.length-1);
                $("#zybmedit").attr("zybmqb",str);
                $("#zybmedit").val(str2);
                layer.close(index);
            }
        })
    })
    //回显方法
    function gandiao(id,str){
        var a = str.split(",");
        var b = $("#"+id).val()||'';
        var str1 = "";
        if(b != null){
            $.each(a,function (i,item) {
                str1 += item.split("@")[2]+"@";
            });
        }
        return str1;
    }
    //工作分工新建保存
    function saveGzfg() {
        var dcsxText = $("#dcsxText").val();
        var dczq = $("#dczq").val();
        var dcts = $("#dcts").val();
        if(dcts == ''||dcts == null){
            dcts = $("#dctsin").val();
        }
        var qtbm = $("#qtbm").attr("qtbmqb");
        var zybm = $("#zybm").attr("zybmqb");
        var djid = $("#djid").val();
        var dczqid = $("#dczqid").val();
        var flag = $("#flag").val();
        $.ajax({
            url: "${ctx}/sjjc/saveGzfg.do",
            type: "post",
            async: false,
            data: {"dcsxText":dcsxText,"dczq":dczq,"qtbm":qtbm,"zybm":zybm,"dcts":dcts,"djid":djid,"dczqid":dczqid,"flag":flag},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:1,time:1000},function () {
                    layer.close(index1);
                    $("#addForm").hide();
                    $("#dcsxText").val("");
                    $("#dczq").find("option[value = '']").attr("selected","selected");
                    $("#dctsin").val("");
                    $("#dcts").val("");
                    $("#zybm").val("");
                    $("#qtbm").val("");
                    $("#qtbm").attr("qtbmqb","");
                    $("#zybm").attr("zybmqb","");
                    queryGzfg();
                });
            }
        })
    }
    //工作分工编辑保存
    function saveGzfgedit() {
        var dcsxText = $("#dcsxTextedit").val();
        var dczq = $("#dczqedit").val();
        var dcts = $("#dctsedit").val();
        if(dcts == ''||dcts == null){
            dcts = $("#dctsinedit").val();
        }
        var qtbm = $("#qtbmedit").attr("qtbmqb");
        var zybm = $("#zybmedit").attr("zybmqb");
        var djid = $("#djid").val();
        var dczqid = $("#dczqidedit").val();
        var flag = $("#flag").val();
        $.ajax({
            url: "${ctx}/sjjc/saveGzfg.do",
            type: "post",
            async: false,
            data: {"dcsxText":dcsxText,"dczq":dczq,"qtbm":qtbm,"zybm":zybm,"dcts":dcts,"djid":djid,"dczqid":dczqid,"flag":flag},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:1,time:1000},function () {
                    layer.close(index1);
                    $("#editForm").hide();
                    $("#dcsxTextedit").val("");
                    $("#dczqedit").find("option[value = '']").attr("selected","selected");
                    $("#dctsinedit").val("");
                    $("#dctsedit").val("");
                    $("#zybmedit").val("");
                    $("#qtbmedit").val("");
                    $("#qtbmedit").attr("qtbmqb","");
                    $("#zybmedit").attr("zybmqb","");
                    queryGzfg();
                });
            }
        })
    }
    //督办日期新增
    function xinzengdcdq() {
        var dczq = $("#dczq").val();
        if(dczq == ''){
            dczq = $("#dczqedit").val();
        }
        $("#dbzqValue").val(dczq);
        if(dczq == '0'){
            dczq = '每周'
        }else if(dczq == '1'){
            dczq = '每月'
        }else if(dczq == '2'){
            dczq = '每季度'
        }else if(dczq == '3'){
            dczq = '每半年'
        }else if(dczq == '4'){
            dczq = '定制天数'
        }
        $("#dbzqInput").val(dczq);
        index2 = layer.open({
            type: 1,
            title:'请选择督办期限',
            area: ['600px', '400px'], //宽高
            content: $('#dcdpForm'),
            cancel: function(index, layero){
                layer.close(index);
                $("#dbzqValue").val("");
                $("#dcts").val("");
                $("#zybm").val("");
                $("#qtbm").val("");
                $("#qtbm").attr("qtbmqb","");
                $("#zybm").attr("zybmqb","");
                $("#dczq").find("option[value = '']").attr("selected","selected");
                //$("#dctsedit").find("option[value = '"4"']").attr("selected","selected");
                $("#dctsin").val("");
                //$("#dczqid").val("");
                $('#dcdpForm').hide();
                return false;
            }

        });
    }
    //督办日期保存
    function saveDbrq() {
        var dbrq = $("#dbrq").val();
        var dczqid = $("#dczqid").val();
        if(dczqid == ""||dczqid == null){
            dczqid = $("#dczqidedit").val();
        }
        //var dbzqInput = $("#dbzqInput").val();
        var dbzqValue = $("#dbzqValue").val();
        $.ajax({
            url: "${ctx}/sjjc/saveDbrq.do",
            type: "post",
            async: false,
            data: {"dbrq":dbrq,"dczqid":dczqid,"dbzqInput":dbzqValue},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:1,time:1000},function () {
                    layer.close(index2);
                    $("#dcdpForm").hide();
                    $("#dbrq").val("");

                    queryDbrq();
                    queryDbrqedit();
                });
            }
        })
    }
    //查询新建督办日期
    function queryDbrq() {
        var dczqid = $("#dczqid").val();
        var dczq = $("#dczq").val();
        table.render({
            elem: '#dczqtable'
            ,url: '${ctx}/sjjc/queryDbrqTable.do' //数据接口
            ,page: false //开启分页
            , where:{"dczqid":dczqid,"dczq":dczq}
            ,cols: [[ //表头
                {field: 'zizeng', align: 'center', title: '序号', templet: '#zizeng'}
                ,{field: 'dczqtype',align: 'center', title: '督查周期'}
                ,{field: 'dcrq',align: 'center', title: '督查日期'}
                ,{field: 'czzt', align: 'center', title: '操作', templet: function (d) {
                        var id = d.id;
                        var html = "<div>";
                        /* html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"zynrBianji('" +
                             id + "','" + ndid + "','" + ydnrid + "')\">" + "编辑" + "</a>";*/
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"shanchugdbzq('"+id+"')\">" + "删除" + '</a></div>';
                        return html;
                    }
                }
            ]]
        });
    }
    //删除督查周期
    function shanchugdbzq(id) {
        layer.confirm("确定要删除？", {btn: ['确定', '取消']}, function (index){
            $.ajax({
                url: "${ctx}/sjjc/shanchugdbzq.do",
                type: "post",
                async: false,
                data: {"id":id},
                dataType: "json",
                success: function (result) {
                    layer.close(index);
                    layer.msg(result.msg,{icon:result.code,time:1000},function () {
                        queryDbrq();
                    });
                }
            })
        });
    }
    //查询编辑督办日期
    function queryDbrqedit() {
        var dczqid = $("#dczqidedit").val();
        var dczq = $("#dczqedit").val();
        table.render({
            elem: '#dczqtableedit'
            ,url: '${ctx}/sjjc/queryDbrqTable.do' //数据接口
            ,page: false //开启分页
            , where:{"dczqid":dczqid,"dczq":dczq}
            ,cols: [[ //表头
                {field: 'zizeng', align: 'center', title: '序号', templet: '#zizeng'}
                ,{field: 'dczqtype',align: 'center', title: '督查周期'}
                ,{field: 'dcrq',align: 'center', title: '督查日期'}
                ,{field: 'czzt', align: 'center', title: '操作', templet: function (d) {
                        var id = d.id;
                        var html = "<div>";
                        /* html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"zynrBianji('" +
                             id + "','" + ndid + "','" + ydnrid + "')\">" + "编辑" + "</a>";*/
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"shanchugzfg('"+id+"')\">" + "删除" + '</a></div>';
                        return html;
                    }
                }
            ]]
        });
    }
    //工作任务编辑
    function gzfgBianji(id) {
        $.ajax({
            url: "${ctx}/sjjc/queryGzfg.do",
            type: "post",
            async: false,
            data: {"id":id},
            dataType: "json",
            success: function (result) {
                console.log(result)
                $("#dcsxTextedit").val(result.dcdbDcsx.dcsxmc);
                $("#dctsedit").find("option[value = '"+result.dcdbDcsx.dczq+"']").attr("selected","selected");
                $("#dczqedit").find("option[value = '"+result.dcdbDcsx.dczqtype+"']").attr("selected","selected");
                $("#dczqedit").parent().find("dd[lay-value='"+result.dcdbDcsx.dczqtype+"']").click();
                $("#dctsinedit").val(result.dcdbDcsx.dczq);
                $("#zybmedit").val(result.dcdbDcsx.zzbmmc);
                $("#qtbmedit").val(result.dcdbDcsx.qtbmmc);
                $("#qtbmedit").attr("qtbmqb",result.dcdbDcsx.qtbmid);
                $("#zybmedit").attr("zybmqb",result.dcdbDcsx.zzbmid);
                $("#dczqidedit").val(result.dcdbDcsx.id);
                $("#flag").val("2");
                  /*if(result.dcdbdcsx.dczqtype != ''){
                    $("#dczqedit").attr("disabled","disabled");
                }*/
                form.render();
                queryDbrqedit();
                index1 = layer.open({
                    type: 1,
                    title:'工作分工',
                    area: ['700px', '500px'], //宽高
                    offset:['50px'],
                    content: $('#editForm'),
                    cancel: function(index, layero){
                        layer.close(index);
                        $("#dcsxTextedit").val("");
                        $("#dctsedit").val("");
                        $("#zybmedit").val("");
                        $("#qtbmedit").val("");
                        $("#dczqedit").find("option[value = '']").attr("selected","selected");
                        $("#dctsinedit").val("");
                        $("#qtbmedit").attr("qtbmqb","");
                        $("#zybmedit").attr("zybmqb","");
                        $("#dczqidedit").val("");
                        $('#editForm').hide();
                        return false;
                    }

                });
            }
        })
    }
    function scfg() {
        index3 = layer.open({
            type: 1,
            title: '任务分工管理',
            area: ['900px', '600px'], //宽高
            offset:['50px'],
            content: $('#scfgForm'),
            cancel: function (index, layero) {
                layer.close(index);
                //$("#dczqid").val("");
                $('#scfgForm').hide();
                return false;
            },
            success:function () {
                queryScfg();
            }
        })

    }
    //查询生成工作分工列表
    function queryScfg() {
        var djid = $("#djid").val();
        table.render({
            elem: '#dczqtablescfg'
            ,url: '${ctx}/sjjc/queryFgTable.do' //数据接口
            ,page: true //开启分页
            , where:{"djid":djid}
            ,cols: [[ //表头
                {field: 'zizeng', align: 'center', width: '10%', title: '序号', templet: '#zizeng'}
                ,{field: 'dcsxmc',align: 'center', title: '督查事项', width:'18%'}
                ,{field: 'dczqtype',align: 'center', title: '督查周期', width:'10%'}
                ,{field: 'qtbmid',align: 'center', title: '牵头部门', width:'20%'}
                ,{field: 'zzbmid',align: 'center', title: '主责部门', width: '20%'}
                ,{field: 'id',align: 'center', title: '主键', hide: "true"}
                ,{field: 'isfq',align: 'center', title: '主键', hide: "true"}
                ,{field: 'czzt', align: 'center', title: '操作', templet: function (d) {
                        var id = d.id;
                        var isfq = d.isfq;
                        var html = "<div>";
                        if(isfq == "0"||isfq == ""){
                            html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"faqigzfg('" +
                                id + "')\">" + "发起" + "</a>";
                            html += "<a class='layui-btn layui-btn-sm layui-btn-disabled' href='javascript:void(0)' \">" + "终止" + "</a>";
                        }else if(isfq == "1"){
                            html += "<a class='layui-btn layui-btn-sm layui-btn-disabled' href='javascript:void(0)' \">" + "已发起" + "</a>";
                            html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"zhongzhi('" +
                                id + "')\">" + "终止" + "</a>";
                        }else if(isfq == '2'){
                            html += "<a class='layui-btn layui-btn-sm layui-btn-disabled' href='javascript:void(0)' \">" + "已发起" + "</a>";
                            html += "<a class='layui-btn layui-btn-sm layui-btn-disabled' href='javascript:void(0)' \">" + "已终止" + "</a>";
                        }
                        html += "<a class='layui-btn layui-btn-sm' href='javascript:void(0)' onclick=\"chakan('"+id+"')\">" + "查看" + '</a></div>';
                        return html;
                    }
                }
            ]]
        });
    }
    //工作任务发起
    function faqigzfg(id) {
        $.ajax({
            url: "${ctx}/sjjc/faqigzfg.do",
            type: "post",
            async: false,
            data: {"id":id},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:1,time:1000},function () {
                    queryScfg();
                });
            }
        })
    }
    //工作任务终止
    function zhongzhi(id) {
        $.ajax({
            url: "${ctx}/sjjc/zhongzhi.do",
            type: "post",
            async: false,
            data: {"id":id},
            dataType: "json",
            success: function (result) {
                layer.msg(result.msg,{icon:1,time:1000},function () {
                    queryScfg();
                });
            }
        })
    }
    //往期查看
    function chakan(id) {
        table.render({
            elem: '#wqcktable'
            , url: '/sjjccb/queryCbrSpyjList.do' //数据接口
            , page: false //开启分页
            , where: { "dcsxid": id}
            , cols: [[ //表头
                {field: 'zizeng', align: 'center', title: '序号', templet: '#zizeng'}
                , {field: 'dcsxmc', align: 'center', title: '督查事项'}
                , {field: 'qtbmid', align: 'center', title: '牵头部门'}
                , {field: 'zzbmid', align: 'center', title: '主责部门'}
                , {field: 'dczqtype', align: 'center', title: '督查周期'}
                , {field: 'cbbmmc', align: 'center', title: '承办部门'}
                , {field: 'cbrq', align: 'center', title: '承办日期'}
                , {field: 'cbms', align: 'center', title: '承办描述'}
            ]]
        });

        index4 = layer.open({
            type: 1,
            title: '往期查看',
            area: ['900px', '500px'], //宽高
            offset: ['30px'],
            content: $('#wqckqdiv'),
            cancel: function (index, layero) {
                layer.close(index);
                $('#wqckqdiv').hide();
                return false;
            }

        });
    }
    //生成uuid方法
    function uuid() {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        var uuid = s.join("");
        return uuid;
    }
</script>

<jsp:include page="../footer.jsp"></jsp:include>