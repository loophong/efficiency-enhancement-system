package com.heli.device.fileTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.fileTable.mapper.DeviceSopFileMapper;
import com.heli.device.fileTable.domain.DeviceSopFile;
import com.heli.device.fileTable.service.IDeviceSopFileService;

/**
 * SOP文件管理Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-19
 */
@Service
public class DeviceSopFileServiceImpl implements IDeviceSopFileService 
{
    @Autowired
    private DeviceSopFileMapper deviceSopFileMapper;

    /**
     * 查询SOP文件管理
     * 
     * @param sopFileId SOP文件管理主键
     * @return SOP文件管理
     */
    @Override
    public DeviceSopFile selectDeviceSopFileBySopFileId(String sopFileId)
    {
        return deviceSopFileMapper.selectDeviceSopFileBySopFileId(sopFileId);
    }

    /**
     * 查询SOP文件管理列表
     * 
     * @param deviceSopFile SOP文件管理
     * @return SOP文件管理
     */
    @Override
    public List<DeviceSopFile> selectDeviceSopFileList(DeviceSopFile deviceSopFile)
    {
        return deviceSopFileMapper.selectDeviceSopFileList(deviceSopFile);
    }

    /**
     * 新增SOP文件管理
     * 
     * @param deviceSopFile SOP文件管理
     * @return 结果
     */
    @Override
    public int insertDeviceSopFile(DeviceSopFile deviceSopFile)
    {
        return deviceSopFileMapper.insertDeviceSopFile(deviceSopFile);
    }

    /**
     * 修改SOP文件管理
     * 
     * @param deviceSopFile SOP文件管理
     * @return 结果
     */
    @Override
    public int updateDeviceSopFile(DeviceSopFile deviceSopFile)
    {
        return deviceSopFileMapper.updateDeviceSopFile(deviceSopFile);
    }

    /**
     * 批量删除SOP文件管理
     * 
     * @param sopFileIds 需要删除的SOP文件管理主键
     * @return 结果
     */
    @Override
    public int deleteDeviceSopFileBySopFileIds(String[] sopFileIds)
    {
        return deviceSopFileMapper.deleteDeviceSopFileBySopFileIds(sopFileIds);
    }

    /**
     * 删除SOP文件管理信息
     * 
     * @param sopFileId SOP文件管理主键
     * @return 结果
     */
    @Override
    public int deleteDeviceSopFileBySopFileId(String sopFileId)
    {
        return deviceSopFileMapper.deleteDeviceSopFileBySopFileId(sopFileId);
    }
}
