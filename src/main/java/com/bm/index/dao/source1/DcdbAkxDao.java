package com.bm.index.dao.source1;

import com.bm.index.model.DcdbAkx;
import java.util.List;
import java.util.Map;

import com.bm.index.model.Menu;
import org.apache.ibatis.annotations.Param;

public interface DcdbAkxDao {

    int insertSelective(DcdbAkx record);

    List<DcdbAkx> selectByExample(DcdbAkx example);

    int updateByExampleSelective(@Param("record") DcdbAkx record);
	
	List<Menu> testTree(Map<String,String> info);
}