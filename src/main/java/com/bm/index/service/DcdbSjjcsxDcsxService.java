package com.bm.index.service;

import com.bm.index.model.DcdbSjjcsxDcsx;
import com.bm.index.model.DcdbSjjcsxDcsxnq;

import java.util.List;

public interface DcdbSjjcsxDcsxService {

    //查询分工列表
    List<DcdbSjjcsxDcsx> selectByExample(DcdbSjjcsxDcsx example);

    //删除分工列表
    int deleteByExample(DcdbSjjcsxDcsx example);

    //插入分工列表
    int insert(DcdbSjjcsxDcsx record);

    DcdbSjjcsxDcsx selectById(DcdbSjjcsxDcsx example);

    //分工发起插入DCDB_SJJCSX_DCSXNQ表中
    int insertDcsxnq(DcdbSjjcsxDcsxnq record);

    //更新发起状态 0，未发起（默认） 1，已发起
    int updateIsfq(DcdbSjjcsxDcsx record);

    int updateIszz(DcdbSjjcsxDcsxnq record);

    //更新插入待办表
    int updateDb(String wh, String cbbmmc, String cbbmid, String userid, String userName,
                 String bllx, String lclx);

}
