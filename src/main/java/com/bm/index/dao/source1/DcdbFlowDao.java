package com.bm.index.dao.source1;

import com.bm.index.model.DcdbDjlbDb;
import com.bm.index.model.DcdbDjlbYb;
import com.bm.index.model.DcdbLcjd;
import com.bm.index.model.DcdbSpyj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbFlowDao {

    List<DcdbLcjd> selectLcjd(DcdbLcjd lcjd);

    List<DcdbDjlbYb> selectYb(DcdbDjlbYb yb);

    int updateByExampleSelective(@Param("record") DcdbDjlbDb record);

    DcdbDjlbDb selectByPrimaryKey(String id);

    void batcheInsertDb(List<DcdbDjlbDb> list);

    int updateSpyj(@Param("record") DcdbSpyj record);

    int insertSpyj(DcdbSpyj record);

    List<DcdbSpyj> selectSpyj(DcdbSpyj spyj);



}