package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbNdrw;
import com.bm.index.model.DcdbProjectFile;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbJdtbService;
import com.bm.index.service.DcdbMbSpService;
import com.bm.index.util.ConfigProperties;
import com.bm.index.util.FileUtil;
import com.bm.index.util.JxlsExcelUtils;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.WebUtils;

/**
 * @ClassName JdtbController
 * @Description 督查督办-季度填报Controller
 * @Author daipx
 * @Date 2019/3/5 9:40
 * @Version 1.0
 **/

@Controller
@RequestMapping("jdtb")
public class JdtbController {
    @Autowired
    DcdbJdtbService dcdbJdtbService;
    @Autowired
    DcdbBmmbnrDao dcdbBmmbnrDao;
    @Autowired
    private DcdbMbSpService dcdbmbspservice;

    private Gson mGson = new Gson();

    private static Log logger = LogFactory.getLog(JdtbController.class);

    /**
     * 查询部门填报任务列表
     *
     * @param se
     * @return
     */
    @RequestMapping("/queryBmdbList.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryBmdbList(HttpSession se,  DcdbNdrw dcdbNdrw) {
        JSONObject jsonobject = new JSONObject();
        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            if(user == null){
                jsonobject.put("msg", "登录超时，请重新进入督查督办系统");
            }else{
                jsonobject.put("msg", "查询成功");
                dcdbNdrw.setCbrid(user.getId()); //当前登录人
                dcdbNdrw.setIsBgs("no");
                //当登录人为办公室人员时
                String bmmc=user.getBmmc();
               /* if("督查组".equals(bmmc) || "办公室".equals(bmmc)){

                    dcdbNdrw.setIsBgs("yes");
                }*/              
                List<DcdbNdrw> dcdbNdrws = dcdbBmmbnrDao.queryBmdbList(dcdbNdrw);
                jsonobject.put("data", dcdbNdrws);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }


    /**
     * 待办列表跳转填报页面
     *
     * @param se
     * @return
     */
    @RequestMapping("/toPage.do")
    public String toPage(HttpSession se, Model model, @ModelAttribute("status") String status,
                              @ModelAttribute("ndid") String ndid, @ModelAttribute("cbbmid") String cbbmid, @ModelAttribute("ssbm") String ssbm) {
        String path = "";
        String ywType = status.substring(1, 2);
        String nodeId = status.substring(2, 3);
        UserEntity user = (UserEntity) se.getAttribute("User");
        String name = user.getName();
        String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
        try {
            //填报全年目标
            if ( "0".equals(ywType) ) {
                if("1".equals(nodeId) || "2".equals(nodeId)){
                    path = "/ndgzrw/zyrwlist";
                }
                if("3".equals(nodeId) || "4".equals(nodeId) || "5".equals(nodeId)){
                    path = "/ndgzrw/zyrwlistbgs";
                }

            //填报季度完成情况
            } else {
                path = "/ndgzrw/bmjdsp";
           //如果是办公室节点
                if ( "7".equals(nodeId) || "8".equals(nodeId) ) {
                    path = "/ndgzrw/bmjdsp_bgs";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("lcjd",nodeId);
        model.addAttribute("currentUserName",name);
        model.addAttribute("currentdate",date);
        model.addAttribute("name",user.getName());
        return path;
    }

    /**
     * 查询部门 季度目标 完成情况
     * 入参： 年度、季度、部门、状态码
     *
     * @param se
     * @param dcdbBmmbnr
     * @return
     */
    @RequestMapping("/queryBmJdmb.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryBmJdmb(HttpSession se, DcdbBmmbnr dcdbBmmbnr) {
        JSONObject jsonobject = new JSONObject();
        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            dcdbBmmbnr.setUserid(user.getId());
            if( StringUtils.isBlank(dcdbBmmbnr.getCbbmid()) || "undefined".equals(dcdbBmmbnr.getCbbmid()) || "null".equals(dcdbBmmbnr.getCbbmid())){
                List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
                dcdbBmmbnr.setCbbmid(users.get(0).getBmbm());
            }
            List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.selectJdtb(dcdbBmmbnr);
            for (DcdbBmmbnr dcdb : dcdbBmmbnrs) {
                List<DcdbProjectFile> dcdbProjectFile = dcdbBmmbnrDao.selectFileByCol("BMRWID", dcdb.getId(),dcdbBmmbnr.getStatus().substring(0,1 ));
                dcdb.setFile(dcdbProjectFile);
            }
            jsonobject.put("data", dcdbBmmbnrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 更新某一条的部门季度完成情况
     * 入参：状态码+id
     *
     * @param se
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateJdwcqk.do")
    @ResponseBody
    public void updateJdwcqk(HttpSession se, HttpServletRequest request, HttpServletResponse response, DcdbBmmbnr dcdbBmmbnr) {
        Map<String, Object> mapRes = new HashMap<>();
        String msg = "更新成功";
        String code = "1";
        try {
            int res = dcdbJdtbService.updateJdwcqk(dcdbBmmbnr);
            if ( res != 1 ) {
                msg = "更新失败,存在"+res+"记录";
                code = "2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            code = "2";

        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }

    /**
     * 更新批示意见and流转
     * 入参;年度+季度+承办部门ID+状态码
     *
     * @param se
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateYjAndFollow.do")
    @ResponseBody
    public void updateYjAndFollow(HttpSession se, HttpServletRequest request, HttpServletResponse response, String ndid, String status,
                                  String cbbmid, String userid, String bmfzryj, String bgsyj, String bgsfzryj,
                                  String flag, String nextuserid,String bmfzrqm,String bmfzrrq,String bgsqm,String bgsrq,String bgsfzrqm,String bgsfzrrq,String ssbm) {

        Map<String, Object> mapRes = new HashMap<>();
        String msg = "提交成功";
        String code = "1";

        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            DcdbBmmbnr dcdbBmmbnr = new DcdbBmmbnr();
            //-------start----操作批示意见表

            if( StringUtils.isBlank(cbbmid) || "undefined".equals(cbbmid) || "null".equals(cbbmid) ){
                List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
                cbbmid=users.get(0).getBmbm();
            }
            String yjid = ndid + "@" + status.substring(0, 1) + "@" + cbbmid;
            dcdbBmmbnr.setYjid(yjid);
            dcdbBmmbnr.setBmfzryj(bmfzryj);
            dcdbBmmbnr.setBgsyj(bgsyj);
            dcdbBmmbnr.setBgsfzryj(bgsfzryj);
            dcdbBmmbnr.setPreuserid(user.getId());
            dcdbBmmbnr.setBmfzrqm(bmfzrqm);
            dcdbBmmbnr.setBmfzrrq(bmfzrrq);
            dcdbBmmbnr.setBgsqm(bgsqm);
            dcdbBmmbnr.setBgsrq(bgsrq);
            dcdbBmmbnr.setBgsfzrqm(bgsfzrqm);
            dcdbBmmbnr.setBgsfzrrq(bgsfzrrq);
            dcdbBmmbnr.setSsbm(ssbm);
            List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.selectKhpzById(yjid,ssbm);
            if ( CollectionUtils.isNotEmpty(dcdbBmmbnrs) ) {
                dcdbJdtbService.updateKhpzById(dcdbBmmbnr);
            } else {
                dcdbJdtbService.insertKhpz(dcdbBmmbnr);
            }
            //---------end  -----操作批示意见表



          //---------start -----插入已办表
        /*  if( "submit".equals(flag)){
            DcdbBmmbnr dcdbYb = new DcdbBmmbnr();
            dcdbYb.setNdid(ndid);
            dcdbYb.setUserid(user.getId());
            dcdbYb.setStatus(status);
            dcdbYb.setCbbmid(cbbmid);
            dcdbYb.setJdlx(status.substring(0, 1));
            if( StringUtils.isBlank(cbbmid) || "undefined".equals(cbbmid) || "null".equals(cbbmid)){
              List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
              dcdbYb.setCbbmid(users.get(0).getBmbm());
            }
            List<DcdbBmmbnr> dcdbBmmbnrss = dcdbJdtbService.selectJdtb(dcdbYb);
            dcdbJdtbService.insertNdrwYb(dcdbBmmbnrss);
          }*/
          //---------end -----插入已办表


            //--------start----操作部门内容表，更新状态和CBRID
            DcdbBmmbnr dcdbBmmbnr1 = new DcdbBmmbnr();
            dcdbBmmbnr1.setNdid(ndid);
            dcdbBmmbnr1.setJdlx(status.substring(0, 1));
            dcdbBmmbnr1.setCbbmid(cbbmid);
            dcdbBmmbnr1.setSsbm(ssbm);

            int nodeid = Integer.parseInt(status.substring(2, 3));
            //提交
            if ( "submit".equals(flag) ) {
                dcdbBmmbnr1.setUserid(nextuserid);
                nodeid = Integer.parseInt(status.substring(2, 3)) + 1;
             //退回
            } else if ( "rebak".equals(flag) ) {
                dcdbBmmbnr1.setUserid(dcdbBmmbnrs.get(0).getPreuserid()); //设置成上一步审批人
                nodeid = Integer.parseInt(status.substring(2, 3)) - 1;
            }else{
              //保存
                dcdbBmmbnr1.setUserid(user.getId());
                msg = "保存成功";
            }
            dcdbBmmbnr1.setStatus(status.substring(0, 2) + String.valueOf(nodeid));

            //当是最后一步提交审批时,把每一条记录userid更成 承办人id
            if("8".equals(status.substring(2, 3)) && "submit".equals(flag) ){
                dcdbBmmbnr1.setUserid(user.getId());
                dcdbBmmbnr1.setStatus(status);
                String jdlx = status.substring(0, 1); //季度类型
                dcdbBmmbnr1.setJdlx(jdlx);
                dcdbBmmbnr1.setColnum("YJDMB AS YJDMB , YJDWCQK AS YJDWCQK , YJDPG AS YJDPG");
                dcdbBmmbnr1.setIsBgs("no");
                List<DcdbBmmbnr> list=   dcdbBmmbnrDao.selectJdtb(dcdbBmmbnr1);
                for (DcdbBmmbnr dcdb:list){
                    DcdbBmmbnr dcdbBmmbnr3 = new DcdbBmmbnr();
                    dcdbBmmbnr3.setId(dcdb.getId());
                    dcdbBmmbnr3.setUserid(dcdb.getCbrid());
                    dcdbBmmbnr3.setStatus(status.substring(0, 2)+"9");
                    dcdbJdtbService.updateJdwcqk(dcdbBmmbnr3);
                }

            }else{
                int res = dcdbJdtbService.updateJdwcqk(dcdbBmmbnr1);
            }

            //---------end -----操作部门内容表

        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            code = "2";

        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }

    /**
     * 查询流转的审批意见
     *
     * @param se
     * @param request
     * @param response
     * @param ndid
     * @param status
     * @param cbbmid
     */
    @RequestMapping("/queryPsyj.do")
    @ResponseBody
    public void queryPsyj(HttpSession se, HttpServletRequest request, HttpServletResponse response, String ndid, String status, String cbbmid, String ssbm) {
        Map<String, Object> mapRes = new HashMap<>();
        String msg = "查询成功";
        String code = "1";
        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            if( StringUtils.isBlank(cbbmid) || "undefined".equals(cbbmid) || "null".equals(cbbmid)){
                List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
                cbbmid=users.get(0).getBmbm();
            }
            //查询批示意见表，by ID =  年度+季度+承办部门ID
            String yjid = ndid + "@" + status.substring(0, 1) + "@" + cbbmid;
            List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.selectKhpzById(yjid,ssbm);
            DcdbBmmbnr resBmnr = new DcdbBmmbnr();
            if ( CollectionUtils.isNotEmpty(dcdbBmmbnrs) ) {
                resBmnr = dcdbBmmbnrs.get(0);
            }
            mapRes.put("data", resBmnr);

        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            code = "2";

        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }



    /**
     * 判断登录人，所有分配的任务，是否全部为配合（办公室除外）
     * @param se
     * @param dcdbBmmbnr
     * @return  当code=1时，所属部门不全是配合部门。
     */
    @RequestMapping("/queryIsAllPh.do")
    @ResponseBody
    public void queryIsAllPh(HttpServletResponse response, HttpSession se, DcdbBmmbnr dcdbBmmbnr) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "";
        try {
            String status = dcdbBmmbnr.getStatus();
            String jdlx = status.substring(0, 1); //季度类型
            dcdbBmmbnr.setJdlx(jdlx);
            dcdbBmmbnr.setIsBgs("no");

           /*    //如果登录是办公室人员，需要传入cbbmid
                dcdbBmmbnr.setIsBgs("yes");
                */

            List<String> bmlxs = dcdbBmmbnrDao.selectIsAllPh(dcdbBmmbnr);
            int js = 1;
            for (String bmlx : bmlxs) {
                if ( "ph".equals(bmlx) ) {
                    js++;
                }
            }
            if ( bmlxs.size() != js ) {
                code = "2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }

    /**
     * 查询部门列表
     * @param ndid
     * @return
     */
    @RequestMapping("/queryBmList.do")
    @ResponseBody
    public void queryBmList(HttpServletResponse response, HttpSession se, String ndid) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "";
        try {
            List<Map<String,String>> bmList = dcdbBmmbnrDao.queryBmList(ndid);
            mapRes.put("data", bmList);

        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }
    /**
     * 督查督办文件上传
     * @param dcdbProjectFile
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/filesUploadDcdb.do")
    public void filesUploadHyjy(DcdbProjectFile dcdbProjectFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> mapRes = new HashMap<>();
        Boolean flag;
        String code = "1";
        String msg = "上传成功";
        try {
            Map<String, Object> map = new HashedMap();
            List<MultipartFile> fileList = new ArrayList<>();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

            //判断 request 是否有文件上传,即多部分请求
            if ( multipartResolver.isMultipart(request) ) {
                //转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    //取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if ( file != null ) {
                        map.put("fileName", file.getOriginalFilename());
                        fileList.add(file);
                    }
                }
            }
            flag = dcdbJdtbService.uploadFiles(fileList, dcdbProjectFile, request);
            if ( flag == false ) {
                msg = "上传失败";
                code = "2";
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "上传失败，" + e.getMessage();
            code = "2";
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }


    }

    /**
     * 督查督办文件下载
     */
    @RequestMapping(value = "/downLoadFileDcdb.do")
    public void downLoadFileDcdb(String id,String ywtype, HttpServletResponse response) {
        try {
            //处理数据
            List<DcdbProjectFile> dcdbProjectFile = dcdbBmmbnrDao.selectFileByCol("ID", id,ywtype);

            Map<String, String> map = new HashedMap();
            map.put("XSBH", dcdbProjectFile.get(0).getBmrwid());
            map.put("FILENAME", id);
            map.put("TARGETFILENAME", dcdbProjectFile.get(0).getFilename());

            FileUtil.downloadFile(response, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 督查督办-文件列表查询
     * @param ywtype
     * @return
     */
    @RequestMapping("/selectUploadFiles.do")
    @ResponseBody
    public  List<DcdbProjectFile> selectUploadFiles(String col,String bmrwid,String ywtype) {
        List<DcdbProjectFile> list = dcdbBmmbnrDao.selectFileByCol(col, bmrwid,ywtype);
        return list;
    }


    /** 全年目标和季度完成情况 导出excel
     *
     * @param ndrwId 年度任务id
     * @param cbbmId 承办部门id
     * @param status 节点编码
     * @param flag 完成情况 or 目标
     * @param httpServletRequest
     * @param response
     * @param se
     */
    @RequestMapping("/exportExcel")
    public void importBm(String ndrwId,String cbbmId,String status,String flag,HttpServletRequest httpServletRequest,HttpServletResponse response,  HttpSession se){
        String path = null;
        try {
            path = httpServletRequest.getRealPath("/");
        UserEntity user = (UserEntity) se.getAttribute("User");
      if("jdmb".equals(flag)){
          Map<String,Object> condtionMap=new HashMap<>();
          String currentuserId = user.getId(); //当前登录人ID
          condtionMap.put("startCount",0);
          condtionMap.put("endCount",100);
          condtionMap.put("ndrwId",ndrwId);
          condtionMap.put("userId",currentuserId);
          if( StringUtils.isBlank(cbbmId) || "undefined".equals(cbbmId) || "null".equals(cbbmId)){
              List<UserEntity> users = dcdbBmmbnrDao.loginQuery(currentuserId);
              cbbmId=users.get(0).getBmbm();
          }

          condtionMap.put("cbbmid",cbbmId);
          List<DcdbBmmbnr> dbTable = dcdbmbspservice.getZYRwTable(condtionMap);
          String mbname="jdmb.xls";
          String templateFile = path+"download/execlTemplate/"+mbname;
          Map<String,Object> beas = new HashMap<>();
          beas.put("datalist",dbTable);
          JxlsExcelUtils.exportExcel(beas, templateFile, path, response);
      }else if("wcqk".equals(flag)){
          DcdbBmmbnr dcdbBmmbnr =new DcdbBmmbnr();
          dcdbBmmbnr.setUserid(user.getId());
          dcdbBmmbnr.setNdid(ndrwId);
          dcdbBmmbnr.setStatus(status);
          dcdbBmmbnr.setCbbmid(cbbmId);
          if( StringUtils.isBlank(dcdbBmmbnr.getCbbmid()) || "undefined".equals(dcdbBmmbnr.getCbbmid()) || "null".equals(dcdbBmmbnr.getCbbmid())){
              List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
              dcdbBmmbnr.setCbbmid(users.get(0).getBmbm());
          }
          List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.selectJdtb(dcdbBmmbnr);
          String jdlx = status.substring(0, 1); //季度类型
          String mbname="";
          switch (jdlx) {
              case "1":
                  mbname = "yjdwcqk.xls";
                  break;
              case "2":
                  mbname = "bnwcqk.xls";
                  break;
              case "3":
                  mbname = "sjdwcqk.xls";
                  break;
              case "4":
                  mbname = "qnwcqk.xls";
                  break;
          }
          String templateFile = path+"download/execlTemplate/"+mbname;
          Map<String,Object> beas = new HashMap<>();
          beas.put("datalist",dcdbBmmbnrs);
          JxlsExcelUtils.exportExcel(beas, templateFile, path, response);
      }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 针对罗艳、王小玉 待办事项页面查询
     * @param se
     * @param dcdbNdrw
     * @return
     */
    @RequestMapping("/queryBmdbListBgs.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryBmdbListBgs(HttpSession se, String ndid, DcdbNdrw dcdbNdrw) {
        JSONObject jsonobject = new JSONObject();
        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            if(user == null){
                jsonobject.put("msg", "登录超时，请重新进入督查督办系统");
            }else{
                jsonobject.put("msg", "查询成功");
                dcdbNdrw.setCbrid(user.getId()); //当前登录人
                dcdbNdrw.setId(ndid);//年度任务编号
                List<DcdbNdrw> dcdbNdrws = dcdbBmmbnrDao.queryBmdbListBgsByNdid(dcdbNdrw);
                jsonobject.put("data", dcdbNdrws);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

  /**
   * 已办事项列表页-查询
   * @param se
   * @param dcdbNdrw
   * @return
   */
  @RequestMapping("/queryBmdbListYb.do")
  @ResponseBody
  @PageHelperAnn
  public JSONObject queryBmdbListYb(HttpSession se,  DcdbNdrw dcdbNdrw) {
    JSONObject jsonobject = new JSONObject();
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      if(user == null){
        jsonobject.put("msg", "登录超时，请重新进入督查督办系统");
      }else{
        jsonobject.put("msg", "查询成功");
        dcdbNdrw.setCbrid(user.getId()); //当前登录人
        List<DcdbNdrw> dcdbNdrws = dcdbBmmbnrDao.queryBmdbListYb(dcdbNdrw);
        jsonobject.put("data", dcdbNdrws);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonobject;
  }

    /**
     * 已办事项-页面跳转
     * @param se
     * @param model
     * @param status
     * @param ndid
     * @param cbbmid
     * @return
     */
  @RequestMapping("/toPageYb.do")
  public String toPageYb(HttpSession se, Model model, @ModelAttribute("status") String status,
      @ModelAttribute("ndid") String ndid, @ModelAttribute("cbbmid") String cbbmid) {
    String path = "";
    String ywType = status.substring(1, 2);
    String nodeId = status.substring(2, 3);
    UserEntity user = (UserEntity) se.getAttribute("User");
    String name = user.getName();
    String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
    try {
      //填报全年目标
      if ( "0".equals(ywType) ) {
          path = "/ndgzrw/ybmxlist_mb";
        //填报季度完成情况
      } else {
        path = "/ndgzrw/ybmxlist_wcqk";
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addAttribute("lcjd",nodeId);
    model.addAttribute("currentUserName",name);
    model.addAttribute("currentdate",date);
    model.addAttribute("name",user.getName());
    return path;
  }

    /**
     * 已办事项详情页-主要任务列表
     * @param se
     * @param dcdbBmmbnr
     * @return
     */
  @RequestMapping("/queryZyrwListYb.do")
  @ResponseBody
  @PageHelperAnn
  public JSONObject queryZyrwListYb(HttpSession se, DcdbBmmbnr dcdbBmmbnr) {
    JSONObject jsonobject = new JSONObject();
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      dcdbBmmbnr.setUserid(user.getId());
      if( StringUtils.isBlank(dcdbBmmbnr.getCbbmid()) || "undefined".equals(dcdbBmmbnr.getCbbmid()) || "null".equals(dcdbBmmbnr.getCbbmid())){
        List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
        dcdbBmmbnr.setCbbmid(users.get(0).getBmbm());
      }
      List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.queryZyrwListYb(dcdbBmmbnr);

      String ywType = dcdbBmmbnr.getStatus().substring(1, 2);

      if("1".equals(ywType)){
        for (DcdbBmmbnr dcdb : dcdbBmmbnrs) {
          List<DcdbProjectFile> dcdbProjectFile = dcdbBmmbnrDao.selectFileByCol("BMRWID", dcdb.getId(),dcdbBmmbnr.getStatus().substring(0,1 ));
          dcdb.setFile(dcdbProjectFile);
        }
      }
      jsonobject.put("data", dcdbBmmbnrs);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonobject;
  }

    /**
     * 填报进度页面查询
     * @param se
     * @param dcdbNdrw
     * @return
     */
    @RequestMapping("/queryBmdbListTbjd.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryBmdbListTbjd(HttpSession se,  DcdbNdrw dcdbNdrw) {
        JSONObject jsonobject = new JSONObject();
        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            if(user == null){
                jsonobject.put("msg", "登录超时，请重新进入督查督办系统");
            }else{
                jsonobject.put("msg", "查询成功");
                List<DcdbNdrw> dcdbNdrws = dcdbBmmbnrDao.queryBmdbListTbjd(dcdbNdrw);
                jsonobject.put("data", dcdbNdrws);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 进入填报进度页面
     * @param se
     * @param model
     * @param status
     * @param ndid
     * @param cbbmid
     * @param userid
     * @param tblx
     * @return
     */
    @RequestMapping("/toPageTbjd.do")
    public String toPageTbjd(HttpSession se, Model model, @ModelAttribute("status") String status,
        @ModelAttribute("ndid") String ndid, @ModelAttribute("cbbmid") String cbbmid,@ModelAttribute("userid") String userid,@ModelAttribute("tblx") String tblx,@ModelAttribute("ssbm") String ssbm) {
        String path = "/ndgzrw/tbjdmxlist";
        try {
            String ywType = status.substring(1, 2);
            String nodeId = status.substring(2, 3);
            model.addAttribute("lcjd",nodeId);
            model.addAttribute("tblx",ywType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * 查询填报进度页面列表
     * @param se
     * @param dcdbBmmbnr
     * @return
     */
    @RequestMapping("/selectZyrwListTbjd.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject selectZyrwListTbjd(HttpSession se, DcdbBmmbnr dcdbBmmbnr) {
        JSONObject jsonobject = new JSONObject();
        try {
            List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.queryZyrwListTbjdmx(dcdbBmmbnr);
            String ywType = dcdbBmmbnr.getStatus().substring(1, 2);

            if("1".equals(ywType)){
                for (DcdbBmmbnr dcdb : dcdbBmmbnrs) {
                    List<DcdbProjectFile> dcdbProjectFile = dcdbBmmbnrDao.selectFileByCol("BMRWID", dcdb.getId(),dcdbBmmbnr.getStatus().substring(0,1 ));
                    dcdb.setFile(dcdbProjectFile);
                }
            }
            jsonobject.put("data", dcdbBmmbnrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     *提交时检验完成情况是否填写
     * @param se
     * @param dcdbBmmbnr
     * @return
     */
    @RequestMapping("/volidateJdtb.do")
    @ResponseBody
    public JSONObject volidateJdtb(HttpSession se, DcdbBmmbnr dcdbBmmbnr) {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("code", 1);
        try {
            UserEntity user = (UserEntity) se.getAttribute("User");
            dcdbBmmbnr.setUserid(user.getId());
            if( StringUtils.isBlank(dcdbBmmbnr.getCbbmid()) || "undefined".equals(dcdbBmmbnr.getCbbmid()) || "null".equals(dcdbBmmbnr.getCbbmid())){
                List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
                dcdbBmmbnr.setCbbmid(users.get(0).getBmbm());
            }
            List<DcdbBmmbnr> dcdbBmmbnrs = dcdbJdtbService.selectJdtb(dcdbBmmbnr);
            for (DcdbBmmbnr dcdb : dcdbBmmbnrs) {
                String yjdwcqk = dcdb.getYjdwcqk();
                if(StringUtils.isBlank(yjdwcqk)){
                    jsonobject.put("code", 0);
                    return jsonobject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
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
