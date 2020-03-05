package com.dj.ssm.web;

import com.dj.ssm.pojo.Order;
import com.dj.ssm.pojo.ResultModel;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.OrderService;
import com.dj.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;


    //订单展示方法
    @PostMapping("show")
    public ResultModel<Object> show(HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            List<Order> list = orderService.findByUserId(user);
            return new ResultModel<>().success(list);
        }catch (Exception e){
            return new ResultModel<>().error(e.getMessage());
        }

    }

    //结束订单方法
    @PutMapping("endDing")
    public ResultModel<Object> endDing(Order order){
        try {
            orderService.updateById(order);
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }
    }


}
