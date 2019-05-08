package com.abc.service.impl;

import com.abc.dao.ContentMapper;
import com.abc.entity.ArtBody;
import com.abc.entity.ArtContent;
import com.abc.service.ArtContentBodyService;
import com.abc.service.ContentService;
import com.abc.util.PinyinUtil;
import com.abc.vo.IndexPugVo;
import com.abc.vo.Json;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:55
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, ArtContent> implements ContentService {


    @Autowired
    private ArtContentBodyService artContentBodyService;

    @Override
    public Page<ArtContent> queryAllListByLeft(Page page, Integer subjectId, Integer status) {
        return page.setRecords(baseMapper.queryAllListByLeftJoin(page, subjectId, status));
    }

    @Override
    public Json pubContent(ArtContent artContent) {
        ArtBody artBody = new ArtBody();
        artBody.setContentHtml(artContent.getContentVo());
        artBody.setContent("");
        artContentBodyService.insertBody(artBody);
        // 将生成的id保存到artcontent
        artContent.setBodyId(artBody.getId());
        // 设置基本参数 初始化
        artContent.setWeight(1); // 1 不置顶
        artContent.setStatus(1); // 1审核中
        artContent.setCommentCount(0);
        artContent.setViewCount(0);
        artContent.setAreaTypeOne(0);
        artContent.setCreateDate(new Date());
        artContent.setUpdateDate(new Date());
        baseMapper.insertContent(artContent);
        return new Json().succ();
    }

    @Override
    public List<IndexPugVo> recommendIndexPug() {
        /**
         * 生成推荐足迹列表：按照足迹热度推荐：
         * 热度H = V（浏览量） * 60% + C（评论量）* 40%
         * 然后根据热度值排序，截取前8个
         */
        PinyinUtil pinyinUtil = new PinyinUtil();
        // 查询全部足迹列表
        List<ArtContent> originList = baseMapper.queryAllListAndCityName(1);
        List<IndexPugVo> firstList = new ArrayList<>();
        if (null != originList && !originList.isEmpty()) {
            for (ArtContent ac : originList) {
                IndexPugVo indexPugVo = new IndexPugVo();
                indexPugVo.setContentId(ac.getId());
                indexPugVo.setTitle(ac.getTitle());
                indexPugVo.setTime(ac.getCreateDate());
                indexPugVo.setPicSrc(ac.getAvatar());
                // 在这里用authorName暂时代替连接查询后城市名
                indexPugVo.setCityName(ac.getAuthorName());
                indexPugVo.setCityNamePinyin(pinyinUtil.changeToTonePinYin(indexPugVo.getCityName()));
                // 计算权重
                Double weight = ac.getCommentCount() * 0.4 + ac.getViewCount() * 0.6;
                indexPugVo.setWeight(weight);
                firstList.add(indexPugVo);
            }
        }
        // 流排序，按照
        firstList = firstList.stream().sorted(Comparator.comparing(IndexPugVo::getWeight).reversed()).collect(Collectors.toList());
        // 因为至少要求8篇，所以足迹至少8篇
        List<IndexPugVo> resultList = firstList.subList(0, 8);
        return resultList;
    }
}
