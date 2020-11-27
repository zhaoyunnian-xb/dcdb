package com.bm.index.service;

import com.bm.index.model.DcdbNdrw;

import java.util.List;

public interface DcdbNdrwService {

    //年度任务查询列表
    public List<DcdbNdrw> queryTable(DcdbNdrw ndrw);

    int deleteById(String id);

    int insertNdrw(DcdbNdrw dcdbNdrw);

    int updateStatus(DcdbNdrw dcdbNdrw);

    DcdbNdrw queryByid(String id);
}
