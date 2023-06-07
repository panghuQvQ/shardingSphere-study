package com.wang.controller;

import com.wang.entity.Course;
import com.wang.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author roy
 * @date 2022/3/17
 * @desc 使用DynamicDataSource框架，通过@DS注解快速切换数据源
 */

@Controller
@RequestMapping("/DS")
public class CourseControllerDS {

    @Resource
    CourseService courseService;

    @ResponseBody
    @RequestMapping("/queryCourse")
    public Object queryOrder(){
        return courseService.selectCourse();
    }

    @ResponseBody
    @RequestMapping("/createCourse")
    public String createCourse(@RequestBody Course course){
        courseService.createCourse(course);
        return "SUCCESS BY DS";
    }
}
