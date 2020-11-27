package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbNdrw;
import com.bm.index.model.DcdbSjjcsxDj;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbDbService;
import com.bm.index.util.HttpClientUitls;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/menhu")
@CrossOrigin("*")
public class MenhuController {

  @Autowired
  private DcdbDbService dbService;
  @Autowired
  private DcdbBmmbnrDao dcdbBmmbnrDao;



  /**
   * 督查督办-home页面数据
   */
  @RequestMapping("queryDbIndex")
  @ResponseBody
  public JSONObject queryDbIndex(HttpSession se, Model model) {
    JSONObject jsonObject = new JSONObject();
    UserEntity user = (UserEntity) se.getAttribute("User");

    DcdbDjlbDbParam dbParam = new DcdbDjlbDbParam();
    dbParam.setUserid(user.getId());
    dbParam.setLclx("lzps");
    List<DcdbDjlbDbParam> lzpsList = dbService.queryTable(dbParam);

    dbParam.setLclx("dzjb");
    List<DcdbDjlbDbParam> dzjbList = dbService.queryTable(dbParam);
    /**获取交办事项待办列表 ----start**/
    dbParam.setLclx("jbsx");
    List<DcdbDjlbDbParam> jbsxList = dbService.queryTable(dbParam);
    /**获取交办事项待办列表 ----end**/

    DcdbSjjcsxDj dcdbSjjcsxDj = new DcdbSjjcsxDj();
    dcdbSjjcsxDj.setUserid(user.getId());
    dcdbSjjcsxDj.setLclx("zyjc");
    List<DcdbSjjcsxDj> sjjcList = dbService.queryZyjcTable(dcdbSjjcsxDj);

    DcdbNdrw dcdbNdrw = new DcdbNdrw();
    dcdbNdrw.setCbrid(user.getId());
    dcdbNdrw.setIsBgs("no");
    //当登录人为办公室人员时
    String name = user.getName();

    List<DcdbNdrw> dcdbNdrws = new ArrayList<>();
    if ("罗艳".equals(name) || "王小玉".equals(name)) {
      dcdbNdrws = dcdbBmmbnrDao.queryBmdbListBgs(dcdbNdrw);
    }else{
      dcdbNdrws = dcdbBmmbnrDao.queryBmdbList(dcdbNdrw);
    }
    jsonObject.put("dataLdps", lzpsList.size() >= 6 ? lzpsList.subList(0, 6) : lzpsList);
    jsonObject.put("dataDzjb", dzjbList.size() >= 6 ? dzjbList.subList(0, 6) : dzjbList);
    jsonObject.put("dataSjjc", sjjcList.size() >= 6 ? sjjcList.subList(0, 6) : sjjcList);
    jsonObject.put("dataNdrw", dcdbNdrws.size() >= 6 ? dcdbNdrws.subList(0, 6) : dcdbNdrws);
    jsonObject.put("dataJbsx", jbsxList.size() >= 6 ? jbsxList.subList(0, 6) : jbsxList);//返回交办事项的待办数据
    return jsonObject;
  }



  /**
   * 查询个人待办列表
   */
  @RequestMapping("queryDbListByUsername")
  @ResponseBody
    public JSONObject queryDbList(HttpSession se, String username) {
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObjectData = new JSONObject();

    String code="200";

    String msg="查询成功";

    String userId="";

    List<UserEntity> users = dcdbBmmbnrDao.loginQueryByName(username);
    if (users != null && users.size() > 0) {
       userId = users.get(0).getId();
    }else{
       code="9999";
       msg="没有查询到相关人员,请联系管理员";

       jsonObject.put("code",code );
       jsonObject.put("msg",msg );
      return jsonObject;
    }

    DcdbDjlbDbParam dbParam = new DcdbDjlbDbParam();
    dbParam.setUserid(userId);
    dbParam.setLclx("lzps");
    List<DcdbDjlbDbParam> lzpsList = dbService.queryTable(dbParam);

    dbParam.setLclx("dzjb");
    List<DcdbDjlbDbParam> dzjbList = dbService.queryTable(dbParam);

    DcdbSjjcsxDj dcdbSjjcsxDj = new DcdbSjjcsxDj();
    dcdbSjjcsxDj.setUserid(userId);
    dcdbSjjcsxDj.setLclx("zyjc");
    List<DcdbSjjcsxDj> sjjcList = dbService.queryZyjcTable(dcdbSjjcsxDj);

    DcdbNdrw dcdbNdrw = new DcdbNdrw();
    dcdbNdrw.setCbrid(userId);
    dcdbNdrw.setIsBgs("no");

    //当登录人为办公室人员时
    List<DcdbNdrw> dcdbNdrws = new ArrayList<>();
    if ("luoyan".equals(username) || "wangxaioyu".equals(username)) {
      dcdbNdrws = dcdbBmmbnrDao.queryBmdbListBgs(dcdbNdrw);
    }else{
      dcdbNdrws = dcdbBmmbnrDao.queryBmdbList(dcdbNdrw);
    }

    jsonObjectData.put("dataLdps", lzpsList);
    jsonObjectData.put("dataDzjb", dzjbList);
    jsonObjectData.put("dataSjjc", sjjcList);
    jsonObjectData.put("dataNdrw", dcdbNdrws);

    jsonObject.put("code",code );
    jsonObject.put("msg",msg );
    jsonObject.put("data",jsonObjectData );
    return jsonObject;
  }

  /**
   * 点击明细跳转页面
   */
  @RequestMapping("toDetail")
  public String chakan(Model model,  HttpSession session,String info,String username) {
    JSONObject dataObject = JSONObject.parseObject(info);
    UserEntity loginUser = new UserEntity();
    List<UserEntity> users = dcdbBmmbnrDao.loginQueryByName(username);
    // session存放
    if (users != null && users.size() > 0) {
      loginUser = users.get(0);
      model.addAttribute("bmmc", users.get(0).getBmmc());
      model.addAttribute("username", users.get(0).getName());
    }
    session.setAttribute("User", loginUser);

    String nodeid = (String) dataObject.get("nodeId");

    String path = "";
    StringBuffer sb = new StringBuffer();
    if (StringUtils.isBlank(nodeid)) {
      sb.append("ndid=").append(dataObject.get("id")).append("&status=")
          .append(dataObject.get("czzt"))
          .append("&cbbmid=").append(dataObject.get("cbbmid"));
      path = "/jdtb/toPage.do?" + sb.toString();
    } else {
      sb.append("isReadOnly=0&id=").append(dataObject.get("id")).
          append("&wh=").append(dataObject.get("wh")).append("&bllx=")
          .append(dataObject.get("bllx"))
          .append("&nodeid=").append(dataObject.get("nodeId")).append("&nodename=")
          .append(dataObject.get("nodeName")).append("&cjr=").append(dataObject.get("cjr"))
          .append("&cbbmid=")
          .append(dataObject.get("cbbmid")).append("&cbbmmc=").append(dataObject.get("cbbmmc"))
          .append("&lclx=").append(dataObject.get("lclx")).append("&isfa=")
          .append(dataObject.get("isfa"));
      if ("0101".equals(nodeid)) {
        path = "/db/toxinjian.do?" + sb.toString();
      } else {
        path = "/db/toshenhe.do?" + sb.toString();
      }
    }

    model.addAttribute("path", path);
    model.addAttribute("flag", "mh");
    return "index";

  }

  /**
   * 点击更多，跳转页面
   */
  @RequestMapping("toMorePages")
  public String morePages(Model model,  HttpSession session,String info,String username) {
    UserEntity loginUser = new UserEntity();
    List<UserEntity> users = dcdbBmmbnrDao.loginQueryByName(username);
    // session存放
    if (users != null && users.size() > 0) {
      loginUser = users.get(0);
      model.addAttribute("bmmc", users.get(0).getBmmc());
      model.addAttribute("username", users.get(0).getName());
    }
    session.setAttribute("User", loginUser);

    String path = "";

    if("ndrw".equals(info)){
      //年度工作任务督办
      path="/pages/ndgzrw/bmrwlist.jsp";
      if ("luoyan".equals(username) || "wangxaioyu".equals(username)) {
        path="/pages/ndgzrw/bmrwlist_bgs.jsp";
      }

    }else if("ldps".equals(info)){
      //领导批示督办
      path="/db/index.do?lclx=lzps";
    }else if("dzjb".equals(info)){
      //党组检办督办
      path="/db/index.do?lclx=dzjb";
    }else if("sjjc".equals(info)){
      //上级重要决策督办
      path="/db/index.do?lclx=zyjc";
    }else if ("jbsx".equals(info)) {
      //交办事项
      path="/db/index.do?lclx=jbsx";
	}

    model.addAttribute("path", path);
    model.addAttribute("flag", "mh");
    return "index";
  }


}
