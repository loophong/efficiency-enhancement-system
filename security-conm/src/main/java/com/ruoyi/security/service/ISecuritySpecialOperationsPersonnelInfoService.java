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
}
