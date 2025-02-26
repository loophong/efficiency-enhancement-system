package com.heli.production.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;

import java.util.List;


/**
 * 每日使用的产能Service接口
 *
 * @author hong
 * @date 2025-02-26
 */
public interface IDailyUsedCapacityService extends IService<DailyUsedCapacityEntity> {
    /**
     * 查询每日使用的产能
     *
     * @param id 每日使用的产能主键
     * @return 每日使用的产能
     */
    public DailyUsedCapacityEntity selectDailyUsedCapacityById(String id);

    /**
     * 查询每日使用的产能列表
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 每日使用的产能集合
     */
    public List<DailyUsedCapacityEntity> selectDailyUsedCapacityList(DailyUsedCapacityEntity dailyUsedCapacity);

    /**
     * 新增每日使用的产能
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 结果
     */
    public int insertDailyUsedCapacity(DailyUsedCapacityEntity dailyUsedCapacity);

    /**
     * 修改每日使用的产能
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 结果
     */
    public int updateDailyUsedCapacity(DailyUsedCapacityEntity dailyUsedCapacity);

    /**
     * 批量删除每日使用的产能
     *
     * @param ids 需要删除的每日使用的产能主键集合
     * @return 结果
     */
    public int deleteDailyUsedCapacityByIds(String[] ids);

    /**
     * 删除每日使用的产能信息
     *
     * @param id 每日使用的产能主键
     * @return 结果
     */
    public int deleteDailyUsedCapacityById(String id);
}
