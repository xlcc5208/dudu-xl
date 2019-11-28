package com.xl.service.impl;

import com.xl.mapper.MeetingTypeMapper;
import com.xl.po.MeetingType;
import com.xl.service.MeetingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {

    @Autowired
    private MeetingTypeMapper meetingTypeMapper;

    /**
     * 查询课题类别数据信息  有效  排序
     * @return
     */
    @Override
    public List<MeetingType> selectByStatusAndSortNumAsc() {
        return meetingTypeMapper.selectByStatusAndSortNumAsc();
    }
}
