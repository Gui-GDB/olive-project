package com.gdb.main.config;

import com.gdb.main.config.interceptor.JWTTokenUserInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-18 17:24
 * @description:
 **/
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
    @Resource
    private Environment environment;
    @Resource
    private JWTTokenUserInterceptor jwtTokenUserInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenUserInterceptor)
                // 用户进行登录则放行
                .excludePathPatterns("/user/login")
                // 拦截以 sensor 开始的所有请求
                .addPathPatterns("/sensor/**")
                // 拦截以 chat 开始的所有请求
                .addPathPatterns("/chat/**")
                // 拦截以 user 开始的所有请求
                .addPathPatterns("/user/**")
                //拦截以 image 开始的所有请求
                .addPathPatterns("/image/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/"+ environment.getProperty("linux.avatarPattern") +"/**")
                .addResourceLocations("file:" + environment.getProperty("linux.avatar"));
        registry
                .addResourceHandler("/"+ environment.getProperty("linux.debugPattern") +"/**")
                .addResourceLocations("file:" + environment.getProperty("linux.debug"));
    }

    // todo 这个方式好像没法实现跨域访问，我把这个单独创建了一个类好像就可以了
/*    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射路径（凡是在addMapping配置的路径则代表可以跨域访问）,这里表示所有的都可以跨域访问
        registry.addMapping("/**")
                //设置放行哪些域
                .allowedOriginPatterns("*")
                // 放行哪些请求方式，也可以使用.allowedMethods("*")放行全部
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 放行哪些请求头部信息
                .allowedHeaders("*")
                // 是否允许跨域请求携带凭证Cookie发送
                .allowCredentials(true)
                // 设置响应的缓存时间
                .maxAge(3600)
                // 暴露哪些响应头部信息
                .exposedHeaders("Authorization");
    }
    @Bean
    public CorsFilter corsFilter() {
        // 1.创建 CORS 配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 支持域
        config.addAllowedOriginPattern("*");
        // 是否发送 Cookie
        config.setAllowCredentials(true);
        // 支持请求方式
        config.addAllowedMethod("*");
        // 允许的原始请求头部信息
        config.addAllowedHeader("*");
        // 暴露的头部信息
        config.addExposedHeader("*");
        // 2.添加地址映射
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        // 3.返回 CorsFilter 对象
        return new CorsFilter(corsConfigurationSource);
    }*/
}


