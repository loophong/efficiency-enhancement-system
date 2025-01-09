package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.CapacityEntity;
import com.heli.production.service.ICapacityService;
import com.heli.production.mapper.CapacityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_capacity】的数据库操作Service实现
 * @createDate 2025-01-09 16:10:35
 */
@Service
public class CapacityServiceImpl extends ServiceImpl<CapacityMapper, CapacityEntity> implements ICapacityService {

    @Autowired
    private CapacityMapper capacityMapper;

    /**
     * 查询产能
     *
     * @param id 产能主键
     * @return 产能
     */
    @Override
    public CapacityEntity selectCapacityById(String id) {
        return capacityMapper.selectCapacityById(id);
    }

    /**
     * 查询产能列表
     *
     * @param capacityEntity 产能
     * @return 产能
     */
    @Override
    public List<CapacityEntity> selectCapacityList(CapacityEntity capacityEntity) {
        return capacityMapper.selectCapacityList(capacityEntity);
    }

    /**
     * 新增产能
     *
     * @param capacityEntity 产能
     * @return 结果
     */
    @Override
    public int insertCapacity(CapacityEntity capacityEntity) {
        return capacityMapper.insertCapacity(capacityEntity);
    }

    /**
     * 修改产能
     *
     * @param capacityEntity 产能
     * @return 结果
     */
    @Override
    public int updateCapacity(CapacityEntity capacityEntity) {
        return capacityMapper.updateCapacity(capacityEntity);
    }

    /**
     * 批量删除产能
     *
     * @param ids 需要删除的产能主键
     * @return 结果
     */
    @Override
    public int deleteCapacityByIds(String[] ids) {
        return capacityMapper.deleteCapacityByIds(ids);
    }

    /**
     * 删除产能信息
     *
     * @param id 产能主键
     * @return 结果
     */
    @Override
    public int deleteCapacityById(String id) {
        return capacityMapper.deleteCapacityById(id);
    }
}




