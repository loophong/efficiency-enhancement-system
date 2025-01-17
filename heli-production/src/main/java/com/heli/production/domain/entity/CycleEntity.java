package com.heli.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 产能表
 * @TableName production_cycle
 */
@TableName(value ="production_cycle")
@Data
public class CycleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 系列名称
     */
    private String series;

    /**
     * 吨位
     */
    private String tonnage;

    /**
     * 车型
     */
    private String vehicleModel;

    /**
     * 产能车型
     */
    private String capacityVehicleModel;

    /**
     * 生产周期
     */
    private Integer productionCycle;
}