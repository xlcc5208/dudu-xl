package com.xl.mapper;

import com.xl.po.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据wid查询用户是否存在
     */
    @Select("select * from user where wid=#{wid}")
    User selectByWid(Integer wid);

    /**
     * 根据邮箱查询user对象信息
     * @param email
     * @return
     */
    @Select("select * from user where email=#{email}")
    User selectByEmail(String email);

    /**
     * 帮定邮箱
     * @param wid
     * @param email
     * @return
     */
    @Update("update user set wid=#{wid} where email=#{email}")
    int updateByEmail(Integer wid,String email);

    /**
     * 根据weiuser中openid  查询得到user对象
     * @param openid
     * @return
     */
    @Select("SELECT * FROM `user` WHERE wid=(SELECT id FROM weiuser WHERE openid=#{openid})")
    User selectByOpenid(String openid);





}