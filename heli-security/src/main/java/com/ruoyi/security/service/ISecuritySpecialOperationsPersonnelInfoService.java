package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySpecialOperationsPersonnelInfo;

/**
 * 特种作业人员信息Service接口
 * 
 * @author wang
 * @date 2025-03-05
 */
public interface ISecuritySpecialOperationsPersonnelInfoService 
{
    /**
     * 查询特种作业人员信息
     * 
     * @param id 特种作业人员信息主键
     * @return 特种作业人员信息
     */
    public SecuritySpecialOperationsPersonnelInfo selectSecuritySpecialOperationsPersonnelInfoById(Long id);

    /**
     * 查询特种作业人员信息列表
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 特种作业人员信息集合
     */
    public List<SecuritySpecialOperationsPersonnelInfo> selectSecuritySpecialOperationsPersonnelInfoList(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo);

    /**
     * 新增特种作业人员信息
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 结果
     */
    public int insertSecuritySpecialOperationsPersonnelInfo(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo);

    /**
     * 修改特种作业人员信息
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 结果
     */
    public int updateSecuritySpecialOperationsPersonnelInfo(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo);

    /**
     * 批量删除特种作业人员信息
     * 
     * @param ids 需要删除的特种作业人员信息主键集合
     * @return 结果
     */
    public int deleteSecuritySpecialOperationsPersonnelInfoByIds(Long[] ids);

    /**
     * 删除特种作业人员信息信息
     *
     * @param id 特种作业人员信息主键
     * @return 结果
     */
    public int deleteSecuritySpecialOperationsPersonnelInfoById(Long id);

    /**
     * 导入特种作业人员信息数据
     *
     * @param personnelInfoList 特种作业人员信息数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSpecialOperationsPersonnelInfo(List<SecuritySpecialOperationsPersonnelInfo> personnelInfoList, Boolean isUpdateSupport, String operName);

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
