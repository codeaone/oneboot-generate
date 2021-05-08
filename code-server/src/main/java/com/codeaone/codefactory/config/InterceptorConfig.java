package com.codeaone.codefactory.config;

import org.oneboot.core.mvc.interceptor.InterfaceMonitorInterceptor;
import org.oneboot.core.mvc.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public InterfaceMonitorInterceptor getInterfaceMonitorInterceptor() {
        return new InterfaceMonitorInterceptor();
    }

    @Bean
    public LoggingInterceptor getLoggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getInterfaceMonitorInterceptor()).addPathPatterns("/**")
            .excludePathPatterns("/dist/**");
        registry.addInterceptor(getLoggingInterceptor()).addPathPatterns("/**")
            .excludePathPatterns("/dist/**");
        // 如果interceptor中不注入redis或其他项目可以直接new，否则请使用上面这种方式
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
