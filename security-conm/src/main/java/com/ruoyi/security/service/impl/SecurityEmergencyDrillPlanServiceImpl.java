package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencyDrillPlanMapper;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import com.ruoyi.security.service.ISecurityEmergencyDrillPlanService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.security.utils.MergedCellProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应急演练计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecurityEmergencyDrillPlanServiceImpl implements ISecurityEmergencyDrillPlanService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityEmergencyDrillPlanServiceImpl.class);

    @Autowired
    private SecurityEmergencyDrillPlanMapper securityEmergencyDrillPlanMapper;

    /**
     * 查询应急演练计划
     * 
     * @param id 应急演练计划主键
     * @return 应急演练计划
     */
    @Override
    public SecurityEmergencyDrillPlan selectSecurityEmergencyDrillPlanById(Long id)
    {
        return securityEmergencyDrillPlanMapper.selectSecurityEmergencyDrillPlanById(id);
    }

    /**
     * 查询应急演练计划列表
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 应急演练计划
     */
    @Override
    public List<SecurityEmergencyDrillPlan> selectSecurityEmergencyDrillPlanList(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return securityEmergencyDrillPlanMapper.selectSecurityEmergencyDrillPlanList(securityEmergencyDrillPlan);
    }

    /**
     * 新增应急演练计划
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencyDrillPlan(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return securityEmergencyDrillPlanMapper.insertSecurityEmergencyDrillPlan(securityEmergencyDrillPlan);
    }

    /**
     * 修改应急演练计划
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencyDrillPlan(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return securityEmergencyDrillPlanMapper.updateSecurityEmergencyDrillPlan(securityEmergencyDrillPlan);
    }

    /**
     * 批量删除应急演练计划
     * 
     * @param ids 需要删除的应急演练计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyDrillPlanByIds(Long[] ids)
    {
        return securityEmergencyDrillPlanMapper.deleteSecurityEmergencyDrillPlanByIds(ids);
    }

    /**
     * 删除应急演练计划信息
     * 
     * @param id 应急演练计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyDrillPlanById(Long id)
    {
        return securityEmergencyDrillPlanMapper.deleteSecurityEmergencyDrillPlanById(id);
    }

    /**
     * 导入应急演练计划数据
     *
     * @param drillPlanList 应急演练计划数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSecurityEmergencyDrillPlan(List<SecurityEmergencyDrillPlan> drillPlanList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(drillPlanList) || drillPlanList.size() == 0) {
            throw new ServiceException("导入应急演练计划数据不能为空！");
        }

        // 处理合并单元格数据
        drillPlanList = MergedCellProcessor.processEmergencyDrillPlanMergedCells(drillPlanList);

        // 过滤无效数据
        drillPlanList = MergedCellProcessor.filterValidData(drillPlanList);

        if (drillPlanList.isEmpty()) {
            throw new ServiceException("导入数据处理后为空，请检查Excel文件格式！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecurityEmergencyDrillPlan drillPlan : drillPlanList) {
            try {
                // 清空id字段，确保使用数据库自动生成的id
                drillPlan.setId(null);

                // 验证必填字段
                if (StringUtils.isEmpty(drillPlan.getLevel())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、级别不能为空");
                    continue;
                }

                if (StringUtils.isEmpty(drillPlan.getDepartmentTeam())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、科室/班组不能为空");
                    continue;
                }

                // 检查是否存在相同的记录（基于级别、科室/班组、演练主题）
                SecurityEmergencyDrillPlan existingPlan = findByUniqueFields(drillPlan.getLevel(), drillPlan.getDepartmentTeam(), drillPlan.getDrillTheme());

                if (existingPlan != null && isUpdateSupport) {
                    // 更新现有记录
                    drillPlan.setId(existingPlan.getId());
                    this.updateSecurityEmergencyDrillPlan(drillPlan);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、级别 " + drillPlan.getLevel() + " 科室/班组 " + drillPlan.getDepartmentTeam() + " 更新成功");
                } else if (existingPlan == null) {
                    // 插入新记录
                    this.insertSecurityEmergencyDrillPlan(drillPlan);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、级别 " + drillPlan.getLevel() + " 科室/班组 " + drillPlan.getDepartmentTeam() + " 导入成功");
                } else {
                    // 存在但不更新，跳过
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、级别 " + drillPlan.getLevel() + " 科室/班组 " + drillPlan.getDepartmentTeam() + " 已存在，跳过导入");
                }
            } catch (Exception e) {
                failureNum++;
                String level = drillPlan.getLevel();
                String dept = drillPlan.getDepartmentTeam();
                String identifier = StringUtils.isNotEmpty(level) && StringUtils.isNotEmpty(dept) ?
                    level + "-" + dept : "第" + (successNum + failureNum) + "行";
                String msg = "<br/>" + failureNum + "、" + identifier + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }

        return successMsg.toString();
    }

    /**
     * 根据唯一字段查找记录
     */
    private SecurityEmergencyDrillPlan findByUniqueFields(String level, String departmentTeam, String drillTheme) {
        SecurityEmergencyDrillPlan query = new SecurityEmergencyDrillPlan();
        query.setLevel(level);
        query.setDepartmentTeam(departmentTeam);
        if (StringUtils.isNotEmpty(drillTheme)) {
            query.setDrillTheme(drillTheme);
        }
        List<SecurityEmergencyDrillPlan> list = securityEmergencyDrillPlanMapper.selectSecurityEmergencyDrillPlanList(query);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据关联ID查询应急演练计划列表
     *
     * @param relatedId 关联ID
     * @return 应急演练计划集合
     */
    @Override
    public List<SecurityEmergencyDrillPlan> selectSecurityEmergencyDrillPlanByRelatedId(Long relatedId) {
        return securityEmergencyDrillPlanMapper.selectSecurityEmergencyDrillPlanByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        return securityEmergencyDrillPlanMapper.updateLatestImportedRelatedId(relatedId);
    }
}
