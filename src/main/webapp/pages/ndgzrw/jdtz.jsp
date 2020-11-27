<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/3/6
  Time: 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp"></jsp:include>
<input type="hidden" id="cbbmid" value="${cbbmid}"/>
<input type="hidden" id="name" value="${name}"/>
<div class="layui-fluid">
    <div class="layui-card" style="min-width: 1300px;">
        <div class="layui-card-header">
            季度统计
        </div>
        <div class="layui-card-body">
            <div class="layui-row layui-col-space10">
                <div class="layui-col-sm6 layui-col-md8">
                    <div class="layui-form layui-inline" style="margin-right: 10px;">
                        任务名称：
                        <div class="layui-input-inline">
                            <select name="interest" lay-filter="aihao" id="tjNd">

                            </select>
                        </div>
                    </div>
                    <div class="layui-form layui-inline">
                        季度：
                        <div class="layui-input-inline">
                            <select name="interest" lay-filter="aihao" id="tjJd">
                                <%--  <option value="1" selected>第一季度</option>--%>
                                <option value="2" selected>第二季度</option>
                                <option value="3">第三季度</option>
                                <option value="4">第四季度</option>
                            </select>
                        </div>
                    </div>
                </div>


                <div class="layui-col-sm6 layui-col-md4">
                    <span class="layui-btn layui-btn-primary" id="selecttTj">查询</span>
                    <span class="layui-btn layui-btn-normal" id="sctz">导出台账</span>
                    <c:if test="${name=='罗艳'}">
                        <span class="layui-btn layui-btn-normal" id="tzgs">台账公示</span>
                    </c:if>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-sm12">
                    <table class="layui-table" id="jdTjTable" ></table>
                </div>
            </div>
        </div>
    </div>
</div>

<%--
<script src=/static/common/js/importExecl.js"></script>--%>
<script language="JavaScript" type="text/javascript">
    var layuiForm = layui.form;
    $(function () {
        $.ajax({
            url: "${ctx}/Tjtable/ndrw.do",
            type: "get",
            async: false,
            data: {"cbbmid": $("#cbbmid").val(), "tjzt": "", "name": $("#name").val()},
            dataType: "json",
            success: function (result) {
                var html = "";
                if (result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        html += "<option value='" + result[i].ID + "'>" + result[i].NDRWMC + "</option>";
                    }
                    $("#tjNd").html(html);
                }
                layuiForm.render();
            }
        });
        $("#selecttTj").click();
    });
    $("#sctz").click(function () {
        location.href = "${ctx}/Tjtable/importExecl.do?jdlx=" + $("#tjJd option:selected").val() + "&ndid=" + $("#tjNd option:selected").val() + "&cbbmid=" + $("#cbbmid").val() + "&fileName=" + $("#tjNd option:selected").text();
    })

    $("#selecttTj").click(function () {
        $.ajax({
            url: "${ctx}/Tjtable/tjtable.do",
            type: "get",
            async: false,
            data: {
                "jdlx": $("#tjJd option:selected").val(),
                "ndid": $("#tjNd option:selected").val(),
                "cbbmid": $("#cbbmid").val(),
                "name": $("#name").val()
            },//
            dataType: "json",
            success: function (result) {
                if (result[0].stauts == '1') {
                    var html = '<tr><td colspan="17" style="text-align: center;background-color:#F7F7F7">' + $("#tjNd option:selected").text() + "" + $('#tjJd option:selected').text()
                        + '工作任务责任分工一览表</td></tr>';
                    for (var i = 1; i < result.length; i++) {
                        var list = result[i];
                        var contextHtml = "";
                        var n = 0;
                        //var bmhtml=handle(list);
                        for (var x = 0; x < list.length; x++) {
                            if(list[x].phbm) {
                                list[x].phbm = list[x].phbm.replace(/;/g,'<br>');
                            }
                            if(list[x].zrld){
                                list[x].zrld = list[x].zrld.replace(/,/g, '<br>')
                            }


                            if (list[x].key != undefined) {
                                html +=
                                    '<tr><td colspan="1" style="text-align: center;background-color: #F7F7F7;width:60px">要点内容</td><td colspan="8" style="background-color: #F7F7F7">' + list[x].key + '</td></tr>' +
                                    '<tr"><td style="text-align: center;width:60px">序号</td><td style="text-align: center;width:80px">责任领导</td><td style="text-align: center;width:200px">主要任务</td>';
                                if ($('#tjJd option:selected').val() == '1') {
                                    html +=
                                        '<td style="text-align: center;width:200px">一季度目标</td><td style="text-align: center;width:200px">一季度完成情况</td><td style="text-align: center">一季度评估</td>';
                                } else if ($("#tjJd option:selected").val() == '2') {
                                    html +=
                                        '<td style="text-align: center;width:200px">半年目标</td><td style="text-align: center;width:200px">半年完成情况</td><td style="text-align: center">半年评估</td>';
                                } else if ($("#tjJd option:selected").val() == '3') {
                                    html +=
                                        '<td style="text-align: center;width:200px">三季度目标</td><td style="text-align: center;width:200px">三季度完成情况</td><td style="text-align: center">三季度评估</td>';
                                } else if ($("#tjJd option:selected").val() == '4') {
                                    html +=
                                        '<td style="text-align: center;width:200px">全年目标</td><td style="text-align: center;width:200px">全年完成情况</td><td style="text-align: center">全年评估</td>';
                                }
                                html +=
                                    '<td style="text-align: center;width:100px">牵头部门</td><td style="text-align: center;width:110px">配合部门</td></tr>';
                            } else {
                                contextHtml += '<tr><td style="text-align: center;width:60px">' + ++n +
                                    '</td><td style="text-align: center;width:80px">' + handleData(list[x].zrld) +
                                    '</td><td style="text-align: left;width:200px">' + handleData(list[x].zyrwmc) +
                                    '</td>';
                                if ($('#tjJd option:selected').val() == '1') {
                                    contextHtml += '<td style="text-align: left;width:200px">' +
                                        handleData(list[x].yjdmb) +
                                        '</td><td style="text-align: left;width:200px">' + handleData(list[x].yjdwcqk) + '</td><td style="text-align: center">' + handleData(list[x].yjdpg) + '</td>';
                                } else if ($("#tjJd option:selected").val() == '2') {
                                    contextHtml += '<td style="text-align: left;width:200px">' + handleData(list[x].bnmb) + '</td><td style="text-align: left;width:200px">' + handleData(list[x].bnwcqk) + '</td><td style="text-align: center;width:200px">' + handleData(list[x].bnpg) + '</td>';
                                } else if ($("#tjJd option:selected").val() == '3') {
                                    contextHtml += '<td style="text-align: left;width:200px">' + handleData(list[x].sjdmb) + '</td><td style="text-align: left;width:200px">' + handleData(list[x].sjdwcqk) + '</td><td style="text-align: center">' + handleData(list[x].sjdpg) + '</td>';
                                } else if ($("#tjJd option:selected").val() == '4') {
                                    contextHtml += '<td style="text-align: left;width:200px">' + handleData(list[x].qnmb) + '</td><td style="text-align: left;width:200px">' + handleData(list[x].qnwcqk) + '</td><td style="text-align: center;width:200px">' + handleData(list[x].qnpg) + '</td>';
                                }
                                contextHtml += '<td style="text-align: center;width:100px">' +
                                    handleData(list[x].ssbm) +
                                    '</td><td style="text-align: center;width:100px">' + handleData(list[x].phbm) +
                                    '</td>';
                                +'</tr>';
                            }
                        }
                        html += contextHtml;
                    }
                    $("#jdTjTable").html(html);
                } else {
                    layer.msg("数据查询为空", {icon: 2});
                }
            }
        })
    })

    function handleData(a) {
        if (a == undefined) {
            a = '---';
        }
        return a;
    }

    /*function handle(list){
        var zzhtml='';
        var qthtml='';
        var phhtml='';x
        var html="";
        for(var x=0;x<list.length;x++){
            if(list[x].bmlx=="zz"){
                zzhtml+=handleData(list[x].ssbm)+' ';
            }
            if(list[x].bmlx=="qt"){
                qthtml+=handleData(list[x].ssbm)+' ';
            }
            if(list[x].bmlx=="ph"){
                phhtml+=handleData(list[x].ssbm)+' ';
            }
        }
        html='<td style="text-align: center">'+qthtml+'</td><td  style="text-align: center">'+phhtml+'</td>';
        return html;
    }*/

    //jQuery HTML导出Excel文件(兼容IE及所有浏览器)
    function HtmlExportToExcel(tableid) {
        var filename = $('.datatitle').text();
        if (getExplorer() == 'ie' || getExplorer() == undefined) {
            HtmlExportToExcelForIE(tableid, filename);
        }
        else {
            HtmlExportToExcelForEntire(tableid, filename)
        }
    }

    //IE浏览器导出Excel
    function HtmlExportToExcelForIE(tableid, filename) {
        try {
            var curTbl = document.getElementById(tableid);
            var oXL;
            try {
                oXL = new ActiveXObject("Excel.Application"); //创建AX对象excel
            } catch (e) {
                alert("无法启动Excel!\n\n如果您确信您的电脑中已经安装了Excel，" + "那么请调整IE的安全级别。\n\n具体操作：\n\n" + "工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
                return false;
            }
            var oWB = oXL.Workbooks.Add(); //获取workbook对象
            var oSheet = oWB.ActiveSheet;//激活当前sheet
            var sel = document.body.createTextRange();
            sel.moveToElementText(curTbl); //把表格中的内容移到TextRange中
            try {
                sel.select(); //全选TextRange中内容
            } catch (e1) {
                e1.description
            }
            sel.execCommand("Copy");//复制TextRange中内容
            oSheet.Paste();//粘贴到活动的EXCEL中
            oXL.Visible = true; //设置excel可见属性
            var fname = oXL.Application.GetSaveAsFilename(filename + ".xls", "Excel Spreadsheets (*.xls), *.xls");
            oWB.SaveAs(fname);
            oWB.Close();
            oXL.Quit();

        } catch (e) {
            alert(e.description);
        }
    }

    //非IE浏览器导出Excel
    var HtmlExportToExcelForEntire = (function () {
        var uri = 'data:application/vnd.ms-excel;base64,',
            //template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
            template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" ' +
                'xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8">' +
                '<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions>' +
                '<x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->' +
                // 在这里自定义table样式，可以将样式导出到excal表格
                ' <style type="text/css">' +
                'table td,table th {' +
                'width: 200px;' +
                'height: 30px;' +
                ' text-align: center;' +
                ' }' +
                '</style>' +
                '</head><body><table>{table}</table></body></html>';
        base64 = function (s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        },
            format = function (s, c) {
                return s.replace(/{(\w+)}/g, function (m, p) {
                    return c[p];
                });
            };
        return function (table, name) {
            if (!table.nodeType) {
                table = document.getElementById(table);
            }
            var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
            var a = document.createElement("a");
            a.download = "test.xls";
            a.href = uri + base64(format(template, ctx));
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
    })()

    function getExplorer() {
        var explorer = window.navigator.userAgent;
        //ie
        if (explorer.indexOf("MSIE") >= 0) {
            return 'ie';
        }
        //firefox
        else if (explorer.indexOf("Firefox") >= 0) {
            return 'Firefox';
        }
        //Chrome
        else if (explorer.indexOf("Chrome") >= 0) {
            return 'Chrome';
        }
        //Opera
        else if (explorer.indexOf("Opera") >= 0) {
            return 'Opera';
        }
        //Safari
        else if (explorer.indexOf("Safari") >= 0) {
            return 'Safari';
        }
    }

    $("#tzgs").click(function () {
        var ndid = $('#tjNd option:selected').val();
        var ndrwmc = $("#tjNd").find("option:selected").text();
        var jd = $('#tjJd option:selected').val();
        var jdmc = $("#tjJd").find("option:selected").text();
        $.ajax({
            url: "${ctx}/Tjtable/addTzgs.do",
            type: "post",
            async: true,
            data: {"ndid": ndid, "ndrwmc": ndrwmc, "jd": jd, "jdmc": jdmc},
            dataType: "json",
            success: function (result) {
                if (result.code == 1) {
                    layer.msg("台账公示成功", {offset:200,icon: 1});
                } else {
                    layer.msg(result.msg, {offset:200,icon: 2});
                }
            }
        });

    });
</script>
<jsp:include page="../footer.jsp"></jsp:include>
