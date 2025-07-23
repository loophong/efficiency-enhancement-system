package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;

/**
 * 受控文件发放目录Mapper接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface SecurityControlledDocumentDistributionDirectoryMapper 
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
     * 删除受控文件发放目录
     * 
     * @param id 受控文件发放目录主键
     * @return 结果
     */
    public int deleteSecurityControlledDocumentDistributionDirectoryById(Long id);

    /**
     * 批量删除受控文件发放目录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityControlledDocumentDistributionDirectoryByIds(Long[] ids);

    /**
     * 查询最近导入的记录（未关联ID的记录）
     *
     * @return 受控文件发放目录集合
     */
    public List<SecurityControlledDocumentDistributionDirectory> selectLatestImportedRecords();

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);

    void batchInsert(List<SecurityControlledDocumentDistributionDirectory> list);
}
