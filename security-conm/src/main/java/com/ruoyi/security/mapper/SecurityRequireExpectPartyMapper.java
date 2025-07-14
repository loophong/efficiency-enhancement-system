package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRequireExpectParty;

/**
 * 相关方期望Mapper接口
 *
 * @author wang
 * @date 2025-03-11
 */
public interface SecurityRequireExpectPartyMapper
{
    /**
     * 查询相关方期望
     *
     * @param id 相关方期望主键
     * @return 相关方期望
     */
    public SecurityRequireExpectParty selectSecurityRequireExpectPartyById(Long id);

    /**
     * 查询相关方期望列表
     *
     * @param securityRequireExpectParty 相关方期望
     * @return 相关方期望集合
     */
    public List<SecurityRequireExpectParty> selectSecurityRequireExpectPartyList(SecurityRequireExpectParty securityRequireExpectParty);

    /**
     * 新增相关方期望
     *
     * @param securityRequireExpectParty 相关方期望
     * @return 结果
     */
    public int insertSecurityRequireExpectParty(SecurityRequireExpectParty securityRequireExpectParty);

    /**
     * 修改相关方期望
     *
     * @param securityRequireExpectParty 相关方期望
     * @return 结果
     */
    public int updateSecurityRequireExpectParty(SecurityRequireExpectParty securityRequireExpectParty);

    /**
     * 删除相关方期望
     *
     * @param id 相关方期望主键
     * @return 结果
     */
    public int deleteSecurityRequireExpectPartyById(Long id);

    /**
     * 批量删除相关方期望
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRequireExpectPartyByIds(Long[] ids);

    /**
     * 更新最近导入数据的关联ID
     *
     * @param fileManagementId 文件管理ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long fileManagementId);


    List<SecurityRequireExpectParty> selectLatestImportedRecords();
}
