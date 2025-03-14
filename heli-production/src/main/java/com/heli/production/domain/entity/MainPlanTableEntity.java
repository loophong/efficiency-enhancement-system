package com.heli.production.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName production_main_plan_table
 */
@Data
@TableName(value = "production_main_plan_table")
public class MainPlanTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    /**
     * 网点名称
     */
    @ExcelProperty(value = "网点")
    @Excel(name = "网点")
    private String branch;

    /**
     * 合同号
     */
    @ExcelProperty(value = "合同号")
    @Excel(name = "合同号")
    private String contractNumber;

    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String orderNumber;

    /**
     * 接单日期
     */
    @ExcelProperty(value = "接单日期")
    @Excel(name = "接单日期")
    private Date orderDate;

    /**
     * 车型
     */
    @ExcelProperty(value = "车型")
    @Excel(name = "车型")
    private String vehicleModel;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    @Excel(name = "数量")
    private Integer quantity;

    /**
     * 阀片
     */
    @ExcelProperty(value = "阀片")
    @Excel(name = "阀片")
    private String valvePlate;

    /**
     * 货叉
     */
    @ExcelProperty(value = "货叉")
    @Excel(name = "货叉")
    private String forklift;

    /**
     * 门架
     */
    @ExcelProperty(value = "门架")
    @Excel(name = "门架")
    private String mast;

    /**
     * 空滤
     */
    @ExcelProperty(value = "空滤")
    @Excel(name = "空滤")
    private String airFilter;

    /**
     * 属具
     */
    @ExcelProperty(value = "属具")
    @Excel(name = "属具")
    private String attachments;

    /**
     * 轮胎
     */
    @ExcelProperty(value = "轮胎")
    @Excel(name = "轮胎")
    private String tires;

    /**
     * 配置详细信息
     */
    @ExcelProperty(value = "配置")
    @Excel(name = "配置")
    private String configuration;

    /**
     * 车号
     */
    @ExcelProperty(value = "车号")
    @Excel(name = "车号")
    private String vehicleNumber;

    /**
     * 备注信息
     */
    @ExcelProperty(value = "备注")
    @Excel(name = "备注信息")
    private String remarks;

    /**
     * 交货形式
     */
    @ExcelProperty(value = "交货形式")
    @Excel(name = "交货形式")
    private String deliveryForm;

    /**
     * 交货地点
     */
    @ExcelProperty(value = "交货地点")
    @Excel(name = "交货地点")
    private String deliveryLocation;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人")
    @Excel(name = "联系人姓名")
    private String contactPerson;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "电话")
    @Excel(name = "联系电话")
    private String phoneNumber;

    /**
     * 订单系统交货期
     */
    @ExcelProperty(value = "订单系统交货期")
    @Excel(name = "订单系统交货期")
    private Date systemDeliveryDate;

    /**
     * 生产回复完工日期
     */
    @ExcelProperty(value = "生产回复完工期")
    @Excel(name = "生产回复完工日期")
    private Date productionCompletionDate;

    /**
     * 采购回复到货时间
     */
    @ExcelProperty(value = "采购回复到货时间")
    @Excel(name = "采购回复到货时间")
    private Date procurementArrivalDate;

    /**
     * 缺件记录
     */
    @ExcelProperty(index = 22)
    @Excel(name = "缺件记录")
    private String missingPartRecords;

    /**
     * 上传时间
     */
    @ExcelIgnore
    private Date uploadDate;

}