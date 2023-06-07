package com.wang.controller;

import com.wang.config.DynamicDataSource;
import com.wang.entity.Course;
import com.wang.mapper.CourseMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @title
 * @description
 * @author wzy
 * @updateTime 2023/5/17 11:00
 * @throws
 */
@Controller
@RequestMapping("/RDS")
public class CourseControllerRDS {
    @Resource
    CourseMapper courseMapper;

    @ResponseBody
    @RequestMapping("/queryCourse")
    public Object queryOrder(@RequestParam(value = "dsKey",defaultValue = "R") String dsKey){
        DynamicDataSource.name.set(dsKey);
        return courseMapper.selectList(null);
    }

    @ResponseBody
    @RequestMapping("/createCourse")
    public String createCourse(@RequestParam(value = "dsKey",defaultValue = "W") String dsKey, @RequestBody Course course){
        DynamicDataSource.name.set(dsKey);
        courseMapper.insert(course);
        return "SUCCESS BY RDS";
    }
}
