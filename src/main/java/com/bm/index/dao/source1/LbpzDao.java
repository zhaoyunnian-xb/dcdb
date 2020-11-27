package com.bm.index.dao.source1;

import com.bm.index.model.LbpzPlb;
import com.bm.index.model.LbpzZlb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LbpzDao {
    List<LbpzPlb> getAllLbpzPlbList(LbpzPlb lbpzplb);
    List<LbpzZlb> queryTable(LbpzZlb lbpzzlb);

    void delChildrenLb(String id);

    void updaterXlb(LbpzZlb lbpzzlb);
    void updaterPlb(LbpzPlb lbpzplb);

    void delParentLb(String id);

    void insertPlb(LbpzPlb lbpzplb);

    void insertXlb(LbpzZlb lbpzzlb);

    LbpzPlb getLbpzPlbById(@Param("lbtype") String lbtype);
    LbpzZlb getLbpzZlbById(@Param("id") String id);
}
