package com.wang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Course.java
 * @Description TODO
 * @createTime 2023年04月18日 10:19:00
 */
@Data
@TableName(value = "course")
public class Course {
    @TableId
    private Long cid;
    private String cname;
    @TableField("user_id")
    private Long userid;
    private String cstatus;
}
