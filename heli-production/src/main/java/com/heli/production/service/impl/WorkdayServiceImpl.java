package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.service.IWorkdayService;
import com.heli.production.mapper.WorkdayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author loophong
* @description 针对表【production_workday】的数据库操作Service实现
* @createDate 2025-01-09 17:55:18
*/
@Service
public class WorkdayServiceImpl extends ServiceImpl<WorkdayMapper, WorkdayEntity> implements IWorkdayService {

    @Autowired
    private WorkdayMapper workdayMapper;

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




