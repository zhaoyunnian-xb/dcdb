<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../header.jsp"></jsp:include>
<style>
    .list-wrapper{
        overflow: hidden;
        margin-top: 20px;
    }
    .list-wrapper .left{
        width: 45%;
        float: left;
        padding: 2%;
        border: 1px solid #cccccc;
        border-radius: 3px;
        height: 400px;
        overflow: auto;

    }
    .list-wrapper .right{
        width: 45%;
        float: right;
        padding: 2%;
        border: 1px solid #cccccc;
        border-radius: 3px;
        height: 400px;
        overflow: auto;
    }
    .list-wrapper .left li, .list-wrapper .right li{
        height: 30px;
        line-height: 30px;
        cursor: pointer;
    }
    .list-wrapper .right li{
        border: 1px solid #999999;
        border-radius: 3px;
        padding: 0 10px;
        color: #999999;
        margin-bottom: 10px;
    }
    .list-wrapper .right li .delete{
        float: right;
        font-size: 18px;

    }
</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-form">
                <div class="layui-form-item">
                    <div class="">
                        <select name="city" lay-verify="required" lay-filter="test" id="cbbmSelect">
                        </select>
                    </div>
                    <div class="list-wrapper">
                        <ul class="left">
                        </ul>
                        <ul class="right">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlow.js" language="javascript"></script>
<script>
    var choseArr = []; // 存储选择人员的数组
    var isMul = $.getUrlString()['isMul']|| false;  // true为可以多选，false为单选
    var flag = $.getUrlString()['flag']|| 'normal';  // true为可以多选，false为单选
    $(function () {
        var form = layui.form;
        var layer = layui.layer;


        queryCbBm();
        var bmid= $('#cbbmSelect option:selected').val();
        queryBmPerson(bmid);

        form.render();
        // 切换select
        form.on('select(test)', function(data){
            var parentid = data.value;
            var parentname = data.elem[data.elem.selectedIndex].text;
            queryBmPerson(parentid)

        });

        // 向右添加人员
        $('.left').on('click','li', function () {
            if (isMul == true || isMul =='true') {
                transEle(this);
            } else {
                if ( !(choseArr.length === 1)) {
                   transEle(this);
                } else {
                    layer.msg('只能选一个人员')
                    return
                }
            }
        });

         // 删除人员
        $('.right').on('click', '.delete',function () {
            var $this = $(this);
            var id = $this.data('id');
            var index = $.inArray(id,choseArr)
          if (index >  -1) {
                choseArr.splice(index, 1);
                $this.parent().remove();
            };

        });
    })


       function transEle(el) {
           var $this = $(el);
           var id = $this.data('id');

           if ($.inArray(id,choseArr) < 0) {
               choseArr.push(id);
               var cloneLi = $this.clone();
               var deleteIcon = '<i class="layui-icon layui-icon-close delete" data-id="'+id+'"></i>'
               cloneLi.append(deleteIcon);
               $('.right').append(cloneLi);
           } else {
               layer.msg('该人员已经选过了')
           }
       }
        // 获取人员arry
     function getPersonArr() {
         var submitArr = [];
         var allLi = $('.right li');
             $.each(allLi, function (i, n) {
                 var $li = $(n),
                     selfid = $li.data('id'),
                     selfname = $li.text(),
                     parentid = $li.data('parentid'),
                     parentname = $li.data('parentname');
                 var choseObj = {
                     selfid: selfid,
                     selfname: selfname,
                     parentid: parentid,
                     parentname: parentname

                 };
                 submitArr.push(choseObj)
             })
            return submitArr;

     }
    //查询部门下拉列表
    function queryCbBm() {
      if(flag == "nq"){
        $("#cbbmSelect").append("<option value=‘neiqin’>内勤</option>");
        $("#cbbmSelect").attr("disabled","disabled");
        return;
      }
        $.ajax({
            url: '/db/queryCbBm.do',
            type: "post",
            async: false,
            dataType: "json",
            success: function (data) {
                var curBm=data.curBm;
                $("#cbbmSelect").empty();
                for (var i = 0; i < data.data.length; i++) {
                    $("#cbbmSelect").append("<option value=" + data.data[i].ID + ">" + data.data[i].NAME + "</option>");
                }
                $("#cbbmSelect").find("option[value ='"+curBm+"']").attr("selected","selected");
                  if(flag == "dcs"){
                  //  $("#cbbmSelect").find("option[text ='督查室']").attr("selected","selected");
                    $("#cbbmSelect option:contains('督查组')").attr("selected", true);   //根据text选中
                  }

            },
        });
    }
    //查询部门下的人
    function queryBmPerson(id) {
        $.ajax({
            url: '/db/queryBmPerson.do',
            type: "post",
            data:{'unitid':id,'flag':flag},
            async: false,
            dataType: "json",
            success: function (data) {
                $(".left").empty();
                var str='';
                for (var i = 0; i < data.data.length; i++) {
                    str += '<li data-id="'+data.data[i].USERID+'" data-parentid= "' + data.data[i].UNITID + '" data-parentname="' + data.data[i].UNITNAME + '">'+data.data[i].USERNAME+'</li>';
                }
                $('.left').html(str);
            },
        });
    }
</script>

<jsp:include page="../footer.jsp"></jsp:include>
