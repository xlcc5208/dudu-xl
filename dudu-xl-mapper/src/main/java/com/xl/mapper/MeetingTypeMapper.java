package com.xl.mapper;

import com.xl.po.MeetingType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MeetingTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingType record);

    int insertSelective(MeetingType record);

    MeetingType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingType record);

    int updateByPrimaryKey(MeetingType record);

    /**
     * 查询课题类别数据信息  有效  排序
     * @return
     */
    @Select("SELECT * from meetingtype WHERE `status`=1 ORDER BY sortnum")
    List<MeetingType> selectByStatusAndSortNumAsc();


}