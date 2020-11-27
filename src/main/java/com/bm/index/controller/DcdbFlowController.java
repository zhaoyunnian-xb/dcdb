package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.DcdbFlowDao;
import com.bm.index.model.*;
import com.bm.index.service.DcdbFlowService;
import com.bm.index.service.DcdbSjjcCbrService;
import com.bm.index.service.FileService;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DcdbFlowController
 * @Description TODO
 * @Author daipx
 * @Date 2019/3/18 11:04
 * @Version 1.0
 **/
@Controller
@RequestMapping("flow")
public class DcdbFlowController {

  @Autowired
  private DcdbFlowService dcdbFlowService;
  @Autowired
  private FileService fileService;
  @Autowired
  private DcdbSjjcCbrService dcdbSjjcCbrService;
  @Autowired
  private DcdbFlowDao flowDao;

  private Gson mGson = new Gson();
  private final String bfyq = "编发要情";
  private final String gd = "归档";
  private final String ybx = "一般性工作批示";
  private final String syzxdb = "专项督办省院";

  private static Log logger = LogFactory.getLog(DcdbFlowController.class);


  /**
   * 流程提交、退回(领导批示、党组检办)
   */
  @RequestMapping("/submitFlowSelect.do")
  @ResponseBody
  public void submitFlowSelect(HttpSession se, HttpServletResponse response, DcdbLcjd dcdbLcjd) {
    Map<String, Object> mapRes = new HashMap<>();
    String ywtype = dcdbLcjd.getYwtype();
    String lctype = dcdbLcjd.getLctype();
    mapRes = dcdbFlowService.submitFlow(se, dcdbLcjd);
    responseJson(response, mapRes);

  }



  /**
   * 流程提交、退回(上级重要决策)
   */
  @RequestMapping("/submitFlowZyjc.do")
  @ResponseBody
  public void submitFlowZyjc(HttpSession se, HttpServletResponse response, DcdbLcjd dcdbLcjd) {
    Map<String, Object> mapRes = new HashMap<>();
    mapRes = dcdbFlowService.submitFlowZyjc(se, dcdbLcjd);
    responseJson(response, mapRes);

  }


  /**
   * 查询审批意见、办理情况
   */
  @RequestMapping("/querySpyj.do")
  @ResponseBody
  public void querySpyj(HttpSession se, HttpServletResponse response, DcdbSpyj spyj) {
    Map<String, Object> mapRes = new HashMap<>();
    String code = "1";
    try {
      String id = spyj.getId();
      if(id.contains(",")){
        String[] idArr = id.split(",");
        DcdbSpyj dcdbSpyj = new DcdbSpyj();
        StringBuffer sb = new StringBuffer();
        List<DcdbSpyj> dcdbSpyjs = new ArrayList<>();
        for (int i=0;i<idArr.length;i++){
          DcdbDjlbDb dcdbDjlbDb = flowDao.selectByPrimaryKey(idArr[i]);
          spyj.setId(idArr[i]);
          spyj.setSplx("blqk");
          dcdbSpyjs = dcdbFlowService.selectSpyj(spyj);
          for (DcdbSpyj d : dcdbSpyjs){
            sb.append(dcdbDjlbDb.getCbbmmc()).append(":");
            sb.append(d.getSpbz()).append("\n");
            dcdbSpyj = d;
          }
        }
        dcdbSpyj.setSpbz(sb.substring(0,sb.length()-1));
        dcdbSpyjs.clear();
        dcdbSpyjs.add(dcdbSpyj);
        mapRes.put("msg", dcdbSpyjs);
      }else {
        mapRes.put("msg", dcdbFlowService.selectSpyj(spyj));
      }
    } catch (Exception e) {
      e.printStackTrace();
      code = "2";
      mapRes.put("msg", e.getMessage());
    } finally {
      mapRes.put("code", code);
    }

    responseJson(response, mapRes);

  }

  /**
   * 审批意见、办理情况-保存
   */
  @RequestMapping("/saveSpyj.do")
  @ResponseBody
  public void saveSpyj(HttpSession se, HttpServletResponse response, String info) {

    Map<String, Object> mapRes = dcdbFlowService.saveSpyj(se, info);
    responseJson(response, mapRes);

  }

  /**
   * 生成文书
   */
  @RequestMapping("/creatWs.do")
  @ResponseBody
  public void creatWs(HttpServletResponse response, HttpServletRequest request, String id,
      String bllx, String wh, String lclx,String spbz) {
    String code = "1";
    String msg = "查询成功";
    //生成文书
    Map<String, String> mapws = new HashedMap();
    List<Map<String, String>> mapList = new ArrayList<>();
    mapws.put("nodeId", "1");
    mapws.put("dbid", id); //待办ID
    mapws.put("bllx", bllx); //办理类型
    mapws.put("wh", wh); //文号
    mapws.put("splx", "blqk"); //意见表审批类型
    mapws.put("spbz", spbz); //意见表审批类型
    //如果办理类型为归档、编发要请时，不生成文书
    if ("归档".equals(bllx)||"编发要情".equals(bllx)){
        code = "3";
    }else if ("一般性工作批示".equals(bllx)){
      mapws.put("wsbm", "反馈单"); //前台显示名称
      mapws.put("file", "山东省人民检察院领导批示件办理结果反馈单.doc"); //模板名称
      boolean flag2 = fileService.jcjyScws(request, mapws);
      if (flag2 == false) {
        code = "2";
        msg = "文书生成有误，请联系管理员";

      }
      mapList = fileService.downloadWsList(mapws);
      if (CollectionUtils.isEmpty(mapList)) {
        code = "2";
        msg = "没有查询到相关文书，请联系管理员";
      }
    }else if ("专项督办省院".equals(bllx)||"专项督办市院".equals(bllx)){
      mapws.put("wsbm", "督办单"); //前台显示名称
      mapws.put("file", "山东省人民检察院专项督查处理单.doc"); //模板名称
      boolean flag1 = fileService.jcjyScws(request, mapws);
      if (flag1 == false) {
        code = "2";
        msg = "文书生成有误，请联系管理员";
      }
      mapList = fileService.downloadWsList(mapws);
      if (CollectionUtils.isEmpty(mapList)) {
        code = "2";
        msg = "没有查询查相关文书，请联系管理员";
      }
    }else if("党组会督办".equals(bllx)){
      mapws.put("wsbm", "督办单"); //前台显示名称
      mapws.put("file", "山东省人民检察院党组会决定事项督办单.doc"); //模板名称
      boolean flag1 = fileService.jcjyScws(request, mapws);
      if (flag1 == false) {
        code = "2";
        msg = "文书生成有误，请联系管理员";
      }
      mapList = fileService.downloadWsList(mapws);
      if (CollectionUtils.isEmpty(mapList)) {
        code = "2";
        msg = "没有查询查相关文书，请联系管理员";
      }
    }else if("检察长办公会督办".equals(bllx)){
      mapws.put("wsbm", "督办单"); //前台显示名称
      mapws.put("file", "山东省人民检察院检察长办公会决定事项督办单.doc"); //模板名称
      boolean flag1 = fileService.jcjyScws(request, mapws);
      if (flag1 == false) {
        code = "2";
        msg = "文书生成有误，请联系管理员";
      }
      mapList = fileService.downloadWsList(mapws);
      if (CollectionUtils.isEmpty(mapList)) {
        code = "2";
        msg = "没有查询查相关文书，请联系管理员";
      }
    }else if("省院交办".equals(bllx)){//add
        mapws.put("wsbm", "督办单"); //前台显示名称
        mapws.put("file", "山东省人民检察院专项督查处理单(上级交办).doc"); //模板名称
        boolean flag1 = fileService.jcjyScws(request, mapws);
        if (flag1 == false) {
          code = "2";
          msg = "文书生成有误，请联系管理员";
        }
        mapList = fileService.downloadWsList(mapws);
        if (CollectionUtils.isEmpty(mapList)) {
          code = "2";
          msg = "没有查询查相关文书，请联系管理员";
        }
     }else if("市院交办".equals(bllx)){//add
    	  mapws.put("wsbm", "督办函"); //前台显示名称
          mapws.put("file", "山东省人民检察院办公室督办函.doc"); //模板名称
          boolean flag1 = fileService.jcjyScws(request, mapws);
          if (flag1 == false) {
            code = "2";
            msg = "文书生成有误，请联系管理员";
          }
          mapList = fileService.downloadWsList(mapws);
          if (CollectionUtils.isEmpty(mapList)) {
            code = "2";
            msg = "没有查询查相关文书，请联系管理员";
          }
     }

    Map<String, Object> mapRes = new HashedMap();
    mapRes.put("code", code);
    mapRes.put("msg", msg);
    mapRes.put("data", mapList);
    responseJson(response, mapRes);
  }

  /**
   * 查询生成文书
   */
  @RequestMapping("/queryWsList.do")
  @ResponseBody
  public void queryWsList(HttpServletResponse response, HttpServletRequest request, String id,
      String bllx, String wh, String lclx) {
    String code = "1";
    String msg = "查询成功";
    //生成文书
    Map<String, String> mapws = new HashedMap();
    mapws.put("dbid", id); //待办ID
    mapws.put("bllx", bllx); //办理类型
    mapws.put("wh", wh); //文号
    List<Map<String, String>> mapList = fileService.downloadWsList(mapws);
    if (CollectionUtils.isEmpty(mapList)) {
      code = "2";
      msg = "没有查询查相关文书，请联系管理员";
    }
    Map<String, Object> mapRes = new HashedMap();
    mapRes.put("code", code);
    mapRes.put("msg", msg);
    mapRes.put("data", mapList);
    responseJson(response, mapRes);
  }
  /**
   *  领导批示等审批页面：提示信息。【查询当前节点和下一个节点】
   */
  @RequestMapping("/tsxx.do")
  @ResponseBody
  public void tsxx(HttpServletResponse response, HttpServletRequest request, DcdbLcjd lcjd) {
    Map<String, Object> mapRes = new HashedMap();
    String lclx = lcjd.getLctype();
    //String bllx = lcjd.getYwtype();
    if ("dzjb".equals(lclx)) {
      lcjd.setYwtype("党组检办");
    } else if ("zyjc".equals(lclx)) {
      lcjd.setYwtype("上级决策");
    }else if ("jbsx".equals(lclx)) {
    	//不处理直接根据前台传递的办理类型去判断
	}
    List<DcdbLcjd> res = flowDao.selectLcjd(lcjd);
    mapRes.put("data", res);
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
