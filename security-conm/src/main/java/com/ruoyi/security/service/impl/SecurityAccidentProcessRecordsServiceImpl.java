package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityAccidentProcessRecordsMapper;
import com.ruoyi.security.domain.SecurityAccidentProcessRecords;
import com.ruoyi.security.service.ISecurityAccidentProcessRecordsService;

/**
 * 事故过程记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityAccidentProcessRecordsServiceImpl implements ISecurityAccidentProcessRecordsService 
{
    @Autowired
    private SecurityAccidentProcessRecordsMapper securityAccidentProcessRecordsMapper;

    /**
     * 查询事故过程记录
     * 
     * @param id 事故过程记录主键
     * @return 事故过程记录
     */
    @Override
    public SecurityAccidentProcessRecords selectSecurityAccidentProcessRecordsById(Long id)
    {
        return securityAccidentProcessRecordsMapper.selectSecurityAccidentProcessRecordsById(id);
    }

    /**
     * 查询事故过程记录列表
     * 
     * @param securityAccidentProcessRecords 事故过程记录
     * @return 事故过程记录
     */
    @Override
    public List<SecurityAccidentProcessRecords> selectSecurityAccidentProcessRecordsList(SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        return securityAccidentProcessRecordsMapper.selectSecurityAccidentProcessRecordsList(securityAccidentProcessRecords);
    }

    /**
     * 新增事故过程记录
     * 
     * @param securityAccidentProcessRecords 事故过程记录
     * @return 结果
     */
    @Override
    public int insertSecurityAccidentProcessRecords(SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        return securityAccidentProcessRecordsMapper.insertSecurityAccidentProcessRecords(securityAccidentProcessRecords);
    }

    /**
     * 修改事故过程记录
     * 
     * @param securityAccidentProcessRecords 事故过程记录
     * @return 结果
     */
    @Override
    public int updateSecurityAccidentProcessRecords(SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        return securityAccidentProcessRecordsMapper.updateSecurityAccidentProcessRecords(securityAccidentProcessRecords);
    }

    /**
     * 批量删除事故过程记录
     * 
     * @param ids 需要删除的事故过程记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentProcessRecordsByIds(Long[] ids)
    {
        return securityAccidentProcessRecordsMapper.deleteSecurityAccidentProcessRecordsByIds(ids);
    }

    /**
     * 删除事故过程记录信息
     * 
     * @param id 事故过程记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentProcessRecordsById(Long id)
    {
        return securityAccidentProcessRecordsMapper.deleteSecurityAccidentProcessRecordsById(id);
    }
}
