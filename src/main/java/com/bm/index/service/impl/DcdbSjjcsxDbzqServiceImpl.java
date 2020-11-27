package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbSjjcsxDbzqDao;
import com.bm.index.model.DcdbSjjcsxDbzq;
import com.bm.index.service.DcdbSjjcsxDbzqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcdbSjjcsxDbzqServiceImpl implements DcdbSjjcsxDbzqService {
    @Autowired
    private DcdbSjjcsxDbzqDao dbzqDao;
    @Override
    public List<DcdbSjjcsxDbzq> selectByExample(DcdbSjjcsxDbzq example) {
        List<DcdbSjjcsxDbzq> dbzqList = dbzqDao.selectByExample(example);
        for(DcdbSjjcsxDbzq dbzq:dbzqList){
           switch (dbzq.getDczqtype()){
               case "0":
                   dbzq.setDczqtype("每周");
                   break;
               case "1":
                   dbzq.setDczqtype("每月");
                   break;
               case "2":
                   dbzq.setDczqtype("每季度");
                   break;
               case "3":
                   dbzq.setDczqtype("每半年");
                   break;
               case "4":
                   dbzq.setDczqtype("定制天数");
                   break;
           }
        }
        return dbzqList;
    }

    @Override
    public int insertSelective(DcdbSjjcsxDbzq record) {
        return dbzqDao.insertSelective(record);
    }

    @Override
    public int deleteById(String id) {
        return dbzqDao.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByDcsxId(String dcsxId) {
        return dbzqDao.deleteByDcsxId(dcsxId);
    }
}
