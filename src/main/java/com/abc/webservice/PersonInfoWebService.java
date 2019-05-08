package com.abc.webservice;

import com.abc.entity.ArtContent;
import com.abc.entity.Customer;
import com.abc.entity.FollowRelation;
import com.abc.service.ContentService;
import com.abc.service.CustomerService;
import com.abc.service.FollowRelationService;
import com.abc.util.MD5Util;
import com.abc.util.UploadUtil;
import com.abc.vo.FoRelation;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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

    @Autowired
    private ContentService contentService;

    @Autowired
    private FollowRelationService followRelationService;


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


    /**
     * 发表内容
     * @param body
     * @return
     */
    @PostMapping("/pubContent")
    public Json pubContent(@RequestBody String body) {
        String oper = "pub content";
        ArtContent artContent = JSON.parseObject(body, ArtContent.class);
        if (artContent.getAuthorId() <= 0) {
            return Json.fail(oper, "无法更新信息：参数为空（authorId）");
        }
        // 插入文章
        Json json = contentService.pubContent(artContent);
        return json;

    }

    /**
     * 查询粉丝数量与关注用户
     * @param body
     * @return
     */
    @PostMapping("/getRelation")
    public Json getRelation(@RequestBody String body) {
        String oper = "get relation";
        JSONObject json = JSON.parseObject(body);
        String userid = json.getString("userid");
        if (userid == null || userid.equals("")) {
            return Json.fail(oper, "无法获取数据: 参数为空(userid)");
        }
        int user = Integer.parseInt(userid);
        // 粉丝数
        int follows = followRelationService.selectCount(new EntityWrapper<FollowRelation>().eq("by_follow", user));
        // 关注者数
        int guanzhu = followRelationService.selectCount(new EntityWrapper<FollowRelation>().eq("followers", user));
        FoRelation foRelation = new FoRelation();
        foRelation.setFollowNumber(follows);
        foRelation.setGuanzhuNumber(guanzhu);
        return Json.succ().data("data", foRelation);

    }


    /**
     * 查询个人主页面板的tab页数据
     * @param body
     * @return
     */
    @PostMapping("/getTabData")
    public Json getTabData(@RequestBody String body) {
        String oper = "query tabData";
        JSONObject json = JSON.parseObject(body);
        // 用户
        String authorId = json.getString("userid");
        // 类型
        String subjectType = json.getString("subjectType");
        int current = json.getIntValue("current");
        int size = json.getIntValue("size");
        if (current == 0) current = 1;
        if (size == 0) size = 10;

        // 设置条件
        Wrapper<ArtContent> queryParams = new EntityWrapper<>();
        queryParams.eq("author_id", Integer.parseInt(authorId));
        queryParams.eq("subject_id", Integer.parseInt(subjectType));
        queryParams.orderBy("create_date", false);
        Page<ArtContent> page = contentService.selectPage(new Page<>(current, size), queryParams);
        return Json.succ(oper).data("page", page);
    }



}
