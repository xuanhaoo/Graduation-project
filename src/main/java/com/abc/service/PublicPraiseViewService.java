package com.abc.service;

import com.abc.entity.PublicPraiseView;
import com.abc.vo.PublicEchartsJson;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

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
}
