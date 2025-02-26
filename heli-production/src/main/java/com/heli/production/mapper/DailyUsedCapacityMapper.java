package com.heli.production.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.production.domain.entity.DailyUsedCapacity;

import java.util.List;

/**
 * 每日使用的产能Mapper接口
 *
 * @author hong
 * @date 2025-02-26
 */
public interface DailyUsedCapacityMapper extends BaseMapper<DailyUsedCapacity> {
    /**
     * 查询每日使用的产能
     *
     * @param id 每日使用的产能主键
     * @return 每日使用的产能
     */
    public DailyUsedCapacity selectDailyUsedCapacityById(String id);

    /**
     * 查询每日使用的产能列表
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 每日使用的产能集合
     */
    public List<DailyUsedCapacity> selectDailyUsedCapacityList(DailyUsedCapacity dailyUsedCapacity);

    /**
     * 新增每日使用的产能
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 结果
     */
    public int insertDailyUsedCapacity(DailyUsedCapacity dailyUsedCapacity);

    /**
     * 修改每日使用的产能
     *
     * @param dailyUsedCapacity 每日使用的产能
     * @return 结果
     */
    public int updateDailyUsedCapacity(DailyUsedCapacity dailyUsedCapacity);

    /**
     * 删除每日使用的产能
     *
     * @param id 每日使用的产能主键
     * @return 结果
     */
    public int deleteDailyUsedCapacityById(String id);

    /**
     * 批量删除每日使用的产能
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDailyUsedCapacityByIds(String[] ids);
}
