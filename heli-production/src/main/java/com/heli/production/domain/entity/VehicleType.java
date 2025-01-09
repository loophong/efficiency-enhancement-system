package com.heli.production.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName production_vehicle_type
 */
@TableName(value ="production_vehicle_type")
@Data
public class VehicleType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  id
     */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /**
     * 系列名称
     */
    @ExcelProperty(value = "系列")
    private String series;

    /**
     * 吨位
     */
    @ExcelProperty(value = "吨位")
    private String tonnage;

    /**
     * 车型
     */
    @ExcelProperty(value = "车型")
    private String vehicleType;

    /**
     * 发动机/主要配置
     */
    @ExcelProperty(value = "发动机/主要配置")
    private String mainConfiguration;

    /**
     * 标配：阀片、电瓶、轮胎
     */
    @ExcelProperty(value = "标配：阀片、电瓶、轮胎")
    private String standardEquipment;

}