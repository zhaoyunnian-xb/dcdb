package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.model.DcdbSjjcsxDcsx;
import com.bm.index.model.DcdbSjjcsxDcsxnq;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbSjjcNqfaService;
import com.bm.index.service.impl.DcdbFlowServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("nqfa")
public class DcdbSjjcNqfaController {
    private Gson mGson = new Gson();
    private static Log logger = LogFactory.getLog(DcdbSjjcNqfaController.class);
    @Autowired
    private DcdbSjjcNqfaService dcdbsjjcnqfaservice;
    /**
     * 上级决策：内勤工作分工查询列表
     * @param response
     * @param
     * @param
     * @return
     */
    @RequestMapping("queryNqfaTable")
    @PageHelperAnn
    @ResponseBody
    public JSONObject queryNqfaTable(HttpServletResponse response, HttpSession se, DcdbSjjcsxDcsxnq dcsxnq){
        JSONObject jsonobject = new JSONObject();
        UserEntity user = (UserEntity) se.getAttribute("User");
        String userId = user.getId();
      //  dcsxnq.setLzuserid(userId);
        dcsxnq.setNquserid(userId);
        List<DcdbSjjcsxDcsxnq> tableList =dcdbsjjcnqfaservice.queryNqfaTable(dcsxnq);
        if(CollectionUtils.isNotEmpty(tableList)){
            for (DcdbSjjcsxDcsxnq dcsx :tableList){
                StringBuffer qtbmMc = new StringBuffer();
                StringBuffer zybmMc = new StringBuffer();
                //牵头部门拼接为部门1，部门2
                if(StringUtils.isNotBlank(dcsx.getQtbmid())){
                    String[] qtbmidArr = dcsx.getQtbmid().split(",");//1@1@1,2@2@2
                    //将部门名称拼接为部门1，部门2
                    for(int i=0;i<qtbmidArr.length;i++){
                        qtbmMc.append(qtbmidArr[i].split("@")[1]).append(",");
                    }
                    //去掉最后一个逗号
                    String qtbm = qtbmMc.substring(0, qtbmMc.length() - 1);
                    dcsx.setQtbmid(qtbm);
                }
                //主责部门拼接为部门1，部门2
                if(StringUtils.isNotBlank(dcsx.getZzbmid())){
                    String[] zzbmidArr = dcsx.getZzbmid().split(",");//1@1@1,2@2@2
                    //将部门名称拼接为部门1，部门2
                    for(int i=0;i<zzbmidArr.length;i++){
                        zybmMc.append(zzbmidArr[i].split("@")[1]).append(",");
                    }
                    //去掉最后一个逗号
                    String zzbm = zybmMc.substring(0, zybmMc.length() - 1);
                    dcsx.setZzbmid(zzbm);
                }
            }
        }
        jsonobject.put("data", tableList);
        return jsonobject;
    }

    /**
     * 上级决策：内勤分案，--工作分配
     * @param response
     * @param se
     * @param data
     * @param
     */
    @RequestMapping("/updateFp.do")
    @ResponseBody
    public void updateFp(HttpServletResponse response, HttpSession se,
                           String data, String lzuserid,String lzusername) {
        Map<String, String> map = new HashMap<>();
        map.put("data", data);
        map.put("lzuserid", lzuserid);
        map.put("lzusername", lzusername);
        Map<String, Object> mapRes = dcdbsjjcnqfaservice.updateFp(se,map);
        responseJson(response, mapRes);
    }

    /**
     * 上级决策：内勤分案，--分配撤销
     * @param response
     * @param se
     * @param data
     *
     */
    @RequestMapping("/updateCx.do")
    @ResponseBody
    public void updateCx(HttpServletResponse response, HttpSession se,
                         String data) {
        Map<String, String> map = new HashMap<>();
        map.put("data", data);
        Map<String, Object> mapRes = dcdbsjjcnqfaservice.updateCx(se,map);
        responseJson(response, mapRes);
    }

    /**
     * 上级决策：内勤分案，--分案
     * @param response
     * @param se
     * @param
     *
     */
    @RequestMapping("/updateFa.do")
    @ResponseBody
    public void updateFa(HttpServletResponse response, HttpSession se,DcdbSjjcsxDcsxnq dcsxnq,String bllx) {
        Map<String, Object> mapRes = null;
        UserEntity user = (UserEntity) se.getAttribute("User");
        String userId = user.getId();
        dcsxnq.setNquserid(userId);
        List<DcdbSjjcsxDcsxnq> tableList =dcdbsjjcnqfaservice.queryNqfaTable(dcsxnq);
        try{
             mapRes = dcdbsjjcnqfaservice.updateFa(se,tableList,dcsxnq,bllx);
        }catch(Exception e){
            logger.error(e);
            mapRes.put("code", 1);
            mapRes.put("msg", "操作失败");
        }
       // Map<String, Object> mapRes = dcdbsjjcnqfaservice.updateFa(se,tableList);
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
