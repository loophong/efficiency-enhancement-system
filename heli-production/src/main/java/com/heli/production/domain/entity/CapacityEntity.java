package com.heli.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName production_capacity
 */
@TableName(value ="production_capacity")
@Data
public class CapacityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 车型
     */
    private String capacityType;

    /**
     * 产量
     */
    private Integer productionQuantity;

    /**
     * 状态（0:停产 1：在产）
     */
    private Integer productionStatus;

    /**
     * 备注
     */
    private String remarks;

}