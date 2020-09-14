package com.wx_shop.rabbitmq_test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RabbitmqTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqTestApplication.class, args);
    }

    @Value("${server.port}")
    String port;


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")//允许跨域的路径
                        .allowedOrigins("*")//允许跨域的域名
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")//允许跨域的方法
                        .maxAge(3600);
            }
        };
    }

}
