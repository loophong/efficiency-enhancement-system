package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityAccidentMeasuresTracking;

/**
 * 事故对策及跟踪记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityAccidentMeasuresTrackingMapper 
{
    /**
     * 查询事故对策及跟踪记录
     * 
     * @param id 事故对策及跟踪记录主键
     * @return 事故对策及跟踪记录
     */
    public SecurityAccidentMeasuresTracking selectSecurityAccidentMeasuresTrackingById(Long id);

    /**
     * 查询事故对策及跟踪记录列表
     * 
     * @param securityAccidentMeasuresTracking 事故对策及跟踪记录
     * @return 事故对策及跟踪记录集合
     */
    public List<SecurityAccidentMeasuresTracking> selectSecurityAccidentMeasuresTrackingList(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking);

    /**
     * 新增事故对策及跟踪记录
     * 
     * @param securityAccidentMeasuresTracking 事故对策及跟踪记录
     * @return 结果
     */
    public int insertSecurityAccidentMeasuresTracking(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking);

    /**
     * 修改事故对策及跟踪记录
     * 
     * @param securityAccidentMeasuresTracking 事故对策及跟踪记录
     * @return 结果
     */
    public int updateSecurityAccidentMeasuresTracking(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking);

    /**
     * 删除事故对策及跟踪记录
     * 
     * @param id 事故对策及跟踪记录主键
     * @return 结果
     */
    public int deleteSecurityAccidentMeasuresTrackingById(Long id);

    /**
     * 批量删除事故对策及跟踪记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityAccidentMeasuresTrackingByIds(Long[] ids);
}
