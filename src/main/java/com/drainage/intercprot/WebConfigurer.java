package com.drainage.intercprot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hrd <br/>
 * @date 2020/12/3
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor LoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //// 拦截所有请求
        registry.addInterceptor(LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/",
                        "/api/*/0/**",
                        "/login",
                        "/error",
                        "/index.html",
                        "/static/**");
    }
}
