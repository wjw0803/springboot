package com.dj.ssm.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order/")
public class OrderPageController {

    //去订单展示页面
    @RequestMapping("toShow")
    public String toShow(){
        return "/order/show";
    }



}
