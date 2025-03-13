package com.heli.device.maintenanceTable.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.device.maintenanceTable.domain.DeviceFaultAnalysis;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 设备故障分析Mapper接口
 * 
 * @author YYY
 * @date 2025-01-17
 */
public interface DeviceFaultAnalysisMapper extends BaseMapper<DeviceFaultAnalysis>
{


    @Select({
            "<script>",
            "SELECT * FROM device_fault_analysis",
            "WHERE analysis_record_time = #{analysisRecordTime}",
            "<if test='analysisName != null and analysisName != \"\"'>",
            "AND analysis_name = #{analysisName}",
            "</if>",
            "</script>"
    })
    public List<DeviceFaultAnalysis> selectByNameAndDate(@Param("analysisRecordTime") Date analysisRecordTime, @Param("analysisName") String analysisName);
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
     * 删除设备故障分析
     * 
     * @param analysisId 设备故障分析主键
     * @return 结果
     */
    public int deleteDeviceFaultAnalysisByAnalysisId(String analysisId);

    /**
     * 批量删除设备故障分析
     * 
     * @param analysisIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceFaultAnalysisByAnalysisIds(String[] analysisIds);
}
