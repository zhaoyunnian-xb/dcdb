package com.bm.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.DcdbDjlbYbDao;
import com.bm.index.dao.source1.DcdbFlowDao;
import com.bm.index.dao.source1.DcdbSjjcsxDbzqDao;
import com.bm.index.dao.source1.DcdbSjjcsxDcsxnqDao;
import com.bm.index.dao.source1.DcdbSjjcsxSpyjDao;
import com.bm.index.model.DcdbDjlbDb;
import com.bm.index.model.DcdbDjlbYb;
import com.bm.index.model.DcdbLcjd;
import com.bm.index.model.DcdbSjjcsxDbzq;
import com.bm.index.model.DcdbSjjcsxDcsxnq;
import com.bm.index.model.DcdbSjjcsxSpyj;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbSjjcCbrService;
import com.bm.index.util.DateUtil;
import com.bm.index.util.UUIDUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName DcdbSjjcCbrServiceImpl
 * @Description TODO
 * @Author daipx
 * @Date 2019/4/15 9:44
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class DcdbSjjcCbrServiceImpl implements DcdbSjjcCbrService {

  @Autowired
  private DcdbSjjcsxDcsxnqDao dcsxnqDao;
  @Autowired
  private DcdbSjjcsxDbzqDao dcdbSjjcsxDbzqDao;
  @Autowired
  private DcdbSjjcsxSpyjDao dcdbSjjcsxSpyjDao;
  @Autowired
  private DcdbFlowDao flowDao;
  @Autowired
  private DcdbDjlbDbDao dbDao;
  @Autowired
  private DcdbDjlbYbDao ybDao;
  private final int cbqx = 2;
  private static Log logger = LogFactory.getLog(DcdbSjjcCbrServiceImpl.class);

  @Override
  public List<DcdbSjjcsxDcsxnq> queryCbrDcsxList(DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq) {
    dcdbSjjcsxDcsxnq.setZt("1");
    dcdbSjjcsxDcsxnq.setIsfa("1");
    //  dcdbSjjcsxDcsxnq.setIszz("0");
    List<DcdbSjjcsxDcsxnq> dcdbSjjcsxDcsxnqs = dcsxnqDao.selectByExample(dcdbSjjcsxDcsxnq);
    for (DcdbSjjcsxDcsxnq dcnq : dcdbSjjcsxDcsxnqs) {
      dcnq.setTbzq(getTbzq(dcnq));
    }
    return dcdbSjjcsxDcsxnqs;
  }

  @Override
  public Map<String, Object> validateTb(DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq) {
    Map<String, Object> mapRes = new HashMap<>();
    Map<String, String> mapData = new HashMap<>();
    mapRes.put("code", "1");
    mapRes.put("msg", "可以填报");
    try {
      DcdbSjjcsxDbzq dczq = new DcdbSjjcsxDbzq();
      String dcsxid = dcdbSjjcsxDcsxnq.getDcsxid();
      String dcsxmc = dcdbSjjcsxDcsxnq.getDcsxmc();
      String type = dcdbSjjcsxDcsxnq.getDczqtype();
      dczq.setDcsxid(dcsxid);
      dczq.setDczqtype(type);
      List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs = dcdbSjjcsxDbzqDao.selectByExample(dczq);
      if (CollectionUtils.isEmpty(dcdbSjjcsxDbzqs)) {
        mapRes.put("code", "2");
        mapRes.put("msg", "该督查事项没有查询到督查周期，请联系管理员");
        mapData.put("dcsxid", dcsxid);
        mapData.put("dcsxmc", dcsxmc);
        mapRes.put("data", mapData);
        return mapRes;
      } else {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sf.format(date.getTime());
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM");
        String curNy = sf1.format(date.getTime());

        if ("0".equalsIgnoreCase(type)) {
          //督查周期为每周
          String dcrq = dcdbSjjcsxDbzqs.get(0).getDcrq();
          //将星期转换成日期
          String zdrq = DateUtil.process(Integer.parseInt(dcrq));
          //判断是否可以填报
          Map<String, Object> tbRes = isTb(curDate, zdrq, dcsxid, dcsxmc);
          return tbRes;
        } else if ("1".equalsIgnoreCase(type) || "2".equalsIgnoreCase(type) || "3"
            .equalsIgnoreCase(type)) {
          //督查周期为每月、督查周期为每季度、督查周期为每半年
          dczq.setDcrq(curNy);
          List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs1 = dcdbSjjcsxDbzqDao.selectByExample(dczq);
          if (CollectionUtils.isEmpty(dcdbSjjcsxDbzqs1)) {
            mapRes.put("code", "2");
            mapRes.put("msg", "该督查事项没有查询到指定的督查周期，请联系管理员");
            mapData.put("dcsxid", dcsxid);
            mapData.put("dcsxmc", dcsxmc);
            mapRes.put("data", mapData);
            return mapRes;
          } else {
            Map<String, Object> tbRes = isTb(curDate, dcsxid, dcsxmc, dcdbSjjcsxDbzqs1);
            return tbRes;
          }
        } else if ("4".equalsIgnoreCase(type)) {
          //督查周期为定制天数
          List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs1 = dcdbSjjcsxDbzqDao.selectByExample(dczq);
          if (CollectionUtils.isEmpty(dcdbSjjcsxDbzqs1)) {
            mapRes.put("code", "2");
            mapRes.put("msg", "该督查事项没有查询到指定的督查周期，请联系管理员");
            mapData.put("dcsxid", dcsxid);
            mapData.put("dcsxmc", dcsxmc);
            mapRes.put("data", mapData);
            return mapRes;
          } else {
            //指定的定制天数
            int dzzq = Integer.parseInt(dcdbSjjcsxDbzqs.get(0).getDcrq());
            //发起时间
            String fqsj = dcdbSjjcsxDbzqs.get(0).getCjrq();
            int margin = DateUtil.getMargin(curDate, fqsj);
            double a = Math.ceil((double) margin / (double) dzzq) * dzzq;
            //截止的督查日期
            String jzdcrq = DateUtil.addDay(fqsj, (int) a);
            Map<String, Object> tb = isTb(curDate, jzdcrq, dcsxid, dcsxmc);
            return tb;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      mapRes.put("code", "2");
      mapRes.put("msg", "服务器错误，请联系管理员");
      return mapRes;
    }

    return mapRes;
  }

  @Override
  public List<DcdbSjjcsxSpyj> querySjjcSpyj(DcdbSjjcsxSpyj spyj) {
    return dcdbSjjcsxSpyjDao.selectByExample(spyj);
  }

  @Override
  public List<DcdbSjjcsxSpyj> querySjjcSpyjWqck(DcdbSjjcsxSpyj spyj) {
    List<DcdbSjjcsxSpyj> dcdbSjjcsxSpyjs = new ArrayList<>();
    try {
      dcdbSjjcsxSpyjs = dcdbSjjcsxSpyjDao.selectWqck(spyj);
      for (DcdbSjjcsxSpyj dbzq : dcdbSjjcsxSpyjs) {
        switch (dbzq.getDczqtype()) {
          case "0":
            dbzq.setDczqtype("每周");
            break;
          case "1":
            dbzq.setDczqtype("每月");
            break;
          case "2":
            dbzq.setDczqtype("每季度");
            break;
          case "3":
            dbzq.setDczqtype("每半年");
            break;
          case "4":
            dbzq.setDczqtype("定制天数");
            break;
        }

        StringBuffer qtbmMc = new StringBuffer();
        StringBuffer zybmMc = new StringBuffer();
        //牵头部门拼接为部门1，部门2
        if (StringUtils.isNotBlank(dbzq.getQtbmid())) {
          String[] qtbmidArr = dbzq.getQtbmid().split(",");//1@1@1,2@2@2
          //将部门名称拼接为部门1，部门2
          for (int i = 0; i < qtbmidArr.length; i++) {
            qtbmMc.append(qtbmidArr[i].split("@")[1]).append(",");
          }
          //去掉最后一个逗号
          String qtbm = qtbmMc.substring(0, qtbmMc.length() - 1);
          dbzq.setQtbmid(qtbm);
        }
        //主责部门拼接为部门1，部门2
        if (StringUtils.isNotBlank(dbzq.getZzbmid())) {
          String[] zzbmidArr = dbzq.getZzbmid().split(",");//1@1@1,2@2@2
          //将部门名称拼接为部门1，部门2
          for (int i = 0; i < zzbmidArr.length; i++) {
            zybmMc.append(zzbmidArr[i].split("@")[1]).append(",");
          }
          //去掉最后一个逗号
          String zzbm = zybmMc.substring(0, zybmMc.length() - 1);
          dbzq.setZzbmid(zzbm);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return dcdbSjjcsxSpyjs;
  }

  @Override
  public Map<String, Object> saveSjjcSpyj(DcdbSjjcsxSpyj spyj) {
    Map<String, Object> mapRes = new HashMap<>();
    try {
      dcdbSjjcsxSpyjDao.deleteByExample(spyj);
      int i = dcdbSjjcsxSpyjDao.insertSelective(spyj);
      if (i == 1) {
        mapRes.put("code", 1);
        mapRes.put("msg", "保存成功");
      } else {
        mapRes.put("code", 2);
        mapRes.put("msg", "保存失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.debug("saveSjjcSpyj,id=" + spyj.getId(), e);
    }
    return mapRes;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> submitSjjc(HttpSession se, Map<String, String> map) {
    Map<String, Object> mapRes = new HashMap<>();
    mapRes.put("code", "2");
    mapRes.put("msg", "操作失败");
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      if (user == null) {
        mapRes.put("code", "2");
        mapRes.put("msg", "操作超时，请重新进入督办系统");
        return mapRes;
      }
      DcdbLcjd lcjd = new DcdbLcjd();
      lcjd.setYwtype("上级决策");
      lcjd.setNodeId(map.get("nodeid"));
      lcjd.setSftg(map.get("sftg"));
      List<DcdbLcjd> res = flowDao.selectLcjd(lcjd);
      String nextId = res.get(0).getNextId();
      String nextName = res.get(0).getNextName();
      String nextuserid = "";
      String nextusername = "";
      nextuserid = map.get("userid").split(";")[0].split("@")[2];
      nextusername = map.get("userid").split(";")[0].split("@")[3];
      String sfth = "0";
      String zt = "1";
      String cbbmid = "";
      String cbbmmc = "";
      //退回操作
      if ("0".equals(map.get("sftg"))) {
        sfth = "1";
        DcdbDjlbYb yb = new DcdbDjlbYb();
        //  yb.setWh(lcjd.getWh());
        yb.setId(map.get("dbid"));
        yb.setNodeId(nextId);
        List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
        if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
          nextuserid = dcdbDjlbYbs.get(0).getUserid();
          nextusername = dcdbDjlbYbs.get(0).getUsername();
        } else {
          mapRes.put("code", "2");
          mapRes.put("msg", "没有在已办中查询到退回的人员信息，请联系管理员");
          return mapRes;
        }
      }
      //批量更新督查事项内容表
      JSONArray jsonArray = JSON.parseArray(map.get("data"));
      List<DcdbSjjcsxDcsxnq> dcnrListYes = new ArrayList<>();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < jsonArray.size(); i++) {
        DcdbSjjcsxDcsxnq dcnr = JSON
            .parseObject(JSON.toJSONString(jsonArray.get(i)), DcdbSjjcsxDcsxnq.class);
        Map<String, Object> stringObjectMap = validateTb(dcnr);
        cbbmid = dcnr.getCbbmid();
        cbbmmc = dcnr.getCbbmmc();
        if ("1".equalsIgnoreCase(stringObjectMap.get("code").toString())) {
          dcnr.setLzuserid(nextuserid);
          dcnr.setLzusername(nextusername);
          dcnr.setNodeid(nextId);
          dcnr.setNodename(nextName);
          dcnrListYes.add(dcnr);
        } else {
          Map<String, String> data = (Map<String, String>) stringObjectMap.get("data");
          sb.append(data.get("dcsxmc")).append("；");
        }
      }
      if (jsonArray.size() == dcnrListYes.size()) {
        //更新 督查事项内容表
        //   dcsxnqDao.batchUpdateSjjc(dcnrListYes);
        for (DcdbSjjcsxDcsxnq dcsx : dcnrListYes) {
          List<DcdbSjjcsxDcsxnq> dcdbSjjcsxDcsxnqs = dcsxnqDao.selectByExample(dcsx);
          if (CollectionUtils.isEmpty(dcdbSjjcsxDcsxnqs)) {
            dcsxnqDao.insertSelective(dcsx);
          }
        }
        //更新待办、已办表
        DcdbDjlbDb db = new DcdbDjlbDb();
        db.setWh(map.get("wh"));
        db.setUserid(nextuserid);
        db.setNodeId(nextId);
        db.setLclx(map.get("lclx"));
        List<DcdbDjlbDb> dbList = dbDao.selectByExample(db);
        if (CollectionUtils.isEmpty(dbList)) {
          insertDb(map.get("wh"), cbbmmc, cbbmid, nextuserid, nextusername,
              nextId, nextName, map.get("ywtype"), map.get("lclx"));
        }
        insertYb(se, cbbmmc, map.get("nodeid"),
            map.get("nodename"), map.get("ywtype"), map.get("dbid"), map.get("lclx"), map.get("wh"),
            cbbmid);
        mapRes.put("code", "1");
        mapRes.put("msg", "操作成功，已发送至" + nextusername);
        return mapRes;

      } else {
        mapRes.put("code", "2");
        mapRes.put("msg", "以下事项超期或者未到承办期限无法提交，请取消（" + sb.toString()+"）");
        return mapRes;
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.debug("待办ID==" + map.get("dbid"), e);
    }

    return mapRes;
  }

  public Map<String, Object> isTb(String curDate, String dcrq, String dcsxid, String dcsxmc)
      throws Exception {
    Map<String, Object> mapRes = new HashMap<>();
    Map<String, String> map = new HashMap<>();
    int margin = DateUtil.getMargin(curDate, dcrq);
    if (margin > 0) {
      mapRes.put("code", "2");
      mapRes.put("msg", "已超过指定的承办期限");
    } else {
      if (Math.abs(margin) <= cbqx) {
        mapRes.put("code", "1");
        mapRes.put("msg", "可以填报");
      } else {
        mapRes.put("code", "2");
        mapRes.put("msg", "还没有到达指定的承办期限");
      }
    }
    map.put("dcsxid", dcsxid);
    map.put("dcsxmc", dcsxmc);
    mapRes.put("data", map);
    return mapRes;
  }

  public Map<String, Object> isTb(String curDate, String dcsxid, String dcsxmc,
      List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs1) throws Exception {
    Map<String, Object> mapRes = new HashMap<>();
    Map<String, String> mapData = new HashMap<>();
    if (CollectionUtils.isEmpty(dcdbSjjcsxDbzqs1)) {
      mapRes.put("code", "2");
      mapRes.put("msg", "该督查事项没有查询到当月指定的督查周期，请联系管理员");
      mapData.put("dcsxid", dcsxid);
      mapData.put("dcsxmc", dcsxmc);
      mapRes.put("data", mapData);
      return mapRes;
    } else {
      String dcrq1 = dcdbSjjcsxDbzqs1.get(0).getDcrq();
      //判断是否可以填报
      Map<String, Object> tbRes = isTb(curDate, dcrq1, dcsxid, dcsxmc);
      return tbRes;
    }
  }

  public void updateDb(String id, String nextuserid, String nextusername,
      String nextId, String nextName, String sfth, String zt) {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    String curDate = sf.format(date.getTime());

    DcdbDjlbDb db = new DcdbDjlbDb();
    db.setUserid(nextuserid);
    db.setUsername(nextusername);
    db.setNodeId(nextId);
    db.setNodeName(nextName);
    db.setId(id);
    db.setSfth(sfth);
    db.setZt(zt);
    db.setLzsj(curDate);
    flowDao.updateByExampleSelective(db);
  }
  @Transactional(rollbackFor = Exception.class)
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
  @Transactional(rollbackFor = Exception.class)
  public void insertYb(HttpSession se, String cbbmmc, String nodeId,
      String nodeName, String ywtype, String id, String lclx, String wh, String cbbmid)
      throws Exception {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    String curDate = sf.format(date.getTime());
    UserEntity user = (UserEntity) se.getAttribute("User");
    String curUserId = user.getId();
    String curUserName = user.getName();

    /*DcdbDjlbDb db = dbDao.selectByPrimaryKey(id);
    String substring = db.getYjqk().substring(1, 2);*/
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

  /**
   * 获取当前可以填报的时段
   * @param dcdbSjjcsxDcsxnq
   * @return
   */
  public String getTbzq(DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq) {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    String curDate = sf.format(date.getTime());
    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM");
    String curNy = sf1.format(date.getTime());

    DcdbSjjcsxDbzq dczq = new DcdbSjjcsxDbzq();
    String dcsxid = dcdbSjjcsxDcsxnq.getDcsxid();
    String type = dcdbSjjcsxDcsxnq.getDczqtype();
    dczq.setDcsxid(dcsxid);
    dczq.setDczqtype(type);
    List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs = dcdbSjjcsxDbzqDao.selectByExample(dczq);
    if ("0".equalsIgnoreCase(type)) {
      //督查周期为每周
      String dcrq = dcdbSjjcsxDbzqs.get(0).getDcrq();
      //将星期转换成日期
      String zdrq = DateUtil.process(Integer.parseInt(dcrq));
      return DateUtil.addDay(zdrq, -cbqx) + "~" + zdrq;
    } else if ("1".equalsIgnoreCase(type) || "2".equalsIgnoreCase(type) || "3"
        .equalsIgnoreCase(type)) {
      //督查周期为每月、督查周期为每季度、督查周期为每半年
      dczq.setDcrq(curNy);
      List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs1 = dcdbSjjcsxDbzqDao.selectByExample(dczq);
      if (CollectionUtils.isEmpty(dcdbSjjcsxDbzqs1)) {
        return "没有查询到";
      } else {
        String dcrq = dcdbSjjcsxDbzqs1.get(0).getDcrq();
        return DateUtil.addDay(dcrq, -cbqx) + "~" + dcrq;
      }
    } else if ("4".equalsIgnoreCase(type)) {
      //督查周期为定制天数
      List<DcdbSjjcsxDbzq> dcdbSjjcsxDbzqs1 = dcdbSjjcsxDbzqDao.selectByExample(dczq);
      if (CollectionUtils.isEmpty(dcdbSjjcsxDbzqs1)) {
        return "没有查询到";
      } else {
        //指定的定制天数
        int dzzq = Integer.parseInt(dcdbSjjcsxDbzqs.get(0).getDcrq());
        //发起时间
        String fqsj = dcdbSjjcsxDbzqs.get(0).getCjrq();
        int margin = DateUtil.getMargin(curDate, fqsj);
        double a = Math.ceil((double) margin / (double) dzzq) * dzzq;
        //截止的督查日期
        String jzdcrq = DateUtil.addDay(fqsj, (int) a);
        return DateUtil.addDay(jzdcrq, -cbqx) + "~" + jzdcrq;
      }
    }
    return "";
  }
}
