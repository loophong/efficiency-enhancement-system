package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityProductionMeetingRecordMapper;
import com.ruoyi.security.domain.SecurityProductionMeetingRecord;
import com.ruoyi.security.service.ISecurityProductionMeetingRecordService;

/**
 * 安全会议记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-07
 */
@Service
public class SecurityProductionMeetingRecordServiceImpl implements ISecurityProductionMeetingRecordService 
{
    @Autowired
    private SecurityProductionMeetingRecordMapper securityProductionMeetingRecordMapper;

    /**
     * 查询安全会议记录
     * 
     * @param id 安全会议记录主键
     * @return 安全会议记录
     */
    @Override
    public SecurityProductionMeetingRecord selectSecurityProductionMeetingRecordById(Long id)
    {
        return securityProductionMeetingRecordMapper.selectSecurityProductionMeetingRecordById(id);
    }

    /**
     * 查询安全会议记录列表
     * 
     * @param securityProductionMeetingRecord 安全会议记录
     * @return 安全会议记录
     */
    @Override
    public List<SecurityProductionMeetingRecord> selectSecurityProductionMeetingRecordList(SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        return securityProductionMeetingRecordMapper.selectSecurityProductionMeetingRecordList(securityProductionMeetingRecord);
    }

    /**
     * 新增安全会议记录
     * 
     * @param securityProductionMeetingRecord 安全会议记录
     * @return 结果
     */
    @Override
    public int insertSecurityProductionMeetingRecord(SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        return securityProductionMeetingRecordMapper.insertSecurityProductionMeetingRecord(securityProductionMeetingRecord);
    }

    /**
     * 修改安全会议记录
     * 
     * @param securityProductionMeetingRecord 安全会议记录
     * @return 结果
     */
    @Override
    public int updateSecurityProductionMeetingRecord(SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        return securityProductionMeetingRecordMapper.updateSecurityProductionMeetingRecord(securityProductionMeetingRecord);
    }

    /**
     * 批量删除安全会议记录
     * 
     * @param ids 需要删除的安全会议记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityProductionMeetingRecordByIds(Long[] ids)
    {
        return securityProductionMeetingRecordMapper.deleteSecurityProductionMeetingRecordByIds(ids);
    }

    /**
     * 删除安全会议记录信息
     * 
     * @param id 安全会议记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityProductionMeetingRecordById(Long id)
    {
        return securityProductionMeetingRecordMapper.deleteSecurityProductionMeetingRecordById(id);
    }
}
