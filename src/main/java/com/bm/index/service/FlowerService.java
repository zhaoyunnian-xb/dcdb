package com.bm.index.service;

import com.bm.index.model.Flow;
import java.util.List;
import java.util.Map;

public interface FlowerService {
    Map<String,Object> getFlower(String ywtype,String id,String preid);
    List<Flow> getFlowList(String ywtype,String id,String preid);
}
