package com.bm.index.service;

import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbKHPZ;

import java.util.List;
import java.util.Map;

public interface DcdbMbSpService {
    public List getRwTable(Map ConditionMap);
    int getRwCount(Map ConditionMap);

    List<DcdbBmmbnr> getZYRwTable(Map<String, Object> condtionMap);
    int getZYRWCount(Map<String, Object> condtionMap);

   /* Map<String, Object> getSpByNdId(String ndrwId);*/

    DcdbBmmbnr deatil(String id);

    void updateZyrw(Map<String, Object> map);
    //目标意见审核存储
    void saveSpyj(DcdbKHPZ dcdbkhpz);
    //目标意见审核查询
    DcdbKHPZ selectSpyj(String spId);
    //目标意见审核更新
    void updateSpyj(DcdbKHPZ dcdbkhpz);
    //更新部门任务列表的所有任务的承办人
    void updateBmMbRwList(Map<String, Object> condtionMap);

    String queryPhbmList(String rwId);

    String selectZtbm(Map<String, Object> condtionMap);

    List<DcdbBmmbnr> selectDcdbBmmbnrList(Map<String, Object> condtionMap);

    List<DcdbBmmbnr> selectAllZyrw(Map<String, Object> condtionMap);
}
