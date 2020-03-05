package com.dj.ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.ssm.mapper.OrderMapper;
import com.dj.ssm.pojo.Order;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> findByUserId(User user) throws Exception {
        return orderMapper.findByUserId(user);
    }

    @Override
    public List<Order> findByUId(Integer userId) throws Exception {
        return orderMapper.findByUId(userId);
    }

    @Override
    public List<Order> findByCarId(Integer carId) throws Exception {
        return orderMapper.findByCarId(carId);
    }
}
