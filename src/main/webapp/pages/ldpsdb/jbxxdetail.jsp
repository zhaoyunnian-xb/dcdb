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
</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    收文登记
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
                                                    <input type="radio" name="issjysldps" value="no"
                                                           title="否" lay-filter='sjysldps' checked/>
                                                    <input type="radio" name="issjysldps"
                                                           value="yes" title="是"
                                                           lay-filter='sjysldps'/>
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
                                                <textarea name="psnr" id="psnr"
                                                          style="min-height: 152px;"
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
                                                <textarea name="nbyj" id="nbyj"
                                                          class="layui-textarea">此件复印件已转，请于X月X日前将贯彻落实检察长批示精神有关情况反馈办公室督查室。期间，如向检察长呈报专题报告，请在反馈单中注明呈送时间等有关情况。</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-row ">
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
                                                            id="bllx" lay-filter='bllx'>
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
                                                           class="layui-input"
                                                           value="鲁检督查[20**］***号"/>
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
                                        <div class="layui-col-md6" id="cbbmdiv">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">承办部门：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="cbbm" id="cbbm"
                                                           placeholder="请选择" autocomplete="off"
                                                           class="layui-input">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6" id="fkdhdiv">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">反馈单号 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="fkdh" id="fkdh"
                                                           class="layui-input" value="[20**]***号"/>
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
                                    <%--<div class="layui-row">
                                        <div class="layui-col-md6">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">联系人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="lxr" id="lxr"
                                                           class="layui-input" value="${curname}"/>
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
                                            <%--  <div class="layui-form-item layui-form-text">
                                                  <label class="layui-form-label">审批备注 ：</label>
                                                  <div class="layui-input-block">
                                                      <textarea name="spbz" id="spbz" class="layui-textarea"></textarea>
                                                  </div>
                                              </div>--%>
                                        </div>
                                    </div>

                                    <div class="layui-colla-item layui-hide">
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
                                                <label class="layui-form-label">审核意见 ：</label>
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
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md12">
                            <hr>
                        </div>

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
<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlow.js"
        language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/common.js" language="javascript"></script>
<script>
  var form = layui.form;
  var element = layui.element;
  var upload = layui.upload;
  layui.use('laydate', function () {
    var laydate = layui.laydate;
    laydate.render({
      elem: '#psrq', //指定元素
      format: 'yyyy年MM月dd日',
      value: $("#psrq").val() == "" ? new Date() : $("#psrq").val(),
      isInitValue: true,
      done: function (value, date, endDate) {
        $("#psrq").val(value);
      }
    });
    laydate.render({
      elem: '#swrq', //指定元素
      format: 'yyyy年MM月dd日',
      value: $("#swrq").val() == "" ? new Date() : $("#swrq").val(),
      isInitValue: true,
      done: function (value, date, endDate) {
        $("#swrq").val(value);
      }
    });
  })
  $(function () {
    selectPeizhiData();
    var nodeId = $("#nodeidyc").val();
    var newwh = $("#whyc").val();
    $("#spr").attr("disabled", "disabled");
    $("#sprq").attr("disabled", "disabled");
    $("#spyj").attr("disabled", "disabled");
    $("#spbz").attr("disabled", "disabled");
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
            var id = $("#wh").val();
            var ywtype = $("#bllx").val();
            if ($("#cbbmidyc").val() != null && (($("#cbbmidyc").val().indexOf(",")) == -1)) {
              id += $("#cbbmidyc").val();
            }
            $("input:radio[name='issjysldps'][value=" + jbxx.issjldpsj + "]").attr("checked", true);
            if (jbxx.issjldpsj == 'yes') {
              $("#sjysldxx").val(jbxx.sjysldxx);
              $("#sjyspsnr").val(jbxx.sysjldpsnr);
              $("#sjldxx").css("display", "block");
              $("#isshow").css("display", "block");

            }
            $("input:radio[name='isbfyq'][value=" + jbxx.isbfyq + "]").attr("checked", true);
            var bllx = $("#bllx").val();
            // if (bllx == '一般性工作批示') {
            //   $("#isbfyqDiv").css("display", "block");
            // }

            hideFkd($("#bllx").val());
            selectUploadFiles(id, ywtype);
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
            $('#cbqxdz').focus();
          }
        })

      } else {
        $('#cbqxdz').attr("disabled", "disabled");
        $('#cbqxdz').val("");
      }

    })

    element.render();

    form.on('select(bllx)', function (data) {
      hideFkd(data.value);

    })

    //隐藏反馈单、承办部门、督办单
    function hideFkd(value) {
      if (value == '归档' || value == '编发要情') {
        $("#zxdbhdiv").css("display", "none");
        $("#cbbmdiv").css("display", "none");
        $("#fkdhdiv").css("display", "none");

        // $("#isbfyqDiv").css("display", "none");
      } else if (value == '一般性工作批示') {
        $("#cbbmdiv").css("display", "block");
        $("#fkdhdiv").css("display", "block");
        $("#zxdbhdiv").css("display", "none");

        // $("#isbfyqDiv").css("display", "block");
      } else if (value == '专项督办省院' || value == '专项督办市院') {
        $("#zxdbhdiv").css("display", "block");
        $("#cbbmdiv").css("display", "block");
        $("#fkdhdiv").css("display", "none");

        // $("#isbfyqDiv").css("display", "none");
      } else {
        $("#zxdbhdiv").css("display", "block");
        $("#cbbmdiv").css("display", "block");
        $("#fkdhdiv").css("display", "block");

        // $("#isbfyqDiv").css("display", "none");
      }
    }

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

    var uploadInst = upload.render({
      elem: '#test1', //绑定元素,
      url: '/jdtb/filesUploadDcdb.do', //上传接口
      data: {
        bmrwid: function () {
          var  id = $("#idyc").val();
          return id;
        },
        ywtype: function () {
          var id = $("#bllx").val();
          return id;
        },

      },
      accept: 'file',
      multiple: false,
      before: function () {

      },
      done: function (res) {
        var id = $("#wh").val();
        var ywtype = $("#bllx").val();
        selectUploadFiles(id, ywtype);
        //上传完毕回调
      },
      error: function () {
        //请求异常回调
      }
    });

    $("#submitFlow").click(function () {
      save(2);//提交流程
    });
    $("#close").click(function () {
      //跳转待办页
      window.location.href = "/db/index.do?lclx=lzps";
    });

    //审批意见、办理情况查询
    querySpyj();
    //初始化审批意见签名和日期
    initQmSj();
    //审批意见的隐藏和不可编辑
    // spyjHideAndDis();
    if ($("#isth").val() == '1') {
      $(".layui-colla-item :eq(1)").removeClass("layui-hide");
      $(".layui-colla-item :eq(1)").find("input,textarea").attr('disabled', 'true');
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
    var lclx = $("#lclxyc").val();
    if (lclx == '' || lclx == null) {
      lclx = "ldps";
    }
    var spr = validate(nodeId, '0102') ? $("#spr").val() : '';
    var sprq = validate(nodeId, '0102') ? $("#sprq").val() : '';
    var spyj = validate(nodeId, '0102') ? $("#spyj").val() : '';
    var spbz = validate(nodeId, '0102') ? $("#spbz").val() : '';
    if (nodeId == '') {
      nodeId = '0101';
    }
    if (nodeName == '') {
      nodeName = '收件登记';
    }
    var issjldpsj = $("input:radio[name='issjysldps']:checked").val(); //是否上级领导批示件
    var sjysldxx = $("#sjysldxx").val(); //省院上级领导姓名
    var sysjldpsnr = $("#sjyspsnr").val(); //省院上级领导批示内容
    var isbfyq = $("input:radio[name='isbfyq']:checked").val(); //是否编发要情
    if (type == 2) {
      if (wh == "" || wh == null) {
        layer.msg("批示编号不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
        return;
      }
      if (cbqx == "" || cbqx == null) {
        layer.msg("承办期限不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
        return;
      }
      if (psjmc == "" || psjmc == null) {
        layer.msg("被批示件名称不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
        return;
      }
      if (psrq == "" || psrq == null) {
        layer.msg("批示日期不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
        return;
      }
      if (swrq == "" || swrq == null) {
        layer.msg("收文日期不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
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
        layer.msg("省院领导批示内容不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
        return;
      }
      if (nbyj == "" || nbyj == null) {
        layer.msg("拟办意见不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
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
      if (bllx != '归档' && bllx != '编发要情') {
        if (cbbmmc == "" || cbbmmc == null) {
          layer.msg("承办部门不能为空", {icon: 0, offset: ['255px', '500px'], time: 1000});
          return;
        }
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
    param.lclx = lclx;
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
        if (result.finalWh) {
          $("#wh").val(result.finalWh);
          $("#idyc").val(result.dbId);
        }
        if (type == 1) {
          saveSpyj(); //保存意见
          layer.msg(result.msg, {icon: result.code, offset: ['255px', '500px'], time: 1000});
        } else {
          if (result.code == 1) {
            saveSpyj(); //保存意见
            if (type == 2) {
              $("#newId").val(result.newId);
              selectPerson("1");//提交流程
            }
          } else {
            layer.msg(result.msg, {icon: result.code, offset: ['255px', '500px'], time: 1000});
          }
        }
      }
    })

  }

  //承办部门选择
  /* $('#cbbm').on('click',function(){
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
            var re = "";
            re = encodeURI("/jdtb/downLoadFileDcdb.do?id=" + item.id + "&ywtype=" + item.ywtype);
            str += '<li><a href = ' + re + '>' + item.filename + '</a></li>';
          })
          $("#uploadFiles").empty().html(str);
        }
      }
    })
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
</script>

<jsp:include page="../footer.jsp"></jsp:include>
