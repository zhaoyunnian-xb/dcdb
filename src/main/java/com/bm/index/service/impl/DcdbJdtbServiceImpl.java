package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.model.DcdbBmmbnr;
import com.bm.index.model.DcdbProjectFile;
import com.bm.index.service.DcdbJdtbService;
import com.bm.index.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DcdbJdtbServiceImpl
 * @Description TODO
 * @Author daipx
 * @Date 2019/3/5 11:42
 * @Version 1.0
 **/
@Service
public class DcdbJdtbServiceImpl implements DcdbJdtbService {
    @Autowired
    DcdbBmmbnrDao dcdbBmmbnrDao;

    private static Log logger = LogFactory.getLog(DcdbJdtbServiceImpl.class);

    @Override
    public List<DcdbBmmbnr> selectJdtb(DcdbBmmbnr dcdbBmmbnr) {
        String status = dcdbBmmbnr.getStatus();
        String jdlx = status.substring(0, 1); //季度类型
        //根据状态码，判断是第几季度，动态字段查询
        String col = "YJDMB AS YJDMB , YJDWCQK AS YJDWCQK , YJDPG AS YJDPG";
        switch (jdlx) {
            case "1":
                col = "YJDMB AS YJDMB , YJDWCQK AS YJDWCQK , YJDPG AS YJDPG";
                break;
            case "2":
                col = "BNMB AS YJDMB , BNWCQK AS YJDWCQK , BNPG AS YJDPG";
                break;
            case "3":
                col = "SJDMB AS YJDMB , SJDWCQK AS YJDWCQK , SJDPG AS YJDPG";
                break;
            case "4":
                col = "QNMB AS YJDMB , QNWCQK AS YJDWCQK , QNPG AS YJDPG";
                break;
        }
        dcdbBmmbnr.setJdlx(jdlx);
        dcdbBmmbnr.setColnum(col);
        dcdbBmmbnr.setIsBgs("no");
        /*//如果是办公室节点
        String nodeId = status.substring(2); //流转节点
        if ( "7".equals(nodeId) || "8".equals(nodeId) ) {
            dcdbBmmbnr.setIsBgs("yes");
        }
*/
        return dcdbBmmbnrDao.selectJdtb(dcdbBmmbnr);
    }

    @Override
    public List<DcdbBmmbnr> queryZyrwListYb(DcdbBmmbnr dcdbBmmbnr) {
        String status = dcdbBmmbnr.getStatus();
        String jdlx = status.substring(0, 1); //季度类型
        //根据状态码，判断是第几季度，动态字段查询
        String col = "c.YJDMB AS JDMB , c.YJDWCQK AS YJDWCQK , c.YJDPG AS YJDPG";
        switch (jdlx) {
            case "1":
                col = "c.YJDMB AS JDMB , c.YJDWCQK AS YJDWCQK , c.YJDPG AS YJDPG";
                break;
            case "2":
                col = "c.BNMB AS JDMB , c.BNWCQK AS YJDWCQK , c.BNPG AS YJDPG";
                break;
            case "3":
                col = "c.SJDMB AS JDMB , c.SJDWCQK AS YJDWCQK , c.SJDPG AS YJDPG";
                break;
            case "4":
                col = "c.QNMB AS JDMB , c.QNWCQK AS YJDWCQK , c.QNPG AS YJDPG";
                break;
        }
        dcdbBmmbnr.setJdlx(jdlx);
        dcdbBmmbnr.setColnum(col);

        return dcdbBmmbnrDao.selectZyrwListYb(dcdbBmmbnr);
    }
    @Override
    public List<DcdbBmmbnr> queryZyrwListTbjdmx(DcdbBmmbnr dcdbBmmbnr) {
        String status = dcdbBmmbnr.getStatus();
        String jdlx = status.substring(0, 1); //季度类型
        //根据状态码，判断是第几季度，动态字段查询
        String col = "c.YJDMB AS JDMB , c.YJDWCQK AS YJDWCQK , c.YJDPG AS YJDPG";
        switch (jdlx) {
            case "1":
                col = "c.YJDMB AS JDMB , c.YJDWCQK AS YJDWCQK , c.YJDPG AS YJDPG";
                break;
            case "2":
                col = "c.BNMB AS JDMB , c.BNWCQK AS YJDWCQK , c.BNPG AS YJDPG";
                break;
            case "3":
                col = "c.SJDMB AS JDMB , c.SJDWCQK AS YJDWCQK , c.SJDPG AS YJDPG";
                break;
            case "4":
                col = "c.QNMB AS JDMB , c.QNWCQK AS YJDWCQK , c.QNPG AS YJDPG";
                break;
        }
        dcdbBmmbnr.setJdlx(jdlx);
        dcdbBmmbnr.setColnum(col);

        return dcdbBmmbnrDao.selectZyrwListTbjd(dcdbBmmbnr);
    }

    @Override
    public int updateJdwcqk(DcdbBmmbnr dcdbBmmbnr) {
        String jdlx = dcdbBmmbnr.getStatus().substring(0, 1);
        //根据状态码，判断更新哪季度数据，动态字段 by id
        if("updateWcqk".equals(dcdbBmmbnr.getIsBgs())){
            switch (jdlx) {
                case "1":
                    dcdbBmmbnr.setYjdwcqk(dcdbBmmbnr.getJdwcqk());
                    break;
                case "2":
                    dcdbBmmbnr.setBnwcqk(dcdbBmmbnr.getJdwcqk());
                    break;
                case "3":
                    dcdbBmmbnr.setSjdwcqk(dcdbBmmbnr.getJdwcqk());
                    break;
                case "4":
                    dcdbBmmbnr.setQnwcqk(dcdbBmmbnr.getJdwcqk());
                    break;
            }

        }else if("updateXnpg".equals(dcdbBmmbnr.getIsBgs())){
            switch (jdlx) {
                case "1":
                    dcdbBmmbnr.setYjdpg(dcdbBmmbnr.getJdwcqk());
                    break;
                case "2":
                    dcdbBmmbnr.setBnpg(dcdbBmmbnr.getJdwcqk());
                    break;
                case "3":
                    dcdbBmmbnr.setSjdpg(dcdbBmmbnr.getJdwcqk());
                    break;
                case "4":
                    dcdbBmmbnr.setQnpg(dcdbBmmbnr.getJdwcqk());
                    break;
            }
        }
        return dcdbBmmbnrDao.updateJdwcqk(dcdbBmmbnr);
    }

    @Override
    public int updateKhpzById(DcdbBmmbnr record) {
        return dcdbBmmbnrDao.updateKhpzById(record);
    }

    @Override
    public int insertKhpz(DcdbBmmbnr record) {
        return dcdbBmmbnrDao.insertKhpz(record);
    }

    @Override
    public List<DcdbBmmbnr> selectKhpzById(String yjid,String ssbm) {
        return dcdbBmmbnrDao.selectKhpzById(yjid,ssbm);
    }

    @Override
    public void deleteDcdbFile(DcdbProjectFile record) {
        dcdbBmmbnrDao.deleteDcdbFile(record);
    }


    @Override
    public Boolean uploadFiles(List<MultipartFile> files, DcdbProjectFile dcdbProjectFile, HttpServletRequest request) {

        if ( CollectionUtils.isNotEmpty(files) ) {
            MultipartFile[] files1 = new MultipartFile[files.size()];
            for (int i = 0; i < files.size(); i++) {
                files1[i] = files.get(i);
            }
           return this.uploadFiles(files1, dcdbProjectFile, request);
        }

        return false;
    }

  @Override
  public int insertNdrwYb(List<DcdbBmmbnr> bmnrlist) {
  for(DcdbBmmbnr bmnr:bmnrlist){
      dcdbBmmbnrDao.insertNdrwYb(bmnr);
  }
        return 0;
  }

  private Boolean uploadFiles(MultipartFile[] files, DcdbProjectFile dcdbProjectFile, HttpServletRequest request) {
        Boolean flag=true;
        try {
            if ( files != null && files.length > 0 ) {
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for (MultipartFile item : files) {
                    //获取uuid,并且上传文件
                    String uuid = FileUtil.getUUID();
                    FileUtil.uploadFile(item, dcdbProjectFile.getBmrwid(), uuid);
                    //入库
                    dcdbProjectFile.setFilename(item.getOriginalFilename());
                    dcdbBmmbnrDao.deleteDcdbFile(dcdbProjectFile);

                    dcdbProjectFile.setId(uuid);
                    dcdbProjectFile.setFilesize(String.valueOf(item.getSize()));
                    dcdbBmmbnrDao.insertDcdbFile(dcdbProjectFile);
                }
            }
        } catch (Exception e) {
             logger.error("文件上传失败!", e);
             e.printStackTrace();
             flag= false;
        }
            return flag;

    }
}
