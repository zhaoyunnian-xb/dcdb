<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.lang.Math" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="../header.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
        <div class="layui-card">
            <div class="layui-card-header">查询条件</div>
            <div class="layui-card-body">
                <div class="layui-form-item">
                    <div class="layui-form">
                        <div class="layui-row layui-col-space10">
                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">文号 ：</label>
                                    <div class="layui-input-block">
                                        <input name="wh" id="wh" type="text" class="layui-input"/>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3 ">
                                <div class="layui-form-item" id="psmcDiv">
                                    <label class="layui-form-label">督办件名称：</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="psjmc" id="psjmc" class="layui-input"/>
                                    </div>
                                </div>
                            </div>

                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">承办部门：</label>
                                    <div class="layui-input-block">
                                        <select name="cbbmmc" id = "cbbmmc"lay-verify="required" lay-search>
                                            <option value="">请选择</option>
                                        </select>
                                    </div>
                                </div>

                            </div>

                        </div>
                        <div class="layui-row">


                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">督办类型 ：</label>
                                    <div class="layui-input-block">
                                        <select name="lclx" id="lclx" lay-verify="required">
                                            <option value="lzps">领导批示</option>
                                            <option value="dzjb">党组决策</option>
                                            <option value="jbsx">交办事项</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-sm6 layui-col-md3">
                                <div class="layui-form-item" style="margin-left: 38px;text-align: right">
                                    <button class="layui-btn layui-btn-normal qryInfo">查询</button>
                                </div>
                            </div>



                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
        <div class="layui-card">
            <div class="layui-card-header">可退回列表</div>
            <div class="layui-card-body">
                <table class="layui-hide" id="jcjy-cellEdit" lay-filter="jcjy-cellEdit"></table>
            </div>
        </div>
    </div>
</div>

<div class="layui-card" id="divYB" hidden>
    <div class="layui-card-header">流程信息</div>
    <div class="layui-card-body">
        <table class="layui-hide" id="jcjy-divYB" lay-filter="jcjy-divYB"></table>
    </div>
</div>
<div class="layui-card" id="updateTable" hidden>
    <div class="layui-card-body">
        <div class="layui-tab layui-tab-brief" lay-filter="tabName">
            <ul class="layui-tab-title" id="tabTitle">
                <li class="layui-this">网站设置</li>
                <li>用户管理</li>
                <li>权限分配</li>
                <li>商品管理</li>
                <li>订单管理</li>
            </ul>
            <div class="layui-tab-content" id="tabContent">
                <div class="layui-tab-item layui-show">内容1</div>
                <div class="layui-tab-item">内容2</div>
                <div class="layui-tab-item">内容3</div>
                <div class="layui-tab-item">内容4</div>
                <div class="layui-tab-item">内容5</div>
            </div>
        </div>
    </div>
</div>

<div class="layui-card" id="scws" hidden>
    <div class="layui-card-body">
        <div class="layui-form" lay-filter="scwsf" >
            <input type="hidden" id="xsbh" >
            <table class="layui-table" lay-skin="line">
                <thead>
                <tr>
                    <th>节点名称</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td style="width:200px" >
                        <span class="layui-btn" onclick="qryTableInfo('JCJY_XSTRANSFER')">修改表信息</span>
                        <input type="checkbox" name="jd0011" title="线索移交节点">
                    </td>
                    <td style="width:100px"><i id="jd0011" class="layui-icon layui-icon-loading" style="font-size: 30px; color: #1aff2c;"></i> </td>
                </tr>
                <tr>
                    <td><span class="layui-btn" onclick="qryTableInfo('JCJY_CH@JCJY_JCGGZH')">修改表信息</span>
                        <input type="checkbox" name="jd101" title="初核节点"></td>
                    <td><i id="jd101" class="layui-icon layui-icon-loading" style="font-size: 30px; color: #1aff2c;"></i></td>
                </tr>
                <tr>
                    <td>
                        <span class="layui-btn" onclick="qryTableInfo('JCJY_LAJDDJ')">修改表信息</span>
                        <input type="checkbox" name="jd0201" title="立案节点"></td>
                    <td><i id="jd0201" class="layui-icon layui-icon-loading" style="font-size: 30px; color: #1aff2c;"></i></td>
                </tr>
                <tr>
                    <td>
                        <span class="layui-btn" onclick="qryTableInfo('JCJY_SCZJBG')">修改表信息</span>
                        <input type="checkbox" name="jd0401" title="审查终结报告节点"></td>
                    <td><i id="jd0401" class="layui-icon layui-icon-loading" style="font-size: 30px; color: #1aff2c;"></i></td>
                </tr>
                <tr>
                    <td>
                        <span class="layui-btn" onclick="qryTableInfo('JCJY_JCJYS_LZQFD')">修改表信息</span>
                        <input type="checkbox" name="jd0501" title="检察建议书节点"></td>
                    <td><i id="jd0501" class="layui-icon layui-icon-loading" style="font-size: 30px; color: #1aff2c;"></i></td>
                </tr>
                <tr>
                    <td>
                        <span class="layui-btn" onclick="qryTableInfo('JCJY_JAJDDJ')">修改表信息</span>
                        <input type="checkbox" name="jd0801" title="结案节点"></td>
                    <td><i id="jd0801" class="layui-icon layui-icon-loading" style="font-size: 30px; color: #1aff2c;"></i></td>
                </tr>
                <tr>
                    <td>
                        <span class="layui-btn" onclick="qryTableInfo('JCJY_XGPGB')">修改表信息</span>
                        <input type="checkbox" name="jd0701" title="效果评估节点"></td>
                    <td><i id="jd0701" class="layui-icon layui-icon-ok" style="font-size: 30px; color: #1aff2c;"></i></td>
                </tr>
                </tbody>`
            </table>
        </div>
    </div>
</div>
<script>
var path = '${ctx}';
$(function () {
  queryCbBm();
    var initReb = {
        init: function () {
            //this.initSelect();

            this.initTab();
            this.initClick();
            this.initQryTab();
            layui.form.render();
            layui.form.on('checkbox()', function(data){
                var checked = data.elem.checked;
                var name = data.elem.name;
                if(checked){
                    $('#'+name).removeClass('layui-icon-ok').addClass('layui-icon-loading');
                    var param = {
                        "xsbh":$('#xsbh').val(),
                        "nodeId":name
                    }
                    $.ajax({
                        async: false,
                        type: "post",
                        url: "/reback/scwsf.do",//注意路径 
                        data: param,
                        dataType: "json",
                        success:function(data){
                            $('#'+name).removeClass('layui-icon-loading').addClass('layui-icon-ok');
                        }
                    })
                }else{
                    $('#'+name).removeClass('layui-icon-ok').removeClass('layui-icon-loading');
                }
            });
        },
        initClick: function () {
            $('.qryInfo').on('click', function () {
                initReb.initTab();
            })
        },
        initTab: function () {
          var psjmc = $("#psjmc").val();
          var swrq = $("#swrq").val();
          var cbbmmc = $("#cbbmmc").val();
          var cjrq = $("#cjrq").val();
          var pslx = $("#pslx").val();
          var psrq = $("#psrq").val();
          var bllx = $("#bllx").val();
          var zt = $("#zt").val();
          var lclx = $("#lclx").val();
          var wh = $("#wh").val();

            var url = path + '/reback/qryJcjyDb.do';
            var table = layui.table;
            table.render({
              elem: '#jcjy-cellEdit',
              url: path + '/reback/qryJcjyDb.do',
              page: true, //开启分页
              where:{"psjmc":psjmc,"swrq":swrq,"cbbmmc":cbbmmc,"cjrq":cjrq,"pslx":pslx,"psrq":psrq,"bllx":bllx,"zt":zt,"lclx":lclx,"wh":wh},
              even: true,
              limit: 10,
              parseData: function(res) {
                var data = res.data;
                $.each(data, function (index,item) {
                  if (item.cbbmmc) {
                    item.cbbmmc = item.cbbmmc.replace(/,/g, '<br>')
                  }
                });
                return {
                  code: res.code,
                  msg: res.msg,
                  count: res.count,
                  data: data
                }
              },
              cols: [[
                {field: 'zizeng', align: 'center', width: 60, title: '序号', templet: '#zizeng'},
                {field: 'yjqk', align: 'center', title: '预警状态', width: 80,templet:function (d){
                    var html="";
                    if(d.yjqk =='1'){
                      html+='<img src="${ctx}/common/images/top/liudeng.png" ></img>';
                    }else if(d.yjqk =='2'){
                      html+='<img src="${ctx}/common/images/top/huangdeng.png" ></img>';
                    }else if(d.yjqk =='3'){
                      html+='<img src="${ctx}/common/images/top/hongdeng.png" ></img>';
                    }
                    return html;
                  }},
                {field: 'wh', align: 'center', title: '文号', width: 170},
                {field: 'psjmc', align: 'center', title: '督办件名称',event:'psjmc',  minWidth: 180,
                  style:'color:blue;cursor:pointer'},
                {field: 'bllx', align: 'center', title: '办理类型', width: 120},
                {field: 'cbbmmc', align: 'center', title: '承办部门', width: 120},
                {field: 'cbbmid', align: 'center', title: '承办部门id',hide:"true", },
                {field: 'nodeId', align: 'center', title: '节点ID',width: 70,hide: true},
                {field: 'nodeName', align: 'center', title: '节点名称',width: 110},
                {field: 'userid', align: 'center', title: '用户ID', midwidth: 100,hide: true},
                {field: 'username', align: 'center', title: '当前操作人姓名', width: 120},
                {field: 'id', align: 'center', title: '主键', hide: true},
              ]]
            })

            //监听单元格编辑
            table.on('edit(jcjy-cellEdit)', function (obj) {
                var value = obj.value //得到修改后的值
                        , data = obj.data //得到所在行所有键值
                        , field = obj.field; //得到字段
                var xsbh = data.XSBH;
                $('#'+xsbh).removeClass('layui-hide');
                $('#'+xsbh).attr('data-'+field,value);

            });

          //监听待办列表事件
            table.on('tool(jcjy-cellEdit)', function (obj) {
              var data = obj.data;
              if (obj.event === 'psjmc') {
                initTabYB(data.id,data.preid);
                layer.open({
                  type : 1,
                  skin : 'layui-layer-rim',
                  area : ['80%','60%'],
                  content : $("#divYB"),
                  closeBtn:1,
                  cancel:function () {
                    $("#divYB").hide();
                  }
                })
              }
            });

        },
        initSelect: function () {
            layui.config({
                base: '${ctx}/static/plugin/layui-exts/'
            }).extend({
                treeSelect: 'treeSelect/treeselect' //主入口模块
            })
            layui.use(['treeSelect','form'], function () {
                var treeSelect = layui.treeSelect
                treeSelect.render({
                    elem: "#org",
                    data: '${ctx}/corp/getCorpTree.do',//可以是treedata，也可以是 获取treedata的URL地址
                    method: "get"
                });
            });
        },
        initQryTab : function(){
            $.ajax({
                type: "post",
                url: "/reback/qryTableInfo.do",//注意路径 
                data: {},
                dataType: "json",
                success:function(data){
                    $('#tabTitle').html('');
                    $('#tabContent').html('');
                    var li = '';
                    var tab = ''
                    var ii = 0;
                    $.each(data,function(i,item){
                        if(ii == 0){
                            li +='<li class="layui-this" lay-id="'+i+'">'+i+'</li>'
                            tab += '<div class="layui-tab-item layui-show">'
                        }else{
                            li +='<li lay-id="'+i+'">'+i+'</li>'
                            tab += '<div class="layui-tab-item">'
                        }


                        tab += '<table class="layui-table" lay-data="{height:315,width:6400, page:true, id:\''+i+'\'}" lay-filter="'+i+'">'
                        tab += '<thead>'
                        tab += '<tr>'
                        $.each(item,function(i,item){
                            if(item.COLUMN_NAME == "XSBH"){
                                tab += '<th lay-data="{field:\''+item.COLUMN_NAME+'\', width:180, sort: true}">\''+item.COMMENTS || item.COLUMN_NAME +'\'</th>'
                            }else{
                                tab += '<th lay-data="{field:\''+item.COLUMN_NAME+'\', width:180, sort: true,edit:\'text\'}">\''+item.COMMENTS || item.COLUMN_NAME +'\'</th>'
                            }
                        })
                        tab += '</tr>'
                        tab += '</thead>'
                        tab += '</table>'
                        tab += '</div>'
                        ii ++;
                    })
                    $('#tabTitle').html(li);
                    $('#tabContent').html(tab);

                    layui.use('element', function(){
                        var element = layui.element;
                        //监听Tab切换，以改变地址hash值
                        element.on('tab(tabName)', function(){
                            var layid = this.getAttribute('lay-id');
                            var table = layui.table;
                            //转换静态表格
                            table.init(layid, {
                                height: 315 //设置高度
                                ,limit: 10 //注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
                                ,url:'/reback/qryTableInfo1.do?xsbh='+$('#xsbh').val()+'&tabName='+layid//支持所有基础参数
                            });
                            //监听单元格编辑
                            table.on('edit('+layid+')', function (obj) {
                                var value = obj.value //得到修改后的值
                                        , data = obj.data //得到所在行所有键值
                                        , field = obj.field; //得到字段
                                var xsbh = data.XSBH;
                                var param = {
                                    'tabName' : layid,
                                    'setCoulmn' : field,
                                    'setValue' : value,
                                    'whereKey' :'xsbh',
                                    'whereValue' : xsbh
                                }
                                $.ajax({
                                    type: "post",
                                    url: "/reback/upateTableInfo.do",//注意路径 
                                    data: param,
                                    dataType: "json",
                                    success: function (data) {

                                    }
                                });

                            });
                        });

                    });
                }
            })
        }
    }
    initReb.init();
})
function toView(xsbh){
    initTabYB(xsbh);
    layer.open({
        type : 1,
        skin : 'layui-layer-rim',
        area : ['80%','60%'],
        content : $("#divYB"),
        closeBtn:1,
        cancel:function () {
            $("#divYB").hide();
        }
    })
}
function initTabYB(id,preid) {
        var url = path + '/reback/qryJcjyYb.do';
        var table = layui.table;
        table.render({
            elem: '#jcjy-divYB'
            , url: url
            , where: {'id':id,'preid':preid}
            , cols: [[
                {field: 'id', title: 'ID',minWidth: 130,hide:true},
                {field: 'nodeId', align: 'center', title: '节点ID',width:100,hide:true},
                {field: 'nodeName', align: 'center', title: '节点名称'},
                {field: 'userid', align: 'center', title: '用户ID', minWidth: 170,hide:true},
                {field: 'username', align: 'center', title: '用户姓名'},
                {
                    field: '', title: '操作',templet: function (data) {
                        return '<div><a href="javaScript:void(0)" onclick="rebackNode(this)" data-id="'+data.id+'" data-nodeid="'+data.nodeId+'" data-nodename="'+ data.nodeName+'" data-userid="'+data.userid+'"  data-username="'+data.username+'"  class="layui-table-link">退回到该节点</a></div>'
                    }
                }
            ]],
            loading:true,
            page: {
                limit: 10,
                layout:['prev', 'page', 'next','skip','count'],
            },
        })

        //监听单元格编辑
        table.on('edit(jcjy-divYB)', function (obj) {
            var value = obj.value //得到修改后的值
                    , data = obj.data //得到所在行所有键值
                    , field = obj.field; //得到字段
            layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value, {
                offset: '15px'
            });
        });
    }

function rebackNode(obj) {
  layer.confirm("确定要退到该节点么？", {btn: ['确定', '取消']}, function (index){
    toJumpNode(obj)
  });
}

function toJumpNode(obj){
    var param = {}
    param['id'] = $(obj).data('id');
    param['nodeId'] = $(obj).data('nodeid') || '';
    param['nodeName'] = $(obj).data('nodename') || '';
    param['userid'] = $(obj).data('userid') || '';
    param['username'] = $(obj).data('username') || '';
    param['zt'] = '1';
    $.ajax({
        async: false,
        type: "post",
        url: "/reback/rebNode.do",//注意路径 
        data: param,
        dataType: "json",
        success:function(data){
            layer.closeAll();
            layer.msg('操作成功',{icon:1,time:1000,offset: '15px'},function(){
                $('#divYB').hide();
                $('.qryInfo').click();
            });
            $('#divYB').hide();
        }
    })
}
function rebackNodeId(xsbh){
    var param = $('#'+xsbh).data();
    param['xsbh'] = xsbh;
    param['nodeId'] = param.nodeid || '';
    param['nodeName'] = param.nodename || '';
    param['userId'] = param.userid || '';
    param['userName'] = param.username || '';
    $.ajax({
        async: false,
        type: "post",
        url: "/reback/rebNode.do",//注意路径 
        data: param,
        dataType: "json",
        success:function(data){
            layer.closeAll();
            layer.msg('操作成功',{icon:1,time:1000,offset: '15px'},function(){
                $('.qryInfo').click();
            });
        }
    })
}
function qryWs(data){
    initWs();
    $('#xsbh').val(data)
    layer.open({
        title:'生成文书',
        type : 1,
        skin : 'layui-layer-rim',
        area : ['40%','80%'],
        content : $("#scws"),
        closeBtn:1,
        cancel:function () {
            $("#scws").hide();
        }
    })
}
function initWs(){
    layui.form.val("scwsf", {
        "jd0011": false,
        "jd101": false,
        "jd0201": false,
        "jd0401": false,
        "jd0501": false,
        "jd0801": false,
        "jd0701": false
    });
    $('#jd0011').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
    $('#jd101').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
    $('#jd0201').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
    $('#jd0401').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
    $('#jd0501').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
    $('#jd0801').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
    $('#jd0701').removeClass('layui-icon-ok').removeClass('layui-icon-loading');
}
function qryTableInfo(tab){
    var table = layui.table;
    //转换静态表格
    table.init("JCJY_JCGGZH", {
        height: 315 //设置高度
        ,limit: 10 //注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
        ,url:'/reback/qryTableInfo1.do?xsbh='+$('#xsbh').val()+'&tabName=JCJY_JCGGZH'//支持所有基础参数
    });
    layer.open({
        title:'修改表信息',
        type : 1,
        skin : 'layui-layer-rim',
        area : ['80%','80%'],
        content : $("#updateTable"),
        closeBtn:1,
        cancel:function () {
            $("#updateTable").hide();
        }
    })
}
var winIndex = null;
function gotoSys(userid){
    if(winIndex != null){
        winIndex.close();
    }
    $.ajax({
        async: false,
        type: "post",
        url: "/reback/gotoSys.do?id="+userid,//注意路径 
        data: {},
        dataType: "json",
        success: function (data) {
            winIndex = window.open("${ctx}/index.do");
        }
    })

}
//查询部门下拉列表
function queryCbBm() {
  $.ajax({
    url: '/reback/queryCbBm.do',
    type: "post",
    async: false,
    dataType: "json",
    success: function (data) {
      $("#cbbmmc").empty();
      $("#cbbmmc").append("<option value="+''+">"+'请选择'+"</option>");
      for (var i = 0; i < data.data.length; i++) {
        $("#cbbmmc").append("<option value=" + data.data[i].NAME + ">" + data.data[i].NAME + "</option>");
      }
    },
  });
}
</script>
<jsp:include page="../footer.jsp"></jsp:include>