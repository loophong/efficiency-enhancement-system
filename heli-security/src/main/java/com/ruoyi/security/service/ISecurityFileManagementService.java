package com.ruoyi.security.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.security.domain.SecurityFileManagement;
import com.ruoyi.security.domain.FileStatistics;

/**
 * 文件管理Service接口
 * 
 * @author wang
 * @date 2025-07-09
 */
public interface ISecurityFileManagementService 
{
    /**
     * 查询文件管理
     * 
     * @param id 文件管理主键
     * @return 文件管理
     */
    public SecurityFileManagement selectSecurityFileManagementById(Long id);

    /**
     * 查询文件管理列表
     * 
     * @param securityFileManagement 文件管理
     * @return 文件管理集合
     */
    public List<SecurityFileManagement> selectSecurityFileManagementList(SecurityFileManagement securityFileManagement);

    /**
     * 新增文件管理
     * 
     * @param securityFileManagement 文件管理
     * @return 结果
     */
    public int insertSecurityFileManagement(SecurityFileManagement securityFileManagement);

    /**
     * 修改文件管理
     * 
     * @param securityFileManagement 文件管理
     * @return 结果
     */
    public int updateSecurityFileManagement(SecurityFileManagement securityFileManagement);

    /**
     * 批量删除文件管理
     * 
     * @param ids 需要删除的文件管理主键集合
     * @return 结果
     */
    public int deleteSecurityFileManagementByIds(Long[] ids);

    /**
     * 删除文件管理信息
     * 
     * @param id 文件管理主键
     * @return 结果
     */
    public int deleteSecurityFileManagementById(Long id);
    
    /**
     * 获取文件统计信息
     * 
     * @return 文件统计信息
     */
    public FileStatistics getFileStatistics();
    
    /**
     * 获取最近上传的文件列表
     * 
     * @param limit 限制数量
     * @return 最近上传的文件列表
     */
    public List<SecurityFileManagement> getRecentFiles(int limit);
    
    /**
     * 获取模块文件统计
     * 
     * @return 模块文件统计信息
     */
    public Map<String, Integer> getModuleStatistics();
}
