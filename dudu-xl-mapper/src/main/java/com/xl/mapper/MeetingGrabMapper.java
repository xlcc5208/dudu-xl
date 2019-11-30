package com.xl.mapper;

import com.xl.po.MeetingGrab;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingGrabMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeetingGrab record);

    int insertSelective(MeetingGrab record);

    MeetingGrab selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeetingGrab record);

    int updateByPrimaryKey(MeetingGrab record);
}