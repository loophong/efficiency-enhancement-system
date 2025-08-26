package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityMeetingRecordMapper;
import com.ruoyi.security.domain.SecurityMeetingRecord;
import com.ruoyi.security.service.ISecurityMeetingRecordService;

/**
 * 安全会议记录Service业务层处理
 * 
 * @author wang
 * @date 2025-08-26
 */
@Service
public class SecurityMeetingRecordServiceImpl implements ISecurityMeetingRecordService 
{
    @Autowired
    private SecurityMeetingRecordMapper securityMeetingRecordMapper;

    /**
     * 查询安全会议记录
     * 
     * @param id 安全会议记录主键
     * @return 安全会议记录
     */
    @Override
    public SecurityMeetingRecord selectSecurityMeetingRecordById(String id)
    {
        return securityMeetingRecordMapper.selectSecurityMeetingRecordById(id);
    }

    /**
     * 查询安全会议记录列表
     * 
     * @param securityMeetingRecord 安全会议记录
     * @return 安全会议记录
     */
    @Override
    public List<SecurityMeetingRecord> selectSecurityMeetingRecordList(SecurityMeetingRecord securityMeetingRecord)
    {
        return securityMeetingRecordMapper.selectSecurityMeetingRecordList(securityMeetingRecord);
    }

    /**
     * 新增安全会议记录
     * 
     * @param securityMeetingRecord 安全会议记录
     * @return 结果
     */
    @Override
    public int insertSecurityMeetingRecord(SecurityMeetingRecord securityMeetingRecord)
    {
        return securityMeetingRecordMapper.insertSecurityMeetingRecord(securityMeetingRecord);
    }

    /**
     * 修改安全会议记录
     * 
     * @param securityMeetingRecord 安全会议记录
     * @return 结果
     */
    @Override
    public int updateSecurityMeetingRecord(SecurityMeetingRecord securityMeetingRecord)
    {
        return securityMeetingRecordMapper.updateSecurityMeetingRecord(securityMeetingRecord);
    }

    /**
     * 批量删除安全会议记录
     * 
     * @param ids 需要删除的安全会议记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityMeetingRecordByIds(String[] ids)
    {
        return securityMeetingRecordMapper.deleteSecurityMeetingRecordByIds(ids);
    }

    /**
     * 删除安全会议记录信息
     * 
     * @param id 安全会议记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityMeetingRecordById(String id)
    {
        return securityMeetingRecordMapper.deleteSecurityMeetingRecordById(id);
    }
}
