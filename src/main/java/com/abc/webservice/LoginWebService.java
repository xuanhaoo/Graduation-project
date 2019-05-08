package com.abc.webservice;

import com.abc.annotation.CustomerLogin;
import com.abc.entity.Customer;
import com.abc.service.CustomerService;
import com.abc.service.impl.CustomerServiceImpl;
import com.abc.util.MD5Util;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author: liangxuanhao
 * @Description: 前台社区登录控制器
 * @Date: 2019年03月13 20:10
 */
@RestController
@RequestMapping("webservice/loginWebService")
public class LoginWebService {

    private static final Logger log = LoggerFactory.getLogger(LoginWebService.class);

    @Autowired
    private CustomerService customerService;


    /**
     * 前台用户的的登录，登录成功，保存session信息
     * @param body
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Json login(@RequestBody String body, HttpServletRequest request) {
        String oper = "customer login";
        JSONObject json = JSON.parseObject(body);
        String cusName = json.getString("username");
        String pwd = json.getString("password");

        if (StringUtils.isEmpty(cusName)){
            return Json.fail(oper,"用户名不能为空");
        }
        if (StringUtils.isEmpty(pwd)){
            return Json.fail(oper,"密码不能为空");
        }
        Customer customerDB = customerService.selectOne(new EntityWrapper<Customer>().eq("cus_name", cusName));
        if (null == customerDB) {
            return Json.fail(oper,"用户帐号或密码不正确");
        }
        if (!MD5Util.validatePassword(customerDB.getPwd(), pwd)) {
            return Json.fail(oper,"用户帐号或密码不正确");
        }
        if (customerDB.getLock() == 1) {
            return Json.fail(oper,"账号被锁定！请联系客服");
        }
        // 保存用户信息至session中
        HttpSession session = request.getSession();
        session.setAttribute("customer_key", customerDB);
        // 设置信息保存30min
        session.setMaxInactiveInterval(30*60);
        customerDB.setPwd("");
        return Json.succ()
                .data("userInfo", customerDB);
    }


    /**
     * 注册用户
     * @param body
     * @return
     */
    @PostMapping("/register")
    public Json register(@RequestBody String body) {
        String oper = "register customer";
        Customer customer = JSON.parseObject(body, Customer.class);
        if (customer.getCusName() == null) {
            return Json.fail("oper", "用户名不能为空");
        }
        if (customer.getNickName() == null) {
            return Json.fail("oper", "昵称不能为空");
        }
        if (customer.getPwd() == null) {
            return Json.fail("oper", "密码不能为空");
        }
        if (customer.getPhone() == null) {
            return Json.fail("oper", "手机号不能为空");
        }
        String md5Pwd = MD5Util.generatePassword(customer.getPwd());
        customer.setPwd(md5Pwd);
        customer.setLock(0);
        customer.setSalt("");
        customer.setSex(1);
        customer.setCreateDate(new Date());
        Customer customerDB = customerService.selectOne(new EntityWrapper<Customer>().eq("cus_name", customer.getCusName()));
        if (customerDB != null) {
            return Json.fail("oper", "用户名已经存在！");
        }
        boolean success = customerService.insert(customer);
        return Json.result(oper, success);
    }


    /**
     * 获取用户信息
     * @param body
     * @return
     */
    @PostMapping("/queryUserInfo")
    public Json getUserInfo(@RequestBody String body) {
        String oper = "query customerInfo";
        JSONObject json = JSON.parseObject(body);
        String cusName = json.getString("username");

        if (StringUtils.isEmpty(cusName)){
            return Json.fail(oper,"用户名不能为空");
        }
        Customer customerDB = customerService.selectOne(new EntityWrapper<Customer>().eq("cus_name", cusName));
        customerDB.setPwd("");
        return Json.succ().
                data("userInfo", customerDB);

    }


    /**
     * 登出
     * @return
     */
    @GetMapping("/logout")
    public Json logout(HttpSession httpSession) {
        httpSession.removeAttribute("customer_key");
        return new Json();
    }

    @CustomerLogin
    @GetMapping("/test")
    public Json test() {
        System.out.println("xxxx");
        return Json.succ();
    }

    @GetMapping("/test2")
    public Json test2() {
        System.out.println("yyyy");
        return Json.succ();
    }
}
