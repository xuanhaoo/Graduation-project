package com.abc.aspect;

import com.abc.constant.Codes;
import com.abc.controller.AuthController;
import com.abc.controller.GlobalExceptionHandler;
import com.abc.entity.Customer;
import com.abc.vo.Json;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: liangxuanhao
 * @Description:  前端用户的登录验证切面
 * @Date: 2019年03月13 20:40
 */
@Aspect
@Component
public class CustomerLoginIntercept {

    private static final Logger log = LoggerFactory.getLogger(CustomerLoginIntercept.class);
    public final static String CUS_SESSION_KEY = "customer_key";


    /**
     * 该方法表示在被@CustomerLogin注解修饰的方法之前调用该方法
     * 参数JoinPoint 表示被拦截点
     * @return
     */
    @Before(value = "@annotation(com.abc.annotation.CustomerLogin)")
    public Object judgeLogin(JoinPoint joinPoint) {
        // 获取到请求的属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取到请求的对象
        HttpServletRequest request = attributes.getRequest();
        // 获取session
        HttpSession session = request.getSession();
        // 判断是否有前端用户的 登录session

        Customer customer = (Customer) session.getAttribute(CUS_SESSION_KEY);
        if (null == customer) {
            log.info("未登录");
            // 如果未登录直接抛出全局统一异常
            throw new UnauthenticatedException();
        } else {
            return true;
        }
    }

}
