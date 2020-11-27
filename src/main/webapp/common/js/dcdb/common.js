/*
* 检查建议模块 公共js
* */
var upload = layui.upload;
var forms=layui.form;



var JCJY = {


    /*
    * 根据相关条件获取组织人员组织机构树
    *  一级分类内容为所属部门
    *       二级内容为当前部门的人员
    *  eg.案件管理中心:
    *          xxxx
    *          xxxx
    *          xxxx
    *     侦查监督科:
    *          xxxx
    *          xxxx
    *     检委会:
    *           xx
    *           xxx
    *     院领导：
    *           xxx
    *           xxx
    *     .....
    *
    * @Params:
    * url:请求路径
    * datas：请求入参
    * eml: 标签id
    * */
    getOrgMap : function (url,datas,elm) {
        if (!url || !datas || !elm) {
            layui.layer.msg("请求入参为空",{icon:5});
            return ;
        }
        var select = $("#" + elm);
        select.html("");
        $.ajax({
            url :  url,
            type : "post",
            data : datas,
            dataType:"json",
            success : function(data) {
                var error  = data.error || '';
                if (error) {
                    layui.layer.msg(error,{icon:5});
                    return ;
                }
                var orgMap = data.msg || {};
                var htm = "<option value=''>请选择审批人</option>";
                if (orgMap.length  == 0) {
                    layui.layer.msg("根据当前条件查询相关信息为空",{icon:5});
                } else {
                    for (var i  in orgMap) {
                        var dept = orgMap[i] || {};
                        var deptName = dept[0].deptName || '';
                        htm +="<optgroup label='"+deptName+"' id='"+i+"'>";
                        for (var x in dept) {
                            var temp = dept[x] || {};
                            htm += "<option value=\""+(temp.userCode || '')+"\" >"+(temp.userName || '')+"</option>";
                        }
                        htm += "</optgroup>";
                    }
                }
                select.html(htm);
                layui.form.render();
            },
            error : function () {
                layui.layer.msg("请求失败，服务器异常",{icon:5});
            }
        });
    },

}


/**
 * js公共组件
 * @param url
 * @param param 必须是json格式数据
 * @param method 默认是post
 * */
$.extend({
    //下载组件
    download:function(url,param,method){
        if(Object.keys(param).length == 0){
            throw new Error("param is null");
        }
        var content = '';
        for(var it in param){
            content += '<input type="text" name="'+it+'" value="'+param[it]+'"/>';
        }
        $('<form action="'+url+'" method="'+(method||'post')+'">' + content + '</form>')
            .appendTo('body').submit().remove();
    },
    //aj组件
    commonAjax:function(url,param,method){
        $.ajax({
            url: url,
            type: "post",
            async: true,
            data: param,
            dataType: "json",
            success: method
        });
    },
    uniqArr: function(array) {
        if(array instanceof Array){
            var temp = []; //一个新的临时数组
            for (var i = 0; i < array.length; i++) {
                if (temp.indexOf(array[i]) == -1) {
                    temp.push(array[i]);
                }
            }
            return temp;
        }
       else{
           return array;
        }
    },
    /**
     * 获取url参数
     * @return array
     * */
    getUrlString:function(){
        var qs = location.search.substr(1), // 获取url中"?"符后的字串
            args = {}, // 保存参数数据的对象
            items = qs.length ? qs.split("&") : [], // 取得每一个参数项,
            item = null,
            len = items.length;

        for(var i = 0; i < len; i++) {
            item = items[i].split("=");
            var name = decodeURIComponent(item[0]),
                value = decodeURIComponent(item[1]);
            if(name) {
                args[name] = value;
            }
        }
        return args;
    }
});

//layui 上传文件
function layuiFormUpload(xsbh,nodeid,type) {
    ajaxQry(xsbh,nodeid,type);
    upload.render({
        elem: '#fileUpload'
        ,url: '/jcjy/commonUpload.do'
        ,multiple: true
        ,data:{"xsbh": xsbh,"nodeId": nodeid}
        ,accept:'file'
        ,allDone: function(obj){ //当文件全部被提交后，才触发
            /* if(fileNameList.length > 0){
                 var name = '';
                 for(var i in fileNameList){
                     name += fileNameList[i] + " ";
                 }
                 layer.msg("文件："+name+"上传失败！已存在同名文件",{icon:2,time:5000},function(){
                     fileNameList=[];
                 })
             }*/
            ajaxQry(xsbh,nodeid,type);
        }
        ,done: function(res, index, upload){
            if(res.code == 2){
                fileNameList.push(res.fileName);
            }
        }
    });

    //全选
    layui.use('form', function () {
        forms.on('checkbox(selectAll)', function (data) {
            //判断是否全部选中，或者未选中
            var flag = data.elem.checked;
            //获取所有的checkbox
            var inputs = $('#fileList').find('input');
            //全选或者不全选
            $(inputs).each(function(){
                var checked = this.checked;
                if(flag != checked){
                    this.checked = flag;
                }
            });
            forms.render();
        });

    });
}


//下载文件
function downLoadFileJcjy(xsbh,nodeid) {
    var info = validationCheckBox();
    var code = info.code;
    if(code == 0){
        layer.msg("请先上传文件",{icon:2,offset:'250px'});
        return;
    }
    var ids = info.ids;

    if(ids != ''){
        $.download('/jcjy/filesDowloadJcjy.do', {'ids': ids, 'xsbh': xsbh, 'nodeId': nodeid});
    }
    else{
        layer.msg('请选择要下载的文件',{icon:2,offset:'250px'});
    }
}

function ajaxQry(xsbh,nodeid,type) {
    if(type==null || type ==undefined || type==""){
        type="4";
    }
    $.ajax({
        url: '/jcjy/queryFileList.do',
        type: "post",
        async: true,
        data: {"nodeId": nodeid,"xsbh": xsbh},
        dataType: "json",
        success: function(data){
            var code = data.code;
            if(code == 1){
                fiList = [];
                var fileList = data.fileList;
                var fileHtml = "";
                for(var i = 0;i<fileList.length;i++){
                    fileHtml += '<div class="layui-col-md'+type+'" title="'+fileList[i].TARGETFILENAME+'">'
                    fileHtml += '<input type="checkbox" name="" title="'+fileList[i].TARGETFILENAME+'" data-id="'+fileList[i].FILENAME+'" lay-skin="primary" class="layui-elip">'
                    fileHtml += '</div>';
                    fiList.push(fileList[i].FILENAME)
                };
                $('#fileList').html(fileHtml);
                forms.render();
            }else{
                layer.msg("查询失败",{icon:2});
            }
        }
    });
}

//删除文件
function deletefilesJcjy(xsbh,nodeid,type) {
    var info = validationCheckBox();
    var code = info.code;
    if(code == 0){
        layer.msg("请先上传文件",{icon:2,offset:'250px'});
        return;
    }
    var param={
        deleteFiles:function(data){
        layer.msg(data.msg,{icon:data.code,offset:'250px',time: 1000},function(){
            ajaxQry(xsbh,nodeid,type);
        })
    }
    }
    var ids = info.ids;
    if(ids != ''){
        $.commonAjax('/jcjy/filesDelete.do',{'ids':ids,'xsbh':xsbh},param.deleteFiles);
    }
    else{
        layer.msg('请选择要删除的文件',{icon:2,offset:'250px'});
    }
}
//校验
function validationCheckBox(){
    //获取所有的checkbox
    var code = 1;
    var inputs = $('#fileList').find('input');
    if(inputs.length == 0){
        code = 0;
    }
    //获取所有的被选中的id
    var ids = '';
    $(inputs).each(function(){
        var checked = this.checked;
        if(checked){
            ids += $(this).data('id')+'-';
        }
    });
    return {
        'code':code,
        'ids':ids
    }
}

//发送至检察长
function sendJcz(xsbh,nodeId,userId,userName) {
    if (!xsbh) {
        layer.msg("xsbh为空", {icon: 5});
        return;
    }
    layer.confirm("确定要发送给检察长么？", {btn: ['确定', '取消'], offset: '120px'}, function (index) {
        $.ajax({
            url: '/jcjy/sendJczCheck.do',
            type: "post",
            async: true,
            data: {"xsbh": xsbh,"nodeId":nodeId,"userId":userId,"userName":userName},
            dataType: "json",
            success: function (data) {
                var error = data.error || '';
                if (error) {
                    layer.msg(error, {icon: 5});
                    return;
                }
                $.ajax({
                    url: '/jcjy/sendJcz.do',
                    type: "post",
                    async: true,
                    data: {"xsbh": xsbh,"nodeId":nodeId,"userId":data.userId,"userName":data.userName},
                    dataType: "json",
                    success: function (data) {
                        var error = data.error || '';
                        if (error) {
                            layer.msg(error, {icon: 5});
                            return;
                        }

                        layer.alert(data.success, {icon: 1}, function () {
                            //跳转首页
                            window.location.href="/jcjy/index_db.do";
                        });
                    }
                });

            }
        });
    });

}