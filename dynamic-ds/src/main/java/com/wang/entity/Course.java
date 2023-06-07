package com.wang.entity;

import lombok.Data;

/**
 * @title
 * @description
 * @author wzy
 * @updateTime 2023/5/17 10:55
 * @throws
 */
@Data
public class Course {

    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", userId=" + userId +
                ", cstatus='" + cstatus + '\'' +
                '}';
    }
}
