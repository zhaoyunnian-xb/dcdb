package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbNdrwYdnrDao;
import com.bm.index.model.DcdbNdrwYdnr;
import com.bm.index.service.DcdbNdrwYdnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcdbNdrwYdnrServiceImpl implements DcdbNdrwYdnrService {

    @Autowired
    private DcdbNdrwYdnrDao ydnrDao;

    @Override
    public int saveYdnr(DcdbNdrwYdnr dcdbNdrwYdnr) {
        int i = ydnrDao.insertSelective(dcdbNdrwYdnr);
        return i;
    }

    @Override
    public List<DcdbNdrwYdnr> selectAll() {
        return ydnrDao.selectAll();
    }

    @Override
    public List<DcdbNdrwYdnr> selectbyNdid( String ndid) {
        return ydnrDao.selectbyNdid(ndid);
    }

    @Override
    public int deleteYdnrmc(String id) {
        return ydnrDao.deleteByPrimaryKey(id);
    }
}
