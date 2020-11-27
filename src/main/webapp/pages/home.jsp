
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${ctx}/pages/header.jsp"></jsp:include>
<%
String name=request.getParameter("name");
%>
<style>
body{
    background-color: #F2F2F2;
}
.layui-card-header > .more{
    margin-top: 0;
    cursor: pointer;
}
    .todo-list .todo-item{
        padding: 5px 0 3px;
        border-bottom: 1px solid #F2F2F2;
        cursor: pointer;
        color: #104ffa;
    }
.todo-list .todo-item a{
    display: block;
    overflow: hidden;
}
.todo-list .todo-item .text{
    display: inline-block;
    width: 70%;
    font-size: 14px;
}
.todo-list .todo-item .text .iconfont{
    padding-right: 5px;
}
.todo-list .todo-item .text .icon-red{
    color: #d81e06;
}
.todo-list .todo-item .text .icon-green{
    color: #01d715;
}
.todo-list .todo-item .text .icon-yellow{
    color: #ffb301;
}
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">
                    年度工作任务督办
                    <a class="fr more" href="javascript:;" onclick="reIframe('/pages/ndgzrw/bmrwlist.jsp')">更多 <i class="layui-icon layui-icon-more"></i></a>
                </div>
                <div class="layui-card-body">
                    <ul class="todo-list" id="ndrwUl">
                    </ul>
                </div>
            </div>

        </div>
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">
                    上级重要决策督办
                    <a class="fr more"  href="javascript:;" onclick="reIframe('/db/index.do?lclx=zyjc')">更多 <i class="layui-icon layui-icon-more"></i></a>
                </div>
                <div class="layui-card-body">
                    <ul class="todo-list" id="zyjcUl">
                    </ul>
                </div>
            </div>
        </div>

    </div>
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">
                    领导批示督办
                    <a class="fr more" href="javascript:;" onclick="reIframe('/db/index.do?lclx=lzps')">更多 <i class="layui-icon layui-icon-more"></i></a>
                </div>
                <div class="layui-card-body">
                    <ul class="todo-list" id="ldpsUl">
                    </ul>
                </div>
            </div>

        </div>
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">
                    党组检办督办
                    <a class="fr more" href="javascript:;" onclick="reIframe('/db/index.do?lclx=dzjb')">更多 <i class="layui-icon layui-icon-more"></i></a>
                </div>
                <div class="layui-card-body">
                    <ul class="todo-list" id="dzjbUl">
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">
                    交办事项督办
                    <a class="fr more" href="javascript:;" onclick="reIframe('/db/index.do?lclx=jbsx')">更多 <i class="layui-icon layui-icon-more"></i></a>
                </div>
                <div class="layui-card-body">
                    <ul class="todo-list" id="jbsxUl">
                    </ul>
                </div>
            </div>

        </div>       
    </div>
</div>

</body>
<script>
  $(function () {
    var name='<%=name%>';
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
      "url":"${ctx}/menhu/queryDbIndex.do",
      "data":"",
      "success" :function(res){
        var ldpsdbHtml ='';
        var dzjbdbHtml ='';
        var zyjcdbHtml ='';
        var ndrwHtml ='';
        var jbsxHtml ='';//交办事项嵌入页面
        $.each(res.dataLdps, function (i, item) {
          var parm = JSON.stringify(item)
            ldpsdbHtml += '<li class="todo-item"  onclick="toPage('+JSON.stringify(item).replace(/"/g, "&quot;")+')">';
          if(typeof (item.yjqk) == 'undefined'){
            ldpsdbHtml+=' <span class="text layui-elip">'+item.psjmc+'</span>'
          }else{
            if(item.yjqk == '1'){
              ldpsdbHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-green"></i>'+item.psjmc+'</span>'
            }else if(item.yjqk == '2'){
              ldpsdbHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-yellow"></i>'+item.psjmc+'</span>'
            }else if(item.yjqk == '3'){
              ldpsdbHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-red"></i>'+item.psjmc+'</span>'
            }
          }
            ldpsdbHtml+='<span class="fr">'+item.lzsj+'</span>'
            ldpsdbHtml += '</li>';
        })
        $("#ldpsUl").empty().html(ldpsdbHtml);

        $.each(res.dataDzjb, function (i, item) {
          var parm = JSON.stringify(item)
          dzjbdbHtml += '<li class="todo-item"  onclick="toPage('+JSON.stringify(item).replace(/"/g, "&quot;")+')">';
          if(typeof (item.yjqk) == 'undefined'){
            dzjbdbHtml+=' <span class="text layui-elip">'+item.zxdbh+'</span>'
          }else{
            if(item.yjqk == '1'){
              dzjbdbHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-green"></i>'+item.zxdbh+'</span>'
            }else if(item.yjqk == '2'){
              dzjbdbHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-yellow"></i>'+item.zxdbh+'</span>'
            }else if(item.yjqk == '3'){
              dzjbdbHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-red"></i>'+item.zxdbh+'</span>'
            }
          }

          dzjbdbHtml+='<span class="fr">'+item.lzsj+'</span>'
          dzjbdbHtml += '</li>';
        })
        $("#dzjbUl").empty().html(dzjbdbHtml);

        $.each(res.dataSjjc, function (i, item) {
          var parm = JSON.stringify(item)
          zyjcdbHtml += '<li class="todo-item" onclick="toPage('+JSON.stringify(item).replace(/"/g, "&quot;")+')">';
          zyjcdbHtml+=' <span class="text layui-elip">'+item.lxmc+'</span>'
          zyjcdbHtml+='<span class="fr">'+item.lzsj+'</span>'
          zyjcdbHtml += '</li>';
        })
        $("#zyjcUl").empty().html(zyjcdbHtml);

        $.each(res.dataNdrw, function (i, item) {
          var parm = JSON.stringify(item)
       /*   if(name == '罗艳'|| name=='王小玉'){
            ndrwHtml += '<li class="todo-item" data-parm='+parm+' onclick="toPage(this)">';
            ndrwHtml+=' <span class="text layui-elip">'+item.ndrwmc+'（'+item.ssbm+'）</span>'
            ndrwHtml+='<span class="fr">'+item.czsj+'</span>'
            ndrwHtml += '</li>';
          }else{
            ndrwHtml += '<li class="todo-item" data-parm='+parm+' onclick="toPage(this)">';
            ndrwHtml+=' <span class="text layui-elip">'+item.ndrwmc+'</span>'
            ndrwHtml+='<span class="fr">'+item.czsj+'</span>'
            ndrwHtml += '</li>';
          }*/
          ndrwHtml += '<li class="todo-item" onclick="toPage('+JSON.stringify(item).replace(/"/g, "&quot;")+')">';
          ndrwHtml+=' <span class="text layui-elip">'+item.ndrwmc+'（'+item.ssbm+'）</span>'
          ndrwHtml+='<span class="fr">'+item.czsj+'</span>'
          ndrwHtml += '</li>';

        })
        $("#ndrwUl").empty().html(ndrwHtml);
        // 交办事项嵌入代办的页面数据
        $.each(res.dataJbsx, function (i, item) {
            var parm = JSON.stringify(item)
            jbsxHtml += '<li class="todo-item"  onclick="toPage('+JSON.stringify(item).replace(/"/g, "&quot;")+')">';
            if(typeof (item.yjqk) == 'undefined'){
            	jbsxHtml+=' <span class="text layui-elip">'+item.zxdbh+'</span>'
            }else{
              if(item.yjqk == '1'){
            	  jbsxHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-green"></i>'+item.zxdbh+'</span>'
              }else if(item.yjqk == '2'){
            	  jbsxHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-yellow"></i>'+item.zxdbh+'</span>'
              }else if(item.yjqk == '3'){
            	  jbsxHtml+=' <span class="text layui-elip"><i class="iconfont icon-alarm-warning-fill icon-red"></i>'+item.zxdbh+'</span>'
              }
            }

            jbsxHtml+='<span class="fr">'+item.lzsj+'</span>'
            jbsxHtml += '</li>';
          })
          $("#jbsxUl").empty().html(jbsxHtml);
      }
    };
    //查询督查事项下拉列表
    ajax(param);
  })

  function toPage(item) {
     var data= item;
     var nodeid=data.nodeId;
     var url="";
     if(typeof (nodeid) == 'undefined'){
        url='${ctx}/jdtb/toPage.do?ndid='+data.id+'&status='+data.czzt+'&cbbmid='+data.cbbmid+'&ssbm='+data.ssbm;
     }else{
       if(nodeid== '0101'){
         url = encodeURI('${ctx}/db/toxinjian.do?isReadOnly=0&id='+data.id+'&wh='+data.wh+'&bllx='+data.bllx
             +'&nodeid='+data.nodeId+'&nodename='+data.nodeName+'&cjr='+data.cjr+'&cbbmid='+data.cbbmid+'&cbbmmc='+data.cbbmmc+'&lclx='+data.lclx+'&isfa='+data.isfa);
       }else {
         url = encodeURI('${ctx}/db/toshenhe.do?isReadOnly=0&id='+data.id+'&wh='+data.wh+'&bllx='+data.bllx
             +'&nodeid='+data.nodeId+'&nodename='+data.nodeName+'&cjr='+data.cjr+'&cbbmid='+data.cbbmid+'&cbbmmc='+data.cbbmmc+'&lclx='+data.lclx+'&isfa='+data.isfa);
       }
     }
    reIframe(url);
    }

function reIframe(url) {
  var name='<%=name%>';
  if(url.indexOf("bmrwlist") != -1 && (name == '罗艳' || name == '王小玉')){
    url="/pages/ndgzrw/bmrwlist_bgs.jsp";
  }
  window.parent.iframe.src=url;
}
</script>
<jsp:include page="${ctx}/pages/footer.jsp"></jsp:include>