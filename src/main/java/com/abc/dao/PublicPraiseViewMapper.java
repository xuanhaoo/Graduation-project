package com.abc.dao;

import com.abc.entity.PublicPraiseView;
import com.abc.vo.PraiseAvarage;
import com.abc.vo.PublicEchartsJson;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月08 13:08
 */
public interface PublicPraiseViewMapper extends BaseMapper<PublicPraiseView> {

    List<PublicPraiseView> queryPublicPraiseByLeftJoin(Pagination page, @Param("spotKeyWord")String spotKeyWord);


    List<PublicEchartsJson> analysisPublicPraiseData(@Param("setTime")Date setTime);

    List<PublicPraiseView> queryPublicBySpotId(Pagination page, @Param("spotId") Integer spotId);

    PraiseAvarage computeAllSingleRate(@Param("spotId") Integer spotId);

    List<PublicPraiseView> queryPublicPraiseByIndex(Pagination page, @Param("spotId")Integer spotId);
}
