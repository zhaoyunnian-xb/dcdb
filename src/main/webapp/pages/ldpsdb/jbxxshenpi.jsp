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

    .half-width .layui-form-select {
        display: inline-block;
        width: 50%;
    }

    .half-width .half-input {
        display: inline-block;
        width: 48%;
    }

    #lcglDiv {
        padding: 0 15px;
    }

    .selectType {
        text-align: center;
    }

    .selectType .layui-btn {
        width: 200px;
        margin-top: 30px;
        height: 38px;
        line-height: 38px;
    }
    .ew-tree-table .ew-tree-icon{
        display: none;
    }
</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    领导批示督办
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
                    <input type="hidden" id="curname" value="${curname}">
                    <input type="hidden" id="isfenan" value="${isfa}">
                    <input type="hidden" id="currentPage" value="${currentPage}">
                    <input type="hidden" id="docPath">
                    <input type="hidden" id="htmlPath">
                    <input type="hidden" id="sffsfzr" value="">
                    <input type="hidden" id="fzlcbs" value="">
                    <input type="hidden" id="ymlx" value="${ymlx}">


                    <div style="float: right;height: 100%;display:flex;align-items:center;">
                        <span id="returnBtn"
                              class="layui-btn layui-btn-normal layui-btn-sm">返回</span>
                        <span id="lcglSpan"
                              class="layui-btn layui-btn-normal layui-btn-sm">流程查看</span>
                        <span class="layui-btn layui-btn-normal layui-btn-sm" id="printviewSpan">打印预览</span>
                        <span class="layui-btn layui-btn-normal layui-btn-sm"
                              id="exportSpan">导出文书</span>
                    </div>
                    <div style="display:none;float: right;top:5px;right:5px;letter-spacing: 0px;margin-right: 20px"
                         id="tsxxDiv"></div>
                </div>
                <div class="layui-card-body">
                    <div class="layui-row">
                        <div class="layui-col-md7 ">
                            <div class="layui-form">
                                <div class="layui-row">
                                    <div class="layui-col-md6">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">批示编号：</label>
                                            <div class="layui-input-block">
                                                <input type="text" name="wh" id="wh"
                                                       class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-col-md6">
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">承办期限：</label>
                                            <div class="layui-input-block half-wrapper">
                                                <select name="cbqx" lay-verify="cbqx" id="cbqx"
                                                        lay-filter='cbqx'>
                                                    <option value="">请选择</option>
                                                    <option value="20">20天</option>
                                                    <option value="30">30天</option>
                                                    <option value="60">60天</option>
                                                    <option value="50">定制天数</option>
                                                </select>
                                                <input type="text" name="nf" id="cbqxdz"
                                                       class="layui-input half-input"
                                                       disabled/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">被批示件名称：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="psjmc" id="psjmc"
                                                           class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">批示日期：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="psrq" id="psrq"
                                                           class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">收文日期：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="swrq" id="swrq"
                                                           class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">是否省级以上领导批示件：</label>
                                                <div class="layui-input-block">
                                                    <input type="radio" name="issjysldps"
                                                           value="yes" title="是"
                                                           lay-filter='sjysldps'/>
                                                    <input type="radio" name="issjysldps" value="no"
                                                           title="否" lay-filter='sjysldps' checked/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-row" style="display: none" id="sjldxx">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">省级以上领导：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="sjysldxx" id="sjysldxx"
                                                           class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-row" style="display: none" id="isshow">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">省级以上领导批示内容：</label>
                                                <div class="layui-input-block">
                                                <textarea name="psnr" id="sjyspsnr"
                                                          style="min-height: 152px;"
                                                          class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">省院领导批示内容：</label>
                                                <div class="layui-input-block">
                                                <textarea name="psnr" placeholder="请输入内容" id="psnr"
                                                          class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label">拟办意见：</label>
                                                <div class="layui-input-block">
                                                <textarea name="nbyj" placeholder="请输入内容" id="nbyj"
                                                          class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">批示类型：</label>
                                                <div class="layui-input-block">
                                                    <select name="pslx" lay-verify="required"
                                                            id="pslx">
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">办理类型：</label>
                                                <div class="layui-input-block">
                                                    <select name="bllx" lay-verify="required"
                                                            id="bllx">
                                                        <option value="">请选择</option>
                                                        <option value="归档">归档</option>
                                                        <%--<option value="编发要情">编发要情</option>--%>
                                                        <option value="一般性工作批示">一般性工作批示</option>
                                                        <option value="专项督办省院">专项督办省院</option>
                                                        <option value="专项督办市院">专项督办市院</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6" style="display: block;"
                                             id="isbfyqDiv">
                                            <div class="layui-form-item layui-form-text">
                                                <label class="layui-form-label"
                                                       style="width: 160px;">是否编发要情：</label>
                                                <div class="layui-input-block">
                                                    <input type="radio" name="isbfyq" value="是"
                                                           title="是" lay-filter='isbfyq'/>
                                                    <input type="radio" name="isbfyq" value="否"
                                                           title="否" lay-filter='isbfyq' checked/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">上传扫描件：</label>
                                                <div class="layui-input-block">
                                                    <button type="button"
                                                            class="layui-btn layui-btn-primary ycBtn"
                                                            id="test1">
                                                        <i class="layui-icon layui-icon-upload"></i>上传文件
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6" id="zxdbhdiv">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">专项督办号：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="zxdbh" id="zxdbh"
                                                           class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md12">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">上传列表：</label>
                                                <div class="layui-input-block">
                                                    <ul class="files-wrapper" id="uploadFiles">
                                                    </ul>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="layui-row">
                                        <div class="layui-col-md6" id="cbbmdiv">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">承办部门：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="cbbm" id="cbbm"
                                                           placeholder="请输入" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6" id="fkdhdiv">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">反馈单号 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="fkdh" id="fkdh"
                                                           class="layui-input"/>
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
                                                          class="layui-textarea" style="min-height: 200px;"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%--        <div class="layui-row">
                                                <div class="layui-col-md6">
                                                    <div class="layui-form-item">
                                                        <label class="layui-form-label">联系人：</label>
                                                        <div class="layui-input-block">
                                                            <input type="text" name="lxr" id="lxr"
                                                                   class="layui-input"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="layui-col-md6">
                                                    <div class="layui-form-item">
                                                        <label class="layui-form-label">联系电话：</label>
                                                        <div class="layui-input-block">
                                                            <input type="text" name="lxdh" id="lxdh"
                                                                   class="layui-input"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>--%>

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
                                                           lay-verify="required" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="sprq" id="sprq"
                                                           required
                                                           lay-verify="required" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="spyj" id="spyj"
                                                           required
                                                           lay-verify="required" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item layui-form-text"
                                                 style="display: none">
                                                <label class="layui-form-label">审批备注 ：</label>
                                                <div class="layui-input-block">
                                                    <textarea name="spbz" id="spbz"
                                                              class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-colla-item">
                                        <h2 class="layui-colla-title">院领导</h2>
                                        <div class="layui-colla-content layui-show">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="yldsp" id="yldsp_spr"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="yldsp" id="yldsp_sprq"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="yldsp" id="yldsp_spyj"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item layui-form-text"
                                                 style="display: none">
                                                <label class="layui-form-label">审批备注 ：</label>
                                                <div class="layui-input-block">
                                                    <textarea name="yldsp" id="yldsp_spbz"
                                                              placeholder=""
                                                              class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-colla-item">
                                        <h2 class="layui-colla-title">部门负责人</h2>
                                        <div class="layui-colla-content layui-show">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bmfzrsp"
                                                           id="bmfzrsp_spr" required
                                                           lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bmfzrsp"
                                                           id="bmfzrsp_sprq" required
                                                           lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bmfzrsp"
                                                           id="bmfzrsp_spyj" required
                                                           lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item layui-form-text"
                                                 style="display: none">
                                                <label class="layui-form-label">审批备注 ：</label>
                                                <div class="layui-input-block">
                                                    <textarea name="bmfzrsp" id="bmfzrsp_spbz"
                                                              placeholder=""
                                                              class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="layui-colla-item">
                                        <h2 class="layui-colla-title">督查审核人</h2>
                                        <div class="layui-colla-content layui-show">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审核人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="dbsfhr" id="dbsfhr_spr"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审核日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="dbsfhr"
                                                           id="dbsfhr_sprq" required
                                                           lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">拟办意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="dbsfhr"
                                                           id="dbsfhr_spyj" required
                                                           lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-colla-item">
                                        <h2 class="layui-colla-title">督查复核人</h2>
                                        <div class="layui-colla-content layui-show">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">复核人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="dscsp" id="dscsp_spr"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">复核日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="dscsp" id="dscsp_sprq"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">拟办意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="dscsp" id="dscsp_spyj"
                                                           required lay-verify="required"
                                                           placeholder="" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item layui-form-text"
                                                 style="display: none">
                                                <label class="layui-form-label">审批备注 ：</label>
                                                <div class="layui-input-block">
                                                    <textarea name="dscsp" id="dscsp_spbz"
                                                              placeholder=""
                                                              class="layui-textarea"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </div>

                        </div>
                        <div class="layui-col-md12">
                            <hr>
                        </div>
                        <div class="layui-col-md12">
                            <div class="btn-wrapper " id="czspan">
                                <div class="layui-col-md7">
                                    <span class="layui-btn layui-btn-normal ycBtn"
                                          onclick="save(1)">保存</span>
                                    <span class="layui-btn layui-btn-primary" id="close">关闭</span>
                                </div>
                                <div class="layui-col-md4 layui-col-md-offset1">
                                    <%-- <span class="layui-btn layui-btn-primary ycBtn" id="yulan">导出预览</span>--%>
                                    <span class="layui-btn layui-btn-normal ycBtn" id="submitFlow">提交</span>
                                    <span class="layui-btn layui-btn-normal ycBtn"
                                          id="submitFlowFzr">发送部门负责人</span>
                                    <span class="layui-btn layui-btn-primary ycBtn" id="rebackFlow">退回</span>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<div class="table-wrapper" id="lcglDiv" style="display: none">
    <table class="layui-table" id="lcdgTable" lay-filter="lcdgTable"></table>
</div>

<div class="selectType" id="selectTypeNq" style="display: none;text-align: center">
    <div>
        <div class="layui-btn-primary layui-btn " onclick="selectStage('CBR')">分办(发送承办人)</div>
    </div>
    <div>
        <div class="layui-btn-primary layui-btn" onclick="selectStage('FZR')">分办(发送部门负责人)</div>
    </div>
    <div>
        <div class="layui-btn-primary layui-btn " onclick="selectStage('FZR')">办理反馈(发送部门负责人)</div>
    </div>
</div>
<div class="selectType" id="selectTypeFzr" style="display: none;text-align: center;">
    <div>
        <div class="layui-btn-primary layui-btn" onclick="selectStage('DCS')">审核通过(发送督查室)</div>
    </div>
    <div>
        <div class="layui-btn-primary layui-btn" onclick="selectStage('CBR')">交办(发送承办人)</div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlow.js"
        language="javascript"></script>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>
<script>
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
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        laydate.render({
            elem: '#psrq', //指定元素
            format: 'yyyy年MM月dd日',
            //value: $("#jyhcxrq").val() != ""?$("#jyhcxrq").val():new Date(),
            //isInitValue: true
        });
        laydate.render({
            elem: '#swrq', //指定元素
            format: 'yyyy年MM月dd日',
            //value: $("#jyhcxrq").val() != ""?$("#jyhcxrq").val():new Date(),
            //isInitValue: true
        });
    })
    var form = layui.form;
    var element = layui.element;
    var upload = layui.upload;
    $(function () {
        //审批意见、办理情况查询
        querySpyj();
        var parms = {
            url: '/flow/creatWs.do',
            data: {
                'id': $('#idyc').val() || '',
                'bllx': $('#bllxyc').val() || '',
                'wh': $('#whyc').val() || '',
                'lclx': $('#lclxyc').val() || '',
                'spbz': $("#blqk").val() || ''
            },
            success: function (data) {
                if (data.code == '1') {
                    var docPath = "";
                    var htmlPath = "";
                    $.each(data.data, function (i, item) {
                        docPath = item.WJLJ + item.WJMC;
                        htmlPath = "${ctx}/" + item.WJLJ + item.WJMC.split(".")[0] + ".html";

                    })
                    $("#docPath").val(docPath);
                    $("#htmlPath").val(htmlPath);

                } else if (data.code == '3') {
                    $("#printviewSpan").css("display", "none");
                    $("#exportSpan").css("display", "none");
                } else {
                    layer.msg(data.msg, {icon: data.code, offset: ['250px'], time: 1000});
                }

            }
        };
        //监听是否省级以上领导批示件的单选按钮事件
        form.on('radio(sjysldps)', function (obj) {
            if ($(this).val() == 'yes') {
                $("#isshow").css("display", "block");
                $("#sjldxx").css("display", "block");
            } else {
                $("#isshow").css("display", "none");
                $("#sjldxx").css("display", "none");
            }
        });

        ajax(parms)
        //查询配置 批示类型
        selectPeizhiData();
        var newwh = $("#whyc").val();
        var nodeId = $("#nodeidyc").val();
        var isReadOnly = $("#isReadOnly").val();
        if (newwh != null && newwh != '') {
            $.ajax({
                url: "/jbxx/jbxxdetail.do",
                type: "post",
                async: false,
                data: {"wh": newwh},
                dataType: "json",
                success: function (jbxx) {
                    if (jbxx != null) {
                        $("#wh").val(jbxx.wh == null ? "" : jbxx.wh);
                        $("#wh").attr("disabled", "disabled");
                        if (jbxx.cbqx != '20' && jbxx.cbqx != '30' && jbxx.cbqx != '60' && jbxx.cbqx != ''
                            && jbxx.cbqx != null) {
                            $("#cbqx").find("option[value = 50]").attr("selected", "selected");
                            $("#cbqxdz").val(jbxx.cbqx);
                        } else {
                            $("#cbqx").find("option[value = '" + jbxx.cbqx + "']").attr("selected", "selected");
                        }
                        $("#psjmc").val(jbxx.psjmc == null ? "" : jbxx.psjmc);
                        $("#psrq").val(jbxx.psrq == null ? "" : jbxx.psrq);
                        $("#swrq").val(jbxx.swrq == null ? "" : jbxx.swrq);
                        $("#psnr").val(jbxx.psnr == null ? "" : jbxx.psnr);
                        $("#nbyj").val(jbxx.nbyj == null ? "" : jbxx.nbyj);
                        $("#pslx").find("option[value = '" + jbxx.pslx + "']").attr("selected", "selected");
                        $("#bllx").find("option[value = '" + jbxx.bllx + "']").attr("selected", "selected");
                        if (jbxx.bllx == '归档' || jbxx.bllx == '编发要情') {
                            $("#zxdbhdiv").css("display", "none");
                            $("#cbbmdiv").css("display", "none");
                            $("#fkdhdiv").css("display", "none");
                        } else if (jbxx.bllx == '一般性工作批示') {
                            $("#cbbmdiv").css("display", "block");
                            $("#fkdhdiv").css("display", "block");
                            $("#zxdbhdiv").css("display", "none");
                        } else if (jbxx.bllx == '专项督办省院' || jbxx.bllx == '专项督办市院') {
                            $("#zxdbhdiv").css("display", "block");
                            $("#cbbmdiv").css("display", "block");
                            $("#fkdhdiv").css("display", "none");
                        } else {
                            $("#zxdbhdiv").css("display", "block");
                            $("#cbbmdiv").css("display", "block");
                            $("#fkdhdiv").css("display", "block");
                        }
                        $("#zxdbh").val(jbxx.zxdbh == null ? "" : jbxx.zxdbh);
                        var cbbmselect = 'dd[lay-value=' + jbxx.cbbm + ']';
                        $("#cbbm").val(jbxx.cbbmmc == null ? "" : jbxx.cbbmmc);
                        $("#cbbm").attr("cbbmid", jbxx.cbbm);
                        $("#fkdh").val(jbxx.fkdh == null ? "" : jbxx.fkdh);
                        $("#lxr").val(jbxx.lxr == null ? "" : jbxx.lxr);
                        $("#lxdh").val(jbxx.lxdh == null ? "" : jbxx.lxdh);
                        $("#spr").val(jbxx.spr == null ? "" : jbxx.spr);
                        $("#sprq").val(jbxx.sprq == null ? "" : jbxx.sprq);
                        $("#spyj").val(jbxx.spyj == null ? "" : jbxx.spyj);
                        $("#spbz").val(jbxx.spbz == null ? "" : jbxx.spbz);
                        $("input:radio[name='issjysldps'][value=" + jbxx.issjldpsj + "]").attr("checked", true);
                        if (!(nodeId == "0101" || nodeId == "01011")) {
                            $("input:radio[name='issjysldps']").attr("disabled", "disabled");
                        }
                        if (jbxx.issjldpsj == 'yes') {
                            if (!(nodeId == "0101" || nodeId == "01011")) {
                                $("#sjysldxx").val(jbxx.sjysldxx).attr("disabled", "disabled");
                                $("#sjyspsnr").val(jbxx.sysjldpsnr).attr("disabled", "disabled");
                            } else {
                                $("#sjysldxx").val(jbxx.sjysldxx).attr("disabled", "none");
                                $("#sjyspsnr").val(jbxx.sysjldpsnr).attr("disabled", "none");
                            }
                            $("#isshow").css("display", "block");
                            $("#sjldxx").css("display", "block");
                        }
                        $("input:radio[name='isbfyq'][value=" + jbxx.isbfyq + "]").attr("checked", true);
                        $("input:radio[name='isbfyq']").attr("disabled", "disabled");
                        var bllx = $("#bllx").val();
                        // if (bllx == '一般性工作批示') {
                        //     $("#isbfyqDiv").css("display", "block");
                        // }
                        readOnlyData();
                        selectUploadFiles($("#idyc").val(), $("#bllxyc").val());
                    }

                }
            })
        } else {
            $.ajax({
                url: "/jbxx/selectwh.do",
                type: "post",
                async: false,
                //data: {"wh":newwh},
                dataType: "json",
                success: function (result) {
                    if (result != null) {
                        $("#wh").val(result.wh);
                        $("#wh").attr("disabled", "disabled");
                    }
                }
            })
        }
        //执行实例
        form.render();
        form.on('select(cbqx)', function (data) {
            if (data.value == '50') {
                $('#cbqxdz').removeAttr("disabled");
                $('#cbqxdz').blur(function () {
                    var reg = /^[0-9]*$/;
                    if (!reg.test($('#cbqxdz').val())) {
                        layer.msg("请输入数字", {icon: 0, offset: ['255px', '500px'], time: 1000});
                        $('#cbqxdz').val("");
                    }
                })
            } else {
                $('#cbqxdz').attr("disabled", "disabled");
                $('#cbqxdz').val("");
            }

        })
        element.render();
        var uploadInst = upload.render({
            elem: '#test1', //绑定元素,
            url: '/jdtb/filesUploadDcdb.do', //上传接口
            data: {
                  bmrwid: function () {
                    var id = $("#idyc").val();
                    return id;
                  },
                  ywtype: function () {
                    var id = $("#bllx").val();
                    return id;
                  },
                  nodeId: function () {
                    var nodeidyc = $("#nodeidyc").val();
                    return nodeidyc;
                  },
            },
            accept: 'file',
            before: function () {

            },
            done: function (res) {
              //上传完毕回调
              var id = $("#idyc").val();
              var ywtype = $("#bllxyc").val();
              var nodeId = $("#nodeidyc").val();
              selectUploadFiles(id,ywtype);

            },
            error: function () {
                //请求异常回调
            }
        });

        $("#submitFlow").click(function () {
            //是否发送部门负责人
            $("#sffsfzr").val("");
            save(2);
        });

        $("#submitFlowFzr").click(function () {
            // //是否发送部门负责人
            $("#sffsfzr").val("1");
            save(2);
        });

        $("#rebackFlow").click(function () {
            save(3);
        });

        $("#close").click(function () {
            //跳转待办页
            window.location.href = "/db/index.do?lclx=lzps";
        });

        //初始化按钮
        initBtn();
        //初始化审批意见签名和日期
        initQmSj();
        //审批意见的隐藏和不可编辑
        spyjHideAndDis();

        var zd = $("#isReadOnly").val();
        //当前节点的提示信息
        if (zd == '0') {
            $("#tsxxDiv").show();
            tsxx();
        }

    })

    //保存
    function save(type) {
        var dbId = $("#idyc").val();
        var wh = $("#wh").val();
        var cbqx = $("#cbqx").val();
        if (cbqx == '50') {
            cbqx = $("#cbqxdz").val();
        }
        var psjmc = $("#psjmc").val();
        var psrq = $("#psrq").val();
        var swrq = $("#swrq").val();
        var psnr = $("#psnr").val();
        var nbyj = $("#nbyj").val();
        var pslx = $("#pslx").val();
        var bllx = $("#bllx").val();
        var zxdbh = $("#zxdbh").val();
        var cbbm = $("#cbbm").attr("cbbmid");
        var fkdh = $("#fkdh").val();
        var blqk = $("#blqk").val();
        var lxr = $("#lxr").val();
        var lxdh = $("#lxdh").val();
        var cbbmmc = $("#cbbm").val();
        var nodeId = $("#nodeidyc").val();
        var nodeName = $("#nodenameyc").val();
        var spr = $("#spr").val();
        var sprq = $("#sprq").val();
        var spyj = $("#spyj").val();
        var spbz = $("#spbz").val();
        var issjldpsj = $("input:radio[name='issjysldps']:checked").val(); //是否上级领导批示件
        var sjysldxx = $("#sjysldxx").val(); //省院上级领导姓名
        var sysjldpsnr = $("#sjyspsnr").val(); //省院上级领导批示内容
        var isbfyq = $("input:radio[name='isbfyq']:checked").val(); //是否编发要情

        if (nodeId == '') {
            nodeId = '0101';
        }
        if (nodeName == '') {
            nodeName = '收件登记';
        }
        if (type == 2) {
            if (wh == "" || wh == null) {
                layer.msg("批示编号不能为空", {icon: 0, time: 1000});
                return;
            }
            if (cbqx == "" || cbqx == null) {
                layer.msg("承办期限不能为空", {icon: 0, time: 1000});
                return;
            }
            if (psjmc == "" || psjmc == null) {
                layer.msg("被批示件名称不能为空", {icon: 0, time: 1000});
                return;
            }
            if (psrq == "" || psrq == null) {
                layer.msg("批示日期不能为空", {icon: 0, time: 1000});
                return;
            }
            if (swrq == "" || swrq == null) {
                layer.msg("收文日期不能为空", {icon: 0, time: 1000});
                return;
            }

            if (issjldpsj == 'yes') {
                if (sjysldxx == null || sjysldxx == '') {
                    layer.msg("省级以上领导不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
                    return;
                }
                if (sysjldpsnr == null || sysjldpsnr == '') {
                    layer.msg("省级领导以上批示内容不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
                    return;
                }
            }

            if (psnr == "" || psnr == null) {
                layer.msg("省院领导批示内容不能为空", {icon: 0, time: 1000});
                return;
            }
            if (nbyj == "" || nbyj == null) {
                layer.msg("拟办意见不能为空", {icon: 0, time: 1000});
                return;
            }
            if (pslx == "" || pslx == null) {
                layer.msg("批示类型不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
                return;
            }
            if (bllx == "" || bllx == null) {
                layer.msg("办理类型不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
                return;
            }
        }
        var param = {};
        param.dbId = dbId;
        param.wh = wh;
        param.cbqx = cbqx;
        param.psjmc = psjmc;
        param.psrq = psrq;
        param.swrq = swrq;
        param.psnr = psnr;
        param.nbyj = nbyj;
        param.pslx = pslx;
        param.bllx = bllx;
        param.zxdbh = zxdbh;
        param.fkdh = fkdh;
        param.blqk = blqk;
        param.lxr = lxr;
        param.lxdh = lxdh;
        param.cbbmmc = cbbmmc;
        param.cbbm = cbbm;
        param.type = type;
        param.nodeId = nodeId;
        param.nodeName = nodeName;
        param.spr = spr;
        param.sprq = sprq;
        param.spyj = spyj;
        param.spbz = spbz;
        param.issjldpsj = issjldpsj;
        param.sjysldxx = sjysldxx;
        param.sysjldpsnr = sysjldpsnr;
        param.isbfyq = isbfyq;
        $.ajax({
            url: "/jbxx/jbxxsave.do",
            type: "post",
            async: false,
            data: param,
            dataType: "json",
            success: function (result) {
                if (type == 1) {
                    saveSpyj(); //保存意见
                    layer.msg(result.msg, {icon: result.code, offset: ['250px'], time: 1000});
                } else {
                    if (result.code == 1) {

                        saveSpyj();//保存意见

                        if (type == 2) {
                            //提交流程
                            showStage();

                        } else if (type == 3) {
                            reback();//退回流程
                        }

                    } else {
                        layer.msg(result.msg, {icon: result.code, time: 1000});
                    }
                }
            }
        })

    }

    //承办部门选择
    /*$('#cbbm').on('click',function(){
        var cbbmhx = huixian("cbbm",$("#cbbm").attr("cbbmid") ||'') ;
        var layer = layui.layer;
        layer.open({
            type: 2,
            title: '请选择承办部门',
            content: '/pages/eleTree.jsp?isQryAll=false&isEcho=true&echoIds='+cbbmhx,
            offset:['30px'],
            area: ['650px', '80%'],
            scrollbar: false,
            btn: ['确认'],
            yes:function(index,layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getCurrentArr();
                var str = "";
                var str2 = "";
                $.each(a,function (i,item) {
                    var depid = item.id;
                    var depname = item.label;
                    str += depid+","
                    str2 += depname+",";
                })
                str = str.substr(0,str.length-1);
                str2 = str2.substr(0,str2.length-1);
                $("#cbbm").val(str2);
                $("#cbbm").attr("cbbmid",str);
                layer.close(index);
            }
        })
    })*/
    $('#cbbm').on('click', function () {
        var cbbmhx = huixian("cbbm", $("#cbbm").attr("cbbmid") || '');
        var cbbmmchx = $("#cbbm").val();
        layer.open({
            type: 2,
            title: '请选择承办部门',
            content: '/pages/dcdblc/transforEle_bm.jsp?isMul=true&cbbmhx=' + cbbmhx + '&cbbmmchx='
                + cbbmmchx,
            area: ['60%', '80%'],
            offset: ['30px'],
            scrollbar: false,
            btn: ['确认'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var a = iframeWin.getPersonArr();
                var str = "";
                var str2 = "";
                var userid = "";
                $.each(a, function (i, item) {
                    var depid = item.selfid;
                    var depname = item.selfname;
                    str += depid + ","
                    str2 += depname + ",";
                })
                str = str.substr(0, str.length - 1);
                str2 = str2.substr(0, str2.length - 1);
                $("#cbbm").val(str2);
                $("#cbbm").attr("cbbmid", str);
                layer.close(index);
            }
        })
    })

    //回显方法
    function huixian(id, str) {
        var a = str.split(",");
        var b = $("#" + id).val() || '';
        var str1 = "";
        if (b != null && b != "") {
            $.each(a, function (i, item) {
                str1 += a[i] + ",";
            });
        }
        return str1;
    }

    function selectUploadFiles(id, ywtype) {
        $.ajax({
            url: "/jbxx/selectUploadFiles.do",
            type: "post",
            // async: false,
          data: {"id": id, "ywtype": ywtype},
            dataType: "json",
            success: function (result) {
                var str = "";
                if (result != null) {
                    $.each(result, function (i, item) {
                        //str+='<li class=\"layui-col-md4\">'+item.filename+'</li>';
                        var re = "";
                        re = encodeURI("/jdtb/downLoadFileDcdb.do?id=" + item.id + "&ywtype=" + item.ywtype);
                        str += '<li><a href = ' + re + '>' + item.filename + '</a></li>';
                        // str+='<li><a href=>' + item.filename + '</a></li>';
                    })
                    $("#uploadFiles").empty().html(str);
                }
            }
        })
    }

    /*function  selectUploadFiles(id,ywtype){
        $.ajax({
            url: "/jbxx/selectUploadFiles.do",
            type: "post",
            // async: false,
            data: {"id": id, "ywtype": ywtype},
            dataType: "json",
            success: function (result) {
                var str = "";
                if(result != null ){
                    $.each(result,function(i,item){
                        //str+='<li class=\"layui-col-md4\">'+item.filename+'</li>';
                        str+='<li><a href="/jdtb/downLoadFileDcdb.do?id=' + item.id + '&ywtype=' + item.ywtype + '">' + item.filename + '</a></li>';
                    })
                    $("#uploadFiles").empty().html(str);
                }
            }
        })
    }*/
    function readOnlyData() {
        $("#bllx").attr("disabled", "disabled");

        var nodeId = $("#nodeidyc").val();
        if (!(nodeId == "0101" || nodeId == "01011"||nodeId =="0102")) {
            $("#zxdbh").attr("disabled", "disabled");
            $("#fkdh").attr("disabled", "disabled");
            $("#cbqx").attr("disabled", "disabled");
            $("#cbqxdz").attr("disabled", "disabled");
            $("#psjmc").attr("disabled", "disabled");
            $("#pslx").attr("disabled", "disabled");

            $("#cbbm").attr("disabled", "disabled");

            $("#psrq").attr("disabled", "disabled");
            $("#swrq").attr("disabled", "disabled");
            $("#psnr").attr("disabled", "disabled");
            $("#nbyj").attr("disabled", "disabled");
        }
    }

    function selectPeizhiData() {
        var type = "PSLX";
        $.ajax({
            url: "/lb/selectPeizhiData.do",
            type: "post",
            async: false,
            data: {"ptype": type},
            dataType: "json",
            success: function (result) {
                if (result.data.length > 0) {
                    $("#pslx").empty();
                    $("#pslx").append("<option value=" + '' + ">" + '请选择' + "</option>");
                    for (var i = 0; i < result.data.length; i++) {
                        $("#pslx").append(
                            "<option value=" + result.data[i].lbmc + ">" + result.data[i].lbmc + "</option>");
                    }
                }
            }
        })
    }

    function tsxx() {
        var nodeid = $("#nodeidyc").val();
        var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();
        $.ajax({
            url: "/flow/tsxx.do",
            type: "post",
            async: false,
            data: {"nodeId": nodeid, "ywtype": ywtype, "sftg": "1"},
            dataType: "json",
            success: function (result) {
                var nodeName = result.data[0].nodeName;
                var nextName = "";

                for (var i in result.data) {
                    nextName += " / " + result.data[i].nextName;
                }
                nextName = nextName.slice(2);
                var html = "<span style='color:#666;font-weight: normal;'> 当前节点： <strong style='color:#222;'>"
                    + nodeName + "；</strong> 下一节点： <strong style='color:#222;'>"
                    + nextName + "；</strong></span>";
                $("#tsxxDiv").html(html);
            }
        })
    }

    // 返回按钮
    $('#returnBtn').on('click', function () {
        var currentPage = $('#currentPage').val()

        var ymlx = $("#ymlx").val()
        if (ymlx == 'db'){
            window.location.href = '/db/index.do?lclx=lzps&currentPage=' + currentPage;
        } else if (ymlx == 'yb'){
            window.location.href = '/yb/index.do?lclx=lzps&currentPage=' + currentPage;
        }else if (ymlx == 'bj'){
            window.location.href = '/db/gdIndex.do?lclx=lzps&currentPage=' + currentPage;
        }
    })
    // 引入treeTable 插件
    layui.config({
        base: '/static/plugin/layui-v2.4.3/'
    }).extend({
        treeTable: 'treeTable/treeTable'
    }).use(['treeTable'], function () {
        var treeTable = layui.treeTable

    })

    $('#lcglSpan').on('click', function () {
        var ywtype = $("#bllxyc").val() == "" ? $('#bllx option:selected').val() : $("#bllxyc").val();
        var id = $("#idyc").val() == "" ? $("#newId").val() : $("#idyc").val();
        var lclxyc = $("#lclxyc").val();
        var wh = $("#whyc").val() == "" ? $("#wh").val() : $("#whyc").val();

        layui.use(['treeTable'], function () {
                var $ = layui.jquery;
                var treeTable = layui.treeTable;
                var data = []
                treeTable.render({
                    elem: '#lcdgTable',
                    tree: {
                        iconIndex: 1,
                        isPidData: false,
                        idName: 'nodeId' // tree id必须和数据中id保持一致
                    },
                    reqData: function (data, callback) {
                        $.get('/flow/getFlowList.do?limit=100&page=1&id=' + id + '&ywtype=' + ywtype, function (res) {
                            callback(res.data)
                        })
                    },
                    page: false, //开启分页
                    cols: [
                        {type: 'numbers', title:'序号'},
                        {
                            field: 'nodeName',
                            align: 'center',
                            title: '节点名称',
                            width: 160,
                            singleLine: false,
                            class: 'break-all'
                        },
                        {field: 'userName', align: 'center', title: '审批人', width: 120},
                        {field: 'approveTime', align: 'center', title: '审批时间'},
                    ]
                })
            }
        )
        var index1 = layer.open({
            type: 1,
            title: "流程查看",
            area: ["600px", "80%"],
            offset: ['20px'],
            content: $('#lcglDiv'),
            cancel: function (index1, layero) {
                layer.close(index1)
                $("#lcglDiv").hide();
                return false;
            }
        });
    })

    $('#printviewSpan').on('click', function () {
        var childWin = window.open("../../" + $("#htmlPath").val());
        childWin.onload = function () {
            childWin.window.print();
        }
    })

    $('#exportSpan').on('click', function () {
        window.location.href = encodeURI(
            "${ctx}/dcdb/downloadwsByPath.do?wspath=" + $("#docPath").val());
    })

    //展示可选择的阶段
    function showStage() {
        var nodeId = $("#nodeidyc").val();
        var ywtype = $("#bllxyc").val();
        if (nodeId == '0103' && (ywtype == '一般性工作批示' || ywtype == '专项督办省院')) {

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
        } else if ((nodeId == '0105' && (ywtype == '一般性工作批示' || ywtype == '专项督办省院'))) {

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

    //选择阶段的点击事件
    function selectStage(fzlcbs) {
        //分支流程标识
        $("#fzlcbs").val(fzlcbs);
        selectPerson("1");//提交流程

    }

</script>
<jsp:include page="../footer.jsp"></jsp:include>
