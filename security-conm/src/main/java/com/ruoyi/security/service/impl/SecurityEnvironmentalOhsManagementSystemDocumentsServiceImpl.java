package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOhsManagementSystemDocumentsMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import com.ruoyi.security.service.ISecurityEnvironmentalOhsManagementSystemDocumentsService;

/**
 * 环境职业健康安全管理体系文件清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityEnvironmentalOhsManagementSystemDocumentsServiceImpl implements ISecurityEnvironmentalOhsManagementSystemDocumentsService 
{
    @Autowired
    private SecurityEnvironmentalOhsManagementSystemDocumentsMapper securityEnvironmentalOhsManagementSystemDocumentsMapper;

    /**
     * 查询环境职业健康安全管理体系文件清单
     * 
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 环境职业健康安全管理体系文件清单
     */
    @Override
    public SecurityEnvironmentalOhsManagementSystemDocuments selectSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id)
    {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.selectSecurityEnvironmentalOhsManagementSystemDocumentsById(id);
    }

    /**
     * 查询环境职业健康安全管理体系文件清单列表
     * 
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 环境职业健康安全管理体系文件清单
     */
    @Override
    public List<SecurityEnvironmentalOhsManagementSystemDocuments> selectSecurityEnvironmentalOhsManagementSystemDocumentsList(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.selectSecurityEnvironmentalOhsManagementSystemDocumentsList(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 新增环境职业健康安全管理体系文件清单
     * 
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.insertSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 修改环境职业健康安全管理体系文件清单
     * 
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.updateSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 批量删除环境职业健康安全管理体系文件清单
     * 
     * @param ids 需要删除的环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(Long[] ids)
    {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理体系文件清单信息
     * 
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id)
    {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(id);
    }
}
