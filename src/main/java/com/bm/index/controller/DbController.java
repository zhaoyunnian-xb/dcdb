package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbDjlbYbParam;
import com.bm.index.model.DcdbJbxx;
import com.bm.index.model.DcdbSjjcsxDj;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbDbService;
import com.bm.index.service.DcdbJbxxService;
import com.bm.index.util.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 督查督办审批流程待办
 */
@Controller
@RequestMapping("/db")
public class DbController {

    private Gson mGson = new Gson();
    @Autowired
    private DcdbDbService dbService;
    @Autowired
    private DcdbJbxxService jbxxService;
    /**
     * 主页跳转方法
     */
    @RequestMapping("/index.do")
    public String index(@ModelAttribute("lclx") String lclx,HttpSession se,Model model,@ModelAttribute("currentPage")String currentPage ){
        UserEntity user = (UserEntity) se.getAttribute("User");
        String bmmc = user.getBmmc();
        model.addAttribute("bmmc",bmmc);
        model.addAttribute("name",user.getName());
        String url = "";
        //党组检办督办待办页面
        if("dzjb".equals(lclx)){
            url = "/dzjbdb/dcdbdzjbdb";
            //领导批示待办页面
        }else if("lzps".equals(lclx)){
            url = "/ldpsdb/dcdbdjlbdb";
            //上级重要决策督办待办页面
        }else if("zyjc".equals(lclx)){
            url = "/sjzyjcdb/dcdbzyjcdb";
        }else if ("jbsx".equals(lclx)) {
			//交办事项待办页面---20200414
        	url = "/jbsx/jbsxdb";
		}
        return url;
    }
    /**
     * 跳转基本信息页面
     */
    @RequestMapping("/toxinjian")
    public String toxinjian(String id, String wh, String bllx, String nodeid, String nodename,String isReadOnly,
                            String cjr,String cbbmid,String cbbmmc, String lclx,String isfa,Model model,String sfth){
        //点击第一次新建，id为空时，赋值为uuid
       /* if(StringUtils.isBlank(id)){
            id = UUIDUtils.getUUID();
        } */
        model.addAttribute("id",id);
        model.addAttribute("wh",wh);
        model.addAttribute("bllx",bllx);
        model.addAttribute("cjr",cjr);
        model.addAttribute("cbbmid",cbbmid);
        model.addAttribute("cbbmmc",cbbmmc);
        model.addAttribute("lclx",lclx);
        model.addAttribute("isfa",isfa);
        model.addAttribute("nodeid","0101");
        model.addAttribute("nodename","收文登记");
        model.addAttribute("isReadOnly",isReadOnly);
        model.addAttribute("sfth",sfth);
        String url ="";
        if("lzps".equals(lclx)){
            url =  "/ldpsdb/jbxxdetail";
       }else if("dzjb".equals(lclx)){
            if(StringUtils.isBlank(id)){
                id = UUIDUtils.getUUID();
            }
            model.addAttribute("id",id);
            url =  "/dzjbdb/dzjbxxdetail";
        }else if("zyjc".equals(lclx)){
            url =  "/sjzyjcdb/sjjcjbxxdetail";
        }else if ("jbsx".equals(lclx)) {
        	if(StringUtils.isBlank(id)){
                id = UUIDUtils.getUUID();
            }
            model.addAttribute("id",id);
			//交办事项新建页面---20200414
        	url = "/jbsx/jbsxxxdetail";
		}
        return url;
    }
    /**
     * 跳转基本信息页面
     */
    @RequestMapping("/toshenhe")
    public String toshenhe(String id, String wh, String bllx, String nodeid,String isReadOnly,
                           String nodename,String cbbmmc,String cjr,String ymlx,
                           String cbbmid,String lclx,String isfa, Model model,HttpSession se,String currentPage){
        model.addAttribute("id",id);
        model.addAttribute("wh",wh);
        model.addAttribute("bllx",bllx);
        model.addAttribute("nodeid",nodeid);
        model.addAttribute("nodename",nodename);
        model.addAttribute("cjr",cjr);
        model.addAttribute("cbbmid",cbbmid);
        model.addAttribute("cbbmmc",cbbmmc);
        model.addAttribute("isReadOnly",isReadOnly);
        model.addAttribute("lclx",lclx);
        model.addAttribute("isfa",isfa);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("ymlx",ymlx);//0,没有返回按钮  1.有返回按钮
        //model.addAttribute("dczqid",UUIDUtils.getUUID());
        UserEntity user = (UserEntity) se.getAttribute("User");
        model.addAttribute("curname",user.getName());
        String url ="";
        if("lzps".equals(lclx)){
            url =  "/ldpsdb/jbxxshenpi";
        }else if("dzjb".equals(lclx)){
            url =  "/dzjbdb/dzjbxxshenpi";
        }else if("zyjc".equals(lclx)){
            url =  "/sjzyjcdb/sjjcjbxxshenpi";
            if("0106".equals(nodeid) || "0107".equals(nodeid)){
                url =  "/sjzyjcdb/chengban";
            }else if("0104".equals(nodeid)){
                url =  "/sjzyjcdb/fjfg";
            }else if("0105".equals(nodeid)){
                model.addAttribute("bmid",user.getBmbm());
                url =  "/sjzyjcdb/fenan";
            }
        }else if ("jbsx".equals(lclx)) {        	
			//20200414--add
        	url = "/jbsx/jbsxxxshenpi";
		}
        return url;
    }
    /**
     * 待办列表查询方法
     * @param response
     * @return
     */
    @RequestMapping("/queryTable.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryTable(HttpServletResponse response, HttpSession se,DcdbDjlbDbParam dbParam){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        dbParam.setUserid(user.getId());
        List<DcdbDjlbDbParam> tableList = dbService.queryTable(dbParam);
        jsonobject.put("data", tableList);
        return jsonobject;
    }
    /**
     * 上级重要决策待办查询
     */
    @RequestMapping("/queryZyjcTable.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryZyjcTable(HttpServletResponse response, HttpSession se, DcdbSjjcsxDj dcdbSjjcsxDj){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        dcdbSjjcsxDj.setUserid(user.getId());
        List<DcdbSjjcsxDj> tableList = dbService.queryZyjcTable(dcdbSjjcsxDj);
        jsonobject.put("data", tableList);
        return jsonobject;
    }
    /**
     * 查询承办部门
     * @return
     */
    @RequestMapping("/queryCbBm.do")
    @ResponseBody
    public void queryCbBm(HttpServletResponse response, HttpSession se) {
        Map<String, Object> mapRes = new HashMap<>();
        try {
            List<Map<String,String>> bmList = dbService.queryCbBm();
            mapRes.put("data", bmList);
            UserEntity user = (UserEntity) se.getAttribute("User");
            mapRes.put("curBm", user.getBmbm());
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(mGson.toJson(mapRes));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 查询部门下的人
     * @return
     */
    @RequestMapping("/queryBmPerson.do")
    @ResponseBody
    public void queryBmPerson(HttpServletResponse response, HttpSession se,String unitid,String flag) {
        Map<String, Object> mapRes = new HashMap<>();
        try {
            Map<String, String> map = new HashMap<>();
            map.put("unitid", unitid);
            map.put("flag", flag);
            //当内勤时，传入fzryfzbh
            if("nq".equals(flag)){
                map.put("fzryfzbh", "e106b287-39e6-4599-9694-4327ed1c3066");
            }
            List<Map<String,String>> bmList = dbService.queryBmPerson(map);
            mapRes.put("data", bmList);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(mGson.toJson(mapRes));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 跳转办结页面
     * @param lclx
     * @param se
     * @param model
     * @return
     */
    @RequestMapping("/gdIndex.do")
    public String gdIndex(@ModelAttribute("lclx") String lclx,HttpSession se,Model model){
        UserEntity user = (UserEntity) se.getAttribute("User");
        String bmmc = user.getBmmc();
        model.addAttribute("bmmc",bmmc);
        model.addAttribute("name",user.getName());
        String url = "";
        //党组检办督办归档页面
        if("lzps".equals(lclx)){
            url = "/ldpsdb/dcdbdjlbgd";
        }else if ("dzjb".equals(lclx)){
            url = "/dzjbdb/dcdbdzjbgd";
        }else if ("jbsx".equals(lclx)){//----交办事项归档页面
            url = "/jbsx/jbsxbj";
        }
        return url;
    }

    /**
     * 查询办结列表 or 转发列表
     * @param response
     * @param se
     * @param dbParam
     * @return
     */
    @RequestMapping("/queryTableGd.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryTableGd(HttpServletResponse response, HttpSession se, DcdbDjlbDbParam dbParam){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        dbParam.setUserid(user.getId());
        List<DcdbDjlbDbParam> tableList = dbService.queryTableGd(dbParam);
        jsonobject.put("data", tableList);
        return jsonobject;
    }

  /**
   * 跳转-转发列表页面
   * @param lclx
   * @param se
   * @param model
   * @return
   */
  @RequestMapping("/zfIndex.do")
  public String zfIndex(@ModelAttribute("lclx") String lclx,HttpSession se,Model model){
    UserEntity user = (UserEntity) se.getAttribute("User");
    String bmmc = user.getBmmc();
    model.addAttribute("bmmc",bmmc);
    model.addAttribute("name",user.getName());
    String url = "";
    //党组检办督办归档页面
    if("lzps".equals(lclx)){
      url = "/ldpsdb/dcdbdjlbzf";
    }
    return url;
  }

}
