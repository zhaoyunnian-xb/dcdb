package com.bm.index.dao.source1;

import com.bm.index.model.DcdbSjjcsxDbzq;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcdbSjjcsxDbzqDao {
    int countByExample(DcdbSjjcsxDbzq example);

    int deleteByPrimaryKey(String id);

    int deleteByDcsxId(String dcsxId);

    List<DcdbSjjcsxDbzq> selectByExample(DcdbSjjcsxDbzq example);

    DcdbSjjcsxDbzq selectByPrimaryKey(String id);

    int insertSelective(DcdbSjjcsxDbzq record);

    int updateByPrimaryKeySelective(DcdbSjjcsxDbzq record);

}