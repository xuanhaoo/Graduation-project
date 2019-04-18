package com.abc.service;

import com.abc.entity.ViewSpotInfo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:56
 */
public interface ViewSpotInfoService extends IService<ViewSpotInfo> {


    Page<ViewSpotInfo> queryAllLeftJoin(Page page, String name);
}
