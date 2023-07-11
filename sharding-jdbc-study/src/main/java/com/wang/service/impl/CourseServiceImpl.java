package com.wang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.entity.Course;
import com.wang.mapper.CourseMapper;
import com.wang.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CourseServiceImpl.java
 * @Description TODO
 * @createTime 2023年07月11日 10:54:00
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Override
    @Transactional // spring的事务注解
//    @ShardingTransactionType(TransactionType.BASE)// Sharding-jdbc柔性事务
    public void add() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java");
            course.setUserid(100L);
            course.setCstatus("1");
            this.saveOrUpdate(course);
        }
    }
}
