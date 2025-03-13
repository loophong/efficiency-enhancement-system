package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardPointLedger;

/**
 * 有点害台账Mapper接口
 * 
 * @author wang
 * @date 2025-03-05
 */
public interface SecurityHazardPointLedgerMapper 
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
     * 删除有点害台账
     * 
     * @param id 有点害台账主键
     * @return 结果
     */
    public int deleteSecurityHazardPointLedgerById(Long id);

    /**
     * 批量删除有点害台账
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityHazardPointLedgerByIds(Long[] ids);
}
