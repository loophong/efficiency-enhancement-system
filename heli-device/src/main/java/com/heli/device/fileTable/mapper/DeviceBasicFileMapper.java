package com.heli.device.fileTable.mapper;

import java.util.List;
import com.heli.device.fileTable.domain.DeviceBasicFile;

/**
 * 设备基础档案Mapper接口
 * 
 * @author YYY
 * @date 2025-01-19
 */
public interface DeviceBasicFileMapper 
{
    /**
     * 查询设备基础档案
     * 
     * @param basicFileId 设备基础档案主键
     * @return 设备基础档案
     */
    public DeviceBasicFile selectDeviceBasicFileByBasicFileId(String basicFileId);

    /**
     * 查询设备基础档案列表
     * 
     * @param deviceBasicFile 设备基础档案
     * @return 设备基础档案集合
     */
    public List<DeviceBasicFile> selectDeviceBasicFileList(DeviceBasicFile deviceBasicFile);

    /**
     * 新增设备基础档案
     * 
     * @param deviceBasicFile 设备基础档案
     * @return 结果
     */
    public int insertDeviceBasicFile(DeviceBasicFile deviceBasicFile);

    /**
     * 修改设备基础档案
     * 
     * @param deviceBasicFile 设备基础档案
     * @return 结果
     */
    public int updateDeviceBasicFile(DeviceBasicFile deviceBasicFile);

    /**
     * 删除设备基础档案
     * 
     * @param basicFileId 设备基础档案主键
     * @return 结果
     */
    public int deleteDeviceBasicFileByBasicFileId(String basicFileId);

    /**
     * 批量删除设备基础档案
     * 
     * @param basicFileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceBasicFileByBasicFileIds(String[] basicFileIds);
}
