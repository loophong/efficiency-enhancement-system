package com.heli.device.maintenanceTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.mapper.DeviceFaultAnalysisMapper;
import com.heli.device.maintenanceTable.domain.DeviceFaultAnalysis;
import com.heli.device.maintenanceTable.service.IDeviceFaultAnalysisService;

/**
 * 设备故障分析Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-17
 */
@Service
public class DeviceFaultAnalysisServiceImpl implements IDeviceFaultAnalysisService 
{
    @Autowired
    private DeviceFaultAnalysisMapper deviceFaultAnalysisMapper;

    /**
     * 查询设备故障分析
     * 
     * @param analysisId 设备故障分析主键
     * @return 设备故障分析
     */
    @Override
    public DeviceFaultAnalysis selectDeviceFaultAnalysisByAnalysisId(String analysisId)
    {
        return deviceFaultAnalysisMapper.selectDeviceFaultAnalysisByAnalysisId(analysisId);
    }

    /**
     * 查询设备故障分析列表
     * 
     * @param deviceFaultAnalysis 设备故障分析
     * @return 设备故障分析
     */
    @Override
    public List<DeviceFaultAnalysis> selectDeviceFaultAnalysisList(DeviceFaultAnalysis deviceFaultAnalysis)
    {
        return deviceFaultAnalysisMapper.selectDeviceFaultAnalysisList(deviceFaultAnalysis);
    }

    /**
     * 新增设备故障分析
     * 
     * @param deviceFaultAnalysis 设备故障分析
     * @return 结果
     */
    @Override
    public int insertDeviceFaultAnalysis(DeviceFaultAnalysis deviceFaultAnalysis)
    {
        return deviceFaultAnalysisMapper.insertDeviceFaultAnalysis(deviceFaultAnalysis);
    }

    /**
     * 修改设备故障分析
     * 
     * @param deviceFaultAnalysis 设备故障分析
     * @return 结果
     */
    @Override
    public int updateDeviceFaultAnalysis(DeviceFaultAnalysis deviceFaultAnalysis)
    {
        return deviceFaultAnalysisMapper.updateDeviceFaultAnalysis(deviceFaultAnalysis);
    }

    /**
     * 批量删除设备故障分析
     * 
     * @param analysisIds 需要删除的设备故障分析主键
     * @return 结果
     */
    @Override
    public int deleteDeviceFaultAnalysisByAnalysisIds(String[] analysisIds)
    {
        return deviceFaultAnalysisMapper.deleteDeviceFaultAnalysisByAnalysisIds(analysisIds);
    }

    /**
     * 删除设备故障分析信息
     * 
     * @param analysisId 设备故障分析主键
     * @return 结果
     */
    @Override
    public int deleteDeviceFaultAnalysisByAnalysisId(String analysisId)
    {
        return deviceFaultAnalysisMapper.deleteDeviceFaultAnalysisByAnalysisId(analysisId);
    }
}
