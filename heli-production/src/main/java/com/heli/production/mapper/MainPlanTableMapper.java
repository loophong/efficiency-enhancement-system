package com.heli.production.mapper;

import com.heli.production.domain.entity.MainPlanTableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author loophong
* @description 针对表【production_main_plan_table】的数据库操作Mapper
* @createDate 2025-01-07 17:57:23
* @Entity com.heli.production.domain.ProductionMainPlanTable
*/
public interface MainPlanTableMapper extends BaseMapper<MainPlanTableEntity> {
    /**
     * 查询主计划表
     *
     * @param id 主计划表主键
     * @return 主计划表
     */
    public MainPlanTableEntity selectMainPlanTableById(String id);

    /**
     * 查询主计划表列表
     *
     * @param mainPlanTable 主计划表
     * @return 主计划表集合
     */
    public List<MainPlanTableEntity> selectMainPlanTableList(MainPlanTableEntity mainPlanTable);

    /**
     * 新增主计划表
     *
     * @param mainPlanTable 主计划表
     * @return 结果
     */
    public int insertMainPlanTable(MainPlanTableEntity mainPlanTable);

    /**
     * 修改主计划表
     *
     * @param mainPlanTable 主计划表
     * @return 结果
     */
    public int updateMainPlanTable(MainPlanTableEntity mainPlanTable);

    /**
     * 删除主计划表
     *
     * @param id 主计划表主键
     * @return 结果
     */
    public int deleteMainPlanTableById(String id);

    /**
     * 批量删除主计划表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMainPlanTableByIds(String[] ids);
}




