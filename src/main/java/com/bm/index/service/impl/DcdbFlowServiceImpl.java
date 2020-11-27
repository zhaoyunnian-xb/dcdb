package com.bm.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.DcdbDjlbYbDao;
import com.bm.index.dao.source1.DcdbFlowDao;
import com.bm.index.dao.source1.RoleUserDao;
import com.bm.index.model.*;
import com.bm.index.service.DcdbFlowService;
import com.bm.index.util.ConfigProperties;
import com.bm.index.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class DcdbFlowServiceImpl implements DcdbFlowService {

  @Autowired
  private DcdbFlowDao flowDao;
  @Autowired
  private DcdbDjlbDbDao dbDao;
  @Autowired
  private DcdbDjlbYbDao ybDao;
  @Autowired
  private RoleUserDao roleDao;
  @Autowired
  private DcdbBmmbnrDao unitDao;

  private final String lastNodeId = "8888";
  private final String bfyq = "编发要情";
  private final String gd = "归档";
  private final String ybx = "一般性工作批示";
  private final String syzxdb = "专项督办省院";
  private final String nbssNode = "0102";
  private final String jbUserId = ConfigProperties.getProperties("jbUserId");
  private final String jbUserName = ConfigProperties.getProperties("jbUserName");

  private static Log logger = LogFactory.getLog(DcdbFlowServiceImpl.class);


  @Override
  public Map<String, Object> submitGdAndYq(HttpSession se, DcdbLcjd lcjd) {
    String ywtype = lcjd.getYwtype();
    String nodeId = lcjd.getNodeId();
    String nodeName = lcjd.getNodeName();
    Map<String, Object> mapRes = new HashMap<>();
    String msg = "操作成功";
    String code = "1";
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    String curDate = sf.format(date.getTime());
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      if (user == null) {
        mapRes.put("code", "2");
        mapRes.put("msg", "session失效,请重新登录门户,进入督办系统");
        return mapRes;
      }
      String curUserId = user.getId();
      String curUserName = user.getName();

      List<DcdbLcjd> res = flowDao.selectLcjd(lcjd);
      if (CollectionUtils.isNotEmpty(res)) {
        String nextId = res.get(0).getNextId();
        String nextName = res.get(0).getNextName();
        String nextuserid = "";
        String nextusername = "";
        RoleUser role = new RoleUser();
        role.setYwtype(ywtype);
        role.setNodeid(nextId);
        //查询下一节点审批人
        List<RoleUser> users = roleDao.selectUserByNodeId(role);
        if (CollectionUtils.isNotEmpty(users)) {
          if (users.size() == 1) {
            nextuserid = users.get(0).getUserid();
            nextusername = users.get(0).getUsername();
          } else {
            logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，查出多条审批人记录");
            mapRes.put("code", "2");
            mapRes.put("msg", "查询到下一节点审批人信息有多条记录，请联系管理员");
            return mapRes;
          }
        } else {
          logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，没有询到审批人记录");
          mapRes.put("code", "2");
          mapRes.put("msg", "没有查询到下一节点审批人信息，请联系管理员");
          return mapRes;
        }

        if (res.size() == 1) {
          //操作待办
          DcdbDjlbDb db = new DcdbDjlbDb();
          //当前为归档节点时，把该待办办结
          if (lastNodeId.equals(nodeId)) {
            db.setZt("3");
            nodeId = "9999";
            nodeName = "已归档";
          }
          //退回操作,把sfth置成1
          if ("0".equals(lcjd.getSftg())) {
            db.setSfth("1");
          }
          db.setUserid(nextuserid);
          db.setUsername(nextusername);
          db.setNodeId(nextId);
          db.setNodeName(nextName);
          db.setId(lcjd.getId());
          flowDao.updateByExampleSelective(db);

          //当流程为编发要情的最后一个节点时，往检察办人员插入一条待办
          if (bfyq.equals(ywtype) && lastNodeId.equals(lcjd.getNodeId())) {
            RoleUser role1 = new RoleUser();
            role1.setYwtype(bfyq);
            role1.setRolename("省院检察长办公室");
            List<RoleUser> list = roleDao.selectRoleUserList(role1);
            DcdbDjlbDb db1 = new DcdbDjlbDb();
            db1.setId(UUID.randomUUID().toString());
            db1.setWh(lcjd.getWh());
            db1.setCbbmmc(lcjd.getCbbmmc());
            db1.setCbbmid(lcjd.getCbbmid());
            db1.setUserid(list.get(0).getUserid());
            db1.setUsername(list.get(0).getUsername());
            db1.setNodeId(nextId);
            db1.setNodeName(nextName);
            db1.setBllx(bfyq);
            db1.setCjrq(curDate);
            dbDao.insertSelective(db1);
          }

          //插入已办
          DcdbDjlbYb yb = new DcdbDjlbYb();
          yb.setId(lcjd.getId());
          yb.setWh(lcjd.getWh());
          yb.setCbbmmc(lcjd.getCbbmmc());
          yb.setCbbmid(lcjd.getCbbmid());
          yb.setZt("2");
          yb.setUserid(curUserId);
          yb.setUsername(curUserName);
          yb.setNodeId(nodeId);
          yb.setNodeName(nodeName);
          yb.setCjrq(curDate);
          yb.setBllx(ywtype);
          ybDao.insertSelective(yb);
          mapRes.put("code", code);
          mapRes.put("msg", "操作成功,已发送至" + nextusername);
          return mapRes;

        } else {
          logger.error("id=" + lcjd.getId() + "的案件，在" + nodeId + "节点下，节点表中查出多条数据");
          mapRes.put("code", "2");
          mapRes.put("msg", "查询失败,节点表查询出" + res.size() + "记录");
          return mapRes;
        }

      } else {
        logger.error("id=" + lcjd.getId() + "的案件，在" + nodeId + "节点下，节点表中没有查到数据");
        mapRes.put("code", "2");
        mapRes.put("msg", "查询失败,节点表没有查询出记录");
        return mapRes;
      }
    } catch (Exception e) {
      e.printStackTrace();
      mapRes.put("code", "2");
      mapRes.put("msg", e.getMessage());
      return mapRes;

    }

  }

  @Override
  public Map<String, Object> submitFlowFa(HttpSession se, DcdbLcjd lcjd) {
    String ywtype = lcjd.getYwtype();
    String nodeId = lcjd.getNodeId();
    String nodeName = lcjd.getNodeName();
    Map<String, Object> mapRes = new HashMap<>();
    String msg = "提交成功";
    String code = "1";
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    String curDate = sf.format(date.getTime());
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      if (user == null) {
        mapRes.put("code", "2");
        mapRes.put("msg", "登录超时,请重新进入督办系统");
        return mapRes;
      }
      String curUserId = user.getId();
      String curUserName = user.getName();

      List<DcdbLcjd> res = flowDao.selectLcjd(lcjd);
      if (CollectionUtils.isNotEmpty(res)) {
        String nextId = res.get(0).getNextId();
        String nextName = res.get(0).getNextName();
        RoleUser role = new RoleUser();
        role.setYwtype(ywtype);
        role.setNodeid(nextId);
        if (res.size() == 1) {
          String nextuserid = "";
          String nextusername = "";
          //当节点为收文登记或拟办送审退回操作
          if ("0101".equals(nodeId) || (nbssNode.equals(nodeId) && "0".equals(lcjd.getSftg()))) {
            List<RoleUser> users = roleDao.selectUserByNodeId(role);
            if (CollectionUtils.isNotEmpty(users)) {
              if (users.size() == 1) {
                nextuserid = users.get(0).getUserid();
                nextusername = users.get(0).getUsername();
              } else {
                logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，查出多条审批人记录");
                mapRes.put("code", "2");
                mapRes.put("msg", "查询到下一节点审批人信息有多条记录，请联系管理员");
                return mapRes;
              }
            } else {
              logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，没有询到审批人记录");
              mapRes.put("code", "2");
              mapRes.put("msg", "没有查询到下一节点审批人信息，请联系管理员");
              return mapRes;
            }
            //操作待办
            DcdbDjlbDb db = new DcdbDjlbDb();
            db.setUserid(nextuserid);
            db.setUsername(nextusername);
            db.setNodeId(nextId);
            db.setNodeName(nextName);
            db.setId(lcjd.getId());
            //退回操作,把sfth置成1
            if ("0".equals(lcjd.getSftg())) {
              db.setSfth("1");
            }
            flowDao.updateByExampleSelective(db);
            //操作已办
            insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName, ywtype,
                lcjd.getId(), lcjd.getLctype());

          } else {
            //拟办送审-->内勤分案(根据登记的承办部门拆分多条)
            if (nbssNode.equals(nodeId)) {
              List<DcdbDjlbDb> dbList = new ArrayList<>();

              //根据承办部门进行分案，分别插入待办
              String[] cbbmmcs = lcjd.getCbbmmc().split(",");
              String[] cbbmids = lcjd.getCbbmid().split(",");
              for (int i = 0; i < cbbmids.length; i++) {
                RoleUser role1 = new RoleUser();
                role1.setYwtype(ywtype);
                role1.setNodeid(nextId);
                role1.setUnitid(cbbmids[i]);
                //从配置表中获取下一节点审批人(传入部门ID)
                List<RoleUser> users = roleDao.selectUserByNodeId(role1);
                if (CollectionUtils.isNotEmpty(users)) {
                  if (users.size() == 1) {
                    nextuserid = users.get(0).getUserid();
                    nextusername = users.get(0).getUsername();
                    DcdbDjlbDb db1 = new DcdbDjlbDb();
                    db1.setId(UUIDUtils.getUUID());
                    db1.setWh(lcjd.getWh());
                    db1.setCbbmid(cbbmids[i]);
                    db1.setCbbmmc(cbbmmcs[i]);
                    db1.setCjrq(curDate);
                    db1.setCjr("罗艳");
                    db1.setUserid(nextuserid);
                    db1.setUsername(nextusername);
                    db1.setNodeId(nextId);
                    db1.setNodeName(nextName);
                    db1.setBllx(ywtype);
                    dbList.add(db1);
                  }
                } else {
                  //从配置表中获取下一节点审批人(传入部门父ID)
                  Map<String, String> unitMap = unitDao.selectUnit(cbbmids[i]);
                  role1.setUnitid(unitMap.get("PARENTID"));
                  List<RoleUser> users1 = roleDao.selectUserByNodeId(role1);
                  if (CollectionUtils.isNotEmpty(users1)) {
                    if (users1.size() == 1) {
                      nextuserid = users1.get(0).getUserid();
                      nextusername = users1.get(0).getUsername();
                      DcdbDjlbDb db1 = new DcdbDjlbDb();
                      db1.setId(UUIDUtils.getUUID());
                      db1.setWh(lcjd.getWh());
                      db1.setCbbmid(cbbmids[i]);
                      db1.setCbbmmc(cbbmmcs[i]);
                      db1.setCjrq(curDate);
                      db1.setCjr("罗艳");
                      db1.setUserid(nextuserid);
                      db1.setUsername(nextusername);
                      db1.setNodeId(nextId);
                      db1.setNodeName(nextName);
                      db1.setBllx(ywtype);
                      dbList.add(db1);
                    }
                  }
                }

              }
              //插入待办表
              if (cbbmids.length == dbList.size()) {
                flowDao.batcheInsertDb(dbList);
                //先把待办数据置成状态4
                DcdbDjlbDb db = new DcdbDjlbDb();
                db.setId(lcjd.getId());
                db.setZt("4");
                flowDao.updateByExampleSelective(db);
                //插入已办
                insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName,
                    ywtype, lcjd.getId(), lcjd.getLctype());
                mapRes.put("code", "1");
                mapRes.put("msg", "操作成功");
                return mapRes;
              } else {
                logger.error(
                    "id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，人员配置表查出多条审批人记录或者没有查询出审批人记录");
                mapRes.put("code", "2");
                mapRes.put("msg", "发送失败，查询下一节点审批人信息有多条或0条，请联系管理员");
                return mapRes;
              }

            } else {
              //分案节点往下流转
              //正常流转时
              if ("1".equals(lcjd.getSftg())) {
                DcdbDjlbDb db = new DcdbDjlbDb();
                //分案-->部门承办人（下一级审批人从页面选择）
                if ("0103".equals(nodeId)) {
                  nextuserid = lcjd.getUserid();
                  nextusername = lcjd.getUsername();
                  //部门承办人-->部门负责人（下一级审批人从配置表查询）
                } else if ("0104".equals(nodeId)) {
                  RoleUser role1 = new RoleUser();
                  role1.setYwtype(ywtype);
                  role1.setNodeid(nextId);
                  role1.setUnitid(lcjd.getCbbmid());
                  //从配置表中获取下一节点审批人(传入部门ID)
                  List<RoleUser> users = roleDao.selectUserByNodeId(role1);
                  if (CollectionUtils.isNotEmpty(users)) {
                    if (users.size() == 1) {
                      nextuserid = users.get(0).getUserid();
                      nextusername = users.get(0).getUsername();
                    } else {
                      logger.error(
                          "id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，部门id=" + lcjd.getCbbmid()
                              + "人员配置表查出多条审批人记录");
                      mapRes.put("code", "2");
                      mapRes.put("msg", "发送失败，查询到下一节点审批人信息有多条记录，请联系管理员");
                      return mapRes;
                    }
                  } else {
                    //从配置表中获取下一节点审批人(传入部门父ID)
                    Map<String, String> unitMap = unitDao.selectUnit(lcjd.getCbbmid());
                    role1.setUnitid(unitMap.get("PARENTID"));
                    List<RoleUser> users1 = roleDao.selectUserByNodeId(role1);
                    if (CollectionUtils.isNotEmpty(users1)) {
                      if (users1.size() == 1) {
                        nextuserid = users1.get(0).getUserid();
                        nextusername = users1.get(0).getUsername();
                      } else {
                        logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，部门id=" + unitMap
                            .get("PARENTID") + "人员配置表查出多条审批人记录");
                        mapRes.put("code", "2");
                        mapRes.put("msg", "发送失败，查询到下一节点审批人信息有多条记录，请联系管理员");
                        return mapRes;
                      }
                    } else {
                      logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，部门id=" + unitMap
                          .get("PARENTID") + "没有询到审批人记录");
                      mapRes.put("code", "2");
                      mapRes.put("msg", "发送失败，没有查询到下一节点审批人信息，请联系管理员");
                      return mapRes;
                    }

                  }
                } else {

                  RoleUser role1 = new RoleUser();
                  role1.setYwtype(ywtype);
                  role1.setNodeid(nextId);
                  //从配置表中获取下一节点审批人
                  List<RoleUser> users = roleDao.selectUserByNodeId(role1);
                  if (CollectionUtils.isNotEmpty(users)) {
                    if (users.size() == 1) {
                      nextuserid = users.get(0).getUserid();
                      nextusername = users.get(0).getUsername();
                    } else {
                      logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，人员配置表查出多条审批人记录");
                      mapRes.put("code", "2");
                      mapRes.put("msg", "发送失败，查询到下一节点审批人信息有多条记录，请联系管理员");
                      return mapRes;
                    }
                  } else {
                    logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，没有询到审批人记录");
                    mapRes.put("code", "2");
                    mapRes.put("msg", "发送失败，没有查询到下一节点审批人信息，请联系管理员");
                    return mapRes;
                  }

                }

                if (lastNodeId.equals(nodeId)) {
                  //当前为归档节点时，把该待办办结
                  db.setZt("3");
                  nodeId = "9999";
                  nodeName = "已归档";
                }
                //更新待办
                db.setUserid(nextuserid);
                db.setUsername(nextusername);
                db.setNodeId(nextId);
                db.setNodeName(nextName);
                db.setId(lcjd.getId());
                flowDao.updateByExampleSelective(db);
                //操作已办
                insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName,
                    ywtype, lcjd.getId(), lcjd.getLctype());
              } else {
                //----start退回流程时---------
                //更新待办
                DcdbDjlbYb yb = new DcdbDjlbYb();
                yb.setWh(lcjd.getWh());
                yb.setCbbmid(lcjd.getCbbmid());
                yb.setNodeId(nextId);
                List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
                if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
                  nextuserid = dcdbDjlbYbs.get(0).getUserid();
                  nextusername = dcdbDjlbYbs.get(0).getUsername();
                  DcdbDjlbDb db = new DcdbDjlbDb();
                  db.setUserid(nextuserid);
                  db.setUsername(nextusername);
                  db.setNodeId(nextId);
                  db.setNodeName(nextName);
                  db.setId(lcjd.getId());
                  flowDao.updateByExampleSelective(db);
                } else {
                  mapRes.put("code", "2");
                  mapRes.put("msg", "没有在已办中查询到退回的人员信息，请联系管理员");
                  return mapRes;
                }
                //操作已办
                insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName,
                    ywtype, lcjd.getId(), lcjd.getLctype());

                //----end退回流程时---------
              }
            }
          }
          mapRes.put("code", "1");
          mapRes.put("msg", "操作成功,已发送至" + nextusername);
          return mapRes;


        } else {
          mapRes.put("code", "2");
          mapRes.put("msg", "查询失败,节点表查询出" + res.size() + "记录");
          return mapRes;
        }

      } else {
        mapRes.put("code", "2");
        mapRes.put("msg", "查询失败,节点表没有查询出记录");
        return mapRes;
      }
    } catch (Exception e) {
      e.printStackTrace();
      mapRes.put("code", "2");
      mapRes.put("msg", e.getMessage());
      return mapRes;
    }
  }


  @Override
  public Map<String, Object> submitFlow(HttpSession se, DcdbLcjd lcjd) {
      String ywtype = lcjd.getYwtype();
    String lctype = lcjd.getLctype();
    String nodeId = lcjd.getNodeId();
    String nodeName = lcjd.getNodeName();
    Map<String, Object> mapRes = new HashMap<>();
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      if (user == null) {
        mapRes.put("code", "2");
        mapRes.put("msg", "session失效,请重新登录门户,进入督办系统");
        return mapRes;
      }
      //查询流程节点表
      List<DcdbLcjd> res = getLcjd(lcjd);
      if (CollectionUtils.isNotEmpty(res)) {
        String nextId = res.get(0).getNextId();
        String nextName = res.get(0).getNextName();
        String nextuserid = "";
        String nextusername = "";
        if (StringUtils.isNotBlank(lcjd.getUserid())) {
          nextuserid = lcjd.getUserid().split(";")[0].split("@")[2];
          nextusername = lcjd.getUserid().split(";")[0].split("@")[3];
        }

        String sfth = "0";
        String zt = "1";
        if (res.size() == 1) {

          //当退回操作时
          if ("0".equals(lcjd.getSftg())) {
            sfth = "1";
            DcdbDjlbYb yb = new DcdbDjlbYb();
            yb.setWh(lcjd.getWh());
            yb.setId(lcjd.getId());
            yb.setNodeId(nextId);

            List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
            if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
              nextuserid = dcdbDjlbYbs.get(0).getUserid();
              nextusername = dcdbDjlbYbs.get(0).getUsername();
              nextId=dcdbDjlbYbs.get(0).getNodeId();
              nextName=dcdbDjlbYbs.get(0).getNodeName();
            } else {
              mapRes.put("code", "2");
              mapRes.put("msg", "没有在已办中查询到退回的人员信息，请联系管理员");
              return mapRes;
            }
          }

          //是否批量内勤分案
          if ("1".equals(lcjd.getIsBatch())) {
            boolean flag = batchInsertDb(lcjd.getUserid(), lcjd.getWh(), nextId, nextName, ywtype,
                lctype, lcjd.getId());
            if (flag) {
              //插入已办
              insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName,
                  ywtype, lcjd.getId(), lcjd.getLctype());
              mapRes.put("code", "1");
              mapRes.put("msg", "操作成功");
              return mapRes;
            } else {
              logger.error("id=" + lcjd.getId() + "的案件，在" + nextId + "节点下，批量插入待办表失败");
              mapRes.put("code", "2");
              mapRes.put("msg", "发送失败，请联系管理员");
              return mapRes;
            }

          } else {
            //当办公室负责人->拟稿人 且审核通过，下一级审批人为拟稿登记人（页面不需要选人）
            if ("0102".equals(nodeId) && "1".equals(lcjd.getSftg())) {
              DcdbDjlbYb yb = new DcdbDjlbYb();
              yb.setWh(lcjd.getWh());
              yb.setId(lcjd.getId());
              yb.setNodeId("0101");
              List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
              if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
                nextuserid = dcdbDjlbYbs.get(0).getUserid();
                nextusername = dcdbDjlbYbs.get(0).getUsername();

              } else {
                mapRes.put("code", "2");
                mapRes.put("msg", "没有在已办中查询到人员信息，请联系管理员");
                return mapRes;
              }
            }

            //当前为部门负责人节点时，且审核通过，下一级审批人为创建人（页面不需要选人）(前提条件：一般性工作批示、专项督办省院，选择直接办理时)
              if (( "一般性工作批示".equals(ywtype)  && "0105".equals(nodeId) && "DCS".equals(lcjd.getFzlcbs()))
                  || ( "专项督办省院".equals(ywtype)  && "0105".equals(nodeId) && "DCS".equals(lcjd.getFzlcbs()))
                      || ( "dzjb".equals(lcjd.getLctype())  && "0105".equals(nodeId) && "DCS".equals(lcjd.getFzlcbs()))
                      || ( "jbsx".equals(lcjd.getLctype())  && "0105".equals(nodeId) && "DCS".equals(lcjd.getFzlcbs()))//交办事项
              ) {

              DcdbDjlbDb db = dbDao.selectByPrimaryKey(lcjd.getId());
              DcdbDjlbYb yb = new DcdbDjlbYb();
              yb.setWh(lcjd.getWh());
              yb.setId(db.getPreid());
              yb.setNodeId("0101");
              List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
              if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
                nextuserid = dcdbDjlbYbs.get(0).getUserid();
                nextusername = dcdbDjlbYbs.get(0).getUsername();

              } else {
                mapRes.put("code", "2");
                mapRes.put("msg", "没有在已办中查询到人员信息，请联系管理员");
                return mapRes;
              }
            }

            //当前为督查室复核节点时，且审核通过，下一级审批人为创建人（页面不需要选人）
            if (("01061".equals(nodeId) || "01071".equals(nodeId) ) && "1".equals(lcjd.getSftg())) {
              DcdbDjlbDb db = dbDao.selectByPrimaryKey(lcjd.getId());
              DcdbDjlbYb yb = new DcdbDjlbYb();
              yb.setWh(lcjd.getWh());
              yb.setId(db.getPreid());
              yb.setNodeId("0101");
              List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
              if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
                nextuserid = dcdbDjlbYbs.get(0).getUserid();
                nextusername = dcdbDjlbYbs.get(0).getUsername();

              } else {
                mapRes.put("code", "2");
                mapRes.put("msg", "没有在已办中查询到人员信息，请联系管理员");
                return mapRes;
              }
            }


              mapRes.put("code", "1");
              mapRes.put("msg", "操作成功,已发送至" + nextusername);

            //当前为待归档节点时（页面不需要选人）
            if (lastNodeId.equals(nodeId)) {
              nextuserid = user.getId();
              nextusername = user.getName();
              zt = "3";
              mapRes.put("msg", "归档成功");
            }
            //更新待办
            updateDb(lcjd.getId(), nextuserid, nextusername, nextId, nextName, sfth, zt);

            //插入已办(通过时)
            if("1".equals(lcjd.getSftg())){
                int i = deleteYb(se, lcjd.getWh(), nodeId, ywtype, lcjd.getId());
                insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName, ywtype, lcjd.getId(), lcjd.getLctype());
            }

            //当办理类型是编发要情且处在归档节点时，同步发送给检办的人
//            if (bfyq.equals(ywtype) && lastNodeId.equals(nodeId)) {
//              insertDb( lcjd.getWh(),lcjd.getCbbmmc(), lcjd.getCbbmid(),jbUserId,jbUserName, nodeId,
//                   nodeName, lcjd.getYwtype(),  lcjd.getId(), lcjd.getLctype(),"5");
//            }
            //当办理类型是一般性工作批示且是编发要请时，在0101节点同步发送给检办的人
            if ( "是".equals(lcjd.getIsBfyq()) && "0101".equals(nodeId)) {
              insertDb( lcjd.getWh(),lcjd.getCbbmmc(), lcjd.getCbbmid(),jbUserId,jbUserName, "8888",
                  "存档", lcjd.getYwtype(),  lcjd.getId(), lcjd.getLctype(),"5");
            }

            return mapRes;
          }
        } else {
          logger.error("id=" + lcjd.getId() + "的案件，在" + nodeId + "节点下，节点表中查出多条数据");
          mapRes.put("code", "2");
          mapRes.put("msg", "查询失败,节点表查询出" + res.size() + "记录");
          return mapRes;
        }

      } else {
        logger.error("id=" + lcjd.getId() + "的案件，在" + nodeId + "节点下，节点表中没有查到数据");
        mapRes.put("code", "2");
        mapRes.put("msg", "查询失败,节点表没有查询出记录");
        return mapRes;
      }
    } catch (Exception e) {
      e.printStackTrace();
      mapRes.put("code", "2");
      mapRes.put("msg", e.getMessage());
      return mapRes;

    }
  }

  @Override
  public List<DcdbSpyj> selectSpyj(DcdbSpyj spyj) {
    return flowDao.selectSpyj(spyj);
  }

  @Override
  public Map<String, Object> saveSpyj(HttpSession se, String info) {
    JSONArray jsonArray = JSON.parseArray(info);
    Map<String, Object> mapRes = new HashMap<>();
    String code = "1";
    String msg = "意见保存成功";
    try {
      for (int i = 0; i < jsonArray.size(); i++) {
        DcdbSpyj spyj = JSON.parseObject(JSON.toJSONString(jsonArray.get(i)), DcdbSpyj.class);
        DcdbSpyj yj = new DcdbSpyj();
        yj.setId(spyj.getId());
        yj.setBllx(spyj.getBllx());
        yj.setSplx(spyj.getSplx());
        List<DcdbSpyj> dcdbSpyjs = flowDao.selectSpyj(yj);
        if (CollectionUtils.isNotEmpty(dcdbSpyjs)) {
          flowDao.updateSpyj(spyj);
        } else {
          flowDao.insertSpyj(spyj);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      code = "2";
      msg = "意见保存失败";
    } finally {
      mapRes.put("code", code);
      mapRes.put("msg", msg);
    }

    return mapRes;
  }

  @Override
  public Map<String, Object> submitFlowZyjc(HttpSession se, DcdbLcjd lcjd) {
    String ywtype = lcjd.getYwtype();
    String lctype = lcjd.getLctype();
    String nodeId = lcjd.getNodeId();
    String nodeName = lcjd.getNodeName();
    Map<String, Object> mapRes = new HashMap<>();
    try {
      UserEntity user = (UserEntity) se.getAttribute("User");
      if (user == null) {
        mapRes.put("code", "2");
        mapRes.put("msg", "session失效,请重新登录门户,进入督办系统");
        return mapRes;
      }
      //查询流程节点表
      List<DcdbLcjd> res = getLcjd(lcjd);
      if (CollectionUtils.isNotEmpty(res)) {
        String nextId = res.get(0).getNextId();
        String nextName = res.get(0).getNextName();
        String nextuserid = "";
        String nextusername = "";
        if (StringUtils.isNotBlank(lcjd.getUserid())) {
          nextuserid = lcjd.getUserid().split(";")[0].split("@")[2];
          nextusername = lcjd.getUserid().split(";")[0].split("@")[3];
        }

        String sfth = "0";
        String zt = "1";
        if (res.size() == 1) {
          //当退回操作时
          if ("0".equals(lcjd.getSftg())) {
            sfth = "1";
            DcdbDjlbYb yb = new DcdbDjlbYb();
            yb.setWh(lcjd.getWh());
            yb.setId(lcjd.getId());
          //  yb.setNodeId(nextId);
            List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
            if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
              nextuserid = dcdbDjlbYbs.get(0).getUserid();
              nextusername = dcdbDjlbYbs.get(0).getUsername();
              nextId=dcdbDjlbYbs.get(0).getNodeId();
              nextName=dcdbDjlbYbs.get(0).getNodeName();

            } else {
              mapRes.put("code", "2");
              mapRes.put("msg", "没有在已办中查询到退回的人员信息，请联系管理员");
              return mapRes;
            }
          }
          //当院领导审批->生成分工时
          if ("0103".equals(nodeId) && "1".equals(lcjd.getSftg())) {
            DcdbDjlbYb yb = new DcdbDjlbYb();
            yb.setId(lcjd.getId());
            yb.setNodeId("0101");
            List<DcdbDjlbYb> dcdbDjlbYbs = flowDao.selectYb(yb);
            if (CollectionUtils.isNotEmpty(dcdbDjlbYbs)) {
              nextuserid = dcdbDjlbYbs.get(0).getUserid();
              nextusername = dcdbDjlbYbs.get(0).getUsername();

            } else {
              mapRes.put("code", "2");
              mapRes.put("msg", "没有在已办中查询到人员信息，请联系管理员");
              return mapRes;
            }
          }

          mapRes.put("code", "1");
          mapRes.put("msg", "操作成功,已发送至" + nextusername);

          //更新待办
          updateDb(lcjd.getId(), nextuserid, nextusername, nextId, nextName, sfth, zt);
          //操作已办
          insertYb(se, lcjd.getWh(), lcjd.getCbbmmc(), lcjd.getCbbmid(), nodeId, nodeName, ywtype,
              lcjd.getId(), lcjd.getLctype());

          return mapRes;
        } else {
          logger.error("id=" + lcjd.getId() + "的案件，在" + nodeId + "节点下，节点表中查出多条数据");
          mapRes.put("code", "2");
          mapRes.put("msg", "查询失败,节点表查询出" + res.size() + "记录");
          return mapRes;
        }

      } else {
        logger.error("id=" + lcjd.getId() + "的案件，在" + nodeId + "节点下，节点表中没有查到数据");
        mapRes.put("code", "2");
        mapRes.put("msg", "查询失败,节点表没有查询出记录");
        return mapRes;
      }
    } catch (Exception e) {
      e.printStackTrace();
      mapRes.put("code", "2");
      mapRes.put("msg", e.getMessage());
      return mapRes;

    }
  }

  public int deleteYb(HttpSession se,String wh,String nodeId,String ywtype,String id){
      DcdbDjlbYb dcdbDjlbYb = new DcdbDjlbYb();
      dcdbDjlbYb.setId(id);
      dcdbDjlbYb.setWh(wh);
      dcdbDjlbYb.setNodeId(nodeId);
      int i = ybDao.deleteByExample(dcdbDjlbYb);
      return i;
  }



  public void insertYb(HttpSession se, String wh, String cbbmmc, String cbbmid, String nodeId,
      String nodeName, String ywtype, String id, String lclx) {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    String curDate = sf.format(date.getTime());
    UserEntity user = (UserEntity) se.getAttribute("User");
    String curUserId = user.getId();
    String curUserName = user.getName();

    DcdbDjlbDb db = dbDao.selectByPrimaryKey(id);

    DcdbDjlbYb yb = new DcdbDjlbYb();
    yb.setWh(db.getWh());
    yb.setId(id);
    if(StringUtils.isNotBlank(db.getCbbmmc())){
      yb.setCbbmmc(db.getCbbmmc());
    }
    if(StringUtils.isNotBlank(db.getCbbmid())){
      yb.setCbbmid(db.getCbbmid());
    }
    yb.setCjr(db.getCjr());
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

  public void insertDb(String wh, String cbbmmc, String cbbmid, String userid, String userName,
      String nodeid, String nodename, String bllx, String id, String lclx,String zt) {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy年MM月dd日");
    String curDate = sf.format(date.getTime());
    String curDate1 = sf1.format(date.getTime());
    DcdbDjlbDb dbdb = dbDao.selectByPrimaryKey(id);
    DcdbDjlbDb db1 = new DcdbDjlbDb();
    db1.setId(UUIDUtils.getUUID());
    db1.setWh(wh);
    if(StringUtils.isNotBlank(dbdb.getCbbmmc())){
      db1.setCbbmmc(dbdb.getCbbmmc());
    }
    if(StringUtils.isNotBlank(dbdb.getCbbmid())){
      db1.setCbbmid(dbdb.getCbbmid());
    }
    db1.setUserid(userid);
    db1.setUsername(userName);
    db1.setNodeId(nodeid);
    db1.setNodeName(nodename);
    db1.setBllx(bllx);
    db1.setCjrq(dbdb.getCjrq());
    db1.setLclx(lclx);
    db1.setLzsj(curDate1);
    db1.setZt(zt);
    db1.setCjr(dbdb.getCjr());
    db1.setPreid(id);
    db1.setYjqk(db1.getYjqk());
    dbDao.insertSelective(db1);
  }

  public void updateDb(String Id, String nextuserid, String nextusername,
      String nextId, String nextName, String sfth, String zt) {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    String curDate = sf.format(date.getTime());

    DcdbDjlbDb db = new DcdbDjlbDb();
    db.setUserid(nextuserid);
    db.setUsername(nextusername);
    db.setNodeId(nextId);
    db.setNodeName(nextName);
    db.setId(Id);
    db.setSfth(sfth);
    db.setZt(zt);
    db.setLzsj(curDate);
    flowDao.updateByExampleSelective(db);
  }

  public boolean batchInsertDb(String user, String wh, String nextId, String nextName,
      String ywtype, String lctype, String id) {
    Calendar date = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy年MM月dd日");
    String curDate = sf.format(date.getTime());
    String curDate1 = sf1.format(date.getTime());
    List<DcdbDjlbDb> dbList = new ArrayList<>();
    String[] users = user.split(";");

    DcdbDjlbDb dbdb = dbDao.selectByPrimaryKey(id);

    for (int i = 0; i < users.length; i++) {
      DcdbDjlbDb db1 = new DcdbDjlbDb();
      db1.setId(UUIDUtils.getUUID());
      db1.setWh(wh);
      db1.setCbbmid(users[i].split("@")[0]);
      db1.setCbbmmc(users[i].split("@")[1]);
      db1.setCjrq(dbdb.getCjrq());
      db1.setCjr(dbdb.getCjr());
      db1.setUserid(users[i].split("@")[2]);
      db1.setUsername(users[i].split("@")[3]);
      db1.setNodeId(nextId);
      db1.setNodeName(nextName);
      db1.setBllx(ywtype);
      db1.setLclx(lctype);
      db1.setIsfa("1");
      db1.setLzsj(curDate1);
      db1.setYjqk(dbdb.getYjqk());
      db1.setPreid(id);
      db1.setPslx(dbdb.getPslx());
      db1.setIssjldpsj(dbdb.getIssjldpsj());
      dbList.add(db1);

    }
    //插入待办表
    if (users.length == dbList.size()) {
      flowDao.batcheInsertDb(dbList);
      //先把待办数据置成状态4
      DcdbDjlbDb db = new DcdbDjlbDb();
      db.setId(id);
      db.setZt("4");
      flowDao.updateByExampleSelective(db);
      return true;
    } else {
      return false;
    }

  }

  public List<DcdbLcjd> getLcjd(DcdbLcjd lcjd) {
    String lclx = lcjd.getLctype();
    if ("dzjb".equals(lclx)) {
      lcjd.setYwtype("党组检办");
    } else if ("zyjc".equals(lclx)) {
      lcjd.setYwtype("上级决策");
    }
    List<DcdbLcjd> res = flowDao.selectLcjd(lcjd);
    return res;

  }
}
