package com.heli.production.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 历史订单统计对象 production_history_order_statistics
 *
 * @author hong
 * @date 2025-02-20
 */
@TableName(value = "production_history_order_statistics")
@Data
public class HistoryOrderStatisticsEntity implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 统计年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date yearAndMonth;

    /**
     * 车型
     */
    private String vehicleModel;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 产能类型
     */
    private String capacityType;

}
