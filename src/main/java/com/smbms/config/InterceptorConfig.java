package com.smbms.config;

import com.smbms.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 *      WebMvcConfigurerAdapter 方法已过时
 *      新版本中实现WebMvcConfigurer 接口
 *
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean //将方法的返回值交给IOC
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(noLoginInterceptor())
                // 设置拦截路径
                .addPathPatterns("/**")
                // 设置放行路径
                .excludePathPatterns("/css/**","/images/**","/js/**","/lib/**", "/index","/user/login");
//                .excludePathPatterns("/css/**", "/image/**", "js/**", "/lib/**", "/index", "/user/login");
    }
}
