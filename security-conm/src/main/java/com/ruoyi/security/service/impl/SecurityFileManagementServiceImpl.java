package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityFileManagementMapper;
import com.ruoyi.security.domain.SecurityFileManagement;
import com.ruoyi.security.domain.FileStatistics;
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
    
    /**
     * 获取文件统计信息
     * 
     * @return 文件统计信息
     */
    @Override
    public FileStatistics getFileStatistics()
    {
        // 获取所有文件列表
        List<SecurityFileManagement> fileList = securityFileManagementMapper.selectSecurityFileManagementList(new SecurityFileManagement());
        
        // 创建统计对象
        FileStatistics statistics = new FileStatistics();
        
        // 设置文件总数
        statistics.setTotalFiles(fileList.size());
        
        // 统计上传和导入文件数量
        int uploadCount = 0;
        int importCount = 0;
        Set<String> moduleSet = new HashSet<>();
        
        for (SecurityFileManagement file : fileList)
        {
            // 统计文件分类
            if ("UPLOAD".equals(file.getFileCategory()))
            {
                uploadCount++;
            }
            else if ("IMPORT".equals(file.getFileCategory()))
            {
                importCount++;
            }
            
            // 统计模块数量
            if (file.getModuleName() != null)
            {
                moduleSet.add(file.getModuleName());
            }
        }
        
        statistics.setUploadFiles(uploadCount);
        statistics.setImportFiles(importCount);
        statistics.setModuleCount(moduleSet.size());
        
        return statistics;
    }
    
    /**
     * 获取最近上传的文件列表
     * 
     * @param limit 限制数量
     * @return 最近上传的文件列表
     */
    @Override
    public List<SecurityFileManagement> getRecentFiles(int limit)
    {
        // 创建查询对象
        SecurityFileManagement query = new SecurityFileManagement();
        
        // 查询所有文件并按上传时间排序
        List<SecurityFileManagement> allFiles = securityFileManagementMapper.selectSecurityFileManagementList(query);
        
        // 按上传时间降序排序并限制数量
        return allFiles.stream()
                .sorted((a, b) -> b.getUploadTime().compareTo(a.getUploadTime()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取模块文件统计
     * 
     * @return 模块文件统计信息
     */
    @Override
    public Map<String, Integer> getModuleStatistics()
    {
        // 获取所有文件列表
        List<SecurityFileManagement> fileList = securityFileManagementMapper.selectSecurityFileManagementList(new SecurityFileManagement());
        
        // 创建模块统计映射
        Map<String, Integer> moduleStats = new HashMap<>();
        
        // 统计每个模块的文件数量
        for (SecurityFileManagement file : fileList)
        {
            String moduleName = file.getModuleName();
            if (moduleName != null)
            {
                moduleStats.put(moduleName, moduleStats.getOrDefault(moduleName, 0) + 1);
            }
        }
        
        return moduleStats;
    }
}
