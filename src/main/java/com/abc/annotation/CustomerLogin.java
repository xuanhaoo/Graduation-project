package com.abc.annotation;

import java.lang.annotation.*;

/**
 * @Author: liangxuanhao
 * @Description: 自定义注解来拦截切面方法：拦截前台某些需要登录请求的操作
 *
 * @Target 注解说明此注解只能用于方法上面
 * @Retention 注解的保留位置
 * @Documented
 *
 * @Date: 2019年03月13 20:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerLogin {

}
