package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.DcdbFlowDao;
import com.bm.index.model.*;
import com.bm.index.service.*;
import com.bm.index.util.ConfigProperties;
import com.google.gson.Gson;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/Tjtable")
@Controller
public class DcdbTjController {

    @Autowired
    private DcdbTjService tjService;
    @Autowired
    private DcdbFlowDao dcdbFlowDao;
    @Autowired
    private DcdbFlowDao flowDao;
    private Gson mGson = new Gson();
    private final String bgsFhPerson = ConfigProperties.getProperties("bgsFhPerson");

    /**
     * 查询台账信息
     */
    @RequestMapping("/tjtable")
    @ResponseBody
    public List<Object> jdTjTable(HttpServletRequest request, String jdlx, String ndid,
        String cbbmid, String name) {
        if (name.equals(bgsFhPerson)) {
            cbbmid = "";
        }
        /*else if("402881824a09c8b9014a0a3d91a80004".equals(cbbmid) && !"罗艳".equals(name)){
           //督查组
           cbbmid="15052B4DC61A445389F4A6F1EE81B87F";//办公室
       }else if("E1B90C71A4B24C79A2C72E65442AB55F".equals(cbbmid)){
           //人事处
           cbbmid="7B30FC15F1B74323B439E3206ACB43D8";//政治部
       }else if("042F712DD94349FE8FC80F05D1146E67".equals(cbbmid)){
           //干部教育培训处
           cbbmid="7B30FC15F1B74323B439E3206ACB43D8";//政治部
       }else if("402881e444c5ff470144c8bbe96f0007".equals(cbbmid)){
           //基层工作处
           cbbmid="7B30FC15F1B74323B439E3206ACB43D8";//政治部
       }*/
        return tjService.selectTjYjd(jdlx, ndid, cbbmid);
    }

    /**
     *
     * @param request
     * @param cbbmid
     * @param tjzt
     * @return
     */
    @RequestMapping("/ndrw")
    @ResponseBody
    public List<Map<String, Object>> selectNdRw(HttpServletRequest request, String cbbmid,
        String tjzt, String name) {
        if (bgsFhPerson.equals(name)) {
            cbbmid = "";
        }
    /*    if("402881824a09c8b9014a0a3d91a80004".equals(cbbmid) && !"罗艳".equals(name)){
            //督查组
            cbbmid="15052B4DC61A445389F4A6F1EE81B87F";//办公室
        }else if("E1B90C71A4B24C79A2C72E65442AB55F".equals(cbbmid)){
            //人事处
            cbbmid="7B30FC15F1B74323B439E3206ACB43D8";//政治部
        }else if("042F712DD94349FE8FC80F05D1146E67".equals(cbbmid)){
            //干部教育培训处
            cbbmid="7B30FC15F1B74323B439E3206ACB43D8";//政治部
        }else if("402881e444c5ff470144c8bbe96f0007".equals(cbbmid)){
            //基层工作处
            cbbmid="7B30FC15F1B74323B439E3206ACB43D8";//政治部
        }*/
        List<Map<String, Object>> list = tjService.selectRw(cbbmid, tjzt);
        return list;
    }
    @RequestMapping("/doSelectNdrw")
    @ResponseBody
    public List<Map<String, Object>> doSelectNdRw(HttpServletRequest request) {         
        List<Map<String, Object>> list = tjService.doSelectNdrw();
        return list;
    }
    /**
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/jdIndex")
    public String jdIndex(HttpServletRequest request, Model model) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("User");
        String cbbmid = user.getBmbm();
        String name = user.getName();
        if (bgsFhPerson.equals(name)) {
            cbbmid = "";
        }
        model.addAttribute("cbbmid", cbbmid);
        model.addAttribute("name", user.getName());
        return "/ndgzrw/jdtz";
    }

    /**
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/ndIndex")
    public String ndIndex(HttpServletRequest request, Model model) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("User");
        String cbbmid = user.getBmbm();
        String name = user.getName();
        if (bgsFhPerson.equals(name)) {
            cbbmid = "";
        }
        model.addAttribute("cbbmid", cbbmid);
        model.addAttribute("name", user.getName());
        return "/ndgzrw/ndtj";
    }

    /**
     *
     * @param r
     * @param jdlx
     * @param ndid
     * @param cbbmid
     * @param fileName
     * @param response
     */
    @RequestMapping("/importExecl")
    public void importExecl(HttpServletRequest r, String jdlx, String ndid, String cbbmid,
        String fileName, HttpServletResponse response) {
        tjService.importExecl(jdlx, ndid, cbbmid, fileName, response);
    }
    /**
     *全年目标台账导出
     * @param r
     * @param jdlx
     * @param ndid
     * @param cbbmid
     * @param fileName
     * @param response
     */
    @RequestMapping("/importExeclQnmb")
    public void importExeclQnmb(HttpServletRequest r, String jdlx, String ndid, String cbbmid,
        String fileName, HttpServletResponse response) {
        tjService.importExeclQnmb(jdlx, ndid, cbbmid, fileName, response);
    }

    /**
     *
     * @param tjzt
     * @param id
     * @return
     */
    @RequestMapping("/ztgs")
    @ResponseBody
    public String updateTjzt(String tjzt, String id) {
        return tjService.updateTjzt(tjzt, id);
    }

    /**
     * 台账公示-跳转
     */
    @RequestMapping("/tzgsIndex")
    public String tzgsIndex(HttpServletRequest request, Model model) {
        return "/ndgzrw/tzgs";
    }

    /**
     *
     * @param lclx
     * @param model
     * @return
     */
    @RequestMapping("/tjIndex")
    public String tjIndex(String lclx, Model model) {
        model.addAttribute("lclx", lclx);
        if ("dzjb".equals(lclx)){
            return "/dcdblc/dzjbTjPage";
        }else if ("jbsx".equals(lclx)) {
        	return "/dcdblc/dzjbTjPage";
		}else {
            return "/dcdblc/tjPage";
        }
    }

    /**
     *统计报表-部门统计查询
     * @param zt
     * @param lclx
     * @return
     */
    @RequestMapping("/bmtj")
    @ResponseBody
    public Map<String, Object> bmtj(String zt, String lclx,String startTime,String endTime) {
        return tjService.bmtj(zt, lclx,startTime,endTime);
    }

    /**
     *统计报表-类型统计查询
     * @param zt
     * @param lclx
     * @return
     */
   /* @RequestMapping("/lxtj")
    @ResponseBody
    public Map<String, Object> lxtj(String zt, String lclx,String nf) {
        return tjService.lxtj(zt, lclx,nf);
    }*/
     @RequestMapping("/lxtj")
    @ResponseBody
    public Map<String, Object> lxtj(String zt, String lclx,String startDate_lx,String endDate_lx,String checkedtype) {
         Map map = new HashMap();
         if ("0".equals(zt)){
             zt = "'1','3'";
         }else {
             zt = "'"+zt+"'";
         }
         map.put("zt",zt);
         map.put("lclx",lclx);
         map.put("startDate",startDate_lx);
         map.put("endDate",endDate_lx);
         map.put("checkedtype",checkedtype);
        return tjService.newlxtj(map);
    }


    /**
     *统计报表-状态统计查询
     * @param zt
     * @param lclx
     * @return
     */
    @RequestMapping("/zttj")
    @ResponseBody
    public Map<String, Object> zttj(String zt, String lclx,String nf,String startTime,String endTime) {
        if ("0".equals(zt)){
            zt = "'1','3'";
        }else {
            zt = "'"+zt+"'";
        }
        return tjService.zttj(zt, lclx,nf,startTime,endTime);
    }
    /**
     *统计报表-导出
     * @param zt
     * @param lx
     * @param lclx
     * @param httpServletRequest
     * @param response
     */
    @RequestMapping("/importBm")
    public void importBm(String zt,
                         String lx,
                         String lclx,
                         String nf,
                         String checkedtype,
                         HttpServletRequest httpServletRequest,
                         HttpServletResponse response,
                         String startTime,
                         String endTime) {
        if ("0".equals(zt)){
            zt = "'1','3'";
        }else {
            zt = "'"+zt+"'";
        }
        tjService.importBm(zt, lx, lclx, nf,checkedtype,httpServletRequest, response,startTime,endTime);
    }

    /**
     *统计报表（列表详情）-导出
     * @param
     * @param
     * @param
     * @param httpServletRequest
     * @param response
     */
    @RequestMapping("/importExeclDetail.do")
    public void importExeclDetail(String lxdetail_zt,
                                  String lxdetail_lclx,
                                  String lxdetail_startDate,
                                  String lxdetail_endDate,
                                  String lxdetail_checkedtype,
                                  String lxdetail_lxtypename,
                                  String tjlx,
                                  HttpServletRequest httpServletRequest,
                         HttpServletResponse response) {
        if ("0".equals(lxdetail_zt)){
            lxdetail_zt = "'1','3'";
        }
            tjService.importExeclDetail(lxdetail_zt, lxdetail_lclx, lxdetail_startDate, lxdetail_endDate,lxdetail_checkedtype,
                    lxdetail_lxtypename,tjlx,httpServletRequest, response);


    }




    /**
     * 跳转上级决策事项页面
     */
    @RequestMapping("/sjjcsxRtIndex")
    public String sjjcsxRtIndex() {
        return "/sjzyjcdb/sjjcdbRt";
    }

    @RequestMapping("/selectMenu")
    @ResponseBody
    public List<Map<String, String>> selectMenu() {
        return tjService.sjjcsxDj();
    }

    @RequestMapping("/sjjcsxRt")
    @ResponseBody
    @PageHelperAnn
    public JSONObject sjjcsxRt(String djid) {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("data", tjService.sjjcsxRt(djid));
        return jsonobject;
    }

    /**
     * 台账公示操作（新）
     * @param response
     * @param tzgs
     */
    @RequestMapping("/addTzgs")
    public void addTzgs(HttpServletResponse response, DcdbNdrwTzgs tzgs) {
            responseJson(response,tjService.addTzgs(tzgs));
    }
    /**
     * 查询台账公示列表（新）
     * @param response
     * @param tzgs
     */
    @RequestMapping("/queryTzgsList")
    public void queryTzgsList(HttpServletResponse response, DcdbNdrwTzgs tzgs) {
            responseJson(response,tjService.queryTzgsList(tzgs));
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
   //查询统计数量对应的数据详情--类型统计
    @RequestMapping("/queryDetaillx.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryDetaillx(String zt, String lclx,String startDate_lx,String endDate_lx,String checkedtype,String lxtypename){
        JSONObject jsonobject = new JSONObject();
        Map map = new HashMap();
        if("合计".equals(lxtypename)){
            map.put("lxtypename","");
        }else if("是".equals(lxtypename)){
            map.put("lxtypename","yes");
        }else if("否".equals(lxtypename)){
            map.put("lxtypename","no");
        }else{
            map.put("lxtypename",lxtypename);
        }
        if ("0".equals(zt)){
            zt = "'1','3'";
        }else {
            zt = "'"+zt+"'";
        }
        map.put("zt",zt);
        map.put("lclx",lclx);
        map.put("startDate",startDate_lx);
        map.put("endDate",endDate_lx);
        map.put("checkedtype",checkedtype);
        List<DcdbDjlbDbParam> tableList = tjService.queryDetaillx(map);
        for (DcdbDjlbDbParam d : tableList){
            StringBuffer sb = new StringBuffer();
            String id = d.getId();
            DcdbSpyj dcdbSpyj = new DcdbSpyj();
            if(id.contains(",")){
                String[] idArr = id.split(",");
                for (int i=0;i<idArr.length;i++){
                    DcdbDjlbDb dcdbDjlbDb = flowDao.selectByPrimaryKey(idArr[i]);
                    dcdbSpyj.setId(idArr[i]);
                    dcdbSpyj.setSplx("blqk");
                    List<DcdbSpyj> dcdbSpyjs = dcdbFlowDao.selectSpyj(dcdbSpyj);
                    for (DcdbSpyj s : dcdbSpyjs){
                        if (StringUtils.isBlank(s.getSpbz())){
                            sb.append("");
                        }else {
                            sb.append(dcdbDjlbDb.getCbbmmc()).append(":");
                            sb.append(s.getSpbz()).append("       ").append("<br>");
                        }

                    }
                }
            }else {
                dcdbSpyj.setId(id);
                dcdbSpyj.setSplx("blqk");
                List<DcdbSpyj> dcdbSpyjs = dcdbFlowDao.selectSpyj(dcdbSpyj);
                for (DcdbSpyj s : dcdbSpyjs){
                    if (StringUtils.isBlank(s.getSpbz())){
                        sb.append("");
                    }else {
                        sb.append(s.getSpbz());
                    }
                }
                sb.append(":");
            }
            if (sb.length()>=1){
                d.setBlqk(sb.substring(0,sb.length()-1));
            }else {
                d.setBlqk(sb.toString());
            }

        }
        jsonobject.put("data", tableList);
        return jsonobject;
    }


    //查询统计数量对应的数据详情--类型统计
    @RequestMapping("/queryDetailzt.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryDetailzt(String zt, String lclx,String nf,String startTime,String endTime){
        JSONObject jsonobject = new JSONObject();
        List<DcdbDjlbDbParam> tableList = tjService.queryDetailzt(zt, lclx,nf,startTime,endTime);
        for (DcdbDjlbDbParam d : tableList){
            StringBuffer sb = new StringBuffer();
            String id = d.getId();
            DcdbSpyj dcdbSpyj = new DcdbSpyj();
            if(id.contains(",")){
                String[] idArr = id.split(",");
                for (int i=0;i<idArr.length;i++){
                    DcdbDjlbDb dcdbDjlbDb = flowDao.selectByPrimaryKey(idArr[i]);
                    dcdbSpyj.setId(idArr[i]);
                    dcdbSpyj.setSplx("blqk");
                    List<DcdbSpyj> dcdbSpyjs = dcdbFlowDao.selectSpyj(dcdbSpyj);
                    for (DcdbSpyj s : dcdbSpyjs){
                        if (StringUtils.isBlank(s.getSpbz())){
                            sb.append("");
                        }else {
                            sb.append(dcdbDjlbDb.getCbbmmc()).append(":");
                            sb.append(s.getSpbz()).append("       ").append("<br>");
                        }

                    }
                }
            }else {
                dcdbSpyj.setId(id);
                dcdbSpyj.setSplx("blqk");
                List<DcdbSpyj> dcdbSpyjs = dcdbFlowDao.selectSpyj(dcdbSpyj);
                for (DcdbSpyj s : dcdbSpyjs){
                    if (StringUtils.isBlank(s.getSpbz())){
                        sb.append("");
                    }else {
                        sb.append(s.getSpbz());
                    }
                }
                sb.append(":");
            }
            if (sb.length()>=1){
                d.setBlqk(sb.substring(0,sb.length()-1));
            }else {
                d.setBlqk(sb.toString());
            }

        }
        jsonobject.put("data", tableList);
        return jsonobject;
    }

    //查询统计数量对应的数据详情--部门统计
    @RequestMapping("/queryDetailbm.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryDetailbm(String zt, String lclx,String startTime,String endTime,String cbbmid){
        JSONObject jsonobject = new JSONObject();
        if ("0".equals(zt)){
            zt = "'1','3'";
        }else {
            zt = "'"+zt+"'";
        }
        List<DcdbDjlbDbParam> tableList = tjService.queryDetailbm(zt, lclx,startTime,endTime,cbbmid);
        jsonobject.put("data", tableList);
        return jsonobject;
    }



}