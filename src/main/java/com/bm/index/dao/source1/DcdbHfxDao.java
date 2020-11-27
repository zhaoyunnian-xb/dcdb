package com.bm.index.dao.source1;

import com.bm.index.model.DcdbHfx;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcdbHfxDao {



    int insertSelective(DcdbHfx record);

    List<DcdbHfx> selectByExample(DcdbHfx example);

    int updateByExampleSelective(@Param("record") DcdbHfx record);

}