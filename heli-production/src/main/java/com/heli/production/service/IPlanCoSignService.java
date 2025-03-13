package com.heli.production.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.production.domain.entity.PlanCoSignEntity;

/**
 * 计划会签Service接口
 *
 * @author ruoyi
 * @date 2025-03-12
 */
public interface IPlanCoSignService extends IService<PlanCoSignEntity> {
    /**
     * 查询计划会签
     *
     * @param id 计划会签主键
     * @return 计划会签
     */
    public PlanCoSignEntity selectPlanCoSignById(String id);

    /**
     * 查询计划会签列表
     *
     * @param planCoSign 计划会签
     * @return 计划会签集合
     */
    public List<PlanCoSignEntity> selectPlanCoSignList(PlanCoSignEntity planCoSign);

    /**
     * 新增计划会签
     *
     * @param planCoSign 计划会签
     * @return 结果
     */
    public int insertPlanCoSign(PlanCoSignEntity planCoSign);

    /**
     * 修改计划会签
     *
     * @param planCoSign 计划会签
     * @return 结果
     */
    public int updatePlanCoSign(PlanCoSignEntity planCoSign);

    /**
     * 批量删除计划会签
     *
     * @param ids 需要删除的计划会签主键集合
     * @return 结果
     */
    public int deletePlanCoSignByIds(String[] ids);

    /**
     * 删除计划会签信息
     *
     * @param id 计划会签主键
     * @return 结果
     */
    public int deletePlanCoSignById(String id);
}
