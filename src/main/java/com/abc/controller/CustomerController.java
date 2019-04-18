package com.abc.controller;

import com.abc.entity.Customer;
import com.abc.entity.SysRole;
import com.abc.entity.SysUser;
import com.abc.service.CustomerService;
import com.abc.util.MD5Util;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: liangxuanhao
 * @Description: 分享足迹前端用户控制器
 * @Date: 2019年02月27 16:01
 */

@RestController
@RequestMapping("/sys/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerService customerService;


    @PostMapping
    public Json add(@RequestBody String body) {

        return null;
    }


    /**
     * 查询分享足迹前台的用户
     * @param body
     * @return
     */
    @PostMapping("/query")
    public Json query(@RequestBody String body) {
        String oper = "query customer";
        log.info("{}, body: {}", oper, body);

        JSONObject json = JSON.parseObject(body);
        // 用户名模糊查询
        String cusName = json.getString("cusName");
        // 邮箱匹配
        String email = json.getString("email");
        int current = json.getIntValue("current");
        int size = json.getIntValue("size");
        if (current == 0) current = 1;
        if (size == 0) size = 10;

        Wrapper<Customer> queryParams = new EntityWrapper<>();
        queryParams.orderBy("createDate", true);
        if (StringUtils.isNotBlank(cusName)) {
            queryParams.like("cus_name", cusName);
        }
//        if (StringUtils.isNotBlank(email)) {
//            queryParams.eq("email", email);
//        }
        Page<Customer> page = customerService.selectPage(new Page<>(current, size), queryParams);
        return Json.succ(oper).data("page", page);
    }


    /**
     * 删除社区用户
     * @param body
     * @return
     */
    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete customer";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        String id = jsonObj.getString("id");

        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除社区用户：参数为空（逻辑id）");
        }
        boolean success = customerService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

    /**
     * 重置密码，如果由管理员重置密码，同一将密码重置为 "123456"
     * @param body
     * @return
     */
    @PatchMapping("/resetPwd")
    public Json resetPwd(@RequestBody String body) {
        String oper = "update cus_pwd";
        log.info("{}, body: {}", oper, body);

        JSONObject json = JSON.parseObject(body);
        String cid = json.getString("id");

        if (StringUtils.isBlank(cid)) {
            return Json.fail(oper,"无法更新密码：社区用户id为空");
        }
        //密码加密
//        RandomNumberGenerator saltGen = new SecureRandomNumberGenerator();
//        String salt = saltGen.nextBytes().toBase64();
//        String hashedPwd = new Sha256Hash("123456", salt, 1024).toBase64();
        String md5Pwd = MD5Util.generatePassword("123456");
        Customer customer = new Customer();
        customer.setId(Integer.parseInt(cid));
        customer.setPwd(md5Pwd);
        customer.setSalt("");
        customer.setUpdateDate(new Date());
        boolean success = customerService.updateById(customer);
        return Json.result(oper, success).data("updated",customer.getUpdateDate());
    }


    /**
     * 锁定用户
     * @return
     */
    @PatchMapping("/lockCustomer")
    public Json lockCustomer(@RequestBody String body) {
        String oper = "lock cus_person";
        log.info("{}, body: {}", oper, body);

        JSONObject json = JSON.parseObject(body);
        String cid = json.getString("id");
        String lock = json.getString("lock");
        if (StringUtils.isBlank(cid)) {
            return Json.fail(oper, "无法更改用户锁定状态：用户id为空");
        }
        // 锁定修改字段
        Customer customer = new Customer();
        customer.setId(Integer.parseInt(cid));
        // 1锁定 默认0
        customer.setLock(Integer.parseInt(lock));
        customer.setUpdateDate(new Date());
        boolean success = customerService.updateById(customer);
        return Json.result(oper, success).data("updated",customer.getUpdateDate());
    }
}
