package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbDjlbYbDao;
import com.bm.index.model.DcdbDjlbYbParam;
import com.bm.index.model.DcdbSjjcsxDj;
import com.bm.index.service.DcdbYbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcdbYbServiceImpl implements DcdbYbService {

    @Autowired
    private DcdbDjlbYbDao ybDao;

    @Override
    public List<DcdbDjlbYbParam> queryTable(DcdbDjlbYbParam ybParam) {
        List<DcdbDjlbYbParam> list  = ybDao.queryTable(ybParam);
        for (DcdbDjlbYbParam yb : list){
            if ("3".equals(yb.getZt())){
                yb.setYjqk("");
            }
        }
        return list;
    }

  @Override
  public List<DcdbSjjcsxDj> queryZyjcTableyb(DcdbSjjcsxDj dcdbSjjcsxDj) {
    return ybDao.queryZyjcTableYb(dcdbSjjcsxDj);
  }

}
