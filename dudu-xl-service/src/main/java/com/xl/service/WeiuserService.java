package com.xl.service;

import com.xl.po.Weiuser;

public interface WeiuserService {

    /**
     * 根据openid判断是存在对象信息
     * @param openid
     * @return
     */
    Weiuser selectByOpenid(String openid);

    int insertSelective(Weiuser record);


}
