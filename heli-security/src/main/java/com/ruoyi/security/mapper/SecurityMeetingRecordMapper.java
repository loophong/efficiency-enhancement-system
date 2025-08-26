package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityMeetingRecord;

/**
 * 安全会议记录Mapper接口
 * 
 * @author wang
 * @date 2025-08-26
 */
public interface SecurityMeetingRecordMapper 
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
     * 删除安全会议记录
     * 
     * @param id 安全会议记录主键
     * @return 结果
     */
    public int deleteSecurityMeetingRecordById(String id);

    /**
     * 批量删除安全会议记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityMeetingRecordByIds(String[] ids);
}
