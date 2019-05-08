package com.abc.webservice;

import com.abc.entity.*;
import com.abc.service.*;
import com.abc.util.PageUtils;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年04月24 16:21
 */
@RestController
@RequestMapping("webservice/sharePugService")
public class SharePugWebService {
    private static final Logger log = LoggerFactory.getLogger(SharePugWebService.class);

    @Autowired
    private ContentService contentService;

    @Autowired
    private ArtContentBodyService artContentBodyService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FollowRelationService followRelationService;

    @Autowired
    private CustomerService customerService;


    /**
     * 主题社区首页：按分类来
     * @param body
     * @return
     */
    @RequestMapping("/getContentList")
    public Json getContentList(@RequestBody String body) {
        String oper = "getContenList";
        JSONObject json = JSON.parseObject(body);
        // 1 按最新 | 2 按最热
        int orderType = json.getIntValue("orderTypeFlag");
        int subjectId = json.getIntValue("subjectId");
        int current = json.getIntValue("current");
        int size = json.getIntValue("size");
        if (current == 0) current = 1;
        if (size == 0) size = 10;

        Wrapper<ArtContent> queryParams = new EntityWrapper<>();
        // 决定排序的方式
        if (orderType == 1) {
            // asc 升序
            queryParams.orderBy("create_date", false);
        } else {
            queryParams.orderBy("view_count", false);
        }
        queryParams.eq("subject_id", subjectId);
        // 所有展示的数据均需要审核通过的
        queryParams.eq("status", 2);
        Page<ArtContent> page = contentService.selectPage(new Page<>(current, size), queryParams);
        return Json.succ().data("page", page);
    }


    /**
     * 获取内容详情页
     * @param body
     * @return
     */
    @PostMapping("/getContentMoreInfo")
    public Json getContentMoreInfo(@RequestBody String body) {
        String oper = "getContentMoreInfo";
        JSONObject json = JSON.parseObject(body);
        int contentId = json.getIntValue("contentId");
        if (contentId <= 0) {
            return Json.fail(oper, "参数缺失（contentid）");
        }
        ArtContent artContent = contentService.selectOne(new EntityWrapper<ArtContent>().eq("id", contentId));
        // 浏览数要加1
        artContent.setViewCount(artContent.getViewCount() + 1);
        contentService.updateById(artContent);
        return Json.succ().data("contentInfo", artContent);
    }

    /**
     * 获取内容主体
     * @return
     */
    @PostMapping("/getContentBody")
    public Json getContentBody(@RequestBody String body) {
        String oper = "getContentBody";
        JSONObject json = JSON.parseObject(body);
        int id = json.getIntValue("bodyId");
        if (id <= 0) {
            return Json.fail(oper, "参数缺失（contentid）");
        }
        ArtBody artBody = artContentBodyService.selectOne(new EntityWrapper<ArtBody>().eq("id", id));
        return Json.succ().data("bodyInfo", artBody);
    }

    /**
     * 获取评论信息
     * @param body
     * @return
     */
    @PostMapping("/getContentComment")
    public Json getContentComment(@RequestBody String body) {
        String oper = "getContentComment";
        JSONObject json = JSON.parseObject(body);
        int contentId = json.getIntValue("contentId");
        if (contentId <= 0) {
            return Json.fail(oper, "参数缺失（contentid）");
        }

        Page<ArtComment> page = commentService.getCommentByContentID(PageUtils.getPageParam(json), contentId);
        return Json.succ().data("page", page);
    }

    /**
     * 寻找是否为关注关联关系
     * @param body
     * @return
     */
    @PostMapping("/findRelation")
    public Json findRelation(@RequestBody String body) {
        String oper = "findRelation";
        JSONObject json = JSON.parseObject(body);
        // 判断用户之间的关系
        int authorId = json.getIntValue("authorId");
        int loginUserId = json.getIntValue("loginUserId");
        Wrapper<FollowRelation> queryParams = new EntityWrapper<>();
        queryParams.eq("by_follow", authorId);
        queryParams.eq("followers", loginUserId);
        FollowRelation followRelation = followRelationService.selectOne(queryParams);
        String flag = "true";
        if (null != followRelation) {
            flag = "false";
        }
        return Json.succ().data("flag", flag);
    }


    /**
     * 添加文章内容的评论
     * @param body
     * @return
     */
    @PostMapping("/addComment")
    public Json addComment(@RequestBody String body) {
        String oper = "addComment";
        ArtComment artComment = JSON.parseObject(body, ArtComment.class);
        if (StringUtils.isBlank(artComment.getContent())) {
            return Json.fail(oper, "回复内容不能为空");
        }
        artComment.setCreateDate(new Date());
        boolean success = commentService.insert(artComment);
        // 在文章的评论数要加1
        int contentId = artComment.getArtId();
        ArtContent artContent1 = contentService.selectOne(new EntityWrapper<ArtContent>().eq("id", contentId));
        artContent1.setCommentCount(artContent1.getCommentCount() + 1);
        contentService.updateById(artContent1);
        return Json.result(oper, success);
    }

    /**
     * 获取作者信息
     * @param body
     * @return
     */
    @PostMapping("/getAuthorInfo")
    public Json getAuthorInfo(@RequestBody String body) {
        JSONObject json = JSON.parseObject(body);
        int id = json.getIntValue("authorId");

        Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("id", id));
        customer.setPwd("");
        return Json.succ().data("authorInfo", customer);
    }


    /**
     * 添加关注
     * @param body
     * @return
     */
    @PostMapping("/addFollow")
    public Json addFollow(@RequestBody String body) {
        String oper = "findRelation";
        JSONObject json = JSON.parseObject(body);
        // 判断用户之间的关系
        int authorId = json.getIntValue("authorId");
        int loginUserId = json.getIntValue("loginUserId");
        FollowRelation followRelation = new FollowRelation();
        followRelation.setByFollow(authorId);
        followRelation.setFollowers(loginUserId);
        followRelation.setCreateDate(new Date());
        followRelationService.insert(followRelation);
        return Json.succ();
    }
}
