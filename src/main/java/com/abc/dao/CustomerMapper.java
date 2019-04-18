package com.abc.dao;

import com.abc.entity.Customer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年02月27 16:08
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}
