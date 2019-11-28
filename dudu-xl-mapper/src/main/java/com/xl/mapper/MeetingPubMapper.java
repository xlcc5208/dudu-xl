package com.xl.mapper;

import com.xl.po.MeetingPub;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingPubMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeetingPub record);

    int insertSelective(MeetingPub record);

    MeetingPub selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeetingPub record);

    int updateByPrimaryKey(MeetingPub record);

    /**
     * 根据召开时间 年月日  查询 当天 pcode值
     * @param time
     * @return
     */
    @Select("SELECT max(pcode) FROM meetingpub WHERE LEFT(pcode,8)=#{time}")
    String selectMaxPcodeBytime(String time);

    /**
     * 根据用户uid查询用户已发布的会议
     * @param uid
     * @return
     */
    @Select("SELECT * from meetingpub where uid=#{uid} and `status`=1 ORDER BY pcode DESC")
    List<MeetingPub> selectMeetigPubByUid(String uid);


}