package com.xl.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.xl.mapper.MeetingPubMapper;
import com.xl.po.MeetingPub;
import com.xl.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingPubServiceImpl implements MeetingPubService {

    @Autowired
    private MeetingPubMapper meetingPubMapper;


    /**
     * 会议发单
     * @param meetingPub
     * @return
     */
    @Override
    public int insertSelective(MeetingPub meetingPub) {

        meetingPub.setId(UUID.randomUUID().toString());//添加uuid主键

        meetingPub.setPcode(pcodeGen(meetingPub.getPtime()));//根据会议召开时间  规律生成 pcode
        meetingPub.setCreatedate(new Date());//添加创建发单时间
        meetingPub.setStatus((short) 1);//添加状态属性  1为有效  0为无效
        return meetingPubMapper.insertSelective(meetingPub);
    }

    /**
     * 根据用户uid查询用户已发布的会议
     * @param uid
     * @return
     */
    @Override
    public List<MeetingPub> selectMeetigPubByUid(String uid) {
        return meetingPubMapper.selectMeetigPubByUid(uid);
    }

    @Override
    public List<MeetingPub> selectGradList(String uid, String tname) {
        return meetingPubMapper.selectGradList(uid,tname);
    }

    @Override
    public List<MeetingPub> selectMyGrabListByUid(String uid) {
        return meetingPubMapper.selectMyGrabListByUid(uid);
    }

    /**
     * 根据会议召开时间  规律生成 pcode
     * @param ptime
     * @return
     */
    public String pcodeGen(String ptime){

        String str = ptime.substring(0,10).replaceAll("-","");
        String pcode = meetingPubMapper.selectMaxPcodeBytime(str);

        if (StringUtils.isEmpty(pcode)){
            return str+"001";
        }else {
            Long lon = Long.parseLong(pcode)+1;
            return lon.toString();
        }
    }




}
