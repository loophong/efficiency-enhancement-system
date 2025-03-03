package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsHandlingLedgerMapper;
import com.ruoyi.security.domain.SecurityHazardousMaterialsHandlingLedger;
import com.ruoyi.security.service.ISecurityHazardousMaterialsHandlingLedgerService;

/**
 * 危化品处理台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-03
 */
@Service
public class SecurityHazardousMaterialsHandlingLedgerServiceImpl implements ISecurityHazardousMaterialsHandlingLedgerService 
{
    @Autowired
    private SecurityHazardousMaterialsHandlingLedgerMapper securityHazardousMaterialsHandlingLedgerMapper;

    /**
     * 查询危化品处理台账
     * 
     * @param id 危化品处理台账主键
     * @return 危化品处理台账
     */
    @Override
    public SecurityHazardousMaterialsHandlingLedger selectSecurityHazardousMaterialsHandlingLedgerById(Long id)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.selectSecurityHazardousMaterialsHandlingLedgerById(id);
    }

    /**
     * 查询危化品处理台账列表
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 危化品处理台账
     */
    @Override
    public List<SecurityHazardousMaterialsHandlingLedger> selectSecurityHazardousMaterialsHandlingLedgerList(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.selectSecurityHazardousMaterialsHandlingLedgerList(securityHazardousMaterialsHandlingLedger);
    }

    /**
     * 新增危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    @Override
    public int insertSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.insertSecurityHazardousMaterialsHandlingLedger(securityHazardousMaterialsHandlingLedger);
    }

    /**
     * 修改危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    @Override
    public int updateSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.updateSecurityHazardousMaterialsHandlingLedger(securityHazardousMaterialsHandlingLedger);
    }

    /**
     * 批量删除危化品处理台账
     * 
     * @param ids 需要删除的危化品处理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsHandlingLedgerByIds(Long[] ids)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.deleteSecurityHazardousMaterialsHandlingLedgerByIds(ids);
    }

    /**
     * 删除危化品处理台账信息
     * 
     * @param id 危化品处理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsHandlingLedgerById(Long id)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.deleteSecurityHazardousMaterialsHandlingLedgerById(id);
    }
}
