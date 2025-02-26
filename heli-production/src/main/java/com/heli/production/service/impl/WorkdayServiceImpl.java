package com.heli.production.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.service.IWorkdayService;
import com.heli.production.mapper.WorkdayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_workday】的数据库操作Service实现
 * @createDate 2025-01-09 17:55:18
 */
@Slf4j
@Service
public class WorkdayServiceImpl extends ServiceImpl<WorkdayMapper, WorkdayEntity> implements IWorkdayService {

    @Autowired
    private WorkdayMapper workdayMapper;

    @Override
    public Date getLatestOnlineDate(Date systemDeliveryDate, Integer productionCycle) {

        if (systemDeliveryDate != null && productionCycle != null) {

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 查询systemDeliveryDate之前的日期  100天
            Date limit = new Date(systemDeliveryDate.getTime() - 100L * 24 * 60 * 60 * 1000);
            List<WorkdayEntity> workdayList = workdayMapper.selectList(
                    new LambdaQueryWrapper<WorkdayEntity>()
                            .between(WorkdayEntity::getDate, limit, systemDeliveryDate)
                            .orderByDesc(WorkdayEntity::getDate));

            // 从systemDeliveryDate开始，向前推productionCycle个工作日
            int count = 0;
            for (WorkdayEntity workday : workdayList) {
                if (workday.getStatus() == 1) {
                    count++;
                    if (count == productionCycle) {
//                        log.info("系统交付日期为：" + sdf.format(systemDeliveryDate));
//                        log.info("生产周期为：" + productionCycle);
//                        log.info("最晚上线日期为：" + sdf.format(workday.getDate()));
                        return workday.getDate();
                    }
                }
            }

        }
        return null;
    }

//    /**
//     * 查询工作日
//     *
//     * @param date 工作日主键
//     * @return 工作日
//     */
//    @Override
//    public WorkdayEntity selectWorkdayByDate(Date date) {
//        return workdayMapper.selectWorkdayByDate(date);
//    }
//
//    /**
//     * 查询工作日列表
//     *
//     * @param workday 工作日
//     * @return 工作日
//     */
//    @Override
//    public List<WorkdayEntity> selectWorkdayList(WorkdayEntity workday) {
//        return workdayMapper.selectWorkdayList(workday);
//    }
//
//    /**
//     * 新增工作日
//     *
//     * @param workday 工作日
//     * @return 结果
//     */
//    @Override
//    public int insertWorkday(WorkdayEntity workday) {
//        return workdayMapper.insertWorkday(workday);
//    }
//
//    /**
//     * 修改工作日
//     *
//     * @param workday 工作日
//     * @return 结果
//     */
//    @Override
//    public int updateWorkday(WorkdayEntity workday) {
//        return workdayMapper.updateWorkday(workday);
//    }

}




