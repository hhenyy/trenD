package com.td.TrenD.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SessionCheckInter interceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor)
                .addPathPatterns("/editUserForm")
                .addPathPatterns("/updateUser")
                .addPathPatterns("/commForm")
                .addPathPatterns("/editPw")
                .addPathPatterns("/editPwOk")
                .addPathPatterns("/delUser")
                .addPathPatterns("/delCheckOk");
    }
}
