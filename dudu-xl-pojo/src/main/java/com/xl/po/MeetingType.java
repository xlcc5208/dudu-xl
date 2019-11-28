package com.xl.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class MeetingType implements Serializable {
    private Integer id;

    private String tname;

    private String remark;

    private Short status;

    private Integer sortnum;


}