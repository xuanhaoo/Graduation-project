package com.abc.service;

import com.abc.entity.ArtComment;
import com.abc.vo.CommentCountData;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月07 17:17
 */
public interface CommentService extends IService<ArtComment> {

    Page<ArtComment> queryContentComment(Page page, Integer artId);

    /**
     * 查询最新的10条
     * @return
     */
    List<ArtComment> queryTheLastComment();

    /**
     * 打包后台管理系统的的一些echart json数据
     * @return
     */
    CommentCountData packageData();


    /**
     * 前台社区获取用户评论信息
     * @param page
     * @param artId
     * @return
     */
    Page<ArtComment> getCommentByContentID(Page page, Integer artId);

}
