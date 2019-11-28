package com.xl.service;

import com.xl.po.MeetingType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeetingTypeService {

    /**
     * 查询课题类别数据信息  有效  排序
     * @return
     */
    List<MeetingType> selectByStatusAndSortNumAsc();


}
