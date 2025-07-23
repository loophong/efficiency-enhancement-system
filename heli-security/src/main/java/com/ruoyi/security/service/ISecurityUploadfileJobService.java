package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityUploadfileJob;

/**
 * 设备设施安全操作规程Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityUploadfileJobService 
{
    /**
     * 查询设备设施安全操作规程
     * 
     * @param id 设备设施安全操作规程主键
     * @return 设备设施安全操作规程
     */
    public SecurityUploadfileJob selectSecurityUploadfileJobById(Long id);

    /**
     * 查询设备设施安全操作规程列表
     * 
     * @param securityUploadfileJob 设备设施安全操作规程
     * @return 设备设施安全操作规程集合
     */
    public List<SecurityUploadfileJob> selectSecurityUploadfileJobList(SecurityUploadfileJob securityUploadfileJob);

    /**
     * 新增设备设施安全操作规程
     * 
     * @param securityUploadfileJob 设备设施安全操作规程
     * @return 结果
     */
    public int insertSecurityUploadfileJob(SecurityUploadfileJob securityUploadfileJob);

    /**
     * 修改设备设施安全操作规程
     * 
     * @param securityUploadfileJob 设备设施安全操作规程
     * @return 结果
     */
    public int updateSecurityUploadfileJob(SecurityUploadfileJob securityUploadfileJob);

    /**
     * 批量删除设备设施安全操作规程
     * 
     * @param ids 需要删除的设备设施安全操作规程主键集合
     * @return 结果
     */
    public int deleteSecurityUploadfileJobByIds(Long[] ids);

    /**
     * 删除设备设施安全操作规程信息
     * 
     * @param id 设备设施安全操作规程主键
     * @return 结果
     */
    public int deleteSecurityUploadfileJobById(Long id);
}
