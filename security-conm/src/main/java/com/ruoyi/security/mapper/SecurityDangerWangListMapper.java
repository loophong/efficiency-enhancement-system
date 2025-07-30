package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityDangerWangList;

/**
 * 风险网格化清单Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-30
 */
public interface SecurityDangerWangListMapper 
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
     * 删除风险网格化清单
     * 
     * @param id 风险网格化清单主键
     * @return 结果
     */
    public int deleteSecurityDangerWangListById(String id);

    /**
     * 批量删除风险网格化清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityDangerWangListByIds(String[] ids);
    
    /**
     * 查询最近导入且没有关联ID的风险网格化清单记录
     * 
     * @param limit 限制查询的记录数
     * @return 风险网格化清单集合
     */
    public List<SecurityDangerWangList> selectLatestImportedWithoutRelatedId(int limit);
    
    /**
     * 根据关联ID查询风险网格化清单数据
     * 
     * @param relatedId 关联ID
     * @return 风险网格化清单集合
     */
    public List<SecurityDangerWangList> selectSecurityDangerWangListByRelatedId(String relatedId);
}
