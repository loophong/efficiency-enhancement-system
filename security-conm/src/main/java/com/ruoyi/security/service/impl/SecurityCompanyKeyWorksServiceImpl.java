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
}
