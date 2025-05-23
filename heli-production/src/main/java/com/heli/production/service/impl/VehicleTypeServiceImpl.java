package com.heli.production.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.listener.VehicleTypeTableListener;
import com.heli.production.domain.entity.VehicleTypeEntity;
import com.heli.production.service.IVehicleTypeService;
import com.heli.production.mapper.VehicleTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_vehicle_type】的数据库操作Service实现
 * @createDate 2025-01-08 14:09:05
 */
@Service
@Slf4j
public class VehicleTypeServiceImpl extends ServiceImpl<VehicleTypeMapper, VehicleTypeEntity> implements IVehicleTypeService {

    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;

    /**
     * 查询车型
     *
     * @param id 车型主键
     * @return 车型
     */
    @Override
    public VehicleTypeEntity selectVehicleTypeById(Long id) {
        return vehicleTypeMapper.selectVehicleTypeById(id);
    }

    /**
     * 查询车型列表
     *
     * @param vehicleType 车型
     * @return 车型
     */
    @Override
    public List<VehicleTypeEntity> selectVehicleTypeList(VehicleTypeEntity vehicleType) {
        return vehicleTypeMapper.selectVehicleTypeList(vehicleType);
    }

    /**
     * 新增车型
     *
     * @param vehicleType 车型
     * @return 结果
     */
    @Override
    public int insertVehicleType(VehicleTypeEntity vehicleType) {
        return vehicleTypeMapper.insertVehicleType(vehicleType);
    }

    /**
     * 修改车型
     *
     * @param vehicleType 车型
     * @return 结果
     */
    @Override
    public int updateVehicleType(VehicleTypeEntity vehicleType) {
        return vehicleTypeMapper.updateVehicleType(vehicleType);
    }

    /**
     * 批量删除车型
     *
     * @param ids 需要删除的车型主键
     * @return 结果
     */
    @Override
    public int deleteVehicleTypeByIds(Long[] ids) {
        return vehicleTypeMapper.deleteVehicleTypeByIds(ids);
    }

    /**
     * 删除车型信息
     *
     * @param id 车型主键
     * @return 结果
     */
    @Override
    public int deleteVehicleTypeById(Long id) {
        return vehicleTypeMapper.deleteVehicleTypeById(id);
    }

    @Override
    @Transactional
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            log.info("开始读取文件: {}", fileName);
            // 读取文件前清空数据库
            log.info("开始清空数据库");
            vehicleTypeMapper.delete(new QueryWrapper<>());
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(), VehicleTypeEntity.class, new VehicleTypeTableListener(vehicleTypeMapper)).headRowNumber(2).sheet("内销 2-3.8t 内燃车").doRead();
                EasyExcel.read(excelFile.getInputStream(), VehicleTypeEntity.class, new VehicleTypeTableListener(vehicleTypeMapper)).headRowNumber(2).sheet("内销 4-10t 内燃车").doRead();
                EasyExcel.read(excelFile.getInputStream(), VehicleTypeEntity.class, new VehicleTypeTableListener(vehicleTypeMapper)).headRowNumber(2).sheet("内销 1-3.8t 电动车").doRead();
                EasyExcel.read(excelFile.getInputStream(), VehicleTypeEntity.class, new VehicleTypeTableListener(vehicleTypeMapper)).headRowNumber(2).sheet("外销车型 ").doRead();
                log.info("读取文件成功: {}", fileName);

            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

//            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
//            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }

}




