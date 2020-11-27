package com.bm.index.dao.source1;

import com.bm.index.model.DcdbDjlbYbParam;
import com.bm.index.model.Flow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowerDao {
    /***
     * 查询节点图
     * @param ywtype
     * @param id
     * @return
     */
    List<Flow> selectFlower(@Param("ywtype") String ywtype,@Param("id") String id,@Param("preid") String preid);

    List<String> selectIdByWh(@Param("wh") String wh);

    List<DcdbDjlbYbParam> selectById(@Param("id") String id);

}
