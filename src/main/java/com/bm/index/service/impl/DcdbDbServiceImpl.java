package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.RoleUserDao;
import com.bm.index.dao.source2.DcdbTyyhDao;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbSjjcsxDj;
import com.bm.index.model.RoleUser;
import com.bm.index.service.DcdbDbService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DcdbDbServiceImpl implements DcdbDbService {

    @Autowired
    private DcdbDjlbDbDao dbDao;
    @Autowired
    private RoleUserDao roleUserDao;
    @Autowired
    private DcdbTyyhDao dcdbtyyhdao;



    @Override
    public List<DcdbDjlbDbParam> queryTable(DcdbDjlbDbParam dbParam) {
        List<DcdbDjlbDbParam>  dcdbdbList = dbDao.queryTable(dbParam);
        //处理部门名称带有@的情况 1@1
        for(DcdbDjlbDbParam djlbDbParam : dcdbdbList){
           /* //最终拼接成的部门名称为1，
            StringBuffer cbbmmcSb = new StringBuffer();
            String cbbmmc = "";
            //如果承办部门为空时，则不进行处理
            if(StringUtils.isNotBlank(djlbDbParam.getCbbmmc())){
                String[] cbbmmcArr = djlbDbParam.getCbbmmc().split("@");
                for(int i = 0;i<cbbmmcArr.length;i++){
                    cbbmmcSb.append(cbbmmcArr[i]).append(",");
                }
                cbbmmc = cbbmmcSb.substring(0,cbbmmcSb.length()-1);
            }
            djlbDbParam.setCbbmmc(cbbmmc);*/
            //查询出当前状态
            String sfth = djlbDbParam.getSfth();//0，不是退回，1，退回
            String nodeId = djlbDbParam.getNodeId();//当前节点id
            String bllx = djlbDbParam.getBllx();//获取业务类型
            String status = "";//当前状态
            //如果不是退回并且节点为0101，则为新建状态，否则要去角色节点表中去查询当前状态
            if("0".equals(sfth)&&"0101".equals(nodeId)){
                djlbDbParam.setStatus("新建中");
            }else {
                if("dzjb".equals(djlbDbParam.getLclx())){
                    bllx="党组检办";
                }
                RoleUser roleUser = new RoleUser();
                roleUser.setYwtype(bllx);
                roleUser.setNodeid(nodeId);
                //根据业务类型以及节点名称去查询当前状态
                List<RoleUser> nodeStatusList = roleUserDao.selectNodeStatus(roleUser);
                if(CollectionUtils.isNotEmpty(nodeStatusList)){
                    status = nodeStatusList.get(0).getStatus();
                }
                djlbDbParam.setStatus(status);
            }
        }
        return dcdbdbList;
    }

    @Override
    public List<Map<String, String>> queryCbBm() {
        return dbDao.queryCbBm();
        //return dcdbtyyhdao.queryCbBm();
    }

    @Override
    public List<Map<String, String>> queryBmPerson(Map<String, String> map) {
        return dbDao.queryBmPerson(map);
        //return dcdbtyyhdao.queryBmPerson(map);
    }

    @Override
    public int batchUpdate(Map<String,Object> param) {
        return dbDao.batchUpdate(param);
    }

    /**
     * 查询统计页面数据
     * @param dbParam
     * @return
     */
    @Override
    public List<DcdbDjlbDbParam> queryTjTable(DcdbDjlbDbParam dbParam) {
        List<DcdbDjlbDbParam>  dcdbdbList = dbDao.queryTjTable(dbParam);
        return dcdbdbList;
    }

    @Override
    public List<DcdbSjjcsxDj> queryZyjcTable(DcdbSjjcsxDj dcdbSjjcsxDj) {
        return dbDao.queryZyjcTable(dcdbSjjcsxDj);
    }

    @Override
    public List<DcdbDjlbDbParam> selectByUserId(String userid) {
        return dbDao.selectByUserId(userid);
    }

    @Override
    public List<DcdbDjlbDbParam> queryTableGd(DcdbDjlbDbParam dbParam) {
        List<DcdbDjlbDbParam>  dcdbdbList = dbDao.queryTableGd(dbParam);
        return dcdbdbList;
    }
}
