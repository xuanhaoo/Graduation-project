package com.abc.service;

import com.abc.entity.ArtBody;
import com.baomidou.mybatisplus.service.IService;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月06 15:40
 */
public interface ArtContentBodyService extends IService<ArtBody> {

    /**
     * 插入文章主题内容，并返回生成的id
     * @param artBody
     * @return
     */
    void insertBody(ArtBody artBody);
}
