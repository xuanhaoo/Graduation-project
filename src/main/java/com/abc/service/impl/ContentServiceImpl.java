package com.abc.service.impl;

import com.abc.dao.ContentMapper;
import com.abc.entity.ArtContent;
import com.abc.service.ContentService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:55
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, ArtContent> implements ContentService {
    @Override
    public Page<ArtContent> queryAllListByLeft(Page page, Integer subjectId, Integer status) {
        return page.setRecords(baseMapper.queryAllListByLeftJoin(page, subjectId, status));
    }
}
