package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.model.*;
import com.bm.index.service.DcdbSjjcsxDbzqService;
import com.bm.index.service.DcdbSjjcsxDcsxService;
import com.bm.index.util.DateUtil;
import com.bm.index.util.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 上级决策功能
 */
@Controller
@RequestMapping("sjjc")
public class DcdbSjjcController {

    private Gson gson = new Gson();
    @Autowired
    private DcdbSjjcsxDcsxService dcsxService;
    @Autowired
    private DcdbSjjcsxDbzqService dbzqService;

    /**
     * 工作分工查询列表
     * @param response
     * @param se
     * @param dcsx
     * @return
     */
    @RequestMapping("queryFgTable")
    @PageHelperAnn
    @ResponseBody
    public JSONObject queryTable(HttpServletResponse response, HttpSession se, DcdbSjjcsxDcsx dcsx){
        JSONObject jsonobject = new JSONObject();
        List<DcdbSjjcsxDcsx> tableList = dcsxService.selectByExample(dcsx);
        jsonobject.put("data", tableList);
        return jsonobject;
    }
    /**
     * 工作分工保存操作
     */
    @RequestMapping("saveGzfg")
    @ResponseBody
    public void saveGzfg(HttpServletResponse response,String flag,String dcsxText,String dczq,String qtbm,String zybm,String dcts,String djid,String dczqid){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try {
            DcdbSjjcsxDcsx dcsx = new DcdbSjjcsxDcsx();
            if("2".equals(flag)){
                dcsx.setId(dczqid);
                dcsxService.deleteByExample(dcsx);
            }
            dcsx.setDcsxmc(dcsxText);
            dcsx.setDczq(dcts);
            dcsx.setDczqtype(dczq);
            dcsx.setDjid(djid);
            dcsx.setId(dczqid);
            dcsx.setQtbmid(qtbm);
            dcsx.setZzbmid(zybm);
            int i = dcsxService.insert(dcsx);
            if("0".equals(dczq)||"4".equals(dczq)){
                DcdbSjjcsxDbzq dbzq = new DcdbSjjcsxDbzq();
                dbzq.setId(UUIDUtils.getUUID());
                dbzq.setDcsxid(dczqid);
                dbzq.setDczqtype(dczq);
                dbzq.setDcrq(dcts);
                dbzq.setCjrq(DateUtil.getDate());
                dbzqService.deleteByDcsxId(dczqid);
                dbzqService.insertSelective(dbzq);
            }
            if(i>=1){
                resultMap.put("code","1");
                resultMap.put("msg","保存成功");
            }else{
                resultMap.put("code","2");
                resultMap.put("msg","保存失败");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 查询督办日期列表
     */
    @RequestMapping("queryDbrqTable")
    @ResponseBody
    public void queryDbrqTable(HttpServletResponse response,String dczqid,String dczq){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try {
            DcdbSjjcsxDbzq dbzq = new DcdbSjjcsxDbzq();
            List<DcdbSjjcsxDbzq> dbzqList = new ArrayList<>();
            if(StringUtils.isNotBlank(dczq)&&StringUtils.isNotBlank(dczqid)){
                dbzq.setDcsxid(dczqid);
                dbzq.setDczqtype(dczq);
                dbzqList = dbzqService.selectByExample(dbzq);
            }
            resultMap.put("code","0");
            resultMap.put("msg","");
            resultMap.put("data",dbzqList);
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 督办日期新增
     */
    @RequestMapping("saveDbrq")
    @ResponseBody
    public void saveDbrq(HttpServletResponse response,String dbrq,String dczqid,String dbzqInput){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try {
            DcdbSjjcsxDbzq dbzq = new DcdbSjjcsxDbzq();
            dbzq.setId(UUIDUtils.getUUID());
            dbzq.setDcsxid(dczqid);
            dbzq.setDczqtype(dbzqInput);
            dbzq.setDcrq(dbrq);
            dbzq.setCjrq(DateUtil.getDate());
            int i = dbzqService.insertSelective(dbzq);
            if(i>=1){
                resultMap.put("code","1");
                resultMap.put("msg","保存成功");
            }else{
                resultMap.put("code","2");
                resultMap.put("msg","保存失败");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     *工作分工编辑
     *
     */
    @RequestMapping("queryGzfg")
    @ResponseBody
    public void queryGzfg(HttpServletResponse response,String id){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try {

            DcdbSjjcsxDcsx dcsx = new DcdbSjjcsxDcsx();
            dcsx.setId(id);
            DcdbSjjcsxDcsx dcdbDcsx = dcsxService.selectById(dcsx);
            resultMap.put("dcdbDcsx",dcdbDcsx);
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 工作任务发起
     */
    @RequestMapping("faqigzfg")
    @ResponseBody
    public void faqigzfg(HttpServletResponse response,String id){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try {
            DcdbSjjcsxDcsx dcsx = new DcdbSjjcsxDcsx();
            dcsx.setId(id);
            DcdbSjjcsxDcsx dcdbDcsx = dcsxService.selectById(dcsx);
            List<DcdbSjjcsxDcsxnq> dcsxnqList = new ArrayList<>();
            //牵头部门
            if (StringUtils.isNotBlank(dcdbDcsx.getQtbmid())) {
                String[] qtbmArr = dcdbDcsx.getQtbmid().split(",");
                for (int i=0;i<qtbmArr.length;i++){
                    //分工任务表
                    DcdbSjjcsxDcsxnq dcsxnq = new DcdbSjjcsxDcsxnq();
                    dcsxnq.setId(UUIDUtils.getUUID());
                    dcsxnq.setDjid(dcdbDcsx.getDjid());
                    dcsxnq.setDcsxid(dcdbDcsx.getId());
                    dcsxnq.setDcsxmc(dcdbDcsx.getDcsxmc());
                    dcsxnq.setDczqtype(dcdbDcsx.getDczqtype());
                    dcsxnq.setNodeid("0105");
                    dcsxnq.setNodename("内勤分案");
                    dcsxnq.setZt("0");
                    dcsxnq.setIsfa("0");
                    dcsxnq.setIszz("0");
                    dcsxnq.setLzuserid(qtbmArr[i].split("@")[2]);
                    dcsxnq.setLzusername(qtbmArr[i].split("@")[3]);
                    dcsxnq.setNquserid(qtbmArr[i].split("@")[2]);
                    dcsxnq.setNqusername(qtbmArr[i].split("@")[3]);
                    dcsxnq.setCbbmid(qtbmArr[i].split("@")[0]);
                    dcsxnq.setCbbmmc(qtbmArr[i].split("@")[1]);
                    dcsxnqList.add(dcsxnq);
                }
            }else {
                resultMap.put("code","0");
                resultMap.put("msg","请选择牵头部门再发起");
            }
            //主责部门
            if (StringUtils.isNotBlank(dcdbDcsx.getZzbmid())) {
                String[] zzbmArr = dcdbDcsx.getZzbmid().split(",");
                for (int i=0;i<zzbmArr.length;i++){
                    //分工任务表
                    DcdbSjjcsxDcsxnq dcsxnq = new DcdbSjjcsxDcsxnq();
                    dcsxnq.setId(UUIDUtils.getUUID());
                    dcsxnq.setDjid(dcdbDcsx.getDjid());
                    dcsxnq.setDcsxid(dcdbDcsx.getId());
                    dcsxnq.setDcsxmc(dcdbDcsx.getDcsxmc());
                    dcsxnq.setDczqtype(dcdbDcsx.getDczqtype());
                    dcsxnq.setNodeid("0105");
                    dcsxnq.setNodename("内勤分案");
                    dcsxnq.setZt("0");
                    dcsxnq.setIsfa("0");
                    dcsxnq.setIszz("0");
                    dcsxnq.setLzuserid(zzbmArr[i].split("@")[2]);
                    dcsxnq.setLzusername(zzbmArr[i].split("@")[3]);
                    dcsxnq.setNquserid(zzbmArr[i].split("@")[2]);
                    dcsxnq.setNqusername(zzbmArr[i].split("@")[3]);
                    dcsxnq.setCbbmid(zzbmArr[i].split("@")[0]);
                    dcsxnq.setCbbmmc(zzbmArr[i].split("@")[1]);
                    dcsxnqList.add(dcsxnq);
                }
            }else {
                resultMap.put("code","0");
                resultMap.put("msg","请选择主责部门再发起");
            }
            //如果code不为0，则填写完整，可以完成发起功能
            if(!"0".equals(resultMap.get("code"))) {
                for (DcdbSjjcsxDcsxnq dcsxnq : dcsxnqList) {
                    dcsxService.insertDcsxnq(dcsxnq);
                    dcsxService.updateDb(dcsxnq.getDjid(), dcsxnq.getCbbmmc(), dcsxnq.getCbbmid(), dcsxnq.getLzuserid(),
                            dcsxnq.getLzusername(), "2", "zyjc");
                }
                dcsx.setIsfq("1");
                dcsxService.updateIsfq(dcsx);
                resultMap.put("code", "1");
                resultMap.put("msg", "发起成功");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 终止任务
     * @param response
     * @param id
     */
    @RequestMapping("zhongzhi")
    @ResponseBody
    public void zhongzhi(HttpServletResponse response,String id){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try {
            DcdbSjjcsxDcsx dcsx = new DcdbSjjcsxDcsx();
            dcsx.setId(id);
            dcsx.setIsfq("2");
            int i = dcsxService.updateIsfq(dcsx);
            DcdbSjjcsxDcsxnq dcsxnq = new DcdbSjjcsxDcsxnq();
            dcsxnq.setDcsxid(id);
            dcsxnq.setIszz("1");
            int j = dcsxService.updateIszz(dcsxnq);
            if(i>=1&&j>=1){
                resultMap.put("code","1");
                resultMap.put("msg","终止成功");
            }else {
                resultMap.put("code","0");
                resultMap.put("msg","终止失败");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code","0");
            resultMap.put("msg","终止失败");
        }
    }

    /**
     * 任务分工删除
     * @param response
     * @param id
     */
    @RequestMapping("shanchugzfg")
    @ResponseBody
    public void shanchugzfg(HttpServletResponse response,String id){
        try {
            HashMap<Object, Object> resultMap = new HashMap<>();
            DcdbSjjcsxDcsx dcsx = new DcdbSjjcsxDcsx();
            dcsx.setId(id);
            int i = dcsxService.deleteByExample(dcsx);
            dbzqService.deleteByDcsxId(id);
            if(i>=1){
                resultMap.put("code","1");
                resultMap.put("msg","删除成功");
            }else{
                resultMap.put("code","2");
                resultMap.put("msg","删除失败");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 督办周期删除
     * @param response
     * @param id
     */
    @RequestMapping("shanchugdbzq")
    @ResponseBody
    public void shanchugdbzq(HttpServletResponse response,String id){
        try {
            HashMap<Object, Object> resultMap = new HashMap<>();
            int i = dbzqService.deleteById(id);
            if(i>=1){
                resultMap.put("code","1");
                resultMap.put("msg","删除成功");
            }else{
                resultMap.put("code","2");
                resultMap.put("msg","删除失败");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
