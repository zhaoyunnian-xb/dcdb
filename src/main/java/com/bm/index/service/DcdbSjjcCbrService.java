package com.bm.index.service;

import com.bm.index.model.DcdbSjjcsxDcsxnq;
import com.bm.index.model.DcdbSjjcsxSpyj;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * @InterfaceName DcdbSjjcCbrService
 * @Description TODO
 * @Author daipx
 * @Date 2019/4/15 9:42
 * @Version 1.0
 **/
public interface DcdbSjjcCbrService {

  /**
   * 承办页面：查询督查事项表格
   * @param dcdbSjjcsxDcsxnq
   * @return
   */
  List<DcdbSjjcsxDcsxnq> queryCbrDcsxList(DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq);

  /**
   * 督查事项，填报校验
   * @param dcdbSjjcsxDcsxnq
   * @return
   */
  Map<String,Object> validateTb(DcdbSjjcsxDcsxnq dcdbSjjcsxDcsxnq);

  /**
   * 承办页面:查询某条督查事项的最新意见
   * @param spyj
   * @return
   */
  List<DcdbSjjcsxSpyj> querySjjcSpyj(DcdbSjjcsxSpyj spyj);

  /**
   * 承办页面：往期查看
   * @param spyj
   * @return
   */
  List<DcdbSjjcsxSpyj> querySjjcSpyjWqck(DcdbSjjcsxSpyj spyj);

  /**
   * 承办页面：保存承办描述，审批意见等
   * @param spyj
   * @return
   */
  Map<String,Object> saveSjjcSpyj(DcdbSjjcsxSpyj spyj);

  /**
   * 上级重要决策，承办阶段提交
   * @param se
   * @param map
   * @return
   */
  Map<String,Object> submitSjjc(HttpSession se,Map<String,String> map);
}
