package com.wang;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ShardingSphereApplication.java
 * @Description TODO
 * @createTime 2023年04月18日 10:17:00
 */
@SpringBootApplication
@MapperScan("com.wang.mapper")
public class ShardingSphereApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }
}
