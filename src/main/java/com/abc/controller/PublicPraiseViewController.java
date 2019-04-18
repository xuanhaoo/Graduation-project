package com.abc.controller;

import com.abc.entity.ArtComment;
import com.abc.entity.PublicPraiseView;
import com.abc.service.PublicPraiseViewService;
import com.abc.util.PageUtils;
import com.abc.vo.Json;
import com.abc.vo.PublicEchartsJson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月08 13:11
 */
@RestController
@RequestMapping("/sys/publicPraiseView")
public class PublicPraiseViewController {

    private static final Logger log = LoggerFactory.getLogger(PublicPraiseViewController.class);


    @Autowired
    private PublicPraiseViewService publicPraiseViewService;


    @PostMapping("/queryPraiseViewList")
    public Json queryPraiseViewList(@RequestBody String body) {

        String oper = "queryPraiseViewList";
        JSONObject json = JSON.parseObject(body);
        String spotKeyWord = json.getString("spotKeyWord");
        Page<PublicPraiseView> page = publicPraiseViewService.queryAllPublicPraise(PageUtils.getPageParam(json), spotKeyWord);
        return Json.succ().data("page", page);
    }

    /**
     * 后台管理页数据分析
     * @return
     */
    @GetMapping("/praiseViewDataAnalysis")
    public Json praiseViewDataAnalysis() {
        String oper = "praiseViewDataAnalysis";

        PublicEchartsJson publicEchartsJson = publicPraiseViewService.analysisPublicData();

        return Json.succ().data("data", publicEchartsJson);
    }

    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete publicPraiseView";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        String id = jsonObj.getString("id");

        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除：参数为空（逻辑id）");
        }
        boolean success = publicPraiseViewService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

    /**
     * 通过景点查取点评
     * @param body
     * @return
     */
    @PostMapping("/queryCommentBySpot")
    public Json queryCommentBySpot(@RequestBody String body) {
        String oper = "query publicPraise by viewspot";

        JSONObject json = JSON.parseObject(body);
        int spotId = json.getIntValue("spotId");
        if (spotId == 0) {
            return Json.fail(oper, "无法查询：参数为空（逻辑id）");
        }
        Page<PublicPraiseView> page = publicPraiseViewService.queryPublicPraiseBySpot(PageUtils.getPageParam(json), spotId);
        return Json.succ().data("page", page);
    }


}
