package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardousMaterialsHandlingLedger;

/**
 * 危化品处理台账Service接口
 * 
 * @author wang
 * @date 2025-03-03
 */
public interface ISecurityHazardousMaterialsHandlingLedgerService 
{
    /**
     * 查询危化品处理台账
     * 
     * @param id 危化品处理台账主键
     * @return 危化品处理台账
     */
    public SecurityHazardousMaterialsHandlingLedger selectSecurityHazardousMaterialsHandlingLedgerById(Long id);

    /**
     * 查询危化品处理台账列表
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 危化品处理台账集合
     */
    public List<SecurityHazardousMaterialsHandlingLedger> selectSecurityHazardousMaterialsHandlingLedgerList(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger);

    /**
     * 新增危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    public int insertSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger);

    /**
     * 修改危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    public int updateSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger);

    /**
     * 批量删除危化品处理台账
     * 
     * @param ids 需要删除的危化品处理台账主键集合
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsHandlingLedgerByIds(Long[] ids);

    /**
     * 删除危化品处理台账信息
     * 
     * @param id 危化品处理台账主键
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsHandlingLedgerById(Long id);
}
