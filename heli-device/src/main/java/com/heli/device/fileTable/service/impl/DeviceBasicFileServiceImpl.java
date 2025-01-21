package com.heli.device.fileTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.fileTable.mapper.DeviceBasicFileMapper;
import com.heli.device.fileTable.domain.DeviceBasicFile;
import com.heli.device.fileTable.service.IDeviceBasicFileService;

/**
 * 设备基础档案Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-19
 */
@Service
public class DeviceBasicFileServiceImpl implements IDeviceBasicFileService 
{
    @Autowired
    private DeviceBasicFileMapper deviceBasicFileMapper;

    /**
     * 查询设备基础档案
     * 
     * @param basicFileId 设备基础档案主键
     * @return 设备基础档案
     */
    @Override
    public DeviceBasicFile selectDeviceBasicFileByBasicFileId(String basicFileId)
    {
        return deviceBasicFileMapper.selectDeviceBasicFileByBasicFileId(basicFileId);
    }

    /**
     * 查询设备基础档案列表
     * 
     * @param deviceBasicFile 设备基础档案
     * @return 设备基础档案
     */
    @Override
    public List<DeviceBasicFile> selectDeviceBasicFileList(DeviceBasicFile deviceBasicFile)
    {
        return deviceBasicFileMapper.selectDeviceBasicFileList(deviceBasicFile);
    }

    /**
     * 新增设备基础档案
     * 
     * @param deviceBasicFile 设备基础档案
     * @return 结果
     */
    @Override
    public int insertDeviceBasicFile(DeviceBasicFile deviceBasicFile)
    {
        return deviceBasicFileMapper.insertDeviceBasicFile(deviceBasicFile);
    }

    /**
     * 修改设备基础档案
     * 
     * @param deviceBasicFile 设备基础档案
     * @return 结果
     */
    @Override
    public int updateDeviceBasicFile(DeviceBasicFile deviceBasicFile)
    {
        return deviceBasicFileMapper.updateDeviceBasicFile(deviceBasicFile);
    }

    /**
     * 批量删除设备基础档案
     * 
     * @param basicFileIds 需要删除的设备基础档案主键
     * @return 结果
     */
    @Override
    public int deleteDeviceBasicFileByBasicFileIds(String[] basicFileIds)
    {
        return deviceBasicFileMapper.deleteDeviceBasicFileByBasicFileIds(basicFileIds);
    }

    /**
     * 删除设备基础档案信息
     * 
     * @param basicFileId 设备基础档案主键
     * @return 结果
     */
    @Override
    public int deleteDeviceBasicFileByBasicFileId(String basicFileId)
    {
        return deviceBasicFileMapper.deleteDeviceBasicFileByBasicFileId(basicFileId);
    }
}
