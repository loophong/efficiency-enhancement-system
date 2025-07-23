package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencyPlanDrillMapper;
import com.ruoyi.security.domain.SecurityEmergencyPlanDrill;
import com.ruoyi.security.service.ISecurityEmergencyPlanDrillService;

/**
 * 应急预案演练Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class SecurityEmergencyPlanDrillServiceImpl implements ISecurityEmergencyPlanDrillService 
{
    @Autowired
    private SecurityEmergencyPlanDrillMapper securityEmergencyPlanDrillMapper;

    /**
     * 查询应急预案演练
     * 
     * @param id 应急预案演练主键
     * @return 应急预案演练
     */
    @Override
    public SecurityEmergencyPlanDrill selectSecurityEmergencyPlanDrillById(String id)
    {
        return securityEmergencyPlanDrillMapper.selectSecurityEmergencyPlanDrillById(id);
    }

    /**
     * 查询应急预案演练列表
     * 
     * @param securityEmergencyPlanDrill 应急预案演练
     * @return 应急预案演练
     */
    @Override
    public List<SecurityEmergencyPlanDrill> selectSecurityEmergencyPlanDrillList(SecurityEmergencyPlanDrill securityEmergencyPlanDrill)
    {
        return securityEmergencyPlanDrillMapper.selectSecurityEmergencyPlanDrillList(securityEmergencyPlanDrill);
    }

    /**
     * 新增应急预案演练
     * 
     * @param securityEmergencyPlanDrill 应急预案演练
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencyPlanDrill(SecurityEmergencyPlanDrill securityEmergencyPlanDrill)
    {
        return securityEmergencyPlanDrillMapper.insertSecurityEmergencyPlanDrill(securityEmergencyPlanDrill);
    }

    /**
     * 修改应急预案演练
     * 
     * @param securityEmergencyPlanDrill 应急预案演练
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencyPlanDrill(SecurityEmergencyPlanDrill securityEmergencyPlanDrill)
    {
        return securityEmergencyPlanDrillMapper.updateSecurityEmergencyPlanDrill(securityEmergencyPlanDrill);
    }

    /**
     * 批量删除应急预案演练
     * 
     * @param ids 需要删除的应急预案演练主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyPlanDrillByIds(String[] ids)
    {
        return securityEmergencyPlanDrillMapper.deleteSecurityEmergencyPlanDrillByIds(ids);
    }

    /**
     * 删除应急预案演练信息
     * 
     * @param id 应急预案演练主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyPlanDrillById(String id)
    {
        return securityEmergencyPlanDrillMapper.deleteSecurityEmergencyPlanDrillById(id);
    }
}
