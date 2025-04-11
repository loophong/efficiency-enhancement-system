package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelatedPartyLedger;

/**
 * 相关方管理台账Mapper接口
 * 
 * @author wang
 * @date 2025-04-09
 */
public interface SecurityRelatedPartyLedgerMapper 
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
     * 删除相关方管理台账
     * 
     * @param id 相关方管理台账主键
     * @return 结果
     */
    public int deleteSecurityRelatedPartyLedgerById(Long id);

    /**
     * 批量删除相关方管理台账
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRelatedPartyLedgerByIds(Long[] ids);
}
