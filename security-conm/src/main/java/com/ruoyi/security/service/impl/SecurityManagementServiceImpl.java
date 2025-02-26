package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityManagementMapper;
import com.ruoyi.security.domain.SecurityManagement;
import com.ruoyi.security.service.ISecurityManagementService;

/**
 * 管理手册Service业务层处理
 * 
 * @author 王
 * @date 2025-01-13
 */
@Service
public class SecurityManagementServiceImpl implements ISecurityManagementService 
{
    @Autowired
    private SecurityManagementMapper securityManagementMapper;

    /**
     * 查询管理手册
     * 
     * @param id 管理手册主键
     * @return 管理手册
     */
    @Override
    public SecurityManagement selectSecurityManagementById(Long id)
    {
        return securityManagementMapper.selectSecurityManagementById(id);
    }

    /**
     * 查询管理手册列表
     * 
     * @param securityManagement 管理手册
     * @return 管理手册
     */
    @Override
    public List<SecurityManagement> selectSecurityManagementList(SecurityManagement securityManagement)
    {
        return securityManagementMapper.selectSecurityManagementList(securityManagement);
    }

    /**
     * 新增管理手册
     * 
     * @param securityManagement 管理手册
     * @return 结果
     */
    @Override
    public int insertSecurityManagement(SecurityManagement securityManagement)
    {
        return securityManagementMapper.insertSecurityManagement(securityManagement);
    }

    /**
     * 修改管理手册
     * 
     * @param securityManagement 管理手册
     * @return 结果
     */
    @Override
    public int updateSecurityManagement(SecurityManagement securityManagement)
    {
        return securityManagementMapper.updateSecurityManagement(securityManagement);
    }

    /**
     * 批量删除管理手册
     * 
     * @param ids 需要删除的管理手册主键
     * @return 结果
     */
    @Override
    public int deleteSecurityManagementByIds(Long[] ids)
    {
        return securityManagementMapper.deleteSecurityManagementByIds(ids);
    }

    /**
     * 删除管理手册信息
     * 
     * @param id 管理手册主键
     * @return 结果
     */
    @Override
    public int deleteSecurityManagementById(Long id)
    {
        return securityManagementMapper.deleteSecurityManagementById(id);
    }
}
