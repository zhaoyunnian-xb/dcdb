package com.bm.index.controller;

import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbKHPZ;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbJdtbService;
import com.bm.index.service.DcdbMbSpService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mbsp")
public class DcdbMbSpController {
    @Autowired
    private DcdbMbSpService dcdbmbspservice;
    @Autowired
    private DcdbBmmbnrDao dcdbBmmbnrDao;
    @Autowired
    private DcdbJdtbService dcdbJdtbService;
    //页面跳转到部门填报总列表
    @RequestMapping("/bmrwlist.do")
    public String index(){
        return "bmrwlist";
    }

    //页面跳转到部门填报总列表
    @RequestMapping("/zyrwlist.do")
    public String zyrwlist(HttpServletRequest request, String id, Model model){
        model.addAttribute("id",id);
        return "ndgzrw/zyrwlist";
    }
   @RequestMapping("/detail.do")
   @ResponseBody
    public DcdbBmmbnr detail(HttpServletRequest request, String id, Model model){
        String newId = request.getParameter("id");
        DcdbBmmbnr  dcdbbmmbnr =  dcdbmbspservice.deatil(id);
        return dcdbbmmbnr;
    }
    /**
     * 查询部门填报任务列表
     * @param request data  年份
     * @param   request czr  操作人
     * @param   request 文件名称  操作人
     * @return
     */
    @RequestMapping("/queryTableRW.do")
    @ResponseBody
    public Map<String,Object> queryTableDb(HttpServletRequest request, String data,String czr,String wjmc){
        //当前页
        int page=Integer.parseInt(request.getParameter("page"));
        //每页显示数
        int limit=Integer.parseInt(request.getParameter("limit"));
        int start = (page - 1) * limit;
        int end = page * limit;
        Map<String,Object> condtionMap=new HashMap<>();
       // UserEntity userentity = (UserEntity) request.getSession().getAttribute("User");
        condtionMap.put("startCount",start);
        condtionMap.put("endCount",end);
       // condtionMap.put("user_id",userentity.getId());
        condtionMap.put("data",data);
        condtionMap.put("czr",czr);
        condtionMap.put("wjmc",wjmc);
        //获取总任务数据
        List<Map<String,Object>> dbTable = dcdbmbspservice.getRwTable(condtionMap);
        //总记录数
        int i=dcdbmbspservice.getRwCount(condtionMap);
        Map<String,Object> resultMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dbTable)){
            resultMap.put("code",0);
            resultMap.put("msg","");
            resultMap.put("count",i);
            resultMap.put("data",dbTable);
        }else{
            resultMap.put("code",1);
            resultMap.put("msg","暂无数据");
        }
        return resultMap;
    }
    /**
     * 查询部门填报任务列表
     * @param request id
     * @return
     */
    @RequestMapping("/queryZyrwList.do")
    @ResponseBody
    public Map<String,Object> initNDRWList(HttpServletRequest request,
                                           HttpSession se,
                                           String ndrwId,String cbbmId ){
        //当前页
        int page=Integer.parseInt(request.getParameter("page"));
        //每页显示数
        int limit=Integer.parseInt(request.getParameter("limit"));
        int start = (page - 1) * limit;
        int end = page * limit;
        UserEntity user = (UserEntity) se.getAttribute("User");
        String currentuserId = user.getId(); //当前登录人ID
        Map<String,Object> condtionMap=new HashMap<>();
        condtionMap.put("startCount",start);
        condtionMap.put("endCount",end);
        // condtionMap.put("userId",userentity.getId());
        condtionMap.put("ndrwId",ndrwId);
        condtionMap.put("userId",currentuserId);
        if( StringUtils.isBlank(cbbmId) || "undefined".equals(cbbmId) || "null".equals(cbbmId)){
            List<UserEntity> users = dcdbBmmbnrDao.loginQuery(currentuserId);
            cbbmId=users.get(0).getBmbm();
        }

        condtionMap.put("cbbmid",cbbmId);
        List<DcdbBmmbnr> dbTable = dcdbmbspservice.getZYRwTable(condtionMap);
       /* for(DcdbBmmbnr db :dbTable ){
            String rwId = db.getZyrwid();
            String pebm = dcdbmbspservice.queryPhbmList(rwId);
            db.setPhbmlist(pebm);
        }*/
        //获取主要任务总记录数
        int i=dcdbmbspservice.getZYRWCount(condtionMap);
        Map<String,Object> resultMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dbTable)){
            resultMap.put("code",0);
            resultMap.put("msg","");
            resultMap.put("count",i);
            resultMap.put("data",dbTable);
        }else{
            resultMap.put("code",1);
            resultMap.put("msg","暂无数据");
        }
        return resultMap;
    }
    /**
     * 查询部门填报任务列表
     * @param request id
     * @return
     */
    @RequestMapping("/updaterwmb.do")
    @ResponseBody
    public int updateZyrw(HttpServletRequest request, String rwmbId,String yjdmb,String bnmb,String sjdmb,String qnmb) {
        int i = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",rwmbId);
        map.put("yjdmb",yjdmb);
        map.put("bnmb",bnmb);
        map.put("sjdmb",sjdmb);
        map.put("qnmb",qnmb);
        try{
            dcdbmbspservice.updateZyrw(map);
            i = 1;
        }catch(Exception e){
           e.printStackTrace();
        }
        return i;
    }

    /**
     * 部门任务目标意见保存
     * @param request ndId   年度ID
     * @param request bmspyj  部门审批意见
     * @param request bgsspyj  办公室意见
     *
     * @return
     */
    @RequestMapping("/savespyj.do")
    @ResponseBody
    public int saveSpyj(HttpServletRequest request,
                        HttpSession se,
                        String bmspyj,
                        String bmspqm,
                        String bmsprq,
                        String bgsspyj,
                        String bgsspqm,
                        String bgssprq,
                        String bgsldpyj,
                        String bgsldpqm,
                        String bgsldprq,
                        String ndrwId,
                        String cbbmId,
                        String nextuserId,
                        String lcjd,
                        String type) {
        int i = 0;

        UserEntity user = (UserEntity) se.getAttribute("User");
        String currentuserId = user.getId(); //当前登录人ID
        if( StringUtils.isBlank(cbbmId) || "undefined".equals(cbbmId) || "null".equals(cbbmId)){
            List<UserEntity> users = dcdbBmmbnrDao.loginQuery(currentuserId);
            cbbmId=users.get(0).getBmbm();
        }
        String spId = ndrwId+cbbmId;
        DcdbKHPZ dcdbkhpz = new DcdbKHPZ();
        if(bmspyj != null && !("".equals(bmspyj))){
            dcdbkhpz.setBmmbfzryj(bmspyj);
        }
        if(bmspqm != null && !("".equals(bmspqm))){
            dcdbkhpz.setBmfzrqm(bmspqm);
        }
        if(bmsprq != null && !("".equals(bmsprq))){
            dcdbkhpz.setBmfzrrq(bmsprq);
        }
        if(bgsspyj != null && !("".equals(bgsspyj))){
            dcdbkhpz.setBgsmbyj(bgsspyj);
        }
        if(bgsspqm != null && !("".equals(bgsspqm))){
            dcdbkhpz.setBgsqm(bgsspqm);
        }
        if(bgssprq != null && !("".equals(bgssprq))){
            dcdbkhpz.setBgsrq(bgssprq);
        }
        if(bgsldpyj != null && !("".equals(bgsldpyj))){
            dcdbkhpz.setBgsldmbyj(bgsldpyj);
        }
        if(bgsldpqm != null && !("".equals(bgsldpqm))){
            dcdbkhpz.setBgsfzrqm(bgsldpqm);
        }
        if(bgsldprq != null && !("".equals(bgsldprq))){
            dcdbkhpz.setBgsfzrrq(bgsldprq);
        }
        if(spId != null && !("".equals(spId))){
            dcdbkhpz.setYjid(spId);
        }

        //承办人  提交中的保存
        if("1".equals(lcjd)){
             if("2".equals(type)){
                 dcdbkhpz.setNextuserid(nextuserId);
                 dcdbkhpz.setCurrentuserid(currentuserId);
                 //退回使用
                 dcdbkhpz.setZt(lcjd);
                 DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                 if(dcdbkhpz2 == null){
                     dcdbmbspservice.saveSpyj(dcdbkhpz);
                     i = 1;
                 }else{
                     String  cbrcurrentUseId = dcdbkhpz2.getCurrentuserid();
                     dcdbkhpz.setPreuserid(cbrcurrentUseId);
                     dcdbkhpz.setNextuserid(nextuserId);
                     dcdbkhpz.setCurrentuserid(currentuserId);
                     dcdbmbspservice.updateSpyj(dcdbkhpz);
                     i = 1;
                 }
             }
        }
        if("2".equals(lcjd)){
            if("1".equals(type)){
                dcdbmbspservice.updateSpyj(dcdbkhpz);
                i = 1;
            }else if("2".equals(type)){
                DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                if(dcdbkhpz2 == null){
                    dcdbmbspservice.saveSpyj(dcdbkhpz);
                    i = 1;
                }else{
                    String  cbrcurrentUseId = dcdbkhpz2.getCurrentuserid();
                    dcdbkhpz.setPreuserid(cbrcurrentUseId);
                    dcdbkhpz.setNextuserid(nextuserId);
                    dcdbkhpz.setCurrentuserid(currentuserId);
                    dcdbmbspservice.updateSpyj(dcdbkhpz);
                    i = 1;
                }
            }
        }

        if("3".equals(lcjd)){
            if("1".equals(type)){
                dcdbmbspservice.updateSpyj(dcdbkhpz);
                i = 1;
            }else if("2".equals(type)){
                DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                if(dcdbkhpz2 == null){
                    dcdbmbspservice.saveSpyj(dcdbkhpz);
                    i = 1;
                }else{
                    String  cbrcurrentUseId = dcdbkhpz2.getCurrentuserid();
                    dcdbkhpz.setPreuserid(cbrcurrentUseId);
                    dcdbkhpz.setNextuserid(nextuserId);
                    dcdbkhpz.setCurrentuserid(currentuserId);
                    dcdbmbspservice.updateSpyj(dcdbkhpz);
                    i = 1;
                }

            }else if("3".equals(type)){
                DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                if(dcdbkhpz2 == null){
                    dcdbmbspservice.saveSpyj(dcdbkhpz);
                    i = 1;
                }else{
                    String  cbrcurrentUseId = dcdbkhpz2.getCurrentuserid();
                    dcdbkhpz.setNextuserid(cbrcurrentUseId);
                    dcdbkhpz.setCurrentuserid(currentuserId);
                    dcdbmbspservice.updateSpyj(dcdbkhpz);
                    i = 1;

                }
            }
        }

        if("4".equals(lcjd)){
            if("1".equals(type)){
                dcdbmbspservice.updateSpyj(dcdbkhpz);
                i = 1;
            }else if("2".equals(type)){
                DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                String  cbrcurrentUseId = dcdbkhpz2.getCurrentuserid();
                dcdbkhpz.setPreuserid(cbrcurrentUseId);
                dcdbkhpz.setNextuserid(nextuserId);
                dcdbkhpz.setCurrentuserid(currentuserId);
                dcdbmbspservice.updateSpyj(dcdbkhpz);
                i = 1;
            }
        }
        return i;
    }

    /**
     * 查询审批意见
     *
     *
     * @return
     */
    @RequestMapping("/selectSpyj.do")
    @ResponseBody
    public Map<String,Object>  selectSpyj(HttpSession se,HttpServletRequest request,String ndId,String cbbmId) {
        Map<String,Object> map = new HashMap<String,Object>();
        if( StringUtils.isBlank(cbbmId) || "undefined".equals(cbbmId) || "null".equals(cbbmId)){
            UserEntity user = (UserEntity) se.getAttribute("User");
            List<UserEntity> users = dcdbBmmbnrDao.loginQuery(user.getId());
            cbbmId=users.get(0).getBmbm();
        }

        //获取部门审批
        String yjid = ndId+cbbmId; //年份+部门编号ID
        DcdbKHPZ  dcdbkhpz =  dcdbmbspservice.selectSpyj(yjid);
        /*if(dcdbkhpz == null){
            dcdbkhpz = new DcdbKHPZ();
        }*/
        map.put("dcdbkhpz",dcdbkhpz);
        return  map;
    }

    /**
     * 部门任务提交
     * @param request ndId   年度ID
     * @param request bmspyj  部门审批意见
     * @param request bgsspyj  办公室意见
     *
     * @return
     */
    @RequestMapping("/tijiaoSpyj.do")
    @ResponseBody
    public Map<String,Object>  tijiaoSpyj(HttpServletRequest request,
                                          HttpSession se,
                                          String nextuserId,String ndrwId,String type,String zt,String cbbmId,String lcjd) {
        int i = 0;
        String spId = ndrwId+cbbmId;//考核批注唯一ID
        UserEntity user = (UserEntity) se.getAttribute("User");
        String currentuserId = user.getId(); //当前登录人ID
        Map<String,Object> condtionMap=new HashMap<>();
        if( StringUtils.isBlank(cbbmId) || "undefined".equals(cbbmId) ||"null".equals(cbbmId) ){
            List<UserEntity> users = dcdbBmmbnrDao.loginQuery(currentuserId);
            cbbmId=users.get(0).getBmbm();
        }
        condtionMap.put("cbbmid",cbbmId);//2018
        condtionMap.put("ndrwId",ndrwId);//2018
        condtionMap.put("currentuserId",currentuserId);//2018
        //在提交时更新部门任务列表的所有任务的承办人Id
        List<DcdbBmmbnr> list  = dcdbmbspservice.selectDcdbBmmbnrList(condtionMap);
        if(list.size()>0){
            for(DcdbBmmbnr db : list){
                String cbrId = db.getCbrid(); //承办人ID
                String  id = db.getId();//唯一Id
                String userId = db.getUserid(); //流转Id
                String zyrwId  = db.getZyrwid();//主要任务Id
                String bmlx = db.getBmlx();
                Map<String,Object> condtionMap2=new HashMap<>();
                condtionMap2.put("id",id);
                condtionMap.put("bmlx",bmlx);
                if("1".equals(lcjd)){
                    if("2".equals(type)){
                        //DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                        condtionMap2.put("userId",nextuserId);
                        String  newZt = String.valueOf(Integer.parseInt(zt)+1);
                        condtionMap2.put("status",newZt);
                        dcdbmbspservice.updateBmMbRwList(condtionMap2);
                        i =  1;
                    }
                }
                if("2".equals(lcjd)){
                    if("2".equals(type)){
                        //DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                        condtionMap2.put("userId",nextuserId);
                        String  newZt = String.valueOf(Integer.parseInt(zt)+1);
                        condtionMap2.put("status",newZt);
                        dcdbmbspservice.updateBmMbRwList(condtionMap2);
                        i =  1;
                    }
                }

                if("3".equals(lcjd)){
                    if("2".equals(type)){
                        //DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                        condtionMap2.put("userId",nextuserId);
                        String  newZt = String.valueOf(Integer.parseInt(zt)+1);
                        condtionMap2.put("status",newZt);
                        dcdbmbspservice.updateBmMbRwList(condtionMap2);
                        i =  1;
                    }
                    if("3".equals(type)){
                        DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                        String nextuserId2 = dcdbkhpz2.getNextuserid();
                        condtionMap2.put("userId",nextuserId2);
                        String  newZt = String.valueOf(Integer.parseInt(zt)-1);
                        condtionMap2.put("status",newZt);
                        dcdbmbspservice.updateBmMbRwList(condtionMap2);
                        i =  1;
                    }
                }

                if("4".equals(lcjd)){
                    if("2".equals(type)){
                        //DcdbKHPZ  dcdbkhpz2 =  dcdbmbspservice.selectSpyj(spId);
                        condtionMap2.put("userId",cbrId);
                        String  newZt = String.valueOf(Integer.parseInt(zt)+1);
                        condtionMap2.put("status",newZt);
                        condtionMap.put("zyrwId",zyrwId);
                        dcdbmbspservice.updateBmMbRwList(condtionMap2);
                        i =  1;
                        /*List<DcdbBmmbnr> list2  = dcdbmbspservice.selectAllZyrw(condtionMap);
                        for(DcdbBmmbnr db2: list2){
                            String id2  = db2.getId();
                            condtionMap2.put("id",id2);
                            dcdbmbspservice.updateBmMbRwList(condtionMap2);
                            i =  1;
                        }*/

                    }
                }

            }

            //插入已办表
           /* if("2".equals(type)){
                dcdbJdtbService.insertNdrwYb(list);
            }*/
        }

        Map<String,Object> resultMap = new HashMap<>();

        if(i == 1){
            resultMap.put("code",0);
            resultMap.put("msg","操作成功");
            //resultMap.put("newZt",newZt);
        }
        if(i==0){
            resultMap.put("code",1);
            resultMap.put("msg","操作失败");
        }
      return resultMap;
    }

}
