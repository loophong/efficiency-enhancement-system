package com.heli.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 主计划表
 * @TableName production_order_scheduling
 */
@TableName(value ="production_order_scheduling")
@Data
public class OrderSchedulingEntity extends MainPlanTableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 生产周期
     */
    private Integer productionCycle;

    /**
     * 产能型号
     */
    private String capacityType;

    /**
     * 最晚上线日期
     */
    private Date latestOnlineDate;

    /**
     * 是否超期
     */
    private Integer isOverdue;

    /**
     * 是否加急
     */
    private Integer isUrgent;

    /**
     * 是否排产
     */
    private Integer isScheduling;

    /**
     * 上线时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date onlineDate;

}