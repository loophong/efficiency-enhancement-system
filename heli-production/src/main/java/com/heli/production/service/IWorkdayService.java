package com.heli.production.service;

import com.heli.production.domain.entity.WorkdayEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.production.mapper.WorkdayMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_workday】的数据库操作Service
 * @createDate 2025-01-09 17:55:18
 */
public interface IWorkdayService extends IService<WorkdayEntity> {
    Date getLatestOnlineDate(Date systemDeliveryDate, Integer productionCycle);
//    /**
//     * 查询工作日
//     *
//     * @param date 工作日主键
//     * @return 工作日
//     */
//    public WorkdayEntity selectWorkdayByDate(Date date);
//
//    /**
//     * 查询工作日列表
//     *
//     * @param workday 工作日
//     * @return 工作日集合
//     */
//    public List<WorkdayEntity> selectWorkdayList(WorkdayEntity workday);
//
//    /**
//     * 新增工作日
//     *
//     * @param workday 工作日
//     * @return 结果
//     */
//    public int insertWorkday(WorkdayEntity workday);
//
//    /**
//     * 修改工作日
//     *
//     * @param workday 工作日
//     * @return 结果
//     */
//    public int updateWorkday(WorkdayEntity workday);

}
