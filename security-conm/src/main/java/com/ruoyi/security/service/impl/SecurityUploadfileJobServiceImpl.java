package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUploadfileJobMapper;
import com.ruoyi.security.domain.SecurityUploadfileJob;
import com.ruoyi.security.service.ISecurityUploadfileJobService;

/**
 * 设备设施安全操作规程Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityUploadfileJobServiceImpl implements ISecurityUploadfileJobService 
{
    @Autowired
    private SecurityUploadfileJobMapper securityUploadfileJobMapper;

    /**
     * 查询设备设施安全操作规程
     * 
     * @param id 设备设施安全操作规程主键
     * @return 设备设施安全操作规程
     */
    @Override
    public SecurityUploadfileJob selectSecurityUploadfileJobById(Long id)
    {
        return securityUploadfileJobMapper.selectSecurityUploadfileJobById(id);
    }

    /**
     * 查询设备设施安全操作规程列表
     * 
     * @param securityUploadfileJob 设备设施安全操作规程
     * @return 设备设施安全操作规程
     */
    @Override
    public List<SecurityUploadfileJob> selectSecurityUploadfileJobList(SecurityUploadfileJob securityUploadfileJob)
    {
        return securityUploadfileJobMapper.selectSecurityUploadfileJobList(securityUploadfileJob);
    }

    /**
     * 新增设备设施安全操作规程
     * 
     * @param securityUploadfileJob 设备设施安全操作规程
     * @return 结果
     */
    @Override
    public int insertSecurityUploadfileJob(SecurityUploadfileJob securityUploadfileJob)
    {
        return securityUploadfileJobMapper.insertSecurityUploadfileJob(securityUploadfileJob);
    }

    /**
     * 修改设备设施安全操作规程
     * 
     * @param securityUploadfileJob 设备设施安全操作规程
     * @return 结果
     */
    @Override
    public int updateSecurityUploadfileJob(SecurityUploadfileJob securityUploadfileJob)
    {
        return securityUploadfileJobMapper.updateSecurityUploadfileJob(securityUploadfileJob);
    }

    /**
     * 批量删除设备设施安全操作规程
     * 
     * @param ids 需要删除的设备设施安全操作规程主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileJobByIds(Long[] ids)
    {
        return securityUploadfileJobMapper.deleteSecurityUploadfileJobByIds(ids);
    }

    /**
     * 删除设备设施安全操作规程信息
     * 
     * @param id 设备设施安全操作规程主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileJobById(Long id)
    {
        return securityUploadfileJobMapper.deleteSecurityUploadfileJobById(id);
    }
}
