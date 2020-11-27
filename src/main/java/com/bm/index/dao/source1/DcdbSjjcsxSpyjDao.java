package com.bm.index.dao.source1;

import com.bm.index.model.DcdbSjjcsxSpyj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbSjjcsxSpyjDao {
    int countByExample(DcdbSjjcsxSpyj example);

    int deleteByExample(DcdbSjjcsxSpyj example);

    int insert(DcdbSjjcsxSpyj record);

    int insertSelective(DcdbSjjcsxSpyj record);

    List<DcdbSjjcsxSpyj> selectByExample(DcdbSjjcsxSpyj example);

    List<DcdbSjjcsxSpyj> selectWqck(DcdbSjjcsxSpyj example);

    int updateByExampleSelective(@Param("record") DcdbSjjcsxSpyj record);

}