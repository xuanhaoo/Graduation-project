package com.abc.service;

import com.abc.entity.ArtContent;
import com.abc.vo.IndexPugVo;
import com.abc.vo.Json;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:54
 */
public interface ContentService extends IService<ArtContent> {

    Page<ArtContent> queryAllListByLeft(Page page, Integer id, Integer status);

    /**
     * 发表文章
     * @param artContent
     * @return
     */
    Json pubContent(ArtContent artContent);


    /**
     * 推荐首页足迹列表
     * @return
     */
    List<IndexPugVo> recommendIndexPug();
}
