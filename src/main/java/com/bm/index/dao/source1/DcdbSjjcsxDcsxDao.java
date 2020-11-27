package com.bm.index.dao.source1;

import com.bm.index.model.DcdbSjjcsxDcsx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbSjjcsxDcsxDao {
    int countByExample(DcdbSjjcsxDcsx example);

    int deleteByExample(DcdbSjjcsxDcsx example);

    int insert(DcdbSjjcsxDcsx record);

    int insertSelective(DcdbSjjcsxDcsx record);

    List<DcdbSjjcsxDcsx> selectByExample(DcdbSjjcsxDcsx example);

    DcdbSjjcsxDcsx selectById(DcdbSjjcsxDcsx example);

    int updateByExampleSelective(@Param("record") DcdbSjjcsxDcsx record, @Param("example") DcdbSjjcsxDcsx example);

    int updateByExample(@Param("record") DcdbSjjcsxDcsx record, @Param("example") DcdbSjjcsxDcsx example);

    int updateIsfq(DcdbSjjcsxDcsx example);
}