package com.abc.controller;

import com.abc.entity.ArtBody;
import com.abc.entity.ArtContent;
import com.abc.entity.ArtSubject;
import com.abc.service.ArtContentBodyService;
import com.abc.service.ContentService;
import com.abc.service.SubjectService;
import com.abc.service.ViewSpotInfoService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: liangxuanhao
 * @Description: 内容运营：可查看内容文章，并且点击按钮实现查看文章详细内容，或者跳转评论查看页面
 * @Date: 2019年03月01 14:41
 */
@RestController
@RequestMapping("/sys/content")
public class ContentController {


    private static final Logger log = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ViewSpotInfoService viewSpotInfoService;

    @Autowired
    private ArtContentBodyService artContentBodyService;



    /**
     * 后台查询
     * @param body
     * @return
     */
    @PostMapping("/query")
    public Json query(@RequestBody String body) {
        String oper = "query content(sys)";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObject = JSON.parseObject(body);

        int subjectId = jsonObject.getIntValue("subjectId");
        int status = jsonObject.getIntValue("status");
        Page<ArtContent> page = contentService.queryAllListByLeft(PageUtils.getPageParam(jsonObject), subjectId, status);
        return Json.succ(oper).data("page", page);
    }


    /**
     * 审核文章
     * @param body
     * @return
     */
    @PostMapping("/checkContent")
    public Json checkContent(@RequestBody String body) {
        String oper = "check content";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);
        int id = json.getIntValue("id");
        int checkFlag = json.getIntValue("checkFlag");
        if (id == 0) {
            return Json.fail(oper, "无法更改状态：关键id参数错误");
        }
        ArtContent artContentDB = contentService.selectOne(new EntityWrapper<ArtContent>().eq("id", id));
        artContentDB.setWeight(checkFlag);
        artContentDB.setUpdateDate(new Date());
        boolean success = contentService.updateById(artContentDB);
        return Json.result(oper, success).data("updated", artContentDB.getUpdateDate());
    }


    /**
     * 设置置顶
     * @param body
     * @return
     */
    @PostMapping("/setWeight")
    public Json setWeight(@RequestBody String body) {
        String oper = "setweight content";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);
        int id = json.getIntValue("id");
        int weightFlag = json.getIntValue("weightFlag");
        if (id == 0) {
            return Json.fail(oper, "无法更改状态：关键id参数错误");
        }

        ArtContent artContentDB = contentService.selectOne(new EntityWrapper<ArtContent>().eq("id", id));
        artContentDB.setWeight(weightFlag);
        artContentDB.setUpdateDate(new Date());
        boolean success = contentService.updateById(artContentDB);
        return Json.result(oper, success).data("updated", artContentDB.getUpdateDate());
    }

    /**
     * 删除文章
     * @return
     */
    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete content";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        String id = jsonObj.getString("id");

        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除：参数为空（id）");
        }

        boolean success = contentService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

    @PostMapping("/queryBody")
    public Json queryBody(@RequestBody String body) {
        String oper = "query contentBody";
        log.info("{}, body: {}", oper, body);
        JSONObject json = JSON.parseObject(body);
        int bodyId = json.getIntValue("bodyId");

        ArtBody artBody = new ArtBody();
        artBody.setId(bodyId);
        ArtBody artBodyDB = artContentBodyService.selectOne(new EntityWrapper<ArtBody>().eq("id", artBody.getId()));
        return Json.succ().data(artBodyDB);
    }

    @PostMapping("/queryById")
    public Json queryContentById(@RequestBody String body) {
        String oper = "query content By id";
        JSONObject json = JSON.parseObject(body);
        int id = json.getIntValue("contentId");
//        ArtContent artContent = new ArtContent();
//        artContent.setId(id);
        ArtContent artContentDB = contentService.selectOne(new EntityWrapper<ArtContent>().eq("id", id));
        return Json.succ().data(artContentDB);
    }

}
