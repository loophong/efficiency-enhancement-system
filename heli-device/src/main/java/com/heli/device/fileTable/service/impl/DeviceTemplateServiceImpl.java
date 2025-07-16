package com.heli.device.fileTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.fileTable.mapper.DeviceTemplateMapper;
import com.heli.device.fileTable.domain.DeviceTemplate;
import com.heli.device.fileTable.service.IDeviceTemplateService;

/**
 * 设备导入模板文件Service业务层处理
 * 
 * @author YYY
 * @date 2025-07-11
 */
@Service
public class DeviceTemplateServiceImpl implements IDeviceTemplateService 
{
    @Autowired
    private DeviceTemplateMapper deviceTemplateMapper;

    /**
     * 查询设备导入模板文件
     * 
     * @param templateId 设备导入模板文件主键
     * @return 设备导入模板文件
     */
    @Override
    public DeviceTemplate selectDeviceTemplateByTemplateId(Long templateId)
    {
        return deviceTemplateMapper.selectDeviceTemplateByTemplateId(templateId);
    }

    /**
     * 查询设备导入模板文件列表
     * 
     * @param deviceTemplate 设备导入模板文件
     * @return 设备导入模板文件
     */
    @Override
    public List<DeviceTemplate> selectDeviceTemplateList(DeviceTemplate deviceTemplate)
    {
        return deviceTemplateMapper.selectDeviceTemplateList(deviceTemplate);
    }

    /**
     * 新增设备导入模板文件
     * 
     * @param deviceTemplate 设备导入模板文件
     * @return 结果
     */
    @Override
    public int insertDeviceTemplate(DeviceTemplate deviceTemplate)
    {
        return deviceTemplateMapper.insertDeviceTemplate(deviceTemplate);
    }

    /**
     * 修改设备导入模板文件
     * 
     * @param deviceTemplate 设备导入模板文件
     * @return 结果
     */
    @Override
    public int updateDeviceTemplate(DeviceTemplate deviceTemplate)
    {
        return deviceTemplateMapper.updateDeviceTemplate(deviceTemplate);
    }

    /**
     * 批量删除设备导入模板文件
     * 
     * @param templateIds 需要删除的设备导入模板文件主键
     * @return 结果
     */
    @Override
    public int deleteDeviceTemplateByTemplateIds(Long[] templateIds)
    {
        return deviceTemplateMapper.deleteDeviceTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除设备导入模板文件信息
     * 
     * @param templateId 设备导入模板文件主键
     * @return 结果
     */
    @Override
    public int deleteDeviceTemplateByTemplateId(Long templateId)
    {
        return deviceTemplateMapper.deleteDeviceTemplateByTemplateId(templateId);
    }
}
