package com.bm.index.service;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbSjjcsxDj;

import java.util.List;
import java.util.Map;

public interface DcdbDbService {

    //待办列表查询
    List<DcdbDjlbDbParam> queryTable(DcdbDjlbDbParam dbParam);

    List<Map<String, String>> queryCbBm();

    List<Map<String, String>> queryBmPerson(Map<String,String> map);

    //定时任务批量插入待办表更新预警状态
    int batchUpdate(Map<String,Object> param);

    //查询统计列表
    List<DcdbDjlbDbParam> queryTjTable(DcdbDjlbDbParam dbParam);
    //查询重大决策待办列表
    List<DcdbSjjcsxDj> queryZyjcTable(DcdbSjjcsxDj dcdbSjjcsxDj);

    List<DcdbDjlbDbParam> selectByUserId(String userid);

    //已归档列表查询
    List<DcdbDjlbDbParam> queryTableGd(DcdbDjlbDbParam dbParam);
}
