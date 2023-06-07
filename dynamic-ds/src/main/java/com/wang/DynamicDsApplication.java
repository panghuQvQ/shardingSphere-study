package com.wang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName DynamicDsApplication.java
 * @Description TODO
 * @createTime 2023年05月15日 15:38:00
 */
@SpringBootApplication
@MapperScan("com.wang.mapper")
public class DynamicDsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDsApplication.class,args);
    }
}
