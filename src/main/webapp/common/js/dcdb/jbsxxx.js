var form = layui.form;
var upload = layui.upload;
var element = layui.element;
$(function () {
    //查询下拉框配置数据
    selectPeizhiData();
    //查询党组检办信息
    queryJbxxDzjb();
    var isReadOnly = $("#isReadOnly").val();
    if(isReadOnly == '1'){
        ybreadOnly();
    }
  layui.use('laydate', function () {
    var laydate = layui.laydate;
    laydate.render({
      elem: '#psrq', //指定元素
      format: 'yyyy年MM月dd日',
      value: $("#psrq").val() == "" ? new Date() : $("#psrq").val(),
      isInitValue: true,
      done: function(value, date, endDate){
        $("#psrq").val(value);
      }
    });
    laydate.render({
      elem: '#swrq', //指定元素
      format: 'yyyy年MM月dd日',
      value: $("#swrq").val() == "" ? new Date() : $("#swrq").val(),
      isInitValue: true,
      done: function(value, date, endDate){
        $("#swrq").val(value);
      }
    });
  })


    //执行实例
    form.render();
    form.on('select(cbqx)', function (data) {
        debugger
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
            var id = $("#bllx").val();
            return id;
          },
          nodeId: function () {
            var nodeidyc = $("#nodeidyc").val();
            return nodeidyc;
          },
        },
        accept: 'file',
        multiple: false,
        before: function () {

        },
        done: function (res) {
          //上传完毕回调
          var id = $("#idyc").val();
          var ywtype = $("#bllx").val();
          var nodeId = $("#nodeidyc").val();
          selectUploadFiles(id,ywtype);
        },
        error: function () {
            //请求异常回调
        }
    });
})
function selectPeizhiData(){
    var type = "PSLX";
    $.ajax({
        url: "/lb/selectPeizhiData.do",
        type: "post",
        async: false,
        data: {"ptype": type},
        dataType: "json",
        success: function (result) {
          if(result.data.length > 0){
            $("#pslx").empty();
            $("#pslx").append("<option value="+''+">"+'请选择'+"</option>");
            for (var i = 0; i < result.data.length; i++) {
              $("#pslx").append("<option value=" + result.data[i].lbmc + ">" + result.data[i].lbmc + "</option>");
            }
          }

        }
    })
}
//回显
function queryJbxxDzjb() {
  var nodeId = $("#nodeidyc").val();
  var dzid = $("#idyc").val();
  var wh = $("#whyc").val();
  if(wh != null && wh != ''){
    $.ajax({
      url: "/dzjbxx/dzjbxxdetail.do",
      type: "post",
      async: false,
      data: {"wh": wh},
      dataType: "json",
      success: function (jbxx) {
        if (jbxx != null) {
          $("#zxdbh").val(jbxx.zxdbh == null ? "" : jbxx.zxdbh);//督办单号
          //党组--承办期限
          if (jbxx.cbqx != '20' && jbxx.cbqx != '30' && jbxx.cbqx != '60' && jbxx.cbqx != '' && jbxx.cbqx != null) {
            $("#cbqx").find("option[value = 50]").attr("selected","selected");
            $("#cbqxdz").val(jbxx.cbqx);
          } else {
            $("#cbqx").find("option[value = '"+jbxx.cbqx+"']").attr("selected","selected");
          }
          //党组--会议日期
          $("#swrq").val(jbxx.swrq == null ? "" : jbxx.swrq);
          //党组--决定事项
          $("#psnr").val(jbxx.psnr == null ? "" : jbxx.psnr);
          //党组--拟办意见
          $("#nbyj").val(jbxx.nbyj == null ? "" : jbxx.nbyj);
          //党组--任务类型
          $("#pslx").find("option[value = '"+jbxx.pslx+"']").attr("selected","selected");
          //党组--办理类型
          $("#bllx").find("option[value = '"+jbxx.bllx+"']").attr("selected","selected");
          //党组--承办部门
          var cbbmselect = 'dd[lay-value=' + jbxx.cbbm + ']';
          $("#cbbm").val(jbxx.cbbmmc == null ? "" : jbxx.cbbmmc);
          $("#cbbm").attr("cbbmid", jbxx.cbbm);
          //党组--办理情况暂不赋值
          //党组--联系人
          $("#lxr").val(jbxx.lxr == null ? "" : jbxx.lxr);
          //党组--联系电话
          $("#lxdh").val(jbxx.lxdh == null ? "" : jbxx.lxdh);
            //党组--审批人
            $("#spr").val(jbxx.spr == null ? "" : jbxx.spr);
            //党组--审批日期
            $("#sprq").val(jbxx.sprq == null ? "" : jbxx.sprq);
            //党组--审批意见
            $("#spyj").val(jbxx.spyj == null ? "" : jbxx.spyj);
            //党组--审批备注
            $("#spbz").val(jbxx.spbz == null ? "" : jbxx.spbz);
            //督查事项
            $("#dcsx").val(jbxx.dcsx == null ? "" : jbxx.dcsx);
            //督查缘由
            $("#dcyy").val(jbxx.dcyy == null ? "" : jbxx.dcyy);
          //党组--附件上传
          selectUploadFiles( $("#idyc").val(),$('#bllx').val());
          //回填只读
            dzreadOnly();
        }
      }
    })
  }
}
//保存
function save(type) {
    var dzId = $("#idyc").val();
    var wh = $("#whyc").val(); //党组--ID
    if(wh == null || wh == ''){
        wh = dzId;
    }
    var cbqx = $("#cbqx").val();//党组--承办期限
    if(cbqx == '50'){
        cbqx = $("#cbqxdz").val();
    }
    var swrq = $("#swrq").val();//党组--会议日期
    var psnr = $("#psnr").val();//党组--决定事项
    var nbyj = $("#nbyj").val();//党组--拟办意见
    var pslx = $("#pslx").val();//党组--任务类型
    var bllx = $("#bllx").val();//党组--办理类型
    var zxdbh = $("#zxdbh").val();//党组--督办单号
    var cbbm = $("#cbbm").attr("cbbmid");//党组--承办部门ID
    var blqk = $("#blqk").val();//党组--办理情况
    var lxr = $("#lxr").val();//党组--联系人
    var lxdh = $("#lxdh").val();//党组--联系电话
    var cbbmmc = $("#cbbm").val();//党组--承办部门名称
    var nodeId = $("#nodeidyc").val();//党组--节点流程ID
    if(nodeId == '' || nodeId == null){
        nodeId = "0101";
    }
    var nodeName = $("#nodenameyc").val();//党组--节点流程名称
    if(nodeName == '' || nodeName == null){
        nodeName = "党组新建";
    }
    var lclx = $("#lclxyc").val();//党组--流程类型
    var spr = $("#spr").val();//党组--办公室审批人
    var sprq = $("#sprq").val();//党组--办公室审批日期
    var spyj = $("#spyj").val();//党组--办公室审批意见
    var spbz = $("#spbz").val();//党组--办公室审批备注
    var dcsx = $("#dcsx").val();//督查事项
    var dcyy = $("#dcyy").val();//督查缘由
    if(type == 2){

        if(zxdbh == "" || zxdbh == null){
            layer.msg("督办单号不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(cbqx == "" || cbqx == null){
            layer.msg("承办期限不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(swrq == "" || swrq == null){
            layer.msg("收文日期不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(psnr == "" || psnr == null){
            layer.msg("决定事项不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(pslx == "" || pslx == null){
            layer.msg("任务类型不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(bllx == "" || bllx == null){
            layer.msg("办理类型不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(dcsx == "" || dcsx == null){
            layer.msg("督查事项不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
            return ;
        }
        if(dcyy == "" || dcyy == null){
        	layer.msg("督查缘由不能为空",{icon:0,offset: ['255px', '500px'],time:1000});
        	return ;
        }
    }
    var param = {};
    param.dzId = dzId;
    param.wh = wh;
    param.cbqx = cbqx;
    param.swrq = swrq;
    param.psnr = psnr;
    param.nbyj = nbyj;
    param.pslx = pslx;
    param.bllx = bllx;
    param.zxdbh = zxdbh;
    param.blqk = blqk;
    param.lxr = lxr;
    param.lxdh = lxdh;
    param.cbbmmc = cbbmmc;
    param.cbbm = cbbm;
    param.type = type;
    param.nodeId = nodeId;
    param.nodeName= nodeName;
    param.spr= spr;
    param.sprq= sprq;
    param.spyj= spyj;
    param.spbz= spbz;
    param.lclx= lclx;
    param.type= type;
    param.dcsx= dcsx;
    param.dcyy= dcyy;
    $.ajax({
        url: "/dzjbxx/dzjbxxsave.do",
        type: "post",
        async: false,
        data: param,
        dataType: "json",
        success: function (result) {
            if (type == 1) {
                saveSpyj(); //保存意见
                layer.msg(result.msg, {icon: result.code, offset: ['250px'], time: 1000});
            } else {
                if (result.code == 1) {
                    saveSpyj();//保存意见
                    if (type == 2) {
                        //提交流程
                        showStage();
                    } else if (type == 3) {
                        reback();//退回流程
                    }

                } else {
                    layer.msg(result.msg, {icon: result.code, time: 1000});
                }
            }
        }
    })

}
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
                    re = encodeURI("/jdtb/downLoadFileDcdb.do?id=" + item.id + "&ywtype=" + item.ywtype);
                    str+='<li><a href = '+re+'>' + item.filename + '</a></li>';
                   // str+='<li><a href="/jdtb/downLoadFileDcdb.do?id=' + item.id + '&ywtype=' + item.ywtype + '">' + item.filename + '</a></li>';
                })
                $("#uploadFiles").empty().html(str);
            }
        }
    })
}

function dzreadOnly(){
    var nodeId = $("#nodeidyc").val();
    if(!(nodeId == "0101" || nodeId == "01011"||nodeId =="0102")){
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
        $("#dcsx").attr("disabled","disabled");
        $("#dcyy").attr("disabled","disabled");
    }

    $("#bllx").attr("disabled", "disabled");

}

function ybreadOnly(){
    $("input").attr("disabled","disabled");
    $("textarea").attr("disabled","disabled");
    $("button").attr("disabled","disabled");
    $("select").attr("disabled","disabled");
}


