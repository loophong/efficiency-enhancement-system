package com.heli.production.mapper;

import com.heli.production.domain.entity.VehicleType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author loophong
* @description 针对表【production_vehicle_type】的数据库操作Mapper
* @createDate 2025-01-08 14:09:05
* @Entity com.heli.production.domain.ProductionVehicleType
*/
public interface VehicleTypeMapper extends BaseMapper<VehicleType> {
    /**
     * 查询车型
     *
     * @param id 车型主键
     * @return 车型
     */
    public VehicleType selectVehicleTypeById(Long id);

    /**
     * 查询车型列表
     *
     * @param vehicleType 车型
     * @return 车型集合
     */
    public List<VehicleType> selectVehicleTypeList(VehicleType vehicleType);

    /**
     * 新增车型
     *
     * @param vehicleType 车型
     * @return 结果
     */
    public int insertVehicleType(VehicleType vehicleType);

    /**
     * 修改车型
     *
     * @param vehicleType 车型
     * @return 结果
     */
    public int updateVehicleType(VehicleType vehicleType);

    /**
     * 删除车型
     *
     * @param id 车型主键
     * @return 结果
     */
    public int deleteVehicleTypeById(Long id);

    /**
     * 批量删除车型
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVehicleTypeByIds(Long[] ids);
}





