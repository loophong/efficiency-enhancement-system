package com.heli.production.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName production_history_order
 */
@TableName(value = "production_history_order")
@Data
public class HistoryOrderEntity implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /**
     * 网点
     */
    @ExcelProperty(value = "网点")
    private String branch;

    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    private String orderNumber;

    /**
     * 接单日期
     */
    @ExcelProperty(value = "接单日期")
    private Date orderDate;

    /**
     * 车型
     */
    @ExcelProperty(value = "车型")
    private String vehicleModel;

    /**
     * 门架
     */
    @ExcelProperty(value = "门架")
    private String mastHeight;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Integer quantity;

    /**
     * 订单年份
     */
    @ExcelIgnore
    private Date orderYear;

    /**
     * 产能类型
     */
    @ExcelIgnore
    private String capacityType;

}