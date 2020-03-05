package com.dj.ssm.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.ssm.pojo.Resource;
import com.dj.ssm.pojo.ResultModel;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.ResourceService;
import com.dj.ssm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义Realm
 */

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建简单授权信息
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
//        simpleAuthorInfo.addStringPermission("resource:add");
        //获取当前登录用户的信息
        Session session = SecurityUtils.getSubject().getSession();
        List<Resource> resourceList = (List<Resource>) session.getAttribute("resourceList");
        for (Resource resource: resourceList) {
            simpleAuthorInfo.addStringPermission(resource.getUrl());
        }
        return simpleAuthorInfo;
    }


    /**
     *  认证--登陆
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 得到用户名
        String username = (String) authenticationToken.getPrincipal();
        // 得到密码
        String password = new String((char[]) authenticationToken.getCredentials());

        try {

            QueryWrapper<User> queryWrapper = new QueryWrapper();
            queryWrapper.or(i -> i.eq("user_name", username)
                    .or().eq("email", username)
                    .or().eq("phone", username));
            queryWrapper.eq("pwd", password);
            User user1 = userService.getOne(queryWrapper);
            //如果查询出来是Null则用户名密码错误
            if(user1 == null){
                throw new AccountException("用户名密码错误");
            }
            //用户未激活不可以登陆
            if("0".equals(user1.getIsActive())){
                throw new LockedAccountException("用户未激活,请激活后登陆");
            }
            Session session = SecurityUtils.getSubject().getSession();
            //存session用户
            session.setAttribute("user",user1);
            //登陆成功时将对应用户的资源权限存入session中
            session.setAttribute("resourceList",resourceService.findUserResource(user1.getId()));
        }catch (Exception e){
            throw new AccountException(e.getMessage());
        }
        //返回认证信息
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
