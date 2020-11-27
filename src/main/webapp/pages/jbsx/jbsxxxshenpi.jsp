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
                    交办事项督办
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
                                <div class="layui-col-md12">
                                    <div class="layui-form">
                                        <div class="layui-row">
                                            <div class="layui-col-md6">
                                                <div class="layui-form-item">
                                                    <label class="layui-form-label">办理类型：</label>
                                                    <div class="layui-input-block">
                                                        <select name="bllx" lay-verify="required" id="bllx" lay-filter='bllx'>
                                                            <option value="">请选择</option>
                                                            <option value="省院交办">省院交办</option>
                                                            <option value="市院交办">市院交办</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="layui-col-md6">
                                                <div class="layui-form-item">
                                                    <label class="layui-form-label">督办单号：</label>
                                                    <div class="layui-input-block">
                                                        <input type="text" name="zxdbh" id="zxdbh" class="layui-input" value="[20**］***号"/>
                                                    </div>
                                                </div>
                                            </div>
											<div class="layui-col-md6">
												<div class="layui-form-item">
													<label class="layui-form-label">督查事项：</label>
													<div class="layui-input-block">
														<input type="text" name="dcsx" id="dcsx"
															class="layui-input" value="" />
													</div>
												</div>
											</div>
											<div class="layui-col-md6">
												<div class="layui-form-item">
													<label class="layui-form-label">督查缘由：</label>
													<div class="layui-input-block">
														<input type="text" name="dcyy" id="dcyy"
															class="layui-input" value="" />
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
                                                        <label class="layui-form-label">收文日期：</label>
                                                        <div class="layui-input-block">
                                                            <input type="text" name="swrq" id="swrq" class="layui-input"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="layui-row">
                                                <div class="layui-col-md12">
                                                    <div class="layui-form-item layui-form-text">
                                                        <label class="layui-form-label">领导批示：</label>
                                                        <div class="layui-input-block">
                                                <textarea name="psnr"  id="psnr"
                                                          class="layui-textarea">省检察院领导批示抄清:
           此件复印件已转，请于X月X日前将贯彻落实检察长批示精神有关情况反馈办公室督查室。期间，如向检察长呈报专题报告，请在反馈单中注明呈送时间等有关情况。

                                                                                                                                    办公室
                                                                                                                                    20XX年X月X日
</textarea>
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
                                                          class="layui-textarea"></textarea>
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
                                                <%--<div class="layui-col-md6">--%>
                                                    <%--<div class="layui-form-item">--%>
                                                        <%--<label class="layui-form-label">办理类型：</label>--%>
                                                        <%--<div class="layui-input-block">--%>
                                                            <%--<select name="bllx" lay-verify="required" id="bllx">--%>
                                                                <%--<option value="">请选择</option>--%>
                                                                <%--<option value="党组会督办">党组会督办</option>--%>
                                                                <%--<option value="检察长办公会督办">检察长办公会督办</option>--%>
                                                            <%--</select>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
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
                                    <div id="shenpiyj" >
                                    <div class="layui-colla-item">
                                        <h2 class="layui-colla-title">部门负责人</h2>
                                        <div class="layui-colla-content layui-show">
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批人：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bmfzrsp" id="bmfzrsp_spr" required lay-verify="required"
                                                           placeholder="" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批日期 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bmfzrsp" id="bmfzrsp_sprq" required lay-verify="required"
                                                           placeholder="" autocomplete="off" class="layui-input">
                                                </div>
                                            </div>
                                            <div class="layui-form-item">
                                                <label class="layui-form-label">审批意见 ：</label>
                                                <div class="layui-input-block">
                                                    <input type="text" name="bmfzrsp" id="bmfzrsp_spyj" required lay-verify="required"
                                                           placeholder="" autocomplete="off" class="layui-input">
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

<script type="text/javascript" src="${ctx}/common/js/dcdb/dcdbFlowJbsx.js" language="javascript"></script>
<script type="text/javascript" src="${ctx}/common/js/dcdb/jbsxxx.js" language="javascript"></script>
<script>
  $(function () {
      var parms = {
          url: '/flow/creatWs.do',
          data: {
              'id': $('#idyc').val() || '',
              'bllx': $('#bllxyc').val() || '',//办理类型
              'wh': $('#whyc').val() || '',//文号
              'lclx': $('#lclxyc').val() || '',//流程类型
              'spbz': $("#blqk").val() || ''
          },
          success: function (data) {
              debugger
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
      ajax(parms)


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
      window.location.href = "/db/index.do?lclx=jbsx";
    });
    tsxx();
      var zd = $("#isReadOnly").val();
      //当前节点的提示信息
      if (zd == '0') {
          $("#tsxxDiv").show();
          tsxx();
      }
    //初始化按钮
    initBtn();
    //审批意见、办理情况查询
    querySpyj();
    //初始化审批意见签名和日期
    initQmSj();
    //审批意见的隐藏和不可编辑
    spyjHideAndDis();
    var form = layui.form;
      form.on('select(bllx)',function (data) {
          /* if (data.value == '党组会督办') {
              $("#zxdbh").val("党组会 督〔20XX〕X号")
          }else if (data.value == '检察长办公会督办') {
              $("#zxdbh").val("检察长办公会 督〔20XX〕X号")
          } */
          $("#zxdbh").val("鲁检督查[20XX]X号")
      })

      // 返回按钮
      $('#returnBtn').on('click', function () {
          var currentPage = $('#currentPage').val()
          var ymlx = $("#ymlx").val()
          if (ymlx == 'db'){
              window.location.href = '/db/index.do?lclx=jbsx&currentPage=' + currentPage;
          } else if (ymlx == 'yb'){
              window.location.href = '/yb/index.do?lclx=jbsx&currentPage=' + currentPage;
          } else if (ymlx == 'bj'){
              window.location.href = '/db/gdIndex.do?lclx=jbsx&currentPage=' + currentPage;
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
                          {type: 'numbers'},
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

  //选择阶段的点击事件
  function selectStage(fzlcbs) {
      //分支流程标识
      $("#fzlcbs").val(fzlcbs);
      selectPerson("1");//提交流程

  }
  //查询当前节点和下一个节点
  function tsxx() {
      var nodeid = $("#nodeidyc").val();//节点
      var lctype = $("#lclxyc").val();//流程类型
      var bllx = $("#bllxyc").val();//办理类型
      $.ajax({
          url: "/flow/tsxx.do",
          type: "post",
          async: false,
          data: {"nodeId": nodeid, "lctype": lctype, "sftg": "1","ywtype":bllx},
          dataType: "json",
          success: function (result) {
              if (result.data ==""){
                  return
              }
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
</script>
<jsp:include page="../footer.jsp"></jsp:include>
