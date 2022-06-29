package uz.kun.config;
//User :Lenovo
//Date :24.06.2022
//Time :14:21
//Project Name :Kun.uz

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;


@Configuration
@ControllerAdvice
public class RestConfig {

@Bean
    public RestTemplate testConfig()
    {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }



}
