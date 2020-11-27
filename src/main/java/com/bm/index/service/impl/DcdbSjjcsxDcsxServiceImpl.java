package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.DcdbSjjcsxDcsxDao;
import com.bm.index.dao.source1.DcdbSjjcsxDcsxnqDao;
import com.bm.index.model.DcdbDjlbDb;
import com.bm.index.model.DcdbSjjcsxDcsx;
import com.bm.index.model.DcdbSjjcsxDcsxnq;
import com.bm.index.service.DcdbSjjcsxDcsxService;
import com.bm.index.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class DcdbSjjcsxDcsxServiceImpl implements DcdbSjjcsxDcsxService {

    @Autowired
    private DcdbSjjcsxDcsxDao dcsxDao;
    @Autowired
    private DcdbSjjcsxDcsxnqDao dcsxnqDao;
    @Autowired
    private DcdbDjlbDbDao dbDao;

    @Override
    public List<DcdbSjjcsxDcsx> selectByExample(DcdbSjjcsxDcsx example) {
        List<DcdbSjjcsxDcsx> dcsxList = dcsxDao.selectByExample(example);
        //当查询列表不为空时，将督办周期变成中文，将部门分割，变成 部门1，部门2的形式
        if(CollectionUtils.isNotEmpty(dcsxList)){
            for (DcdbSjjcsxDcsx dcsx:dcsxList){
                if(StringUtils.isNotBlank(dcsx.getDczqtype())){
                    switch (dcsx.getDczqtype()){
                        case "0":
                            dcsx.setDczqtype("每周");
                            break;
                        case "1":
                            dcsx.setDczqtype("每月");
                            break;
                        case "2":
                            dcsx.setDczqtype("每季度");
                            break;
                        case "3":
                            dcsx.setDczqtype("每半年");
                            break;
                        case "4":
                            dcsx.setDczqtype("定制天数");
                            break;
                    }
                }
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
        return dcsxList;
    }

    @Override
    public int deleteByExample(DcdbSjjcsxDcsx example) {
        return dcsxDao.deleteByExample(example);
    }

    @Override
    public int insert(DcdbSjjcsxDcsx record) {
        return dcsxDao.insertSelective(record);
    }

    @Override
    public DcdbSjjcsxDcsx selectById(DcdbSjjcsxDcsx example) {
        DcdbSjjcsxDcsx dcsx = dcsxDao.selectById(example);
       /* switch (dcsx.getDczqtype()){
            case "0":
                dcsx.setDczqtype("每周");
                break;
            case "1":
                dcsx.setDczqtype("每月");
                break;
            case "2":
                dcsx.setDczqtype("每季度");
                break;
            case "3":
                dcsx.setDczqtype("每半年");
                break;
            case "4":
                dcsx.setDczqtype("定制天数");
                break;
        }*/
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
            dcsx.setQtbmmc(qtbm);
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
            dcsx.setZzbmmc(zzbm);
        }
        return dcsx;
    }

    @Override
    public int insertDcsxnq(DcdbSjjcsxDcsxnq record) {
        return dcsxnqDao.insertSelective(record);
    }

    @Override
    public int updateIsfq(DcdbSjjcsxDcsx record) {
        return dcsxDao.updateIsfq(record);
    }

    @Override
    public int updateIszz(DcdbSjjcsxDcsxnq record) {
        return dcsxnqDao.updateIszz(record);
    }

    @Override
    public int updateDb(String wh, String cbbmmc, String cbbmid, String userid, String userName, String bllx, String lclx) {
        //更新待办、已办表
        DcdbDjlbDb db= new DcdbDjlbDb();
        db.setWh(wh);
        db.setUserid(userid);
        db.setNodeId("0105");
        db.setLclx("zyjc");
        List<DcdbDjlbDb> dbList = dbDao.selectByExample(db);
        int i = 0;
        if (CollectionUtils.isEmpty(dbList)) {
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
            db1.setNodeId("0105");
            db1.setNodeName("内勤分案");
            db1.setBllx(bllx);
            db1.setCjrq(curDate);
            db1.setLclx(lclx);
            db1.setLzsj(curDate1);
            i = dbDao.insertSelective(db1);
        }
        return i;
    }
}
