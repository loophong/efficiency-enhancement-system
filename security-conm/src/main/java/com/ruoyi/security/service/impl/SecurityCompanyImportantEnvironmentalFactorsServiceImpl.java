package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityCompanyImportantEnvironmentalFactorsMapper;
import com.ruoyi.security.domain.SecurityCompanyImportantEnvironmentalFactors;
import com.ruoyi.security.service.ISecurityCompanyImportantEnvironmentalFactorsService;

/**
 * 公司级重要环境因素清单Service业务层处理
 * 
 * @author wang
 * @date 2025-02-27
 */
@Service
public class SecurityCompanyImportantEnvironmentalFactorsServiceImpl implements ISecurityCompanyImportantEnvironmentalFactorsService 
{
    @Autowired
    private SecurityCompanyImportantEnvironmentalFactorsMapper securityCompanyImportantEnvironmentalFactorsMapper;

    /**
     * 查询公司级重要环境因素清单
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 公司级重要环境因素清单
     */
    @Override
    public SecurityCompanyImportantEnvironmentalFactors selectSecurityCompanyImportantEnvironmentalFactorsById(Long id)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.selectSecurityCompanyImportantEnvironmentalFactorsById(id);
    }

    /**
     * 查询公司级重要环境因素清单列表
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 公司级重要环境因素清单
     */
    @Override
    public List<SecurityCompanyImportantEnvironmentalFactors> selectSecurityCompanyImportantEnvironmentalFactorsList(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.selectSecurityCompanyImportantEnvironmentalFactorsList(securityCompanyImportantEnvironmentalFactors);
    }

    /**
     * 新增公司级重要环境因素清单
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 结果
     */
    @Override
    public int insertSecurityCompanyImportantEnvironmentalFactors(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.insertSecurityCompanyImportantEnvironmentalFactors(securityCompanyImportantEnvironmentalFactors);
    }

    /**
     * 修改公司级重要环境因素清单
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 结果
     */
    @Override
    public int updateSecurityCompanyImportantEnvironmentalFactors(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.updateSecurityCompanyImportantEnvironmentalFactors(securityCompanyImportantEnvironmentalFactors);
    }

    /**
     * 批量删除公司级重要环境因素清单
     * 
     * @param ids 需要删除的公司级重要环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCompanyImportantEnvironmentalFactorsByIds(Long[] ids)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.deleteSecurityCompanyImportantEnvironmentalFactorsByIds(ids);
    }

    /**
     * 删除公司级重要环境因素清单信息
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCompanyImportantEnvironmentalFactorsById(Long id)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.deleteSecurityCompanyImportantEnvironmentalFactorsById(id);
    }
}
