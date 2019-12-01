package com.xl.service;

import com.xl.po.MeetingGrab;

import java.util.List;

public interface MeetingGrabService {

    int insertSelectiveWeixi(MeetingGrab meetingGrab);


    /**
     * 根据会议发布ID查询抢单人列表信息
     * @param pid
     * @return
     */
    List<MeetingGrab> selectGrabListByPid(String pid);


    /**
     * 就选你功能
     * @param uid
     * @param pid
     * @return
     */
    int chooseMeetingGrab(String pid,String uid) throws RuntimeException;

}
