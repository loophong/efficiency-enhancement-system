package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUploadfileDeviceMapper;
import com.ruoyi.security.domain.SecurityUploadfileDevice;
import com.ruoyi.security.service.ISecurityUploadfileDeviceService;

/**
 * 岗位安全操作规程Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityUploadfileDeviceServiceImpl implements ISecurityUploadfileDeviceService 
{
    @Autowired
    private SecurityUploadfileDeviceMapper securityUploadfileDeviceMapper;

    /**
     * 查询岗位安全操作规程
     * 
     * @param id 岗位安全操作规程主键
     * @return 岗位安全操作规程
     */
    @Override
    public SecurityUploadfileDevice selectSecurityUploadfileDeviceById(Long id)
    {
        return securityUploadfileDeviceMapper.selectSecurityUploadfileDeviceById(id);
    }

    /**
     * 查询岗位安全操作规程列表
     * 
     * @param securityUploadfileDevice 岗位安全操作规程
     * @return 岗位安全操作规程
     */
    @Override
    public List<SecurityUploadfileDevice> selectSecurityUploadfileDeviceList(SecurityUploadfileDevice securityUploadfileDevice)
    {
        return securityUploadfileDeviceMapper.selectSecurityUploadfileDeviceList(securityUploadfileDevice);
    }

    /**
     * 新增岗位安全操作规程
     * 
     * @param securityUploadfileDevice 岗位安全操作规程
     * @return 结果
     */
    @Override
    public int insertSecurityUploadfileDevice(SecurityUploadfileDevice securityUploadfileDevice)
    {
        return securityUploadfileDeviceMapper.insertSecurityUploadfileDevice(securityUploadfileDevice);
    }

    /**
     * 修改岗位安全操作规程
     * 
     * @param securityUploadfileDevice 岗位安全操作规程
     * @return 结果
     */
    @Override
    public int updateSecurityUploadfileDevice(SecurityUploadfileDevice securityUploadfileDevice)
    {
        return securityUploadfileDeviceMapper.updateSecurityUploadfileDevice(securityUploadfileDevice);
    }

    /**
     * 批量删除岗位安全操作规程
     * 
     * @param ids 需要删除的岗位安全操作规程主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileDeviceByIds(Long[] ids)
    {
        return securityUploadfileDeviceMapper.deleteSecurityUploadfileDeviceByIds(ids);
    }

    /**
     * 删除岗位安全操作规程信息
     * 
     * @param id 岗位安全操作规程主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileDeviceById(Long id)
    {
        return securityUploadfileDeviceMapper.deleteSecurityUploadfileDeviceById(id);
    }
}
