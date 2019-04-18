package com.abc.service.impl;

import com.abc.dao.SubjectMapper;
import com.abc.entity.ArtSubject;
import com.abc.service.SubjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:53
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, ArtSubject> implements SubjectService {
}
