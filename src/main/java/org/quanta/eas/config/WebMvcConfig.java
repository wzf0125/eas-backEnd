package org.quanta.eas.config;

import org.quanta.eas.bean.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 添加拦截器
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry. addInterceptor(authInterceptor())
                // 放通静态资源和所有登录接口
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/common/login")
//                .excludePathPatterns("/druid/login.html") // druid连接池面板
                .excludePathPatterns("/swagger-ui.html") // swagger面板
                .excludePathPatterns("/swagger-resources/**") // swagger面板
                .excludePathPatterns("/v2/**/") // swagger面板
                .excludePathPatterns("/webjars/**") // swagger面板
                .addPathPatterns("/**");
    }

//    @Value("${file.path}")
//    String resourcePath;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("file:" + resourcePath);
//    }
}
