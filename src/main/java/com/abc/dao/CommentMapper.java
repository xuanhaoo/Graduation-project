package com.abc.dao;

import com.abc.entity.ArtComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月07 17:06
 */
@Mapper
public interface CommentMapper extends BaseMapper<ArtComment> {
    List<ArtComment> queryCommentByContent(Pagination page, @Param("artId")Integer artId);


    List<ArtComment> queryTheLastComment();

    Integer packageData(@Param("subjectId")Integer subjectId);

    /**
     * 前端社区查询文章评论
     * @param page
     * @param artId
     * @return
     */
    List<ArtComment> getCommentByContentId(Pagination page, @Param("artId")Integer artId);
}
