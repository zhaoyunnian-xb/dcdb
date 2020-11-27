$(function () {
    var isReadOnly= $("#isReadOnly").val();
    var ywtype = $("#bllxyc").val();
    if(isReadOnly == '1'){
        $("input").attr("disabled","disabled");
        $("textarea").attr("disabled","disabled");
        $("#czspan").hide();
        $("button").hide();
        $(".ycBtn").hide();
    }
    if(ywtype=='市院交办'){
    	$("#shenpiyj").hide();
    }

})
var code ="2";
/**
 * 发送ajax请求
 * */
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

var rebackFlag = 0;
function validate(curnodeId,needNodeId){
    if(rebackFlag == 1){
        return true;
    }
    return curnodeId >= needNodeId;
}


//提交和退回(sftg 1:提交、0:退回)
function flow(sftg,userid,pl) {
    if(sftg =="0"){
        rebackFlag = 1;
    }

    var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
    var wh = $("#whyc").val() == "" ? $("#wh").val() : $("#whyc").val();
    var ywtype = ($("#bllxyc").val() == ""  || $("#bllxyc").val() == "undefined")? $('#bllx option:selected').val() : $("#bllxyc").val();
    var cbbmmc = ($("#cbbmmcyc").val() == ""  || $("#cbbmmcyc").val() == "undefined" )? $("#cbbm").val() : $("#cbbmmcyc").val();
    var lctype = $("#lclxyc").val();
    var cbbmid = $("#cbbmidyc").val();
    var nodeid = $("#nodeidyc").val();
    var nodename = $("#nodenameyc").val();
    var userid=userid;
    var username="";
    var isBatch= pl == false ? "0":"1";  //是否批量发送(0:否，1：是)
    var isBfyq=$("input:radio[name='isbfyq']:checked").val(); //是否编发要请

    var sffsfzr=$("#sffsfzr").val();
    var fzlcbs=$("#fzlcbs").val();

    //当发送内勤节点时，判断 下一个步骤是 "发送内勤" or "发送部门负责人"
    if(nodeid == "01021"){
        if(sffsfzr == ""){
            sffsfzr="0";
        }
    }

    var param = {
        url: '/flow/submitFlowSelect.do',
        data: {'id': id, 'wh':wh,'nodeId': nodeid, 'nodeName': nodename,
            'ywtype':ywtype,'sftg':sftg,'cbbmid':cbbmid,'cbbmmc':cbbmmc,
            'userid':userid,'username':username,'lctype':lctype,'isBatch':isBatch,'isBfyq':isBfyq,"sffzr":sffsfzr,"fzlcbs":fzlcbs},
        success: function (data) {
            layer.msg(data.msg, {icon: data.code,time:1000}, function () {
                if (data.code == '1') {
                    var url="/db/index.do?lclx=jbsx";
                    //跳转待办页
                    window.location.href = url;

                }});

        }
    };
    ajax(param);


}


function reback() {
    layer.confirm("确定要退回么？", {btn: ['确定', '取消']}, function (index){
        flow("0","",false);//退回流程
    });
}

//选择下一节审批人
function selectPerson(sftg) {
    var isBatch=false;
    var flag='normal';
    var nodeid=$("#nodeidyc").val();
    var nodename=$("#nodenameyc").val();
    var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();
    var fzlcbs= $("#fzlcbs").val();


    //当办公室负责人节点->发送内勤节点时，不用选人，直接发送给创建人
    if ( nodeid == "0102") {
        flow(sftg,"",isBatch);
        return;
    }

    //当发送内勤节点时，设置成多选，flag设置成nq，显示内勤人员
    if ( nodeid == "01021") {
        if($("#sffsfzr").val() != "1"){
            flag = 'nq';
            isBatch = true;
        }

    }

    //当部门负责人审批->督查室审查时，不需要选人，直接发送给创建人(前提条件：一般性工作批示、专项督办省院，选择直接办理时)
    if ( nodeid == "0105" && fzlcbs =="DCS") {
        flow(sftg,"",isBatch);
        return;
    }


    //当为督查审核节点时，不用选人，直接发送给创建人
    if(nodeid == "01061" ){
        flow(sftg,"",isBatch);
        return;
    }

    //当为待归档节点时，不用选人
    if(nodeid == "8888"){
        flow(sftg,"",isBatch);
        return;
    }


    var layer = layui.layer;
    layer.open({
        type: 2,
        title: '请选择发送人',
        content: '/pages/dcdblc/transforEle.jsp?isMul='+isBatch+'&flag='+flag,
        area: ['60%', '80%'],
        offset:['30px'],
        scrollbar: false,
        btn: ['确认'],
        yes: function (index, layero) {
            var body = layer.getChildFrame('body', index);
            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var a = iframeWin.getPersonArr();
            var str = "";
            var str2 = "";
            var userid = "";
            var username = "";
            $.each(a, function (i, item) {
                userid = item.selfid;
                username = item.selfname;
                var depid = item.parentid;
                var depname = item.parentname;
                str += depid + "@" + depname+"@"+userid+"@"+username+";";
                str2 += username;
            })

            if (userid == '') {
                layer.alert("请选择下一级审批人！");
                layer.close(index);
                return;
            }
            flow(sftg,str,isBatch);
            layer.close(index);


        }
    })
}


//审批意见-保存
function saveSpyj() {
    var data=[];
    var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
    var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();
    var blqk=$("#blqk").val();
    var nodeid=$("#nodeidyc").val();


        if(nodeid <= "0104"){
            var parmBlqk=getParm(id,ywtype,"blqk");
            parmBlqk.spbz=blqk;
            data.push(parmBlqk);

            if (nodeid == "0104") {
                //督查复核人-意见数据
                var parmDcfhr = getParm(id, ywtype, "dscsp");
                $("[name='dscsp']").each(function () {
                    var id = $(this).attr('id');
                    var name = $(this).attr('id').split("_")[1];
                    //parm1[name]=$('#'+ id).val();

                        parmDcfhr[name] = validate(nodeid, '0106') ? $('#' + id).val() : '';
                })

                //部门负责人-意见数据
                var parmBmfzr = getParm(id, ywtype, "bmfzrsp");
                $("[name='bmfzrsp']").each(function () {
                    var id = $(this).attr('id');
                    var name = $(this).attr('id').split("_")[1];
                    // parm2[name]=$('#'+ id).val();
                    parmBmfzr[name] = validate(nodeid, '0105') ? $('#' + id).val() : '';
                })


                data.push(parmDcfhr);
                data.push(parmBmfzr);
            }

        }else{

            //办理情况-数据
            var parmBlqk=getParm(id,ywtype,"blqk");
            parmBlqk.spbz=blqk;

            //部门负责人-意见数据
            var parmBmfzr=getParm(id,ywtype,"bmfzrsp");
            $("[name='bmfzrsp']").each(function () {
                var id=$(this).attr('id');
                var name = $(this).attr('id').split("_")[1];
                // parm2[name]=$('#'+ id).val();
                parmBmfzr[name]=validate(nodeid,'0105')?$('#'+ id).val():'';
            })




            //督查复核人-意见数据
            var parmDcfhr=getParm(id,ywtype,"dscsp");
            $("[name='dscsp']").each(function () {
                var id=$(this).attr('id');
                var name = $(this).attr('id').split("_")[1];
                //parm1[name]=$('#'+ id).val();

                    parmDcfhr[name]=validate(nodeid,'0106')?$('#'+ id).val():'';
            })


            //督查审核人-意见数据
            var parmDcshr=getParm(id,ywtype,"dbsfhr");
            $("[name='dbsfhr']").each(function () {
                var id=$(this).attr('id');
                var name = $(this).attr('id').split("_")[1];
                // parm5[name]=$('#'+ id).val();
                    parmDcshr[name] = validate(nodeid, '01061') ? $('#' + id).val() : '';
            })



            data.push(parmBlqk);
            data.push(parmBmfzr);
            data.push(parmDcshr);
            data.push(parmDcfhr);
        }


    var param = {
        url: '/flow/saveSpyj.do',
        data: {'info': JSON.stringify(data)},
        success: function (data) {
            code=data.code;
            if(data.code!="1"){
                layer.msg(data.msg,{icon: data.code,offset: ['255px', '500px']});

            }
        }
    };
    ajax(param);
}

function getParm(id,ywtype,splx) {
    var parm={};
    parm["id"]=id;
    parm["bllx"]=ywtype;
    parm["splx"]=splx;
    return parm;
}
//查询审批意见
function querySpyj() {
    var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
    var param = {
        url: '/flow/querySpyj.do',
        data: {'id':id},
        success: function (data) {
            if(data.code =='1'){
                var blqk="";
                $.each(data.msg,function (i,item) {
                    var splx=item.splx;
                    if(splx == "blqk"){
                        blqk=item.spbz;
                    }
                    $.each(item,function(key,value){
                        $("#"+splx+"_"+key).val(value);
                    });
                })
                $("#blqk").val(blqk);

            }else{
                layer.msg(data.msg, {icon: data.code});
            }

        }
    };
    ajax(param);
}

//初始化按钮
function initBtn() {
    if ( $("#zxdbh").val() ==""){
        $("#zxdbh").val("鲁检督查[20XX]X号")
    }
    var ymlx = $("#ymlx").val()
    if (ymlx == 'db'){
        $("#returnBtn").show();
    } else if (ymlx == 'yb'){
        $("#returnBtn").show();
    }else if (ymlx == 'bj') {
        $("#returnBtn").show();
    }else {
        $("#returnBtn").hide();
    }
    var nodeid=$("#nodeidyc").val();
    var nodename=$("#nodenameyc").val();
    var lclxyc=$("#lclxyc").val();
    var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();

        $("#submitFlow").show();
        $("#rebackFlow").hide();
        $("#submitFlowFzr").hide();
        $("#yulan").hide();
        if(nodeid == "01011"){
            $("#submitFlow").html("复核通过");
            $("#rebackFlow").show();
        }else  if(nodeid == "0102"){
            $("#submitFlow").html("审核通过");
            $("#rebackFlow").show();
        }else if(nodeid == "0103"){
            //内勤分案
            $("#submitFlow").html("分办");
        }else if(nodeid == "0104"){
            //部门承办人
            $("#submitFlow").html("提交审核");
        }else if(nodeid == "0105"){
            //承办部门负责人审核
            $("#rebackFlow").show();
            $("#submitFlow").html("交办或审核");
        }else if(nodeid == '0106' ){
            //督查室复核
            $("#rebackFlow").show();
            $("#submitFlow").html("复核通过");
        } else if(nodeid == "8888"){
            $("#submitFlow").html("归档");
        }else if(nodeid == '01021'){
            //发送内勤
            $("#submitFlowFzr").show();
            $("#submitFlow").html("发送内勤");
        }else if(nodeid == '01061'){
            //督查审核
            $("#submitFlow").html("审核通过");
            $("#rebackFlow").show();
        }


}

//初始化签名和日期
function initQmSj() {
    var laydate = layui.laydate;
    var nodeid=$("#nodeidyc").val();
    var nodename=$("#nodenameyc").val();
    var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();

    if(nodeid == '0102'){
        //办公室负责人审批
        laydate.render({
            elem: '#sprq',
            format: 'yyyy年MM月dd日',
            value: $("#sprq").val() == "" ? new Date() : $("#sprq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#sprq").val(value);
            }
        });
        $("#spr").val(  $("#spr").val() == $("#curname").val() ?$("#spr").val() : "");
        $("#spr").val(  $("#spr").val() == "" ?$("#curname").val() : $("#spr").val());
    }else if(nodeid == '0105'){
        //承办部门负责人审批
        laydate.render({
            elem: '#bmfzrsp_sprq',
            format: 'yyyy年MM月dd日',
            value: $("#bmfzrsp_sprq").val() == "" ? new Date() : $("#bmfzrsp_sprq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#bmfzrsp_sprq").val(value);
            }
        });
        $("#bmfzrsp_spr").val(  $("#bmfzrsp_spr").val() == $("#curname").val() ?$("#bmfzrsp_spr").val() : "");
        $("#bmfzrsp_spr").val(  $("#bmfzrsp_spr").val() == "" ?$("#curname").val() : $("#bmfzrsp_spr").val());
    }else if(nodeid == '0106' ){
        //督查室复核
        laydate.render({
            elem: '#dscsp_sprq',
            format: 'yyyy年MM月dd日',
            value: $("#dscsp_sprq").val() == "" ? new Date() : $("#dscsp_sprq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#dscsp_sprq").val(value);
            }
        });
        $("#dscsp_spr").val(  $("#dscsp_spr").val() == $("#curname").val() ?$("#dscsp_spr").val() : "");
        $("#dscsp_spr").val(  $("#dscsp_spr").val() == "" ?$("#curname").val() : $("#dscsp_spr").val());
    }else if(nodeid == '01061' ){
        //督查审核
        laydate.render({
            elem: '#dbsfhr_sprq',
            format: 'yyyy年MM月dd日',
            value: $("#dbsfhr_sprq").val() == "" ? new Date() : $("#dbsfhr_sprq").val(),
            isInitValue: true,
            done: function(value, date, endDate){
                $("#dbsfhr_sprq").val(value);
            }
        });
        $("#dbsfhr_spr").val(  $("#dbsfhr_spr").val() == $("#curname").val() ?$("#dbsfhr_spr").val() : "");
        $("#dbsfhr_spr").val(  $("#dbsfhr_spr").val() == "" ?$("#curname").val() : $("#dbsfhr_spr").val());
    }

}
//审批意见的隐藏和不可编辑
function spyjHideAndDis() {
    var nodeid=$("#nodeidyc").val();
    var nodename=$("#nodenameyc").val();
    var lclxyc=$("#lclxyc").val();
    var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();

    if(nodeid == '0101'){
        $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '01011' ){
        $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '0102'){
        //办公室负责人审批-拟办送审
        $(".layui-colla-item :eq(0)").siblings().find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '0103'){
        //内勤分案
        $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '0104'){
        //承办人填写
        $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '0105'){
        //部门负责人审批
        $(".layui-colla-item :eq(1)").siblings().find("input,textarea").attr('disabled', 'true');
    }else if((nodeid == '0106') ){
        //督查室复核
        $(".layui-colla-item :eq(3)").siblings().find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '8888'){
        $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
    }else if(nodeid == '01021'){
        //发送内勤
        $(".layui-colla-item").find("input,textarea").attr('disabled', 'true');
    }else if( nodeid == '01061' ){
        //督查审核
        $(".layui-colla-item :eq(2)").siblings().find("input,textarea").attr('disabled', 'true');
    }
   
}

//预览文件
$('#yulan').on('click', function () {
    var lclxyc=$("#lclxyc").val();
    var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();
    var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
    var wh = $("#whyc").val() == "" ? $("#wh").val() : $("#whyc").val();
    var index = layer.open({
        type: 2,
        title:"导出预览",
        area:["80%", "95%"],
        offset: ['20px'],
        content: encodeURI('../pages/ldpsdb/gdyl.jsp?id='+id+'&lclx='+lclxyc+'&bllx='+ywtype+'&wh='+wh),
    });
})

