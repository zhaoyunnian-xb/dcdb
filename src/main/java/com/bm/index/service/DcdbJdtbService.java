package com.bm.index.service;

import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbProjectFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @InterfaceName DcdbJdtbService
 * @Description TODO
 * @Author daipx
 * @Date 2019/3/5 11:41
 * @Version 1.0
 **/
public interface DcdbJdtbService {
    List<DcdbBmmbnr> selectJdtb(DcdbBmmbnr record);
    //查询已办详情页-任务列表
    List<DcdbBmmbnr> queryZyrwListYb(DcdbBmmbnr record);

    int updateJdwcqk(DcdbBmmbnr record);

    int updateKhpzById(DcdbBmmbnr record);

    int insertKhpz(DcdbBmmbnr record);

    List<DcdbBmmbnr> selectKhpzById(String yjid,String ssbm);

    //删除附件表
    void deleteDcdbFile(DcdbProjectFile record);
    //文件上传
    Boolean uploadFiles(List<MultipartFile> files, DcdbProjectFile dcdbProjectFile, HttpServletRequest request);
    //插入已办表
    int insertNdrwYb(List<DcdbBmmbnr> bmnrlist);

  List<DcdbBmmbnr> queryZyrwListTbjdmx(DcdbBmmbnr dcdbBmmbnr);
}
