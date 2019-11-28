package com.xl.service;

import com.xl.po.User;
import org.apache.ibatis.annotations.Select;

public interface UserService {

    /**
     * 根据wid查询用户是否存在
     */
    User selectByWid(Integer wid);

    /**
     * 根据邮箱查询user对象信息
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 帮定邮箱
     * @param wid
     * @param email
     * @return
     */
    int updateByEmail(Integer wid,String email);

    /**
     * 修改个人信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 判断发单的角色
     * @param openid
     * @return
     */
    User selectByOpenid(String openid);



}
