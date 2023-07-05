package com.wang;

import com.wang.entity.Course;
import com.wang.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ShardingTransactionTest.java
 * @Description TODO 事务的测试方法
 * 注意，引入了 XA事务 依赖
 * @createTime 2023年06月08日 13:28:00
 */
@SpringBootTest
public class ShardingTransactionTest {

    @Resource
    CourseMapper courseMapper;


    /**
     * 使用 application-config1.yml
     * course_2 表，添加唯一索引 user_id，当以下代码执行时，就会出现唯一索引冲突，
     * 在 ShardingSphere 下，@Transactional 同样可以解决分布式事务问题
     */
    @Test
    @Transactional
    void addCourse(){
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java");
            course.setUserid(100L);
            course.setCstatus("1");
            courseMapper.insert(course);
        }
    }
}
