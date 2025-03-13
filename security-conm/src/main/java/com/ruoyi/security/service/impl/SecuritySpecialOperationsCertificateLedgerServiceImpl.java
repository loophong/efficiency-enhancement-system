package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialOperationsCertificateLedgerMapper;
import com.ruoyi.security.domain.SecuritySpecialOperationsCertificateLedger;
import com.ruoyi.security.service.ISecuritySpecialOperationsCertificateLedgerService;

/**
 * 特种设备台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecuritySpecialOperationsCertificateLedgerServiceImpl implements ISecuritySpecialOperationsCertificateLedgerService 
{
    @Autowired
    private SecuritySpecialOperationsCertificateLedgerMapper securitySpecialOperationsCertificateLedgerMapper;

    /**
     * 查询特种设备台账
     * 
     * @param id 特种设备台账主键
     * @return 特种设备台账
     */
    @Override
    public SecuritySpecialOperationsCertificateLedger selectSecuritySpecialOperationsCertificateLedgerById(Long id)
    {
        return securitySpecialOperationsCertificateLedgerMapper.selectSecuritySpecialOperationsCertificateLedgerById(id);
    }

    /**
     * 查询特种设备台账列表
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 特种设备台账
     */
    @Override
    public List<SecuritySpecialOperationsCertificateLedger> selectSecuritySpecialOperationsCertificateLedgerList(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return securitySpecialOperationsCertificateLedgerMapper.selectSecuritySpecialOperationsCertificateLedgerList(securitySpecialOperationsCertificateLedger);
    }

    /**
     * 新增特种设备台账
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialOperationsCertificateLedger(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return securitySpecialOperationsCertificateLedgerMapper.insertSecuritySpecialOperationsCertificateLedger(securitySpecialOperationsCertificateLedger);
    }

    /**
     * 修改特种设备台账
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialOperationsCertificateLedger(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return securitySpecialOperationsCertificateLedgerMapper.updateSecuritySpecialOperationsCertificateLedger(securitySpecialOperationsCertificateLedger);
    }

    /**
     * 批量删除特种设备台账
     * 
     * @param ids 需要删除的特种设备台账主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsCertificateLedgerByIds(Long[] ids)
    {
        return securitySpecialOperationsCertificateLedgerMapper.deleteSecuritySpecialOperationsCertificateLedgerByIds(ids);
    }

    /**
     * 删除特种设备台账信息
     * 
     * @param id 特种设备台账主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsCertificateLedgerById(Long id)
    {
        return securitySpecialOperationsCertificateLedgerMapper.deleteSecuritySpecialOperationsCertificateLedgerById(id);
    }
}
