package com.xl.project.meeting.controller;

import com.xl.po.MeetingType;
import com.xl.service.MeetingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("meetingType")
public class MeetingTypeController {

    @Autowired
    private MeetingTypeService meetingTypeService;

    /**
     * 发布页面加载课题下拉框数据
     * @return
     */
    @ResponseBody
    @RequestMapping("list") //   /meetingType/list
    public List<MeetingType> selectMeetingTye(){
        return meetingTypeService.selectByStatusAndSortNumAsc();
    }



}
