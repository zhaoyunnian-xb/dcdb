package com.bm.index.dao.source1;

import com.bm.index.model.DcdbDb;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcdbDbDao {
    
    
    int deleteSelective(DcdbDb record);
    
    int insertSelective(DcdbDb record);

    List<DcdbDb> selectByExample(DcdbDb example);

    int updateByExampleSelective(@Param("record") DcdbDb record);

}