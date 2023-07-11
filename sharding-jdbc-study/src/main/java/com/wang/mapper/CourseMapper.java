package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CourseMapper.java
 * @Description TODO
 * @createTime 2023年04月18日 10:24:00
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
