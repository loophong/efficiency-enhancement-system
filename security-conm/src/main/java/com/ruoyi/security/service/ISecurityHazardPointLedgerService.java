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

    /**
     * 根据关联ID查询有点害台账列表
     *
     * @param relatedId 关联ID
     * @return 有点害台账集合
     */
    public List<SecurityHazardPointLedger> selectByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);

    /**
     * 导入有点害台账数据
     *
     * @param hazardPointLedgerList 有点害台账数据列表
     * @param isUpdateSupport 是否更新支持
     * @param operName 操作用户
     * @return 结果
     */
    public String importHazardPointLedgerList(List<SecurityHazardPointLedger> hazardPointLedgerList, Boolean isUpdateSupport, String operName);
}
