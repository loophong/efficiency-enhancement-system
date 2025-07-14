package com.heli.device.fileTable.mapper;

import com.heli.device.fileTable.domain.DeviceTemplate;

import java.util.List;


/**
 * 设备导入模板文件Mapper接口
 * 
 * @author YYY
 * @date 2025-07-11
 */
public interface DeviceTemplateMapper 
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
     * 删除设备导入模板文件
     * 
     * @param templateId 设备导入模板文件主键
     * @return 结果
     */
    public int deleteDeviceTemplateByTemplateId(Long templateId);

    /**
     * 批量删除设备导入模板文件
     * 
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceTemplateByTemplateIds(Long[] templateIds);
}
