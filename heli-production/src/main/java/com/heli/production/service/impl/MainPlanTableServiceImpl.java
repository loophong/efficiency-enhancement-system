package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.MainPlanTable;
import com.heli.production.service.IMainPlanTableService;
import com.heli.production.mapper.MainPlanTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_main_plan_table】的数据库操作Service实现
 * @createDate 2025-01-07 17:57:23
 */
@Service
public class MainPlanTableServiceImpl extends ServiceImpl<MainPlanTableMapper, MainPlanTable> implements IMainPlanTableService {
    @Autowired
    private MainPlanTableMapper mainPlanTableMapper;

    /**
     * 查询主计划表
     *
     * @param id 主计划表主键
     * @return 主计划表
     */
    @Override
    public MainPlanTable selectMainPlanTableById(String id) {
        return mainPlanTableMapper.selectMainPlanTableById(id);
    }

    /**
     * 查询主计划表列表
     *
     * @param mainPlanTable 主计划表
     * @return 主计划表
     */
    @Override
    public List<MainPlanTable> selectMainPlanTableList(MainPlanTable mainPlanTable) {
        return mainPlanTableMapper.selectMainPlanTableList(mainPlanTable);
    }

    /**
     * 新增主计划表
     *
     * @param mainPlanTable 主计划表
     * @return 结果
     */
    @Override
    public int insertMainPlanTable(MainPlanTable mainPlanTable) {
        return mainPlanTableMapper.insertMainPlanTable(mainPlanTable);
    }

    /**
     * 修改主计划表
     *
     * @param mainPlanTable 主计划表
     * @return 结果
     */
    @Override
    public int updateMainPlanTable(MainPlanTable mainPlanTable) {
        return mainPlanTableMapper.updateMainPlanTable(mainPlanTable);
    }

    /**
     * 批量删除主计划表
     *
     * @param ids 需要删除的主计划表主键
     * @return 结果
     */
    @Override
    public int deleteMainPlanTableByIds(String[] ids) {
        return mainPlanTableMapper.deleteMainPlanTableByIds(ids);
    }

    /**
     * 删除主计划表信息
     *
     * @param id 主计划表主键
     * @return 结果
     */
    @Override
    public int deleteMainPlanTableById(String id) {
        return mainPlanTableMapper.deleteMainPlanTableById(id);
    }
}




