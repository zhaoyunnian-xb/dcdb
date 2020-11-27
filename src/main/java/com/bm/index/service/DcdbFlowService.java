package com.bm.index.service;

import com.bm.index.model.DcdbLcjd;
import com.bm.index.model.DcdbSpyj;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DcdbFlowService {

    /**
     * 暂时不用
     * @param se
     * @param lcjd
     * @return
     */
    Map<String,Object> submitGdAndYq(HttpSession se,DcdbLcjd lcjd);
    /**
     * 暂时不用
     * @param se
     * @param lcjd
     * @return
     */
    Map<String,Object> submitFlowFa(HttpSession se,DcdbLcjd lcjd);
    /**
     * 领导批示、党组检办 督办流转
     * @param se
     * @param lcjd
     * @return
     */
    Map<String,Object> submitFlow(HttpSession se,DcdbLcjd lcjd);

    /**
     * 查询 领导批示、党组检办 审批意见
     * @param spyj
     * @return
     */
    List<DcdbSpyj> selectSpyj(DcdbSpyj spyj);

    /**
     * 保存 领导批示、党组检办 审批意见
     * @param se
     * @param info
     * @return
     */
    Map<String,Object> saveSpyj(HttpSession se,String info);

    /**
     *  上级重要决策事项 流转
     * @param se
     * @param lcjd
     * @return
     */
    Map<String,Object> submitFlowZyjc(HttpSession se,DcdbLcjd lcjd);

}
