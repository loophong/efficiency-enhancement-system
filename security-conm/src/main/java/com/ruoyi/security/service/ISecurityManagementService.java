package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityManagement;

/**
 * 管理手册Service接口
 * 
 * @author 王
 * @date 2025-01-13
 */
public interface ISecurityManagementService 
{
    /**
     * 查询管理手册
     * 
     * @param id 管理手册主键
     * @return 管理手册
     */
    public SecurityManagement selectSecurityManagementById(Long id);

    /**
     * 查询管理手册列表
     * 
     * @param securityManagement 管理手册
     * @return 管理手册集合
     */
    public List<SecurityManagement> selectSecurityManagementList(SecurityManagement securityManagement);

    /**
     * 新增管理手册
     * 
     * @param securityManagement 管理手册
     * @return 结果
     */
    public int insertSecurityManagement(SecurityManagement securityManagement);

    /**
     * 修改管理手册
     * 
     * @param securityManagement 管理手册
     * @return 结果
     */
    public int updateSecurityManagement(SecurityManagement securityManagement);

    /**
     * 批量删除管理手册
     * 
     * @param ids 需要删除的管理手册主键集合
     * @return 结果
     */
    public int deleteSecurityManagementByIds(Long[] ids);

    /**
     * 删除管理手册信息
     * 
     * @param id 管理手册主键
     * @return 结果
     */
    public int deleteSecurityManagementById(Long id);
}
