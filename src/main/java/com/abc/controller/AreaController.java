package com.abc.controller;

import com.abc.entity.Area;
import com.abc.service.AreaService;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description: 区域信息管理设置
 * @Date: 2019年02月27 16:01
 */
@RestController
@RequestMapping("/sys/area")
public class AreaController {
    private static final Logger log = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;


    /*********暂时搁置********/

    @PostMapping("/queryAllListNoPageTwo")
    public Json queryAllListNoPage() {
        String oper = "query area no page";

//        JSONObject jsonObj = JSON.parseObject(body);
//        String parentId = jsonObj.getString("parentId");
        int parentId = 0;
        List<Area> areaList = areaService.selectList(new EntityWrapper<Area>().eq("PARENT_ID", parentId));
        return Json.succ().data(areaList);

    }

    @PostMapping("/queryAllListNoPageThree")
    public Json queryAllListNoPage2(@RequestBody String body) {
        String oper = "query area2 no page";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        int parentId = jsonObj.getIntValue("parentId");
        List<Area> areaList = areaService.selectList(new EntityWrapper<Area>().eq("PARENT_ID", parentId));
        return Json.succ().data(areaList);

    }


}
