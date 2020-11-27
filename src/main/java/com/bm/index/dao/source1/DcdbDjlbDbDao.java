package com.bm.index.dao.source1;

import com.bm.index.model.DcdbDjlbDb;
import com.bm.index.model.DcdbDjlbDb;
import java.util.List;
import java.util.Map;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbSjjcsxDj;
import org.apache.ibatis.annotations.Param;

public interface DcdbDjlbDbDao {
    int countByExample(DcdbDjlbDb example);

    int deleteByExample(DcdbDjlbDb example);

    int deleteByPrimaryKey(String id);

    int insert(DcdbDjlbDb record);

    int insertSelective(DcdbDjlbDb record);

    List<DcdbDjlbDb> selectByExample(DcdbDjlbDb example);

    DcdbDjlbDb selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Map<String,Object> record);

    int updateByExample(@Param("record") DcdbDjlbDb record);

    int updateByPrimaryKeySelective(DcdbDjlbDb record);

    int updateByPrimaryKey(DcdbDjlbDb record);

    List<DcdbDjlbDbParam> queryTable(DcdbDjlbDbParam dbParam);
    //根据文号和办理类型查询待办列表中新建案件是否已存在
    List<DcdbDjlbDb> selectDbByWh(Map map);

    List<Map<String, String>> queryCbBm();

    List<Map<String, String>> queryBmPerson(Map<String,String> map);

    int batchUpdate(Map<String,Object> param);

    //统计列表查询
    List<DcdbDjlbDbParam> queryTjTable(DcdbDjlbDbParam dbParam);

    List<DcdbSjjcsxDj> queryZyjcTable(DcdbSjjcsxDj dcdbSjjcsxDj);

    //根据userid查询待办列表
    List<DcdbDjlbDbParam> selectByUserId(@Param("userid") String userid);

    //查询归档列表
    List<DcdbDjlbDbParam> queryTableGd(DcdbDjlbDbParam dbParam);

    //查询退回的待办列表
    List<DcdbDjlbDbParam> queryTableReback(DcdbDjlbDbParam dbParam);
}