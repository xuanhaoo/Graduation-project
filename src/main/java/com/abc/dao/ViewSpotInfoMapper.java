package com.abc.dao;

import com.abc.entity.ViewSpotInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月01 14:50
 */
@Mapper
public interface ViewSpotInfoMapper extends BaseMapper<ViewSpotInfo> {


    /**
     * 获取所有数据的时候一并获取地址信息
     * @param page
     * @param name
     * @return
     */
    List<ViewSpotInfo> queryAllLeftList(Pagination page, @Param("name")String name);
}
