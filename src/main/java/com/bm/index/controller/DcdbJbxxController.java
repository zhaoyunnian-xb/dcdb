package com.bm.index.controller;

import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.model.DcdbDjlbDb;
import com.bm.index.model.DcdbJbxx;
import com.bm.index.model.DcdbProjectFile;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbJbxxService;
import com.bm.index.util.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/jbxx")
public class DcdbJbxxController {
    @Autowired
    private DcdbJbxxService DcdbJbxxService;
    @Autowired
    private DcdbDjlbDbDao dcdbdjlbdbdao;
    @Autowired
    private DcdbBmmbnrDao dcdbBmmbnrDao;
    private Gson mGson = new Gson();

    //查询
    @RequestMapping("/jbxxdetail.do")
    @ResponseBody
    public DcdbJbxx zyrwlist(String wh, HttpServletRequest request, Model model) {
        DcdbJbxx dcdbjbxx = DcdbJbxxService.getById(wh);
        return dcdbjbxx;
    }

    //案件基本信息的保存（领导批示）
    @RequestMapping("/jbxxsave.do")
    @ResponseBody
    public Map<String, Object> jbxxsave(HttpServletRequest request, Model model, DcdbJbxx dcdbjbxx, HttpSession se, String nodeId, String nodeName, String lclx, String dbId, String type) {
        Map<String, Object> resultMap = new HashMap<>();
        String newdbId = UUIDUtils.getUUID();
        try {
            if (dcdbjbxx != null) {
                String wh = dcdbjbxx.getWh();
                if (wh == null || "".equals(wh)) {
                    resultMap.put("code", 2);
                    resultMap.put("msg", "文号不准为空");
                } else {
                    DcdbJbxx jbxx = DcdbJbxxService.getById(wh);//根据wh查询基本信息

                    String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
                    DcdbDjlbDb dcdbdjlbdb = new DcdbDjlbDb();
                    UserEntity user = (UserEntity) se.getAttribute("User");
                    String currentuserId = user.getId(); //当前登录人ID
                    String currentuserName = user.getName();

                    dcdbdjlbdb.setCbbmmc(dcdbjbxx.getCbbmmc());
                    dcdbdjlbdb.setCbbmid(dcdbjbxx.getCbbm());
                    dcdbdjlbdb.setUserid(currentuserId);
                    dcdbdjlbdb.setUsername(user.getName());
                    dcdbdjlbdb.setBllx(dcdbjbxx.getBllx());
                    dcdbdjlbdb.setPslx(dcdbjbxx.getPslx());
                    dcdbdjlbdb.setIssjldpsj(dcdbjbxx.getIssjldpsj());
                    if ("2".equals(type)) {
                        int dzts = Integer.valueOf(dcdbjbxx.getCbqx());
                        if (dzts < 3) {
                            dcdbdjlbdb.setYjqk("2");
                        } else {
                            dcdbdjlbdb.setYjqk("1");
                        }
                    }
                    if (StringUtils.isEmpty(dbId)) {
                        if ("lzps".equalsIgnoreCase(lclx)) {
                            List<DcdbJbxx> listWh = DcdbJbxxService.selectWh();
                            final String eqlWh = wh;
                            if (listWh.stream().anyMatch(w -> w.getWh().equals(eqlWh))) {
                                String whLast = listWh.get(0).getWh();
                                String whSub = whLast.substring(4);
                                wh = String.valueOf(Integer.valueOf(whLast) + 1);
                                dcdbjbxx.setWh(wh);

                            }
                        }
                        int i = DcdbJbxxService.save(dcdbjbxx);
                        if (i == 1) {
                            //针对领导批示，把wh返回前台
                            if ("lzps".equalsIgnoreCase(lclx)) {
                                resultMap.put("finalWh", wh);
                                resultMap.put("dbId", newdbId);

                            }

                            Map map = new HashMap();
                            map.put("wh", wh);
                            List<DcdbDjlbDb> list = dcdbdjlbdbdao.selectDbByWh(map);

                            if (list.size() == 0) {
                                String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                dcdbdjlbdb.setWh(wh);
                                dcdbdjlbdb.setNodeId("0101");
                                dcdbdjlbdb.setNodeName("收件登记");
                                dcdbdjlbdb.setId(newdbId);
                                dcdbdjlbdb.setLclx(lclx);
                                dcdbdjlbdb.setCjrq(date);
                                dcdbdjlbdb.setCjr(currentuserName);
                                dcdbdjlbdb.setLzsj(date2);
                                dcdbdjlbdb.setPreid(newdbId);
                                int r = dcdbdjlbdbdao.insertSelective(dcdbdjlbdb);
                                if (r == 1) {
                                    resultMap.put("code", 1);
                                    resultMap.put("msg", "保存成功");
                                    resultMap.put("newId", dbId);
                                } else {
                                    resultMap.put("code", 2);
                                    resultMap.put("msg", "保存失败");
                                }
                            }

                        } else {
                            resultMap.put("code", 2);
                            resultMap.put("msg", "保存失败");
                        }
                    } else {
                        //针对领导批示，如果保存的时候，文号在库中存在，则重新生成wh保存。
                        int i = DcdbJbxxService.update(dcdbjbxx);
                        if (i == 1) {
                            DcdbDjlbDb dcdbdjlbdb2 = dcdbdjlbdbdao.selectByPrimaryKey(dbId);
                            if (dcdbdjlbdb2 != null) {
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
                                    dcdbdjlbdb1.setIssjldpsj(dcdbdjlbdb.getIssjldpsj());
                                    r = dcdbdjlbdbdao.updateByPrimaryKeySelective(dcdbdjlbdb1);
                                }
                                if (r == 1) {
                                    resultMap.put("code", 1);
                                    resultMap.put("msg", "保存成功");
                                } else {
                                    resultMap.put("code", 2);
                                    resultMap.put("msg", "保存失败");
                                }
                            }
                        } else {
                            resultMap.put("code", 2);
                            resultMap.put("msg", "保存失败");
                        }
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    @RequestMapping("/selectwh.do")
    @ResponseBody
    public Map<String, Object> selectWh(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String date2 = date.substring(0, 4);
        String newwh = "";
        List<DcdbJbxx> list = DcdbJbxxService.selectWh();
        if (list.size() > 0) {
            String wh = list.get(0).getWh();
            String wh2 = wh.substring(0, 4);
            if (wh2.equals(date2)) {
                newwh = String.valueOf(Integer.valueOf(wh) + 1);
            } else {
                newwh = date2 + "0001";
            }
        } else {
            newwh = date2 + "0001";
        }
        resultMap.put("wh", newwh);
        return resultMap;
    }

    @RequestMapping("/selectUploadFiles.do")
    @ResponseBody
    public List<DcdbProjectFile> selectUploadFiles(HttpServletRequest request, String id, String ywtype) {
        List<DcdbProjectFile> list = DcdbJbxxService.selectUploadFiles(id, ywtype);
        return list;
    }

}
