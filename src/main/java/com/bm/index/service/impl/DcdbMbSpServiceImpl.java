package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbMbSpDao;
import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbKHPZ;
import com.bm.index.service.DcdbMbSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class DcdbMbSpServiceImpl implements DcdbMbSpService {
    @Autowired
    private DcdbMbSpDao dcdbmbspdao;
    @Override
    public List getRwTable(Map ConditionMap) {
        return dcdbmbspdao.getRwTable(ConditionMap);
    }

    @Override
    public int getRwCount(Map ConditionMap) {
        return dcdbmbspdao.getRwCount(ConditionMap);
    }

    @Override
    public List<DcdbBmmbnr> getZYRwTable(Map<String, Object> condtionMap) {
        return dcdbmbspdao.getZYRwTable(condtionMap);
    }

    @Override
    public int getZYRWCount(Map<String, Object> condtionMap) {
        return dcdbmbspdao.getZYRWCount(condtionMap);
    }

   /* @Override
    public Map<String, Object> getSpByNdId(String ndrwId) {
        return dcdbmbspdao.getSpByNdId(ndrwId);
    }*/

    @Override
    public DcdbBmmbnr deatil(String id) {
        return dcdbmbspdao.deatil(id);
    }

    @Override
    public void updateZyrw(Map<String, Object> map) {
         dcdbmbspdao.updateZyrw(map);
    }

    @Override
    public void saveSpyj(DcdbKHPZ dcdbkhpz) {
        dcdbmbspdao.saveSpyj(dcdbkhpz);
    }

    @Override
    public DcdbKHPZ selectSpyj(String spId) {
        return dcdbmbspdao.selectSpyj(spId);
    }

    @Override
    public void updateSpyj(DcdbKHPZ dcdbkhpz) {
        dcdbmbspdao.updateSpyj(dcdbkhpz);
    }

    @Override
    public void updateBmMbRwList(Map<String, Object> condtionMap) {
        dcdbmbspdao.updateBmMbRwList(condtionMap);
    }

    @Override
    public String queryPhbmList(String rwId) {
        return dcdbmbspdao.queryPhbmList(rwId);
    }
    @Override
    public String selectZtbm(Map<String, Object> condtionMap){
        return dcdbmbspdao.selectZtbm(condtionMap);
    }

    @Override
    public List<DcdbBmmbnr> selectDcdbBmmbnrList(Map<String, Object> condtionMap) {
        return dcdbmbspdao.selectDcdbBmmbnrList(condtionMap);
    }

    @Override
    public List<DcdbBmmbnr> selectAllZyrw(Map<String, Object> condtionMap) {
        return dcdbmbspdao.selectAllZyrw(condtionMap);
    }


}
