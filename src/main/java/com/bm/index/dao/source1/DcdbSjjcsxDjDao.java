package com.bm.index.dao.source1;

import com.bm.index.model.DcdbSjjcsxDj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbSjjcsxDjDao {
    int countByExample(DcdbSjjcsxDj example);

    int deleteByExample(DcdbSjjcsxDj example);

    int insert(DcdbSjjcsxDj record);

    int insertSelective(DcdbSjjcsxDj record);

    List<DcdbSjjcsxDj> selectByExample(DcdbSjjcsxDj example);

    int updateByExampleSelective(@Param("record") DcdbSjjcsxDj record);

    int updateByExample(@Param("record") DcdbSjjcsxDj record, @Param("example") DcdbSjjcsxDj example);

    DcdbSjjcsxDj getDcdbSjjcsxDjById(@Param("id") String id);

    DcdbSjjcsxDj getDcdbSjjcsxDbById(@Param("id") String id);

    int insertDcdbSjjcsxDb(DcdbSjjcsxDj record);

    int updateDcdbSjjcsxDb(DcdbSjjcsxDj record);



}