<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/4/1
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${ctx}/pages/header.jsp"></jsp:include>
<style>
    body {

    }

    div {
        margin: 2px;
    }

    .horizontal {
        height: 300px;
    }

    .horizontal .layui-timeline-item {
        width: 12%;
        float: left;
        height: 50px;
    }

    .horizontal .layui-timeline-item:before {
        content: '';
        position: absolute;
        left: 5px;
        top: 35px;
        z-index: 0;
        width: 100%;
        height: 1px;
    }

    .horizontal .layui-timeline-axis {
        top: 25px;
        color: #ccc;
    }

    .horizontal .active .layui-timeline-axis {
        background-color: #5FB878;
        background: url("../../static/common/img/icon-circle-green.png") no-repeat 0 0;
    }

    .horizontal .active.pitch .layui-timeline-axis {
        background-color: #1E9FFF;
        background: url("../../static/common/img/icon-circle-blue.png") no-repeat 0 0;
    }

    .horizontal .layui-timeline-axis:hover {
        color: #5FB878;
    }

    .horizontal .layui-timeline-content {
        padding-left: 0;
        position: absolute;
        top: 0px;
        left: -50%;
        width: 100%;
        text-align: center;
    }

    .layui-timeline-title {
        margin-bottom: 25px;
    }

    .area {
        padding: 15px 5%;
        height: 150px;

    }

    .main {
        padding: 5px;
        border: 1px solid #cccccc;
    }
    .layui-icon-self{
        background: url("../../static/common/img/icon-circle.png") no-repeat 0 0;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    流程监控
                </div>
                <div class="layui-card-body">
                    <div class="area">
                        <ul class="layui-timeline horizontal" id="flower">
                        </ul>
                    </div>
                    <div class="main">
                        <iframe style="width: 100%;height: 450px" frameborder="0" src="" id="flow_iframe"></iframe>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>



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
<input type="hidden" id="preid" value="${preid}">
</body>
<script>
    $(function () {
        var nodeId = "";
        var url = '${ctx}/flow/getFlower.do';
        var data = {"ywtype": "${bllx}", "id": "${id}", "lclx": "${lclx}","preid": "${preid}"};
        var success = function (d) {
            if (d.code = '202') {
                var list = d.list;
                var html = "";
                for (var i = 0; i < list.length; i++) {
                    if (list[i].istf != '0') {
                        html += "<li class='layui-timeline-item active' onclick=\"cutOver(this,'" + list[i].nodeId + "')\">";
                    } else {
                        html += "<li class='layui-timeline-item'>";
                    }
                    html +=
                         "<i class='layui-icon layui-timeline-axis layui-icon-self'></i>" +
                        "<div class='layui-timeline-content layui-text'>" +
                        "<h4 class='layui-timeline-title'>" + list[i].nodeName + "</h4>";
                    if (list[i].istf != '0') {
                        html += "<h5>" + list[i].approveTime + "</h5>";
                    }
                    if (list[i].istf != '0') {
                        html += "<h5>" + list[i].userName + "</h5>";
                    }
                    "</div>" +
                    "</li>";
                }
                $("#flower").html(html);
                $(".active:last").addClass("pitch");
                $(".active:last").click();
            }
        }
        $.commonAjax(url, data, success)
    })

    function cutOverFirst(nodeId) {
        if (nodeId == '0101' || nodeId == '') {
            var path = '${ctx}/db/toxinjian.do?isReadOnly=' + $("#isReadOnly").val() + '&id=' + $("#idyc").val() + '&wh=' + $("#whyc").val() + '&bllx=' + $("#bllxyc").val()
                + '&nodeid=' + nodeId + '&nodename=' + $("#nodenameyc").val() + '&cjr=' + $("#cjryc").val() + '&cbbmid=' + $("#cbbmidyc").val() + '&cbbmmc=' + $("#cbbmmc").val() + '&lclx=' + $("#lclxyc").val() + '&isfa=' + $("#isfa").val();
            $('#flow_iframe').attr('src', encodeURI(path));
        } else {
            var path = '${ctx}/db/toshenhe.do?isReadOnly=' + $("#isReadOnly").val() + '&id=' + $("#idyc").val() + '&wh=' + $("#whyc").val() + '&bllx=' + $("#bllxyc").val()
                + '&nodeid=' + nodeId + '&nodename=' + $("#nodenameyc").val() + '&cjr=' + $("#cjryc").val() + '&cbbmid=' + $("#cbbmidyc").val() + '&cbbmmc=' + $("#cbbmmc").val() + '&lclx=' + $("#lclxyc").val() + '&isfa=' + $("#isfa").val();
            $('#flow_iframe').attr('src', encodeURI(path));
        }
    }

    function cutOver(el, nodeId) {
        $(el).addClass("pitch").siblings('.active').removeClass('pitch');
        cutOverFirst(nodeId);
    }
</script>
<jsp:include page="${ctx}/pages/footer.jsp"></jsp:include>