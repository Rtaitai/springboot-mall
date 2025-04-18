package com.rtaitai.springbootmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rtaitai.springbootmall.mapper")
public class SpringbootMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMallApplication.class, args);
    }

}
