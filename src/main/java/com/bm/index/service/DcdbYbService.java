package com.bm.index.service;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbDjlbYbParam;

import com.bm.index.model.DcdbSjjcsxDj;
import java.util.List;
import java.util.Map;

public interface DcdbYbService {

    //待办列表查询
    List<DcdbDjlbYbParam> queryTable(DcdbDjlbYbParam ybParam);

  List<DcdbSjjcsxDj> queryZyjcTableyb(DcdbSjjcsxDj dcdbSjjcsxDj);
}
