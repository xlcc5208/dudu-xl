package com.xl.project.weixin.api.tuling.bean;

import lombok.Data;

@Data
public class TuLingBean {

    private int reqType = 0;
    private Perception perception;
    private UserInfo userInfo;

}
