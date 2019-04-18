package com.abc.webservice;

import com.abc.entity.Customer;
import com.abc.service.CustomerService;
import com.abc.util.MD5Util;
import com.abc.util.UploadUtil;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月26 13:39
 */
@RestController
@RequestMapping("webservice/personInfoWebService")
public class PersonInfoWebService {

    private static final Logger log = LoggerFactory.getLogger(PersonInfoWebService.class);

    @Autowired
    private CustomerService customerService;


    /**
     * 根据id查询用户信息
     * @param body
     * @return
     */
    @PostMapping("/queryUserInfo")
    public Json getUserInfo(@RequestBody String body) {
        String oper = "query customerInfo";
        JSONObject json = JSON.parseObject(body);
        int id = Integer.parseInt(json.getString("id"));

        Customer customerDB = customerService.selectOne(new EntityWrapper<Customer>().eq("id", id));
        customerDB.setPwd("");
        customerDB.setSalt("");
        return Json.succ().
                data("userInfo", customerDB);

    }


    /**
     * 修改个人信息
     * @param body
     * @return
     */
    @PostMapping("/updateUserInfo")
    public Json updateUserInfo(@RequestBody String body) {
        String oper = "update userInfo front";
        Customer customer = JSON.parseObject(body, Customer.class);
        if (customer.getId() == null) {
            return Json.fail(oper, "无法更新信息：参数为空（id）");
        }
        customer.setUpdateDate(new Date());
        boolean success = customerService.updateById(customer);

        return Json.result(oper, success);
    }

    /**
     * 上传头像
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadAvatar")
    public Json uploadAvatar(@RequestParam("file")MultipartFile multipartFile) throws Exception {
        UploadUtil cosClientUtil = new UploadUtil();
        String name = cosClientUtil.uploadFile2Cos(multipartFile);
        String imgUrl = cosClientUtil.getImgUrl(name);
        String[] split = imgUrl.split("\\?");
        String resultUrl = split[0];
        return Json.succ().data("urlData", resultUrl);

    }


    /**
     * 修改密码
     * @param body
     * @return
     */
    @PostMapping("/updatePsw")
    public Json updatePsw(@RequestBody String body) {
        String oper = "customer updatePwd";
        JSONObject json = JSON.parseObject(body);
        String oldPsw = json.getString("oldPsw");
        String newPsw = json.getString("newPsw");
        String id = json.getString("id");

        if (StringUtils.isEmpty(id)){
            return Json.fail(oper,"用户id不能为空");
        }
        if (StringUtils.isEmpty(oldPsw) || StringUtils.isEmpty(newPsw)){
            return Json.fail(oper,"密码不能为空");
        }
        Customer customerDB = customerService.selectOne(new EntityWrapper<Customer>().eq("id", Integer.parseInt(id)));
        if (null == customerDB) {
            return Json.fail(oper,"用户帐号不正确");
        }
        if (!MD5Util.validatePassword(customerDB.getPwd(), oldPsw)) {
            return Json.fail(oper,"原密码不正确");
        }

        String md5Pwd = MD5Util.generatePassword(newPsw);
        Customer customer = new Customer();
        customer.setId(Integer.parseInt(id));
        customer.setPwd(md5Pwd);
        customer.setSalt("");
        customer.setUpdateDate(new Date());
        boolean success = customerService.updateById(customer);
        return Json.result(oper, success);


    }

}
