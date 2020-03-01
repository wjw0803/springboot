package com.dj.ssm.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.ssm.pojo.ResultModel;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.UserService;
import com.dj.ssm.utils.JavaEmailUtils;
import com.dj.ssm.utils.PasswordSecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    //登陆方法
    @PostMapping("login")
    public ResultModel<Object> login(User user){
        try {
            /**
             * 登录： md5(明文)+盐
             */
            //将对应的加密盐查出来，这个盐是注册根据时间戳生成的，只能从数据库里取值
            QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_name",user.getUserName());
            User user2 = userService.getOne(queryWrapper1);
            //解密
            //把已经md5一次的密码，还有盐，再加密
            String newPwd = PasswordSecurityUtil.generatePassword(user.getPwd(), user2.getSalt());

          /*  QueryWrapper<User> queryWrapper = new QueryWrapper();
            queryWrapper.or(i -> i.eq("user_name", user.getUserName())
                    .or().eq("email", user.getUserName())
                    .or().eq("phone", user.getUserName()));
            queryWrapper.eq("pwd", newPwd);
            User user1 = userService.getOne(queryWrapper);
            //如果查询出来是Null则用户名密码错误
            if(user1 == null){
                return new ResultModel<>().error("用户名密码错误");
            }
            //用户未激活不可以登陆
            if("0".equals(user1.getIsActive())){
                return new ResultModel<>().error("用户未激活,请激活后登陆");
            }
            */
            //shiro登陆方式
            Subject subject = SecurityUtils.getSubject(); //得到Subject
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),newPwd);
            subject.login(token);//访问shiro的认证器
            return new ResultModel<>().success();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error(e.getMessage());
        }

    }

    @PostMapping("show")
    public ResultModel<Object> show(HttpSession session){
        try {
            /* 0:普通用户1:vip2:管理员*/
            User user = (User) session.getAttribute("user");
            //普通用户和管理员展示只可以看到自己的
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if(user.getLevel() == 0 || user.getLevel() == 1){
                queryWrapper.eq("id",user.getId());
            }
            List<User> list = userService.list(queryWrapper);
            return new ResultModel<>().success(list);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error("有异常"+e.getMessage());
        }
    }



}
