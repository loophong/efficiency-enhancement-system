package com.heli.device.maintenanceTable.service;

import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceFaultAnalysis;

/**
 * 设备故障分析Service接口
 * 
 * @author YYY
 * @date 2025-01-17
 */
public interface IDeviceFaultAnalysisService 
{
    /**
     * 查询设备故障分析
     * 
     * @param analysisId 设备故障分析主键
     * @return 设备故障分析
     */
    public DeviceFaultAnalysis selectDeviceFaultAnalysisByAnalysisId(String analysisId);

    /**
     * 查询设备故障分析列表
     * 
     * @param deviceFaultAnalysis 设备故障分析
     * @return 设备故障分析集合
     */
    public List<DeviceFaultAnalysis> selectDeviceFaultAnalysisList(DeviceFaultAnalysis deviceFaultAnalysis);

    /**
     * 新增设备故障分析
     * 
     * @param deviceFaultAnalysis 设备故障分析
     * @return 结果
     */
    public int insertDeviceFaultAnalysis(DeviceFaultAnalysis deviceFaultAnalysis);

    /**
     * 修改设备故障分析
     * 
     * @param deviceFaultAnalysis 设备故障分析
     * @return 结果
     */
    public int updateDeviceFaultAnalysis(DeviceFaultAnalysis deviceFaultAnalysis);

    /**
     * 批量删除设备故障分析
     * 
     * @param analysisIds 需要删除的设备故障分析主键集合
     * @return 结果
     */
    public int deleteDeviceFaultAnalysisByAnalysisIds(String[] analysisIds);

    /**
     * 删除设备故障分析信息
     * 
     * @param analysisId 设备故障分析主键
     * @return 结果
     */
    public int deleteDeviceFaultAnalysisByAnalysisId(String analysisId);
}
