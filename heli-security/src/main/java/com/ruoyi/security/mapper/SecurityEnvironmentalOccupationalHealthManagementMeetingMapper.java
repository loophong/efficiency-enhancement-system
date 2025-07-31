package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementMeeting;

/**
 * 环境职业健康安全管理评审会议记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityEnvironmentalOccupationalHealthManagementMeetingMapper 
{
    /**
     * 查询环境职业健康安全管理评审会议记录
     * 
     * @param id 环境职业健康安全管理评审会议记录主键
     * @return 环境职业健康安全管理评审会议记录
     */
    public SecurityEnvironmentalOccupationalHealthManagementMeeting selectSecurityEnvironmentalOccupationalHealthManagementMeetingById(String id);

    /**
     * 查询环境职业健康安全管理评审会议记录列表
     * 
     * @param securityEnvironmentalOccupationalHealthManagementMeeting 环境职业健康安全管理评审会议记录
     * @return 环境职业健康安全管理评审会议记录集合
     */
    public List<SecurityEnvironmentalOccupationalHealthManagementMeeting> selectSecurityEnvironmentalOccupationalHealthManagementMeetingList(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting);

    /**
     * 新增环境职业健康安全管理评审会议记录
     * 
     * @param securityEnvironmentalOccupationalHealthManagementMeeting 环境职业健康安全管理评审会议记录
     * @return 结果
     */
    public int insertSecurityEnvironmentalOccupationalHealthManagementMeeting(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting);

    /**
     * 修改环境职业健康安全管理评审会议记录
     * 
     * @param securityEnvironmentalOccupationalHealthManagementMeeting 环境职业健康安全管理评审会议记录
     * @return 结果
     */
    public int updateSecurityEnvironmentalOccupationalHealthManagementMeeting(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting);

    /**
     * 删除环境职业健康安全管理评审会议记录
     * 
     * @param id 环境职业健康安全管理评审会议记录主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthManagementMeetingById(String id);

    /**
     * 批量删除环境职业健康安全管理评审会议记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthManagementMeetingByIds(String[] ids);
}
