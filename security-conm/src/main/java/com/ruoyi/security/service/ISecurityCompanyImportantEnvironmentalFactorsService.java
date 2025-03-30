package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityCompanyImportantEnvironmentalFactors;

/**
 * 公司级重要环境因素清单Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityCompanyImportantEnvironmentalFactorsService 
{
    /**
     * 查询公司级重要环境因素清单
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 公司级重要环境因素清单
     */
    public SecurityCompanyImportantEnvironmentalFactors selectSecurityCompanyImportantEnvironmentalFactorsById(Long id);

    /**
     * 查询公司级重要环境因素清单列表
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 公司级重要环境因素清单集合
     */
    public List<SecurityCompanyImportantEnvironmentalFactors> selectSecurityCompanyImportantEnvironmentalFactorsList(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors);

    /**
     * 新增公司级重要环境因素清单
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 结果
     */
    public int insertSecurityCompanyImportantEnvironmentalFactors(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors);

    /**
     * 修改公司级重要环境因素清单
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 结果
     */
    public int updateSecurityCompanyImportantEnvironmentalFactors(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors);

    /**
     * 批量删除公司级重要环境因素清单
     * 
     * @param ids 需要删除的公司级重要环境因素清单主键集合
     * @return 结果
     */
    public int deleteSecurityCompanyImportantEnvironmentalFactorsByIds(Long[] ids);

    /**
     * 删除公司级重要环境因素清单信息
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 结果
     */
    public int deleteSecurityCompanyImportantEnvironmentalFactorsById(Long id);
}
