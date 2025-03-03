package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;

/**
 * 受控文件发放目录Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecurityControlledDocumentDistributionDirectoryService 
{
    /**
     * 查询受控文件发放目录
     * 
     * @param id 受控文件发放目录主键
     * @return 受控文件发放目录
     */
    public SecurityControlledDocumentDistributionDirectory selectSecurityControlledDocumentDistributionDirectoryById(Long id);

    /**
     * 查询受控文件发放目录列表
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 受控文件发放目录集合
     */
    public List<SecurityControlledDocumentDistributionDirectory> selectSecurityControlledDocumentDistributionDirectoryList(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory);

    /**
     * 新增受控文件发放目录
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 结果
     */
    public int insertSecurityControlledDocumentDistributionDirectory(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory);

    /**
     * 修改受控文件发放目录
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 结果
     */
    public int updateSecurityControlledDocumentDistributionDirectory(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory);

    /**
     * 批量删除受控文件发放目录
     * 
     * @param ids 需要删除的受控文件发放目录主键集合
     * @return 结果
     */
    public int deleteSecurityControlledDocumentDistributionDirectoryByIds(Long[] ids);

    /**
     * 删除受控文件发放目录信息
     * 
     * @param id 受控文件发放目录主键
     * @return 结果
     */
    public int deleteSecurityControlledDocumentDistributionDirectoryById(Long id);
}
