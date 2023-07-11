package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.entity.Course;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CourseService.java
 * @Description TODO
 * @createTime 2023年07月11日 10:51:00
 */
public interface CourseService extends IService<Course> {

    void add();
}
