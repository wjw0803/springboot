package com.dj.ssm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

 /*   @Autowired
    private MyInterceptor myInterceptor;*/

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       /* //向容器注册拦截器
        InterceptorRegistration loginRegistration = registry.addInterceptor(myInterceptor);
        //拦截请求
        loginRegistration.addPathPatterns("/**");
        //放过的请求
        loginRegistration.excludePathPatterns("/static/res/**");
        loginRegistration.excludePathPatterns("/static/res/js/**");
        loginRegistration.excludePathPatterns("/static/res/md5/**");
        loginRegistration.excludePathPatterns("/static/res/layer/**");
        loginRegistration.excludePathPatterns("/user/toLogin");
        loginRegistration.excludePathPatterns("/user/login");
        loginRegistration.excludePathPatterns("/user/toAdd");
        loginRegistration.excludePathPatterns("/user/add");*/
        InterceptorRegistration authRegistration = registry.addInterceptor(authInterceptor);
        authRegistration.addPathPatterns("/user/toShow");
        authRegistration.addPathPatterns("/order/toShow");
        authRegistration.addPathPatterns("/car/toShow");

    }

}
