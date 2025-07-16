package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardSourceList;

/**
 * 危险源清单Mapper接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface SecurityHazardSourceListMapper 
{
    /**
     * 查询危险源清单
     * 
     * @param id 危险源清单主键
     * @return 危险源清单
     */
    public SecurityHazardSourceList selectSecurityHazardSourceListById(Long id);

    /**
     * 查询危险源清单列表
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 危险源清单集合
     */
    public List<SecurityHazardSourceList> selectSecurityHazardSourceListList(SecurityHazardSourceList securityHazardSourceList);

    /**
     * 新增危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    public int insertSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList);

    /**
     * 修改危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    public int updateSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList);

    /**
     * 删除危险源清单
     * 
     * @param id 危险源清单主键
     * @return 结果
     */
    public int deleteSecurityHazardSourceListById(Long id);

    /**
     * 批量删除危险源清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityHazardSourceListByIds(Long[] ids);
    
    /**
     * 批量插入危险源清单
     * 
     * @param riskList 危险源清单列表
     * @return 结果
     */
    public int batchInsertRiskList(List<SecurityHazardSourceList> riskList);
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
