package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelatedPartyLedgerMapper;
import com.ruoyi.security.domain.SecurityRelatedPartyLedger;
import com.ruoyi.security.service.ISecurityRelatedPartyLedgerService;

/**
 * 相关方管理台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityRelatedPartyLedgerServiceImpl implements ISecurityRelatedPartyLedgerService 
{
    @Autowired
    private SecurityRelatedPartyLedgerMapper securityRelatedPartyLedgerMapper;

    /**
     * 查询相关方管理台账
     * 
     * @param id 相关方管理台账主键
     * @return 相关方管理台账
     */
    @Override
    public SecurityRelatedPartyLedger selectSecurityRelatedPartyLedgerById(Long id)
    {
        return securityRelatedPartyLedgerMapper.selectSecurityRelatedPartyLedgerById(id);
    }

    /**
     * 查询相关方管理台账列表
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 相关方管理台账
     */
    @Override
    public List<SecurityRelatedPartyLedger> selectSecurityRelatedPartyLedgerList(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return securityRelatedPartyLedgerMapper.selectSecurityRelatedPartyLedgerList(securityRelatedPartyLedger);
    }

    /**
     * 新增相关方管理台账
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 结果
     */
    @Override
    public int insertSecurityRelatedPartyLedger(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return securityRelatedPartyLedgerMapper.insertSecurityRelatedPartyLedger(securityRelatedPartyLedger);
    }

    /**
     * 修改相关方管理台账
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 结果
     */
    @Override
    public int updateSecurityRelatedPartyLedger(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return securityRelatedPartyLedgerMapper.updateSecurityRelatedPartyLedger(securityRelatedPartyLedger);
    }

    /**
     * 批量删除相关方管理台账
     * 
     * @param ids 需要删除的相关方管理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyLedgerByIds(Long[] ids)
    {
        return securityRelatedPartyLedgerMapper.deleteSecurityRelatedPartyLedgerByIds(ids);
    }

    /**
     * 删除相关方管理台账信息
     * 
     * @param id 相关方管理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyLedgerById(Long id)
    {
        return securityRelatedPartyLedgerMapper.deleteSecurityRelatedPartyLedgerById(id);
    }
}
