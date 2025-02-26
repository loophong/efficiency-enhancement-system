package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.DailyUsedCapacity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.DailyUsedCapacityMapper;
import com.heli.production.service.IDailyUsedCapacityService;

import java.util.List;

/**
 * 每日使用的产能Service业务层处理
 *
 * @author hong
 * @date 2025-02-26
 */
@Service
public class DailyUsedCapacityServiceImpl extends ServiceImpl<DailyUsedCapacityMapper, DailyUsedCapacity> implements IDailyUsedCapacityService {
    @Autowired
    private DailyUsedCapacityMapper dailyUsedCapacityMapper;

    /**
     * 查询每日使用的产能
     *
     * @param id 每日使用的产能主键
     * @return 每日使用的产能
     */
    @Override
    public DailyUsedCapacity selectDailyUsedCapacityById(String id) {
        return dailyUsedCapacityMapper.selectDailyUsedCapacityById(id);
    }

    /**
     * 查询每日使用的产能列表
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 每日使用的产能
     */
    @Override
    public List<DailyUsedCapacity> selectDailyUsedCapacityList(DailyUsedCapacity dailyUsedCapacity) {
        return dailyUsedCapacityMapper.selectDailyUsedCapacityList(dailyUsedCapacity);
    }

    /**
     * 新增每日使用的产能
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 结果
     */
    @Override
    public int insertDailyUsedCapacity(DailyUsedCapacity dailyUsedCapacity) {
        return dailyUsedCapacityMapper.insertDailyUsedCapacity(dailyUsedCapacity);
    }

    /**
     * 修改每日使用的产能
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 结果
     */
    @Override
    public int updateDailyUsedCapacity(DailyUsedCapacity dailyUsedCapacity) {
        return dailyUsedCapacityMapper.updateDailyUsedCapacity(dailyUsedCapacity);
    }

    /**
     * 批量删除每日使用的产能
     *
     * @param ids 需要删除的每日使用的产能主键
     * @return 结果
     */
    @Override
    public int deleteDailyUsedCapacityByIds(String[] ids) {
        return dailyUsedCapacityMapper.deleteDailyUsedCapacityByIds(ids);
    }

    /**
     * 删除每日使用的产能信息
     *
     * @param id 每日使用的产能主键
     * @return 结果
     */
    @Override
    public int deleteDailyUsedCapacityById(String id) {
        return dailyUsedCapacityMapper.deleteDailyUsedCapacityById(id);
    }
}
