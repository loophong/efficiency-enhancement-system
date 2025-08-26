package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityMeetingRecord;

/**
 * 安全会议记录Service接口
 * 
 * @author wang
 * @date 2025-08-26
 */
public interface ISecurityMeetingRecordService 
{
    /**
     * 查询安全会议记录
     * 
     * @param id 安全会议记录主键
     * @return 安全会议记录
     */
    public SecurityMeetingRecord selectSecurityMeetingRecordById(String id);

    /**
     * 查询安全会议记录列表
     * 
     * @param securityMeetingRecord 安全会议记录
     * @return 安全会议记录集合
     */
    public List<SecurityMeetingRecord> selectSecurityMeetingRecordList(SecurityMeetingRecord securityMeetingRecord);

    /**
     * 新增安全会议记录
     * 
     * @param securityMeetingRecord 安全会议记录
     * @return 结果
     */
    public int insertSecurityMeetingRecord(SecurityMeetingRecord securityMeetingRecord);

    /**
     * 修改安全会议记录
     * 
     * @param securityMeetingRecord 安全会议记录
     * @return 结果
     */
    public int updateSecurityMeetingRecord(SecurityMeetingRecord securityMeetingRecord);

    /**
     * 批量删除安全会议记录
     * 
     * @param ids 需要删除的安全会议记录主键集合
     * @return 结果
     */
    public int deleteSecurityMeetingRecordByIds(String[] ids);

    /**
     * 删除安全会议记录信息
     * 
     * @param id 安全会议记录主键
     * @return 结果
     */
    public int deleteSecurityMeetingRecordById(String id);
}
