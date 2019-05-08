package com.abc.service.impl;

import com.abc.dao.PublicPraiseViewMapper;
import com.abc.dao.ViewSpotInfoMapper;
import com.abc.entity.PublicPraiseView;
import com.abc.entity.ViewSpotInfo;
import com.abc.service.PublicPraiseViewService;
import com.abc.service.ViewSpotInfoService;
import com.abc.util.PinyinUtil;
import com.abc.vo.IndexViewSpotVo;
import com.abc.vo.PraiseAvarage;
import com.abc.webservice.SharePugWebService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:57
 */
@Service
public class ViewSpotInfoServiceImpl extends ServiceImpl<ViewSpotInfoMapper, ViewSpotInfo> implements ViewSpotInfoService {
    private static final Logger log = LoggerFactory.getLogger(ViewSpotInfoServiceImpl.class);

    @Autowired
    PublicPraiseViewService publicPraiseViewService;

    @Autowired
    PublicPraiseViewMapper publicPraiseViewMapper;

    @Override
    public Page<ViewSpotInfo> queryAllLeftJoin(Page page, String name) {
        return page.setRecords(baseMapper.queryAllLeftList(page, name));
    }

    @Override
    public Page<ViewSpotInfo> getTheIndexData(Page page, int addressId) {
        // 先查询出所有的景点信息，然后查询出该景点下的口碑评价列表并且计算各单项的平均分，
        // 然后将平均分从小到大排序，然后在[4.0-4.5]中随机选择一个数，二分查找这个的数的脚标，计算比这个数大的百分比
        List<ViewSpotInfo> originList = new ArrayList<>();
        List<ViewSpotInfo> resultList = new ArrayList<>();
        if (addressId == 0) {
            // address == 0 说明是首页加载，还未选择地图数据，直接查找全部目的地
            originList = baseMapper.queryAllLeftListByAddressId(page, 0);
        } else {
            originList = baseMapper.queryAllLeftListByAddressId(page, addressId);
        }
        if (null != originList && !originList.isEmpty()) {
            for (ViewSpotInfo vsi : originList) {
                int tmpSpotId = vsi.getId();
                List<PublicPraiseView> publicPraiseViewList = publicPraiseViewService.selectList(new EntityWrapper<PublicPraiseView>()
                .eq("spot_id", tmpSpotId));
                // 排序平均分
                String[] resultArr = computeRate(publicPraiseViewList);
                vsi.setPercent(resultArr[0]);
                vsi.setPercentValue(resultArr[1]);
                // 设置标签数组
                vsi.setTagsArr(vsi.getTags().split(","));
                // 设置图片数组
                vsi.setPicArr(vsi.getPicture().split(","));
                resultList.add(vsi);
            }
        }
        page.setRecords(resultList);
        return page;
    }

    @Override
    public List<IndexViewSpotVo> indexRecommendList() {
        PinyinUtil pinyinUtil = new PinyinUtil();
        // 生成景点推荐直接根据风景分数
        List<ViewSpotInfo> originList = baseMapper.queryAllIndexList();
        List<IndexViewSpotVo> firstList = new ArrayList<>();
        for (ViewSpotInfo vsi : originList) {
            int tmpSpotId = vsi.getId();
            PraiseAvarage praiseAvarage = publicPraiseViewMapper.computeAllSingleRate(tmpSpotId);
            double  avarageSum = 0.00;
            if (null != praiseAvarage) {
                float sum = praiseAvarage.getFacilities() + praiseAvarage.getDiet() + praiseAvarage.getManage() + praiseAvarage.getPassengerFlow() + praiseAvarage.getScenery() + praiseAvarage.getService() + praiseAvarage.getTransport();
                float avarageSumTmp = sum / 7;

                BigDecimal b  =  new  BigDecimal(avarageSumTmp);
                avarageSum = b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
            }
            IndexViewSpotVo indexViewSpotVo = new IndexViewSpotVo();

            indexViewSpotVo.setAddressId(vsi.getId());
            indexViewSpotVo.setSpotName(vsi.getName());
            indexViewSpotVo.setAddressName(vsi.getAreaThreeName());
            String[] picTemp = vsi.getPicture().split(",");
            indexViewSpotVo.setPic(picTemp[0]);
            indexViewSpotVo.setWeight(avarageSum);
            // 设置城市拼音
            indexViewSpotVo.setAddressNamePinyin(pinyinUtil.changeToTonePinYin(indexViewSpotVo.getAddressName()));
            firstList.add(indexViewSpotVo);
        }
        firstList = firstList.stream().sorted(Comparator.comparing(IndexViewSpotVo::getWeight).reversed()).collect(Collectors.toList());
        List<IndexViewSpotVo> resultList = firstList.subList(0,4);
        return resultList;
    }


    /**
     * 计算比率
     * @param publicPraiseViewList
     * @return
     */
    private String[] computeRate(List<PublicPraiseView> publicPraiseViewList) {
        DecimalFormat df = new DecimalFormat("0.0%");
        String[] result = new String[2];
        Random random = new Random();
        if (null != publicPraiseViewList && !publicPraiseViewList.isEmpty()) {
           List<Float> avarageList = new ArrayList<>();
           for (PublicPraiseView ppv : publicPraiseViewList) {
               float sum = ppv.getFacilities() + ppv.getManage() + ppv.getPassengerFlow() + ppv.getScenery()
                       + ppv.getDiet() + ppv.getTransport() + ppv.getService();
               float avarageRateTmp = sum / 7;

               BigDecimal b  =  new  BigDecimal(avarageRateTmp);
               float  avarageRate = b.setScale(1,  BigDecimal.ROUND_HALF_UP).floatValue();
               log.info("sum: {}，avarage：{}", sum, avarageRate);
               ppv.setAvarageRate(avarageRate);
               avarageList.add(ppv.getAvarageRate());
           }
           Float[] avarageArr = avarageList.toArray(new Float[avarageList.size()]);
           Arrays.sort(avarageArr);  // 排序，确保查找准确
            // 生成[4.0, 4.5]之间的随机数作为比较值
            float indexRateTmp = (float) (4.0 + random.nextFloat() * (4.5 - 4.0));
            BigDecimal b2  =  new  BigDecimal(indexRateTmp);
            float  indexRate = b2.setScale(1,  BigDecimal.ROUND_HALF_UP).floatValue();
            int le = avarageArr.length;
            int maxCount = 0;
            for (int i = 0; i < le; i++) {
                if (avarageArr[i] > indexRate) {
                    maxCount = maxCount + 1;
                    break;
                }
            }
            // 计算比率
            float rate = (float) maxCount / (float) le;
            log.info("indexRate:{}, rate: {}", indexRate,rate);
            String resultRate = df.format(rate);
            result[0] = resultRate;
            result[1] = Float.toString(indexRate);
        } else {
            result[0] = "0%";
            result[1] = "0.0";
        }
        return result;

    }
}
