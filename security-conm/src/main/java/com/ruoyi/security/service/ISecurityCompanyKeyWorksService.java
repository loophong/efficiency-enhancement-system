package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityCompanyKeyWorks;

/**
 * 公司重点工作Service接口
 * 
 * @author wang
 * @date 2025-02-27
 */
public interface ISecurityCompanyKeyWorksService 
{
    /**
     * 查询公司重点工作
     * 
     * @param id 公司重点工作主键
     * @return 公司重点工作
     */
    public SecurityCompanyKeyWorks selectSecurityCompanyKeyWorksById(Long id);

    /**
     * 查询公司重点工作列表
     * 
     * @param securityCompanyKeyWorks 公司重点工作
     * @return 公司重点工作集合
     */
    public List<SecurityCompanyKeyWorks> selectSecurityCompanyKeyWorksList(SecurityCompanyKeyWorks securityCompanyKeyWorks);

    /**
     * 新增公司重点工作
     * 
     * @param securityCompanyKeyWorks 公司重点工作
     * @return 结果
     */
    public int insertSecurityCompanyKeyWorks(SecurityCompanyKeyWorks securityCompanyKeyWorks);

    /**
     * 修改公司重点工作
     * 
     * @param securityCompanyKeyWorks 公司重点工作
     * @return 结果
     */
    public int updateSecurityCompanyKeyWorks(SecurityCompanyKeyWorks securityCompanyKeyWorks);

    /**
     * 批量删除公司重点工作
     * 
     * @param ids 需要删除的公司重点工作主键集合
     * @return 结果
     */
    public int deleteSecurityCompanyKeyWorksByIds(Long[] ids);

    /**
     * 删除公司重点工作信息
     * 
     * @param id 公司重点工作主键
     * @return 结果
     */
    public int deleteSecurityCompanyKeyWorksById(Long id);
    
    /**
     * 根据关联ID查询公司重点工作列表
     * 
     * @param relatedId 关联ID
     * @return 公司重点工作集合
     */
    public List<SecurityCompanyKeyWorks> selectSecurityCompanyKeyWorksByRelatedId(Long relatedId);
    
    /**
     * 更新公司重点工作的关联ID
     * 
     * @param id 公司重点工作ID
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateSecurityCompanyKeyWorksRelatedId(Long id, Long relatedId);
    
    /**
     * 更新最近导入的公司重点工作的关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
