package com.abc.service;

import com.abc.entity.PublicPraiseView;
import com.abc.vo.PraiseAvarage;
import com.abc.vo.PublicEchartsJson;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月08 13:09
 */
public interface PublicPraiseViewService extends IService<PublicPraiseView> {

    /**
     * 查询所有的点评
     * @param page
     * @param spotKeyWord
     * @return
     */
    Page<PublicPraiseView> queryAllPublicPraise(Page page, String spotKeyWord);


    /**
     * 生成数据分析结果：最近时间内的点评数集合
     * @return
     */
    PublicEchartsJson analysisPublicData();


    Page<PublicPraiseView> queryPublicPraiseBySpot(Page page, Integer spotId);


    /**
     * 计算某一个景点下的平均分
     * @param spotId
     * @return
     */
    PraiseAvarage computeAllSingleRate(Integer spotId);

    /**
     * 获取某个景点的所有的口碑评价，并且各自计算各个用户的平均综合分
     * @param page
     * @param spotId
     * @return
     */
    Page<PublicPraiseView> queryPublicPraiseByIndex(Page page, Integer spotId);

}
