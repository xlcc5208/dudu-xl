package com.xl.service.impl;

import com.xl.mapper.WeiuserMapper;
import com.xl.po.Weiuser;
import com.xl.service.WeiuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeiuserServiceImpl implements WeiuserService {

    @Autowired
    private WeiuserMapper weiuserMapper;


    /**
     * 根据openid判断是存在对象信息
     * @param openid
     * @return
     */
    @Override
    public Weiuser selectByOpenid(String openid) {

        return  weiuserMapper.selectByOpenid(openid);
    }

    /**
     * 添加
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Weiuser record) {
        return weiuserMapper.insertSelective(record);
    }


}
