package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.model.DcdbSjjcsxDcsxnq;
import com.bm.index.model.DcdbSjjcsxSpyj;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbSjjcCbrService;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName DcdbSjjcCbController
 * @Description TODO
 * @Author daipx
 * @Date 2019/4/18 11:04
 * @Version 1.0
 **/
@Controller
@RequestMapping("sjjccb")
public class DcdbSjjcCbController {

  @Autowired
  private DcdbSjjcCbrService dcdbSjjcCbrService;

  private Gson mGson = new Gson();
  private static Log logger = LogFactory.getLog(DcdbSjjcCbController.class);

  /**
   * 承办页面：查询督查事项表格
   * @param se
   * @param dcdbSjjcsxDcsxnq
   * @return
   */
  @RequestMapping("/queryCbrDcsxList.do")
  @ResponseBody
  @PageHelperAnn
  public JSONObject queryCbrDcsxList(HttpSession se, DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq) {
    JSONObject jsonobject = new JSONObject();
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      dcdbSjjcsxDcsxnq.setLzuserid(user.getId());
      List<DcdbSjjcsxDcsxnq> dcdbs = dcdbSjjcCbrService.queryCbrDcsxList(dcdbSjjcsxDcsxnq);
      jsonobject.put("data", dcdbs);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonobject;
  }

  /**
   * 督查事项，填报校验
   * @param response
   * @param se
   * @param dcdbSjjcsxDcsxnq
   */
  @RequestMapping("/validateTb.do")
  @ResponseBody
  public void validateTb(HttpServletResponse response, HttpSession se,
      DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq) {
    Map<String, Object> mapRes = dcdbSjjcCbrService.validateTb(dcdbSjjcsxDcsxnq);
    responseJson(response, mapRes);
  }

  /**
   * 承办页面：往期查看
   * @param se
   * @param dcdbSjjcsxSpyj
   * @return
   */
  @RequestMapping("/queryCbrSpyjList.do")
  @ResponseBody
  @PageHelperAnn
  public JSONObject queryCbrSpyjList(HttpSession se,  DcdbSjjcsxSpyj dcdbSjjcsxSpyj) {
    JSONObject jsonobject = new JSONObject();
    try {
      dcdbSjjcsxSpyj.setPx("asc");
      List<DcdbSjjcsxSpyj> spyjs = dcdbSjjcCbrService.querySjjcSpyjWqck(dcdbSjjcsxSpyj);
      jsonobject.put("data", spyjs);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonobject;
  }

  /**
   * 承办页面:查询某条督查事项的最新意见
   * @param response
   * @param se
   * @param dcdbSjjcsxSpyj
   */
  @RequestMapping("/querySjjcSpyj.do")
  @ResponseBody
  public void querySjjcSpyj(HttpServletResponse response, HttpSession se,
      DcdbSjjcsxSpyj dcdbSjjcsxSpyj) {

    Map<String, Object> mapRes = new HashMap<>();
    dcdbSjjcsxSpyj.setPx("desc");
    List<DcdbSjjcsxSpyj> spyjs = dcdbSjjcCbrService.querySjjcSpyj(dcdbSjjcsxSpyj);
    if (CollectionUtils.isNotEmpty(spyjs)) {
      mapRes.put("data", spyjs.get(0));
    } else {
      mapRes.put("data", new DcdbSjjcsxSpyj());
    }
    responseJson(response, mapRes);
  }

  /**
   * 承办页面：保存承办描述，审批意见等
   * @param response
   * @param se
   * @param dcdbSjjcsxSpyj
   */
  @RequestMapping("/saveSjjcSpyj.do")
  @ResponseBody
  public void saveSjjcSpyj(HttpServletResponse response, HttpSession se,
      DcdbSjjcsxSpyj dcdbSjjcsxSpyj) {

    Map<String, Object> mapRes = dcdbSjjcCbrService.saveSjjcSpyj(dcdbSjjcsxSpyj);
    responseJson(response, mapRes);
  }

  /**
   * 承办页面：提交、退回功能
   * @param response
   * @param se
   * @param data
   * @param nodeid
   * @param nodename
   * @param ywtype
   * @param userid
   * @param sftg
   * @param cbbmid
   * @param cbbmmc
   * @param wh
   */
  @RequestMapping("/submitSjjc.do")
  @ResponseBody
  public void submitSjjc(HttpServletResponse response, HttpSession se,
      String data, String nodeid, String nodename, String ywtype, String userid, String sftg,
      String cbbmid, String cbbmmc,String wh,String dbid) {
    Map<String, String> map = new HashMap<>();
    map.put("data", data);
    map.put("nodeid", data);
    map.put("userid", userid);
    map.put("sftg", sftg);
    map.put("nodeid", nodeid);
    map.put("nodename", nodename);
    map.put("cbbmid", cbbmid);
    map.put("cbbmmc", cbbmmc);
    map.put("lclx", "zyjc");
    map.put("wh", wh);
    map.put("dbid", dbid);
    map.put("ywtype", ywtype);
    Map<String, Object> mapRes = dcdbSjjcCbrService.submitSjjc(se, map);
    responseJson(response, mapRes);
  }

  public void responseJson(HttpServletResponse response, Map<String, Object> mapRes) {
    try {
      /*设置编码格式，返回结果   */
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(mGson.toJson(mapRes));
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }
}
