package com.heli.production.service;

import com.heli.production.domain.entity.VehicleTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_vehicle_type】的数据库操作Service
 * @createDate 2025-01-08 14:09:05
 */
public interface IVehicleTypeService extends IService<VehicleTypeEntity> {
    /**
     * 查询车型
     *
     * @param id 车型主键
     * @return 车型
     */
    public VehicleTypeEntity selectVehicleTypeById(Long id);

    /**
     * 查询车型列表
     *
     * @param vehicleType 车型
     * @return 车型集合
     */
    public List<VehicleTypeEntity> selectVehicleTypeList(VehicleTypeEntity vehicleType);

    /**
     * 新增车型
     *
     * @param vehicleType 车型
     * @return 结果
     */
    public int insertVehicleType(VehicleTypeEntity vehicleType);

    /**
     * 修改车型
     *
     * @param vehicleType 车型
     * @return 结果
     */
    public int updateVehicleType(VehicleTypeEntity vehicleType);

    /**
     * 批量删除车型
     *
     * @param ids 需要删除的车型主键集合
     * @return 结果
     */
    public int deleteVehicleTypeByIds(Long[] ids);

    /**
     * 删除车型信息
     *
     * @param id 车型主键
     * @return 结果
     */
    public int deleteVehicleTypeById(Long id);

    void readSalaryExcelToDB(String fileName,  MultipartFile excelFile);
}
