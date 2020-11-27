<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../header.jsp"></jsp:include>
<style>
    .layui-tab-title{
        position: absolute;
        top: 0;
        left: 0;
        width: 100px;
        border-bottom-width:0;
    }
    .layui-tab-title li{
        width: 100%;
        float: left;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-bottom: 10px;
    }
    .layui-tab-content {
        position: absolute;
        top: 0;
        left: 290px;
        width: 60%;
        border: 1px solid #cccc;
    }
    .layui-tab-brief>.layui-tab-title .layui-this {
        color: #fff;
        background: #5374c8;
    }
    .layui-tab-brief>.layui-tab-more li.layui-this:after, .layui-tab-brief>.layui-tab-title .layui-this:after{
        border-bottom: 0;
    }
    .export{
        position: absolute;
        right: 10px;
        top: 2px;
    }
    iframe{
        width: 100%;
        border: 0;
        height: 500px;
    }
    iframe body{
        margin: 20px;
    }

</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="yulan-wrapper">
                <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
                    <ul class="layui-tab-title" id="ws_ul">
                        <li class="layui-this">反馈单</li>
                        <li>党组督办单</li>
                        <li>检长办督办单</li>
                        <li>通知单(督办函)</li>
                    </ul>
                    <div class="layui-tab-content" id="wsnr_div">
                        <div class="layui-tab-item layui-show"><iframe src=""></iframe></div>
                        <div class="layui-tab-item">内容2</div>
                        <div class="layui-tab-item">内容3</div>
                        <div class="layui-tab-item">内容4</div>
                    </div>
                </div>
                <div class="layui-btn layui-btn-normal layui-btn-sm export">
                    <i class="layui-icon layui-icon-export"> 导出 </i></div>
            </div>
        </div>
    </div>
</div>

<script>
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

    $(function () {
        var layer = layui.layer;
        var parms = {
        url: '/flow/creatWs.do',
        data: {'id' : $.getUrlString()['id'] || '',
            'bllx' : $.getUrlString()['bllx'] || '',
            'wh' : $.getUrlString()['wh'] || '',
            'lclx' : $.getUrlString()['lclx'] || ''},
        success: function (data) {
                if (data.code == '1') {
                var ulHtml="";
                var nrHtml="";
                    $.each(data.data, function (i, item) {
                        var docPath=item.WJLJ+item.WJMC;
                        var htmlPath="${ctx}/"+item.WJLJ+item.WJMC.split(".")[0]+".html";
                        if(i==0){
                            ulHtml+="  <li class='layui-this' data-path='"+docPath+"'>"+item.WSBM+"</li>";
                            nrHtml+="<div class=\"layui-tab-item layui-show\"><iframe src='"+htmlPath+"' ></iframe></div>";
                        }else{
                            ulHtml+="  <li  data-path='"+docPath+"'>"+item.WSBM+"</li>";
                            nrHtml+="<div class=\"layui-tab-item \"><iframe src='"+htmlPath+"'></iframe></div>";
                        }

                    })
                    $("#ws_ul").empty().html(ulHtml);
                    $("#wsnr_div").empty().html(nrHtml);
                    $('#wsnr_div iframe').load(function () {
                        var iframe = $('#wsnr_div iframe');
                        $.each(iframe, function (i,n) {
                            var body = n.contentWindow.document.getElementsByClassName('b2');
                            $(body).css('margin', '20px');
                        })

                    })
                }else{
                    layer.msg(data.msg,{icon:data.code,offset:['250px'],time:1000});
                }

        } };
        ajax(parms)

        $(".layui-icon-export").click(function(){
            window.location.href =  encodeURI("${ctx}/dcdb/downloadwsByPath.do?wspath="+$(".layui-this").data("path"));
        });
    })
</script>

<jsp:include page="../footer.jsp"></jsp:include>
