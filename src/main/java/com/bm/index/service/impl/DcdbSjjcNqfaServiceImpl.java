package com.bm.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.DcdbDjlbYbDao;
import com.bm.index.dao.source1.DcdbFlowDao;
import com.bm.index.dao.source1.DcdbSjjcsxDcsxnqDao;
import com.bm.index.dao.source1.DcdbSjjcsxDjDao;
import com.bm.index.model.*;
import com.bm.index.service.DcdbSjjcNqfaService;
import com.bm.index.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DcdbSjjcNqfaServiceImpl implements DcdbSjjcNqfaService {

     @Autowired
     private DcdbSjjcsxDcsxnqDao dcdbsjjcsxdcsxnqdao;
    @Autowired
    private DcdbSjjcsxDjDao dcdbsjjcsxdjdao ;
    @Autowired
    private DcdbDjlbDbDao dbDao;
    @Autowired
    private DcdbFlowDao flowDao;
    @Autowired
    private DcdbDjlbYbDao ybDao;
    @Override
    public List<DcdbSjjcsxDcsxnq> queryNqfaTable(DcdbSjjcsxDcsxnq example) {
        List<DcdbSjjcsxDcsxnq> list = dcdbsjjcsxdcsxnqdao.queryNqfaTable(example);
        return list;
    }
    @Override
    public Map<String, Object> updateFp(HttpSession se, Map<String, String> map) {
        Map<String, Object> mapRes = new HashMap<>();
        mapRes.put("code", 1);
        mapRes.put("msg", "操作失败");
        try {
            //批量更新督查事项内勤表
            JSONArray jsonArray = JSON.parseArray(map.get("data"));
            List<DcdbSjjcsxDcsxnq> dcnrListYes = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                DcdbSjjcsxDcsxnq dcnr = JSON
                        .parseObject(JSON.toJSONString(jsonArray.get(i)), DcdbSjjcsxDcsxnq.class);
                String  lzuserid = map.get("lzuserid");
                String  lzusername = map.get("lzusername");
                dcnr.setLzuserid(lzuserid);
                dcnr.setLzusername(lzusername);
                dcnr.setZt("1");
                dcnrListYes.add(dcnr);
                }
            if(dcnrListYes.size() > 0 ){
              dcdbsjjcsxdcsxnqdao.batchUpdateNqfp(dcnrListYes);
                    mapRes.put("msg", "操作成功");
            }else{
                mapRes.put("code", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mapRes.put("code", 0);
        }

        return mapRes;
    }

    @Override
    public Map<String, Object> updateCx(HttpSession se, Map<String, String> map) {
        Map<String, Object> mapRes = new HashMap<>();
        mapRes.put("code", 1);
        mapRes.put("msg", "操作失败");
        try {
            //批量更新督查事项内勤表:撤销
            JSONArray jsonArray = JSON.parseArray(map.get("data"));
            List<DcdbSjjcsxDcsxnq> dcnrListYes = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                DcdbSjjcsxDcsxnq dcnr = JSON
                        .parseObject(JSON.toJSONString(jsonArray.get(i)), DcdbSjjcsxDcsxnq.class);
                dcnr.setZt("0");
                dcnrListYes.add(dcnr);
            }
            if(dcnrListYes.size() > 0 ){
                int i = dcdbsjjcsxdcsxnqdao.batchUpdateNqfp(dcnrListYes);
                    mapRes.put("msg", "操作成功");
            }else{
                mapRes.put("code", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mapRes.put("code", 0);
        }

        return mapRes;
    }
    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public Map<String, Object> updateFa(HttpSession se,List<DcdbSjjcsxDcsxnq> list,DcdbSjjcsxDcsxnq example,String bllx) {
        Map<String, Object> mapRes = new HashMap<>();
        mapRes.put("code", 1);
        mapRes.put("msg", "操作失败");
        //try {
           if(list.size()>0){
               for(int i = 0;i<list.size();i++){
                   DcdbSjjcsxDcsxnq dcnr = list.get(i);
                   String lzuserId = dcnr.getLzuserid();
                   String lzuserName = dcnr.getLzusername();
                   String djId = dcnr.getDjid();
                   //根据Id进行基本信息的案件查询
                   DcdbSjjcsxDj dcdbsjjcsxdj =  dcdbsjjcsxdjdao.getDcdbSjjcsxDjById(djId);
                   if(dcdbsjjcsxdj == null){
                       dcdbsjjcsxdj = new DcdbSjjcsxDj();
                   }
                   DcdbLcjd lcjd = new DcdbLcjd();
                   lcjd.setYwtype("上级决策");
                   lcjd.setNodeId(dcnr.getNodeid());
                   lcjd.setSftg("1");
                   List<DcdbLcjd> res = flowDao.selectLcjd(lcjd);
                   String nextId = res.get(0).getNextId();
                   String nextName = res.get(0).getNextName();
                   //分案 更新内勤表
                   dcnr.setIsfa("1");
                   dcnr.setNodeid("0106");
                   dcnr.setNodename("承办人承办");
                   SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
                   String curDate = sf.format(new Date());
                   dcnr.setFarq(curDate);
                   //更新内勤表
                   int re = dcdbsjjcsxdcsxnqdao.updateFa(dcnr);
                   if(re == 1){
                       //更新待办、已办表
                       DcdbDjlbDb db= new DcdbDjlbDb();
                       db.setWh(djId);
                       db.setUserid(lzuserId);
                       db.setNodeId("0106");
                       db.setLclx("zyjc");
                       List<DcdbDjlbDb> dbList = dbDao.selectByExample(db);
                       if (CollectionUtils.isEmpty(dbList)) {
                           String nextuserid = "";
                           String nextusername = "";
                           insertDb(djId, dcdbsjjcsxdj.getCbbmmc(),dcdbsjjcsxdj.getCbbmid(),
                                   lzuserId, lzuserName,
                                   nextId,nextName, dcdbsjjcsxdj.getZyjclx(), "zyjc");
                           insertYb(se, dcdbsjjcsxdj.getCbbmmc(), "0105",
                                   dcnr.getNodename(), dcdbsjjcsxdj.getZyjclx(), djId, "zyjc",djId,dcdbsjjcsxdj.getCbbmid());
                       }else{
                           insertYb(se, dcdbsjjcsxdj.getCbbmmc(), "0105",
                                   dcnr.getNodename(), dcdbsjjcsxdj.getZyjclx(), dbList.get(0).getId(), "zyjc",djId,dcdbsjjcsxdj.getCbbmid());
                       }
                       //当内勤人员下无分配任务时，将待办案件状态更新为‘4’
                      /* List<DcdbSjjcsxDcsxnq> list2 = dcdbsjjcsxdcsxnqdao.queryNqfaTable(example);
                       if(list2.size() == 0){
                           Map<String,Object> map6 = new HashMap<String,Object>();
                           map6.put("wh",example.getDjid());
                           map6.put("userid",example.getNquserid());
                           map6.put("nodeid","0105");
                           map6.put("bllx",bllx);
                           map6.put("lclx","zyjc");
                           dcdbsjjcsxdcsxnqdao.updateDbNq(map6);
                       }*/
                       mapRes.put("msg", "操作成功");

                   }else{
                       mapRes.put("code", 0);
                   }
               }
           }else{
               mapRes.put("code", 0);
               mapRes.put("msg", "没有可分案的督察事项");
           }

        //} catch (Exception e) {
          /*  e.printStackTrace();
            mapRes.put("code", 0);
        }*/

        return mapRes;
    }

    public void insertDb(String wh, String cbbmmc, String cbbmid, String userid, String userName,
                         String nodeid, String nodename, String bllx, String lclx) {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sf.format(date.getTime());
        String curDate1 = sf1.format(date.getTime());
        DcdbDjlbDb db1 = new DcdbDjlbDb();
        db1.setId(UUIDUtils.getUUID());
        db1.setWh(wh);
        db1.setCbbmmc(cbbmmc);
        db1.setCbbmid(cbbmid);
        db1.setUserid(userid);
        db1.setUsername(userName);
        db1.setNodeId(nodeid);
        db1.setNodeName(nodename);
        db1.setBllx(bllx);
        db1.setCjrq(curDate);
        db1.setLclx(lclx);
        db1.setLzsj(curDate1);
        dbDao.insertSelective(db1);
    }

    public void insertYb(HttpSession se, String cbbmmc, String nodeId,
                         String nodeName, String ywtype, String id, String lclx,String wh,String cbbmid) {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String curDate = sf.format(date.getTime());
        UserEntity user = (UserEntity) se.getAttribute("User");
        String curUserId = user.getId();
        String curUserName = user.getName();

        DcdbDjlbDb db = dbDao.selectByPrimaryKey(id);

        DcdbDjlbYb yb = new DcdbDjlbYb();
        yb.setWh(wh);
        yb.setId(id);
        yb.setCbbmmc(cbbmmc);
        yb.setCbbmid(cbbmid);
        yb.setZt("2");
        yb.setUserid(curUserId);
        yb.setUsername(curUserName);
        yb.setNodeId(nodeId);
        yb.setNodeName(nodeName);
        yb.setCjrq(curDate);
        yb.setBllx(ywtype);
        yb.setLclx(lclx);
        ybDao.insertSelective(yb);
    }
}
