package com.dj.ssm.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResultModel<Object> show(HttpSession session, String phone, String isActive, Integer pageNo){
        try {
            Map<String, Object> resultMap = new HashMap<>();
            //Mp地分页对象
            IPage<User> page = new Page<>(pageNo, 2);
            /* 0:普通用户1:vip2:管理员*/
            User user = (User) session.getAttribute("user");
            //如果登陆用户是vip但是它的vip时间超时则修改它的level
            if(user.getLevel() == 1 && System.currentTimeMillis() > user.getVipVolidateTime().getTime()){
                user.setLevel(0);
                user.setId(user.getId());
                userService.updateById(user);
            }
            //普通用户和管理员展示只可以看到自己的
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if(user.getLevel() == 0 || user.getLevel() == 1){
                queryWrapper.eq("id",user.getId());
            }
            //手机号模糊查
            if(!StringUtils.isEmpty(phone)){
                queryWrapper.like("phone",phone);
            }
            //单选按钮查询
            if(!StringUtils.isEmpty(isActive)){
                queryWrapper.eq("is_active",isActive);
            }
            List<User> list = userService.list(queryWrapper);
            IPage<User> allRoles = userService.getAllRoles((Page<User>) page, queryWrapper);
            return new ResultModel<>().success(allRoles);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultModel<>().error("有异常"+e.getMessage());
        }
    }

    //购买vip方法
    @PutMapping("buyVip")
    public ResultModel<Object> buyVip(User user,String code, HttpSession session){
        try {
            //判断非空
            if(StringUtils.isEmpty(user.getVipType()) || StringUtils.isEmpty(code) ){
                return new ResultModel<>().error("vip类型或图形验证码不可为空");
            }
            User user1 = (User) session.getAttribute("user");
            Calendar calendar = Calendar.getInstance();
            //如果选则的日vip则vip失效时间加1天
            if(user.getVipType() == 0){
                calendar.add(Calendar.DATE,1);
            }
            //如果选则的是月vip则vip失效时间加1月
            if(user.getVipType() == 1){
                calendar.add(Calendar.MONTH,1);
            }
            //如果选则的是年vip则vip失效时间加1年
            if(user.getVipType() == 2){
                calendar.add(Calendar.YEAR,1);
            }
            user.setVipVolidateTime(calendar.getTime());
            user.setId(user1.getId());
            user.setLevel(1);
            userService.updateById(user);
            return new ResultModel<>().success();
        }catch (Exception e){
            return new ResultModel<>().error(e.getMessage());
        }

    }


    //volidate注册用户名去重
    @RequestMapping("findByName")
    public Boolean findByName(String userName){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user = userService.getOne(queryWrapper);
        return user == null ? true : false;
    }


}
