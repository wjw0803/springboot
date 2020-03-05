package com.dj.ssm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.ssm.pojo.Order;
import com.dj.ssm.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public interface OrderService extends IService<Order> {


    List<Order> findByUserId(User user) throws Exception;

    /*查看该用户订单表中存在几条租车记录*/
    List<Order> findByUId(Integer userId) throws Exception;

    //根据车id判断是否有订单在使用该车
    List<Order> findByCarId(Integer carId) throws Exception;
}
