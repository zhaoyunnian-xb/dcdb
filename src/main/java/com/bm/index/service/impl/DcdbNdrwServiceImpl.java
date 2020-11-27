package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbNdrwDao;
import com.bm.index.model.DcdbNdrw;
import com.bm.index.service.DcdbNdrwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcdbNdrwServiceImpl implements DcdbNdrwService {

    @Autowired
    private DcdbNdrwDao ndrwDao;

    @Override
    public List<DcdbNdrw> queryTable(DcdbNdrw ndrw) {
        return ndrwDao.selectAll(ndrw);
    }

    @Override
    public int deleteById(String id) {
        return ndrwDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insertNdrw(DcdbNdrw dcdbNdrw) {
        return ndrwDao.insertSelective(dcdbNdrw);
    }

    @Override
    public int updateStatus(DcdbNdrw dcdbNdrw) {
        return ndrwDao.updateStatus(dcdbNdrw);
    }

    @Override
    public DcdbNdrw queryByid(String id) {
        return ndrwDao.queryByid(id);
    }
}
