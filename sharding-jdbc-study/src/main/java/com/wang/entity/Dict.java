package com.wang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Dict.java
 * @Description TODO
 * @createTime 2023年05月22日 11:01:00
 */
@Data
@TableName("t_dict")
public class Dict {

    @TableId
    private Long id;
    private String status;
    private String value;
}
