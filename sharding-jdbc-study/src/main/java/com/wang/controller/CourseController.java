package com.wang.controller;

import com.wang.service.CourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CourseController.java
 * @Description TODO
 * @createTime 2023年07月07日 16:30:00
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    CourseService courseService;

    @PostMapping("/add")
    public void addCourse() {
        courseService.add();
    }
}

