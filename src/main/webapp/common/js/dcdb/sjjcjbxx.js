var form = layui.form;
var upload = layui.upload;
var element = layui.element;
layui.use('laydate', function () {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#lxrq', //指定元素
        format: 'yyyy年MM月dd日',
        value: $("#lxrq").val() == "" ? new Date() : $("#lxrq").val(),
        isInitValue: true,
        done: function(value, date, endDate){
            $("#lxrq").val(value);
        }
    });
})
$(function () {
    //查询上级决策待办案件的基本信息
    queryJbxxDzjb();
    var isReadOnly = $("#isReadOnly").val();
    if(isReadOnly == '1'){
        ybreadOnly();
    }



    //执行实例
    form.render();
    form.on('select(cbqx)', function (data) {
        if(data.value == '50'){
            $('#cbqxdz').removeAttr("disabled");
            $('#cbqxdz').blur(function(){
                var reg = /^[0-9]*$/;
                if(!reg.test($('#cbqxdz').val())){
                    layer.msg("请输入数字",{icon:0,offset: ['255px', '500px'],time:1000});
                    $('#cbqxdz').val("");
                    $('#cbqxdz').focus();
                }
            })

        }else{
            $('#cbqxdz').attr("disabled","disabled");
            $('#cbqxdz').val("");
        }

    })

    element.render();
    //附件上传执行实例
    var uploadInst = upload.render({
        elem: '#test1', //绑定元素,
        url: '/jdtb/filesUploadDcdb.do', //上传接口
        data: {
            bmrwid: function () {
                var id = $("#idyc").val();
                return id;
            },
            ywtype: function () {
                var id = $("#lxh").val();
                return id;
            },
        },
        accept: 'file',
        multiple: true,
        before: function () {

        },
        done: function (res) {
            var id = $("#idyc").val();
            var lxh = $("#lxh").val();
            selectUploadFiles(id,lxh);
            //上传完毕回调
        },
        error: function () {
            //请求异常回调
        }
    });
})
//回显
function queryJbxxDzjb() {
    //上级决策流程NodeId  流程ID
    var nodeId = $("#nodeidyc").val();
    //上级决策待办案件的 唯一标识ID
    var sjjcid = $("#idyc").val();
    //立项号
    var wh = $("#whyc").val();
    if(wh != null && wh != ''){
            $.ajax({
                url: "/dzjbxx/sjjcjbxxdetail.do",
                type: "post",
                async: false,
                data: {"wh": wh},
                dataType: "json",
                success: function (jbxx) {
                    if (jbxx != null) {
                        //上级决策--立项号
                        $("#lxh").val(jbxx.lxh == null ? "" : jbxx.lxh);
                        //上级决策--重要决策类型
                        $("#zyjclx").find("option[value = '"+jbxx.zyjclx+"']").attr("selected","selected");
                        //上级决策--立项日期
                        $("#lxrq").val(jbxx.lxrq == null ? "" : jbxx.lxrq);
                        //上级决策--立项名称
                        $("#lxmc").val(jbxx.lxmc == null ? "" : jbxx.lxmc);
                        //上级决策--拟办意见
                        $("#nbyj").val(jbxx.nbyj == null ? "" : jbxx.nbyj);
                        //上级决策--承办部门
                        var cbbmselect = 'dd[lay-value=' + jbxx.cbbmid + ']';
                        $("#cbbm").val(jbxx.cbbmmc == null ? "" : jbxx.cbbmmc);
                        $("#cbbm").attr("cbbmid", jbxx.cbbmid);
                        //上级决策--联系人
                        $("#lxr").val(jbxx.lxr == null ? "" : jbxx.lxr);
                        //上级决策--联系电话
                        $("#lxdh").val(jbxx.lxdh == null ? "" : jbxx.lxdh);


                        //上级决策--办公室负责人：审批人
                        $("#bgsfzrmc").val(jbxx.bgsfzrmc == null ? "" : jbxx.bgsfzrmc);
                        //上级决策--办公室负责人：审批日期
                        $("#bgssprq").val(jbxx.bgssprq == null ? "" : jbxx.bgssprq);
                        //上级决策--办公室负责人：审批意见
                        $("#bgsspyj").val(jbxx.bgsspyj == null ? "" : jbxx.bgsspyj);
                        //上级决策--办公室负责人：审批备注
                        $("#bgsspbz").val(jbxx.bgsspbz == null ? "" : jbxx.bgsspbz);

                        //上级决策--院领导：审批人
                        $("#yldfzrmc").val(jbxx.yldfzrmc == null ? "" : jbxx.yldfzrmc);
                        //上级决策--院领导：审批日期
                        $("#yldsprq").val(jbxx.yldsprq == null ? "" : jbxx.yldsprq);
                        //上级决策--院领导：审批意见
                        $("#yldspyj").val(jbxx.yldspyj == null ? "" : jbxx.yldspyj);
                        //上级决策--院领导：审批备注
                        $("#yldspbz").val(jbxx.yldspbz == null ? "" : jbxx.yldspbz);

                        //上级决策--附件上传
                        selectUploadFiles( $("#idyc").val(),$('#lxh').val());
                        //回填只读
                        dzreadOnly();
                    }
                }
            })
        }

}
//保存
function save(type) {
    var sjjcId = $("#idyc").val();
    var nodeId = $("#nodeidyc").val();//党组--节点流程ID
    if(nodeId == '' || nodeId == null){
        nodeId = "0101";
    }
    var nodeName = $("#nodenameyc").val();//党组--节点流程名称
    if(nodeName == '' || nodeName == null){
        nodeName = "上级决策新建";
    }
    //上级决策--立项号
    var lxh =  $("#lxh").val();
    //上级决策--重要决策类型
    var zyjclx = $("#zyjclx").val();
    //上级决策--立项日期
    var lxrq =  $("#lxrq").val();
    //上级决策--立项名称
   var lxmc =  $("#lxmc").val();
    //上级决策--拟办意见
    var nbyj = $("#nbyj").val();
    //上级决策--承办部门
   // var cbbmselect = 'dd[lay-value=' + jbxx.cbbmid + ']';
    var cbbmmc = $("#cbbm").val();
    var cbbmid =  $("#cbbm").attr("cbbmid");
    //上级决策--联系人
    var  lxr =  $("#lxr").val();
    //上级决策--联系电话
    var lxdh = $("#lxdh").val();

    //上级决策--办公室负责人：审批人
    var bgsfzrmc = $("#bgsfzrmc").val();
    //上级决策--办公室负责人：审批日期
    var bgssprq = $("#bgssprq").val();
    //上级决策--办公室负责人：审批意见
    var bgsspyj =  $("#bgsspyj").val();
    //上级决策--办公室负责人：审批备注
    var bgsspbz = $("#bgsspbz").val();

    //上级决策--院领导：审批人
    var yldfzrmc = $("#yldfzrmc").val();
    //上级决策--院领导：审批日期
    var yldsprq = $("#yldsprq").val();
    //上级决策--院领导：审批意见
    var yldspyj = $("#yldspyj").val();
    //上级决策--院领导：审批备注
    var yldspbz = $("#yldspbz").val();
   var lclx = $("#lclxyc").val();
    if(type == 2){

        if(lxh == "" || lxh == null){
            layer.msg("立项号不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(zyjclx == "" || zyjclx == null){
            layer.msg("重要决策类型不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(lxrq == "" || lxrq == null){
            layer.msg("立项日期不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(lxmc == "" || lxmc == null){
            layer.msg("立项名称不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(nbyj == "" || nbyj == null){
            layer.msg("拟办不能意见为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(cbbmmc == "" || cbbmmc == null){
            layer.msg("承办部门不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(cbbmmc == "" || cbbmmc == null){
            layer.msg("承办部门不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(nodeId == '0102'){
            if(bgsfzrmc  == "" || bgsfzrmc  == null){
                layer.msg("办公室负责人不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
                return ;
            }
            if(bgsspyj   == "" || bgsspyj   == null){
                layer.msg("办公室负责人审批意见不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
                return ;
            }
        }
        if(nodeId == '0103'){
            if(yldfzrmc   == "" || yldfzrmc   == null){
                layer.msg("院领导不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
                return ;
            }
            if(yldsprq    == "" || yldsprq    == null){
                layer.msg("院领导审批意见不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
                return ;
            }
        }
    }
    var param = {};
    param.id = sjjcId;
    param.lxh = lxh;
    param.zyjclx  = zyjclx ;
    param.lxrq  = lxrq ;
    param.lxmc  = lxmc ;
    param.nbyj  = nbyj ;
    param.cbbmmc  = cbbmmc ;
    param.cbbmid  = cbbmid ;
    param.lxr  = lxr ;
    param.lxdh  = lxdh ;
    param.type = type;
    param.nodeId = nodeId;
    param.nodeName= nodeName;
    param.bgsfzrmc = bgsfzrmc;
    param.bgssprq = bgssprq ;
    param.bgsspyj = bgsspyj ;
    param.bgsspbz = bgsspbz ;
    param.yldfzrmc  = yldfzrmc;
    param.yldsprq  = yldsprq;
    param.yldspyj  = yldspyj ;
    param.yldspbz  = yldspbz;
    param.wh= sjjcId;
    param.bllx= zyjclx;
    param.lclx= lclx;
    $.ajax({
        url: "/dzjbxx/sjjcjbxxsave.do",
        type: "post",
        async: false,
        data: param,
        dataType: "json",
        success: function (result) {
            if(type == 1){
                layer.msg(result.msg,{icon:result.code,offset: ['255px', '500px'],time:1000});
            }else{
                if(result.code == 1){
                    if(type == 2){
                        selectPerson("1");//提交流程
                    }else  if(type == 3){
                        reback();//退回流程
                    }
                }else{
                    layer.msg(result.msg,{icon:result.code,offset: ['255px', '500px'],time:1000});
                }
            }
        }
    })

}
//承办部门选择
/*$('#cbbm').on('click',function(){
    var cbbmhx = huixian("cbbm",$("#cbbm").attr("cbbmid") ||'') ;
    var layer = layui.layer;
    layer.open({
        type: 2,
        title: '请选择承办部门',
        content: '/pages/eleTree.jsp?isQryAll=false&isEcho=true&echoIds='+cbbmhx,
        offset:['30px'],
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
                var depid = item.id;
                var depname = item.label;
                str += depid+","
                str2 += depname+",";
            })
            str = str.substr(0,str.length-1);
            str2 = str2.substr(0,str2.length-1);
            $("#cbbm").val(str2);
            $("#cbbm").attr("cbbmid",str);
            layer.close(index);
        }
    })
})*/
$('#cbbm').on('click',function(){
  var cbbmhx = huixian("cbbm",$("#cbbm").attr("cbbmid") ||'') ;
  var cbbmmchx =  $("#cbbm").val() ;
  layer.open({
    type: 2,
    title: '请选择承办部门',
    content: '/pages/dcdblc/transforEle_bm.jsp?isMul=true&cbbmhx='+cbbmhx+'&cbbmmchx='+cbbmmchx,
    area: ['60%', '80%'],
    offset: ['30px'],
    scrollbar: false,
    btn: ['确认'],
    yes: function (index, layero) {
      var body = layer.getChildFrame('body', index);
      var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
      var a = iframeWin.getPersonArr();
      var str = "";
      var str2 = "";
      var userid = "";
      $.each(a,function (i,item) {
        var depid = item.selfid;
        var depname = item.selfname;
        str += depid+","
        str2 += depname+",";
      })
      str = str.substr(0,str.length-1);
      str2 = str2.substr(0,str2.length-1);
      $("#cbbm").val(str2);
      $("#cbbm").attr("cbbmid",str);
      layer.close(index);
    }
  })
})
//回显方法
function huixian(id,str){
    var a = str.split(",");
    var b = $("#"+id).val()||'';
    var str1 = "";
    if(b != null && b!=""){
        $.each(a,function (i,item) {
            str1 += a[i]+",";
        });
    }
    return str1;
}


function  selectUploadFiles(id,ywtype){
    $.ajax({
        url: "/jbxx/selectUploadFiles.do",
        type: "post",
        // async: false,
        data: {"id": id, "ywtype": ywtype},
        dataType: "json",
        success: function (result) {
            var str = "";
            if(result != null ){
                $.each(result,function(i,item){
                    var re = "";
                    re = encodeURI("/jdtb/downLoadFileDcdb.do?id="+ item.id + "&ywtype=" + item.ywtype);
                    str+='<li><a href = '+re+'>' + item.filename + '</a></li>';
                })
                $("#uploadFiles").empty().html(str);
            }
        }
    })
}

function dzreadOnly(){
    var nodeId = $("#nodeidyc").val();
    if(nodeId != '0101' && nodeId !='8888'){
        $("#zxdbh").attr("disabled","disabled");
        $("#cbqx").attr("disabled","disabled");
        $("#swrq").attr("disabled","disabled");
        $("#psnr").attr("disabled","disabled")
        $("#nbyj").attr("disabled","disabled");
        $("#pslx").attr("disabled","disabled");
        $("#bllx").attr("disabled","disabled");
        $("#cbbm").attr("disabled","disabled");
        $("#lxr").attr("disabled","disabled");
        $("#lxdh").attr("disabled","disabled");
        $("#zyjclx").attr("disabled","disabled");
    }
    if(nodeId  =='8888'){
        $("#zxdbh").attr("disabled","disabled");
        $("#cbqx").attr("disabled","disabled");
        $("#swrq").attr("disabled","disabled");
        $("#pslx").attr("disabled","disabled");
        $("#bllx").attr("disabled","disabled");
        $("#cbbm").attr("disabled","disabled");
    }
}

function ybreadOnly(){
    $("input").attr("disabled","disabled");
    $("textarea").attr("disabled","disabled");
    $("button").attr("disabled","disabled");
    $("select").attr("disabled","disabled");
    $(".layui-btn").hide();
}


