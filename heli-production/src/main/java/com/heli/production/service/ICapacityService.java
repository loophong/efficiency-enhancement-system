package com.heli.production.service;

import com.heli.production.domain.entity.CapacityEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author loophong
* @description 针对表【production_capacity】的数据库操作Service
* @createDate 2025-01-09 16:10:35
*/
public interface ICapacityService extends IService<CapacityEntity> {

    /**
     * 查询产能
     *
     * @param id 产能主键
     * @return 产能
     */
    public CapacityEntity selectCapacityById(String id);

    /**
     * 查询产能列表
     *
     * @param capacity 产能
     * @return 产能集合
     */
    public List<CapacityEntity> selectCapacityList(CapacityEntity capacity);

    /**
     * 新增产能
     *
     * @param capacity 产能
     * @return 结果
     */
    public int insertCapacity(CapacityEntity capacity);

    /**
     * 修改产能
     *
     * @param capacity 产能
     * @return 结果
     */
    public int updateCapacity(CapacityEntity capacity);

    /**
     * 批量删除产能
     *
     * @param ids 需要删除的产能主键集合
     * @return 结果
     */
    public int deleteCapacityByIds(String[] ids);

    /**
     * 删除产能信息
     *
     * @param id 产能主键
     * @return 结果
     */
    public int deleteCapacityById(String id);

}
