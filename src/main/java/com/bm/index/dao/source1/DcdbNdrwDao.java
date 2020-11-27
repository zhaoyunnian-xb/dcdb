package com.bm.index.dao.source1;

import com.bm.index.model.DcdbNdrw;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcdbNdrwDao {
    int countByExample(DcdbNdrw example);

    int deleteByExample(DcdbNdrw example);

    int deleteByPrimaryKey(String id);

    int insert(DcdbNdrw record);

    int insertSelective(DcdbNdrw record);


    List<DcdbNdrw> selectAll(DcdbNdrw ndrw);

    DcdbNdrw queryByid(@Param("id") String id);

    int updateByExampleSelective(@Param("record") DcdbNdrw record, @Param("example") DcdbNdrw example);

    int updateByExample(@Param("record") DcdbNdrw record, @Param("example") DcdbNdrw example);

    int updateByPrimaryKeySelective(DcdbNdrw record);

    int updateByPrimaryKey(DcdbNdrw record);

    int updateStatus(DcdbNdrw ndrw);
}