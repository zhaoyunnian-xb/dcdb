package com.bm.index.dao.source1;

import com.bm.index.model.DcdbSjjcsxDcsxnq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DcdbSjjcsxDcsxnqDao {
    int countByExample(DcdbSjjcsxDcsxnq example);

    int deleteByExample(DcdbSjjcsxDcsxnq example);

    int insert(DcdbSjjcsxDcsxnq record);

    int insertSelective(DcdbSjjcsxDcsxnq record);

    List<DcdbSjjcsxDcsxnq> selectByExample(DcdbSjjcsxDcsxnq example);

    int updateByExampleSelective(@Param("record") DcdbSjjcsxDcsxnq record);

    int updateByExample(@Param("record") DcdbSjjcsxDcsxnq record, @Param("example") DcdbSjjcsxDcsxnq example);

   void batchUpdateSjjc(List<DcdbSjjcsxDcsxnq> list);


    List<DcdbSjjcsxDcsxnq> queryNqfaTable(DcdbSjjcsxDcsxnq example);

    int batchUpdateNqfp( List<DcdbSjjcsxDcsxnq> list);

    //updateFa
    int updateFa( DcdbSjjcsxDcsxnq DcdbSjjcsxDcsxnq);

   //更新终止状态
   int updateIszz(DcdbSjjcsxDcsxnq record);
   //更新内勤分案后，无分案任务的待办案件状态为 ： 4
    int updateDbNq(Map<String,Object> map );
}