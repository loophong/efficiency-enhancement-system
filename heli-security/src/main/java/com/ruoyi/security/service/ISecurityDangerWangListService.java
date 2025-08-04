package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityDangerWangList;

/**
 * 风险网格化清单Service接口
 * 
 * @author ruoyi
 * @date 2025-07-30
 */
public interface ISecurityDangerWangListService 
{
    /**
     * 查询风险网格化清单
     * 
     * @param id 风险网格化清单主键
     * @return 风险网格化清单
     */
    public SecurityDangerWangList selectSecurityDangerWangListById(String id);

    /**
     * 查询风险网格化清单列表
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 风险网格化清单集合
     */
    public List<SecurityDangerWangList> selectSecurityDangerWangListList(SecurityDangerWangList securityDangerWangList);

    /**
     * 新增风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    public int insertSecurityDangerWangList(SecurityDangerWangList securityDangerWangList);

    /**
     * 修改风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    public int updateSecurityDangerWangList(SecurityDangerWangList securityDangerWangList);

    /**
     * 批量删除风险网格化清单
     * 
     * @param ids 需要删除的风险网格化清单主键集合
     * @return 结果
     */
    public int deleteSecurityDangerWangListByIds(String[] ids);

    /**
     * 删除风险网格化清单信息
     * 
     * @param id 风险网格化清单主键
     * @return 结果
     */
    public int deleteSecurityDangerWangListById(String id);
    
    /**
     * 导入风险网格化清单数据
     * 
     * @param dangerWangList 风险网格化清单数据列表
     * @param updateSupport 是否支持更新
     * @param operName 操作用户
     * @return 结果
     */
    public String importDangerWangList(List<SecurityDangerWangList> dangerWangList, boolean updateSupport, String operName);
    
    /**
     * 更新最近导入的风险网格化清单关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
    
    /**
     * 根据关联ID查询风险网格化清单数据
     * 
     * @param relatedId 关联ID
     * @return 风险网格化清单集合
     */
    public List<SecurityDangerWangList> selectSecurityDangerWangListByRelatedId(String relatedId);
}
