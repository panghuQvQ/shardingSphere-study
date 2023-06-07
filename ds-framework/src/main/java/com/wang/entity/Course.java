package com.wang.entity;

import lombok.Data;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
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
