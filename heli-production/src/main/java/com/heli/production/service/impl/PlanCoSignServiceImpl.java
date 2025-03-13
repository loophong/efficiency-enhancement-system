package com.heli.production.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.PlanCoSignMapper;
import com.heli.production.domain.entity.PlanCoSignEntity;
import com.heli.production.service.IPlanCoSignService;

/**
 * 计划会签Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@Service
public class PlanCoSignServiceImpl extends ServiceImpl<PlanCoSignMapper, PlanCoSignEntity> implements IPlanCoSignService {
    @Autowired
    private PlanCoSignMapper planCoSignMapper;

    /**
     * 查询计划会签
     *
     * @param id 计划会签主键
     * @return 计划会签
     */
    @Override
    public PlanCoSignEntity selectPlanCoSignById(String id) {
        return planCoSignMapper.selectPlanCoSignById(id);
    }

    /**
     * 查询计划会签列表
     *
     * @param planCoSign 计划会签
     * @return 计划会签
     */
    @Override
    public List<PlanCoSignEntity> selectPlanCoSignList(PlanCoSignEntity planCoSign) {
        return planCoSignMapper.selectPlanCoSignList(planCoSign);
    }

    /**
     * 新增计划会签
     *
     * @param planCoSign 计划会签
     * @return 结果
     */
    @Override
    public int insertPlanCoSign(PlanCoSignEntity planCoSign) {
        return planCoSignMapper.insertPlanCoSign(planCoSign);
    }

    /**
     * 修改计划会签
     *
     * @param planCoSign 计划会签
     * @return 结果
     */
    @Override
    public int updatePlanCoSign(PlanCoSignEntity planCoSign) {
        return planCoSignMapper.updatePlanCoSign(planCoSign);
    }

    /**
     * 批量删除计划会签
     *
     * @param ids 需要删除的计划会签主键
     * @return 结果
     */
    @Override
    public int deletePlanCoSignByIds(String[] ids) {
        return planCoSignMapper.deletePlanCoSignByIds(ids);
    }

    /**
     * 删除计划会签信息
     *
     * @param id 计划会签主键
     * @return 结果
     */
    @Override
    public int deletePlanCoSignById(String id) {
        return planCoSignMapper.deletePlanCoSignById(id);
    }
}
