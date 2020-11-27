$(function () {
  var isReadOnly = $("#isReadOnly").val();
  if (isReadOnly == '1') {
    $("input").attr("disabled", "disabled");
    $("textarea").attr("disabled", "disabled");
    $("#czspan").hide();
    $("button").hide();
    $(".layui-btn").hide();
  }

})
var table = layui.table;
var code = "2";
var index1 = "";
var index2 = "";
var index3 = "";
/**
 * 发送ajax请求
 * */
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

//提交和退回(sftg 1:提交、0:退回)
function flow(sftg, userid, pl) {
  var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
  var wh = $("#whyc").val() == "" ? $("#lxh").val() : $("#whyc").val();
  var ywtype = $("#i").val() == "" ? $('#zyjclx option:selected').val() : $(
      "#bllxyc").val();
  var cbbmmc = $("#cbbmmcyc").val() == "" ? $("#cbbm").val() : $(
      "#cbbmmcyc").val();
  var lctype = $("#lclxyc").val();
  var cbbmid = $("#cbbmidyc").val();
  var nodeid = $("#nodeidyc").val();
  var nodename = $("#nodenameyc").val();
  var userid = userid;
  var username = "";
  var isBatch = pl == false ? "0" : "1";  //是否批量发送(0:否，1：是)

  var param = {
    url: '/flow/submitFlowZyjc.do',
    data: {
      'id': id,
      'wh': wh,
      'nodeId': nodeid,
      'nodeName': nodename,
      'ywtype': ywtype,
      'sftg': sftg,
      'cbbmid': cbbmid,
      'cbbmmc': cbbmmc,
      'userid': userid,
      'username': username,
      'lctype': lctype,
      'isBatch': isBatch
    },
    success: function (data) {
      layer.msg(data.msg, {icon: data.code, time: 1000}, function () {
        if (data.code == '1') {
          var url = "/db/index.do?lclx=lzps";
          if (lctype == 'dzjb') {
            url = "/db/index.do?lclx=dzjb";
          } else if (lctype == 'zyjc') {
            url = "/db/index.do?lclx=zyjc";
          }
          //跳转待办页
          window.location.href = url;

        }
      });

    }
  };
  ajax(param);

}

function reback() {
  var nodeid = $("#nodeidyc").val();
  if (nodeid == "0107") {
    var checkStatus = table.checkStatus('idTest')
        , data = checkStatus.data;
    if (data.length == 0) {
      layer.msg("请选择要退回的督查事项", {icon: 6, time: 2000});
      return;
    }
  }
  layer.confirm("确定要退回么？", {btn: ['确定', '取消']}, function (index) {
    if (nodeid == "0107") {
      submitSjjcCb("0", "");
    } else {
      flow("0", "", false);//退回流程
    }
  });
}

//选择下一节审批人
function selectPerson(sftg) {
  var isBatch = false;
  var flag = 'normal';
  var nodeid = $("#nodeidyc").val();
  //  var ywtype = $("#bllxyc").val() == "" ? $('#zyjclx option:selected').val() : $("#bllxyc").val();
  //当院领导审批->生成分工时
  if (nodeid == "0103") {
    flow(sftg, "", isBatch);
    return;
  }
  var layer = layui.layer;
  layer.open({
    type: 2,
    title: '请选择发送人',
    content: '/pages/dcdblc/transforEle.jsp?isMul=' + isBatch + '&flag=' + flag,
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
      var username = "";
      $.each(a, function (i, item) {
        userid = item.selfid;
        username = item.selfname;
        var depid = item.parentid;
        var depname = item.parentname;
        str += depid + "@" + depname + "@" + userid + "@" + username + ";";
        str2 += username;
      })

      if (userid == '') {
        layer.alert("请选择下一级审批人！");
        layer.close(index);
        return;
      }
      if (nodeid == "0106") {
        submitSjjcCb(sftg, str);
      } else {
        flow(sftg, str, isBatch);
      }
      layer.close(index);
    }
  })
}

//初始化按钮
function initBtn() {
  var nodeid = $("#nodeidyc").val();
  //  var lclxyc=$("#lclxyc").val();
  //  var ywtype = $("#bllxyc").val() == "" ? $('#zyjclx option:selected').val() : $("#bllxyc").val();
  $("#submitFlow").show();
  $("#rebackFlow").hide();
  if (nodeid == "0102") {
    $("#rebackFlow").show();
    $("#submitFlow").html("审核通过");
  } else if (nodeid == "0103") {
    $("#rebackFlow").show();
    $("#submitFlow").html("审核通过");
  }

}

//初始化签名和日期
function initQmSj() {
  var laydate = layui.laydate;
  var nodeid = $("#nodeidyc").val();
  // var ywtype = $("#bllxyc").val() == "" ? $('#zyjclx option:selected').val() : $("#bllxyc").val();

  if (nodeid == '0102') {
    //办公室负责人审批-拟办送审
    laydate.render({
      elem: '#bgssprq',
      format: 'yyyy年MM月dd日',
      value: $("#bgssprq").val() == "" ? new Date() : $("#bgssprq").val(),
      isInitValue: true,
      done: function (value, date, endDate) {
        $("#bgssprq").val(value);
      }
    });
    $("#bgsfzrmc").val(
        $("#bgsfzrmc").val() == $("#curname").val() ? $("#bgsfzrmc").val()
            : "");
    $("#bgsfzrmc").val($("#bgsfzrmc").val() == "" ? $("#curname").val() : $(
        "#bgsfzrmc").val());
  } else if (nodeid == '0103') {
    //院领导审批
    laydate.render({
      elem: '#yldsprq',
      format: 'yyyy年MM月dd日',
      value: $("#yldsprq").val() == "" ? new Date() : $("#yldsprq").val(),
      isInitValue: true,
      done: function (value, date, endDate) {
        $("#yldsprq").val(value);
      }
    });
    $("#yldfzrmc").val(
        $("#yldfzrmc").val() == $("#curname").val() ? $("#yldfzrmc").val()
            : "");
    $("#yldfzrmc").val($("#yldfzrmc").val() == "" ? $("#curname").val() : $(
        "#yldfzrmc").val());

  }
}

//审批意见的隐藏和不可编辑
function spyjHideAndDis() {
  var nodeid = $("#nodeidyc").val();
  //  var lclxyc = $("#lclxyc").val();
  // var ywtype = $("#bllxyc").val() == "" ? $('#zyjclx option:selected').val() : $("#bllxyc").val();
  if (nodeid == '0101') {
    $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
  } else if (nodeid == '0102') {
    //办公室负责人审批-拟办送审
    $(".layui-colla-item :eq(0)").siblings().find("input,textarea").attr(
        'disabled', 'true');
  } else if (nodeid == '0103') {

    $(".layui-colla-item :eq(1)").siblings().find("input,textarea").attr(
        'disabled', 'true');
  }
}

function ajaxQryWs() {
  var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
  var wh = $("#whyc").val() == "" ? $("#wh").val() : $("#whyc").val();
  var bllx = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $(
      "#bllxyc").val();
  $.ajax({
    url: '/flow/queryWsList.do',
    type: "post",
    async: true,
    data: {"id": id, "wh": wh, "bllx": bllx},
    dataType: "json",
    success: function (data) {
      var $wsList = $('#wslist');
      $wsList.html('');
      var list = data.data;
      var html = '<p></p>'
      $.each(list, function (i, item) {
        html += '<span class="attachment-download-text" style="color: blue" onclick="downLoadWS(this)" data-uuid="'
            + item.UUID + '">' + item.WJMC + '</span>'
        html += '<i class="layui-icon layui-icon-download-circle attachment-download-icon" onclick="downLoadWS(this)" data-uuid="'
            + item.UUID + '"></i>'
      })
      $wsList.html(html);
    }
  });
}

function downLoadWS(obj) {
  var $this = $(obj);
  var uuid = $this.data('uuid');
  var wh = $("#whyc").val() == "" ? $("#wh").val() : $("#whyc").val();
  $.download('/dcdb/downLoadWs.do', {'uuid': uuid, 'xsbh': wh});
}

//查询承办人，部门负责人-意见
function querySjjcSpyjCb(id) {
  $.ajax({
    url: "/sjjccb/querySjjcSpyj.do",
    type: "post",
    data: {"id": id},
    async: false,
    dataType: "json",
    success: function (data) {
      if (!data.data.id) {
        $("#addForm input[type='text']").each(function () {
          var id = $(this).attr('id');
          $("#" + id).val("");
        })
        $("#addForm textarea").each(function () {
          var id = $(this).attr('id');
          $("#" + id).val("");
        })
      } else {
        $.each(data.data, function (key, value) {
          //  $("#"+key).val(value);
          $('#addForm input[id="' + key + '"]').val(value);
          $('#addForm textarea[id="' + key + '"]').val(value);
        });
      }

      initSjjcCbrq();
    },
    error: function () {
      layer.closeAll();
      layer.msg("操作失败", {icon: 2});
    }
  });
}

//保存承办描述、审核意见等
function saveSjjcSpyjCb() {
  if ($("#cbms").val().trim() == "") {
    layer.msg("请填写承办描述", {icon: 6});
    var strObj = document.getElementById("cbms");
    strObj.value = "";
    strObj.focus();
    return;
  }
  var parms = {};
  $("#addForm input").each(function () {
    var id = $(this).attr('id');
    parms[id] = $('#' + id).val();
  })
  $("#addForm textarea").each(function () {
    var id = $(this).attr('id');
    parms[id] = $('#' + id).val();
  })
  $.ajax({
    url: "/sjjccb/saveSjjcSpyj.do",
    type: "post",
    data: parms,
    async: false,
    dataType: "json",
    success: function (data) {
      layer.msg(data.msg, {icon: data.code, time: 1500},function(){
          $('#addForm').hide();
          layer.closeAll();
        });
    },
    error: function () {
      layer.closeAll();
      layer.msg("操作失败", {icon: 2});
    }
  });
}

//承办页面，提交、退回
function submitSjjcCb(sftg, userid) {
  var checkStatus = table.checkStatus('idTest')
      , data = checkStatus.data;
  var ywtype = $("#bllxyc").val()
  var wh = $("#whyc").val();
  var cbbmmc = $("#cbbmmcyc").val();
  var lctype = $("#lclxyc").val();
  var cbbmid = $("#cbbmidyc").val();
  var nodeid = $("#nodeidyc").val();
  var nodename = $("#nodenameyc").val();
  var dbid = $("#idyc").val();
  $.ajax({
    url: "/sjjccb/submitSjjc.do",
    type: "post",
    data: {
      "data": JSON.stringify(data),
      "nodeid": $("#nodeidyc").val(),
      "userid": userid,
      'ywtype': ywtype,
      'sftg': sftg,
      'wh': wh,
      "dbid": dbid,
      'cbbmid': cbbmid,
      'cbbmmc': cbbmmc,
      'lctype': lctype,
      'nodeid': nodeid,
      'nodename': nodename
    },
    async: false,
    dataType: "json",
    success: function (data) {
      if (data.code == '1') {
        layer.msg(data.msg, {icon: data.code,time:2000},function () {
          window.location.href = "/db/index.do?lclx=zyjc";
        });

      } else {
       // layer.msg(data.msg, {icon: data.code});
        layer.alert(data.msg);
      }
    },
    error: function () {
      layer.msg("操作失败", {icon: 2});
      layer.closeAll();
    }
  });
}

//初始化，填报页面日期
function initSjjcCbrq() {
  $("#cbrq").attr("disabled", "disabled");
  $("#bmfzrsprq").attr("disabled", "disabled");
  $("#bmfzrmc").attr("disabled", "disabled");
  var nodeid = $("#nodeidyc").val();
  var laydate = layui.laydate;
  laydate.render({
    elem: '#cbrq', //指定元素
    format: 'yyyy年MM月dd日',
    value: $("#cbrq").val() == "" ? new Date() : $("#cbrq").val(),
    isInitValue: true
  });
  if (nodeid == '0106') {

    $(".cbBmfzr").find("input,textarea").attr('disabled', 'true');
  }
  if (nodeid == '0107') {
    laydate.render({
      elem: '#bmfzrsprq', //指定元素
      format: 'yyyy年MM月dd日',
      value: $("#bmfzrsprq").val() == "" ? new Date() : $("#bmfzrsprq").val(),
      isInitValue: true
    });
    $("#bmfzrmc").val(
        $("#bmfzrmc").val() == $("#curname").val() ? $("#bmfzrmc").val() : "");
    $("#bmfzrmc").val(
        $("#bmfzrmc").val() == "" ? $("#curname").val() : $("#bmfzrmc").val());
  }
}

//填报规则查看
function queryDcsxTbgz(id, dcsxid, dczqtype) {
  table.render({
    elem: '#dczqtable'
    , url: '/sjjc/queryDbrqTable.do' //数据接口
    , page: false //开启分页
    , where: {"dczqid": dcsxid, "dczq": dczqtype}
    , cols: [[ //表头
      {field: 'zizeng', align: 'center', title: '序号', templet: '#zizeng'}
      , {field: 'dczqtype', align: 'center', title: '督查周期'}
      , {
        field: 'dcrq', align: 'center', title: '填报指定日期', templet: function (d) {
          var dczqtype = d.dczqtype;
          var dcrq = d.dcrq;
          if (dczqtype == '每周') {
            dcrq = "每周" + To_SimplifiedChinese(dcrq);
          }
          return dcrq;
        }
      }
    ]]
  });

  index1 = layer.open({
    type: 1,
    title: '填报规则',
    area: ['700px', '500px'], //宽高
    offset: ['30px'],
    content: $('#dczqdiv'),
    cancel: function (index, layero) {
      layer.close(index);
      $('#dczqdiv').hide();
      return false;
    }

  });
}

//往期查看
function queryDcsxHistory(id, dcsxid, dczqtype) {
  table.render({
    elem: '#wqcktable'
    , url: '/sjjccb/queryCbrSpyjList.do' //数据接口
    , page: false //开启分页
    , where: {"id": id, "dczqtype": dczqtype, "dcsxid": dcsxid}
    , cols: [[ //表头
      {field: 'zizeng', align: 'center', title: '序号',width:'7%', templet: '#zizeng'}
      , {field: 'dcsxmc', align: 'center',width:'20%', title: '督查事项'}
      , {field: 'qtbmid', align: 'center', title: '牵头部门'}
      , {field: 'zzbmid', align: 'center', title: '主责部门'}
      , {field: 'dczqtype', align: 'center', title: '督查周期'}
      , {field: 'cbrq', align: 'center', title: '承办日期'}
      , {field: 'cbms', align: 'center', title: '承办描述'}
    ]]
  });

  index2 = layer.open({
    type: 1,
    title: '往期查看',
    area: ['1000px', '500px'], //宽高
    offset: ['30px'],
    content: $('#wqckqdiv'),
    cancel: function (index, layero) {
      layer.close(index);
      $('#wqckqdiv').hide();
      return false;
    }

  });
}

//阿拉伯数字转换为简写汉字
function To_SimplifiedChinese(Num) {
  var tmpnewchar;
  switch (Num) {
    case "0":
      tmpnewchar = "零";
      break;
    case "1":
      tmpnewchar = "一";
      break;
    case "2":
      tmpnewchar = "二";
      break;
    case "3":
      tmpnewchar = "三";
      break;
    case "4":
      tmpnewchar = "四";
      break;
    case "5":
      tmpnewchar = "五";
      break;
    case "6":
      tmpnewchar = "六";
      break;
    case "7":
      tmpnewchar = "七";
      break;
    case "8":
      tmpnewchar = "八";
      break;
    case "9":
      tmpnewchar = "九";
      break;
  }

  return tmpnewchar;
}

//上级事项承办页面-提交
function submitPre(sftg) {
  var checkStatus = table.checkStatus('idTest')
      , data = checkStatus.data;
  if (data.length == 0) {
    layer.msg("请选择至少选择一条要提交的督查事项", {icon: 6, time: 3000});
    return;
  }

  selectPerson(sftg);
}

//查询附件列表
function selectUploadFiles(id, ywtype) {
  $.ajax({
    url: "/jdtb/selectUploadFiles.do",
    type: "post",
    async: false,
    data: {"bmrwid": id, "ywtype": ywtype, "col": "bmrwid"},
    dataType: "json",
    success: function (result) {
      var str = "";
      if (result != null) {
        $.each(result, function (i, item) {
          var re = "";
          re = encodeURI("/jdtb/downLoadFileDcdb.do?id=" + item.id + "&ywtype="
              + item.ywtype);
          str += '<li><a href = ' + re + '>' + item.filename + '</a></li>';
        })
        $("#uploadFiles").empty().html(str);
      }
    }
  })
}

//填报页面弹出
function cbmsTb(id) {
 // $("#addForm").show();
  //查询承办描述、审批意见
  querySjjcSpyjCb(id);
  //查询附件
  selectUploadFiles( $("#id").val(),$("#cbrq").val());
  index3 = layer.open({
    type: 1,
    title: '填写承办情况',
    area: ['1000px', '500px'], //宽高
    offset: ['30px'],
    content: $('#addForm'),
    cancel: function (index, layero) {
      layer.close(index);
      $('#addForm').hide();
      return false;
    }

  });
}