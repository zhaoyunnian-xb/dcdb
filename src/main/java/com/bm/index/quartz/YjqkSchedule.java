package com.bm.index.quartz;

import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.service.DcdbDbService;
import com.bm.index.service.DcdbJbxxService;
import com.bm.index.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component
public class YjqkSchedule {

    private static Log logger = LogFactory.getLog(YjqkSchedule.class);
    @Autowired
    private DcdbJbxxService jbxxService;
    @Autowired
    private DcdbDbService dbService;
    //定时更新待办表预警状态1.正常期限 2.离最后期限三天 3.超过最后期限
    @Scheduled(cron = "0 32 17 * * ?")
    public void yjqkUpdate() throws ParseException {
        try {
            List<DcdbDjlbDbParam> resultList = new ArrayList<>();
            List<DcdbDjlbDbParam> dbQjqkList = jbxxService.queryYjqk();
            for(DcdbDjlbDbParam djlbDbParam : dbQjqkList){
                DcdbDjlbDbParam dbParam = new DcdbDjlbDbParam();
                String cjrq = djlbDbParam.getCjrq();//创建日期
                String cbqx = djlbDbParam.getCbqx();//承办期限
                String id = djlbDbParam.getId();//待办表id
                dbParam.setId(id);
                //增加承办期限后的日期
                String addDay = DateUtil.addDay(cjrq, Integer.parseInt(cbqx));
                //离最后期限三天
                String lastFiveDay = DateUtil.addDay(addDay,-3);
                //转换为日期格式
                Date addDayDate = DateUtil.convertStringToDate(addDay);
                Date LastFiveDayDate = DateUtil.convertStringToDate(lastFiveDay);
                //获取最后期限的时间戳
                long addDayDateTime = addDayDate.getTime();
                //离最后期限三天的时间戳
                long lastFiveDayDateTime = LastFiveDayDate.getTime();
                //当前日期时间戳
                long currentTime = new Date().getTime();
                if(currentTime>addDayDateTime){
                    //当前时间大于最后截至日期，则严重预警
                    dbParam.setYjqk("3");
                }else if(lastFiveDayDateTime<currentTime&&currentTime<=addDayDateTime){
                    //当前时间大于最后截至日期的前三天并且小于等于最后的截至日期
                    dbParam.setYjqk("2");
                }else {
                    //当前时间小于最后截至日期的前三天
                    dbParam.setYjqk("1");
                }
                resultList.add(dbParam);
            }
            Map<String,Object> param = new HashMap<>();
            param.put("list",resultList);
            dbService.batchUpdate(param);
            logger.error("定时跑批成功！");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("定时跑批失败！");
        }


    }
}
