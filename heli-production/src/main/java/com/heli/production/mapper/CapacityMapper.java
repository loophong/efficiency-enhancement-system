package com.heli.production.mapper;

import com.heli.production.domain.entity.CapacityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author loophong
* @description 针对表【production_capacity】的数据库操作Mapper
* @createDate 2025-01-09 16:10:35
* @Entity com.heli.production.domain.ProductionCapacity
*/
public interface CapacityMapper extends BaseMapper<CapacityEntity> {

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
     * @param capacityEntity 产能
     * @return 产能集合
     */
    public List<CapacityEntity> selectCapacityList(CapacityEntity capacityEntity);

    /**
     * 删除产能
     *
     * @param id 产能主键
     * @return 结果
     */
    public int deleteCapacityById(String id);

    /**
     * 批量删除产能
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCapacityByIds(String[] ids);

}




