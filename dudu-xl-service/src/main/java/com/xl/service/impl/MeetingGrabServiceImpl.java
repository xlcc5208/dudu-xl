package com.xl.service.impl;

import com.xl.mapper.MeetingGrabMapper;
import com.xl.po.MeetingGrab;
import com.xl.service.MeetingGrabService;
import com.xl.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
}
