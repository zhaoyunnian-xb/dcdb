package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 批示督办统计
 */
@Controller
@RequestMapping("psdbTj")
public class PsdbTjController {

    @Autowired
    private DcdbDbService dbService;
    /**
     * 跳转首页方法
     */
    @RequestMapping("index.do")
    public String index(@ModelAttribute("lclx") String lclx){
        String url = "";
        //党组检办督办统计页面
        if("dzjb".equals(lclx)){
            url = "/dzjbdb/dcdbdzjbtj";
            //领导批示统计页面
        }else if("lzps".equals(lclx)){
            url = "/ldpsdb/dcdbdjlbtj";
        }
        return url;
    }
    /**
     * 查询列表数据
     */
    @RequestMapping("/queryTable.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryTable(HttpServletResponse response, HttpSession se, DcdbDjlbDbParam dbParam){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        //  dbParam.setUserid(user.getId());
        List<DcdbDjlbDbParam> tableList = dbService.queryTjTable(dbParam);
        jsonobject.put("data", tableList);
        return jsonobject;
    }
}
