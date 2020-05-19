package com.turcom.conferencedemo.interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by oa on 6/3/2019.
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // registry.addInterceptor(new LogInterceptor())
        //         .addPathPatterns("/api/v1/sessions")
        //         .addPathPatterns("/api/v1/speakers")
        //         .addPathPatterns("/");
        

    }
}
