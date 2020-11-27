package com.bm.index.service.impl;


import com.bm.index.dao.source1.DcdbFlowDao;
import com.bm.index.dao.source1.DcdbTjDao;
import com.bm.index.model.*;

import com.bm.index.util.ConfigProperties;
import java.io.File;

import com.bm.index.util.JxlsExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bm.index.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;


@Service
public class DcdbTjServiceImpl implements DcdbTjService {

  @Autowired
  private DcdbTjDao d;
  @Autowired
  private DcdbFlowDao flowDao;

  @Override
  public List<Object> selectTjYjd(String jdlx, String ndid, String cbbmid) {
    if (StringUtils.isBlank(ndid)) {
      List<Object> relist = new ArrayList<Object>();
      Map<String, String> msgMap = new HashMap<String, String>();
      msgMap.put("stauts", "2");
      msgMap.put("msg", "没有相关数据,");
      relist.add(msgMap);
      return relist;
    }
    List<DcdbTj> list = d.selectTj(jdlx, ndid, cbbmid);
    List<Object> relist = new ArrayList<Object>();
    if (!CollectionUtils.isEmpty(list)) {
      Map<String, String> msgMap = new HashMap<String, String>();
      msgMap.put("stauts", "1");
      relist.add(msgMap);
      relist = handleData(relist, list);
    } else {
      Map<String, String> msgMap = new HashMap<String, String>();
      msgMap.put("stauts", "2");
      msgMap.put("msg", "没有相关数据,");
      relist.add(msgMap);
    }
    return relist;
  }


  @Override
  public List<Map<String, Object>> selectRw(String cbbmid, String tjzt) {
    return d.selectRw(cbbmid, tjzt);
  }


  @Override
  public void importExecl(String jdlx, String ndid, String cbbmid, String fileName,
      HttpServletResponse response) {
    List<DcdbTj> list = d.selectTj(jdlx, ndid, cbbmid);
    List<Object> relist = new ArrayList<Object>();
    relist = handleData(relist, list);
    Map<String, String> msgMap = new HashMap<String, String>();
    if (jdlx.equals("1")) {
      fileName += "第一季度";
    } else if (jdlx.equals("2")) {
      fileName += "第二季度";
    } else if (jdlx.equals("3")) {
      fileName += "第三季度";
    } else if (jdlx.equals("4")) {
      fileName += "第四季度";
    }
    fileName += "工作任务责任分工一览表";
    HSSFWorkbook wk = new HSSFWorkbook();
    HSSFSheet sheet = wk.createSheet();
    sheet.setColumnWidth(1, 5000);
    sheet.setColumnWidth(2, 5000);
    sheet.setColumnWidth(3, 5000);
    sheet.setColumnWidth(4, 5000);
    sheet.setColumnWidth(5, 5000);
    sheet.setColumnWidth(6, 5000);
    sheet.setColumnWidth(7, 5000);
    sheet.setColumnWidth(8, 5000);
    if (jdlx.equals("")) {
      sheet.setColumnWidth(9, 5000);
      sheet.setColumnWidth(10, 5000);
      sheet.setColumnWidth(11, 5000);
      sheet.setColumnWidth(12, 5000);
      sheet.setColumnWidth(13, 5000);
      sheet.setColumnWidth(14, 5000);
    /*  sheet.setColumnWidth(15, 5000);
      sheet.setColumnWidth(16, 5000);
      sheet.setColumnWidth(17, 5000);*/
    }

    HSSFCellStyle style1 = wk.createCellStyle();
    style1.setWrapText(true);// 设置自动换行
    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
    style1.setBottomBorderColor(HSSFColor.BLACK.index);
    Font f1 = wk.createFont();
    f1.setFontName("宋体");
    f1.setFontHeightInPoints((short) 10); // 设置字体大小
    style1.setFont(f1);

    HSSFCellStyle style2 = wk.createCellStyle();
    style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
    HSSFFont headerFont1 = wk.createFont(); // 创建字体样式
    headerFont1.setFontName("黑体"); // 设置字体类型
    headerFont1.setFontHeightInPoints((short) 12);
    style2.setFont(headerFont1);

    CellRangeAddress cra0 = null;
    if (jdlx.equals("")) {
      cra0 = new CellRangeAddress(0, 0, 2, 14);
    } else {
      cra0 = new CellRangeAddress(0, 0, 2, 8);
    }
    sheet.addMergedRegion(cra0);
    HSSFRow row0 = sheet.createRow(0);
    row0.setHeightInPoints(37);
    HSSFCell cell0 = row0.createCell(2);
    cell0.setCellValue(fileName);
    cell0.setCellStyle(style2);
    List<Object> rowsList = new ArrayList<Object>();
    int y = 0;
    for (int i = 0; i < relist.size(); i++) {
      rowsList = (List<Object>) relist.get(i);
      //String bmbm = getBm(rowsList);
      //String phbm = getPhBm(rowsList);
      Map<String, Object> rowMap = (Map) rowsList.get(0);
      HSSFRow row = sheet.createRow(++y);
      row.setHeightInPoints(37);
      HSSFCell cell1 = row.createCell(1);
      cell1.setCellValue("要点内容");
      cell1.setCellStyle(style2);
      CellRangeAddress cra = null;
      if (jdlx.equals("")) {
        cra = new CellRangeAddress(y, y, 2, 14);
      } else {
        cra = new CellRangeAddress(y, y, 2, 8);
      }

      sheet.addMergedRegion(cra);
      HSSFCell cell2 = row.createCell(2);
      cell2.setCellValue((String) rowMap.get("key"));
      cell2.setCellStyle(style1);

      HSSFRow row1 = sheet.createRow(++y);
      row1.setHeightInPoints(37);
      HSSFCell xh = row1.createCell(1);
      xh.setCellValue("序号");
      xh.setCellStyle(style2);
      HSSFCell ld = row1.createCell(2);
      ld.setCellValue("责任领导");
      ld.setCellStyle(style2);
      HSSFCell rw = row1.createCell(3);
      rw.setCellValue("主要任务");
      rw.setCellStyle(style2);
      if (jdlx.equals("1")) {
        HSSFCell ym = row1.createCell(4);
        ym.setCellValue("一季度目标");
        ym.setCellStyle(style2);
        HSSFCell yw = row1.createCell(5);
        yw.setCellValue("一季度完成情况");
        yw.setCellStyle(style2);
        HSSFCell yp = row1.createCell(6);
        yp.setCellValue("一季度评估");
        yp.setCellStyle(style2);
        HSSFCell zr = row1.createCell(7);
        zr.setCellValue("牵头部门");
        zr.setCellStyle(style2);
        HSSFCell ph = row1.createCell(8);
        ph.setCellValue("配合部门");
        ph.setCellStyle(style2);
      } else if (jdlx.equals("2")) {
        HSSFCell bm = row1.createCell(4);
        bm.setCellValue("半年目标");
        bm.setCellStyle(style2);
        HSSFCell bw = row1.createCell(5);
        bw.setCellValue("半年完成情况");
        bw.setCellStyle(style2);
        HSSFCell bp = row1.createCell(6);
        bp.setCellValue("半年评估");
        bp.setCellStyle(style2);
        HSSFCell zr = row1.createCell(7);
        zr.setCellValue("牵头部门");
        zr.setCellStyle(style2);
        HSSFCell ph = row1.createCell(8);
        ph.setCellValue("配合部门");
        ph.setCellStyle(style2);
      } else if (jdlx.equals("3")) {
        HSSFCell sm = row1.createCell(4);
        sm.setCellValue("三季度目标");
        sm.setCellStyle(style2);
        HSSFCell sw = row1.createCell(5);
        sw.setCellValue("三季度完成情况");
        sw.setCellStyle(style2);
        HSSFCell sp = row1.createCell(6);
        sp.setCellValue("三季度评估");
        sp.setCellStyle(style2);
        HSSFCell zr = row1.createCell(7);
        zr.setCellValue("牵头部门");
        zr.setCellStyle(style2);
        HSSFCell ph = row1.createCell(8);
        ph.setCellValue("配合部门");
        ph.setCellStyle(style2);
      } else if (jdlx.equals("4")) {
        HSSFCell ym = row1.createCell(4);
        ym.setCellValue("全年目标");
        ym.setCellStyle(style2);
        HSSFCell yw = row1.createCell(5);
        yw.setCellValue("全年完成情况");
        yw.setCellStyle(style2);
        HSSFCell yp = row1.createCell(6);
        yp.setCellValue("全年评估");
        yp.setCellStyle(style2);
        HSSFCell zr = row1.createCell(7);
        zr.setCellValue("牵头部门");
        zr.setCellStyle(style2);
        HSSFCell ph = row1.createCell(8);
        ph.setCellValue("配合部门");
        ph.setCellStyle(style2);
      } else {
      /*  HSSFCell ym = row1.createCell(4);
        ym.setCellValue("一季度目标");
        ym.setCellStyle(style2);
        HSSFCell yw = row1.createCell(5);
        yw.setCellValue("一季度完成情况");
        yw.setCellStyle(style2);
        HSSFCell yp = row1.createCell(6);
        yp.setCellValue("一季度评估");
        yp.setCellStyle(style2);*/
        HSSFCell bm = row1.createCell(4);
        bm.setCellValue("半年目标");
        bm.setCellStyle(style2);
        HSSFCell bw = row1.createCell(5);
        bw.setCellValue("半年完成情况");
        bw.setCellStyle(style2);
        HSSFCell bp = row1.createCell(6);
        bp.setCellValue("半年评估");
        bp.setCellStyle(style2);
        HSSFCell sm = row1.createCell(7);
        sm.setCellValue("三季度目标");
        sm.setCellStyle(style2);
        HSSFCell sw = row1.createCell(8);
        sw.setCellValue("三季度完成情况");
        sw.setCellStyle(style2);
        HSSFCell sp = row1.createCell(9);
        sp.setCellValue("三季度评估");
        sp.setCellStyle(style2);
        HSSFCell ynm = row1.createCell(10);
        ynm.setCellValue("全年目标");
        ynm.setCellStyle(style2);
        HSSFCell ynw = row1.createCell(11);
        ynw.setCellValue("全年完成情况");
        ynw.setCellStyle(style2);
        HSSFCell ynp = row1.createCell(12);
        ynp.setCellValue("全年评估");
        ynp.setCellStyle(style2);
        HSSFCell zr = row1.createCell(13);
        zr.setCellValue("牵头部门");
        zr.setCellStyle(style2);
        HSSFCell ph = row1.createCell(14);
        ph.setCellValue("配合部门");
        ph.setCellStyle(style2);
      }
      for (int x = 1; x < rowsList.size(); x++) {
        DcdbTj dcdbTj = (DcdbTj) rowsList.get(x);
        /*if (!"".equals(cbbmid)) {
          if (dcdbTj.getYdnrid() != cbbmid) {
            continue;
          }
        }*/
        HSSFRow rowy = sheet.createRow(++y);
        rowy.setHeightInPoints(37);

        HSSFCell xh1 = rowy.createCell(1);
        xh1.setCellValue(x + "");
        xh1.setCellStyle(style1);

        HSSFCell xh2 = rowy.createCell(2);
        xh2.setCellValue(dcdbTj.getZrld());
        xh2.setCellStyle(style1);

        HSSFCell xh3 = rowy.createCell(3);
        xh3.setCellValue(dcdbTj.getZyrwmc());
        xh3.setCellStyle(style1);

        if (jdlx.equals("1")) {
          if (dcdbTj.getYjdmb() != null) {
            HSSFCell xh7 = rowy.createCell(4);
            xh7.setCellValue(dcdbTj.getYjdmb());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(4);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getYjdwcqk() != null) {
            HSSFCell xh8 = rowy.createCell(5);
            xh8.setCellValue(dcdbTj.getYjdwcqk());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(5);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }
          if (dcdbTj.getYjdpg() != null) {
            HSSFCell xh9 = rowy.createCell(6);
            xh9.setCellValue(dcdbTj.getYjdpg());
            xh9.setCellStyle(style1);
          } else {
            HSSFCell xh9 = rowy.createCell(6);
            xh9.setCellStyle(style1);
            xh9.setCellValue("---");
          }
          if (dcdbTj.getSsbm() != null) {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue(dcdbTj.getSsbm());
          } else {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue("----");
          }
          if (dcdbTj.getPhbm() != null) {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue(dcdbTj.getPhbm());
          } else {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue("----");
          }
        } else if (jdlx.equals("2")) {
          if (dcdbTj.getBnmb() != null) {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellValue(dcdbTj.getBnmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getBnwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellValue(dcdbTj.getBnwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getBnpg() != null) {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellValue(dcdbTj.getBnpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(7);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }
          if (dcdbTj.getSsbm() != null) {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue(dcdbTj.getSsbm());
          } else {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue("----");
          }
          if (dcdbTj.getPhbm() != null) {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue(dcdbTj.getPhbm());
          } else {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue("----");
          }

        } else if (jdlx.equals("3")) {
          if (dcdbTj.getSjdmb() != null) {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellValue(dcdbTj.getSjdmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getBnwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellValue(dcdbTj.getBnwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getBnpg() != null) {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellValue(dcdbTj.getBnpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }
          if (dcdbTj.getSsbm() != null) {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue(dcdbTj.getSsbm());
          } else {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue("----");
          }
          if (dcdbTj.getPhbm() != null) {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue(dcdbTj.getPhbm());
          } else {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue("----");
          }
        } else if (jdlx.equals("4")) {
          if (dcdbTj.getQnmb() != null) {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellValue(dcdbTj.getQnmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getQnwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellValue(dcdbTj.getQnwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getQnpg() != null) {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellValue(dcdbTj.getQnpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }
          if (dcdbTj.getSsbm() != null) {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue(dcdbTj.getSsbm());
          } else {
            HSSFCell xh10 = rowy.createCell(7);
            xh10.setCellStyle(style1);
            xh10.setCellValue("----");
          }
          if (dcdbTj.getPhbm() != null) {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue(dcdbTj.getPhbm());
          } else {
            HSSFCell xh11 = rowy.createCell(8);
            xh11.setCellStyle(style1);
            xh11.setCellValue("----");
          }
        } else {
         /* if (dcdbTj.getYjdmb() != null) {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellValue(dcdbTj.getYjdmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getYjdwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellValue(dcdbTj.getYjdwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getYjdpg() != null) {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellValue(dcdbTj.getYjdpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }*/
          if (dcdbTj.getBnmb() != null) {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellValue(dcdbTj.getBnmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(4);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getBnwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellValue(dcdbTj.getBnwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(5);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getBnpg() != null) {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellValue(dcdbTj.getBnpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(6);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }
          if (dcdbTj.getSjdmb() != null) {
            HSSFCell xh6 = rowy.createCell(7);
            xh6.setCellValue(dcdbTj.getSjdmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(7);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getBnwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(8);
            xh7.setCellValue(dcdbTj.getBnwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(8);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getBnpg() != null) {
            HSSFCell xh8 = rowy.createCell(9);
            xh8.setCellValue(dcdbTj.getBnpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(9);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }
          if (dcdbTj.getQnmb() != null) {
            HSSFCell xh6 = rowy.createCell(10);
            xh6.setCellValue(dcdbTj.getQnmb());
            xh6.setCellStyle(style1);
          } else {
            HSSFCell xh6 = rowy.createCell(10);
            xh6.setCellStyle(style1);
            xh6.setCellValue("---");
          }
          if (dcdbTj.getQnwcqk() != null) {
            HSSFCell xh7 = rowy.createCell(11);
            xh7.setCellValue(dcdbTj.getQnwcqk());
            xh7.setCellStyle(style1);
          } else {
            HSSFCell xh7 = rowy.createCell(11);
            xh7.setCellStyle(style1);
            xh7.setCellValue("---");
          }
          if (dcdbTj.getQnpg() != null) {
            HSSFCell xh8 = rowy.createCell(12);
            xh8.setCellValue(dcdbTj.getQnpg());
            xh8.setCellStyle(style1);
          } else {
            HSSFCell xh8 = rowy.createCell(12);
            xh8.setCellStyle(style1);
            xh8.setCellValue("---");
          }

          if (dcdbTj.getSsbm() != null) {
            HSSFCell xh10 = rowy.createCell(13);
            xh10.setCellStyle(style1);
            xh10.setCellValue(dcdbTj.getSsbm());
          } else {
            HSSFCell xh10 = rowy.createCell(13);
            xh10.setCellStyle(style1);
            xh10.setCellValue("----");
          }
          if (dcdbTj.getPhbm() != null) {
            HSSFCell xh11 = rowy.createCell(14);
            xh11.setCellStyle(style1);
            xh11.setCellValue(dcdbTj.getPhbm());
          } else {
            HSSFCell xh11 = rowy.createCell(14);
            xh11.setCellStyle(style1);
            xh11.setCellValue("----");
          }
        }
      }
    }
    try {
      String download_path = ConfigProperties.getProperties("download_Path");
      File file = new File(download_path);
      if (!file.exists()) {
        file.mkdirs();
      }
      String filepath = download_path + File.separator + fileName + ".xls";
      FileOutputStream fout = new FileOutputStream(filepath);
      wk.write(fout);
      response.setHeader("content-disposition",
          "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      response.setCharacterEncoding("UTF-8");
      // 放到缓冲流里面
      BufferedInputStream bins = new BufferedInputStream(new FileInputStream(filepath));
      // 读取目标文件，通过response将目标文件写到客户端
      BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream());
      // 写文件
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      // 开始向网络传输文件流
      while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
        bouts.write(buffer, 0, bytesRead);
      }
      bouts.flush();
      bins.close();
      bouts.close();
      File tfile = new File(filepath);
      tfile.delete();

    } catch (IOException e) {
      e.printStackTrace();
      msgMap.put("status", "2");

    }
  }

  @Override
  public void importExeclQnmb(String jdlx, String ndid, String cbbmid, String fileName,
      HttpServletResponse response) {
    List<DcdbTj> list = d.selectTjQnmb(ndid);
    List<Object> relist = new ArrayList<Object>();
    relist = handleData(relist, list);
    Map<String, String> msgMap = new HashMap<String, String>();
    fileName += "工作任务责任分工一览表";
    HSSFWorkbook wk = new HSSFWorkbook();
    HSSFSheet sheet = wk.createSheet();
    sheet.setColumnWidth(1, 5000);
    sheet.setColumnWidth(2, 5000);
    sheet.setColumnWidth(3, 5000);
    sheet.setColumnWidth(4, 5000);
    sheet.setColumnWidth(5, 5000);
    sheet.setColumnWidth(6, 5000);
    sheet.setColumnWidth(7, 5000);
    sheet.setColumnWidth(8, 5000);
    sheet.setColumnWidth(9, 5000);

    HSSFCellStyle style1 = wk.createCellStyle();
    style1.setWrapText(true);// 设置自动换行
    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
    style1.setBottomBorderColor(HSSFColor.BLACK.index);
    Font f1 = wk.createFont();
    f1.setFontName("宋体");
    f1.setFontHeightInPoints((short) 10); // 设置字体大小
    style1.setFont(f1);

    HSSFCellStyle style2 = wk.createCellStyle();
    style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
    HSSFFont headerFont1 = wk.createFont(); // 创建字体样式
    headerFont1.setFontName("黑体"); // 设置字体类型
    headerFont1.setFontHeightInPoints((short) 12);
    style2.setFont(headerFont1);

    CellRangeAddress cra0 = new CellRangeAddress(0, 0, 2, 9);

    sheet.addMergedRegion(cra0);
    HSSFRow row0 = sheet.createRow(0);
    row0.setHeightInPoints(37);
    HSSFCell cell0 = row0.createCell(2);
    cell0.setCellValue(fileName);
    cell0.setCellStyle(style2);
    List<Object> rowsList = new ArrayList<Object>();
    int y = 0;
    for (int i = 0; i < relist.size(); i++) {
      rowsList = (List<Object>) relist.get(i);
      Map<String, Object> rowMap = (Map) rowsList.get(0);
      HSSFRow row = sheet.createRow(++y);
      row.setHeightInPoints(37);
      HSSFCell cell1 = row.createCell(1);
      cell1.setCellValue("要点内容");
      cell1.setCellStyle(style2);
      CellRangeAddress cra = new CellRangeAddress(y, y, 2, 9);

      sheet.addMergedRegion(cra);
      HSSFCell cell2 = row.createCell(2);
      cell2.setCellValue((String) rowMap.get("key"));
      cell2.setCellStyle(style1);

      HSSFRow row1 = sheet.createRow(++y);
      row1.setHeightInPoints(37);
      HSSFCell xh = row1.createCell(1);
      xh.setCellValue("序号");
      xh.setCellStyle(style2);
      HSSFCell ld = row1.createCell(2);
      ld.setCellValue("责任领导");
      ld.setCellStyle(style2);
      HSSFCell rw = row1.createCell(3);
      rw.setCellValue("主要任务");
      rw.setCellStyle(style2);
      HSSFCell ym = row1.createCell(4);
      ym.setCellValue("一季度目标");
      ym.setCellStyle(style2);
      HSSFCell bm = row1.createCell(5);
      bm.setCellValue("半年目标");
      bm.setCellStyle(style2);

      HSSFCell sm = row1.createCell(6);
      sm.setCellValue("三季度目标");
      sm.setCellStyle(style2);

      HSSFCell ynm = row1.createCell(7);
      ynm.setCellValue("全年目标");
      ynm.setCellStyle(style2);

      HSSFCell zr = row1.createCell(8);
      zr.setCellValue("牵头部门");
      zr.setCellStyle(style2);
      HSSFCell ph = row1.createCell(9);
      ph.setCellValue("配合部门");
      ph.setCellStyle(style2);
      for (int x = 1; x < rowsList.size(); x++) {
        DcdbTj dcdbTj = (DcdbTj) rowsList.get(x);
        /*if (!"".equals(cbbmid)) {
          if (dcdbTj.getYdnrid() != cbbmid) {
            continue;
          }
        }*/
        HSSFRow rowy = sheet.createRow(++y);
        rowy.setHeightInPoints(37);

        HSSFCell xh1 = rowy.createCell(1);
        xh1.setCellValue(x + "");
        xh1.setCellStyle(style1);

        HSSFCell xh2 = rowy.createCell(2);
        xh2.setCellValue(dcdbTj.getZrld());
        xh2.setCellStyle(style1);

        HSSFCell xh3 = rowy.createCell(3);
        xh3.setCellValue(dcdbTj.getZyrwmc());
        xh3.setCellStyle(style1);

        if (dcdbTj.getYjdmb() != null) {
          HSSFCell xh7 = rowy.createCell(4);
          xh7.setCellValue(dcdbTj.getYjdmb());
          xh7.setCellStyle(style1);
        } else {
          HSSFCell xh7 = rowy.createCell(4);
          xh7.setCellStyle(style1);
          xh7.setCellValue("---");
        }
        //查看
        if (dcdbTj.getBnmb() != null) {
          HSSFCell xh6 = rowy.createCell(5);
          xh6.setCellValue(dcdbTj.getBnmb());
          xh6.setCellStyle(style1);
        } else {
          HSSFCell xh6 = rowy.createCell(5);
          xh6.setCellStyle(style1);
          xh6.setCellValue("---");
        }

        if (dcdbTj.getSjdmb() != null) {
          HSSFCell xh6 = rowy.createCell(6);
          xh6.setCellValue(dcdbTj.getSjdmb());
          xh6.setCellStyle(style1);
        } else {
          HSSFCell xh6 = rowy.createCell(6);
          xh6.setCellStyle(style1);
          xh6.setCellValue("---");
        }

        if (dcdbTj.getQnmb() != null) {
          HSSFCell xh6 = rowy.createCell(7);
          xh6.setCellValue(dcdbTj.getQnmb());
          xh6.setCellStyle(style1);
        } else {
          HSSFCell xh6 = rowy.createCell(7);
          xh6.setCellStyle(style1);
          xh6.setCellValue("---");
        }

        if (dcdbTj.getSsbm() != null) {
          HSSFCell xh10 = rowy.createCell(8);
          xh10.setCellStyle(style1);
          xh10.setCellValue(dcdbTj.getSsbm());
        } else {
          HSSFCell xh10 = rowy.createCell(8);
          xh10.setCellStyle(style1);
          xh10.setCellValue("----");
        }
        if (dcdbTj.getPhbm() != null) {
          HSSFCell xh11 = rowy.createCell(9);
          xh11.setCellStyle(style1);
          xh11.setCellValue(dcdbTj.getPhbm());
        } else {
          HSSFCell xh11 = rowy.createCell(9);
          xh11.setCellStyle(style1);
          xh11.setCellValue("----");
        }
      }
    }
    try {
      String download_path = ConfigProperties.getProperties("download_Path");
      File file = new File(download_path);
      if (!file.exists()) {
        file.mkdirs();
      }
      String filepath = download_path + File.separator + fileName + ".xls";
      FileOutputStream fout = new FileOutputStream(filepath);
      wk.write(fout);
      response.setHeader("content-disposition",
          "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      response.setCharacterEncoding("UTF-8");
      // 放到缓冲流里面
      BufferedInputStream bins = new BufferedInputStream(new FileInputStream(filepath));
      // 读取目标文件，通过response将目标文件写到客户端
      BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream());
      // 写文件
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      // 开始向网络传输文件流
      while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
        bouts.write(buffer, 0, bytesRead);
      }
      bouts.flush();
      bins.close();
      bouts.close();
      File tfile = new File(filepath);
      tfile.delete();

    } catch (IOException e) {
      e.printStackTrace();
      msgMap.put("status", "2");

    }
  }

  private String getPhBm(List<Object> rowsList) {
    String bm = "";
    for (int i = 1; i < rowsList.size(); i++) {
      DcdbTj dcdbTj = (DcdbTj) rowsList.get(i);
      if (("ph".equals((String) dcdbTj.getBmlx()))) {
        bm += dcdbTj.getSsbm();
      }
    }
    return bm;
  }

  private String getBm(List<Object> rowsList) {
    String bm = "";
    for (int i = 1; i < rowsList.size(); i++) {
      DcdbTj dcdbTj = (DcdbTj) rowsList.get(i);
      if (("qt".equals((String) dcdbTj.getBmlx()))) {
        bm += dcdbTj.getSsbm();
      }
    }
       /* bm+="";
        for(int i=1;i<rowsList.size();i++){
            DcdbTj dcdbTj=(DcdbTj)rowsList.get(i);
            if(("zz".equals((String) dcdbTj.getBmlx()))) {
                bm+=dcdbTj.getSsbm();
            }
        }*/
    return bm;
  }


  private List<Object> handleData(List<Object> relist, List<DcdbTj> list) {
    Map<String, Object> ydMap = new LinkedHashMap<String, Object>();
    for (int i = 0; i < list.size(); i++) {
      ydMap.put(list.get(i).getYdnrid(), list.get(i).getYdnrmc());
    }

    for (String key : ydMap.keySet()) {
      List<Object> rowsList = new ArrayList<Object>();
      Map<String, Object> rowsYdMap = new HashMap<String, Object>();
      rowsYdMap.put("key", ydMap.get(key));
      rowsList.add(rowsYdMap);
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getYdnrid().equals(key)) {
          rowsList.add(list.get(i));
        }
      }
      relist.add(rowsList);
    }
    return relist;
  }

  @Override
  public String updateTjzt(String tjzt, String id) {
    int i = d.updateTjzt(tjzt, id);
    return i + "";
  }

  @Override
  public Map<String, Object> bmtj(String zt, String lclx,String startTime,String endTime) {
    Map<String, Object> map = new HashMap<String, Object>();
    if ("0".equals(zt)){
      zt = "'1','3'";
    }else {
      zt = "'"+zt+"'";
    }
    List<DcdbCounTj> list = d.getBmtj(zt, lclx,startTime,endTime);
    return msgData(map, list);
  }

  @Override
  public void importBm(String zt, String lx, String lclx,String nf,String checkedtype,HttpServletRequest httpServletRequest,
      HttpServletResponse response,String startTime,String endTime) {
    List<DcdbCounTj> list = new ArrayList<DcdbCounTj>();
    if (lx.equals("bmtj")) {
      list = d.getBmtj(zt, lclx,startTime,endTime);
      String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
      String templateFile = path + ConfigProperties.getProperties(lx);
      Map<String,Object> beas = new HashMap<>();
      beas.put("datalist",list);
      JxlsExcelUtils.exportExcel(beas, templateFile, path, response);
    } else if (lx.equals("lxtj")) {
     // list = d.getLxtj(zt, lclx,nf);
      Map map = new HashMap();
      map.put("zt",zt);
      map.put("lclx",lclx);
      map.put("startDate",startTime);
      map.put("endDate",endTime);
      map.put("checkedtype",checkedtype);
      List<Map<String, Object>> maps = d.newqueryLxtj(map);
      if("bllx".equals(checkedtype)){
        lx = "lxtj_bllx";
      }else if("pslx".equals(checkedtype)){
        lx = "lxtj_pslx";
      }else if("issjysj".equals(checkedtype)){
        lx = "lxtj_issjysj";
      }
      String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
      String templateFile = path + ConfigProperties.getProperties(lx);
        Map<String,Object> beas = new HashMap<>();
        beas.put("datalist",maps);
      JxlsExcelUtils.exportExcel(beas, templateFile, path, response);
    } else if (lx.equals("zttj")) {
      list = d.getZttj(lclx,nf,startTime,endTime);
      String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
      String templateFile = path + ConfigProperties.getProperties(lx);
        Map<String,Object> beas = new HashMap<>();
        beas.put("datalist",list);
      JxlsExcelUtils.exportExcel(beas, templateFile, path, response);
    }

    /*String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
    String templateFile = path + ConfigProperties.getProperties(lx);
    JxlsExcelUtils.exportExcel(list, templateFile, path, response);*/
  }

  @Override
  public void importExeclDetail(String lxdetail_zt, String lxdetail_lclx, String lxdetail_startDate, String lxdetail_endDate, String lxdetail_checkedtype,String lxdetail_lxtypename, String tjlx,HttpServletRequest httpServletRequest, HttpServletResponse response) {
    List<DcdbDjlbDbParam> list = new ArrayList<DcdbDjlbDbParam>();
    StringBuffer buff = new StringBuffer();
    Map<String,Object> beas = new HashMap<>();
    buff.append(lxdetail_startDate).append("-").append(lxdetail_endDate);
    if("lxtj".equals(tjlx)){
      Map map = new HashMap();
      map.put("zt",lxdetail_zt);
      map.put("lclx",lxdetail_lclx);
      map.put("startDate",lxdetail_startDate);
      map.put("endDate",lxdetail_endDate);
      map.put("checkedtype",lxdetail_checkedtype);
      if("合计".equals(lxdetail_lxtypename)){
          map.put("lxtypename","");
      }else if("是".equals(lxdetail_lxtypename)){
          map.put("lxtypename","yes");
      }else if("否".equals(lxdetail_lxtypename)){
          map.put("lxtypename","no");
      } else{
          map.put("lxtypename",lxdetail_lxtypename);
      }
      list = d.queryDetaillx(map);
      for (DcdbDjlbDbParam d : list){
        StringBuffer sb = new StringBuffer();
        String id = d.getId();
        DcdbSpyj dcdbSpyj = new DcdbSpyj();
        if(id.contains(",")){
          String[] idArr = id.split(",");
          for (int i=0;i<idArr.length;i++){
            DcdbDjlbDb dcdbDjlbDb = flowDao.selectByPrimaryKey(idArr[i]);
            dcdbSpyj.setId(idArr[i]);
            dcdbSpyj.setSplx("blqk");
            List<DcdbSpyj> dcdbSpyjs = flowDao.selectSpyj(dcdbSpyj);
            for (DcdbSpyj s : dcdbSpyjs){
              if (StringUtils.isBlank(s.getSpbz())){
                sb.append("");
              }else {
                sb.append(dcdbDjlbDb.getCbbmmc()).append(":").append("  ");
                sb.append(s.getSpbz()).append("  ").append("\n");
              }

            }
          }
        }else {
          dcdbSpyj.setId(id);
          dcdbSpyj.setSplx("blqk");
          List<DcdbSpyj> dcdbSpyjs = flowDao.selectSpyj(dcdbSpyj);
          for (DcdbSpyj s : dcdbSpyjs){
            if (StringUtils.isBlank(s.getSpbz())){
              sb.append("");
            }else {
              sb.append(s.getSpbz());
            }
          }
          sb.append(":");
        }
        if (sb.length()>=1){
          d.setBlqk(sb.substring(0,sb.length()-1));
        }else {
          d.setBlqk(sb.toString());
        }

      }

      switch (lxdetail_checkedtype){
        case "bllx":
          buff.append("合计".equals(lxdetail_lxtypename)?"办理类型":"办理类型为").append(lxdetail_lxtypename).append("合计".equals(lxdetail_lxtypename)?"批示件":"的批示件").append("(").append(list.size()).append("件)");
          break;
        case "pslx":
          buff.append("合计".equals(lxdetail_lxtypename)?"批示类型":"批示类型为").append(lxdetail_lxtypename).append("合计".equals(lxdetail_lxtypename)?"批示件":"的批示件").append("(").append(list.size()).append("件)");
          break;
        case "issjysj":
          buff.append("合计".equals(lxdetail_lxtypename)?"批示件合计":"是".equals(lxdetail_lxtypename)?"省级以上的批示件":"非省级以上的批示件").append("(").append(list.size()).append("件)");
          break;
      }
      beas.put("datalist",list);
    }else if("zttj".equals(tjlx)){
      list = d.queryDetailzt(lxdetail_zt, lxdetail_lclx, "", lxdetail_startDate, lxdetail_endDate);
      for (DcdbDjlbDbParam d : list) {
        StringBuffer sb = new StringBuffer();
        String id = d.getId();
        DcdbSpyj dcdbSpyj = new DcdbSpyj();
        if (id.contains(",")) {
          String[] idArr = id.split(",");
          for (int i = 0; i < idArr.length; i++) {
            DcdbDjlbDb dcdbDjlbDb = flowDao.selectByPrimaryKey(idArr[i]);
            dcdbSpyj.setId(idArr[i]);
            dcdbSpyj.setSplx("blqk");
            List<DcdbSpyj> dcdbSpyjs = flowDao.selectSpyj(dcdbSpyj);
            for (DcdbSpyj s : dcdbSpyjs) {
              if (StringUtils.isBlank(s.getSpbz())) {
                sb.append("");
              } else {
                sb.append(dcdbDjlbDb.getCbbmmc()).append(":").append("  ");
                sb.append(s.getSpbz()).append("  ").append("\n");
              }

            }
          }
        } else {
          dcdbSpyj.setId(id);
          dcdbSpyj.setSplx("blqk");
          List<DcdbSpyj> dcdbSpyjs = flowDao.selectSpyj(dcdbSpyj);
          for (DcdbSpyj s : dcdbSpyjs) {
            if (StringUtils.isBlank(s.getSpbz())) {
              sb.append("");
            } else {
              sb.append(s.getSpbz());
            }
          }
          sb.append(":");
        }
        if (sb.length() >= 1) {
          d.setBlqk(sb.substring(0, sb.length() - 1));
        } else {
          d.setBlqk(sb.toString());
        }

      }
      beas.put("datalist",list);
      switch (lxdetail_zt) {
        case "1":
          buff.append("未办结批示件").append("(").append(list.size()).append("件)");
          break;
        case "3":
          buff.append("已办结批示件").append("(").append(list.size()).append("件)");
          break;
        case "00":
          buff.append("批示件合计").append("(").append(list.size()).append("件)");
          break;
      }
    }else if("bmtj".equals(tjlx)){
      List<DcdbDjlbDbParam> bmlist = d.queryDetailbm(lxdetail_zt, lxdetail_lclx, lxdetail_startDate, lxdetail_endDate,lxdetail_checkedtype);
      beas.put("datalist",bmlist);
      if("".equals(lxdetail_lxtypename) || lxdetail_lxtypename == null){
        buff.append("批示件合计").append("(").append(list.size()).append("件)");
      }else{
        buff.append("批示件部门为：").append(lxdetail_lxtypename).append("(").append(bmlist.size()).append("件)");
      }
    }
    String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
    String templateFile ="";
    if ("dzjb".equals(lxdetail_lclx)){
      templateFile = path + ConfigProperties.getProperties("dzjb_detail");
    }else if ("jbsx".equals(lxdetail_lclx)){
        templateFile = path + ConfigProperties.getProperties("dzjb_detail");
    }
    else {
      templateFile = path + ConfigProperties.getProperties("detail");
    }
      beas.put("title",buff.toString());
    JxlsExcelUtils.exportExcel(beas, templateFile, path, response);
  }

  @Override
  public Map<String, Object> lxtj(String zt, String lclx,String nf) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<DcdbCounTj> list = d.getLxtj(zt, lclx,nf);
    return msgData(map, list);
  }

  @Override
  public Map<String, Object> newlxtj(Map map) {
    Map<String, Object> resultmap = new HashMap<String, Object>();
    List<Map<String,Object>> list = d.newqueryLxtj(map);
    return msgData(resultmap, list);
  }

  @Override
  public Map<String, Object> zttj(String zt, String lclx,String nf,String startTime,String endTime) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<DcdbCounTj> list = d.getZttj(lclx,nf,startTime,endTime);
    return msgData(map, list);
  }

  @Override
  public List<SjjcsxRt> sjjcsxRt(String djid) {
    List<SjjcsxRt> list = d.sjjcsxRt(djid);
    handlerData(list);
    return list;
  }

  @Override
  public List<Map<String, String>> sjjcsxDj() {
    return d.sjjcsxDj();
  }

  @Override
  public Map<String, Object> addTzgs(DcdbNdrwTzgs tzgs) {
    Map<String, Object> map = new HashMap<>();
    try {
      d.deleteTzgs(tzgs);
      d.insertTzgs(tzgs);
      map.put("code", 1);
      map.put("msg", "公示成功");
      return map;
    } catch (Exception e) {
      map.put("code", 2);
      map.put("msg", "公示失败，" + e.getMessage());
      return map;
    }

  }

  @Override
  public Map<String, Object> queryTzgsList(DcdbNdrwTzgs tzgs) {
    Map<String, Object> map = new HashMap<>();
    try {
      if ("ndrw".equals(tzgs.getFlag())) {
        map.put("data", d.queryTzgsNdrwList(tzgs));
      } else {
        map.put("data", d.queryTzgsList(tzgs));
      }
      map.put("code", 1);
      map.put("msg", "查询成功");
      return map;
    } catch (Exception e) {
      map.put("code", 2);
      map.put("msg", "查询失败，" + e.getMessage());
      return map;
    }

  }

  @Override
  public List<DcdbDjlbDbParam> queryDetaillx(Map map) {
    List<DcdbDjlbDbParam> list =  d.queryDetaillx(map);
    return list;
  }

  @Override
  public List<DcdbDjlbDbParam> queryDetailzt(String zt, String lclx, String nf, String startTime, String endTime) {
    List<DcdbDjlbDbParam> list =  d.queryDetailzt(zt,  lclx,  nf,  startTime,  endTime);
    return list;
  }

  @Override
  public List<DcdbDjlbDbParam> queryDetailbm(String zt, String lclx, String startTime, String endTime, String cbbmid) {
    List<DcdbDjlbDbParam> list =  d.queryDetailbm(zt,lclx,startTime,endTime,cbbmid);
    return list;
  }


  private Map<String, Object> msgData(Map map, List list) {
    if (list.size() > 0) {
      map.put("code", 0);
      map.put("msg", "");
      map.put("count", list.size());
    } else {
      map.put("code", 1);
      map.put("msg", "暂无数据");
      map.put("count", "0");
    }
    map.put("data", list);
    return map;
  }

  private void handlerData(List<SjjcsxRt> list) {
    for (SjjcsxRt sjjcsxRt : list) {
      if (StringUtils.isNotBlank(sjjcsxRt.getDczq())) {
        switch (sjjcsxRt.getDczq()) {
          case "0":
            sjjcsxRt.setDczq("每周");
            break;
          case "1":
            sjjcsxRt.setDczq("每月");
            break;
          case "2":
            sjjcsxRt.setDczq("每季度");
            break;
          case "3":
            sjjcsxRt.setDczq("每半年");
            break;
          case "4":
            sjjcsxRt.setDczq("定制天数");
            break;
        }
      }

      StringBuffer qtbmMc = new StringBuffer();
      StringBuffer zybmMc = new StringBuffer();
      //牵头部门拼接为部门1，部门2
      if (StringUtils.isNotBlank(sjjcsxRt.getQtbmid())) {
        String[] qtbmidArr = sjjcsxRt.getQtbmid().split(",");//1@1@1,2@2@2
        //将部门名称拼接为部门1，部门2
        for (int i = 0; i < qtbmidArr.length; i++) {
          qtbmMc.append(qtbmidArr[i].split("@")[1]).append(",");
        }
        //去掉最后一个逗号
        String qtbm = qtbmMc.substring(0, qtbmMc.length() - 1);
        sjjcsxRt.setQtbmid(qtbm);
      }
      //牵头部门拼接为部门1，部门2
      if (StringUtils.isNotBlank(sjjcsxRt.getZzbmid())) {
        String[] zzbmidArr = sjjcsxRt.getZzbmid().split(",");//1@1@1,2@2@2
        //将部门名称拼接为部门1，部门2
        for (int i = 0; i < zzbmidArr.length; i++) {
          zybmMc.append(zzbmidArr[i].split("@")[1]).append(",");
        }
        //去掉最后一个逗号
        String zzbm = zybmMc.substring(0, zybmMc.length() - 1);
        sjjcsxRt.setZzbmid(zzbm);
      }
    }
  }
  @Override
  public List<Map<String, Object>> doSelectNdrw() {
	return d.selectNdrw();
  }
}
