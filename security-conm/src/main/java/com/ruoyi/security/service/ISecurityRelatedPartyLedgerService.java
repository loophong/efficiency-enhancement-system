package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelatedPartyLedger;

/**
 * 相关方管理台账Service接口
 * 
 * @author wang
 * @date 2025-04-09
 */
public interface ISecurityRelatedPartyLedgerService 
{
    /**
     * 查询相关方管理台账
     * 
     * @param id 相关方管理台账主键
     * @return 相关方管理台账
     */
    public SecurityRelatedPartyLedger selectSecurityRelatedPartyLedgerById(Long id);

    /**
     * 查询相关方管理台账列表
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 相关方管理台账集合
     */
    public List<SecurityRelatedPartyLedger> selectSecurityRelatedPartyLedgerList(SecurityRelatedPartyLedger securityRelatedPartyLedger);

    /**
     * 新增相关方管理台账
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 结果
     */
    public int insertSecurityRelatedPartyLedger(SecurityRelatedPartyLedger securityRelatedPartyLedger);

    /**
     * 修改相关方管理台账
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 结果
     */
    public int updateSecurityRelatedPartyLedger(SecurityRelatedPartyLedger securityRelatedPartyLedger);

    /**
     * 批量删除相关方管理台账
     * 
     * @param ids 需要删除的相关方管理台账主键集合
     * @return 结果
     */
    public int deleteSecurityRelatedPartyLedgerByIds(Long[] ids);

    /**
     * 删除相关方管理台账信息
     *
     * @param id 相关方管理台账主键
     * @return 结果
     */
    public int deleteSecurityRelatedPartyLedgerById(Long id);

    /**
     * 导入相关方管理台账数据
     *
     * @param relatedPartyLedgerList 相关方管理台账数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importRelatedPartyLedger(List<SecurityRelatedPartyLedger> relatedPartyLedgerList, Boolean isUpdateSupport, String operName);

    /**
     * 根据关联ID查询相关方管理台账列表
     *
     * @param relatedId 关联ID
     * @return 相关方管理台账集合
     */
    public List<SecurityRelatedPartyLedger> selectSecurityRelatedPartyLedgerByRelatedId(Long relatedId);

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
