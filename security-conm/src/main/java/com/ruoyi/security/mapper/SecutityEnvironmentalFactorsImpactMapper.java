package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecutityEnvironmentalFactorsImpact;

/**
 * 环境因素清单Mapper接口
 * 
 * @author wang
 * @date 2025-07-14
 */
public interface SecutityEnvironmentalFactorsImpactMapper 
{
    /**
     * 查询环境因素清单
     * 
     * @param id 环境因素清单主键
     * @return 环境因素清单
     */
    public SecutityEnvironmentalFactorsImpact selectSecutityEnvironmentalFactorsImpactById(Long id);

    /**
     * 查询环境因素清单列表
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 环境因素清单集合
     */
    public List<SecutityEnvironmentalFactorsImpact> selectSecutityEnvironmentalFactorsImpactList(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact);

    /**
     * 新增环境因素清单
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 结果
     */
    public int insertSecutityEnvironmentalFactorsImpact(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact);

    /**
     * 修改环境因素清单
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 结果
     */
    public int updateSecutityEnvironmentalFactorsImpact(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact);

    /**
     * 删除环境因素清单
     * 
     * @param id 环境因素清单主键
     * @return 结果
     */
    public int deleteSecutityEnvironmentalFactorsImpactById(Long id);

    /**
     * 批量删除环境因素清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecutityEnvironmentalFactorsImpactByIds(Long[] ids);
    
    /**
     * 批量插入环境因素清单
     * 
     * @param impactList 环境因素清单列表
     * @return 结果
     */
    public int batchInsertImpacts(List<SecutityEnvironmentalFactorsImpact> impactList);
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
