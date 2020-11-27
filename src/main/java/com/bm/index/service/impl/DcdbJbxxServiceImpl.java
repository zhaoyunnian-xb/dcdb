package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbJbxxDao;
import com.bm.index.dao.source1.DcdbSjjcsxDjDao;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbJbxx;
import com.bm.index.model.DcdbProjectFile;
import com.bm.index.model.DcdbSjjcsxDj;
import com.bm.index.service.DcdbJbxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcdbJbxxServiceImpl implements DcdbJbxxService {
    @Autowired
    private DcdbJbxxDao dcdbjbxxdao;
    @Autowired
    private DcdbSjjcsxDjDao dcdbsjjcsxdjdao;
    @Override
    public DcdbJbxx getById(String id) {
        return dcdbjbxxdao.getById(id);
    }

    @Override
    public int save(DcdbJbxx dcdbjbxx) {
        int i = 0;
        try{
            dcdbjbxxdao.save(dcdbjbxx);
            i= 1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int  update(DcdbJbxx dcdbjbxx) {
        int i = 0;
        try{
            dcdbjbxxdao.update(dcdbjbxx);
            i= 1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return i;

    }

    @Override
    public List<DcdbJbxx> selectWh() {
        return  dcdbjbxxdao.selectWh();
    }

    @Override
    public List<DcdbProjectFile> selectUploadFiles(String id, String ywtype) {
        List<DcdbProjectFile> list = dcdbjbxxdao.selectUploadFiles(id,ywtype);
        return list;
    }


    @Override
    public List<DcdbDjlbDbParam> queryYjqk() {
        return dcdbjbxxdao.queryYjqk();
    }
   /**
    * 上级决策模块 Start
    *
    * */
    //根据Id查询某一上级决策待办案件的详情
    @Override
    public DcdbSjjcsxDj getDcdbSjjcsxDjById(String id) {
        DcdbSjjcsxDj dcdbsjjcsxdj =  dcdbsjjcsxdjdao.getDcdbSjjcsxDjById(id);
        return dcdbsjjcsxdj;
    }

    @Override
    public DcdbSjjcsxDj getDcdbSjjcsxDbById(String id) {
        DcdbSjjcsxDj dcdbsjjcsxdj =  dcdbsjjcsxdjdao.getDcdbSjjcsxDbById(id);
        return dcdbsjjcsxdj;
    }

    @Override
    public int saveDcdbSjjcsxDj(DcdbSjjcsxDj dcdbsjjcsxdj) {
        int i = dcdbsjjcsxdjdao.insertSelective(dcdbsjjcsxdj);
        return i;
    }

    @Override
    public int insertDcdbSjjcsxDb(DcdbSjjcsxDj dcdbsjjcsxdj) {
        int i = dcdbsjjcsxdjdao.insertDcdbSjjcsxDb(dcdbsjjcsxdj);
        return i;
    }

    @Override
    public int updateDcdbSjjcsxDj(DcdbSjjcsxDj dcdbsjjcsxdj) {
        int i = dcdbsjjcsxdjdao.updateByExampleSelective(dcdbsjjcsxdj);
        return i;
    }

    @Override
    public int updateDcdbSjjcsxDb(DcdbSjjcsxDj dcdbsjjcsxdj) {
        int i = dcdbsjjcsxdjdao.updateDcdbSjjcsxDb(dcdbsjjcsxdj);
        return i;
    }
}
