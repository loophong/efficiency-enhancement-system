package com.heli.production.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.production.domain.entity.DailyPlanEntity;

/**
 * 日生产计划Mapper接口
 *
 * @author hong
 * @date 2025-03-12
 */
public interface DailyPlanMapper extends BaseMapper<DailyPlanEntity> {
    /**
     * 查询日生产计划
     *
     * @param id 日生产计划主键
     * @return 日生产计划
     */
    public DailyPlanEntity selectProductionDailyPlanById(String id);

    /**
     * 查询日生产计划列表
     *
     * @param productionDailyPlan 日生产计划
     * @return 日生产计划集合
     */
    public List<DailyPlanEntity> selectProductionDailyPlanList(DailyPlanEntity productionDailyPlan);

    /**
     * 新增日生产计划
     *
     * @param productionDailyPlan 日生产计划
     * @return 结果
     */
    public int insertProductionDailyPlan(DailyPlanEntity productionDailyPlan);

    /**
     * 修改日生产计划
     *
     * @param productionDailyPlan 日生产计划
     * @return 结果
     */
    public int updateProductionDailyPlan(DailyPlanEntity productionDailyPlan);

    /**
     * 删除日生产计划
     *
     * @param id 日生产计划主键
     * @return 结果
     */
    public int deleteProductionDailyPlanById(String id);

    /**
     * 批量删除日生产计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductionDailyPlanByIds(String[] ids);
}
