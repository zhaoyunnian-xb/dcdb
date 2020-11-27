package com.bm.index.service;

import com.bm.index.model.*;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface DcdbTjService {
     //查询一季度
    List<Object> selectTjYjd(String jdlx, String ndid,String cbbmid);


    List<Map<String,Object>> selectRw(String cbbmid,String tjzt);
    
    List<Map<String,Object>> doSelectNdrw();

    void importExecl( String jdlx, String ndid, String cbbmid,String fileName, HttpServletResponse response);

    void importExeclQnmb( String jdlx, String ndid, String cbbmid,String fileName, HttpServletResponse response);

    String  updateTjzt(String tjzt,String id);

    Map<String, Object> bmtj(String zt,String lclx,String startTime,String endTime);

    void importBm(String zt,String lx,String lclx,String nf, String checkedtype,HttpServletRequest httpServletRequest, HttpServletResponse response,String startTime,String endTime);

    void importExeclDetail(String lxdetail_zt, String lxdetail_lclx, String lxdetail_startDate, String lxdetail_endDate,String lxdetail_checkedtype,
                           String lxdetail_lxtypename,String tjlx,HttpServletRequest httpServletRequest, HttpServletResponse response);

    Map<String, Object> lxtj(String zt,String lclx,String nf);

    Map<String, Object> zttj(String zt, String lclx,String nf,String startTime,String endTime);
    Map<String, Object> newlxtj(Map map);


    List<SjjcsxRt> sjjcsxRt(String djid);

    List<Map<String, String>> sjjcsxDj();

    Map<String, Object> addTzgs(DcdbNdrwTzgs tzgs);

    Map<String, Object> queryTzgsList(DcdbNdrwTzgs tzgs);

    List<DcdbDjlbDbParam> queryDetaillx(Map map);

    List<DcdbDjlbDbParam> queryDetailzt(String zt, String lclx, String nf, String startTime, String endTime);

    List<DcdbDjlbDbParam> queryDetailbm(String zt, String lclx, String startTime, String endTime, String cbbmid);
}
