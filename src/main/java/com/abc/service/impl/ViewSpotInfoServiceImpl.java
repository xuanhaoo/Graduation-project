package com.abc.service.impl;

import com.abc.dao.ViewSpotInfoMapper;
import com.abc.entity.ViewSpotInfo;
import com.abc.service.ViewSpotInfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:57
 */
@Service
public class ViewSpotInfoServiceImpl extends ServiceImpl<ViewSpotInfoMapper, ViewSpotInfo> implements ViewSpotInfoService {
    @Override
    public Page<ViewSpotInfo> queryAllLeftJoin(Page page, String name) {
        return page.setRecords(baseMapper.queryAllLeftList(page, name));
    }
}
