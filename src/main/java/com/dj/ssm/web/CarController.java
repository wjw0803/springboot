package com.dj.ssm.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.ssm.pojo.Car;
import com.dj.ssm.pojo.Order;
import com.dj.ssm.pojo.ResultModel;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.CarService;
import com.dj.ssm.service.OrderService;
import com.dj.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/car/")
public class CarController {


    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    //汽车展示方法
    @PostMapping("show")
    public ResultModel<Object> show(String carName, Integer carType,String minMoney, String maxMoney, HttpSession session){
        User user = (User) session.getAttribute("user");
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        //车名模糊查
        if(!StringUtils.isEmpty(carName)){
            queryWrapper.like("car_name",carName);
        }
        if(carType !=  null){
            queryWrapper.eq("car_type",carType);
        }
        //普通用户和vip展示已上架的汽车和未删除的汽车,管理员展示全部(删除的汽车不展示)
        if(user.getLevel() == 0 || user.getLevel() == 1){
            queryWrapper.eq("status",1);
        }
        //日租金范围查询
        if(!StringUtils.isEmpty(minMoney)){
            queryWrapper.gt("money",minMoney);
        }
        if(!StringUtils.isEmpty(maxMoney)){
            queryWrapper.lt("money",maxMoney);
        }
        queryWrapper.eq("is_del",0);
        List<Car> list = carService.list(queryWrapper);
        return new ResultModel<>().success(list);
    }

    //借车方法
    @PostMapping("borCar")                                      //页面输入的支付密码
    public ResultModel<Object> borCar(Order order, Double money, String MiMa, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            //租车记录表中改用户存在几条订单
            List<Order> orderList = orderService.findByUId(user.getId());
            for(Order order1 :orderList){
                if(order1.getCarStatus() == 1){
                    return new ResultModel<>().error("您已经有一条进行的订单,请结束上次订单后继续使用");
                }
            }
            if(StringUtils.isEmpty(order.getBorrowDay()) || StringUtils.isEmpty(MiMa)){
                return new ResultModel<>().error("借车天数或支付密码不可为空");
            }
            if(user.getPayPwd() == null){
                return new ResultModel<>().error("您还未设置支付密码请进入首页设置");
            }
            //如果输入的支付密码和设置的支付密码不一致则提示输入错误
            if(!MiMa.equals(user.getPayPwd())){
                return new ResultModel<>().error("支付密码错误哦请重新输入");
            }
            //总租车费用= 日租金 * 租车天数
            double sumMoney = money * order.getBorrowDay();
            if(user.getAccountMoney() < sumMoney){
                return new ResultModel<>().error("账户余额不足请及时充值");
            }
            order.setUserId(user.getId());
            Double payMoney;
            //如果用户是普通用户的话租车费用不变,vip用户打0.8折
            if(user.getLevel() == 0){
                payMoney =money * order.getBorrowDay();
            }
            payMoney =  0.8 * money * order.getBorrowDay();
            order.setPayMoney(payMoney);
            order.setCarStatus(1);
            orderService.save(order);
            //租车成功后账户余额减少对应金额
            user.setAccountMoney(user.getAccountMoney()-sumMoney);
            userService.updateById(user);
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }

    }

    //批量新增车辆方法
    @RequestMapping("addCars")
    public ResultModel<Object> addCars(Car car){
        try {
            List<Car> cars = new ArrayList<>();
            for(Car car2 : car.getCarList()){
                if(StringUtils.isEmpty(car2.getCarName())){
                    continue;
                }
                cars.add(car2);
            }
            carService.addList(cars);
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }
    }

    //汽车上下架
    @PutMapping("updTorDown")
    public ResultModel<Object> updTorDown(Car car){
        try {
            //根据要进行上下架操作的汽车的id判断该车是否已经有订单正在使用
            List<Order> orderList = orderService.findByCarId(car.getId());
            if(orderList != null && orderList.size()>0){
                for(Order order :orderList){
                    if(order.getCarStatus() == 1){
                        return new ResultModel<>().error("用户"+order.getUserNameShow()+"正在使用该辆车,请等待订单结束后进行下架操作");
                    }
                }
            }
            carService.updateById(car);
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }
    }

    //逻辑删除汽车
    @PutMapping("delCar")
    public ResultModel<Object> delCar(Integer[] ids, Integer isDel){
        try {
            //根据要进行删除操作的汽车的id判断该车是否已经有订单正在使用
            for(int i = 0 ; i < ids.length; i++){
                List<Order> orderList = orderService.findByCarId(ids[i]);
                if(orderList != null && orderList.size()>0){
                    for(Order order :orderList){
                        if(order.getCarStatus() == 1){
                            return new ResultModel<>().error("用户"+order.getUserNameShow()+"正在使用这辆"+order.getCarNameShow()+"车,请等待订单结束后进行删除操作");
                        }
                    }
                }
            }
            carService.updCarIsDel(ids,isDel);
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }
    }


}
