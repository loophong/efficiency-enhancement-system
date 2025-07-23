package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecutityEnvironmentalFactorsImpact;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境因素清单Service接口
 * 
 * @author wang
 * @date 2025-07-14
 */
public interface ISecutityEnvironmentalFactorsImpactService 
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
     * 批量删除环境因素清单
     * 
     * @param ids 需要删除的环境因素清单主键集合
     * @return 结果
     */
    public int deleteSecutityEnvironmentalFactorsImpactByIds(Long[] ids);

    /**
     * 删除环境因素清单信息
     * 
     * @param id 环境因素清单主键
     * @return 结果
     */
    public int deleteSecutityEnvironmentalFactorsImpactById(Long id);
    
    /**
     * 导入环境因素清单数据
     * 
     * @param file 导入文件
     * @param updateSupport 是否更新已存在数据
     * @return 结果
     */
    public String importData(MultipartFile file, boolean updateSupport);
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
