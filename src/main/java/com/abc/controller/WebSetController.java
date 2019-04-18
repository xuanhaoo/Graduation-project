package com.abc.controller;

import com.abc.entity.WebSet;
import com.abc.service.WebSetService;
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


/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月09 15:39
 */
@RestController
@RequestMapping("/sys/webSet")
public class WebSetController {


    private static final Logger log = LoggerFactory.getLogger(WebSetController.class);



    @Autowired
    private WebSetService webSetService;



    @PostMapping("/query")
    public Json query(@RequestBody String body) {

        String oper = "query webset";
        log.info("{}, body: {}", oper, body);

        JSONObject json = JSON.parseObject(body);
        // 名称模糊查询
        // 类型
        int type = json.getIntValue("type");
        int current = json.getIntValue("current");
        int size = json.getIntValue("size");
        if (current == 0) current = 1;
        if (size == 0) size = 10;

        Wrapper<WebSet> queryParams = new EntityWrapper<>();
        Page<WebSet> page = webSetService.selectPage(new Page<>(current, size), queryParams);
        return Json.succ(oper).data("page", page);

    }

    @PostMapping
    public Json add(@RequestBody String body) {
        String oper = "add webset";

        WebSet webSet = JSON.parseObject(body, WebSet.class);

        if (StringUtils.isBlank(webSet.getName())) {
            return Json.fail(oper, "网站名不能为空");
        }
        WebSet webSetDB = webSet.selectOne(new EntityWrapper<WebSet>().eq("type", webSet.getType()));
        if (webSetDB != null) {
            return Json.fail(oper, "该网站配置已经存在：" +  webSet.getName());
        }

        //保存新用户数据
        boolean success = webSetService.insert(webSet);
        return Json.result(oper, success)
                .data("id", webSet.getId());
    }


    @PatchMapping
    public Json update(@RequestBody String body) {
        String oper = "update webset";
        log.info("{}, body: {}", oper, body);

        WebSet webSet = JSON.parseObject(body, WebSet.class);
        if (null == webSet.getId()) {
            return Json.fail(oper, "无法更新：参数为空（id）");
        }
        boolean success = webSetService.updateById(webSet);
        return Json.result(oper, success).data("id", webSet.getId());
    }


    /**
     * 基本删除
     * @param body
     * @return
     */
    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete webset";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        String id = jsonObj.getString("id");

        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除：参数为空（id）");
        }

        boolean success = webSetService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

}
