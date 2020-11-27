package com.bm.index.service;

import com.bm.index.model.DcdbSjjcsxDcsxnq;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DcdbSjjcNqfaService {
    List<DcdbSjjcsxDcsxnq> queryNqfaTable(DcdbSjjcsxDcsxnq example);
    Map<String,Object> updateFp(HttpSession se, Map<String,String> map);
    Map<String,Object> updateCx(HttpSession se, Map<String,String> map);
    Map<String,Object> updateFa(HttpSession se,List<DcdbSjjcsxDcsxnq> list,DcdbSjjcsxDcsxnq example,String bllx);
}
