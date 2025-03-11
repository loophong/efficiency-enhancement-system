package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardPointLedger;

/**
 * 有点害台账Service接口
 * 
 * @author wang
 * @date 2025-03-05
 */
public interface ISecurityHazardPointLedgerService 
{
    /**
     * 查询有点害台账
     * 
     * @param id 有点害台账主键
     * @return 有点害台账
     */
    public SecurityHazardPointLedger selectSecurityHazardPointLedgerById(Long id);

    /**
     * 查询有点害台账列表
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 有点害台账集合
     */
    public List<SecurityHazardPointLedger> selectSecurityHazardPointLedgerList(SecurityHazardPointLedger securityHazardPointLedger);

    /**
     * 新增有点害台账
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 结果
     */
    public int insertSecurityHazardPointLedger(SecurityHazardPointLedger securityHazardPointLedger);

    /**
     * 修改有点害台账
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 结果
     */
    public int updateSecurityHazardPointLedger(SecurityHazardPointLedger securityHazardPointLedger);

    /**
     * 批量删除有点害台账
     * 
     * @param ids 需要删除的有点害台账主键集合
     * @return 结果
     */
    public int deleteSecurityHazardPointLedgerByIds(Long[] ids);

    /**
     * 删除有点害台账信息
     * 
     * @param id 有点害台账主键
     * @return 结果
     */
    public int deleteSecurityHazardPointLedgerById(Long id);
}
