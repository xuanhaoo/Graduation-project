package com.abc.service.impl;

import com.abc.dao.PublicPraiseViewMapper;
import com.abc.entity.PublicPraiseView;
import com.abc.service.PublicPraiseViewService;
import com.abc.vo.PraiseAvarage;
import com.abc.vo.PublicEchartsJson;
import com.abc.webservice.SharePugWebService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月08 13:10
 */
@Service
public class PublicPraiseViewServiceImpl extends ServiceImpl<PublicPraiseViewMapper, PublicPraiseView> implements
        PublicPraiseViewService {

    private static final Logger log = LoggerFactory.getLogger(PublicPraiseViewServiceImpl.class);


    @Override
    public Page<PublicPraiseView> queryAllPublicPraise(Page page, String spotKeyWord) {
        return page.setRecords(baseMapper.queryPublicPraiseByLeftJoin(page, spotKeyWord));
    }

    @Override
    public PublicEchartsJson analysisPublicData() {
        // 计算当前时间的前7天
        Calendar no = Calendar.getInstance();
        no.setTime(new Date());
        no.set(Calendar.DATE, no.get(Calendar.DATE) - 7);
        Date setTime = no.getTime();
        List<PublicEchartsJson> list = baseMapper.analysisPublicPraiseData(setTime);
        List<String> dateString = new ArrayList<>();
        List<Integer> valueArr = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            for (PublicEchartsJson p : list) {
                dateString.add(p.getDateStage());
                valueArr.add(p.getTotalSum());
            }
        }
        PublicEchartsJson publicEchartsJson = new PublicEchartsJson();
        publicEchartsJson.setDateString(dateString);
        publicEchartsJson.setValueArr(valueArr);
        return publicEchartsJson;
    }

    @Override
    public Page<PublicPraiseView> queryPublicPraiseBySpot(Page page, Integer spotId) {
        return page.setRecords(baseMapper.queryPublicBySpotId(page, spotId));
    }

    @Override
    public PraiseAvarage computeAllSingleRate(Integer spotId) {
        return baseMapper.computeAllSingleRate(spotId);
    }

    @Override
    public Page<PublicPraiseView> queryPublicPraiseByIndex(Page page, Integer spotId) {
        List<PublicPraiseView> list = baseMapper.queryPublicPraiseByIndex(page, spotId);
        List<PublicPraiseView> resultList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            for (PublicPraiseView ppv : list) {
                float sum = ppv.getFacilities() + ppv.getManage() + ppv.getPassengerFlow() + ppv.getScenery()
                        + ppv.getDiet() + ppv.getTransport() + ppv.getService();
                float avarageRateTmp = sum / 7;
//                float avarageRate = (float)(Math.round(avarageRateTmp*100)/100);
                BigDecimal b  =  new  BigDecimal(avarageRateTmp);
                float avarageRate = b.setScale(1,  BigDecimal.ROUND_HALF_UP).floatValue();
                ppv.setAvarageRate(avarageRate);
                resultList.add(ppv);
            }
        }
        page.setRecords(resultList);

        return page;
    }
}
