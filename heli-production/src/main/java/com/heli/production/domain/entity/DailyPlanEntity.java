package com.heli.production.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

/**
 * 日生产计划对象 production_daily_plan
 *
 * @author hong
 * @date 2025-03-12
 */
@TableName(value = "production_daily_plan")
@Data
public class DailyPlanEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private String serialNumber;

    /**
     * 生产订单号
     */
    @Excel(name = "生产订单号")
    private String productionOrderNumber;

    /**
     * 合同号
     */
    @Excel(name = "合同号")
    private String contractNumber;

    /**
     * 车型（项目号）
     */
    @Excel(name = "车型", readConverterExp = "项=目号")
    private String vehicleModel;

    /**
     * 门架
     */
    @Excel(name = "门架")
    private String mast;

    /**
     * 项目说明
     */
    @Excel(name = "项目说明")
    private String projectDescription;

    /**
     * 车号
     */
    @Excel(name = "车号")
    private String vehicleNumber;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long quantity;

    /**
     * 属具
     */
    @Excel(name = "属具")
    private String attachments;

    /**
     * 阀片
     */
    @Excel(name = "阀片")
    private String valvePlate;

    /**
     * 描述性配置信息
     */
    @Excel(name = "描述性配置信息")
    private String descriptiveConfigurationInfo;

    /**
     * 订单系统交货期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单系统交货期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date systemDeliveryDate;

    /**
     * 网点名称（客户）
     */
    @Excel(name = "网点名称", readConverterExp = "客=户")
    private String branch;

    /**
     * 接单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "接单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;

    /**
     * 上线日期（排产日期）
     */
    @Excel(name = "上线日期", readConverterExp = "排产日期")
    private Date onlineDate;

}
