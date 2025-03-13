package com.heli.production.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

/**
 * 计划会签对象 production_plan_co_sign
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@TableName(value = "production_plan_co_sign")
@Data
public class PlanCoSignEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 会签编号
     */
    @Excel(name = "会签编号")
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 会签人编号
     */
    @Excel(name = "会签人编号")
    private Long reviewerId;

    /**
     * 会签人
     */
    @Excel(name = "会签人")
    private String reviewerName;

    /**
     * 会签状态
     */
    @Excel(name = "会签状态")
    private String reviewStatus;

    /**
     * 上线日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上线日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date onlineDate;

    /**
     * 评审意见
     */
    @Excel(name = "评审意见")
    private String comments;

}
