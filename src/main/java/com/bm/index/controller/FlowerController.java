package com.bm.index.controller;


import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/flow")
@Controller
public class FlowerController {
    @Autowired
    private FlowerService f;
    @RequestMapping("/getFlower")
    @ResponseBody
    public Map<String,Object> getFlower(String ywtype,String id,String lclx,String preid){
        if("dzjb".equals(lclx)){
            ywtype="党组检办";
        }
        return f.getFlower(ywtype,id,preid);
    }
    @RequestMapping("/getFlowList")
    @ResponseBody
    @PageHelperAnn
    public JSONObject getFlowList(String ywtype,String id,String lclx,String preid){
        JSONObject jb=new JSONObject();
        if("dzjb".equals(lclx)){
            ywtype="党组检办";
        }
        System.out.println("进入了");
        System.out.println("数据:"+f.getFlowList(ywtype,id,preid));
        jb.put("data", f.getFlowList(ywtype,id,preid));
        return jb;
    }
    @RequestMapping("/flowPage")
    public String flowPage(String id, String wh, String bllx, String nodeid, String nodename, String isReadOnly,
                           String cjr, String cbbmid, String cbbmmc, String lclx, String isfa, String preid,Model model){
        model.addAttribute("id",id);
        model.addAttribute("wh",wh);
        model.addAttribute("cjr",cjr);
        model.addAttribute("cbbmid",cbbmid);
        model.addAttribute("cbbmmc",cbbmmc);
        model.addAttribute("lclx",lclx);
        model.addAttribute("bllx",bllx);
        model.addAttribute("isfa",isfa);
        model.addAttribute("nodeid",nodeid);
        model.addAttribute("nodename",nodename);
        model.addAttribute("preid",preid);
        model.addAttribute("isReadOnly",1);
        return "dcdblc/flower";

    }
}
