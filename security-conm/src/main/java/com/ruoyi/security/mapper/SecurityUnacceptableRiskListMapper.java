package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityUnacceptableRiskList;

/**
 * 不可接受风险清单Mapper接口
 * 
 * @author wang
 * @date 2025-03-01
 */
public interface SecurityUnacceptableRiskListMapper 
{
    /**
     * 查询不可接受风险清单
     * 
     * @param id 不可接受风险清单主键
     * @return 不可接受风险清单
     */
    public SecurityUnacceptableRiskList selectSecurityUnacceptableRiskListById(Long id);

    /**
     * 查询不可接受风险清单列表
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 不可接受风险清单集合
     */
    public List<SecurityUnacceptableRiskList> selectSecurityUnacceptableRiskListList(SecurityUnacceptableRiskList securityUnacceptableRiskList);

    /**
     * 新增不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    public int insertSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList);

    /**
     * 修改不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    public int updateSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList);

    /**
     * 删除不可接受风险清单
     * 
     * @param id 不可接受风险清单主键
     * @return 结果
     */
    public int deleteSecurityUnacceptableRiskListById(Long id);

    /**
     * 批量删除不可接受风险清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityUnacceptableRiskListByIds(Long[] ids);
}
