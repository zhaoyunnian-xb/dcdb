package com.bm.index.dao.source1;

import com.bm.index.model.DcdbNdrwZyrw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbNdrwZyrwDao {
    int countByExample(DcdbNdrwZyrw example);

    int deleteByExample(DcdbNdrwZyrw example);

    int deleteByPrimaryKey(String id);

    int insert(DcdbNdrwZyrw record);

    int insertSelective(DcdbNdrwZyrw record);

    List<DcdbNdrwZyrw> selectByExampleWithBLOBs(DcdbNdrwZyrw example);

    List<DcdbNdrwZyrw> selectByExample(DcdbNdrwZyrw example);

    List<DcdbNdrwZyrw> selectAll();

    DcdbNdrwZyrw selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DcdbNdrwZyrw record);

    int updateByExampleWithBLOBs(@Param("record") DcdbNdrwZyrw record, @Param("example") DcdbNdrwZyrw example);

    int updateByExample(@Param("record") DcdbNdrwZyrw record, @Param("example") DcdbNdrwZyrw example);

    int updateByPrimaryKeySelective(DcdbNdrwZyrw record);

    int updateByPrimaryKeyWithBLOBs(DcdbNdrwZyrw record);

    int updateByPrimaryKey(DcdbNdrwZyrw record);
}