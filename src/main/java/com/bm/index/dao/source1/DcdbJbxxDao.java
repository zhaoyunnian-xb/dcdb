package com.bm.index.dao.source1;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbJbxx;
import com.bm.index.model.DcdbProjectFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DcdbJbxxDao {
    DcdbJbxx getById(String id);
    void save(DcdbJbxx dcdbjbxx);
    void update(DcdbJbxx dcdbjbxx);

    List<DcdbDjlbDbParam> queryYjqk();

    List<DcdbJbxx> selectWh();

    List<DcdbProjectFile> selectUploadFiles(@Param("id") String id, @Param("ywtype") String ywtype);
    //查询党组上传文件列表
    List<DcdbProjectFile> selectUploadFilesDZ(@Param("id") String id, @Param("ywtype") String ywtype,@Param("wh") String wh);
}
