package com.abc.service;

import com.abc.entity.ArtContent;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:54
 */
public interface ContentService extends IService<ArtContent> {

    Page<ArtContent> queryAllListByLeft(Page page, Integer id, Integer status);
}
