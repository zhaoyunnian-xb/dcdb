package com.bm.index.common.context;



import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用springmvc的底层的holder存储当前线程的ServletAPI对象
 *
 */
public class ServletContext {

    /**
     * 获取请求上下文之中属性
     * @return
     */
    private static ServletRequestAttributes getRequestAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 返回当前请求的HttpServletRequest对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 返回当前请求的HttpServletResponse
     * @return
     *
     * springframework  3.2.13.RELEASE 中
     * 不支持 ServletRequestAttributes 的 getResponse 方法   ----未实现
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }


}
