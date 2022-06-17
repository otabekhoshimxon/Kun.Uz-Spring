package uz.kun.config;
//User :Lenovo
//Date :15.06.2022
//Time :17:36
//Project Name :Kun.uz

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Configuration
public class SecuredFilterConfig {

    @Autowired
    JwtFilter filter;


    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBean( )
    {
        FilterRegistrationBean<JwtFilter> bean=new FilterRegistrationBean();
        bean.setFilter(filter);
        bean.addUrlPatterns("/profile/*");
        bean.addUrlPatterns("/article/mod/*");
        bean.addUrlPatterns("/comment/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/article_like/*");
        bean.addUrlPatterns("/comment_like/*");
        bean.addUrlPatterns("/article_type/adm/*");




        return bean;
    }

}
