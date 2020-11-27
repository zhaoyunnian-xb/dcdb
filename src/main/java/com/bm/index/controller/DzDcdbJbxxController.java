package com.bm.index.controller;

import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.DcdbJbxxDao;
import com.bm.index.model.*;
import com.bm.index.service.FileService;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dzjbxx")
public class DzDcdbJbxxController {
    @Autowired
    private com.bm.index.service.DcdbJbxxService DcdbJbxxService;
    @Autowired
    private DcdbDjlbDbDao dcdbdjlbdbdao;
    @Autowired
    private DcdbBmmbnrDao dcdbBmmbnrDao;
    @Autowired
    private DcdbJbxxDao dcdbjbxxdao;
   @Autowired
   private FileService fileService;
    private Gson mGson = new Gson();
    //查询
    @RequestMapping("/dzjbxxdetail.do")
    @ResponseBody
    public DcdbJbxx dzjbxxdetail(String wh, HttpServletRequest request, Model model){
        DcdbJbxx dcdbjbxx =  DcdbJbxxService.getById(wh);
        return dcdbjbxx;
    }
    //案件基本信息的保存（党组决策）
    @RequestMapping("/dzjbxxsave.do")
    @ResponseBody
    public Map<String,Object> dzjbxxsave(HttpServletRequest request, Model model, DcdbJbxx dcdbjbxx, HttpSession se, String nodeId, String nodeName, String lclx,String dzId,String type){
        Map<String,Object> resultMap = new HashMap<>();
                String  wh = dcdbjbxx.getWh();
                DcdbJbxx  jbxx = DcdbJbxxService.getById(wh);//根据wh查询基本信息
                Map  map = new HashMap();
                map.put("wh",wh);
                List<DcdbDjlbDb> list =  dcdbdjlbdbdao.selectDbByWh(map);
                String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
                DcdbDjlbDb  dcdbdjlbdb  = new DcdbDjlbDb();
                UserEntity user = (UserEntity) se.getAttribute("User");
                String currentuserId = user.getId(); //当前登录人ID
                String currentUserName = user.getName();
                dcdbdjlbdb.setWh(wh);
                dcdbdjlbdb.setCbbmmc(dcdbjbxx.getCbbmmc());
                dcdbdjlbdb.setCbbmid(dcdbjbxx.getCbbm());
                dcdbdjlbdb.setUserid(currentuserId);
                dcdbdjlbdb.setUsername(user.getName());
                dcdbdjlbdb.setBllx(dcdbjbxx.getBllx());
                dcdbdjlbdb.setPslx(dcdbjbxx.getPslx());
                if("2".equals(type)){
                    int dzts = Integer.valueOf(dcdbjbxx.getCbqx());
                    if(dzts < 3){
                        dcdbdjlbdb.setYjqk("2");
                    }else{
                        dcdbdjlbdb.setYjqk("1");
                    }
                }
                if (jbxx == null){
                    int i  = DcdbJbxxService.save(dcdbjbxx);
                    if(i == 1){
                        if(list.size() == 0){
                            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                            dcdbdjlbdb.setNodeId("0101");
                            dcdbdjlbdb.setNodeName("党组新建");
                            dcdbdjlbdb.setId(wh);
                            dcdbdjlbdb.setLclx(lclx);
                            dcdbdjlbdb.setCjrq(date);
                            dcdbdjlbdb.setCjr(currentUserName);
                            dcdbdjlbdb.setLzsj(date2);
                            dcdbdjlbdb.setPreid(wh);
                            int r = dcdbdjlbdbdao.insertSelective(dcdbdjlbdb);
                            if(r==1){
                                resultMap.put("code",1);
                                resultMap.put("msg","保存成功");
                                creatWs(request,dzId, dcdbjbxx.getBllx(), wh);
                            }else{
                                resultMap.put("code",2);
                                resultMap.put("msg","保存失败");
                            }
                        }
                    }else{
                        resultMap.put("code",2);
                        resultMap.put("msg","保存失败");
                    }
                }else{
                    int i = DcdbJbxxService.update(dcdbjbxx);
                    if(i == 1){
                        DcdbDjlbDb dcdbdjlbdb2 =  dcdbdjlbdbdao.selectByPrimaryKey(dzId);
                        if(dcdbdjlbdb2 != null){
                            String newId = dcdbdjlbdb2.getId();
                            int r = 1;
                            if ("0101".equals(nodeId)|| "01011".equals(nodeId)|| "0102".equals(nodeId)) {
                                DcdbDjlbDb dcdbdjlbdb1 = new DcdbDjlbDb();
                                dcdbdjlbdb1.setId(newId);
                                dcdbdjlbdb1.setCbbmmc(dcdbjbxx.getCbbmmc());
                                dcdbdjlbdb1.setCbbmid(dcdbjbxx.getCbbm());
                                dcdbdjlbdb1.setBllx(dcdbjbxx.getBllx());
                                dcdbdjlbdb1.setYjqk(dcdbdjlbdb.getYjqk());
                                dcdbdjlbdb1.setPslx(dcdbdjlbdb.getPslx());
                                r = dcdbdjlbdbdao.updateByPrimaryKeySelective(dcdbdjlbdb1);
                            }
                            if(r == 1){
                                resultMap.put("code",1);
                                resultMap.put("msg","保存成功");
                                creatWs(request,dzId, dcdbjbxx.getBllx(), wh);
                            }else{
                                resultMap.put("code",2);
                                resultMap.put("msg","保存失败");
                            }
                        }
                    }else{
                        resultMap.put("code",2);
                        resultMap.put("msg","保存失败");
                    }
                }
        return  resultMap;
    }

    @RequestMapping("/selectUploadFiles.do")
    @ResponseBody
    public  List<DcdbProjectFile> selectUploadFiles(HttpServletRequest request, String id, String ywtype,String wh) {
        List<DcdbProjectFile> list = dcdbjbxxdao.selectUploadFilesDZ(id, ywtype,wh);
        return list;
    }


  /**
   * 党组流程-生成事项督办单
   * @param request
   * @param id 待办ID
   * @param bllx 办理类型
   * @param wh 文号
   */
  public void creatWs(HttpServletRequest request, String id, String bllx, String wh) {
    //生成文书
      Map<String,String> mapws = new HashedMap();
      mapws.put("nodeId","1");
      mapws.put("dbid",id); //待办ID
      mapws.put("bllx",bllx); //办理类型
      mapws.put("wh",wh); //文号
      mapws.put("splx","blqk"); //意见表审批类型
      if("党组委会督办".equals(bllx)){
        mapws.put("wsbm","督办单"); //前台显示名称
        mapws.put("file","山东省人民检察院党组会决定事项督办单.doc"); //模板名称
        boolean flag1 = fileService.jcjyScws(request, mapws);
      }else if("检察长办公会督办".equals(bllx)){
        mapws.put("wsbm","督办单"); //前台显示名称
        mapws.put("file","山东省人民检察院检察长办公会决定事项督办单.doc"); //模板名称
        boolean flag2 = fileService.jcjyScws(request, mapws);
      }
  }


    /**
     * 上级决策--根据Id查询某一待办案件的详情
     */
    @RequestMapping("/sjjcjbxxdetail.do")
    @ResponseBody
    public DcdbSjjcsxDj sjjcjbxxdetail(String wh, HttpServletRequest request, Model model){

        DcdbSjjcsxDj dcdbsjjcsxdj = DcdbJbxxService.getDcdbSjjcsxDjById(wh);
        return  dcdbsjjcsxdj;

    }


    //案件基本信息的保存（上级决策）
    @RequestMapping("/sjjcjbxxsave.do")
    @ResponseBody
    public Map<String,Object> sjjcjbxxsave(HttpServletRequest request, Model model, DcdbSjjcsxDj dcdbsjjcsxdj, HttpSession se, String nodeId, String nodeName, String lclx,String type){
        Map<String,Object> resultMap = new HashMap<>();
        //根据Id进行基本信息的案件查询
        DcdbSjjcsxDj dcdbsjjcsxdj1 = DcdbJbxxService.getDcdbSjjcsxDjById(dcdbsjjcsxdj.getWh());
        //根据Id进行待办表的查询
        DcdbSjjcsxDj dcdbsjjcsxdj2 = DcdbJbxxService.getDcdbSjjcsxDbById(dcdbsjjcsxdj.getId());
        if(dcdbsjjcsxdj1 == null){
            int i = DcdbJbxxService.saveDcdbSjjcsxDj(dcdbsjjcsxdj);
            if(i == 1){
                    if(dcdbsjjcsxdj2 == null){
                        String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
                        String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        UserEntity user = (UserEntity) se.getAttribute("User");
                        String currentuserId = user.getId(); //当前登录人ID
                        String currentUserName = user.getName();
                        dcdbsjjcsxdj.setCjrq(date);
                        dcdbsjjcsxdj.setCjr(currentUserName);
                        dcdbsjjcsxdj.setUserid(currentuserId);
                        dcdbsjjcsxdj.setUsername(currentUserName);
                        dcdbsjjcsxdj.setLzsj(date1);
                        //dcdbsjjcsxdj.setPreid(wh);
                        int r = DcdbJbxxService.insertDcdbSjjcsxDb(dcdbsjjcsxdj);
                        if(r==1){
                            resultMap.put("code",1);
                            resultMap.put("msg","保存成功");
                        }else{
                            resultMap.put("code",2);
                            resultMap.put("msg","保存失败");
                        }
                    }
            }else{
                resultMap.put("code",2);
                resultMap.put("msg","保存失败");
            }
        }else{
            int i = DcdbJbxxService.updateDcdbSjjcsxDj(dcdbsjjcsxdj);
            if(i==1){
                resultMap.put("code",1);
                resultMap.put("msg","保存成功");
            }else{
                resultMap.put("code",2);
                resultMap.put("msg","保存失败");
            }
        }
        return  resultMap;
    }

}
