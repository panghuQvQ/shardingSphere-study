package com.wang.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Student.java
 * @Description TODO
 * @createTime 2023年05月17日 17:22:00
 */
@Data
public class Student {

    @TableId
    private Long id;

    private String name;

    private Date birthday;
}
