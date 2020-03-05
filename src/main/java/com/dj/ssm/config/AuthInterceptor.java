package com.dj.ssm.config;


import com.dj.ssm.pojo.Resource;
import com.dj.ssm.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 权限拦截器
 */

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前请求是否有权限
        //当前请求是否在当前登陆用户的资源列表中
        HttpSession session = request.getSession();
      /*  User user = (User) session.getAttribute("user");
        //当前登陆用户的资源
        List<Resource> resourceList = resourceService.getUserResouce(user.getId());*/
      //从session中获取当前登录用户的资源
        List<Resource> resourceList = (List<Resource>) session.getAttribute("resourceList");
        // 输出结果/ssm/user/toShow
        System.out.println(request.getRequestURI());
        // 输出结果http://localhost:8080/ssm/user/toShow
        System.out.println(request.getRequestURL());
        //当前请求uri
        String url = request.getRequestURI();
        //上下文路径
        String ctx = request.getContextPath();
        for (Resource resource:resourceList) {
            if(url.equals(ctx+resource.getUrl())){
                return true;
            }
        }
        //没权限
        response.sendRedirect(request.getContextPath()+"/403.jsp");
        return false;
    }
}
