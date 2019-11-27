package com.xl.service.impl;

import com.xl.mapper.UserMapper;
import com.xl.po.User;
import com.xl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据wid查询用户是否存在
     */
    @Override
    public User selectByWid(Integer wid) {
        return userMapper.selectByWid(wid);
    }

    /**
     * 根据邮箱查询user对象信息
     * @param email
     * @return
     */
    @Override
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    /**
     * 帮定邮箱
     * @param wid
     * @param email
     * @return
     */
    @Override
    public int updateByEmail(Integer wid, String email) {
        return userMapper.updateByEmail(wid,email);
    }

    /**
     * 修改个人信息
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }
}
