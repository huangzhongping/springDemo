package com.example.springbootdemo.product.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 */
@Configuration
//@EnableWebMvc 静态资源
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionIntercetor sessionIntercetor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionIntercetor).addPathPatterns("/**");
    }
}