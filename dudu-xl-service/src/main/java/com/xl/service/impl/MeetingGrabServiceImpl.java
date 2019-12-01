package com.xl.service.impl;

import com.xl.mapper.MeetingGrabMapper;
import com.xl.po.MeetingGrab;
import com.xl.service.MeetingGrabService;
import com.xl.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingGrabServiceImpl implements MeetingGrabService {

    @Autowired
    private MeetingGrabMapper meetingGrabMapper;

    @Override
    public int insertSelectiveWeixi(MeetingGrab meetingGrab) {

        meetingGrab.setId(UUID.randomUUID().toString());
        meetingGrab.setCreatedate(new Date());
        meetingGrab.setGrabstatus(0);
        meetingGrab.setStatus((short) 1);
        return meetingGrabMapper.insertSelective(meetingGrab);
    }

    @Override
    public List<MeetingGrab> selectGrabListByPid(String pid) {
        return meetingGrabMapper.selectGrabListByPid(pid);
    }

    @Override
    @Transactional
    public int chooseMeetingGrab(String pid, String uid) throws RuntimeException{

        int num = meetingGrabMapper.updateMeetingGrabMatchFail(pid);
        if (num<1){
            throw new RuntimeException("抢单匹配失败异常");
        }
        int num1 =meetingGrabMapper.updateMeetingGrabMatchSucc(pid,uid);
        if (num1<1){
            throw new RuntimeException("抢单匹配成功异常");
        }
        return 1;
    }
}
