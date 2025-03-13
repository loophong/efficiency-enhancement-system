package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityRegulationList;

/**
 * 安全制度清单Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityRegulationListService 
{
    /**
     * 查询安全制度清单
     * 
     * @param id 安全制度清单主键
     * @return 安全制度清单
     */
    public SecurityRegulationList selectSecurityRegulationListById(Long id);

    /**
     * 查询安全制度清单列表
     * 
     * @param securityRegulationList 安全制度清单
     * @return 安全制度清单集合
     */
    public List<SecurityRegulationList> selectSecurityRegulationListList(SecurityRegulationList securityRegulationList);

    /**
     * 新增安全制度清单
     * 
     * @param securityRegulationList 安全制度清单
     * @return 结果
     */
    public int insertSecurityRegulationList(SecurityRegulationList securityRegulationList);

    /**
     * 修改安全制度清单
     * 
     * @param securityRegulationList 安全制度清单
     * @return 结果
     */
    public int updateSecurityRegulationList(SecurityRegulationList securityRegulationList);

    /**
     * 批量删除安全制度清单
     * 
     * @param ids 需要删除的安全制度清单主键集合
     * @return 结果
     */
    public int deleteSecurityRegulationListByIds(Long[] ids);

    /**
     * 删除安全制度清单信息
     * 
     * @param id 安全制度清单主键
     * @return 结果
     */
    public int deleteSecurityRegulationListById(Long id);
}
