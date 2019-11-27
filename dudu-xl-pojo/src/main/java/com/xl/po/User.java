package com.xl.po;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
public class User {
    private String id;

    private String name;

    private String email;

    private String telephone;

    private String province;

    private String city;

    private String zone;

    private Integer rid;

    private Date createdate;

    private Short status;

    private Integer wid;

}