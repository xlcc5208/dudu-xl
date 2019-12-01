package com.xl.mapper;

import com.xl.po.MeetingGrab;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingGrabMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeetingGrab record);

    int insertSelective(MeetingGrab record);

    MeetingGrab selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeetingGrab record);

    int updateByPrimaryKey(MeetingGrab record);

    /**
     * 根据会议发布ID查询抢单人列表信息
     * @param pid
     * @return
     */
    List<MeetingGrab> selectGrabListByPid(String pid);

    /**
     * 就选你功能
     *
     * @return
     */
    @Update("update meetinggrab set grabStatus=2,grabDate=NOW() where pid=#{pid}")
    int updateMeetingGrabMatchFail(String pid);
    @Update("update meetinggrab set grabStatus=1,grabDate=NOW() where pid=#{pid} and uid=#{uid}")
    int updateMeetingGrabMatchSucc(String pid,String uid);


}