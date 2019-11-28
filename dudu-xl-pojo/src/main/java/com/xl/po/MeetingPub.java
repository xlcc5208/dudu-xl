package com.xl.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MeetingPub implements Serializable {
    //主键uuid
    private String id;
    //会议编号
    private String pcode;
    //会议召开时间
    private String ptime;
    //课题类别
    private String tname;
    //会议主题
    private String ptitle;
    //讲者区域
    private String pzone;
    //发单人
    private String uid;
    //描述
    private String remark;
    //发单时间
    private Date createdate;
    //发单状态
    private Short status;


}