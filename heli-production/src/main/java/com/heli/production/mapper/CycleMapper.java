package com.heli.production.mapper;

import com.heli.production.domain.entity.CycleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author loophong
* @description 针对表【production_cycle(产能表)】的数据库操作Mapper
* @createDate 2025-01-17 09:33:12
* @Entity com.heli.production.domain.ProductionCycle
*/
public interface CycleMapper extends BaseMapper<CycleEntity> {

    /**
     * 查询产能
     *
     * @param id 产能主键
     * @return 产能
     */
    public CycleEntity selectProductionCycleById(Long id);

    /**
     * 查询产能列表
     *
     * @param productionCycle 产能
     * @return 产能集合
     */
    public List<CycleEntity> selectProductionCycleList(CycleEntity productionCycle);

    /**
     * 新增产能
     *
     * @param productionCycle 产能
     * @return 结果
     */
    public int insertProductionCycle(CycleEntity productionCycle);

    /**
     * 修改产能
     *
     * @param productionCycle 产能
     * @return 结果
     */
    public int updateProductionCycle(CycleEntity productionCycle);

    /**
     * 删除产能
     *
     * @param id 产能主键
     * @return 结果
     */
    public int deleteProductionCycleById(Long id);

    /**
     * 批量删除产能
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductionCycleByIds(Long[] ids);

}




