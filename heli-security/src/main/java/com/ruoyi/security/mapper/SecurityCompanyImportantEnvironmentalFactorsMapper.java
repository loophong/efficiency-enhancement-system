package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityCompanyImportantEnvironmentalFactors;

/**
 * 公司级重要环境因素清单Mapper接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface SecurityCompanyImportantEnvironmentalFactorsMapper 
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
     * 删除公司级重要环境因素清单
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 结果
     */
    public int deleteSecurityCompanyImportantEnvironmentalFactorsById(Long id);

    /**
     * 批量删除公司级重要环境因素清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityCompanyImportantEnvironmentalFactorsByIds(Long[] ids);
    
    /**
     * 批量插入公司级重要环境因素清单
     * 
     * @param factorsList 公司级重要环境因素清单列表
     * @return 结果
     */
    public int batchInsertFactors(List<SecurityCompanyImportantEnvironmentalFactors> factorsList);
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
