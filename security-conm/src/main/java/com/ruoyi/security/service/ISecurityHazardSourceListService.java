package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardSourceList;

/**
 * 危险源清单Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityHazardSourceListService 
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
     * 批量删除危险源清单
     * 
     * @param ids 需要删除的危险源清单主键集合
     * @return 结果
     */
    public int deleteSecurityHazardSourceListByIds(Long[] ids);

    /**
     * 删除危险源清单信息
     * 
     * @param id 危险源清单主键
     * @return 结果
     */
    public int deleteSecurityHazardSourceListById(Long id);
}
