package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencySuppliesLedgerMapper;
import com.ruoyi.security.domain.SecurityEmergencySuppliesLedger;
import com.ruoyi.security.service.ISecurityEmergencySuppliesLedgerService;

/**
 * 应急物资管理台帐Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecurityEmergencySuppliesLedgerServiceImpl implements ISecurityEmergencySuppliesLedgerService 
{
    @Autowired
    private SecurityEmergencySuppliesLedgerMapper securityEmergencySuppliesLedgerMapper;

    /**
     * 查询应急物资管理台帐
     * 
     * @param id 应急物资管理台帐主键
     * @return 应急物资管理台帐
     */
    @Override
    public SecurityEmergencySuppliesLedger selectSecurityEmergencySuppliesLedgerById(Long id)
    {
        return securityEmergencySuppliesLedgerMapper.selectSecurityEmergencySuppliesLedgerById(id);
    }

    /**
     * 查询应急物资管理台帐列表
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 应急物资管理台帐
     */
    @Override
    public List<SecurityEmergencySuppliesLedger> selectSecurityEmergencySuppliesLedgerList(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return securityEmergencySuppliesLedgerMapper.selectSecurityEmergencySuppliesLedgerList(securityEmergencySuppliesLedger);
    }

    /**
     * 新增应急物资管理台帐
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencySuppliesLedger(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return securityEmergencySuppliesLedgerMapper.insertSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger);
    }

    /**
     * 修改应急物资管理台帐
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencySuppliesLedger(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return securityEmergencySuppliesLedgerMapper.updateSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger);
    }

    /**
     * 批量删除应急物资管理台帐
     * 
     * @param ids 需要删除的应急物资管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencySuppliesLedgerByIds(Long[] ids)
    {
        return securityEmergencySuppliesLedgerMapper.deleteSecurityEmergencySuppliesLedgerByIds(ids);
    }

    /**
     * 删除应急物资管理台帐信息
     * 
     * @param id 应急物资管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencySuppliesLedgerById(Long id)
    {
        return securityEmergencySuppliesLedgerMapper.deleteSecurityEmergencySuppliesLedgerById(id);
    }
}
