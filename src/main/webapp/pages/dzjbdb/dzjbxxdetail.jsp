<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../header.jsp"></jsp:include>
<style>
    .layui-form-label {
        width: 100px
    }

    .layui-form-item .layui-input-block {
        margin-left: 130px
    }

    .files-wrapper {
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        overflow: hidden
    }

    .half-width .layui-form-select{
        display: inline-block;
        width: 50%;
    }
    .half-width .half-input{
        display: inline-block;
        width: 48%;
    }
</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    党组收文登记
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
                    <input type="hidden" id="newId" value="">
                    <input type="hidden" id="curname" value="${curname}">
                    <input type="hidden" id="isth" value="${sfth}">
                </div>
                <div class="layui-card-body">
                    <div class="layui-row">
                        <div class="layui-col-md7">
                            <div class="layui-form">
                                <div class="layui-row">
                                    <div class="layui-col-md6">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">办理类型：</label>
                                            <div class="layui-input-block">
                                                <select name="bllx" lay-verify="required" id="bllx" lay-filter='bllx'>
                                                    <option value="">请选择</option>
                                                    <option value="党组会督办" selected>党组会督办</option>
                                                    <option value="检察长办公会督办">检察长办公会督办</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">督办单号：</label>
                                            <div class="layui-input-block">
                                                <input type="text" name="zxdbh" id="zxdbh" class="layui-input" value=""/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">承办期限：</label>
                                                <div class="layui-input-block half-wrapper">
                                                    <select name="cbqx" lay-verify="cbqx" id="cbqx" lay-filter='cbqx' >
                                                        <option value="">请选择</option>
                                                        <option value="20">20天</option>
                                                        <option value="30">30天</option>
                                                        <option value="60">60天</option>
                                                        <option value="50">定制天数</option>
                                                    </select>
                                                    <input type="text" name="nf" id="cbqxdz" class="layui-input half-input"
                                                           disabled   />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">会议日期：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="swrq" id="swrq" class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">决定事项：</label>
                                                <div class="layui-input-block">
                                                <textarea name="psnr"  id="psnr"
                                                          class="layui-textarea">
现将2020年X月X日省检察院党组会有关工作事项的决议通知如下：研究********有关情况的议题</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">办理需求：</label>
                                                <div class="layui-input-block">
                                                <textarea name="nbyj"  id="nbyj"
                                                          class="layui-textarea">会议同意*****************的有关工作意见。会议要求，****************，请切实抓好贯彻落实。
根据《山东省人民检察院规范效能督查工作若干规定》，请于20XX年X月X日前，分已落实事项和正在落实中事项两类，将决议落实情况送办公室督查室。联系人：*****         联系电话：*****
                                                                                                                                        办  公  室
                                                                                                                                        20XX年X月X日</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">任务类型：</label>
                                                <div class="layui-input-block">
                                                    <select name="pslx" lay-verify="required" id="pslx">
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">上传扫描件：</label>
                                                <div class="layui-input-block">
                                                    <button type="button" class="layui-btn layui-btn-primary" id="test1">
                                                        <i class="layui-icon layui-icon-upload"></i>上传文件
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">上传文件列表：</label>
                                                <div class="layui-input-block">
                                                    <ul class="files-wrapper" id="uploadFiles">
                                                    </ul>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">承办部门：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="cbbm" id="cbbm" placeholder="请输入" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">办理情况：</label>
                                                <div class="layui-input-block">
                                                <textarea name="blqk" placeholder="请输入内容" id="blqk"
                                                          class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md4 layui-col-md-offset1">
                            <div class="layui-form">
                                <div class="layui-collapse">
                                    <div class="layui-colla-item">
                                        <h2 class="layui-colla-title">办公室负责人</h2>
                                        <div class="layui-colla-content layui-show">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="spr" id="spr" required
                                                           lay-verify="required" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="sprq" id="sprq" required
                                                           lay-verify="required" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="spyj" id="spyj" required
                                                           lay-verify="required" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md12"><hr></div>

                        <div class="layui-col-md12">
                            <div class="btn-wrapper ">
                                <div class="layui-col-md7">
                                    <span class="layui-btn layui-btn-normal ycBtn"
                                          onclick="save(1)">保存</span>
                                    <span class="layui-btn layui-btn-primary" id="close">关闭</span>
                                </div>
                                <div class="layui-col-md4 layui-col-md-offset1">
                                    <span class="layui-btn layui-btn-normal ycBtn" id="submitFlow">提交</span>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlowDzjb.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/common.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/dzjbxx.js" language="javascript"></script>
<script>
    $(function () {
      //提交
      $("#submitFlow").click(function(){
        save(2);
      });
      //关闭
      $("#close").click(function(){
        //跳转待办页
        window.location.href = "/db/index.do?lclx=dzjb";
      });

      //初始化按钮
      initBtn();
      //审批意见、办理情况查询
      querySpyj();
      //初始化审批意见签名和日期
      initQmSj();
      //审批意见的隐藏和不可编辑
      spyjHideAndDis();
    })
    var form = layui.form;
    form.on('select(bllx)',function (data) {
        if (data.value == '党组会督办') {
            $("#zxdbh").val("党组会 督〔20XX〕X号")
        }else if (data.value == '检察长办公会督办') {
            $("#zxdbh").val("检察长办公会 督〔20XX〕X号")
        }
    })
    //展示可选择的阶段
    function showStage() {
        var nodeId = $("#nodeidyc").val();
        var ywtype = $("#bllxyc").val();
        if (nodeId == '0103') {

            var layer = layui.layer;
            var addLayer = layer.open({
                type: 1,
                title: '请选择阶段 ',
                content: $('#selectTypeNq'),
                area: ['300px', '300px'],
                scrollbar: false,
                cancel: function (index, layero) {
                    layer.close(index);
                    $('#selectTypeNq').hide();
                }
            })
        } else if (nodeId == '0105') {

            var layer = layui.layer;
            var addLayer = layer.open({
                type: 1,
                title: '请选择阶段 ',
                content: $('#selectTypeFzr'),
                area: ['300px', '300px'],
                scrollbar: false,
                cancel: function (index, layero) {
                    layer.close(index);
                    $('#selectTypeFzr').hide();
                }
            })
        } else {
            selectPerson("1");//提交流程
        }

    }
</script>

<jsp:include page="../footer.jsp"></jsp:include>
