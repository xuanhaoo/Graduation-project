package com.abc.service.impl;

import com.abc.dao.CommentMapper;
import com.abc.entity.ArtComment;
import com.abc.entity.ArtSubject;
import com.abc.service.CommentService;
import com.abc.service.SubjectService;
import com.abc.vo.CommentCountData;
import com.abc.vo.EchartsJson;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月07 17:18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, ArtComment> implements CommentService {


    @Autowired
    private SubjectService subjectService;

    @Override
    public Page<ArtComment> queryContentComment(Page page, Integer artId) {
        return page.setRecords(baseMapper.queryCommentByContent(page, artId));
    }

    @Override
    public List<ArtComment> queryTheLastComment() {
        // 直接返回即可
        return baseMapper.queryTheLastComment();
    }

    @Override
    public CommentCountData packageData() {
        Integer totalCount = selectCount(new EntityWrapper<>());
        if (null == totalCount) {
            totalCount = 0;
        }
        List<ArtSubject> subjectList = subjectService.selectList(new EntityWrapper<>());
        List<EchartsJson> echartsJsonList = new ArrayList<>();
        // 分别计数，统计
        if (null != subjectList && !subjectList.isEmpty()) {
            int index = 0;
            for (ArtSubject as : subjectList) {
                EchartsJson echartsJson = new EchartsJson();
                echartsJson.setName(as.getName());
                Integer tmpCount = baseMapper.packageData(as.getId());
                if (null == tmpCount) {
                    tmpCount = 0;
                }
                echartsJson.setValue(tmpCount);
                echartsJsonList.add(echartsJson);
            }
        }
        CommentCountData commentCountData = new CommentCountData();
        commentCountData.setCountNum(totalCount);
        commentCountData.setEchartsJsonList(echartsJsonList);
        return commentCountData;
    }

    @Override
    public Page<ArtComment> getCommentByContentID(Page page, Integer artId) {
        return page.setRecords(baseMapper.getCommentByContentId(page, artId));
    }
}
