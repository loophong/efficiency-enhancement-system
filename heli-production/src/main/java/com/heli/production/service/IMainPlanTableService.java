package com.heli.production.service;

import com.heli.production.domain.entity.MainPlanTableEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_main_plan_table】的数据库操作Service
 * @createDate 2025-01-07 17:57:23
 */
public interface IMainPlanTableService extends IService<MainPlanTableEntity> {
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
     * 批量删除主计划表
     *
     * @param ids 需要删除的主计划表主键集合
     * @return 结果
     */
    public int deleteMainPlanTableByIds(String[] ids);

    /**
     * 删除主计划表信息
     *
     * @param id 主计划表主键
     * @return 结果
     */
    public int deleteMainPlanTableById(String id);

    void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadDate);
}
