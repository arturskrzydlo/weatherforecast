package org.artur.skrzydlo.sharkbytetask.configuration;

import org.artur.skrzydlo.sharkbytetask.interceptors.RateLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("org.artur.skrzydlo.sharkbytetask")
public class MvcConfig extends WebMvcConfigurerAdapter {


    @Bean
    public RateLimitInterceptor rateLimitInterceptor(){
        return new RateLimitInterceptor();
    }

    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor());
    }
}
