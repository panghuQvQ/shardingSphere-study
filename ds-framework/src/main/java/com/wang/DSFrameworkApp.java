package com.wang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title
 * @description
 * @author wzy
 * @updateTime 2023/5/17 13:25
 * @throws
 */
@SpringBootApplication
@MapperScan("com.wang.mapper")
public class DSFrameworkApp {

    public static void main(String[] args) {
        SpringApplication.run(DSFrameworkApp.class,args);
    }
}
