package com.bm.index.dao.source1;

import com.bm.index.model.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface DcdbTjDao {

    List<DcdbTj> selectTj(@Param("jdlx")String jdlx, @Param("ndid") String ndid,@Param("cbbmid")String cbbmid);

    List<Map<String,Object>> selectRw(@Param("cbbmid") String cbbmid,@Param("tjzt")String  tjzt);
    
    List<Map<String,Object>> selectNdrw();
    
    int  updateTjzt(@Param("tjzt") String tjzt,@Param("id") String id);

    List<Map<String, Object>> selectRwgs();

    List<DcdbCounTj> getBmtj(@Param("zt") String zt, @Param("lclx")String lclx,@Param("startTime")String startTime,@Param("endTime")String endTime);

    List<DcdbCounTj> getLxtj(@Param("zt") String zt, @Param("lclx")String lclx,@Param("nf")String nf);

    List<DcdbCounTj>  getZttj(@Param("lclx")String lclx,@Param("nf")String nf,@Param("startTime")String startTime,@Param("endTime")String endTime);

    List<SjjcsxRt> sjjcsxRt(@Param("djid") String djid);

    List<Map<String, String>> sjjcsxDj();

    List<DcdbNdrwTzgs> queryTzgsList(DcdbNdrwTzgs tzgs);

    List<DcdbNdrwTzgs> queryTzgsNdrwList(DcdbNdrwTzgs tzgs);

    int insertTzgs(DcdbNdrwTzgs tzgs);

    int deleteTzgs(DcdbNdrwTzgs tzgs);

    List<DcdbTj> selectTjQnmb(@Param("ndid") String ndid);

    List<Map<String,Object>> newqueryLxtj(Map map);

    List<DcdbDjlbDbParam>  queryDetaillx(Map map);

    List<DcdbDjlbDbParam> queryDetailzt(@Param("zt")String zt, @Param("lclx")String lclx, @Param("nf")String nf, @Param("startTime")String startTime, @Param("endTime")String endTime);

    List<DcdbDjlbDbParam> queryDetailbm(@Param("zt")String zt, @Param("lclx")String lclx, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("cbbmid")String cbbmid);
}
