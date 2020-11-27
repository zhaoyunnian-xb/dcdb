package com.bm.index.service;

import com.bm.index.model.DcdbSjjcsxDbzq;

import java.util.List;

public interface DcdbSjjcsxDbzqService {

    List<DcdbSjjcsxDbzq> selectByExample(DcdbSjjcsxDbzq example);

    int insertSelective(DcdbSjjcsxDbzq record);

    int deleteById(String id);

    int deleteByDcsxId(String dcsxId);

}
