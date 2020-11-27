package com.bm.index.dao.source1;

import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbKHPZ;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DcdbMbSpDao {
    public List getRwTable(Map ConditionMap);
    int getRwCount(Map ConditionMap);

    List<DcdbBmmbnr> getZYRwTable(Map<String, Object> condtionMap);

    int getZYRWCount(Map<String, Object> condtionMap);

   /* Map<String, Object> getSpByNdId(String ndrwId);*/

    DcdbBmmbnr deatil(@Param("id") String id);

    void updateZyrw(Map<String, Object> map);

    void saveSpyj(DcdbKHPZ dcdbkhpz);

    DcdbKHPZ selectSpyj(String spId);

    void updateSpyj(DcdbKHPZ dcdbkhpz);

    void updateBmMbRwList(Map<String, Object> condtionMap);

    String queryPhbmList(String rwId);

    String selectZtbm(Map<String, Object> condtionMap);

    List<DcdbBmmbnr> selectDcdbBmmbnrList(Map<String, Object> condtionMap);

    List<DcdbBmmbnr> selectAllZyrw(Map<String, Object> condtionMap);
}
