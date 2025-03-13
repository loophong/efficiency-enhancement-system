package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityControlledDocumentDistributionDirectoryMapper;
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;
import com.ruoyi.security.service.ISecurityControlledDocumentDistributionDirectoryService;

/**
 * 受控文件发放目录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityControlledDocumentDistributionDirectoryServiceImpl implements ISecurityControlledDocumentDistributionDirectoryService 
{
    @Autowired
    private SecurityControlledDocumentDistributionDirectoryMapper securityControlledDocumentDistributionDirectoryMapper;

    /**
     * 查询受控文件发放目录
     * 
     * @param id 受控文件发放目录主键
     * @return 受控文件发放目录
     */
    @Override
    public SecurityControlledDocumentDistributionDirectory selectSecurityControlledDocumentDistributionDirectoryById(Long id)
    {
        return securityControlledDocumentDistributionDirectoryMapper.selectSecurityControlledDocumentDistributionDirectoryById(id);
    }

    /**
     * 查询受控文件发放目录列表
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 受控文件发放目录
     */
    @Override
    public List<SecurityControlledDocumentDistributionDirectory> selectSecurityControlledDocumentDistributionDirectoryList(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return securityControlledDocumentDistributionDirectoryMapper.selectSecurityControlledDocumentDistributionDirectoryList(securityControlledDocumentDistributionDirectory);
    }

    /**
     * 新增受控文件发放目录
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 结果
     */
    @Override
    public int insertSecurityControlledDocumentDistributionDirectory(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return securityControlledDocumentDistributionDirectoryMapper.insertSecurityControlledDocumentDistributionDirectory(securityControlledDocumentDistributionDirectory);
    }

    /**
     * 修改受控文件发放目录
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 结果
     */
    @Override
    public int updateSecurityControlledDocumentDistributionDirectory(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return securityControlledDocumentDistributionDirectoryMapper.updateSecurityControlledDocumentDistributionDirectory(securityControlledDocumentDistributionDirectory);
    }

    /**
     * 批量删除受控文件发放目录
     * 
     * @param ids 需要删除的受控文件发放目录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityControlledDocumentDistributionDirectoryByIds(Long[] ids)
    {
        return securityControlledDocumentDistributionDirectoryMapper.deleteSecurityControlledDocumentDistributionDirectoryByIds(ids);
    }

    /**
     * 删除受控文件发放目录信息
     * 
     * @param id 受控文件发放目录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityControlledDocumentDistributionDirectoryById(Long id)
    {
        return securityControlledDocumentDistributionDirectoryMapper.deleteSecurityControlledDocumentDistributionDirectoryById(id);
    }
}
