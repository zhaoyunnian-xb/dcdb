package com.bm.index.dao.source2;

import com.bm.index.model.Menu;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DcdbTyyhDao
 * @Description TODO
 * @Author daipx
 * @Date 2019/4/24 9:39
 * @Version 1.0
 **/
public interface DcdbTyyhDao {

    List<Menu> testTree(Map<String,String> info);
}
