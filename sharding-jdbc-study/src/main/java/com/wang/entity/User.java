package com.wang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2023年05月22日 13:38:00
 */
@Data
@TableName("t_user")
public class User {

    private Long userId;

    private String username;

    private String ustatus;

    private int uage;
}
