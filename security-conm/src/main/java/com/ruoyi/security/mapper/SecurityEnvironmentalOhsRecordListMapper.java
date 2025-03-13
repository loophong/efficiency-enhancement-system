package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsRecordList;

/**
 * 环境职业健康安全记录清单Mapper接口
 * 
 * @author wang
 * @date 2025-03-07
 */
public interface SecurityEnvironmentalOhsRecordListMapper 
{
    /**
     * 查询环境职业健康安全记录清单
     * 
     * @param id 环境职业健康安全记录清单主键
     * @return 环境职业健康安全记录清单
     */
    public SecurityEnvironmentalOhsRecordList selectSecurityEnvironmentalOhsRecordListById(Long id);

    /**
     * 查询环境职业健康安全记录清单列表
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 环境职业健康安全记录清单集合
     */
    public List<SecurityEnvironmentalOhsRecordList> selectSecurityEnvironmentalOhsRecordListList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList);

    /**
     * 新增环境职业健康安全记录清单
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 结果
     */
    public int insertSecurityEnvironmentalOhsRecordList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList);

    /**
     * 修改环境职业健康安全记录清单
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 结果
     */
    public int updateSecurityEnvironmentalOhsRecordList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList);

    /**
     * 删除环境职业健康安全记录清单
     * 
     * @param id 环境职业健康安全记录清单主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOhsRecordListById(Long id);

    /**
     * 批量删除环境职业健康安全记录清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOhsRecordListByIds(Long[] ids);
}
