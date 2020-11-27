package com.bm.index.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author tp
 * Date : 2018/5/10/010 10:33
 */
public class Constants {
    
    /**
     * 询问提纲
     */
    public static final int SUCCESS = 1;
    /**
     * 公诉意见
     */
    public static final int FAILED = 2;
    /**
     *  是否为新增
     */
    public static final String ADD = "01";
    /**
     * 是否为收藏
     */
    public static final String STOCK = "02";
    /**
     *  正常
     */
    public static final String NO_DEL = "N";
    /**
     *  删除
     */
    public static final String DEL = "Y";
    /**
     * 询问提纲
     */
    public static final String XXTG = "01";
    /**
     * 公诉意见
     */
    public static final String GSYJ = "02";
    /**
     * 答辩提纲
     */
    public static final String DBTG = "03";
    /**
     * 其他
     */
    public static final String OTHER = "04";
    
    /**
     * 询问笔录文书类型号
     */
    public static final String WSLX_XWBL = "0201005";
    
    /**
     * 讯问笔录文书类型号
     */
    public static final String WSLX_XWBLL = "0201003";
    
    /**
     * 检察建议 待办状态
     */
    public static final String JCJY_DB = "1";
    
    /**
     * 检察建议 办结状态
     */
    public static final String JCJY_BJ = "3";
    
    /**
     * 检察建议 办结状态
     */
    public static final String JCJY_BACK = "reback";
    
    /**
     * 检察建议文书模板字典组
     */
    public static final String JCJY_DOC_TEMP = "JcjyDocTemp";
    
    /**
     * 检察建议案件基本信息
     */
    public static final String JCJY_AJJBXX = "jcjyAjjbxx";
    
    /**
     * 检察建议案件字典项类别编码
     */
    public static final String JCJY_DICT_ITEM = "jcjyDictItem";
    
    /**
     * 新建检察建议页面初始化配置
     */
    public static final String JCJY_CONFIG = "createJcjyConfig";
    
    /**
     * 新建检察建议文件上传nodeid字段标识
     */
    public static final String JCJY_CLUE_FILE = "clue";
    /**
     * 检察建议统计分析导出EXCEL英文字段
     */
    public final static List<String> JCJY_TJFX_LIST = Arrays.asList("LEVNAME","ZSJCJYMX","ZSJCJYXZ","ZSJCJYXS",
    													"JZSSWFLGA","JZSSWFLLA","SHZAZHL","GYSSMS","GYSSXZ",
											    		"LFXJL","DCXZJGLZ","LBQT","ZJSD","GKYG",
											    		"ZZZJ","ZZGS","ZZWJ","ZZXZ","ZZMX",
											    		"ZZKS","ZZQT","XZJGZFBM","AGSFJG","QYSYDW",
											    		"QT","HFS","HFL","CNS","CNL"
    													);
    
    /**
     * db_link测试库
     * */
    public final static String DB_LINK_TEST = "@db_tyyw_test.dw";
    
    /**
     * db_link正式库
     * */
    public final static String DB_LINK_ZS = "@db_tyyw.dw";
    
    /**
     * db_link正式库
     * */
    public final static String DB_LINK_NAME = "DB_LINK";
}
