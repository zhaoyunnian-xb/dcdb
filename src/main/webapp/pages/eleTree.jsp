<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="/static/plugin/layui-v2.4.3/layui/css/layui.css" media="all">
    <!--<link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../eleTree/eleTree.css" media="all">-->
    <link rel="stylesheet" href="/static/plugin/layui-v2.4.3/layui/css/eleTree.css" media="all">

    <link rel="icon" type="image/png" sizes="32x32" href="../favicon.ico">
    <style>
        html,body{
            width: 80%;
            margin: 0 auto;
            background-color: #fff;
            padding: 15px 0px 30px 0px;
        }
        .nav ul{
            padding:10px 0px 10px 35px;
        }
        .nav ul li{
            padding: 5px 0px;
            list-style-type: decimal;
        }
        .nav ul a{
            color: #333;
            font-size: 14px;
            text-decoration: none;
        }
        .eleTree-wrapper{
            width: 510px;
        }
        .eleTree{
            width: 250px;
            height: 400px;
            border: 1px solid #ccc;
            overflow: auto;
            display: inline-block;
        }
        .eleTree-search{
            margin-bottom: 10px;
        }
        .btn-wrapper{
            text-align: right;
        }
        a:hover{
            color: #01AAED;
        }
    </style>
</head>
<body>
<div class="eleTree-wrapper">
    <input type="text" placeholder="请输入关键字进行搜索" autocomplete="off" class="layui-input eleTree-search">
    <div class="eleTree ele1" lay-filter="data1">
    </div>
    <div class="eleTree layui-form">
        <ul id="add-box"></ul>
    </div>
</div>

<script src="/static/plugin/jquery/jquery-1.9.1.min.js"></script>
<script src="/static/plugin/layui-v2.4.3/layui/layui.all.js"></script>
<script src="/static/common/js/common.js"></script>
<script>
    var arr = [];
    $(function(){
        var config = {
            'isAllowMore' : $.getUrlString()['isAllowMore'] || '',//同一机构下是否可以选择多人 默认为true
            'isQryAll' : $.getUrlString()['isQryAll'] || '',//是否全部查询 默认为true
            'isRemove' : $.getUrlString()['isRemove'] || 'false',//是否需要排除选项 默认为false
            'removeIds' : $.getUrlString()['removeIds'],//排除的成员id 例如'111@222'
            'isSctSamDept' : $.getUrlString()['isSctSamDept'] || '',//同一部门是否可以选择多个人员 默认为true
            'isEcho': $.getUrlString()['isEcho'] || 'false',//是否需要灰显数据 默认false
            'echoIds': $.getUrlString()['echoIds'] ||'',//回显的id 例如'111@222' isEcho必须为true才可以
        }

        layui.config({
            base: "/static/plugin/layui-v2.4.3/layui/lay/mymodules/"
        }).use(['jquery','table','eleTree','code','form','slider'], function(){
            var $ = layui.jquery;
            var eleTree = layui.eleTree;
            var obj={
                elem: '.ele1',
                url: "/dcdb/testTree.do",
                where:config,
                defaultExpandAll:false,
                renderAfterExpand: true,
                expandOnClickNode: true,
                defaultCheckedKeys: [23],
                searchNodeMethod: function(value,data) {
                    if (!value) return true;
                    return data.label.indexOf(value) !== -1;
                }
            }
            var el1=eleTree.render(obj);
            eleTree.on("nodeClick(data1)",function(d) {
                var data = d.data.currentData;
                var parentDate = d.data.parentData.data;
                data['parent'] = parentDate;
                var children = d.data.currentData.children;
                if( children == undefined){
                    if(!(config.isAllowMore === false || config.isAllowMore === 'false')){
                        if(!isChongFu(data)){
                            if(!(config.isSctSamDept === false || config.isSctSamDept === 'false')){
                                arr.push(data);
                            }else if(!isParentChongFu(parentDate)){
                                arr.push(data);
                            }else{
                                layer.msg("同一机构下不允许选择多人！");
                            }
                        }
                    }else {
                        if(arr.length>0){
                            layer.msg("只能选择一名人员或机构！");
                        }else{
                         //   layer.msg("同一机构下不允许选择多人");
                            arr.push(data);
                        }
                    }
                    flushEle();
                }
            })
            $(".eleTree-search").on("keyup",function() {
                if($(this).val()!=''){
                    el1.expandAll(true);
                }else{
                    el1.unExpandAll(true);
                }

                el1.search($(this).val());
            })
        });
        function isChongFu(d){
            var flag = false;
            $.each(arr,function(a,b){
                if(b.id == d.id){
                    flag = true;
                }
            })
            return flag;
        }
        function isParentChongFu(d){
            var flag = false;
            $.each(arr,function(a,b){
                if(d.id == b.parent.id){
                    flag = true;
                }
            })
            return flag;
        }
        function flushEle(){
            var ele = $('.layui-form #add-box');
            ele.html('');
            var content = '';
            $.each(arr,function(a,b){
                content += '<li>'+b.label+' <i data-index="'+a+'"  class="layui-icon layui-icon-close-fill x"></i></li>';
            })
            ele.html(content);

            $('.x').on('click',function(){
                arr.splice($(this).data('index'),1);
                flushEle();
            })
        }
        function echoData(){
            if(config.isEcho === 'true' && config.echoIds != ''){
                var url = '/dcdb/echoData.do';
                var data = {"echoIds":config.echoIds};
                var success = function(d){
                    var list = d.data;
                    $.each(list,function(a,b){
                        arr.push(b);
                    })
                    flushEle();
                }
                $.commonAjax(url,data,success)
            }
        }
        echoData();
        flushEle()
    })
    function getCurrentArr(){
        return arr;
    }
</script>
</body>
</html>