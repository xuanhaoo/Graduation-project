package com.abc.controller;

import com.abc.entity.ViewSpotInfo;
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
 * @Description:
 * @Date: 2019年03月01 15:06
 */
@RestController
@RequestMapping("/sys/viewSpot")
public class ViewSpotInfoController {

    private static final Logger log = LoggerFactory.getLogger(ViewSpotInfoController.class);

    @Autowired
    private ViewSpotInfoService viewSpotInfoService;




    @PostMapping("/query")
    public Json query(@RequestBody String body) {

        String oper = "query viewSpot";
        log.info("{}, body: {}", oper, body);

        JSONObject json = JSON.parseObject(body);
        // 名称模糊查询
        String viewName = json.getString("name");
        Page<ViewSpotInfo> page = viewSpotInfoService.queryAllLeftJoin(PageUtils.getPageParam(json), viewName);
        return Json.succ(oper).data("page", page);
    }

    /**
     * add
     * @param body
     * @return
     */
    @PostMapping
    public Json add(@RequestBody String body) {
        String oper = "add viewSpot";
        ViewSpotInfo viewSpotInfo = JSON.parseObject(body, ViewSpotInfo.class);

//        if (StringUtils.isBlank(artSubject.getName())) {
//            return Json.fail(oper, "主题名不能为空");
//        }

        ViewSpotInfo viewSpotInfoDB = viewSpotInfoService.selectOne(new EntityWrapper<ViewSpotInfo>().eq("name", viewSpotInfo.getName()));
        if (viewSpotInfoDB != null) {
            return Json.fail(oper, "景点已经存在：" +  viewSpotInfo.getName());
        }

        //保存新数据
        viewSpotInfo.setCreateDate(new Date());
        boolean success = viewSpotInfoService.insert(viewSpotInfo);
        return Json.result(oper, success)
                .data("id", viewSpotInfo.getId())
                .data("created", viewSpotInfo.getCreateDate());
    }


    /**
     * update
     * @param body
     * @return
     */
    @PatchMapping
    public Json update(@RequestBody String body) {
        String oper = "update viewSpot";
        ViewSpotInfo viewSpotInfo = JSON.parseObject(body, ViewSpotInfo.class);
        if (null == viewSpotInfo.getId()) {
            return Json.fail(oper, "无法更新：参数为空（id）");
        }
        viewSpotInfo.setUpdateTime(new Date());
        boolean success = viewSpotInfoService.updateById(viewSpotInfo);
        return Json.result(oper, success).data("updated", viewSpotInfo.getUpdateTime());
    }

    /**
     * delete
     * @param body
     * @return
     */
    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete viewSpot";

        JSONObject jsonObject = JSON.parseObject(body);
        String id = jsonObject.getString("id");
        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除：参数为空（id）");
        }
        boolean success = viewSpotInfoService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

}
