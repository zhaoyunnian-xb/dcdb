package com.bm.index.dao.source1;

import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbNdrw;
import com.bm.index.model.DcdbProjectFile;
import com.bm.index.model.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DcdbBmmbnrDao {

  int deleteByNdid(String ndid);

  //插入部门内容表
  int insertSelective(DcdbBmmbnr record);

  List<DcdbBmmbnr> selectBmJdtb(DcdbBmmbnr record);

  //查询季度完成情况内容列表
  List<DcdbBmmbnr> selectJdtb(DcdbBmmbnr record);

  //更新部门内容表
  int updateJdwcqk(DcdbBmmbnr record);

  //发起后更新内容表
  int updatefqgx(DcdbBmmbnr record);


  //查询批示意见表
  List<DcdbBmmbnr> selectKhpzById(@Param("yjid") String yjid,@Param("ssbm") String ssbm);

  //更新批示意见表
  int updateKhpzById(DcdbBmmbnr record);

  //插入批示意见表
  int insertKhpz(DcdbBmmbnr record);

  //插入 附件表
  int insertDcdbFile(DcdbProjectFile record);

  //删除附件表
  void deleteDcdbFile(DcdbProjectFile record);

  //查询附件表
  List<DcdbProjectFile> selectFileByCol(@Param("col") String col, @Param("value") String value,
      @Param("ywtype") String ywtype);

  //查询登录用户信息
  List<UserEntity> loginQuery(@Param("id") String id);

  //查询登录用户信息
  List<UserEntity> loginQueryByName(@Param("username") String username);

  //该部门是否都是配合任务
  List<String> selectIsAllPh(DcdbBmmbnr record);

  //查询待办任务列表
  List<DcdbNdrw> queryBmdbList(DcdbNdrw record);

  //查询待办页面列表（办公室审批人）
  List<DcdbNdrw> queryBmdbListBgs(DcdbNdrw record);
  //查询待办页面列表（办公室审批人）
  List<DcdbNdrw> queryBmdbListBgsByNdid(DcdbNdrw record);
  //查询部门列表
  List<Map<String, String>> queryBmList(@Param("ndid") String ndid);

  //查询部门表
  Map<String, String> selectUnit(@Param("unit") String id);

  List<Map<String, String>> queryTest();

  //查询已办页面列表
  List<DcdbNdrw> queryBmdbListYb(DcdbNdrw record);

  //插入已办表
  int insertNdrwYb(DcdbBmmbnr record);

  //查询已办详情页-主要任务列表
  List<DcdbBmmbnr> selectZyrwListYb(DcdbBmmbnr record);

  //查询已办页面列表
  List<DcdbNdrw> queryBmdbListTbjd(DcdbNdrw record);

  //进度明细页面列表

  List<DcdbBmmbnr> selectZyrwListTbjd(DcdbBmmbnr record);

}