package com.ruoyi.security.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityFileManagementMapper;
import com.ruoyi.security.domain.SecurityFileManagement;
import com.ruoyi.security.service.ISecurityFileManagementService;

/**
 * 文件管理Service业务层处理
 * 
 * @author wang
 * @date 2025-07-09
 */
@Service
public class SecurityFileManagementServiceImpl implements ISecurityFileManagementService 
{
    @Autowired
    private SecurityFileManagementMapper securityFileManagementMapper;

    /**
     * 查询文件管理
     * 
     * @param id 文件管理主键
     * @return 文件管理
     */
    @Override
    public SecurityFileManagement selectSecurityFileManagementById(Long id)
    {
        return securityFileManagementMapper.selectSecurityFileManagementById(id);
    }

    /**
     * 查询文件管理列表
     * 
     * @param securityFileManagement 文件管理
     * @return 文件管理
     */
    @Override
    public List<SecurityFileManagement> selectSecurityFileManagementList(SecurityFileManagement securityFileManagement)
    {
        return securityFileManagementMapper.selectSecurityFileManagementList(securityFileManagement);
    }

    /**
     * 新增文件管理
     * 
     * @param securityFileManagement 文件管理
     * @return 结果
     */
    @Override
    public int insertSecurityFileManagement(SecurityFileManagement securityFileManagement)
    {
        securityFileManagement.setCreateTime(DateUtils.getNowDate());
        return securityFileManagementMapper.insertSecurityFileManagement(securityFileManagement);
    }

    /**
     * 修改文件管理
     * 
     * @param securityFileManagement 文件管理
     * @return 结果
     */
    @Override
    public int updateSecurityFileManagement(SecurityFileManagement securityFileManagement)
    {
        securityFileManagement.setUpdateTime(DateUtils.getNowDate());
        return securityFileManagementMapper.updateSecurityFileManagement(securityFileManagement);
    }

    /**
     * 批量删除文件管理
     * 
     * @param ids 需要删除的文件管理主键
     * @return 结果
     */
    @Override
    public int deleteSecurityFileManagementByIds(Long[] ids)
    {
        return securityFileManagementMapper.deleteSecurityFileManagementByIds(ids);
    }

    /**
     * 删除文件管理信息
     * 
     * @param id 文件管理主键
     * @return 结果
     */
    @Override
    public int deleteSecurityFileManagementById(Long id)
    {
        return securityFileManagementMapper.deleteSecurityFileManagementById(id);
    }
}
