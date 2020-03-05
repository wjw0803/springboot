package com.dj.ssm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.ssm.pojo.Order;
import com.dj.ssm.pojo.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<Order> findByUserId(User user) throws DataAccessException;

    /*查看该用户订单表中存在几条租车记录*/
    List<Order> findByUId(Integer userId) throws DataAccessException;

    //根据车id判断是否有订单在使用该车
    List<Order> findByCarId(Integer carId) throws  DataAccessException;

}
