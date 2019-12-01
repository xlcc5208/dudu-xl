package com.xl.project.meeting.controller;

import com.xl.po.MeetingGrab;
import com.xl.po.MeetingPub;
import com.xl.service.MeetingGrabService;
import com.xl.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("meetingPub")
public class MeetingPubController {

    @Autowired
    private MeetingPubService meetingPubService;
    @Autowired
    private MeetingGrabService meetingGrabService;
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


    @RequestMapping("chooseGrabToPage") //  meetingPub/chooseGrabToPage
    public String chooseGrabToPage(@RequestParam("uid") String uid, @RequestParam("pid") String pid,
                                   HttpServletRequest request, HttpSession session){

        request.setAttribute("uid",uid);
        request.setAttribute("pid",pid);
        return "weixin/meetingPub/grabList";
    }


    @ResponseBody
    @RequestMapping("grabListByPid") //  meetingPub/grabListByPid
    public List<MeetingGrab> selectGrabListByPid(@RequestParam("pid") String pid){
        return meetingGrabService.selectGrabListByPid(pid);
    }

    /**
     * 就选你功能
     * @param pid
     * @param uid
     * @return
     */
    @ResponseBody
    @RequestMapping("chooseGrabList")//  meetingPub/chooseGrabList
    public int chooseGrabList(@RequestParam("pid") String pid,@RequestParam("uid") String uid){

        int num = 0;
        try {
            num = meetingGrabService.chooseMeetingGrab(pid,uid);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return num;

    }








}
