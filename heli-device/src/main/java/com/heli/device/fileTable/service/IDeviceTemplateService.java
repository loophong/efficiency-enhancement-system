package com.heli.device.maintenanceTable.service;

import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceTemplate;

/**
 * 设备导入模板文件Service接口
 * 
 * @author YYY
 * @date 2025-07-11
 */
public interface IDeviceTemplateService 
{
    /**
     * 查询设备导入模板文件
     * 
     * @param templateId 设备导入模板文件主键
     * @return 设备导入模板文件
     */
    public DeviceTemplate selectDeviceTemplateByTemplateId(Long templateId);

    /**
     * 查询设备导入模板文件列表
     * 
     * @param deviceTemplate 设备导入模板文件
     * @return 设备导入模板文件集合
     */
    public List<DeviceTemplate> selectDeviceTemplateList(DeviceTemplate deviceTemplate);

    /**
     * 新增设备导入模板文件
     * 
     * @param deviceTemplate 设备导入模板文件
     * @return 结果
     */
    public int insertDeviceTemplate(DeviceTemplate deviceTemplate);

    /**
     * 修改设备导入模板文件
     * 
     * @param deviceTemplate 设备导入模板文件
     * @return 结果
     */
    public int updateDeviceTemplate(DeviceTemplate deviceTemplate);

    /**
     * 批量删除设备导入模板文件
     * 
     * @param templateIds 需要删除的设备导入模板文件主键集合
     * @return 结果
     */
    public int deleteDeviceTemplateByTemplateIds(Long[] templateIds);

    /**
     * 删除设备导入模板文件信息
     * 
     * @param templateId 设备导入模板文件主键
     * @return 结果
     */
    public int deleteDeviceTemplateByTemplateId(Long templateId);
}
