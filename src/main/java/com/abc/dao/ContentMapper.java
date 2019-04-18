package com.abc.dao;

import com.abc.entity.ArtContent;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:45
 */
@Mapper
public interface ContentMapper extends BaseMapper<ArtContent> {

    List<ArtContent> queryAllListByLeftJoin(Pagination page, @Param("subjectId")Integer subjectId, @Param("status")Integer status);
}
