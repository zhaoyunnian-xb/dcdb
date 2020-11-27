package com.bm.index.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//表示运行时可以使用反射技术来使用
@Retention(RetentionPolicy.RUNTIME)
//表示这个注解只能标记到方法上面
@Target({ElementType.METHOD})
public @interface PageHelperAnn {


}
