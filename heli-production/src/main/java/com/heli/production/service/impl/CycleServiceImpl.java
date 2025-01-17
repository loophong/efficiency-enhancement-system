package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.CycleEntity;
import com.heli.production.service.ICycleService;
import com.heli.production.mapper.CycleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_cycle(产能表)】的数据库操作Service实现
 * @createDate 2025-01-17 09:33:12
 */
@Service
public class CycleServiceImpl extends ServiceImpl<CycleMapper, CycleEntity> implements ICycleService {
    @Autowired
    private CycleMapper productionCycleMapper;

    /**
     * 查询产能
     *
     * @param id 产能主键
     * @return 产能
     */
    @Override
    public CycleEntity selectProductionCycleById(Long id) {
        return productionCycleMapper.selectProductionCycleById(id);
    }

    /**
     * 查询产能列表
     *
     * @param productionCycle 产能
     * @return 产能
     */
    @Override
    public List<CycleEntity> selectProductionCycleList(CycleEntity productionCycle) {
        return productionCycleMapper.selectProductionCycleList(productionCycle);
    }

    /**
     * 新增产能
     *
     * @param productionCycle 产能
     * @return 结果
     */
    @Override
    public int insertProductionCycle(CycleEntity productionCycle) {
        return productionCycleMapper.insertProductionCycle(productionCycle);
    }

    /**
     * 修改产能
     *
     * @param productionCycle 产能
     * @return 结果
     */
    @Override
    public int updateProductionCycle(CycleEntity productionCycle) {
        return productionCycleMapper.updateProductionCycle(productionCycle);
    }

    /**
     * 批量删除产能
     *
     * @param ids 需要删除的产能主键
     * @return 结果
     */
    @Override
    public int deleteProductionCycleByIds(Long[] ids) {
        return productionCycleMapper.deleteProductionCycleByIds(ids);
    }

    /**
     * 删除产能信息
     *
     * @param id 产能主键
     * @return 结果
     */
    @Override
    public int deleteProductionCycleById(Long id) {
        return productionCycleMapper.deleteProductionCycleById(id);
    }
}




