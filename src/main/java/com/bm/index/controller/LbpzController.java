package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.LbpzDao;
import com.bm.index.model.LbpzPlb;
import com.bm.index.model.LbpzZlb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/lb")
public class LbpzController {

    @Autowired
    private LbpzDao lbpzdao;

    @RequestMapping("/topage.do")
    public String topage(){
        return "/xtgl/lbpz";
    }
    /**
     * 查询父类别列表
     * */
    @RequestMapping("/queryPAlltype.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject getAllLbpzPlbList(LbpzPlb lbpzplb){
        JSONObject jsonobject = new JSONObject();
        List<LbpzPlb> list = lbpzdao.getAllLbpzPlbList(lbpzplb);
        jsonobject.put("data", list);
        return jsonobject;
    }
    /**
     * 根据查询条件查询相应的子类别列表
     * */
    @RequestMapping("/queryTable.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryTable(LbpzZlb lbpzzlb){
        JSONObject jsonobject = new JSONObject();
        List<LbpzZlb> tableList = lbpzdao.queryTable(lbpzzlb);
        jsonobject.put("data", tableList);
        return jsonobject;
    }

    /**
     * 子类别的删除
     * */
    @RequestMapping("/delChildrenLb.do")
    @ResponseBody
    public Map<String,Object> delChildrenLb(String id){
        int i = 0;
        Map<String,Object> resultmap = new HashMap<String,Object>();
        try{
            lbpzdao.delChildrenLb(id);
            i = 1;
            resultmap.put("code",i);
            resultmap.put("msg","删除成功");
        }catch(Exception e){
            e.getStackTrace();
            resultmap.put("code",i);
            resultmap.put("msg","删除失败");
        }
        return resultmap;
    }

    /**
     * 父类别的删除
     * */
    @RequestMapping("/delParentLb.do")
    @ResponseBody
    public Map<String,Object> delParentLb(LbpzPlb lbpzplb){
        int i = 0;
        Map<String,Object> resultmap = new HashMap<String,Object>();
        try{
            LbpzZlb lbpzzlb = new LbpzZlb();
            lbpzzlb.setPid(lbpzplb.getId());
            lbpzzlb.setPtype(lbpzplb.getLbtype());
            List<LbpzZlb> tableList = lbpzdao.queryTable(lbpzzlb);
            if(tableList.size()>0){
                resultmap.put("code",i);
                resultmap.put("msg","请先删除该父类别下的子类别");
            }else{
                lbpzdao.delParentLb(lbpzplb.getId());
                i = 1;
                resultmap.put("code",i);
                resultmap.put("msg","删除成功");
            }

        }catch(Exception e){
            e.getStackTrace();
            resultmap.put("code",i);
            resultmap.put("msg","删除失败");
        }
        return resultmap;
    }


    /**
     * 子类别的编辑
     * */
    @RequestMapping("/updaterXlb.do")
    @ResponseBody
    public Map<String,Object> updaterXlb(LbpzZlb lbpzzlb){
        int i = 0;
        Map<String,Object> resultmap = new HashMap<String,Object>();
        try{

            String id = lbpzzlb.getId();
            if(id == null || "".equals(id)){
                lbpzzlb.setId(UUID.randomUUID().toString());
                lbpzdao.insertXlb(lbpzzlb);
                i = 1;
                resultmap.put("code",i);
                resultmap.put("msg","编辑成功");
            }else{
                lbpzdao.updaterXlb(lbpzzlb);
                i = 1;
                resultmap.put("code",i);
                resultmap.put("msg","编辑成功");
            }
        }catch(Exception e){
            e.printStackTrace();
            resultmap.put("code",i);
            resultmap.put("msg","编辑失败");
        }
        return resultmap;
    }


    /**
     * 父类别的编辑
     * */
    @RequestMapping("/updaterDlb.do")
    @ResponseBody
    public Map<String,Object> updaterDlb(LbpzPlb lbpzplb){
        int i = 0;
        Map<String,Object> resultmap = new HashMap<String,Object>();
        try{

            String id = lbpzplb.getId();
            if(id == null || "".equals(id)){
                String lbtype = lbpzplb.getLbtype();
                LbpzPlb  lbpzplb2 = lbpzdao.getLbpzPlbById(lbtype);
                if(lbpzplb2 != null){
                    resultmap.put("code",i);
                    resultmap.put("msg","类别标志已存在");
                }else{
                    lbpzplb.setId(UUID.randomUUID().toString());
                    lbpzdao.insertPlb(lbpzplb);
                    i = 1;
                    resultmap.put("code",i);
                    resultmap.put("msg","编辑成功");
                }

            }else{
                String lbtype = lbpzplb.getLbtype();
                if(lbtype == null || "".equals(lbtype)){

                }else{
                    LbpzZlb lbpzzlb = new LbpzZlb();
                    lbpzzlb.setPid(lbpzplb.getId());
                    List<LbpzZlb> tableList = lbpzdao.queryTable(lbpzzlb);
                    if(tableList.size() > 0 ){
                        resultmap.put("code",i);
                        resultmap.put("msg","该类别下存在子类别，请先操作子类别");
                    }else{
                        LbpzPlb  lbpzplb2 = lbpzdao.getLbpzPlbById(lbtype);
                        if(lbpzplb2 != null){
                            resultmap.put("code",i);
                            resultmap.put("msg","类别标志已存在");
                        }else{
                            lbpzdao.updaterPlb(lbpzplb);
                            i = 1;
                            resultmap.put("code",i);
                            resultmap.put("msg","编辑成功");
                        }
                    }
                }
            }
        }catch(Exception e){
            e.getStackTrace();
            resultmap.put("code",i);
            resultmap.put("msg","编辑失败");
        }
        return resultmap;
    }

    /**
     * 根据查询条件查询相应的子类别列表
     * */
    @RequestMapping("/selectPeizhiData.do")
    @ResponseBody
    public Map<String,Object>  selectPeizhiData(LbpzZlb lbpzzlb){
        Map<String,Object> map = new HashMap<String,Object>();
        List<LbpzZlb> tableList = lbpzdao.queryTable(lbpzzlb);
        map.put("data",tableList);
        return map;
    }

}
