package com.wang.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.wang.entity.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author roy
 * @date 2022/3/17
 * @desc
 */
@Service
@DS("write")
public class CourseService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @DS("read") // 指定数据源 read
    public List selectCourse(){
        return jdbcTemplate.queryForList("select * from course");
    }

//    @DS("read")
    public int createCourse(Course course){
        return jdbcTemplate.update("insert into course values(?,?,?,?)",course.getCid(),course.getCname(),course.getUserId(),course.getCstatus());
    }
}
