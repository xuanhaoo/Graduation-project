package com.abc.service.impl;

import com.abc.dao.CustomerMapper;
import com.abc.entity.Customer;
import com.abc.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: liangxuanhao
 * @Description: 业务处理具体实现
 * @Date: 2019年02月27 16:07
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {


}
