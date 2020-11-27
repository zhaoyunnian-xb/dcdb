var table = layui.table;
var upload = layui.upload;
var form = layui.form;

$(function () {
    //监听单元格事件，填写某一季度完成情况
    layui.use('table', function () {
        table.on('tool(demoEvent)', function (obj) {
            var status = $("#status").val();
            var jd = To_SimplifiedChinese(status.substring(0, 1));
            var data = obj.data;
            if (obj.event === 'setWcqk') {
                var bmlx = data.bmlx;
                if (bmlx == 'zz' || bmlx == 'qt') {
                    if (status.substring(2, 3) == '9') {
                        layer.alert("该季度流程已流转完，不允许编辑！");
                        return false;
                    }
                    layer.prompt({
                        area: ['800px', '350px'], //自定义文本域宽高
                        offset: '10%',
                        formType: 2,
                        maxlength: 500,
                        title: '填写第' + jd + '季度完成情况（内容限制500字）',
                        value: data.yjdwcqk
                    }, function (value, index) {
                        layer.close(index);
                        //这里一般是发送修改的Ajax请求
                        var param = {
                            url: '/jdtb/updateJdwcqk.do',
                            data: {'id': data.id, 'jdwcqk': value, 'status': $("#status").val(), 'isBgs': 'updateWcqk'},
                            success: function (data) {
                                if (data.code != '1') {
                                    layer.msg(data.msg, {icon: data.code});
                                } else {
                                    //上传完毕回调, 执行重载
                                    table.reload('testReload', {
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        }

                                    });
                                }
                            },
                        };
                        ajax(param);
                        //同步更新表格和缓存对应的值
                        /*obj.update({
                            yjdwcqk: value
                        });*/
                    });

                } else {
                    layer.alert("对于该任务来说，您所在部门属于配合部门，不允许编辑！");
                }
            }
        });
    });

    //监听radio操作，效能评估
    form.on('radio(xgpg)', function (obj) {
        //  console.log($(this).data('id'));
        //   layer.tips(this.value + ' ' + this.name + '：' + obj.elem.checked, obj.othis);
        var bmlx = $(this).data('bmlx');
        if (bmlx == "ph") {
            layer.msg("对于该任务来说，您所选择部门属于配合部门，不允许打分！", {icon: 1});
            return false;
        }
        var param = {
            url: '/jdtb/updateJdwcqk.do',
            data: {'id': $(this).data('id'), 'jdwcqk': this.value, 'status': $("#status").val(), 'isBgs': 'updateXnpg'},
            success: function (data) {
                if (data.code != '1') {
                    layer.msg(data.msg, {icon: data.code});
                }
            },
        };
        ajax(param);

    });


})

function loadTableList(ndid, status, cbbmid, hid_upload, hid_fj, hid_xnpg) {
    var jd = To_SimplifiedChinese(status.substring(0, 1));
    var ssbm = $("#ssbm").val();
    var tableH = ($('body').height())*0.8;
    // table render
    table.render({
        id: 'testReload',
        elem: '#demo'
        , url: '/jdtb/queryBmJdmb.do' //数据接口
        , where: {'ndid': ndid, 'status': status, 'cbbmid': cbbmid, 'ssbm': ssbm}
        , page: true //开启分页
        , height: tableH
        , cols: [[ //表头
            {field: 'ydnrmc', title: '要点内容', width: 200},
             {field: 'zrld', title: '责任领导', width: 80, align:'center'},
             {field: 'zyrwmc', title: '主要任务',minWidth: 200},
            {field: 'yjdmb', title: jd + '季度目标', minWidth: 200},
            {field: 'yjdwcqk', title: jd + '季度完成情况(点击表格填写)', event: 'setWcqk', minWidth:300,style: 'cursor: pointer;'},
            {field: 'ssbm', title: '牵头部门', width: 100, align:'center'},
             {field: 'phbm', title: '配合部门', width: 100, align:'center'},
             {
                field: 'classify', hide: hid_upload, width: 100, align:'center', title: '上传附件',
                 templet: function (d) {
                    return '<div><button class="layui-btn layui-btn-normal layui-btn-sm upload-btn" \n' +
                        '                    id="test1" lay-event="upload" data-id="' + d.id + '" data-bmlx="' + d.bmlx + '"  lay-data="{data:{bmrwid:\'' +d.id+'\', ywtype: '+status.substring(0, 1)+' }}">上传文件</button></div>'
                }
            },
            {
                field: 'fj', title: '附件', hide: hid_fj,minWidth:200, templet: function (d) {
                    var html = '<ul>';
                    for (var i = 0; i < d.file.length; i++) {
                        html += '<li><a href="/jdtb/downLoadFileDcdb.do?id=' + d.file[i].id + '&ywtype=' + status.substring(0, 1) + '">' + d.file[i].filename + '</a></li>';
                    }
                    html += '</ul>';
                    return html;
                }
            },
            {field: 'yjdpg', title: '效能评估', hide: hid_xnpg, width:160, templet: '#switchTpl'}

        ]],
        parseData: function(res) {
            var data = res.data;
            $.each(data, function (index,item) {
                if (item.phbm) {
                    item.phbm = item.phbm.replace(/;/g, '<br>')
                }
                if (item.ssbm) {
                    item.ssbm = item.ssbm.replace(/;/g, '<br>')
                }
                if (item.zrld) {
                    item.zrld = item.zrld.replace(/,/g, '<br>')
                }
            });
            return {
                code: res.code,
                msg: res.msg,
                count: res.count,
                data: data
            }

        },
        done: function (res, curr, count) {
            var tableElem = this.elem.next('.layui-table-view');
            var uploadInst = upload.render({
                elem: '.upload-btn',
                url: '/jdtb/filesUploadDcdb.do', //上传接口
                data: {
                    // bmrwid: function () {
                  //                     //     debugger;
                  //                     //   // var id = uploadInst.config.item.data('id');
                  //                     //   var id = $('#test1').data('id');
                  //                     //     return id;
                  //                     // },
                  //   ywtype: function () {
                  //       var ywtype = status.substring(0, 1);
                  //       return ywtype;
                  //   }
                },
                accept: 'file',
                multiple: true,
                before: function () {
                    /*var bmlx = uploadInst.config.item.data('bmlx');
                   if(bmlx=="ph"){
                       console.log(bmlx);
                       layer.msg("对于该任务来说，您所在部门属于配合部门，不允许上传证明材料！", {icon: 1});
                       return false;
                   }*/
                },
                allDone: function (res) {
                    //上传完毕回调, 执行重载
                    table.reload('testReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }

                    });
                },
                error: function () {

                }
            })

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

//查询意见
function queryPsyj(ndid, status, cbbmid) {
  var ssbm=$("#ssbm").val();
    var param = {
        url: '/jdtb/queryPsyj.do',
        data: {'ndid': ndid, 'status': status, 'cbbmid': cbbmid, 'ssbm': ssbm},
        success: function (data) {
            if (data.code != '1') {
                layer.msg(data.msg, {icon: data.code});
            }
            $("#bmfzryj").val(data.data.bmfzryj);
            $("#bgsyj").val(data.data.bgsyj);
            $("#bgsfzryj").val(data.data.bgsfzryj);

            $("#bmfzrqm").val(data.data.bmfzrqm);
            $("#bgsqm").val(data.data.bgsqm);
            $("#bgsfzrqm").val(data.data.bgsfzrqm);

            $("#bmfzrrq").val(data.data.bmfzrrq);
            $("#bgsrq").val(data.data.bgsrq);
            $("#bgsfzrrq").val(data.data.bgsfzrrq);


        },
    };
    ajax(param);
}

//提交
function submit(flag, nextuserid) {
  var status = $("#status").val();
  var nodeid = status.substring(2, 3);
  var param = {
        url: '/jdtb/updateYjAndFollow.do',
        data: {
            'ndid': $("#ndid").val(),
            'status': $("#status").val(),
            'cbbmid': $("#cbbmid").val(),
            'bmfzryj': $("#bmfzryj").val(),
            'bgsyj': $("#bgsyj").val(),
            'bgsfzryj': $("#bgsfzryj").val(),
            'flag': flag,
            'nextuserid': nextuserid,
            'bmfzrqm': $("#bmfzrqm").val(),
            'bmfzrrq': $("#bmfzrrq").val(),
            'bgsqm': $("#bgsqm").val(),
            'bgsrq': $("#bgsrq").val(),
            'bgsfzrqm':$("#bgsfzrqm").val() ,
            'bgsfzrrq':$("#bgsfzrrq").val(),
            'ssbm':$("#ssbm").val()
        },
        success: function (data) {
            layer.msg(data.msg, {icon: data.code,time:1000,offset:'250px'}, function () {
              //  if (data.code == '1' && flag != 'save') {
                if (data.code == '1') {
                    //跳转待办页
                  if(nodeid =='7' || nodeid=='8'){
                    window.location.href = "../../../pages/ndgzrw/bmrwlist_bgs.jsp";
                  }else{
                    window.location.href = "../../../pages/ndgzrw/bmrwlist.jsp";
                  }
                }

            });

        },
    };
    ajax(param);
}

//校验该部门的任务，是否都是配合任务
function queryIsAllPh() {
    var param = {
        url: '/jdtb/queryIsAllPh.do',
        data: {'ndid': '<%=ndid%>', 'status': '<%=status%>', 'cbbmid': '<%=cbbmid%>'},
        success: function (data) {
            if (data.code != '1') {
                layer.msg(data.msg, {icon: data.code});
            }

        },
    };
    ajax(param);
}

//查询部门下拉列表
function queryBmList(ndid) {
    var param = {
        url: '/jdtb/queryBmList.do',
        data: {'ndid': ndid},
        success: function (data) {
            if (data.code != '1') {
                layer.msg(data.msg, {icon: data.code});
            } else {
                $("#bmList").empty();
                for (var i = 0; i < data.data.length; i++) {
                    $("#bmList").append("<option value=" + data.data[i].CBBMID + ">" + data.data[i].SSBM + "</option>");
                }
                form.render();
            }

        },
    };
    ajax(param);
}

//提交、审核、退回
function send(flag) {
    var status = $("#status").val();
    var nodeid = status.substring(2, 3);
    if (flag == 'submit' && (nodeid != "8")) {
      var spbm='normal';
      if(nodeid == '6'){
        spbm = 'dcs';
      }
      var layer = layui.layer;
      layer.open({
        type: 2,
        title: '请选择发送人',
        content: '/pages/dcdblc/transforEle.jsp?isMul=false&flag='+spbm,
        area: ['60%', '80%'],
        offset:['30px'],
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
            str += depid + "@" + depname+"@"+userid+"@"+username+";";
            str2 += username;
          })

          if (userid == '') {
            layer.alert("请选择下一级审批人！");
            layer.close(index);
            return;
          }
          submit(flag, userid); //提交逻辑
          layer.close(index);


        }
      })
    } else {
        submit(flag, ""); //提交逻辑
    }


}
//初始化签名和时间
function initQmSj() {
    var laydate = layui.laydate;
    var status = $("#status").val();
    var nodeid = status.substring(2, 3);
    if(nodeid == '6'){
        laydate.render({
            elem: '#bmfzrrq',
            format: 'yyyy年MM月dd日',
            value: $("#bmfzrrq").val() == "" ? new Date() : $("#bmfzrrq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#bmfzrrq").val(value);
            }
        });
      $("#bmfzrqm").val(  $("#bmfzrqm").val() == $("#name").val() ?$("#bmfzrqm").val() : "");
        $("#bmfzrqm").val(  $("#bmfzrqm").val() == "" ?$("#name").val() : $("#bmfzrqm").val());
    }else if(nodeid == '7'){
        laydate.render({
            elem: '#bgsrq',
            format: 'yyyy年MM月dd日',
            value: $("#bgsrq").val() == "" ? new Date() : $("#bgsrq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#bgsrq").val(value);
            }
        });
      $("#bgsqm").val(  $("#bgsqm").val() == $("#name").val() ?$("#bgsqm").val() : "");
        $("#bgsqm").val(  $("#bgsqm").val() == "" ?$("#name").val() : $("#bgsqm").val());
    }else if(nodeid == '8'){
        laydate.render({
            elem: '#bgsfzrrq',
            format: 'yyyy年MM月dd日',
            value: $("#bgsfzrrq").val() == "" ? new Date() : $("#bgsfzrrq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#bgsfzrrq").val(value);
            }
        });
      $("#bgsfzrqm").val(  $("#bgsfzrqm").val() == $("#name").val() ?$("#bgsfzrqm").val() : "");
        $("#bgsfzrqm").val(  $("#bgsfzrqm").val() == "" ?$("#name").val() : $("#bgsfzrqm").val());
    }

}

//提交时检验完成情况是否填写
function volidateJdtb(flag) {
  var ndid=$("#ndid").val();
  var status=$("#status").val();
  var cbbmid=$("#cbbmid").val();
  var jd = To_SimplifiedChinese(status.substring(0, 1));
  var param = {
    url: '/jdtb/volidateJdtb.do',
    data: {'ndid': ndid, 'status': status, 'cbbmid': cbbmid},
    success: function (data) {
      if (data.code != 1) {
        layer.msg("您"+ jd+"季度完成情况没有全部填写完，请填写后再提交！", {icon: data.code,offset:'250px'});
      } else {
         send(flag);
      }
    },
  };
  ajax(param);
}