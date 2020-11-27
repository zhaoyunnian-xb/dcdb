package com.bm.index.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.context.ServletContext;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
// 一个切面 ,完成springmvc的方法
@Aspect
@Component
public class PagerAspect {

    //日志
  //  private final static Logger logger = LoggerFactory.getLogger(PagerAspect.class);

    private final static String PAGE_SIZE = "limit";//每页大小
    private final static String PAGE_NUM = "page";//当前页码


    // 标记的注解
    @Pointcut("@annotation(com.bm.index.common.annotation.PageHelperAnn)")
    public void page() {}


    @Around("page()")
    public JSONObject pageAround(ProceedingJoinPoint jp ){

        JSONObject res = new JSONObject();

        String pageSize = ServletContext.getRequest().getParameter(PAGE_SIZE);//每页大小
        String pageNum  = ServletContext.getRequest().getParameter(PAGE_NUM);//当前页码

        //logger.debug("调用分页框架传入的每页大小是:{},当前页码是:{}",pageSize,pageNum);

        if (StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageNum)) {
            res.put("msg","传入的分页参数有空值");
            return res;
        }

        int pSize ;
        int pNum ;
        //转换数据 类型
        try {
            pSize = Integer.parseInt(pageSize);
            pNum  = Integer.parseInt(pageNum);
        }catch (NumberFormatException e) {
            //logger.error("分页参数Str->int格式转换异常，pageSize:{},pageNum:{}",pageSize,pageNum);
            res.put("msg","分页参数转换异常，请检查传入的分页参数是否为数字");
            return res;
        }
        //开始分页
        Page<?> page = PageHelper.startPage(pNum,pSize);
        //执行方法 获取返回值

        Object retval = null;
        try {
            retval = jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //logger.error("执行核心的查询逻辑异常:{}",throwable);
            res.put("msg","查询失败，执行核心的查询逻辑异常");
            return res;
        }
        JSONObject p ;
        if (retval != null && retval instanceof JSONObject) {
            p = (JSONObject) retval;
        } else {
            res.put("msg","查询的返回值为空或者返回类型不是规定的数据类型");
            return res;
        }

        long count = page.getTotal();
        p.put("count",count);
        p.put("code",0);
        p.put("msg","");
        p.put("currentPage",pNum);
        return p;
    }
}