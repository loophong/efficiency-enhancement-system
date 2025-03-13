package com.heli.production.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.DailyPlanMapper;
import com.heli.production.domain.entity.DailyPlanEntity;
import com.heli.production.service.IDailyPlanService;

/**
 * 日生产计划Service业务层处理
 *
 * @author hong
 * @date 2025-03-12
 */
@Service
public class DailyPlanServiceImpl extends ServiceImpl<DailyPlanMapper, DailyPlanEntity> implements IDailyPlanService {
    @Autowired
    private DailyPlanMapper productionDailyPlanMapper;

    /**
     * 查询日生产计划
     *
     * @param id 日生产计划主键
     * @return 日生产计划
     */
    @Override
    public DailyPlanEntity selectProductionDailyPlanById(String id) {
        return productionDailyPlanMapper.selectProductionDailyPlanById(id);
    }

    /**
     * 查询日生产计划列表
     *
     * @param productionDailyPlan 日生产计划
     * @return 日生产计划
     */
    @Override
    public List<DailyPlanEntity> selectProductionDailyPlanList(DailyPlanEntity productionDailyPlan) {
        return productionDailyPlanMapper.selectProductionDailyPlanList(productionDailyPlan);
    }

    /**
     * 新增日生产计划
     *
     * @param productionDailyPlan 日生产计划
     * @return 结果
     */
    @Override
    public int insertProductionDailyPlan(DailyPlanEntity productionDailyPlan) {
        return productionDailyPlanMapper.insertProductionDailyPlan(productionDailyPlan);
    }

    /**
     * 修改日生产计划
     *
     * @param productionDailyPlan 日生产计划
     * @return 结果
     */
    @Override
    public int updateProductionDailyPlan(DailyPlanEntity productionDailyPlan) {
        return productionDailyPlanMapper.updateProductionDailyPlan(productionDailyPlan);
    }

    /**
     * 批量删除日生产计划
     *
     * @param ids 需要删除的日生产计划主键
     * @return 结果
     */
    @Override
    public int deleteProductionDailyPlanByIds(String[] ids) {
        return productionDailyPlanMapper.deleteProductionDailyPlanByIds(ids);
    }

    /**
     * 删除日生产计划信息
     *
     * @param id 日生产计划主键
     * @return 结果
     */
    @Override
    public int deleteProductionDailyPlanById(String id) {
        return productionDailyPlanMapper.deleteProductionDailyPlanById(id);
    }
}
