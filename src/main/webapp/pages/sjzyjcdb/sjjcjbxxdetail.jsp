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
                    上级决策收文登记
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
                </div>
                <div class="layui-card-body">
                    <div class="layui-row">
                        <div class="layui-col-md7">
                            <div class="layui-form">
                                <div class="layui-row">
                                    <div class="layui-col-md6">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">立项号：</label>
                                            <div class="layui-input-block">
                                                <input type="text" name="lxh" id="lxh" class="layui-input" value="20190000"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">重要决策类型：</label>
                                            <div class="layui-input-block half-wrapper">
                                                <select name="zyjclx" lay-verify="cbqx" id="zyjclx" lay-filter='zyjclx' >
                                                    <option value="">请选择</option>
                                                    <option value="1">党中央重大决策</option>
                                                    <option value="2">党中央重要会议</option>
                                                    <option value="3">党中央重要文件</option>
                                                    <option value="4">党中央重要决定</option>
                                                    <option value="5">党中央重要指示</option>
                                                    <option value="6">省委重要会议</option>
                                                    <option value="7">省委重要文件工作部署</option>
                                                    <option value="8">最高检重要会议</option>
                                                    <option value="9">最高检重要文件工作部署</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">立项日期：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="lxrq" id="lxrq" class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">立项名称：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="lxmc" id="lxmc" class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">拟办意见：</label>
                                                <div class="layui-input-block">
                                                <textarea name="nbyj"  id="nbyj"
                                                          class="layui-textarea">会议同意*****************的有关工作意见。会议要求，****************，请切实抓好贯彻落实。
根据《山东省人民检察院规范效能督查工作若干规定》，请于20XX年X月X日前，分已落实事项和正在落实中事项两类，将决议落实情况送办公室督查室。
 联系人：*****     联系电话：*****


                                    办  公  室
                                  20XX年X月X日
</textarea>
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
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">上传附件：</label>
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
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">联系人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="lxr" id="lxr" class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">联系电话：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="lxdh" id="lxdh" class="layui-input"/>
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
                                                    <input type="text" name="bgsfzrmc" id="bgsfzrmc" required
                                                           lay-verify="required" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bgssprq" id="bgssprq" required
                                                           lay-verify="required" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bgsspyj" id="bgsspyj" required
                                                           lay-verify="required" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">审批备注 ：</label>
                                                <div class="layui-input-block">
                                                    <textarea name="bgsspbz" id="bgsspbz" class="layui-textarea"></textarea>
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
                                    <span class="layui-btn layui-btn-normal" onclick="save(1)">保存</span>
                                    <span class="layui-btn layui-btn-primary" id="close">关闭</span>
                                </div>
                                <div class="layui-col-md4 layui-col-md-offset1">
                                    <span class="layui-btn layui-btn-normal" id="submitFlow">提交</span>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlowSjzyjc.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/common.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/sjjcjbxx.js" language="javascript"></script>
<script>
    $(function () {
        //提交
        $("#submitFlow").click(function(){
          save(2);
        });
        //退回
        $("#rebackFlow").click(function(){
            save(3);
        });
        //关闭
        $("#close").click(function(){
            //跳转待办页
            window.location.href = "/db/index.do?lclx=zyjc";
        });


        //初始化按钮
        initBtn();
        //初始化审批意见签名和日期
        initQmSj();
        //审批意见的隐藏和不可编辑
        spyjHideAndDis();
    })

</script>

<jsp:include page="../footer.jsp"></jsp:include>
