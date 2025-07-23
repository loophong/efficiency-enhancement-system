package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityUploadfileDevice;

/**
 * 岗位安全操作规程Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityUploadfileDeviceService 
{
    /**
     * 查询岗位安全操作规程
     * 
     * @param id 岗位安全操作规程主键
     * @return 岗位安全操作规程
     */
    public SecurityUploadfileDevice selectSecurityUploadfileDeviceById(Long id);

    /**
     * 查询岗位安全操作规程列表
     * 
     * @param securityUploadfileDevice 岗位安全操作规程
     * @return 岗位安全操作规程集合
     */
    public List<SecurityUploadfileDevice> selectSecurityUploadfileDeviceList(SecurityUploadfileDevice securityUploadfileDevice);

    /**
     * 新增岗位安全操作规程
     * 
     * @param securityUploadfileDevice 岗位安全操作规程
     * @return 结果
     */
    public int insertSecurityUploadfileDevice(SecurityUploadfileDevice securityUploadfileDevice);

    /**
     * 修改岗位安全操作规程
     * 
     * @param securityUploadfileDevice 岗位安全操作规程
     * @return 结果
     */
    public int updateSecurityUploadfileDevice(SecurityUploadfileDevice securityUploadfileDevice);

    /**
     * 批量删除岗位安全操作规程
     * 
     * @param ids 需要删除的岗位安全操作规程主键集合
     * @return 结果
     */
    public int deleteSecurityUploadfileDeviceByIds(Long[] ids);

    /**
     * 删除岗位安全操作规程信息
     * 
     * @param id 岗位安全操作规程主键
     * @return 结果
     */
    public int deleteSecurityUploadfileDeviceById(Long id);
}
