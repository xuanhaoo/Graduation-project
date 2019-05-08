package com.abc.webservice;

import com.abc.entity.Area;
import com.abc.entity.PublicPraiseView;
import com.abc.entity.ViewSpotInfo;
import com.abc.service.AreaService;
import com.abc.service.PublicPraiseViewService;
import com.abc.service.ViewSpotInfoService;
import com.abc.util.PageUtils;
import com.abc.vo.ChineseMapVo;
import com.abc.vo.Json;
import com.abc.vo.PraiseAvarage;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description: 目的地景点信息控制器
 * @Date: 2019年04月30 16:28
 */

@RestController
@RequestMapping("webservice/destinationWebService")
public class DestinationWebService {
    private static final Logger log = LoggerFactory.getLogger(DestinationWebService.class);

    @Autowired
    private AreaService areaService;

    @Autowired
    ViewSpotInfoService viewSpotInfoService;

    @Autowired
    PublicPraiseViewService publicPraiseViewService;


    /**
     * 获取中国地图的数据
     * @return
     */
    @RequestMapping("/getChinaMapData")
    public Json getChinaMapData() {

        String oper = "getChinaMapData";
        Wrapper<Area> queryParams = new EntityWrapper<>();
        // 查询出所有的省级行政单位
        queryParams.eq("level", 1);
        List<Area> list = areaService.selectList(queryParams);
        List<ChineseMapVo> resultList = new ArrayList<>();
        if (null != list) {
            for (Area a : list) {
                ChineseMapVo cmv = new ChineseMapVo();
                cmv.setName(a.getShortName());
                cmv.setValue(a.getId());
                resultList.add(cmv);
            }
        }
        return Json.succ().data("mapData", resultList);

    }


    /**
     * 选择区域后生成推荐信息数据
     * @return
     */
    @PostMapping("/getIndexData")
    public Json getIndexData(@RequestBody String body) {
        String oper = "getMapIndexData";
        JSONObject json = JSON.parseObject(body);
        int addressId = json.getIntValue("addressId");
        Page<ViewSpotInfo> page = viewSpotInfoService.getTheIndexData(PageUtils.getPageParam(json), addressId);
        return Json.succ().data("page", page);
    }

    /**
     * 获取景点概要信息
     * @param body
     * @return
     */
    @PostMapping("/getViewSpotInfo")
    public Json getViewSpotInfo(@RequestBody String body) {
        String oper = "getViewSpotInfo";
        JSONObject json = JSON.parseObject(body);
        // 获取景点的id
        int id = json.getIntValue("spotId");
        ViewSpotInfo viewSpotInfo = viewSpotInfoService.selectOne(new EntityWrapper<ViewSpotInfo>().eq("id", id));
        if (null != viewSpotInfo) {
            // 把图片处理成数组
            viewSpotInfo.setPicArr(viewSpotInfo.getPicture().split(","));
        }
        return Json.succ().data("viewSpotInfo", viewSpotInfo);
    }


    /**
     * 获取景点的口碑评价信息
     * @param body
     * @return
     */
    @PostMapping("/getPraiseInfo")
    public Json getPraiseInfo(@RequestBody String body) {
        String oper = "getPraiseInfo";
        JSONObject json = JSON.parseObject(body);
        int spotId = json.getIntValue("spotId");
        PraiseAvarage praiseAvarage = publicPraiseViewService.computeAllSingleRate(spotId);
        return Json.succ().data("praiseAvarage", praiseAvarage);
    }


    /**
     * 获取用户的口碑评价列表
     * @param body
     * @return
     */
    @PostMapping("/getPraiseCommentList")
    public Json getPraiseCommentList(@RequestBody String body) {
        String oper = "getPraiseCommentList";
        JSONObject json = JSON.parseObject(body);
        int spotId = json.getIntValue("spotId");
        Page<PublicPraiseView> page = publicPraiseViewService.queryPublicPraiseByIndex(PageUtils.getPageParam(json), spotId);
        return Json.succ().data("page", page);
    }


    /**
     * 增加口碑评价信息
     * @param body
     * @return
     */
    @PostMapping("/addPraiseComment")
    public Json addPraiseComment(@RequestBody String body) {
        String oper = "addPraiseComment";
        PublicPraiseView publicPraiseView = JSON.parseObject(body, PublicPraiseView.class);
        if (StringUtils.isBlank(String.valueOf(publicPraiseView.getSpotId()))) {
            return Json.fail(oper, "景点id不能为空");
        }
        if (StringUtils.isBlank(String.valueOf(publicPraiseView.getCreateBy()))) {
            return Json.fail(oper, "口碑点评用户id不能为空");
        }
        publicPraiseView.setCreateDate(new Date());
        publicPraiseView.setType(1);
        boolean success = publicPraiseViewService.insert(publicPraiseView);
        return Json.result(oper, success);
    }

}
