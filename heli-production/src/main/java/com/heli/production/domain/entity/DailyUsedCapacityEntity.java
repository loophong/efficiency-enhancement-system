package com.heli.production.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 每日使用的产能对象 production_daily_used_capacity
 *
 * @author hong
 * @date 2025-02-26
 */
@TableName(value = "production_daily_used_capacity")
@Data
public class DailyUsedCapacityEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 排产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;

    /**
     * 产能车型
     */
    private String capacityType;

    /**
     * 产量
     */
    private Long productionQuantity;

    /**
     * 设置的产量
     */
    private Long quantitySettings;
}
