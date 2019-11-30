package com.xl.service;

import com.xl.po.MeetingPub;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeetingPubService {


    int insertSelective(MeetingPub record);

    /**
     * 根据用户uid查询用户已发布的会议
     * @param uid
     * @return
     */
    List<MeetingPub> selectMeetigPubByUid(String uid);


    /**
     * 查询可抢单列表
     * @return
     */
    List<MeetingPub> selectGradList(String uid,String tname);


    /**
     * 查询我的抢单列表
     *
     * @param uid 抢单人的uid
     * @return
     */
    List<MeetingPub> selectMyGrabListByUid(String uid);

}
