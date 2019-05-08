package com.abc.service;

import com.abc.entity.ViewSpotInfo;
import com.abc.vo.IndexViewSpotVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:56
 */
public interface ViewSpotInfoService extends IService<ViewSpotInfo> {


    Page<ViewSpotInfo> queryAllLeftJoin(Page page, String name);


    /**
     * 前台社区查询地图目的地景点的推荐数据
     * @param page
     * @param addressId
     * @return
     */
    Page<ViewSpotInfo> getTheIndexData(Page page, int addressId);


    /**
     * 生成首页目的地推荐列表
     * @return
     */
    List<IndexViewSpotVo> indexRecommendList();
}
