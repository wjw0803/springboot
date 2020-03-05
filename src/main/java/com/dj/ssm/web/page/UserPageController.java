package com.dj.ssm.web.page;

import com.dj.ssm.pojo.User;
import com.dj.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserPageController {

    @Autowired
    private UserService userService;

    //去注册页面
    @RequestMapping("toAdd")
    public String toAdd() {
        return "/user/add";
    }

    //去登陆页面
    @RequestMapping("toLogin")
    public String toLogin() {
        return "/user/login";
    }

    //进入邮箱点击链接后去登陆页面
    @RequestMapping("upd2")
    public String upd2(User user) {
        userService.updateById(user);
        return "redirect:toLogin";
    }

    //去用户展示页面
    @RequestMapping("toShow")
    public String toShow() {
        return "/user/show";
    }

    //去升级vip页面
    @RequestMapping("toTopVip")
    public String toTopVip() {
        return "/user/top_vip";
    }

    //去借车页面
    @RequestMapping("toBorrowCar")
    public String toBorrowCar(){
        return "/user/borrow_car";
    }

    //去设置支付密码页面
    @RequestMapping("setPayPwd/{id}")
    public String setPayPwd(@PathVariable Integer id, Model model){
        model.addAttribute("id",id);
        return "/user/set_paypwd";
    }

    //去账户充值页面
    @RequestMapping("toChong")
    public String toChong(){
        return "/user/pay_user";
    }

    //退出登录
    @RequestMapping("exitLoginUser")
    public String exitLoginUser(){
        return "redirect:toLogin";
    }



}
