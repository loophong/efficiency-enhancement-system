package com.heli.production.mapper;

import com.heli.production.domain.entity.WorkdayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
* @author loophong
* @description 针对表【production_workday】的数据库操作Mapper
* @createDate 2025-01-09 17:55:18
* @Entity com.heli.production.domain.ProductionWorkday
*/
public interface WorkdayMapper extends BaseMapper<WorkdayEntity> {

    /**
     * 查询工作日
     *
     * @param date 工作日主键
     * @return 工作日
     */
    public WorkdayEntity selectWorkdayByDate(Date date);

    /**
     * 查询工作日列表
     *
     * @param workday 工作日
     * @return 工作日集合
     */
    public List<WorkdayEntity> selectWorkdayList(WorkdayEntity workday);

    /**
     * 新增工作日
     *
     * @param workday 工作日
     * @return 结果
     */
    public int insertWorkday(WorkdayEntity workday);

    /**
     * 修改工作日
     *
     * @param workday 工作日
     * @return 结果
     */
    public int updateWorkday(WorkdayEntity workday);
}




