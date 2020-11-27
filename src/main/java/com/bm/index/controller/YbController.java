package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbDjlbYbParam;
import com.bm.index.model.DcdbSjjcsxDj;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbDbService;
import com.bm.index.service.DcdbYbService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/yb")
public class YbController {

    private Gson mGson = new Gson();
    @Autowired
    private DcdbYbService ybService;

    /**
     * 主页跳转方法
     */
    @RequestMapping("/index.do")
    public String index(@ModelAttribute("lclx") String lclx,@ModelAttribute("currentPage")String currentPage ){
        String url = "";
        //党组检办督办已办页面
        if("dzjb".equals(lclx)){
            url = "/dzjbdb/dcdbdzjbyb";
            //领导批示已办页面
        }else if("lzps".equals(lclx)){
            url = "/ldpsdb/dcdbdjlbyb";
        }else if("zyjc".equals(lclx)){
            url = "/sjzyjcdb/dcdbzyjcyb";
        }else if ("jbsx".equals(lclx)) {
        	 url = "/jbsx/jbsxyb";
		}
        return url;
    }
    /**
     * 跳转基本信息页面
     */
    @RequestMapping("/toxinjian")
    public String toxinjian(String id, String wh, String lclx,
                            String bllx, String nodeid, String nodename,String cjr,String cbbmid, Model model){
        model.addAttribute("id",id);
        model.addAttribute("wh",wh);
        model.addAttribute("bllx",bllx);
        model.addAttribute("nodeid",nodeid);
        model.addAttribute("nodename",nodename);
        model.addAttribute("cjr",cjr);
        model.addAttribute("cbbmid",cbbmid);
        model.addAttribute("lclx",lclx);
        String url ="";
        if("lzps".equals(lclx)){
            url =  "/dcdblc/jbxxdetail";
        }else if("dzjb".equals(lclx)){
            url =  "/dcdblc/dzjbxxdetail";
        }
        return url;
    }
    /**
     * 跳转基本信息页面
     */
    @RequestMapping("/toshenhe")
    public String toshenhe(String id, String wh, String bllx, String nodeid,String lclx, String nodename,String cjr,String cbbmid, Model model){
        model.addAttribute("id",id);
        model.addAttribute("wh",wh);
        model.addAttribute("bllx",bllx);
        model.addAttribute("nodeid",nodeid);
        model.addAttribute("nodename",nodename);
        model.addAttribute("cjr",cjr);
        model.addAttribute("cbbmid",cbbmid);
        model.addAttribute("lclx",lclx);
        model.addAttribute("isfh","1");//0,没有返回按钮  1.有返回按钮
        String url ="";
        if("lzps".equals(lclx)){
            url =  "/dcdblc/jbxxshenpi";
        }else if("dzjb".equals(lclx)){
            url =  "/dcdblc/dzjbxxshenpi";
        }else if("zyjc".equals(lclx)){
            url =  "/dcdblc/dzjbxxshenpi";
        }
        return url;
    }
    /**
     * 已办列表查询方法
     * @param response
     * @return
     */
    @RequestMapping("/queryTable.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryTable(HttpServletResponse response, HttpSession se, DcdbDjlbYbParam ybParam){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        ybParam.setUserid(user.getId());
        List<DcdbDjlbYbParam> tableList = ybService.queryTable(ybParam);
        jsonobject.put("data", tableList);
        return jsonobject;
    }

    /**
     * 上级重要决策已办查询
     */
    @RequestMapping("/queryZyjcTableyb.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryZyjcTableyb(HttpServletResponse response, HttpSession se, DcdbSjjcsxDj dcdbSjjcsxDj){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        dcdbSjjcsxDj.setUserid(user.getId());
        List<DcdbSjjcsxDj> tableList = ybService.queryZyjcTableyb(dcdbSjjcsxDj);
        jsonobject.put("data", tableList);
        return jsonobject;
    }
}
