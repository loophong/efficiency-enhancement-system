package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityCompanyKeyWorksMapper;
import com.ruoyi.security.domain.SecurityCompanyKeyWorks;
import com.ruoyi.security.service.ISecurityCompanyKeyWorksService;

/**
 * 公司重点工作Service业务层处理
 * 
 * @author wang
 * @date 2025-02-27
 */
@Service
public class SecurityCompanyKeyWorksServiceImpl implements ISecurityCompanyKeyWorksService 
{
    @Autowired
    private SecurityCompanyKeyWorksMapper securityCompanyKeyWorksMapper;

    /**
     * 查询公司重点工作
     * 
     * @param id 公司重点工作主键
     * @return 公司重点工作
     */
    @Override
    public SecurityCompanyKeyWorks selectSecurityCompanyKeyWorksById(Long id)
    {
        return securityCompanyKeyWorksMapper.selectSecurityCompanyKeyWorksById(id);
    }

    /**
     * 查询公司重点工作列表
     * 
     * @param securityCompanyKeyWorks 公司重点工作
     * @return 公司重点工作
     */
    @Override
    public List<SecurityCompanyKeyWorks> selectSecurityCompanyKeyWorksList(SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return securityCompanyKeyWorksMapper.selectSecurityCompanyKeyWorksList(securityCompanyKeyWorks);
    }

    /**
     * 新增公司重点工作
     * 
     * @param securityCompanyKeyWorks 公司重点工作
     * @return 结果
     */
    @Override
    public int insertSecurityCompanyKeyWorks(SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return securityCompanyKeyWorksMapper.insertSecurityCompanyKeyWorks(securityCompanyKeyWorks);
    }

    /**
     * 修改公司重点工作
     * 
     * @param securityCompanyKeyWorks 公司重点工作
     * @return 结果
     */
    @Override
    public int updateSecurityCompanyKeyWorks(SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return securityCompanyKeyWorksMapper.updateSecurityCompanyKeyWorks(securityCompanyKeyWorks);
    }

    /**
     * 批量删除公司重点工作
     * 
     * @param ids 需要删除的公司重点工作主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCompanyKeyWorksByIds(Long[] ids)
    {
        return securityCompanyKeyWorksMapper.deleteSecurityCompanyKeyWorksByIds(ids);
    }

    /**
     * 删除公司重点工作信息
     * 
     * @param id 公司重点工作主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCompanyKeyWorksById(Long id)
    {
        return securityCompanyKeyWorksMapper.deleteSecurityCompanyKeyWorksById(id);
    }
    
    /**
     * 根据关联ID查询公司重点工作列表
     * 
     * @param relatedId 关联ID
     * @return 公司重点工作集合
     */
    @Override
    public List<SecurityCompanyKeyWorks> selectSecurityCompanyKeyWorksByRelatedId(Long relatedId)
    {
        return securityCompanyKeyWorksMapper.selectSecurityCompanyKeyWorksByRelatedId(relatedId);
    }
    
    /**
     * 更新公司重点工作的关联ID
     * 
     * @param id 公司重点工作ID
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateSecurityCompanyKeyWorksRelatedId(Long id, Long relatedId)
    {
        return securityCompanyKeyWorksMapper.updateSecurityCompanyKeyWorksRelatedId(id, relatedId);
    }
    
    /**
     * 更新最近导入的公司重点工作的关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        // 查询最近导入的数据
        List<SecurityCompanyKeyWorks> recentRecords = securityCompanyKeyWorksMapper.selectLatestImportedRecords();
        
        if (recentRecords == null || recentRecords.isEmpty()) {
            return 0;
        }
        
        int updatedCount = 0;
        
        // 更新这些记录的relatedId
        for (SecurityCompanyKeyWorks record : recentRecords) {
            record.setRelatedId(relatedId);
            updatedCount += securityCompanyKeyWorksMapper.updateSecurityCompanyKeyWorks(record);
        }
        
        return updatedCount;
    }
}
