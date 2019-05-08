package com.abc.dao;

import com.abc.entity.ArtBody;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月06 15:34
 */
@Mapper
public interface ArtContentBodyMapper extends BaseMapper<ArtBody> {

    /**
     * 插入文章主体内容
     * @param artBody
     */
    void insertArticleBody(ArtBody artBody);
}
