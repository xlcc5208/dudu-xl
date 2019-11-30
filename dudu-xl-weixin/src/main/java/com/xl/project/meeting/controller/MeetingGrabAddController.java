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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class MeetingGrabAddController {


    @Autowired
    private MeetingGrabService meetingGrabService;

    @Autowired
    private MeetingPubService meetingPubService;
    /**
     *
     * @param uid
     * @param pid
     * @param map
     * @return
     */
    @RequestMapping("meetingGrabAdd/toMeetingGrabAdd")//  meetingGrabAdd/toMeetingGrabAdd
    public String toMeetingGrabAdd(@RequestParam("uid") String uid, @RequestParam("pid") String pid,
                                   Map<String,Object> map){
        map.put("uid",uid);
        map.put("pid",pid);
        return "weixin/meetingGrabAdd/meetingGrabAdd";
    }


    @RequestMapping("meetingGrabAdd/add")
    public ModelAndView meetingGrabAdd(MeetingGrab meetingGrab){
        ModelAndView modelAndView = new ModelAndView();

        meetingGrabService.insertSelectiveWeixi(meetingGrab);

        modelAndView.addObject("uid",meetingGrab.getUid());

        modelAndView.setViewName("weixin/meetingGrad/meetingGrad");
        return modelAndView;
    }


    /**
     * 查询我的抢单列表
     *
     * @param uid 抢单人的uid
     * @return
     */
    @ResponseBody
    @RequestMapping("meetingGrabAdd/uid")
    List<MeetingPub> selectMyGrabListByUid(@RequestParam("uid") String uid){
        return meetingPubService.selectMyGrabListByUid(uid);
    }




}
