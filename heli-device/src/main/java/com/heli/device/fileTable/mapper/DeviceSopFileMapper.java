package com.heli.device.fileTable.mapper;

import java.util.List;
import com.heli.device.fileTable.domain.DeviceSopFile;

/**
 * SOP文件管理Mapper接口
 * 
 * @author YYY
 * @date 2025-01-19
 */
public interface DeviceSopFileMapper 
{
    /**
     * 查询SOP文件管理
     * 
     * @param sopFileId SOP文件管理主键
     * @return SOP文件管理
     */
    public DeviceSopFile selectDeviceSopFileBySopFileId(String sopFileId);

    /**
     * 查询SOP文件管理列表
     * 
     * @param deviceSopFile SOP文件管理
     * @return SOP文件管理集合
     */
    public List<DeviceSopFile> selectDeviceSopFileList(DeviceSopFile deviceSopFile);

    /**
     * 新增SOP文件管理
     * 
     * @param deviceSopFile SOP文件管理
     * @return 结果
     */
    public int insertDeviceSopFile(DeviceSopFile deviceSopFile);

    /**
     * 修改SOP文件管理
     * 
     * @param deviceSopFile SOP文件管理
     * @return 结果
     */
    public int updateDeviceSopFile(DeviceSopFile deviceSopFile);

    /**
     * 删除SOP文件管理
     * 
     * @param sopFileId SOP文件管理主键
     * @return 结果
     */
    public int deleteDeviceSopFileBySopFileId(String sopFileId);

    /**
     * 批量删除SOP文件管理
     * 
     * @param sopFileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceSopFileBySopFileIds(String[] sopFileIds);
}
