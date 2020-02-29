package com.dj.ssm.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.ssm.pojo.ResultModel;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.UserService;
import com.dj.ssm.utils.JavaEmailUtils;
import com.dj.ssm.utils.PasswordSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    //注册方法
    @PostMapping("add")
    public ResultModel<Object> add(User user, HttpServletRequest request){
        try {
            //不能为空
            if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPwd()) || StringUtils.isEmpty(
                    user.getEmail()
            ) || StringUtils.isEmpty(user.getPhone())) {
                return new ResultModel<>().error("不能有一项为空");
            }
            //用户名去重
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name",user.getUserName());
            User user1 = userService.getOne(queryWrapper);
            if(user1 != null){
                return new ResultModel<>().error("用户名不可重复");
            }

            /**
             * md5 注册： md5(md5(明文)+盐）
             */
            //根据时间戳加入盐值
            String salt = PasswordSecurityUtil.generateSalt();
            user.setSalt(salt);
            //把前台加密过的密码再次把他们md5加密一下
            String password = PasswordSecurityUtil.generatePassword(user.getPwd(), salt);
            user.setPwd(password);
            userService.save(user);
            //用户注册完成后给注册邮箱发送链接
            String content = "<a href = 'http://192.168.47.1:8080"+request.getContextPath()+"/user/upd2?id="+user.getId()+"&isActive=1'>点此激活</a>";
            JavaEmailUtils.sendEmail(user.getEmail(),"用户激活",content);
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }
    }


}
