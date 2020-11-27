package com.bm.index.service;

import com.bm.index.model.DcdbNdrwZyrw;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcdbNdrwZyrwService {

    List<DcdbNdrwZyrw> queryynr();

    List<DcdbNdrwZyrw> selectByExample(DcdbNdrwZyrw zyrw);

    int insertSelective(DcdbNdrwZyrw zyrw);

    int deleteZynr(String id);

    DcdbNdrwZyrw selectByPrimaryKey(String id);

    int updateByExampleSelective(DcdbNdrwZyrw record);




}
