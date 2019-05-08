package com.abc.service.impl;

import com.abc.dao.ArtContentBodyMapper;
import com.abc.entity.ArtBody;
import com.abc.service.ArtContentBodyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月06 15:41
 */
@Service
public class ArtContentBodyServiceImpl extends ServiceImpl<ArtContentBodyMapper, ArtBody>  implements ArtContentBodyService {



    @Override
    public void insertBody(ArtBody artBody) {
        baseMapper.insertArticleBody(artBody);
    }
}
