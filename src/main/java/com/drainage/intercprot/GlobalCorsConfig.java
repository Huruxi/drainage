package com.drainage.intercprot;

import org.springframework.web.cors.CorsConfiguration;

/**
 * @author hrd <br/>
 * @date 2020/12/4
 */
//@Configuration
public class GlobalCorsConfig {

    private CorsConfiguration addCorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", addCorsConfig());
//        return new CorsFilter(source);
//    }
}
