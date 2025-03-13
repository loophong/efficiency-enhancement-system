package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityProductionMeetingRecord;

/**
 * 安全会议记录Service接口
 * 
 * @author wang
 * @date 2025-03-07
 */
public interface ISecurityProductionMeetingRecordService 
{
    /**
     * 查询安全会议记录
     * 
     * @param id 安全会议记录主键
     * @return 安全会议记录
     */
    public SecurityProductionMeetingRecord selectSecurityProductionMeetingRecordById(Long id);

    /**
     * 查询安全会议记录列表
     * 
     * @param securityProductionMeetingRecord 安全会议记录
     * @return 安全会议记录集合
     */
    public List<SecurityProductionMeetingRecord> selectSecurityProductionMeetingRecordList(SecurityProductionMeetingRecord securityProductionMeetingRecord);

    /**
     * 新增安全会议记录
     * 
     * @param securityProductionMeetingRecord 安全会议记录
     * @return 结果
     */
    public int insertSecurityProductionMeetingRecord(SecurityProductionMeetingRecord securityProductionMeetingRecord);

    /**
     * 修改安全会议记录
     * 
     * @param securityProductionMeetingRecord 安全会议记录
     * @return 结果
     */
    public int updateSecurityProductionMeetingRecord(SecurityProductionMeetingRecord securityProductionMeetingRecord);

    /**
     * 批量删除安全会议记录
     * 
     * @param ids 需要删除的安全会议记录主键集合
     * @return 结果
     */
    public int deleteSecurityProductionMeetingRecordByIds(Long[] ids);

    /**
     * 删除安全会议记录信息
     * 
     * @param id 安全会议记录主键
     * @return 结果
     */
    public int deleteSecurityProductionMeetingRecordById(Long id);
}
