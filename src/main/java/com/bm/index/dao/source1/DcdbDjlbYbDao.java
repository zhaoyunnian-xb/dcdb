package com.bm.index.dao.source1;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbDjlbYb;
import com.bm.index.model.DcdbDjlbYb;
import com.bm.index.model.DcdbSjjcsxDj;
import java.util.List;

import com.bm.index.model.DcdbDjlbYbParam;
import org.apache.ibatis.annotations.Param;

public interface DcdbDjlbYbDao {
    int countByExample(DcdbDjlbYb example);

    int deleteByExample(DcdbDjlbYb example);

    int deleteByPrimaryKey(String id);

    int insert(DcdbDjlbYb record);

    int insertSelective(DcdbDjlbYb record);

    List<DcdbDjlbYb> selectByExample(DcdbDjlbYb example);

    DcdbDjlbYb selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DcdbDjlbYb record);

    int updateByExample(@Param("record") DcdbDjlbYb record, @Param("example") DcdbDjlbYb example);

    int updateByPrimaryKeySelective(DcdbDjlbYb record);

    int updateByPrimaryKey(DcdbDjlbYb record);

    List<DcdbDjlbYbParam> queryTable(DcdbDjlbYbParam ybParam);

    List<DcdbSjjcsxDj> queryZyjcTableYb(DcdbSjjcsxDj ybParam);

    List<DcdbDjlbYbParam> queryTableById(DcdbDjlbYbParam ybParam);
}