package com.bm.index.service;

import com.bm.index.model.DcdbNdrwYdnr;

import java.util.List;

public interface DcdbNdrwYdnrService {

    int saveYdnr(DcdbNdrwYdnr dcdbNdrwYdnr);

    List<DcdbNdrwYdnr> selectAll();

    List<DcdbNdrwYdnr> selectbyNdid(String ndid);

    int deleteYdnrmc(String id);
}
