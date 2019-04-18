package com.abc.dao;

import com.abc.entity.ArtSubject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: liangxuanhao
 * @Description: 主题社区管理
 * @Date: 2019年03月01 14:43
 */
@Mapper
public interface SubjectMapper extends BaseMapper<ArtSubject> {

}
