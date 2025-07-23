package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityAccidentMeasuresTrackingMapper;
import com.ruoyi.security.domain.SecurityAccidentMeasuresTracking;
import com.ruoyi.security.service.ISecurityAccidentMeasuresTrackingService;

/**
 * 事故对策及跟踪记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityAccidentMeasuresTrackingServiceImpl implements ISecurityAccidentMeasuresTrackingService 
{
    @Autowired
    private SecurityAccidentMeasuresTrackingMapper securityAccidentMeasuresTrackingMapper;

    /**
     * 查询事故对策及跟踪记录
     * 
     * @param id 事故对策及跟踪记录主键
     * @return 事故对策及跟踪记录
     */
    @Override
    public SecurityAccidentMeasuresTracking selectSecurityAccidentMeasuresTrackingById(Long id)
    {
        return securityAccidentMeasuresTrackingMapper.selectSecurityAccidentMeasuresTrackingById(id);
    }

    /**
     * 查询事故对策及跟踪记录列表
     * 
     * @param securityAccidentMeasuresTracking 事故对策及跟踪记录
     * @return 事故对策及跟踪记录
     */
    @Override
    public List<SecurityAccidentMeasuresTracking> selectSecurityAccidentMeasuresTrackingList(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        return securityAccidentMeasuresTrackingMapper.selectSecurityAccidentMeasuresTrackingList(securityAccidentMeasuresTracking);
    }

    /**
     * 新增事故对策及跟踪记录
     * 
     * @param securityAccidentMeasuresTracking 事故对策及跟踪记录
     * @return 结果
     */
    @Override
    public int insertSecurityAccidentMeasuresTracking(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        return securityAccidentMeasuresTrackingMapper.insertSecurityAccidentMeasuresTracking(securityAccidentMeasuresTracking);
    }

    /**
     * 修改事故对策及跟踪记录
     * 
     * @param securityAccidentMeasuresTracking 事故对策及跟踪记录
     * @return 结果
     */
    @Override
    public int updateSecurityAccidentMeasuresTracking(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        return securityAccidentMeasuresTrackingMapper.updateSecurityAccidentMeasuresTracking(securityAccidentMeasuresTracking);
    }

    /**
     * 批量删除事故对策及跟踪记录
     * 
     * @param ids 需要删除的事故对策及跟踪记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentMeasuresTrackingByIds(Long[] ids)
    {
        return securityAccidentMeasuresTrackingMapper.deleteSecurityAccidentMeasuresTrackingByIds(ids);
    }

    /**
     * 删除事故对策及跟踪记录信息
     * 
     * @param id 事故对策及跟踪记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentMeasuresTrackingById(Long id)
    {
        return securityAccidentMeasuresTrackingMapper.deleteSecurityAccidentMeasuresTrackingById(id);
    }
}
