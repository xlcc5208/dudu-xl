package com.xl.project.meeting.controller;

import com.xl.po.MeetingPub;
import com.xl.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("meetingPub")
public class MeetingPubController {

    @Autowired
    private MeetingPubService meetingPubService;

    /**
     * 发布会议
     * @param meetingPub
     * @return
     */
    @ResponseBody
    @RequestMapping("add") //  /meetingPub/add
    public int meetingPubAdd(MeetingPub meetingPub){

        return meetingPubService.insertSelective(meetingPub);
    }

    /**
     * 我的发布会议列表
     */
    @ResponseBody
    @RequestMapping("list") //   /meetingPub/list
    public List<MeetingPub> selectMyMeetingPub(@RequestParam("uid") String uid){
        return  meetingPubService.selectMeetigPubByUid(uid);
    }

    /**
     * 可抢单列表
     * @param uid
     * @param tname
     * @return
     */
    @ResponseBody
    @RequestMapping("gradList") //  meetingPub/gradList
    public List<MeetingPub> selectGradList(@RequestParam("uid") String uid,
                                           @RequestParam("tname") String tname){

        return meetingPubService.selectGradList(uid,tname);
    }




}
