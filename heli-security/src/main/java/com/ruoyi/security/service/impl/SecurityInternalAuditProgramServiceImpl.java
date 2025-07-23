package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityInternalAuditProgramMapper;
import com.ruoyi.security.domain.SecurityInternalAuditProgram;
import com.ruoyi.security.service.ISecurityInternalAuditProgramService;

/**
 * 内审方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-10
 */
@Service
public class SecurityInternalAuditProgramServiceImpl implements ISecurityInternalAuditProgramService 
{
    @Autowired
    private SecurityInternalAuditProgramMapper securityInternalAuditProgramMapper;

    /**
     * 查询内审方案
     * 
     * @param id 内审方案主键
     * @return 内审方案
     */
    @Override
    public SecurityInternalAuditProgram selectSecurityInternalAuditProgramById(String id)
    {
        return securityInternalAuditProgramMapper.selectSecurityInternalAuditProgramById(id);
    }

    /**
     * 查询内审方案列表
     * 
     * @param securityInternalAuditProgram 内审方案
     * @return 内审方案
     */
    @Override
    public List<SecurityInternalAuditProgram> selectSecurityInternalAuditProgramList(SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        return securityInternalAuditProgramMapper.selectSecurityInternalAuditProgramList(securityInternalAuditProgram);
    }

    /**
     * 新增内审方案
     * 
     * @param securityInternalAuditProgram 内审方案
     * @return 结果
     */
    @Override
    public int insertSecurityInternalAuditProgram(SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        return securityInternalAuditProgramMapper.insertSecurityInternalAuditProgram(securityInternalAuditProgram);
    }

    /**
     * 修改内审方案
     * 
     * @param securityInternalAuditProgram 内审方案
     * @return 结果
     */
    @Override
    public int updateSecurityInternalAuditProgram(SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        return securityInternalAuditProgramMapper.updateSecurityInternalAuditProgram(securityInternalAuditProgram);
    }

    /**
     * 批量删除内审方案
     * 
     * @param ids 需要删除的内审方案主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditProgramByIds(String[] ids)
    {
        return securityInternalAuditProgramMapper.deleteSecurityInternalAuditProgramByIds(ids);
    }

    /**
     * 删除内审方案信息
     * 
     * @param id 内审方案主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditProgramById(String id)
    {
        return securityInternalAuditProgramMapper.deleteSecurityInternalAuditProgramById(id);
    }
}
