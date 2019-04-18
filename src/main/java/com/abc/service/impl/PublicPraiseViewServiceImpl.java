package com.abc.service.impl;

import com.abc.dao.PublicPraiseViewMapper;
import com.abc.entity.PublicPraiseView;
import com.abc.service.PublicPraiseViewService;
import com.abc.vo.PublicEchartsJson;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
