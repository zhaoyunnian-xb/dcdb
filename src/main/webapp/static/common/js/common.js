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