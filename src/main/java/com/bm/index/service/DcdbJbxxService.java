package com.bm.index.service;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbJbxx;
import com.bm.index.model.DcdbProjectFile;
import com.bm.index.model.DcdbSjjcsxDj;

import java.util.List;

public interface DcdbJbxxService {
    DcdbJbxx getById(String id);
    int save(DcdbJbxx dcdbjbxx);
    int  update(DcdbJbxx dcdbjbxx);
   //查询wh,用来新制定文号
    List<DcdbJbxx> selectWh();

    List<DcdbProjectFile> selectUploadFiles(String id, String ywtype);

    //查询所有待办表中需要计算预警情况的数据
    List<DcdbDjlbDbParam> queryYjqk();

    /*
    * 上级决策 -  模块  Start
    * */
    DcdbSjjcsxDj getDcdbSjjcsxDjById(String id); //根据ID查询上级决策表中某一待办案件的详细信息
    DcdbSjjcsxDj getDcdbSjjcsxDbById(String id); //根据ID查询上级决策某一待办案件在待办表中是否有数据
    int saveDcdbSjjcsxDj(DcdbSjjcsxDj dcdbsjjcsxdj);//保存基本信息--登记表
    int insertDcdbSjjcsxDb(DcdbSjjcsxDj dcdbsjjcsxdj);//待办表中新增数据
    int updateDcdbSjjcsxDj(DcdbSjjcsxDj dcdbsjjcsxdj);//基本信息表的更新
    int updateDcdbSjjcsxDb(DcdbSjjcsxDj dcdbsjjcsxdj);//待办表的更新
}
