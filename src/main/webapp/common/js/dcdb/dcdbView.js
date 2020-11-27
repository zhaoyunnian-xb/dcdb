var fileNameList = [];
$(function () {
    var upload = layui.upload;
    var forms = layui.form;
    var param = {
        fileList:function(data){
            document.getElementById('selectAll').checked=false;;
            var code = data.code;
            if(code == 1){
                fiList = [];
                var fileList = data.fileList;
                var fileHtml = "";
                for(var i = 0;i<fileList.length;i++){
                    if(fileList[i] != null){
                        fileHtml += '<div class="layui-col-md3 " title="'+fileList[i].TARGETFILENAME+'">'
                        fileHtml += '<input type="checkbox" name="" title="'+fileList[i].TARGETFILENAME+'" data-id="'+fileList[i].FILENAME+'" lay-skin="primary" class="layui-elip">'
                        fileHtml += '</div>';
                    }
                    fiList.push(fileList[i].FILENAME)
                };
                $('#fileList').html(fileHtml);
                forms.render();
            }
        },
        deleteFiles:function(data){
            layer.msg("删除成功",{icon:data.code},function(){
                ajaxQry(param);
            })
        },
        qryDcdbInfo:function(data){
            var $wsList = $('#wslist');
            $wsList.html('');
            var list = data.msg;
            var html = '<p></p>'
            $.each(list,function(i,item){
                html += '<span class="attachment-download-text" onclick="downLoadWS(this)" data-uuid="'+item.UUID+'">'+item.WENSHU_MC+'</span>'
                html += '<i class="layui-icon layui-icon-download-circle attachment-download-icon" onclick="downLoadWS(this)" data-uuid="'+item.UUID+'"></i>'
            })
            $wsList.html(html);
        },
        uploadHyjy : function(){
            var upload1 = layui.upload;
            upload1.render({
                elem: '.upload-hyjy'
                ,url: '/dcdb/filesUploadHyjy.do'
                ,data:{"id": $("#id").val(),"nodeId":"hyjy"}
                ,accept:'file'
                ,exts:'doc|docx'
                ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(); //上传loading
            }
                ,done: function(res, index, upload){
                    var fileList = res.file;
                    var info = res.info;
                    $.each(info,function(key,item){
                        $('#'+key).val(item);
                    })
                    $.each(fileList,function(key,item){
                        var lable = $('#filehyjy');
                        lable.html("")
                        var html = '<a style="cursor:pointer;" onclick="downloadfile(\''+item.uuid+'\')">'+item.fileName+'</a>'
                        lable.html(html)
                        $('#hyjytext').html("重新上传")
                    })
                    layer.closeAll('loading'); //关闭loading
                }
            });
        }
    }

    ajaxQry(param);
    upload.render({
        elem: '#fileUpload'
        ,url: '/dcdb/filesUpload.do'
        ,multiple: true
        ,data:{"id": $("#id").val(),"nodeId":"1"}
        ,accept:'file'
        ,allDone: function(obj){ //当文件全部被提交后，才触发
            if(fileNameList.length > 0){
                var name = '';
                for(var i in fileNameList){
                    name += fileNameList[i] + " ";
                }
                layer.msg("文件："+name+"上传失败！已存在同名文件",{icon:2,time:5000},function(){
                    fileNameList=[];
                })
            }
            ajaxQry(param);
        }
        ,done: function(res, index, upload){
            if(res.code == 2){
                fileNameList.push(res.fileName);
            }
        }
    });
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
    forms.render();
    //下载
    $('.download-files').on('click',function(){
        var info = validationCheckBox();
        var code = info.code;
        if(code == 0){
            layer.msg("请先上传文件",{icon:2});
            return;
        }
        var ids = info.ids;

        if(ids != ''){
            $.download('/dcdb/filesDowload.do',{'ids':ids,'nodeId':'1','id':$('#id').val()});
        }
        else{
            layer.msg('请选择要下载的文件',{icon:2});
        }

    })
    //删除
    $('.delete-files').on('click',function(){
        var info = validationCheckBox();
        var code = info.code;
        if(code == 0){
            layer.msg("请先上传文件",{icon:2});
            return;
        }
        var ids = info.ids;
        if(ids != ''){
            layer.confirm("确定删除？", {
                    btn: ['确认','取消']
                }, //第一个按钮的方法
                function(){
                    $.commonAjax('/dcdb/filesDelete.do',{'ids':ids,'nodeId':'1','id':$('#id').val()},param.deleteFiles);
                }, //第二个按钮的方法
                function(){
                    layer.closeAll();
                });
        }
        else{
            layer.msg('请选择要删除的文件',{icon:2});
        }
    })
    ajaxQryWs(param);
    param.uploadHyjy();
});
function ajaxQry(param) {
    $.ajax({
        url: '/dcdb/qryJcjyList.do',
        type: "post",
        async: true,
        data: {"id": $("#id").val(),"nodeId": '1'},
        dataType: "json",
        success: param.fileList
    });
}
function ajaxQryJY(param) {
    $.ajax({
        url: '/dcdb/qryJcjyList.do',
        type: "post",
        async: true,
        data: {"id": $("#id").val(),"nodeId": 'hyjy'},
        dataType: "json",
        success: param.fileList
    });
}
function ajaxQryWs(param) {
    $.ajax({
        url: '/dcdb/qryDcdbInfo.do',
        type: "post",
        async: true,
        data: {"id": $("#id").val(),"nodeId": '1'},
        dataType: "json",
        success: param.qryDcdbInfo
    });
}
function downLoadWS(obj){
    var $this = $(obj);
    var uuid = $this.data('uuid');
    $.download('/dcdb/downLoadWs.do',{'uuid':uuid});
}
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

/*
 * 数据传递到初核页面
 * */
function moveData(){
    //传递参数(初核页面)
    window.parent.document.getElementById("xsbh").value = $('#xsbh').val();
    //传递参数(初核页面)
    window.parent.document.getElementById("nodeId").value = '0101';
    window.parent.document.getElementById("bmsah").value = $('#bmsah').val();
    //简要案情
    var jyaq = window.parent.document.getElementsByName('jyaq')[0].value;
    if(jyaq ==''){
        window.parent.document.getElementsByName('jyaq')[0].value = $('textarea[name=jyaq]').val();
    }
    //案件名称
    var ajmc = window.parent.document.getElementById("ajmc").value;
    if(ajmc == ''){
        window.parent.document.getElementById("ajmc").value = $('input[name=ajmc]').val();
    }
    //监督事项基本情况
    var layj = window.parent.document.getElementById("layj").value;
    if(layj == ''){
        window.parent.document.getElementById("layj").value = $('textarea[name=jdsqjbqk]').val();
    }
    //承办检察官意见
    var cbryj = window.parent.document.getElementById("cbryj").value;
    if(cbryj == ''){
        window.parent.document.getElementById("cbryj").value = $('textarea[name=cbjcgyj]').val();
    }
}
//上传文件
function uploadFile(file) {
    $("#" + file).click();
    if (file == 'file') {
        $("#jshf_jshfwsmc").val();
    } else {
        $("#jshf_jshfwsmc").val();
    }

}

function changeFile(obj, ele) {
    debugger
    var files = obj.files;
    var fileName = "";
    for (var i = 0; i < files.length; i++) {
        fileName += files[i].name + " ";
        /*if(fileName.lastIndexOf("doc")!=-1 || fileName.lastIndexOf("docx")!=-1 || fileName.lastIndexOf("jpg")!=-1 || fileName.lastIndexOf("png")!=-1){

         }else{
         layer.msg("您上传的文件不是doc、docx、jpg、png的格式，请校验文档的格式",{icon:2,offset:'100px'});
         return;
         }*/
        //验证文件的大小
        var fileSize = files[i].size;
        if (50 < fileSize / 1024 / 1024) {
            layer.msg("您上传的文件超过50M文件过大，请重新上传", {icon: 2, offset: '550px'});
            return;
        }
        if (0 == fileSize) {
            layer.msg("您上传的文件为空，请重新上传", {icon: 2, offset: '550px'});
            return;
        }
    }

    $("#" + ele).val(fileName);
}

function downloadfile(uuid){
    $.download('/dcdb/filesDowload.do',{'ids':uuid,'nodeId':'hyjy','id':$('#id').val()});
}