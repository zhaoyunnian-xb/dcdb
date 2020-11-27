package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbNdrwZyrwDao;
import com.bm.index.model.DcdbNdrwZyrw;
import com.bm.index.service.DcdbNdrwZyrwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcdbNdrwZyrwServiceImpl implements DcdbNdrwZyrwService {

    @Autowired
    private DcdbNdrwZyrwDao zyrwDao;

    @Override
    public List<DcdbNdrwZyrw> queryynr() {
        return zyrwDao.selectAll();
    }

    @Override
    public List<DcdbNdrwZyrw> selectByExample(DcdbNdrwZyrw zyrw) {
        return zyrwDao.selectByExample(zyrw);
    }

    @Override
    public int insertSelective(DcdbNdrwZyrw zyrw) {
        return zyrwDao.insertSelective(zyrw);
    }

    @Override
    public int deleteZynr(String id) {
        return zyrwDao.deleteByPrimaryKey(id);
    }

    @Override
    public DcdbNdrwZyrw selectByPrimaryKey(String id) {
        return zyrwDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(DcdbNdrwZyrw record) {
        return zyrwDao.updateByExampleSelective(record);
    }
}
