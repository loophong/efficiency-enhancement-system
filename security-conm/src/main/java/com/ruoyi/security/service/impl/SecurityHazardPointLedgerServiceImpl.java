package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardPointLedgerMapper;
import com.ruoyi.security.domain.SecurityHazardPointLedger;
import com.ruoyi.security.service.ISecurityHazardPointLedgerService;

/**
 * 有点害台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityHazardPointLedgerServiceImpl implements ISecurityHazardPointLedgerService 
{
    @Autowired
    private SecurityHazardPointLedgerMapper securityHazardPointLedgerMapper;

    /**
     * 查询有点害台账
     * 
     * @param id 有点害台账主键
     * @return 有点害台账
     */
    @Override
    public SecurityHazardPointLedger selectSecurityHazardPointLedgerById(Long id)
    {
        return securityHazardPointLedgerMapper.selectSecurityHazardPointLedgerById(id);
    }

    /**
     * 查询有点害台账列表
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 有点害台账
     */
    @Override
    public List<SecurityHazardPointLedger> selectSecurityHazardPointLedgerList(SecurityHazardPointLedger securityHazardPointLedger)
    {
        return securityHazardPointLedgerMapper.selectSecurityHazardPointLedgerList(securityHazardPointLedger);
    }

    /**
     * 新增有点害台账
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 结果
     */
    @Override
    public int insertSecurityHazardPointLedger(SecurityHazardPointLedger securityHazardPointLedger)
    {
        return securityHazardPointLedgerMapper.insertSecurityHazardPointLedger(securityHazardPointLedger);
    }

    /**
     * 修改有点害台账
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 结果
     */
    @Override
    public int updateSecurityHazardPointLedger(SecurityHazardPointLedger securityHazardPointLedger)
    {
        return securityHazardPointLedgerMapper.updateSecurityHazardPointLedger(securityHazardPointLedger);
    }

    /**
     * 批量删除有点害台账
     * 
     * @param ids 需要删除的有点害台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardPointLedgerByIds(Long[] ids)
    {
        return securityHazardPointLedgerMapper.deleteSecurityHazardPointLedgerByIds(ids);
    }

    /**
     * 删除有点害台账信息
     * 
     * @param id 有点害台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardPointLedgerById(Long id)
    {
        return securityHazardPointLedgerMapper.deleteSecurityHazardPointLedgerById(id);
    }
}
