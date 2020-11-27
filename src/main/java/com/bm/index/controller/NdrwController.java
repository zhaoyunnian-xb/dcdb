package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.model.*;
import com.bm.index.service.DcdbNdrwService;
import com.bm.index.service.DcdbNdrwYdnrService;
import com.bm.index.service.DcdbNdrwZyrwService;
import com.bm.index.util.DateUtil;
import com.bm.index.util.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.index.controller
 * @Description: 年度任务controller
 * @date 2019/3/4
 */
@Controller
@RequestMapping("/ndrw")
public class NdrwController {

    private Gson gson = new Gson();

    @Autowired
    private DcdbNdrwService ndrwService;
    @Autowired
    private DcdbNdrwYdnrService ydnrService;
    @Autowired
    private DcdbNdrwZyrwService zyrwService;
    @Autowired
    private DcdbBmmbnrDao bmmbnrDao;
    /**
     * 跳转主页方法
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        String ndid = UUIDUtils.getUUID();
        model.addAttribute("ndid",ndid);
        return "/ndgzrw/ndrw";
    }
    /**
     * 查询年度任务数据方法
     */
    @RequestMapping(value="/queryTable.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryTable(HttpServletRequest request,String nd,String czr,String ndrwmc,
                                 HttpServletResponse response){
        JSONObject jsonobject = new JSONObject();
        DcdbNdrw ndrw = new DcdbNdrw();
        ndrw.setCzr(czr);
        ndrw.setNd(nd);
        ndrw.setNdrwmc(ndrwmc);
        List<DcdbNdrw> ndrwList = ndrwService.queryTable(ndrw);
        for (DcdbNdrw dcdbNdrw:ndrwList){
            String ndrwId = dcdbNdrw.getId();
            DcdbBmmbnr bmmbnr = new DcdbBmmbnr();
            bmmbnr.setNdid(ndrwId);
            List<DcdbBmmbnr> bmmbnrList = bmmbnrDao.selectBmJdtb(bmmbnr);
            //第一季度发起可以点击
            //如果状态为空，则第一季度为未发起，赋值为初始值，如果不为空，则已经发起
            if(StringUtils.isBlank(dcdbNdrw.getCzzt())) {
                dcdbNdrw.setCzzt("00");
            }else if("00".equals(dcdbNdrw.getCzzt())) {
                bmmbnr.setStatus("105");
                List<DcdbBmmbnr> bmmbnrZtList = bmmbnrDao.selectBmJdtb(bmmbnr);
                //部门任务表中全部状态都为105，则全年目标填写完成
                if(bmmbnrZtList.size()!=0){
                    if(bmmbnrList.size()==bmmbnrZtList.size()){
                        dcdbNdrw.setCzzt("11");
                    }
                }
                //第二季度发起
            }else if("12".equals(dcdbNdrw.getCzzt())){
                bmmbnr.setStatus("119");
                List<DcdbBmmbnr> bmmbnrZtList = bmmbnrDao.selectBmJdtb(bmmbnr);
                //部门任务表中全部状态都为119，则第一季度填写完成
                if(bmmbnrList.size()==bmmbnrZtList.size()){
                    dcdbNdrw.setCzzt("21");
                }
                //第三季度发起
            }else if("22".equals(dcdbNdrw.getCzzt())){
                bmmbnr.setStatus("219");
                List<DcdbBmmbnr> bmmbnrZtList = bmmbnrDao.selectBmJdtb(bmmbnr);
                //部门任务表中全部状态都为219，则第二季度填写完成
                if(bmmbnrList.size()==bmmbnrZtList.size()){
                    dcdbNdrw.setCzzt("31");
                }
                //第四季度发起
            }else if("32".equals(dcdbNdrw.getCzzt())){
                bmmbnr.setStatus("319");
                List<DcdbBmmbnr> bmmbnrZtList = bmmbnrDao.selectBmJdtb(bmmbnr);
                //部门任务表中全部状态都为319，则第三季度填写完成
                if(bmmbnrList.size()==bmmbnrZtList.size()){
                    dcdbNdrw.setCzzt("41");
                }
                //全部完成状态改为66
            }else if("42".equals(dcdbNdrw.getCzzt())){
                bmmbnr.setStatus("419");
                List<DcdbBmmbnr> bmmbnrZtList = bmmbnrDao.selectBmJdtb(bmmbnr);
                //部门任务表中全部状态都为419，则第四季度填写完成
                if(bmmbnrList.size()==bmmbnrZtList.size()){
                    dcdbNdrw.setCzzt("66");
                }
            }
            ndrwService.updateStatus(dcdbNdrw);
        }
        jsonobject.put("data", ndrwList);
        return jsonobject;
    }
    /**
     * 查询要点内容列表
     */
    @RequestMapping("queryYdnr.do")
    @ResponseBody
    public void queryYdnr(HttpServletRequest request, Model model,HttpServletResponse response,String ndid){
        try {
            HashMap<Object, Object> resultMap = new HashMap<>();
            List<DcdbNdrwYdnr> ydnrList = ydnrService.selectbyNdid(ndid);
            resultMap.put("code","0");
            resultMap.put("msg","");
            resultMap.put("data",ydnrList);
           /* DcdbNdrw ndrw = ndrwService.queryByid(ndid);
           // model.addAttribute("czzt",ndrw.getCzzt());
            resultMap.put("czzt",ndrw.getCzzt());*/
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 要点内容保存
     */
    @RequestMapping("saveYdnr.do")
    @ResponseBody
    public void saveYdnr(HttpServletResponse response,String ydnrText,String ndid,HttpServletRequest request){
        try {
            String uuid = UUIDUtils.getUUID();
            HashMap<Object, Object> resultMap = new HashMap<>();
            DcdbNdrwYdnr ydnr = new DcdbNdrwYdnr();
            ydnr.setId(uuid);
            ydnr.setCzsj(DateUtil.getStringFormatTime());
            ydnr.setNdid(ndid);
            ydnr.setYdnrmc(ydnrText);
            int i = ydnrService.saveYdnr(ydnr);
            if(i>=1){
                resultMap.put("code","1");
                resultMap.put("msg","保存成功");
            }else{
                resultMap.put("code","2");
                resultMap.put("msg","保存失败");
            }
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 主要内容查询
     *
     */
    @RequestMapping("queryynr")
    @ResponseBody
    public void queryynr(HttpServletRequest request,String zynrydnr,String zynrndid,HttpServletResponse response){
        try {
            DcdbNdrwZyrw zyrw = new DcdbNdrwZyrw();
            zyrw.setYdnrid(zynrydnr);
            zyrw.setNdid(zynrndid);
            HashMap<Object, Object> resultMap = new HashMap<>();
            List<DcdbNdrwZyrw> zyrwList = zyrwService.selectByExample(zyrw);
            //将配合部门，牵头部门，主责部门的数据截取，只留部门名称
            for(DcdbNdrwZyrw zyrw1 : zyrwList){
                //配合部门
                String phbm = zyrw1.getPhbm();
                StringBuffer phbmBuffer = new StringBuffer();
                if(StringUtils.isNotBlank(phbm)){
                    String[] phbmArr = phbm.split(",");//526BFBC6DDB74DE38C845FDC993DE3E8@监察处@e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                    for(int i=0;i<phbmArr.length;i++){
                        String[] phbmUserArr = phbmArr[i].split("@");//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                        if(phbmUserArr.length<3){
                            continue;
                        }else {
                            phbmBuffer.append(phbmUserArr[1]).append(",");
                        }
                    }
                    if(phbmBuffer.length()!=0){
                        phbm = phbmBuffer.toString().substring(0,phbmBuffer.toString().length()-1);
                    }
                }
                //牵头部门
                String qtbm = zyrw1.getQtbm();
                StringBuffer qtbmBuffer = new StringBuffer();
                if(StringUtils.isNotBlank(qtbm)){
                    String[] qtbmArr = qtbm.split(",");//526BFBC6DDB74DE38C845FDC993DE3E8@监察处@e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                    for(int i=0;i<qtbmArr.length;i++){
                        String[] qtbmUserArr = qtbmArr[i].split("@");//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                        if(qtbmUserArr.length<3){
                            continue;
                        }else {
                            qtbmBuffer.append(qtbmUserArr[1]).append(",");
                        }
                    }
                    if(qtbmBuffer.length()!=0){
                        qtbm = qtbmBuffer.toString().substring(0,qtbmBuffer.toString().length()-1);
                    }
                }
                //主责部门
                String zybm = zyrw1.getZybm();
                StringBuffer zybmBuffer = new StringBuffer();
                if(StringUtils.isNotBlank(zybm)){
                    String[] zybmArr = zybm.split(",");//526BFBC6DDB74DE38C845FDC993DE3E8@监察处@e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                    for(int i=0;i<zybmArr.length;i++){
                        String[] zybmUserArr = zybmArr[i].split("@");//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                        if(zybmUserArr.length<3){
                            continue;
                        }else {
                            zybmBuffer.append(zybmUserArr[1]).append(",");
                        }
                    }
                    if(zybmBuffer.length()!=0){
                        zybm = zybmBuffer.toString().substring(0,zybmBuffer.toString().length()-1);
                    }
                }
                zyrw1.setPhbm(phbm);
                zyrw1.setQtbm(qtbm);
                zyrw1.setZybm(zybm);
            }
            resultMap.put("code","0");
            resultMap.put("msg","");
            resultMap.put("data",zyrwList);
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 主要内容新增保存
     */
    @RequestMapping("savezynrxzInfo")
    @ResponseBody
    public void savezynrxzInfo(HttpServletResponse response,String zynrtext,String zrld,String qtbm,
                               String zybm,String phbm,String ndid,String zynrydnr,String flag,String zyrwid){
        try {
            int i=0;
            HashMap<Object, Object> resultMap = new HashMap<>();
            if("modify".equalsIgnoreCase(flag)){
                DcdbNdrwZyrw zyrwUp = new DcdbNdrwZyrw();
                zyrwUp.setId(zyrwid);
                zyrwUp.setZyrwmc(zynrtext);
                zyrwUp.setPhbm(phbm);
                zyrwUp.setQtbm(qtbm);
                 i = zyrwService.updateByExampleSelective(zyrwUp);
            }else{
                String id = UUIDUtils.getUUID();
                DcdbNdrwZyrw zyrw = new DcdbNdrwZyrw();
                zyrw.setYdnrid(zynrydnr);
                zyrw.setNdid(ndid);
                zyrw.setId(id);
                zyrw.setPhbm(phbm);
                zyrw.setQtbm(qtbm);
                zyrw.setZrld(zrld);
                zyrw.setZybm(zybm);
                zyrw.setZyrwmc(zynrtext);
                zyrw.setCjsj(DateUtil.getStringFormatTime());
                 i = zyrwService.insertSelective(zyrw);
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
     * 删除要点内容
     */
    @RequestMapping("deleteYdnr")
    @ResponseBody
    public void deleteYdnr(HttpServletResponse response,String id){
        try {
            HashMap<Object, Object> resultMap = new HashMap<>();
            int i = ydnrService.deleteYdnrmc(id);
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
     * 删除主要内容
     */
    @RequestMapping("deleteZynr")
    @ResponseBody
    public void deleteZynr(HttpServletResponse response,String id){
        try {
            HashMap<Object, Object> resultMap = new HashMap<>();
            int i = zyrwService.deleteZynr(id);
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
     * 根据主键查询主要内容具体内容
     */
    @RequestMapping("queryZynrById")
    @ResponseBody
    public void queryZynrById(HttpServletResponse response,String id){
        try{
            HashMap<Object, Object> resultMap = new HashMap<>();
            DcdbNdrwZyrw zyrw = zyrwService.selectByPrimaryKey(id);
            //配合部门
            String phbm = zyrw.getPhbm();
            StringBuffer phbmBuffer = new StringBuffer();
            if(StringUtils.isNotBlank(phbm)){
                zyrw.setPhbm1(phbm);
                String[] phbmArr = phbm.split(",");//526BFBC6DDB74DE38C845FDC993DE3E8@监察处@e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                for(int i=0;i<phbmArr.length;i++){
                    String[] phbmUserArr = phbmArr[i].split("@");//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                    if(phbmUserArr.length<3){
                        continue;
                    }else {
                        phbmBuffer.append(phbmUserArr[1]).append(",");
                    }
                }
                if(phbmBuffer.length()!=0){
                    phbm = phbmBuffer.toString().substring(0,phbmBuffer.toString().length()-1);
                }
            }
            //牵头部门
            String qtbm = zyrw.getQtbm();
            StringBuffer qtbmBuffer = new StringBuffer();
            if(StringUtils.isNotBlank(qtbm)){
                zyrw.setQtbm1(qtbm);
                String[] qtbmArr = qtbm.split(",");//526BFBC6DDB74DE38C845FDC993DE3E8@监察处@e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                for(int i=0;i<qtbmArr.length;i++){
                    String[] qtbmUserArr = qtbmArr[i].split("@");//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                    if(qtbmUserArr.length<3){
                        continue;
                    }else {
                        qtbmBuffer.append(qtbmUserArr[1]).append(",");
                    }
                }
                if(qtbmBuffer.length()!=0){
                    qtbm = qtbmBuffer.toString().substring(0,qtbmBuffer.toString().length()-1);
                }
            }
            //主责部门
            String zybm = zyrw.getZybm();
            StringBuffer zybmBuffer = new StringBuffer();
            if(StringUtils.isNotBlank(zybm)) {
                zyrw.setZybm1(zybm);
                String[] zybmArr = zybm.split(",");//526BFBC6DDB74DE38C845FDC993DE3E8@监察处@e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                for (int i = 0; i < zybmArr.length; i++) {
                    String[] zybmUserArr = zybmArr[i].split("@");//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                    if (zybmUserArr.length < 3) {
                        continue;
                    } else {
                        zybmBuffer.append(zybmUserArr[1]).append(",");
                    }
                }
                if (zybmBuffer.length() != 0) {
                    zybm = zybmBuffer.toString().substring(0, zybmBuffer.toString().length() - 1);
                }
            }
            zyrw.setPhbm(phbm);
            zyrw.setQtbm(qtbm);
            zyrw.setZybm(zybm);
            resultMap.put("zyrw",zyrw);
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/**
 * 保存年度任务
 */
@RequestMapping("baocunYdnr")
@ResponseBody
public void baocunYdnr(HttpServletResponse response,HttpSession se,String ndid,String rwmctext){
    HashMap<Object, Object> resultMap = new HashMap<>();
    int k = 0;
    try {
        DcdbNdrw dcdbNdrw = new DcdbNdrw();
        dcdbNdrw.setId(ndid);
        dcdbNdrw.setNd(DateUtil.getYear());
        dcdbNdrw.setNdrwmc(rwmctext);
        UserEntity user = (UserEntity) se.getAttribute("User");
        dcdbNdrw.setCzr(user.getName());
        dcdbNdrw.setCzsj(DateUtil.getDate());
        ndrwService.deleteById(ndid);
        k = ndrwService.insertNdrw(dcdbNdrw);
        resultMap.put("code","1");
        resultMap.put("msg","保存成功");
        response.setContentType( "text/html;charset=UTF-8");
        response.getWriter().write( gson.toJson(resultMap));
    }catch (Exception e){
        e.printStackTrace();
    }
}
    /**
     * 提交年度任务
     * @param response
     * @param se
     * @param ndid
     * @param rwmctext
     */
    @RequestMapping("tijiaoYdnr")
    @ResponseBody
    public void tijiaoYdnr(HttpServletResponse response, HttpSession se,String ndid, String rwmctext){
        try{
            HashMap<Object, Object> resultMap = new HashMap<>();
            int k = 0;
            DcdbNdrwZyrw dcdbNdrwZyrw = new DcdbNdrwZyrw();
            dcdbNdrwZyrw.setNdid(ndid);
            List<DcdbNdrwZyrw> zyrwList = zyrwService.selectByExample(dcdbNdrwZyrw);
            List<DcdbBmmbnr> dcdbBmmbnrList = new ArrayList<>();
            for(DcdbNdrwZyrw zyrw : zyrwList){
                String qtbm = zyrw.getQtbm();
                if(StringUtils.isNotBlank(qtbm)){
                    String[] qtbmArr = qtbm.split(",");
                    for(int i=0;i<qtbmArr.length;i++){
                        DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                        String cbbmid = qtbmArr[i].split("@")[0];//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                        String ssbm = qtbmArr[i].split("@")[1];
                        String cbrid = qtbmArr[i].split("@")[2];
                        dcdbBmmbnr.setId(UUIDUtils.getUUID());
                        dcdbBmmbnr.setNdid(ndid);
                        dcdbBmmbnr.setYdnrid(zyrw.getYdnrid());
                        dcdbBmmbnr.setZyrwid(zyrw.getId());
                        dcdbBmmbnr.setSsbm(ssbm);
                        dcdbBmmbnr.setBmlx("qt");
                        dcdbBmmbnr.setCbrid(cbrid);
                        dcdbBmmbnr.setStatus("101");
                        dcdbBmmbnr.setCbbmid(cbbmid);
                        dcdbBmmbnr.setUserid(cbrid);
                        dcdbBmmbnr.setJdlx("1");
                        dcdbBmmbnrList.add(dcdbBmmbnr);
                    }
                }
                String zybm = zyrw.getZybm();
                if(StringUtils.isNotBlank(zybm)){
                    String[] zybmArr = zybm.split(",");
                    for(int i=0;i<zybmArr.length;i++){
                        DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                        String cbbmid = zybmArr[i].split("@")[0];//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                        String ssbm = zybmArr[i].split("@")[1];
                        String cbrid = zybmArr[i].split("@")[2];
                        dcdbBmmbnr.setId(UUIDUtils.getUUID());
                        dcdbBmmbnr.setNdid(ndid);
                        dcdbBmmbnr.setYdnrid(zyrw.getYdnrid());
                        dcdbBmmbnr.setZyrwid(zyrw.getId());
                        dcdbBmmbnr.setSsbm(ssbm);
                        dcdbBmmbnr.setBmlx("zz");
                        dcdbBmmbnr.setCbrid(cbrid);
                        dcdbBmmbnr.setStatus("101");
                        dcdbBmmbnr.setCbbmid(cbbmid);
                        dcdbBmmbnr.setUserid(cbrid);
                        dcdbBmmbnr.setJdlx("1");
                        dcdbBmmbnrList.add(dcdbBmmbnr);
                    }
                }
                /*String phbm = zyrw.getPhbm();
                if(StringUtils.isNotBlank(phbm)){
                    String[] phbmArr = phbm.split(",");
                    for(int i=0;i<phbmArr.length;i++){
                        DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                        String cbbmid = phbmArr[i].split("@")[0];//526BFBC6DDB74DE38C845FDC993DE3E8 监察处 e7e248f4-0fc0-40a4-8eb4-a9d568d575ea
                        String ssbm = phbmArr[i].split("@")[1];
                        String cbrid = phbmArr[i].split("@")[2];
                        dcdbBmmbnr.setId(UUIDUtils.getUUID());
                        dcdbBmmbnr.setNdid(ndid);
                        dcdbBmmbnr.setYdnrid(zyrw.getYdnrid());
                        dcdbBmmbnr.setZyrwid(zyrw.getId());
                        dcdbBmmbnr.setSsbm(ssbm);
                        dcdbBmmbnr.setBmlx("ph");
                        dcdbBmmbnr.setCbrid(cbrid);
                        dcdbBmmbnr.setStatus("101");
                        dcdbBmmbnr.setCbbmid(cbbmid);
                        dcdbBmmbnr.setUserid(cbrid);
                        dcdbBmmbnr.setJdlx("1");
                        dcdbBmmbnrList.add(dcdbBmmbnr);
                    }
                }*/
            }
            bmmbnrDao.deleteByNdid(ndid);
            for(DcdbBmmbnr dcdbBmmbnr:dcdbBmmbnrList){
                bmmbnrDao.insertSelective(dcdbBmmbnr);
            }
            DcdbNdrw dcdbNdrw = new DcdbNdrw();
            dcdbNdrw.setId(ndid);
            dcdbNdrw.setNd(DateUtil.getYear());
            dcdbNdrw.setNdrwmc(rwmctext);
            UserEntity user = (UserEntity) se.getAttribute("User");
            dcdbNdrw.setCzr(user.getName());
            dcdbNdrw.setCzsj(DateUtil.getDate());
            ndrwService.deleteById(ndid);
            k = ndrwService.insertNdrw(dcdbNdrw);
            resultMap.put("code","1");
            resultMap.put("msg","提交成功");
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping("faqi")
    @ResponseBody
    public void faqi(HttpServletResponse response,String id,String jidu){
        HashMap<Object, Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isNotBlank(jidu)){
                if("1".equals(jidu)){
                    DcdbNdrw dcdbNdrw = new DcdbNdrw();
                    //改为一季度发起中
                    dcdbNdrw.setCzzt("12");
                    dcdbNdrw.setId(id);
                    ndrwService.updateStatus(dcdbNdrw);
                    DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                    dcdbBmmbnr.setNdid(id);
                    //状态改为季度填写
                    dcdbBmmbnr.setStatus("115");
                    bmmbnrDao.updatefqgx(dcdbBmmbnr);
                }else if("2".equals(jidu)){
                    //第二季度发起
                    DcdbNdrw dcdbNdrw = new DcdbNdrw();
                    dcdbNdrw.setCzzt("22");
                    dcdbNdrw.setId(id);
                    ndrwService.updateStatus(dcdbNdrw);
                    DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                    dcdbBmmbnr.setNdid(id);
                    //状态改为二季度填写
                    dcdbBmmbnr.setStatus("215");
                    dcdbBmmbnr.setJdlx("2");
                    bmmbnrDao.updatefqgx(dcdbBmmbnr);
                }else if("3".equals(jidu)){
                    //第三季度发起
                    DcdbNdrw dcdbNdrw = new DcdbNdrw();
                    dcdbNdrw.setCzzt("32");
                    dcdbNdrw.setId(id);
                    ndrwService.updateStatus(dcdbNdrw);
                    DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                    dcdbBmmbnr.setNdid(id);
                    //状态改为三季度填写
                    dcdbBmmbnr.setStatus("315");
                    dcdbBmmbnr.setJdlx("3");
                    bmmbnrDao.updatefqgx(dcdbBmmbnr);
                }else if("4".equals(jidu)){
                    //第四季度发起
                    DcdbNdrw dcdbNdrw = new DcdbNdrw();
                    dcdbNdrw.setCzzt("42");
                    dcdbNdrw.setId(id);
                    ndrwService.updateStatus(dcdbNdrw);
                    DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
                    dcdbBmmbnr.setNdid(id);
                    //状态改为四季度填写
                    dcdbBmmbnr.setStatus("415");
                    dcdbBmmbnr.setJdlx("4");
                    bmmbnrDao.updatefqgx(dcdbBmmbnr);
                }
            }
            resultMap.put("code","1");
            resultMap.put("msg","发起成功");
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write( gson.toJson(resultMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
