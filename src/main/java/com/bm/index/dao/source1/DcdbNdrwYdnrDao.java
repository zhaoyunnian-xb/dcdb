package com.bm.index.dao.source1;

import com.bm.index.model.DcdbNdrwYdnr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbNdrwYdnrDao {
    int countByExample(DcdbNdrwYdnr example);

    int deleteByExample(DcdbNdrwYdnr example);

    int deleteByPrimaryKey(String id);

    int insert(DcdbNdrwYdnr record);

    int insertSelective(DcdbNdrwYdnr record);

    List<DcdbNdrwYdnr> selectByExampleWithBLOBs(DcdbNdrwYdnr example);

    List<DcdbNdrwYdnr> selectByExample(DcdbNdrwYdnr example);

    List<DcdbNdrwYdnr> selectbyNdid(@Param("ndid") String ndid);

    List<DcdbNdrwYdnr> selectAll();

    DcdbNdrwYdnr selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DcdbNdrwYdnr record, @Param("example") DcdbNdrwYdnr example);

    int updateByExampleWithBLOBs(@Param("record") DcdbNdrwYdnr record, @Param("example") DcdbNdrwYdnr example);

    int updateByExample(@Param("record") DcdbNdrwYdnr record, @Param("example") DcdbNdrwYdnr example);

    int updateByPrimaryKeySelective(DcdbNdrwYdnr record);

    int updateByPrimaryKeyWithBLOBs(DcdbNdrwYdnr record);

    int updateByPrimaryKey(DcdbNdrwYdnr record);
}