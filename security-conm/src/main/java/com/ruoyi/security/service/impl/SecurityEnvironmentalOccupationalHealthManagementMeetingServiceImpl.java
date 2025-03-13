package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOccupationalHealthManagementMeetingMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementMeeting;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthManagementMeetingService;

/**
 * 环境职业健康安全管理评审会议记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityEnvironmentalOccupationalHealthManagementMeetingServiceImpl implements ISecurityEnvironmentalOccupationalHealthManagementMeetingService 
{
    @Autowired
    private SecurityEnvironmentalOccupationalHealthManagementMeetingMapper securityEnvironmentalOccupationalHealthManagementMeetingMapper;

    /**
     * 查询环境职业健康安全管理评审会议记录
     * 
     * @param id 环境职业健康安全管理评审会议记录主键
     * @return 环境职业健康安全管理评审会议记录
     */
    @Override
    public SecurityEnvironmentalOccupationalHealthManagementMeeting selectSecurityEnvironmentalOccupationalHealthManagementMeetingById(String id)
    {
        return securityEnvironmentalOccupationalHealthManagementMeetingMapper.selectSecurityEnvironmentalOccupationalHealthManagementMeetingById(id);
    }

    /**
     * 查询环境职业健康安全管理评审会议记录列表
     * 
     * @param securityEnvironmentalOccupationalHealthManagementMeeting 环境职业健康安全管理评审会议记录
     * @return 环境职业健康安全管理评审会议记录
     */
    @Override
    public List<SecurityEnvironmentalOccupationalHealthManagementMeeting> selectSecurityEnvironmentalOccupationalHealthManagementMeetingList(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        return securityEnvironmentalOccupationalHealthManagementMeetingMapper.selectSecurityEnvironmentalOccupationalHealthManagementMeetingList(securityEnvironmentalOccupationalHealthManagementMeeting);
    }

    /**
     * 新增环境职业健康安全管理评审会议记录
     * 
     * @param securityEnvironmentalOccupationalHealthManagementMeeting 环境职业健康安全管理评审会议记录
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOccupationalHealthManagementMeeting(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        return securityEnvironmentalOccupationalHealthManagementMeetingMapper.insertSecurityEnvironmentalOccupationalHealthManagementMeeting(securityEnvironmentalOccupationalHealthManagementMeeting);
    }

    /**
     * 修改环境职业健康安全管理评审会议记录
     * 
     * @param securityEnvironmentalOccupationalHealthManagementMeeting 环境职业健康安全管理评审会议记录
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOccupationalHealthManagementMeeting(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        return securityEnvironmentalOccupationalHealthManagementMeetingMapper.updateSecurityEnvironmentalOccupationalHealthManagementMeeting(securityEnvironmentalOccupationalHealthManagementMeeting);
    }

    /**
     * 批量删除环境职业健康安全管理评审会议记录
     * 
     * @param ids 需要删除的环境职业健康安全管理评审会议记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthManagementMeetingByIds(String[] ids)
    {
        return securityEnvironmentalOccupationalHealthManagementMeetingMapper.deleteSecurityEnvironmentalOccupationalHealthManagementMeetingByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理评审会议记录信息
     * 
     * @param id 环境职业健康安全管理评审会议记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthManagementMeetingById(String id)
    {
        return securityEnvironmentalOccupationalHealthManagementMeetingMapper.deleteSecurityEnvironmentalOccupationalHealthManagementMeetingById(id);
    }
}
